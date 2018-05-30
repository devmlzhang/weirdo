package com.weirdo.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.jms.Queue;

/**
 * @Author ML.Zhang
 * @Date 2018/5/28
 */
@Configuration
public class QueueConfig {
    @Value("${queue}")
    private String queueName;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(queueName);
    }

}
