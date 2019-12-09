package com.atguigu.activemq.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/**
 * spring整合mq生产者案例
 * @author Administrator
 *
 */
@Service
public class SpringMQ_Produce {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public static void main(String[] args) {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");//读取配置文件
		SpringMQ_Produce produce=(SpringMQ_Produce)ctx.getBean("springMQ_Produce");//获取SpringMQ_Produce对象
		//匿名内部类的方式
		/*produce.jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage=session.createTextMessage("****spring整合activeMQ案例");
				return textMessage;
			}
		});*/
		//lanble表达式方式
		produce.jmsTemplate.send((session) -> {
			
			TextMessage textMessage=session.createTextMessage("****spring整合activemq案例");
			return textMessage;
		});
		System.out.println("*******send task over");
	}
}
