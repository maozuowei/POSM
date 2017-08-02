package com.jfpay.preposing.reqhandle;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.control.LoginInfoObject;
import com.jfpay.preposing.properties.DataDictInitialize;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.JedisUtil;
import com.jfpay.preposing.utils.JedisUtil.Objects;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class UserLogout extends ClientReqHandle implements IClientReqHandleService{

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public LocalResp verifyMessage(HashMap<String, String> map) {
//		LocalResp localResp = new LocalResp();
//		if (!super.checkMD5(map)) {// SIGN校验失败
//			localResp.setRespCode(SystemCode.SIGN_ERROR.getCode());
//			localResp.setRespDesc(SystemCode.SIGN_ERROR.getDesc());
//			return localResp;
//		}
//		if (!super.checkReqTime(map)) {// 请求交易时间异常
//			localResp.setRespCode(SystemCode.REQ_EXCEPTION.getCode());
//			localResp.setRespDesc(SystemCode.REQ_EXCEPTION.getDesc());
//			return localResp;
//		}
//		super.checkMessageUnify(map);
//		String appUser = map.get(ParseCoreReceiveXml.APP_USER);
//		String phone = map.get(ParseCoreReceiveXml.PHONE);
//		System.out.println("用户当前连接池信息： " + DataDictInitialize.LOGIN_INFO_POOL.get(appUser + phone));
//		DataDictInitialize.LOGIN_INFO_POOL.remove(appUser + phone);
//		System.out.println("当前用户登出后的连接池信息： " + DataDictInitialize.LOGIN_INFO_POOL.get(appUser + phone));
//		localResp.setRespCode(SystemCode.SUCCESS_LOGOUT.getCode());
//		localResp.setRespDesc(SystemCode.SUCCESS_LOGOUT.getDesc());
//		return localResp;
//	}
	
	@Override
	public LocalResp verifyMessage(HashMap<String, String> map) {
		String version = map.get(ParseCoreReceiveXml.VERSION);
		if(version.equals("2.0.6") || version.equals("2.0.7") || version.equals("2.1.0") || version.equals("2.1.4")){
			LocalResp localResp = new LocalResp();
			if (!super.checkMD5(map)) {// SIGN校验失败
				localResp.setRespCode(SystemCode.SIGN_ERROR.getCode());
				localResp.setRespDesc(SystemCode.SIGN_ERROR.getDesc());
				return localResp;
			}
			if (!super.checkReqTime(map)) {// 请求交易时间异常
				localResp.setRespCode(SystemCode.REQ_EXCEPTION.getCode());
				localResp.setRespDesc(SystemCode.REQ_EXCEPTION.getDesc());
				return localResp;
			}
			super.checkMessageUnify(map);
			String appUser = map.get(ParseCoreReceiveXml.APP_USER);
			String phone = map.get(ParseCoreReceiveXml.PHONE);
			System.out.println("用户当前连接池信息： " + DataDictInitialize.LOGIN_INFO_POOL.get(appUser + phone));
			DataDictInitialize.LOGIN_INFO_POOL.remove(appUser + phone);
			System.out.println("当前用户登出后的连接池信息： " + DataDictInitialize.LOGIN_INFO_POOL.get(appUser + phone));
			localResp.setRespCode(SystemCode.SUCCESS_LOGOUT.getCode());
			localResp.setRespDesc(SystemCode.SUCCESS_LOGOUT.getDesc());
			return localResp;
		}else{
			LocalResp localResp = new LocalResp();
			if (!super.checkMD5(map)) {// SIGN校验失败
				localResp.setRespCode(SystemCode.SIGN_ERROR.getCode());
				localResp.setRespDesc(SystemCode.SIGN_ERROR.getDesc());
				return localResp;
			}
			if (!super.checkReqTime(map)) {// 请求交易时间异常
				localResp.setRespCode(SystemCode.REQ_EXCEPTION.getCode());
				localResp.setRespDesc(SystemCode.REQ_EXCEPTION.getDesc());
				return localResp;
			}
			super.checkMessageUnify(map);
			String appUser = map.get(ParseCoreReceiveXml.APP_USER);
			String phone = map.get(ParseCoreReceiveXml.PHONE);
			JedisUtil jedisUtil = JedisUtil.getInstance();
			JedisUtil.Objects objects = jedisUtil.new Objects();
			LoginInfoObject loginInfoObject = null;
			if(objects.exists(appUser + phone)){
				String token = (String) objects.getObject(appUser + phone);
				loginInfoObject = (LoginInfoObject) objects.getObject(token);
				System.out.println("用户当前连接池信息： " + loginInfoObject);
				objects.del(appUser + phone);
				objects.del(token);
			}
			System.out.println("当前用户登出后的连接池信息： " + loginInfoObject);
			localResp.setRespCode(SystemCode.SUCCESS_LOGOUT.getCode());
			localResp.setRespDesc(SystemCode.SUCCESS_LOGOUT.getDesc());
			return localResp;
		}
	}

}
