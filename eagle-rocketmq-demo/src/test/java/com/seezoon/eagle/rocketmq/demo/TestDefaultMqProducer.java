package com.seezoon.eagle.rocketmq.demo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.seezoon.eagle.core.test.BaseJunitTest;
import com.seezoon.eagle.core.utils.NDCUtils;

public class TestDefaultMqProducer extends BaseJunitTest{

	@Autowired
	private DefaultMQProducer defaultMQProducer;
	/**
	 * 同步发确保直接返回回执
	 * @throws 
	 */
	@Test
	public void send() {
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		msg.putUserProperty("threadId", NDCUtils.peek());
		try {
			SendResult send = defaultMQProducer.send(msg);
			logger.debug("DefaultMQProducer send:{}",JSON.toJSONString(send));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 不关心发送结果
	 */
	@Test
	public void  sendOneway(){
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		try {
			defaultMQProducer.sendOneway(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	/**
	 * 异步发送，关心发送结果
	 */
	@Test
	public void  sendCallback(){
		Message msg = new Message("TopicTest", "hello rocketmq".getBytes()) ;
		try {
			defaultMQProducer.send(msg,new SendCallback() {
				
				@Override
				public void onSuccess(SendResult sendResult) {
					logger.debug("sendCallback onSuccess :{}",JSON.toJSONString(sendResult));
				}
				
				@Override
				public void onException(Throwable e) {
					logger.error(e.getMessage());
				}
			});
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 有序消息 接收需要用MessageListenerOrderly  保证发入一个queue 就可以 利用MessageQueueSelector
	 */
	@Test
	public void sendOrdered(){
		Message msg = new Message("TopicTest", "hello rocketmq1".getBytes()) ;
		MessageQueueSelector messageQueueSelector = new DemoMessageQueueSelector();
		try {
			defaultMQProducer.send(msg, messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq2".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq3".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq4".getBytes()) , messageQueueSelector, 1);
			defaultMQProducer.send(new Message("TopicTest", "hello rocketmq5".getBytes()) , messageQueueSelector, 1);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 有序消息 接收需要用MessageListenerOrderly  保证发入一个queue 就可以 利用MessageQueueSelector
	 */
	@Test
	public void sendTagMsg(){
		Message msg = new Message("TopicTest", "hello rocketmq tag".getBytes()) ;
		msg.setTags("mytag");
		try {
			defaultMQProducer.sendOneway(msg);
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
