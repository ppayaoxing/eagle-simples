package com.seezoon.eagle.amq.demo.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.seezoon.eagle.amq.sender.AbstractSender;

/**

 * Topic发送

 * @author DF

 *

 */
@Component
public class TopicSender extends AbstractSender{
	@Autowired
	@Qualifier("jmsTopicTemplate")
	private JmsTemplate jmsTemplate;

	@Override
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

}