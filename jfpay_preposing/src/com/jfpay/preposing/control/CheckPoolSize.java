package com.jfpay.preposing.control;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jfpay.preposing.properties.DataDictInitialize;


public class CheckPoolSize extends Thread {

	static Logger log = Logger.getLogger(CheckPoolSize.class);
	
	public void run() {
		try {
			while (DataDictInitialize.isRun) {
				if (DataDictInitialize.REQUEST_POOL != null) {
					//System.out.println("请求池大小："+DataDict.REQUEST_POOL.size());	
					log.info("交易请求池大小："+DataDictInitialize.REQUEST_POOL.size());
				}
				if(DataDictInitialize.LOGIN_INFO_POOL!= null) {
					log.info("登录信息池大小："+DataDictInitialize.LOGIN_INFO_POOL.size());
				}
				TimeUnit.SECONDS.sleep(30L); //睡60秒钟
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
