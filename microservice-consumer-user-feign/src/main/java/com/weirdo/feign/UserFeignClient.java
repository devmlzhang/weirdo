package com.weirdo.feign;

import com.weirdo.model.UserDomain;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @Author ML.Zhang
 * @Date 2018/5/25
 */

@Service
@FeignClient(name = "microservice-provider-user" ,fallback = UserFeignClient.FeignClientFallback.class)
public interface UserFeignClient {

  @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
  public UserDomain findUser(@PathVariable("id") Integer id);

  @RequestMapping(value = "/user/all",method = RequestMethod.GET)
  public Object findAllUsers();

  @RequestMapping(value = "/user/get", method = RequestMethod.GET)
  public UserDomain get1(@RequestParam("userId") Integer id, @RequestParam("userName") String username);

  @RequestMapping(value = "/user/get", method = RequestMethod.GET)
  public UserDomain get2(@RequestParam Map<String, Object> map);

  @RequestMapping(value = "/user/post", method = RequestMethod.POST)
  public UserDomain post(@RequestBody UserDomain user);




  /**
   * 回退类FeignClientFallback需实现Feign Client接口
   * FeignClientFallback也可以是public class，没有区别
   */
  @Component
  class FeignClientFallback implements UserFeignClient {
    @Override
    public Object findAllUsers() {
      return "findAllUsers===使用了熔断器";
    }

    @Override
    public UserDomain findUser(Integer id) {
      UserDomain userDomain = new UserDomain();
      userDomain.setUserId(2222);
      userDomain.setUserName("findUser==使用了熔断器");
      return userDomain;
    }



    @Override
    public UserDomain get1(Integer id, String username) {
      UserDomain userDomain = new UserDomain();
      userDomain.setUserId(454545);
      userDomain.setUserName("findUser==使用了熔断器get1");
      return userDomain;
    }

    @Override
    public UserDomain get2(Map<String, Object> map) {
      UserDomain userDomain = new UserDomain();
      userDomain.setUserId(454545);
      userDomain.setUserName("findUser==使用了熔断器get2");
      return userDomain;
    }

    @Override
    public UserDomain post(UserDomain user) {
      return null;
    }
  }
}
