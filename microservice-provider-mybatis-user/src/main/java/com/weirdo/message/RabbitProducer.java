package com.weirdo.message;

import com.alibaba.fastjson.JSONObject;
import com.weirdo.model.UserDomain;
import com.weirdo.service.MqttClientServiceImpl;
import com.weirdo.util.WebsocketModel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


/**
 * @Author ML.Zhang
 * @Date 2018/5/29
 */

@Component//将Producer注入到容器
@EnableScheduling//定时任务的注解
public class RabbitProducer {
    /**
     * 定时发送主题消息
     */

    @Autowired
    private AmqpTemplate rabbitTemplate;


   // @Scheduled(fixedDelay = 5000)//每隔5秒钟执行这个方法
    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }



    private int age = 18;
    @Scheduled(fixedDelay = 5000)//每隔5秒钟执行这个方法
    public void send2() {
        age++;
        UserDomain userEntity = new UserDomain(age, UUID.randomUUID().toString(), Integer.toString(age),"1222122");
        WebsocketModel  websocketModel = new WebsocketModel();
        websocketModel.setData(userEntity);
        websocketModel.setType("message");
        websocketModel.setOperation("add");
        System.out.println("RabbitProducer:" + websocketModel.toString());
        this.rabbitTemplate.convertAndSend("fanoutExchange","", websocketModel.toString());
    }

}
