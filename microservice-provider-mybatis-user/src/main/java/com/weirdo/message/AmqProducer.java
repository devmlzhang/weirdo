package com.weirdo.message;

import com.alibaba.fastjson.JSONObject;
import java.util.UUID;
import javax.jms.Queue;

import com.weirdo.model.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author ML.Zhang
 * @Date 2018/5/28
 */
@Component//将Producer注入到容器
@EnableScheduling//定时任务的注解
public class AmqProducer {

    /**
     * 定时发送队列消息
     */

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired//注入
    private Queue queue;
    private int age = 18;
    //@Scheduled(fixedDelay = 5000)//每隔5秒钟执行这个方法
    public void send() {
        age++;
        UserDomain userEntity = new UserDomain(age, UUID.randomUUID().toString(), Integer.toString(age),"");
        String json = new JSONObject().toJSONString(userEntity);//将实体类转换成json字符串
        System.out.println("json:" + json+"  queue:"+queue);
        jmsTemplate.convertAndSend(queue, json);//向指定队列中发送消息
    }
}
