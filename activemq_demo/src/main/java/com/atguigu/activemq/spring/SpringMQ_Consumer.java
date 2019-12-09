package com.atguigu.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * spring整合mq消费者
 * @author Administrator
 *
 */
@Service
public class SpringMQ_Consumer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public static void main(String[] args) {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");//读取配置文件
	    SpringMQ_Consumer consumer=(SpringMQ_Consumer)ctx.getBean("springMQ_Consumer");//创建springMQ_Consumer对象
	    String  retValue=(String)consumer.jmsTemplate.receiveAndConvert();
	    System.out.println("***消费者收到的消息："+retValue);
	
	}

}
