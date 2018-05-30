package com.weirdo.controller;

import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.weirdo.feign.UserFeignClient;
import com.weirdo.model.UserDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * @Author ML.Zhang
 * @Date 2018/5/25
 */

@Controller
@RequestMapping("/user")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  @Autowired
  private UserFeignClient userFeignClient;
  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private LoadBalancerClient loadBalancerClient;


  @GetMapping("/{id}")
  @ResponseBody
  public Object findById(@PathVariable Integer id) {
    return this.userFeignClient.findUser(id);
  }

  @GetMapping("/users/{id}")
  @ResponseBody
  public UserDomain findUserById(@PathVariable Integer id) {
    return this.restTemplate.getForObject("http://localhost:8081/user/"+id,UserDomain.class);
  }


  @GetMapping("/users")
  public String  findAllUsers(ModelMap modelMap) {
    Object users = this.userFeignClient.findAllUsers();
    modelMap.put("info",users);
    return "index";
  }


  @GetMapping("/log-user-instance")
  public void logUserInstance() {
    ServiceInstance serviceInstance = this.loadBalancerClient.choose("microservice-provider-user");
    // 打印当前选择的是哪个节点
    UserController.LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
  }



    /**
   * 测试URL：http://localhost:8010/user/get1?userId=1&userName=张三
   * @param user user
   * @return 用户信息
   */
  @GetMapping("/get1")
  @ResponseBody
  @HystrixCommand(fallbackMethod = "myFallback",commandProperties ={
          @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
          value = "5000"),
          @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",
          value = "1000")},
          threadPoolProperties = {
            @HystrixProperty(name = "coreSize",value = "1"),
            @HystrixProperty(name = "maxQueueSize",value = "10")
          }
  )
  public UserDomain get1(UserDomain user) {
    return this.userFeignClient.get1(user.getUserId(), user.getUserName());
  }

    /**
     * 方法simpleHystrixClientCall的回退方法，可以指定将hystrix执行失败异常传入到方法中
     * @param user ystrix执行失败的传入方法的请求
     * @return
     */
    UserDomain myFallback(UserDomain user) {
        UserDomain userDomain =new UserDomain();
        user.setUserName("回退用户");
        return user;
    }

  /**
   * 测试URL：http://localhost:8010/user/get2?userId=1&userName=张三
   * @param user user
   * @return 用户信息
   */
  @GetMapping("/get2")
  @ResponseBody
  public UserDomain get2(UserDomain user) {
    Map<String, Object> map = Maps.newHashMap();
    map.put("userId", user.getUserId());
    map.put("userName", user.getUserName());
    return this.userFeignClient.get2(map);
  }

  /**
   * 测试URL:http://localhost:8010/user/post?userId=1&userName=张三
   * @param user user
   * @return 用户信息
   */
  @GetMapping("/post")
  @ResponseBody
  public UserDomain post(UserDomain user) {
    return this.userFeignClient.post(user);
  }


}
