package com.weirdo.config;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;

/**
 * @Author ML.Zhang
 * @Date 2018/5/29
 */
//@Configuration
public class MqttConfig {


    /*@Bean
    public MemoryPersistence memoryPersistence(){
        return new MemoryPersistence();
    }

    @Bean
    public DefaultMqttPahoClientFactory DefaultMqttPahoClientFactory(){
        DefaultMqttPahoClientFactory defaultMqttPahoClientFactory = new DefaultMqttPahoClientFactory();
        defaultMqttPahoClientFactory.setKeepAliveInterval(60);
        defaultMqttPahoClientFactory.setCleanSession(false);
        defaultMqttPahoClientFactory.setConnectionTimeout(30);
        defaultMqttPahoClientFactory.setPersistence(new MemoryPersistence());
        defaultMqttPahoClientFactory.setUserName("admin");
        defaultMqttPahoClientFactory.setPassword("admin");
        return defaultMqttPahoClientFactory;
    }

    @Bean
    public MqttPahoMessageHandler mqttPahoMessageHandler(){
        String url ="tcp://localhost:1883";
        String clientId="weirdo-cliendId";
        MqttPahoMessageHandler mqttPahoMessageHandler = new MqttPahoMessageHandler(url, clientId, new DefaultMqttPahoClientFactory()) ;
        mqttPahoMessageHandler.setAsync(false);
        mqttPahoMessageHandler.setDefaultQos(0);
        return mqttPahoMessageHandler;
    }
*/

}
