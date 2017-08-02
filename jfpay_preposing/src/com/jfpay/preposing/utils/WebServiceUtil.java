package com.jfpay.preposing.utils;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import cn.hnae.ws.client.Proxy;
import cn.hnae.ws.client.ProxyServiceLocator;

public class WebServiceUtil {

	static Logger log = Logger.getLogger(WebServiceUtil.class);
	/**
	 * webservice通讯
	 * @param url
	 * @param reqMsg
	 * @return
	 * @throws RemoteException 
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws ServiceException 
	 */
	public static String invokeWebService(String url,String reqMsg) throws RemoteException, ServiceException{
		
		ProxyServiceLocator w=new ProxyServiceLocator(url);
		String resultJson = "";
		Proxy s;
		try {
			s = w.getProxyPort();		
			resultJson = s.service(reqMsg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			throw e;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			throw e;
		}finally{
			w = null;
		}
		return resultJson;
	}
}
