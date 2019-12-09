package boot.active.topic.comsumer;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Topic_Consumer {

	@JmsListener(destination="${mytopic}")
	public void receive(TextMessage textMessage) throws JMSException {
		System.out.println("消费者收到消息："+textMessage.getText());
	}
}
