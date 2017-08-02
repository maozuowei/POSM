package com.jfpay.preposing.reqhandle.service;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;

public interface IClientReqHandleService {
	/**
	 * 获取通讯信息
	 * @param reqMap
	 * @return
	 */
	public CommunicationInfo getCommuInfo(HashMap<String, String> map);
	/**
	 * 获取返回报文
	 * @param returnMessage
	 * @return
	 */
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map,Object returnMessage);
	/**
	 * 报文数据校验
	 * @param map
	 * @return
	 */
	public LocalResp verifyMessage(HashMap<String, String> map);
}
