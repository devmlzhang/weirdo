package com.weirdo.message;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author ML.Zhang
 * @Date 2018/6/11
 * @Description
 */

@Component
@RabbitListener(queues = "fanout.A")
public class RabbitConsumera {
    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver A: " + message);
    }
}
