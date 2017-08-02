package com.jfpay.preposing.remote;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import cn.hnae.tuxedojms.ejb.TuxedoRemote;

import com.jfpay.preposing.properties.DataDictInitialize;

public class TuxedoJndi {

	static Logger log = Logger.getLogger(TuxedoJndi.class);
	private static TuxedoJndi jndi = null;
	public static TuxedoRemote test = null;
	
	private TuxedoJndi(String flag){
		try {
			Properties props = new Properties();
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			props.setProperty(Context.PROVIDER_URL,
					DataDictInitialize.CONTEXT_URL);
			InitialContext ctx = new InitialContext(props);
			test = (TuxedoRemote) ctx
					.lookup("tuxedo#cn.hnae.tuxedojms.ejb.TuxedoRemote");
			if(flag.equals("1"))//如果是队列方式调用
				//初始化设置队列相关参数
				test.initialParameter(DataDictInitialize.NAME_SPACE, DataDictInitialize.SEND_QUEUE_NAME, DataDictInitialize.RECEIVE_QUEUE_NAME, DataDictInitialize.CORR_ID);
			// 初始化设置队列相关参数
			log.debug("初始化jndi完成");
		} catch (Exception e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static TuxedoJndi getInstance(String flag){
		if(jndi == null){
			jndi = new TuxedoJndi(flag);
		}
		return jndi;
	}
}
