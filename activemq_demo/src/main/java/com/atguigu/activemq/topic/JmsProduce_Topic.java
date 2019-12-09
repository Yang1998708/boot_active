package com.atguigu.activemq.topic;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 主题代码
 * @author Administrator
 *
 */
public class JmsProduce_Topic {

	public static final String ACTIVEMQ_URL="tcp://192.168.227.130:61616";
	public static final String TOPIC_NAME="topic-atguigu";
	public static void main(String[] args) throws JMSException {
		//1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
		ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		//2.通过连接工厂，获得连接connection,获得连接connection并启动访问
		Connection connection=activeMQConnectionFactory.createConnection();
		connection.start();
		//创建会话session,两个参数，第一个叫事务，第二个叫签收，事务的权限大过签收
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//创建目的地，（具体是队列还是主题）
		Topic topic=session.createTopic(TOPIC_NAME);
		//创建消息生产者
		MessageProducer messageProducer=session.createProducer(topic);
		//通过使用messageProducer生产3条消息发送到mq队列里
		for (int i = 1; i <=3; i++) {
			//创建消息
			TextMessage textMessage=session.createTextMessage("msg---"+i);
			//通过messageProducer发送给mq
			messageProducer.send(textMessage);
		}
		//关闭资源，顺序开，倒序关
		messageProducer.close();
		session.close();
		connection.close();
		System.out.println("TOPIC_NAME消息发送到mq完成");
	}
}
