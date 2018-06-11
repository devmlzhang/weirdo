package com.weirdo.message;

import com.alibaba.fastjson.JSONObject;
import com.weirdo.model.UserDomain;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author ML.Zhang
 * @Date 2018/5/28
 */
@Component
@RabbitListener(queues = "hello")
public class RabbitConsumer {
    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }
}
