package com.weirdo;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * @Author ML.Zhang
 * @Date 2018/5/29
 */
public class TopicProducer {

    public static void main(String[] args) {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        try {
            //创建连接
            Connection connection = connectionFactory.createConnection();
            //开启连接
            connection.start();
            //创建一个回话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建一个Destination，queue或者Topic
            Topic topic = session.createTopic("Topic.Weirdo.DaChange");
            //创建一个生成者
            MessageProducer producer = session.createProducer(topic);
            //创建一个消息
            TextMessage textMessage = new ActiveMQTextMessage();
            textMessage.setText("hello my topic");
            //发送消息
            producer.send(textMessage);
            //关闭
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
