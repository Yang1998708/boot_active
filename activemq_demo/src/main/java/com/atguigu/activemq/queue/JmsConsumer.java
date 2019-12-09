package com.atguigu.activemq.queue;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.security.spec.ECPrivateKeySpec;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsConsumer {

	public static final String ACTIVEMQ_URL="nio://192.168.227.130:61618";
	public static final String QUEUE_NAME="queue01";

	public static void main(String[] args) throws JMSException, IOException {
		//1.创建连接工厂,按照给定的url地址，采用默认用户名和密码
		ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
		//2.通过连接工厂，获得连接connection,获得连接connection并启动访问
		Connection connection=activeMQConnectionFactory.createConnection();
		connection.start();
		//创建会话session,两个参数，第一个叫事务，第二个叫签收，事务的权限大过签收
		Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//创建目的地，（具体是队列还是主题）
		Queue queue=session.createQueue(QUEUE_NAME);
		//创建消费者
		MessageConsumer messageConsumer=session.createConsumer(queue);
		/*while (true) {
			//receive没参数是一直等待，给参数是等待时间,发送的消息类型要对应，生产者发送的是textMessage类型，消费者就要用这个类型接
			TextMessage textMessage=(TextMessage) messageConsumer.receive(4000);
			if (null != textMessage) {
             System.out.println("****消费者接到消息"+textMessage.getText());
			}else {
				break;
			}
		}
		//关闭资源
		messageConsumer.close();
		session.close();
		connection.close();*/
		
		//通过监听的方式来消费消息,匿名内部类的方式
		messageConsumer.setMessageListener(new MessageListener() {
			
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
		});
		//使用监听器要用这段代码给一点时间给监听器执行
		System.in.read();
		//关闭资源
		messageConsumer.close();
		session.close();
		connection.close();
		
		/**
		 * 队列的方式是一个消费者消费了那另一个消费者就不能消费了，如果队列是持久化的的话，生产了消息机器宕机后再连接还是能收到消息，但是是非持久化的话，宕机后就收不到那个消息了
		 * 队列的默认持久化设置是默认持久化
		 * 主题的方式是要先启动消费者，然后再生产消息，如果先生产消息的话，再启动消费者，那消费者会消费不到消息，是废消息，如果消费者已经注册在队列里后
		 * 然后这个消费者掉线了，如果是持久化的主题，那这个消费者上线后会收到所有掉线时的生产者发送的消息，如果是非持久化的主题的话，消费者掉线了的话，重新登录
		 * 上也是收不到掉线时发送的消息的
		 * 队列里：事务偏生产者，生产者事务选项设置为true后要commit才能将生产的消息发送至mq，为false时不需要commit，消费者设置为true时
		 * 要commit才能消费成功，不然会造成重复消费现象，为false则不需要commit，事务的权利大于签收，事务为true是签收作用不大
		 * 队列里：签收偏消费者,签收有三种模式为自动签收，手动签收，允许重复消息这三种模式，消费者事务不开启时，自动签收会自己签收消息并在mq上
		 * 显示该消息已消费，手动签收的话要调用acknowledge方法进行手动签收，不然也会造成重复消费，下面是事务开启时也commit的签收,事务开启时手动签收但不调用acknowledge
		 * 签收方法的的情况如下：因为事务开启了也commit了，会显示消费成功，不会重复消费，就算签收的ack方法没调用，说明事务的权限大于签收的，然后
		 * 事务开启，签收也开启，签收的ack签收方法调用，但事务的commit方法没调用，结果是会造成重复消费的情况，又说明事务的优先级是大于签收的
		 * 在spring中，主题可以实现消费者不启动，直接通过配置监听完成
		 * 
		 * 
		 * 
		 */
	}
}
