package com.atguigu.activemq.topic;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.console.command.store.tar.TarOutputStream;

public class JmsConsumer_Topic {

	public static final String ACTIVEMQ_URL="tcp://192.168.227.130:61616";
	public static final String TOPIC_NAME="topic-atguigu";

	public static void main(String[] args) throws JMSException, IOException {
		System.out.println("我是2号消费者");
		//1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
		ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		//2.通过连接工厂，获得连接connection,获得连接connection并启动访问
		Connection connection=activeMQConnectionFactory.createConnection();
		connection.start();
		//创建会话session,两个参数，第一个叫事务，第二个叫签收，事务的权限大过签收
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//创建目的地，（具体是队列还是主题）
		Topic topic=session.createTopic(TOPIC_NAME);
		//创建消费者
		
				MessageConsumer messageConsumer=session.createConsumer(topic);
		//通过监听的方式来消费消息,匿名内部类的方式
		/*messageConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				if (null !=message && message instanceof TextMessage) {
					TextMessage textMessage =(TextMessage)message;
					try {
						System.out.println("****消费者接到消息"+textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
				
			}
		});*/
			
			messageConsumer.setMessageListener((message) -> {
				if (null !=message && message instanceof TextMessage) {
					TextMessage textMessage =(TextMessage)message;
					try {
						System.out.println("****消费者接到topic消息"+textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
				
			});	
		//使用监听器要用这段代码给一点时间给监听器执行
		System.in.read();
		//关闭资源
		messageConsumer.close();
		session.close();
		connection.close();
	
	}
}
