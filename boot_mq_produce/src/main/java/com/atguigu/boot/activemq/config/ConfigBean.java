package com.atguigu.boot.activemq.config;

import javax.jms.Queue;
import javax.validation.Valid;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component
@EnableJms//开启jms功能注解
public class ConfigBean {

	@Value("${myqueue}")
	private String myQueue;
	
	@Bean
   public Queue queue() {
	   return new ActiveMQQueue(myQueue);
   }
}
