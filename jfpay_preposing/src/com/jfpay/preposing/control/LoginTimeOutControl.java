package com.jfpay.preposing.control;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jfpay.preposing.properties.DataDictInitialize;

public class LoginTimeOutControl extends Thread {

	static Logger log = Logger.getLogger(LoginTimeOutControl.class);

	public void run() {

		try {
			while (DataDictInitialize.isRun) {
				if (DataDictInitialize.LOGIN_INFO_POOL != null) {

					Iterator it = DataDictInitialize.LOGIN_INFO_POOL.entrySet()
							.iterator();
					while (it.hasNext()) {
						ConcurrentHashMap.Entry map = (ConcurrentHashMap.Entry) it
								.next();
						LoginInfoObject lio = (LoginInfoObject) map.getValue();
						if ((System.currentTimeMillis() - lio.getLoginTime()) > DataDictInitialize.LOGIN_TIMEOUT) {
							log.info("用户:" + map.getKey() + "的登录已超时，将被移出请求池！");
							DataDictInitialize.LOGIN_INFO_POOL.remove(
							  map.getKey().toString());
						}
					}
				}
				TimeUnit.SECONDS.sleep(1L); // 睡1秒钟
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void close(){
		if(!DataDictInitialize.isRun)
			DataDictInitialize.isExit = true;
		log.info("清理系统资源:login_info_pool");
		if (DataDictInitialize.LOGIN_INFO_POOL != null) 
			DataDictInitialize.LOGIN_INFO_POOL = null;
	}
}
