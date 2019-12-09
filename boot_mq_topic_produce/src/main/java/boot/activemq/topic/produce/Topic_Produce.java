package boot.activemq.topic.produce;

import java.util.UUID;

import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Topic_Produce {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired
	private Topic topic;

	@Scheduled(fixedDelay=3000)//定时投递
   public void produceTopic() {
	  jmsMessagingTemplate.convertAndSend(topic,"主题消息:"+UUID.randomUUID().toString().substring(0,6));
   }
}
