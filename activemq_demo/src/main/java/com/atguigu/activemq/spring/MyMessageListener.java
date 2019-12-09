package com.atguigu.activemq.spring;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

/**
 * 监听器类
 * @author Administrator
 *
 */
@Component
public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		if (null!=message && message instanceof TextMessage) {
              TextMessage textMessage=(TextMessage)message;
              try {
				System.out.println(textMessage.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}

}
