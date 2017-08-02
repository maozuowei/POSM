package com.jfpay.preposing.reqhandle;

import java.util.HashMap;

import cn.hnae.tuxedojms.allocate.TuxedoConst;
import cn.hnae.tuxedojms.allocate.TxServiceHelper;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;

public class ClientReqHandleProxy implements IClientReqHandleService {

	private IClientReqHandleService reqHandle = null;
	
	public ClientReqHandleProxy(HashMap<String, String> map){
		Class<?> clientHandle=null;
		String application = map.get(ParseCoreReceiveXml.APPLICATION);
		try {
			clientHandle=Class.forName(TxServiceHelper.getReqKey(application, TuxedoConst.cacheFlag));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.reqHandle = (IClientReqHandleService)clientHandle.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.reqHandle.getCommuInfo(map);
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map,Object returnMessage) {
		// TODO Auto-generated method stub
		return this.reqHandle.handleReturnMessage(map,returnMessage);
	}

	@Override
	public LocalResp verifyMessage(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		
		return this.reqHandle.verifyMessage(map);
	}

}
