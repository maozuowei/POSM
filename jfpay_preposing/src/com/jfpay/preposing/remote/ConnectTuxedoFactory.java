package com.jfpay.preposing.remote;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;

import cn.hnae.tuxedojms.allocate.TxServiceHelper;

import com.jfpay.preposing.control.RequestPoolObject;
import com.jfpay.preposing.properties.DataDictInitialize;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;
import com.jfpay.preposing.xml.DynamicCreateCoreXmlForJFPay;

public class ConnectTuxedoFactory {

	static Logger log = Logger.getLogger(ConnectTuxedoFactory.class);

	public static HashMap<String, String> createConnectWay(HashMap<String, String> map) throws InterruptedException, TPReplyException, TPException {

		// 获取调用核心主机的方式(0：tpcall 1:queue 2:webservice)
		String callHostSwitch = TxServiceHelper.getTransSwitchDef(map.get(ParseCoreReceiveXml.APPLICATION), Boolean.valueOf(map.get("cacheFlag")));

		ConnectTuxedoWay connect = null;

		// tpcall调用
		if (callHostSwitch.equals("0")) {
			connect = new TpCallConnectTuxedo();
			map = (HashMap<String, String>) connect.connectTuxedo(map);
		} else {// 队列调用
			String rpoKey = map.get("rpoKey");
			map.remove("rpoKey");// 去除XML里面不需要的内容项
			connect = new QueueConnectTuxedo();
			// 发送数据
			connect.connectTuxedo(DynamicCreateCoreXmlForJFPay.dynamicCreateXml(map));
			boolean temp = true;
			while (temp) {
				if (DataDictInitialize.REQUEST_POOL != null) {
					RequestPoolObject returnRpo = (RequestPoolObject) DataDictInitialize.REQUEST_POOL.get(map.get("rpoKey"));
					if (returnRpo != null) {
						int status = returnRpo.getStatus();

						if (status == 1 || status == 2 || status == 3) {// 1、处理完毕；2、超时；3、出错；0、待处理；4、处理中
							map = returnRpo.getReturnMap();
							DataDictInitialize.REQUEST_POOL.remove(rpoKey);
							temp = false;
							log.info("请求处理成功");
						}
					}
				}
				TimeUnit.MILLISECONDS.sleep(500); // 睡500毫秒
			}
		}
		return map;
	}
}
