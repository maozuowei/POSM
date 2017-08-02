package com.jfpay.preposing.remote;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jfpay.preposing.properties.DataDictInitialize;

/**
 * 循环处理接收消息队列
 * @author linser
 *
 */
public class ExecGetQueueThread extends Thread {
	
	static Logger log = Logger.getLogger(ExecGetQueueThread.class);
	
	ConnectTuxedoWay connect = null;
	
	public void run() {
		log.info("开始初始化JNDI连接,循环接收消息队列");
		try {
			while(DataDictInitialize.isRun) {
				/* 进入接收消息队列过程 */
				if(connect==null){
					connect = new QueueConnectTuxedo();
				}
				connect.receiveTuxedo();
			TimeUnit.MILLISECONDS.sleep(10); // 处理完一次接收沉睡10毫秒
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}