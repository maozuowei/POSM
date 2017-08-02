package com.jfpay.preposing.remote;

import java.util.HashMap;

import org.apache.log4j.Logger;

import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;

import com.jfpay.preposing.control.RequestPoolObject;
import com.jfpay.preposing.properties.DataDictInitialize;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class QueueConnectTuxedo extends ConnectTuxedoWay {

	static Logger log = Logger.getLogger(QueueConnectTuxedo.class);

	@Override
	public Object connectTuxedo(Object param) throws TPReplyException, TPException {
		// TODO Auto-generated method stub
			// 发送队列
		TuxedoJndi.getInstance("Queue").test.callQueue((String) param);
		return null;
	}

	@Override
	public void receiveTuxedo() {
		// TODO Auto-generated method stub
		try {
			String result = TuxedoJndi.getInstance("Queue").test.getQueue();
			if (result != null) {
				log.info("接收到的消息队列:" + result);
				HashMap<String, String> map = ParseCoreReceiveXml.parseCoreXml(result);
				// 实时交易报文
				if (map != null && map.get("transDate") != null
						&& map.get("transTime") != null
						&& map.get("transLogNo") != null) {
					String datetime = map.get("transDate").toString()
							+ map.get("transTime").toString()
							+ map.get("transLogNo").toString();
					RequestPoolObject rpo = (RequestPoolObject) DataDictInitialize.REQUEST_POOL
							.get(datetime);
					if (rpo != null && rpo.getStatus() != 2) {// 未超时且请求对象未从请求池移出的情况
						rpo.setReturnXml(result);
						rpo.setStatus(1);// 处理结束
						DataDictInitialize.REQUEST_POOL.put(datetime, rpo); // 结果集覆盖回请求池
						log.info("接收消息队列完毕");
					} else {
						log.info(datetime+"原交易已超时不存在");
					}
				}
			}
		} catch (TPReplyException tre) {
			tre.printStackTrace();
			log.warn(tre.getMessage());
		} catch (TPException te) {
			te.printStackTrace();
			log.warn(te.getMessage());
		} catch (Exception ee) {
			ee.printStackTrace();
			log.warn(ee.getMessage());
		}

	}

}
