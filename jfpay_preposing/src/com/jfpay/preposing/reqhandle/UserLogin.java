package com.jfpay.preposing.reqhandle;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.control.LoginInfoObject;
import com.jfpay.preposing.properties.DataDictInitialize;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.utils.JedisUtil.Objects;
import com.jfpay.preposing.utils.JedisUtil;
import com.jfpay.preposing.utils.MD5;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * 用户登录
 * 
 * @author 
 * 
 */
public class UserLogin extends ClientReqHandle implements IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_TPCALL);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		if (returnMessage instanceof HashMap) {
			resultMap = (HashMap) returnMessage;
			String transLogNo = map.get(ParseCoreReceiveXml.TRANSLOGNO);
			String phone = map.get(ParseCoreReceiveXml.PHONE);
			String mobileSerialNum = map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM);
			String appUser = map.get(ParseCoreReceiveXml.APP_USER);
			String version = map.get(ParseCoreReceiveXml.VERSION);
			if (resultMap.get("respCode").equals(SystemCode.SUCCESS.getCode())) {
				String newToken = this.getLoginToken(appUser, transLogNo, phone, mobileSerialNum,version);
				resultMap.put("token", newToken);
			} else {
				log.error(" 用户： " + appUser + phone + " 登录失败!");
			}
		}
		return resultMap;
	}

	@Override
	public LocalResp verifyMessage(HashMap<String, String> map) {
		// TODO Auto-generated method stub
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
		if("01".equals(map.get(ParseCoreReceiveXml.CLIENT_TYPE)) && "hlf".equals(map.get(ParseCoreReceiveXml.APP_USER))
				&& ("1.0.0".equals(map.get(ParseCoreReceiveXml.VERSION)) || "1.0.1".equals(map.get(ParseCoreReceiveXml.VERSION)))){
			localResp.setRespCode(SystemCode.SYSTEM_MAINTENANCE.getCode());
			localResp.setRespDesc(SystemCode.SYSTEM_MAINTENANCE.getDesc());
			return localResp;
		}else{
			if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null || map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
			}
			if (map.get(ParseCoreReceiveXml.LOGINPWD) == null || map.get(ParseCoreReceiveXml.LOGINPWD).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.LOGINPWD + ",");
			}
			if (i > 0) {
				log.error(map.get(ParseCoreReceiveXml.TRANSDATE) + map.get(ParseCoreReceiveXml.TRANSTIME) + map.get(ParseCoreReceiveXml.TRANSLOGNO) + "本次请求参数缺失："
						+ sb.substring(0, sb.length() - 1).toString());
				// 校验失败 缺少必填参数
				localResp.setRespCode(SystemCode.MISSING_ARGUMENT.getCode());
				localResp.setRespDesc(sb.substring(0, sb.length() - 1).toString());
				sb = null;
				return localResp;
			}
			sb = null;
			
			String version = map.get(ParseCoreReceiveXml.VERSION);
			if(version.equals("2.0.6") || version.equals("2.1.4") || version.equals("2.1.0") || version.equals("2.0.7")){
				if (!oldcheckLoginInfo(map)) {// 登录信息请求异常
					localResp.setRespCode(SystemCode.LOGIN_INFO_EXCEPTION.getCode());
					localResp.setRespDesc(SystemCode.LOGIN_INFO_EXCEPTION.getDesc());
					return localResp;
				}
			}
			
			localResp.setRespCode(SystemCode.SUCCESS.getCode());
			localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
			return localResp;
		}
	}

	/**
	 * 校验登录请求信息是否异常
	 * 
	 * @param map
	 * @return
	 */
	private boolean oldcheckLoginInfo(HashMap<String, String> map) {

		boolean checkFlag = true;
		String transLogNo = map.get(ParseCoreReceiveXml.TRANSLOGNO);
		String phone = map.get(ParseCoreReceiveXml.PHONE);
		String mobileSerialNum = map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM);
		String appUser = map.get(ParseCoreReceiveXml.APP_USER);
		if (DataDictInitialize.LOGIN_INFO_POOL.containsKey(appUser + phone)) {
			LoginInfoObject lio = (LoginInfoObject) DataDictInitialize.LOGIN_INFO_POOL.get(appUser + phone);
			//if ((mobileSerialNum.equals(lio.getMobileSerialNum())) && (Integer.valueOf(transLogNo) <= Integer.valueOf(lio.getLogNo()))) {
			if ((mobileSerialNum.equals(lio.getMobileSerialNum())) && (Integer.valueOf(transLogNo) < 0)) {
				log.error(" 用户： " + appUser + phone + " 登录请求报文异常,本次请求流水号" + transLogNo + "不应小于0" );
				checkFlag = false;
			}
		}
		return checkFlag;
	}
	
	/**
	 * 校验登录请求信息是否异常
	 * 
	 * @param map
	 * @return
	 */
	private boolean checkLoginInfo(HashMap<String, String> map) {

		boolean checkFlag = true;
		String transLogNo = map.get(ParseCoreReceiveXml.TRANSLOGNO);
		String phone = map.get(ParseCoreReceiveXml.PHONE);
		String mobileSerialNum = map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM);
		String appUser = map.get(ParseCoreReceiveXml.APP_USER);
		String token = map.get(ParseCoreReceiveXml.TOKEN);
		JedisUtil jedisUtil = JedisUtil.getInstance();
		JedisUtil.Objects objects = jedisUtil.new Objects();
		if(objects.exists(appUser+phone)){
			String localtoken = (String) objects.getObject(appUser+phone);
			if(!localtoken.equals(token)){
				log.error(" 用户： " + appUser + phone + " 已经在其他手机上登录");
				checkFlag = false;
			}
//			LoginInfoObject lio = (LoginInfoObject) objects.getObject(token);
//			//if ((mobileSerialNum.equals(lio.getMobileSerialNum())) && (Integer.valueOf(transLogNo) <= Integer.valueOf(lio.getLogNo()))) {
//			if ((!mobileSerialNum.equals(lio.getMobileSerialNum())) || (Integer.valueOf(transLogNo) < 0)) {
//				log.error(" 用户： " + appUser + phone + " 登录请求报文异常,本次请求流水号" + transLogNo + "不应小于0" );
//				objects.del(appUser+phone);
//				objects.del(token);
//				checkFlag = false;
//			}
		}
		return checkFlag;
	}

