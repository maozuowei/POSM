package com.jfpay.preposing.jms;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import cn.hnae.tuxedojms.allocate.TuxedoConst;

import com.jfpay.preposing.properties.DataDictInitialize;

public class CacheSyn extends Thread implements MessageListener {

	private String topicConnFactory = "JmsFactory";

	private String topicName = "LogTopic";

	TopicConnection connection = null;

	TopicSession session = null;

	TopicSubscriber subscriber = null;

	static Logger log = Logger.getLogger(CacheSyn.class);

	public void run() {

		try {
			log.info("开始启动JMS TOPIC消费者程序!" + DataDictInitialize.isRun);
			Context context = new InitialContext();

			TopicConnectionFactory factory = (TopicConnectionFactory) context.lookup(topicConnFactory);
			Topic topic = (Topic) context.lookup(topicName);

			context.close();

			connection = factory.createTopicConnection();

			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			subscriber = session.createSubscriber(topic);
			subscriber.setMessageListener(this);
			connection.start();
			log.info("JMS TOPIC消费者程序启动完成!" + DataDictInitialize.isRun);
			while (DataDictInitialize.isRun) {
				TimeUnit.SECONDS.sleep(10L);
			}
			log.info("JMS TOPIC消费者程序退出中....!");
			connection.close();
			subscriber.close();
			log.info("JMS TOPIC消费者程序退出完成!");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("关闭TOPIC消费者失败!" + e.getMessage());
		}
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		String msg = null;
		try {
			if (message instanceof TextMessage) {
				msg = ((TextMessage) message).getText();
				if (msg.equals("synchronize")) {
					log.info("接收到TOPIC消息，开始进行缓存更新操作！");
					TuxedoConst.reloadCache();
				} else
					log.info("MESSAGE COMMAND ERROR: Message received: " + msg);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (!DataDictInitialize.isRun)
			DataDictInitialize.isExit = true;
		log.info("正在关闭TOPIC消费者");
		try {
			connection.close();
			subscriber.close();
			TimeUnit.SECONDS.sleep(3L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("关闭TOPIC消费者失败!" + e.getMessage());
		}
	}

}
