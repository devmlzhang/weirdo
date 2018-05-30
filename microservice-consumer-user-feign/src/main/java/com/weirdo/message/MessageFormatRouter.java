package com.weirdo.message;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @Author ML.Zhang
 * @Date 2018/5/29
 */


@Service("alarmMsgJMSProcess")
public class MessageFormatRouter  {
    /**
     * 使用Camel接收主题、队列消息
     */

    private static Logger logger = Logger.getLogger(MessageFormatRouter.class);
    public boolean onMessage(Exchange exchange) {
        String msg = decodeMessage(exchange);
        logger.info(msg);
        System.out.println("Camel收到消息："+msg);
        return true;
    }



    /**
     * 重新编码
     * @param exchange
     * @return
     */
    private String decodeMessage(Exchange exchange) {
        if (exchange == null) {
            System.out.println("exchange is null");
        }
        String mstext = null;
        Object message = exchange.getIn().getBody();
        if (message instanceof String) {
            mstext = message.toString();
        } else if (message instanceof byte[]) {
            try {
                mstext = new String((byte[]) message, "utf-8");

            } catch (UnsupportedEncodingException e) {
                logger.info("二进制流转消息失败");
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
        return mstext;
    }
}
