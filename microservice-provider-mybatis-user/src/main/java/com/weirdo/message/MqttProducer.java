package com.weirdo.message;

import com.alibaba.fastjson.JSONObject;
import com.weirdo.model.UserDomain;
import com.weirdo.service.MqttClientServiceImpl;
import com.weirdo.util.WebsocketModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * @Author ML.Zhang
 * @Date 2018/5/29
 */

@Component//将Producer注入到容器
@EnableScheduling//定时任务的注解
public class MqttProducer {
    /**
     * 定时发送主题消息
     */

    private String DA_TOPIC = "Topic/Weirdo/DaChange";
    @Autowired
    MqttClientServiceImpl mqttClientService;

    private int age = 18;
    @Scheduled(fixedDelay = 5000)//每隔5秒钟执行这个方法
    public void send() {
        age++;
        UserDomain userEntity = new UserDomain(age, UUID.randomUUID().toString(), Integer.toString(age),"");
        WebsocketModel  websocketModel = new WebsocketModel();
        websocketModel.setData(userEntity);
        websocketModel.setType("message");
        websocketModel.setOperation("add");
        String s = new JSONObject().toJSONString(websocketModel);
        System.out.println("MqttProducer:" + s);
        mqttClientService.sendMessage(DA_TOPIC,s);

    }

}
