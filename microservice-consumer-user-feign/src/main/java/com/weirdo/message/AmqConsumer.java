package com.weirdo.message;

import com.alibaba.fastjson.JSONObject;
import com.weirdo.model.UserDomain;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author ML.Zhang
 * @Date 2018/5/28
 */
@Component
public class AmqConsumer {
    // @JmsListener(destination = "${queue}")//activeMq监听监听接收消息队列
    public void receive(String msg){//这个msg就是从消息队列获得到的参数
        System.out.println(msg);
        JSONObject jsonObject = new JSONObject();
        UserDomain userEntity = jsonObject.parseObject(msg,UserDomain.class);
        System.out.println(userEntity.toString()+"消费方");
    }
}