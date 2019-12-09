package com.atguigu.boot.activemq.produce;

import java.util.UUID;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Queue_Produce {

	@Autowired
    private	JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired
	private Queue queue;
	
	public void produceMsg() {
		jmsMessagingTemplate.convertAndSend(queue,UUID.randomUUID().toString().substring(0,6));
	}
	
	//间隔一段时间定时投递消息到mq
	@Scheduled(fixedDelay=3000)//定时投放注解
	public void produceMsgScheduled() {
		jmsMessagingTemplate.convertAndSend(queue,UUID.randomUUID().toString().substring(0,6));
		System.out.println("定投成功");
	}
}
