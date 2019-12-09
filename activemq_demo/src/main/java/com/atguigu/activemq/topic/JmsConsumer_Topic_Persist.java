package com.atguigu.activemq.topic;

import static org.hamcrest.CoreMatchers.both;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 持久化主题消费者案例
 * @author Administrator
 *
 */
public class JmsConsumer_Topic_Persist {

	public static final String ACTIVEMQ_URL="tcp://192.168.227.130:61616";
	public static final String TOPIC_NAME="Topic-Persist";

	public static void main(String[] args) throws JMSException, IOException {
		System.out.println("我是z4");
		//1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
		ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		//2.通过连接工厂，获得连接connection,获得连接connection并启动访问
		Connection connection=activeMQConnectionFactory.createConnection();
		connection.setClientID("z4");
		//创建会话session,两个参数，第一个叫事务，第二个叫签收，事务的权限大过签收
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//创建目的地，（具体是队列还是主题）
		Topic topic=session.createTopic(TOPIC_NAME);
		TopicSubscriber topicSubscriber=session.createDurableSubscriber(topic,"remark...");
		connection.start();//启动
		Message message= topicSubscriber.receive();
		while(null != message) {
			TextMessage textMessage=(TextMessage)message;
			System.out.println("***收到的持久化topic："+textMessage.getText());
		    message=topicSubscriber.receive(3000);
		}
		session.close();
		connection.close();

	}
}
