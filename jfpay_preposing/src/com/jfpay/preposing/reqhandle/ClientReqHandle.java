package com.jfpay.preposing.reqhandle;

import java.util.HashMap;
import org.apache.log4j.Logger;
import cn.hnae.tuxedojms.allocate.TuxedoConst;
import cn.hnae.tuxedojms.allocate.TxServiceHelper;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.control.LoginInfoObject;
import com.jfpay.preposing.properties.DataDictInitialize;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.Format;
import com.jfpay.preposing.utils.HttpUtil;
import com.jfpay.preposing.utils.JedisUtil;
import com.jfpay.preposing.utils.MD5;
import com.jfpay.preposing.utils.JedisUtil.Objects;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public abstract class ClientReqHandle {

	protected int i = 0;

	protected StringBuilder sb = new StringBuilder();

	protected HashMap<String, String> resultMap = new HashMap<String, String>();

	protected static Logger log = Logger.getLogger(ClientReqHandle.class);

	/**
	 * 校验公共参数
	 * 
	 * @param reqMap
	 * @return
	 */
	protected void checkMessageUnify(HashMap<String, String> map) {

		sb.append("以下参数不能为空:");
		// **************请求公共参数部分校验--start******************//
		if (map.get(ParseCoreReceiveXml.SIGN) == null || map.get(ParseCoreReceiveXml.SIGN).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.SIGN + ",");
		}
		if (map.get(ParseCoreReceiveXml.CLIENT_TYPE) == null || map.get(ParseCoreReceiveXml.CLIENT_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CLIENT_TYPE + ",");
		}
		if (map.get(ParseCoreReceiveXml.APP_USER) == null || map.get(ParseCoreReceiveXml.APP_USER).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.APP_USER + ",");
		}
		if (map.get(ParseCoreReceiveXml.TRANSDATE) == null || map.get(ParseCoreReceiveXml.TRANSDATE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TRANSDATE + ",");
		}
		if (map.get(ParseCoreReceiveXml.TRANSTIME) == null || map.get(ParseCoreReceiveXml.TRANSTIME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TRANSTIME + ",");
		}
		if (map.get(ParseCoreReceiveXml.TRANSLOGNO) == null || map.get(ParseCoreReceiveXml.TRANSLOGNO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TRANSLOGNO + ",");
		}
		if (map.get(ParseCoreReceiveXml.VERSION) == null || map.get(ParseCoreReceiveXml.VERSION).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.VERSION + ",");
		}
		if (map.get(ParseCoreReceiveXml.OS_TYPE) == null || map.get(ParseCoreReceiveXml.OS_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.OS_TYPE + ",");
		}
		if (map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM) == null || map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_SERIAL_NUM + ",");
		}
		if (map.get(ParseCoreReceiveXml.TOKEN) == null || map.get(ParseCoreReceiveXml.TOKEN).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TOKEN + ",");
		}
		if (map.get(ParseCoreReceiveXml.APPLICATION) == null || map.get(ParseCoreReceiveXml.APPLICATION).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.APPLICATION + ",");
			// **************请求公共参数部分校验--over******************//
		}
	}

	/**
	 * 校验报文MD5
	 * 
	 * @param map
	 * @return
	 */
	protected boolean checkMD5(HashMap<String, String> map) {
		boolean checkFlag = true;
		String md5Key = "";
		String requestXml = map.get("signStr");
		String index = map.get(ParseCoreReceiveXml.APP_USER).toLowerCase() + map.get(ParseCoreReceiveXml.VERSION) + map.get(ParseCoreReceiveXml.CLIENT_TYPE);
		md5Key = TxServiceHelper.getSignKey(index, TuxedoConst.cacheFlag);
		// System.out.println("登陆KEY:"+index+"=="+md5Key);
		// String
		// key=TxServiceHelper.getSignKey(map.get(ParseCoreReceiveXml.VERSION));
		if (md5Key.equals("")) {
			log.error("获取不到md5key！对应版本号：" + index);
			checkFlag = false;
		} else {
			if (!MD5.reqXmlMD5IsCorrect(requestXml, md5Key)) {
				md5Key = "412fadsfoinhuc450f8jcnalzq08mfja";
				if (!MD5.reqXmlMD5IsCorrect(requestXml, md5Key)) {
					checkFlag = false;
				}
			}
		}
		map.put(ParseCoreReceiveXml.SIGN, md5Key);
		map.put(ParseCoreReceiveXml.VERSION_FLAG, "1");
		return checkFlag;
	}

	/**
	 * 检查报文请求时间
	 * 
	 * @param map
	 * @return
	 */
	protected boolean checkReqTime(HashMap<String, String> map) {
		long systemTime = System.currentTimeMillis();
		String time = map.get(ParseCoreReceiveXml.TRANSDATE) + map.get(ParseCoreReceiveXml.TRANSTIME);
		// 客户端与前置系统时间相差10分钟
		if (Math.abs(Format.StrToTime(time).getTime() - systemTime) >= 600000) {
			log.error("客户端系统时间与服务端系统时间不允许相差10分钟以上：" + map.get(ParseCoreReceiveXml.TRANSDATE) + map.get(ParseCoreReceiveXml.TRANSTIME) + map.get(ParseCoreReceiveXml.TRANSLOGNO));
			return false;
		} else {
			return true;
		}
	}

