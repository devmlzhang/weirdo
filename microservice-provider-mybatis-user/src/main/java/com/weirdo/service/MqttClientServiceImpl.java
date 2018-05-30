package com.weirdo.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
/**
 * @Author ML.Zhang
 * @Date 2018/5/29
 */


@Service
public class MqttClientServiceImpl {
	private static Logger log = Logger.getLogger(MqttClientServiceImpl.class);

	private static String TOPIC_PRIX = "topic/";// 斜杠变点
	
	@Autowired
	MqttPahoMessageHandler handler;

	public void sendMessage(String type, String dname, String data) {
		String topicName = getTopicName(type, dname);
		this.sendMessage(topicName, data);
	}
	
	public void sendMessage(String topicName, String data) {
		if (StringUtils.isEmpty(topicName) || StringUtils.isEmpty(data)) {
			return;
		}
		try {
			Message<String> message = MessageBuilder.withPayload(data).setHeader(MqttHeaders.TOPIC, topicName).build();
			handler.handleMessage(message);
		} catch (Exception e) {
			log.error(getExceptionMessage(e));
		}
	}
	/**
	 * 根据类型、名称获取对应的topic名称
	 * 
	 * @param type
	 * @param name
	 * @return
	 */
	private String getTopicName(String type, String name) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name)) {
			return null;
		}
		String topicName = TOPIC_PRIX + type + "/" + name;
		return topicName;
	}

	
	public String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
}