//	/**
//	 * 登录信息存内存并获取token值
//	 * 
//	 * @param transLogNo
//	 * @param phone
//	 * @param mobileSerialNum
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	private String getLoginToken(String appUser, String transLogNo, String phone, String mobileSerialNum) {
//		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
//		String loginTime = date.format(new Date());
//		String param = phone + mobileSerialNum + loginTime;
//		String newToken = MD5.md5(param).toUpperCase();
//		LoginInfoObject lios = new LoginInfoObject(newToken, mobileSerialNum, System.currentTimeMillis(), transLogNo);
//		DataDictInitialize.LOGIN_INFO_POOL.put(appUser + phone, lios);
//		return newToken;
//	}
	
	/**
	 * 登录信息存内存并获取token值
	 * 
	 * @param transLogNo
	 * @param phone
	 * @param mobileSerialNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getLoginToken(String appUser, String transLogNo, String phone, String mobileSerialNum, String version) {
		if(version.equals("2.0.6") || version.equals("2.0.7") || version.equals("2.1.0") || version.equals("2.1.4")){
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
			String loginTime = date.format(new Date());
			String param = phone + mobileSerialNum + loginTime;
			String newToken = MD5.md5(param).toUpperCase();
			LoginInfoObject lios = new LoginInfoObject(newToken, mobileSerialNum, System.currentTimeMillis(), transLogNo);
			log.info(">>>>>>>>>>>>>>存放token到登录池:----->getLoginToken:"+appUser+phone);
			DataDictInitialize.LOGIN_INFO_POOL.put(appUser + phone, lios);
			return newToken;
		}else{
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
			String loginTime = date.format(new Date());
			String param = phone + mobileSerialNum + loginTime;
			String newToken = MD5.md5(param).toUpperCase();
			LoginInfoObject lios = new LoginInfoObject(newToken, mobileSerialNum, System.currentTimeMillis(), transLogNo);
			JedisUtil jedisUtil = JedisUtil.getInstance();
			JedisUtil.Objects objects = jedisUtil.new Objects();
			if(objects.exists(appUser + phone)){
				String token = (String) objects.getObject(appUser + phone);
				objects.del(token);
			}
			objects.setObject(appUser + phone, newToken, JedisUtil.EXPIRE_MONTH);
			objects.setObject(newToken, lios, JedisUtil.EXPIRE_MONTH);
			return newToken;
		}
		
	}

}