//	/**
//	 * 用户登录session校验
//	 * 
//	 * @param map
//	 * @return
//	 */
//	protected LocalResp checkLoginToken(HashMap<String, String> map) {
//		LocalResp localResp = new LocalResp();
//		// appuser+phone
//		String key = map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE);
//		if (DataDictInitialize.LOGIN_INFO_POOL.containsKey(key)) {
//			LoginInfoObject lio = (LoginInfoObject) DataDictInitialize.LOGIN_INFO_POOL.get(key);
//			if (!map.get("token").equals(lio.getToken())) {// token数据校验失败
//				log.error(" 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " token校验失败，登录信息异常，请重新登录！");
//				localResp.setRespCode(SystemCode.LOGIN_INFO_EXCEPTION.getCode());
//				localResp.setRespDesc(SystemCode.LOGIN_INFO_EXCEPTION.getDesc());
//			} else {
//				// 更新当前的登录有效时间
//				lio.setLoginTime(System.currentTimeMillis());
//				localResp.setRespCode(SystemCode.SUCCESS.getCode());
//				localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
//			}
//		} else {// 用户登录信息不存在
//			log.error(" 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " 登录信息已过期，请重新登录！");
//			localResp.setRespCode(SystemCode.LOGIN_TIME_OUT.getCode());
//			localResp.setRespDesc(SystemCode.LOGIN_TIME_OUT.getDesc());
//		}
//		return localResp;
//	}
	
	/**
	 * 用户登录session校验
	 * 
	 * @param map
	 * @return
	 */
	protected LocalResp checkLoginToken(HashMap<String, String> map) {
		String version = map.get(ParseCoreReceiveXml.VERSION);
		if(version.equals("2.0.6") || version.equals("2.0.7") || version.equals("2.1.0") || version.equals("2.1.4")){
			LocalResp localResp = new LocalResp();
			// appuser+phone
			String key = map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE);
			if (DataDictInitialize.LOGIN_INFO_POOL.containsKey(key)) {
				LoginInfoObject lio = (LoginInfoObject) DataDictInitialize.LOGIN_INFO_POOL.get(key);
				log.info("map里的token:"+map.get("token")+"--------lio里的token:"+lio.getToken());
				if (!map.get("token").equals(lio.getToken())) {// token数据校验失败
					log.error(" 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " token校验失败，登录信息异常，请重新登录！");
					localResp.setRespCode(SystemCode.LOGIN_INFO_EXCEPTION.getCode());
					localResp.setRespDesc(SystemCode.LOGIN_INFO_EXCEPTION.getDesc());
				} else {
					// 更新当前的登录有效时间
					lio.setLoginTime(System.currentTimeMillis());
					localResp.setRespCode(SystemCode.SUCCESS.getCode());
					localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
				}
			} else {// 用户登录信息不存在
				log.error(" 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " 登录信息已过期，请重新登录！");
				localResp.setRespCode(SystemCode.LOGIN_TIME_OUT.getCode());
				localResp.setRespDesc(SystemCode.LOGIN_TIME_OUT.getDesc());
			}
			return localResp;
		}else{
			LocalResp localResp = new LocalResp();
			// appuser+phone
			String appuser = map.get(ParseCoreReceiveXml.APP_USER);
			String phone = map.get(ParseCoreReceiveXml.PHONE);
			String token = map.get(ParseCoreReceiveXml.TOKEN);
			JedisUtil jedisUtil = JedisUtil.getInstance();
			JedisUtil.Objects objects = jedisUtil.new Objects();
			if(objects.exists(appuser+phone)){
				if(token != null && token.length()>0){
					if (token.equals(objects.getObject(appuser+phone).toString())) {
						LoginInfoObject lio = (LoginInfoObject) objects.getObject(token);
//						if (!token.equals(lio.getToken())) {// token数据校验失败
//							log.error(" 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " token校验失败，登录信息异常，请重新登录！");
//							localResp.setRespCode(SystemCode.LOGIN_INFO_EXCEPTION.getCode());
//							localResp.setRespDesc("登录信息异常,请重新登录");
//						} else {
							// 更新当前的登录有效时间
							lio.setLoginTime(System.currentTimeMillis());
//							objects.setObject(appuser + phone, token, JedisUtil.EXPIRE_MONTH);
//							objects.setObject(token, lio, JedisUtil.EXPIRE_MONTH);
							localResp.setRespCode(SystemCode.SUCCESS.getCode());
							localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
//						}
					} else {
						log.info(" 用户： " + appuser + phone + " 已经在其他手机上登录:"+token);
						localResp.setRespCode(SystemCode.LOGIN_INFO_EXCEPTION.getCode());
						localResp.setRespDesc(SystemCode.LOGIN_INFO_EXCEPTION.getDesc());
						String sendPostResult = HttpUtil.sendPost(DataDictInitialize.TOKEN_ERROR_SEND_IP, "{\"cmd\":\"removeTagByToken\",\"appName\":\""+appuser+"\",\"tagName\":\""+phone+"\",\"token\":\"*\"}");
						log.info("已经在其他手机上登录,通知客户端--->返回:" + sendPostResult);
					}
				}else{
					log.info(" 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " token校验失败，登录信息异常，请重新登录！");
					localResp.setRespCode(SystemCode.LOGIN_INFO_EXCEPTION.getCode());
					localResp.setRespDesc("token为空，登录信息异常，请重新登录！");
				}
			}else{
				// 用户登录信息不存在
				log.info(" 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " 登录信息已过期，请重新登录！");
				localResp.setRespCode(SystemCode.LOGIN_TIME_OUT.getCode());
				localResp.setRespDesc(SystemCode.LOGIN_TIME_OUT.getDesc());
			}
			
			return localResp;
		}
	}

	protected void handleReturnMessage(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		resultMap.put("application", applicationToSYSTEM + ".Rsp");
		resultMap.put("version", map.get(ParseCoreReceiveXml.VERSION));
		resultMap.put("mobileSerialNum", map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM));
		resultMap.put("appUser", map.get(ParseCoreReceiveXml.APP_USER));
		resultMap.put("clientType", map.get(ParseCoreReceiveXml.CLIENT_TYPE));
		resultMap.put("osType", map.get(ParseCoreReceiveXml.OS_TYPE));
		resultMap.put("transDate", map.get(ParseCoreReceiveXml.TRANSDATE));
		resultMap.put("transTime", map.get(ParseCoreReceiveXml.TRANSTIME));
		resultMap.put("transLogNo", map.get(ParseCoreReceiveXml.TRANSLOGNO));
	}
}
