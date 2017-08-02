package com.jfpay.preposing.control;

import java.util.Iterator;
import org.apache.log4j.Logger;

import com.jfpay.preposing.properties.DataDictInitialize;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RequestTimeOutControl extends Thread {

	static Logger log = Logger.getLogger(RequestTimeOutControl.class);
	
	public void run(){
		try{
			while(DataDictInitialize.isRun){
				if(DataDictInitialize.REQUEST_POOL !=null){
					
					Iterator it = DataDictInitialize.REQUEST_POOL.entrySet().iterator();
					while(it.hasNext()){
						ConcurrentHashMap.Entry map = (ConcurrentHashMap.Entry) it.next();
						RequestPoolObject rpo = (RequestPoolObject)map.getValue();
						//请求在请求池中停留的时间超时，则移除此请求
						if((System.currentTimeMillis()-rpo.getCreateTime())>DataDictInitialize.RESPONSE_TIMEOUT){
							log.info("日期+时间+流水号："+map.getKey()+"的交易已超时，将被移出请求池！");
							DataDictInitialize.REQUEST_POOL.remove(map.getKey().toString());
						}
					}					
				}
				TimeUnit.SECONDS.sleep(1L); //睡1秒钟
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		if(!DataDictInitialize.isRun)
			DataDictInitialize.isExit = true;
		log.info("清理系统资源:request_pool");
		if (DataDictInitialize.REQUEST_POOL != null)
			DataDictInitialize.REQUEST_POOL = null;
	}
}
