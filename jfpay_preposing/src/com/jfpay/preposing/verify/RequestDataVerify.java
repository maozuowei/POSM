package com.jfpay.preposing.verify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.hnae.tuxedojms.allocate.TuxedoConst;
import cn.hnae.tuxedojms.allocate.TxServiceHelper;

import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.Format;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.utils.MD5;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class RequestDataVerify {
	
	static Logger log = Logger.getLogger(RequestDataVerify.class);

	public static HashMap<String, String> verify(HashMap<String, String> map,HashMap<String, String> resultMap) throws UnsupportedEncodingException {
		
		int i = 0;

		StringBuilder sb = new StringBuilder();

		sb.append("以下参数不能为空:");
		// **************请求公共参数部分校验--start******************//
		if((map.get(ParseCoreReceiveXml.VERSION)).compareTo("2.0.0")>=0){
			String md5Key = "";
			if (map.get(ParseCoreReceiveXml.SIGN) == null
					|| map.get(ParseCoreReceiveXml.SIGN).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.SIGN + ",");
			}else{
				String requestXml = map.get("signStr");
				
				String index = map.get(ParseCoreReceiveXml.APP_USER).toLowerCase()+map.get(ParseCoreReceiveXml.VERSION)+map.get(ParseCoreReceiveXml.CLIENT_TYPE);
				md5Key=TxServiceHelper.getSignKey(index,TuxedoConst.cacheFlag);
//				String key=TxServiceHelper.getSignKey(map.get(ParseCoreReceiveXml.VERSION));
				if(md5Key.equals("")){
					log.error("获取不到md5key！对应版本号："+index);
				}
				if(!MD5.reqXmlMD5IsCorrect(requestXml, md5Key)){
					md5Key = "03f9c58d88ad450f83c33f81e11b427d";
					if(!MD5.reqXmlMD5IsCorrect(requestXml, md5Key)){
						resultMap.put("respCode", SystemCode.SIGN_ERROR.getCode());
						resultMap.put("respDesc", SystemCode.SIGN_ERROR.getDesc());
						//签名失败则直接返回
						return resultMap;
					}
				}
				
//				System.out.println("sign:"+sign);
//				System.out.println("localSign:"+localSign);
				
//				if(!sign.equals(localSign)){
//					System.out.println("签名不一致");
//					resultMap.put("respCode", SystemCode.SIGN_ERROR.getCode());
//					resultMap.put("respDesc", SystemCode.SIGN_ERROR.getDesc());
//					//签名失败则直接返回
//					return resultMap;
//				}
			}
			map.put(ParseCoreReceiveXml.SIGN, md5Key); 
			map.put(ParseCoreReceiveXml.VERSION_FLAG, "1");
		}
		else{
			map.put(ParseCoreReceiveXml.VERSION_FLAG, "0");
		}
		if (map.get(ParseCoreReceiveXml.CLIENT_TYPE) == null
				|| map.get(ParseCoreReceiveXml.CLIENT_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CLIENT_TYPE + ",");
		}
		if (map.get(ParseCoreReceiveXml.APP_USER) == null
				|| map.get(ParseCoreReceiveXml.APP_USER).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.APP_USER + ",");
		}
//		if (map.get(ParseCoreReceiveXml.USER_IP) == null
//				|| map.get(ParseCoreReceiveXml.USER_IP).trim().length() == 0) {
//			i++;
//			sb.append(ParseCoreReceiveXml.USER_IP + ",");
//		}
		if (map.get(ParseCoreReceiveXml.TRANSDATE) == null
				|| map.get(ParseCoreReceiveXml.TRANSDATE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TRANSDATE + ",");
		}
		if (map.get(ParseCoreReceiveXml.TRANSTIME) == null
				|| map.get(ParseCoreReceiveXml.TRANSTIME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TRANSTIME + ",");
		}
		if (map.get(ParseCoreReceiveXml.TRANSLOGNO) == null
				|| map.get(ParseCoreReceiveXml.TRANSLOGNO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TRANSLOGNO + ",");
		}
		if (map.get(ParseCoreReceiveXml.VERSION) == null
				|| map.get(ParseCoreReceiveXml.VERSION).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.VERSION + ",");
		}
		if (map.get(ParseCoreReceiveXml.OS_TYPE) == null
				|| map.get(ParseCoreReceiveXml.OS_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.OS_TYPE + ",");
		}
		if (map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM) == null
				|| map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM).trim()
						.length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_SERIAL_NUM + ",");
		}
		if (map.get(ParseCoreReceiveXml.TOKEN) == null
				|| map.get(ParseCoreReceiveXml.TOKEN).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TOKEN + ",");
		}
		if (map.get(ParseCoreReceiveXml.APPLICATION) == null
				|| map.get(ParseCoreReceiveXml.APPLICATION).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.APPLICATION + ",");
			// **************请求公共参数部分校验--over******************//
		} else {
			String application = map.get(ParseCoreReceiveXml.APPLICATION);
			// **************具体交易参数部分校验--start******************//
			// 用户注册
			if (application
					.equals(ParseCoreReceiveXml.application_UserRegister)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.USER_NAME) == null
						|| map.get(ParseCoreReceiveXml.USER_NAME).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.USER_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			// 用户登录
			if (application.equals(ParseCoreReceiveXml.application_UserLogin)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
			}
			// 修改密码
			if (application
					.equals(ParseCoreReceiveXml.application_UserUpdatePwd)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.NEW_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.NEW_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.NEW_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			// 用户资料完善
			if (application
					.equals(ParseCoreReceiveXml.application_UserUpdateInfo)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.REAL_NAME) == null
						|| map.get(ParseCoreReceiveXml.REAL_NAME).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.REAL_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.CERT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.CERT_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CERT_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.CERT_PID) == null
						|| map.get(ParseCoreReceiveXml.CERT_PID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CERT_PID + ",");
				}
			}
			// 获取短信校验码
			if (application
					.equals(ParseCoreReceiveXml.application_GetMobileMac)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.APP_TYPE) == null
						|| map.get(ParseCoreReceiveXml.APP_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.APP_TYPE + ",");
				}
			}
			// 终端合法性验证
			if (application
					.equals(ParseCoreReceiveXml.application_JFTermVerify)) {
				if (map.get(ParseCoreReceiveXml.TERM_ID) == null
						|| map.get(ParseCoreReceiveXml.TERM_ID).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.TERM_ID + ",");
				}
			}
			// 余额查询
			if (application
					.equals(ParseCoreReceiveXml.application_JFPalAcctEnquiry)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.ACCT_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCT_TYPE + ",");
				}
			}
			// 收支明细
			if (application
					.equals(ParseCoreReceiveXml.application_GetJFPalDetail)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
			}
			// 预付卡绑定
			if (application
					.equals(ParseCoreReceiveXml.application_UserCardBind)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_NO) == null
						|| map.get(ParseCoreReceiveXml.CARD_NO).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
				}
			}
			// 预付卡列表查询
			if (application
					.equals(ParseCoreReceiveXml.application_GetUserCardList)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
			}
			// 预付卡余额查询
			if (application
					.equals(ParseCoreReceiveXml.application_UserCardBalanceEnquiry)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_TAG) == null
						|| map.get(ParseCoreReceiveXml.CARD_TAG).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_TAG + ",");
				}
			}
			// 预付卡余额合并
			if (application
					.equals(ParseCoreReceiveXml.application_UserCardMerger)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_TAG) == null
						|| map.get(ParseCoreReceiveXml.CARD_TAG).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_TAG + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
			}
			// 预付卡解除绑定
			if (application
					.equals(ParseCoreReceiveXml.applicate_UserCardUnBind)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_TAG) == null
						|| map.get(ParseCoreReceiveXml.CARD_TAG).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_TAG + ",");
				}
			}
			// 银行卡登记
			if (application
					.equals(ParseCoreReceiveXml.application_BankCardBind)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.BANK_ID) == null
						|| map.get(ParseCoreReceiveXml.BANK_ID).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.BANK_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCOUNT_NO) == null
						|| map.get(ParseCoreReceiveXml.ACCOUNT_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCOUNT_NO + ",");
				}
			}
			// 银行卡列表查询
			if (application
					.equals(ParseCoreReceiveXml.application_GetBankCardList)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.BIND_TYPE) == null
						|| map.get(ParseCoreReceiveXml.BIND_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.BIND_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_NUM) == null
						|| map.get(ParseCoreReceiveXml.CARD_NUM).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_NUM + ",");
				}
			}
			// 提款信息查询
			if (application
					.equals(ParseCoreReceiveXml.application_QueryUserCash)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
			}
			// 手机号提款
			if (application.equals(ParseCoreReceiveXml.application_JFPalCash)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_TAG) == null
						|| map.get(ParseCoreReceiveXml.CARD_TAG).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_TAG + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.CASH_AMT) == null
						|| map.get(ParseCoreReceiveXml.CASH_AMT).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CASH_AMT + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			// 信用卡信息查询
			if (application
					.equals(ParseCoreReceiveXml.application_QueryCreditInfo)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.REAL_NAME) == null
						|| map.get(ParseCoreReceiveXml.REAL_NAME).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.REAL_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCOUNT_NO) == null
						|| map.get(ParseCoreReceiveXml.ACCOUNT_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCOUNT_NO + ",");
				}
			}
			// 即付宝订单请求
			if (application
					.equals(ParseCoreReceiveXml.application_RequestOrder)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null
						|| map.get(ParseCoreReceiveXml.MERCHANT_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_AMT) == null
						|| map.get(ParseCoreReceiveXml.ORDER_AMT).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_AMT + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_DESC) == null
						|| map.get(ParseCoreReceiveXml.ORDER_DESC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_DESC + ",");
				}
			}
			// 即付宝账户支付
			if (application
					.equals(ParseCoreReceiveXml.application_JFPalAcctPay)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.ACCT_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCT_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null
						|| map.get(ParseCoreReceiveXml.MERCHANT_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.PRODUCT_ID) == null
						|| map.get(ParseCoreReceiveXml.PRODUCT_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PRODUCT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_AMT) == null
						|| map.get(ParseCoreReceiveXml.ORDER_AMT).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_AMT + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_ID) == null
						|| map.get(ParseCoreReceiveXml.ORDER_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
				}
			}
			// 即付宝预付卡支付
			if (application.equals(ParseCoreReceiveXml.application_PrepaidPay)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_TAG) == null
						|| map.get(ParseCoreReceiveXml.CARD_TAG).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_TAG + ",");
				}
				if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null
						|| map.get(ParseCoreReceiveXml.MERCHANT_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.PRODUCT_ID) == null
						|| map.get(ParseCoreReceiveXml.PRODUCT_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PRODUCT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_AMT) == null
						|| map.get(ParseCoreReceiveXml.ORDER_AMT).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_AMT + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_ID) == null
						|| map.get(ParseCoreReceiveXml.ORDER_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
				}
			}
			// 即付宝刷卡支付
			if (application
					.equals(ParseCoreReceiveXml.application_JFPalCardPay)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INFO) == null
						|| map.get(ParseCoreReceiveXml.CARD_INFO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INFO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null
						|| map.get(ParseCoreReceiveXml.MERCHANT_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.PRODUCT_ID) == null
						|| map.get(ParseCoreReceiveXml.PRODUCT_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PRODUCT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_AMT) == null
						|| map.get(ParseCoreReceiveXml.ORDER_AMT).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_AMT + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_ID) == null
						|| map.get(ParseCoreReceiveXml.ORDER_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
				}
			}
			// 银行卡解除绑定
			if (application
					.equals(ParseCoreReceiveXml.application_BankCardUnBind)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
			}
			// 银行卡余额查询
			if (application
					.equals(ParseCoreReceiveXml.application_BankCardBalance)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INFO) == null
						|| map.get(ParseCoreReceiveXml.CARD_INFO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INFO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_ID) == null
						|| map.get(ParseCoreReceiveXml.ORDER_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
				}
			}
//			//即付宝公缴查询
//			if (application.equals(ParseCoreReceiveXml.application_QueryPublicUtilities)){
//				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
//						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
//								.length() == 0) {
//					i++;
//					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
//				}
//				if (map.get(ParseCoreReceiveXml.ORDER_NO) == null
//						|| map.get(ParseCoreReceiveXml.ORDER_NO).trim()
//								.length() == 0) {
//					i++;
//					sb.append(ParseCoreReceiveXml.ORDER_NO + ",");
//				}
//				if (map.get(ParseCoreReceiveXml.ORDER_CONTENT) == null
//						|| map.get(ParseCoreReceiveXml.ORDER_CONTENT).trim()
//								.length() == 0) {
//					i++;
//					sb.append(ParseCoreReceiveXml.ORDER_CONTENT + ",");
//				}
//			}
			// 获取用户须知
			if (application
					.equals(ParseCoreReceiveXml.application_GetUserInstruction)) {
				if (map.get(ParseCoreReceiveXml.INSTRUCTION_VERSION) == null
						|| map.get(ParseCoreReceiveXml.INSTRUCTION_VERSION)
								.trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.INSTRUCTION_VERSION + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.INSTRUCTION_VERSION
							+ "="+ map.get(ParseCoreReceiveXml.INSTRUCTION_VERSION)+"&"+
							ParseCoreReceiveXml.VERSION+"="+map.get(ParseCoreReceiveXml.VERSION)+"&"+
							ParseCoreReceiveXml.APP_USER+ "="+ map.get(ParseCoreReceiveXml.APP_USER);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			// 获取公告通知
			if (application
					.equals(ParseCoreReceiveXml.application_GetPublicNotice)) {
				if (map.get(ParseCoreReceiveXml.NOTICE_CODE) == null
						|| map.get(ParseCoreReceiveXml.NOTICE_CODE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.NOTICE_CODE + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.NOTICE_CODE + "="
							+ map.get(ParseCoreReceiveXml.NOTICE_CODE)+"&"+
							ParseCoreReceiveXml.APP_USER+ "="+ map.get(ParseCoreReceiveXml.APP_USER);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			// 获取总行列表
			if (application
					.equals(ParseCoreReceiveXml.application_GetBankHeadQuarter)) {
				if(i==0){
					map.put(ParseCoreReceiveXml.PARAMETER, "");
					map.put("postUrl", UrlCache.POST_URL_NOTE);		
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			// 获取支行列表
			if (application
					.equals(ParseCoreReceiveXml.application_GetBankBranch)) {
				if (map.get(ParseCoreReceiveXml.BANK_ID) == null
						|| map.get(ParseCoreReceiveXml.BANK_ID).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.BANK_ID + ",");
				} 
				if (map.get(ParseCoreReceiveXml.BANK_PROVINCE_ID) == null
						|| map.get(ParseCoreReceiveXml.BANK_PROVINCE_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.BANK_PROVINCE_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.BANK_CITY_ID) == null
						|| map.get(ParseCoreReceiveXml.BANK_CITY_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.BANK_CITY_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.OFFSET) == null
						|| map.get(ParseCoreReceiveXml.OFFSET).trim().length() == 0){
					i++;
					sb.append(ParseCoreReceiveXml.OFFSET + ",");
				}
				if (i==0){
					StringBuffer parameter = new StringBuffer();
					if(map.get(ParseCoreReceiveXml.CONDITION) != null){
						parameter.append(ParseCoreReceiveXml.CONDITION + "="
						+map.get(ParseCoreReceiveXml.CONDITION)+"&");
					}
					parameter.append(ParseCoreReceiveXml.BANK_ID + "="
							+ map.get(ParseCoreReceiveXml.BANK_ID) + "&"
							+ ParseCoreReceiveXml.BANK_PROVINCE_ID + "="
							+ map.get(ParseCoreReceiveXml.BANK_PROVINCE_ID) + "&"
							+ ParseCoreReceiveXml.BANK_CITY_ID + "="
							+ map.get(ParseCoreReceiveXml.BANK_CITY_ID) + "&"
							+ ParseCoreReceiveXml.OFFSET + "="
							+ map.get(ParseCoreReceiveXml.OFFSET));
					map.put(ParseCoreReceiveXml.PARAMETER, parameter.toString());
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			// 银行卡列表查询
			if (application
					.equals(ParseCoreReceiveXml.application_GetBankCardList2)) {
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.BIND_TYPE) == null
						|| map.get(ParseCoreReceiveXml.BIND_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.BIND_TYPE + ",");
				} 
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				} 
				if (map.get(ParseCoreReceiveXml.CARD_NUM) == null
						|| map.get(ParseCoreReceiveXml.CARD_NUM).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_NUM + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.MOBILE_NO + "="
							+ map.get(ParseCoreReceiveXml.MOBILE_NO) + "&"
							+ ParseCoreReceiveXml.BIND_TYPE + "="
							+ map.get(ParseCoreReceiveXml.BIND_TYPE) + "&"
							+ ParseCoreReceiveXml.CARD_INDEX + "="
							+ map.get(ParseCoreReceiveXml.CARD_INDEX) + "&"
							+ ParseCoreReceiveXml.CARD_NUM + "="
							+ map.get(ParseCoreReceiveXml.CARD_NUM);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			//新的客户端更新
			if(application
					.equals(ParseCoreReceiveXml.application_ClientUpdate2)){
				if(i==0){
					String parameter = ParseCoreReceiveXml.VERSION+"="+
					map.get(ParseCoreReceiveXml.VERSION)+"&"+ParseCoreReceiveXml.CLIENT_TYPE+"="+
					map.get(ParseCoreReceiveXml.CLIENT_TYPE)+"&"+ParseCoreReceiveXml.APP_USER+"="+
					map.get(ParseCoreReceiveXml.APP_USER);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			//找回即付宝密码
			if(application.equals(ParseCoreReceiveXml.application_RetrievePassword)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.REAL_NAME) == null
						|| map.get(ParseCoreReceiveXml.REAL_NAME).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.REAL_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.CERT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.CERT_TYPE).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CERT_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.CERT_PID) == null
						|| map.get(ParseCoreReceiveXml.CERT_PID).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CERT_PID + ",");
				}
				if (map.get(ParseCoreReceiveXml.NEW_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.NEW_PASSWD).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.NEW_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			//企业账户资料完善
			if(application.equals(ParseCoreReceiveXml.application_EnterpriseUserUpdateInfo)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.ENTER_NAME) == null
						|| map.get(ParseCoreReceiveXml.ENTER_NAME).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ENTER_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.ENTER_CERT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.ENTER_CERT_TYPE).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ENTER_CERT_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.ENTER_CERT_PID) == null
						|| map.get(ParseCoreReceiveXml.ENTER_CERT_PID).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ENTER_CERT_PID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ENTER_ADDRESS) == null
						|| map.get(ParseCoreReceiveXml.ENTER_ADDRESS).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ENTER_ADDRESS + ",");
				}
				if (map.get(ParseCoreReceiveXml.LEGAL_NAME) == null
						|| map.get(ParseCoreReceiveXml.LEGAL_NAME).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.LEGAL_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.LEGAL_CERT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.LEGAL_CERT_TYPE).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.LEGAL_CERT_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.LEGAL_CERT_PID) == null
						|| map.get(ParseCoreReceiveXml.LEGAL_CERT_PID).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.LEGAL_CERT_PID + ",");
				}
				if (map.get(ParseCoreReceiveXml.CONTACT_NAME) == null
						|| map.get(ParseCoreReceiveXml.CONTACT_NAME).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CONTACT_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.CONTACT_PHONE) == null
						|| map.get(ParseCoreReceiveXml.CONTACT_PHONE).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CONTACT_PHONE + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.MOBILE_NO + "="
							+ map.get(ParseCoreReceiveXml.MOBILE_NO) + "&"
							+ ParseCoreReceiveXml.ENTER_NAME + "="
							+ map.get(ParseCoreReceiveXml.ENTER_NAME) + "&"
							+ ParseCoreReceiveXml.ENTER_CERT_TYPE + "="
							+ map.get(ParseCoreReceiveXml.ENTER_CERT_TYPE) + "&"
							+ ParseCoreReceiveXml.ENTER_CERT_PID + "="
							+ map.get(ParseCoreReceiveXml.ENTER_CERT_PID)+ "&"
							+ ParseCoreReceiveXml.ENTER_ADDRESS + "="
							+ map.get(ParseCoreReceiveXml.ENTER_ADDRESS)+ "&"
							+ ParseCoreReceiveXml.LEGAL_NAME + "="
							+ map.get(ParseCoreReceiveXml.LEGAL_NAME) + "&"
							+ ParseCoreReceiveXml.LEGAL_CERT_TYPE + "="
							+ map.get(ParseCoreReceiveXml.LEGAL_CERT_TYPE)+ "&"
							+ ParseCoreReceiveXml.LEGAL_CERT_PID + "="
							+ map.get(ParseCoreReceiveXml.LEGAL_CERT_PID)+ "&"
							+ ParseCoreReceiveXml.CONTACT_NAME + "="
							+ map.get(ParseCoreReceiveXml.CONTACT_NAME)+ "&"
							+ ParseCoreReceiveXml.CONTACT_PHONE + "="
							+ map.get(ParseCoreReceiveXml.CONTACT_PHONE) + "&"
							+ ParseCoreReceiveXml.TRANSDATE + "="
							+ map.get(ParseCoreReceiveXml.TRANSDATE)+ "&"
							+ ParseCoreReceiveXml.TRANSTIME + "="
							+ map.get(ParseCoreReceiveXml.TRANSTIME);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "insert");
				}
			}
			//订单手写签名信息上传
			if(application.equals(ParseCoreReceiveXml.application_UserSignatureUpload)){
				if (map.get(ParseCoreReceiveXml.LONGITUDE) == null
						|| map.get(ParseCoreReceiveXml.LONGITUDE).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.LONGITUDE + ",");
				}
				if (map.get(ParseCoreReceiveXml.LATITUDE) == null
						|| map.get(ParseCoreReceiveXml.LATITUDE).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.LATITUDE + ",");
				}
				if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null
						|| map.get(ParseCoreReceiveXml.MERCHANT_ID).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ORDER_ID) == null
						|| map.get(ParseCoreReceiveXml.ORDER_ID).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.SIGN_PIC_ASCII) == null
						|| map.get(ParseCoreReceiveXml.SIGN_PIC_ASCII).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.SIGN_PIC_ASCII + ",");
				}
				if (map.get(ParseCoreReceiveXml.PIC_SIGN) == null
						|| map.get(ParseCoreReceiveXml.PIC_SIGN).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PIC_SIGN + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.LONGITUDE + "="
							+ map.get(ParseCoreReceiveXml.LONGITUDE) + "&"
							+ ParseCoreReceiveXml.LATITUDE + "="
							+ map.get(ParseCoreReceiveXml.LATITUDE) + "&"
							+ ParseCoreReceiveXml.MERCHANT_ID + "="
							+ map.get(ParseCoreReceiveXml.MERCHANT_ID) + "&"
							+ ParseCoreReceiveXml.ORDER_ID + "="
							+ map.get(ParseCoreReceiveXml.ORDER_ID)+ "&"
							+ ParseCoreReceiveXml.SIGN_PIC_ASCII + "="
							+ map.get(ParseCoreReceiveXml.SIGN_PIC_ASCII)+ "&"
							+ ParseCoreReceiveXml.PIC_SIGN + "="
							+ map.get(ParseCoreReceiveXml.PIC_SIGN)+ "&"
							+ ParseCoreReceiveXml.TRANSDATE+ "="
							+ map.get(ParseCoreReceiveXml.TRANSDATE)+ "&"
							+ ParseCoreReceiveXml.TRANSTIME+ "="
							+ map.get(ParseCoreReceiveXml.TRANSTIME);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "insert");
				}
			}
			//未支付商户订单信息查询
			if(application.equals(ParseCoreReceiveXml.application_EnquiryOrder)){
				if (map.get(ParseCoreReceiveXml.ORDER_ID) == null
						|| map.get(ParseCoreReceiveXml.ORDER_ID).trim()
						.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.ORDER_ID + "="
							+ map.get(ParseCoreReceiveXml.ORDER_ID);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			//用户个人照片上传
			if(application
					.equals(ParseCoreReceiveXml.application_UserIdentityPicUpload)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.PID_IMG) == null
						|| map.get(ParseCoreReceiveXml.PID_IMG).trim()
						.length() == 0){
					i++;
					sb.append(ParseCoreReceiveXml.PID_IMG + ",");
				}
				if (map.get(ParseCoreReceiveXml.PID_ANTI_IMG) == null
						|| map.get(ParseCoreReceiveXml.PID_ANTI_IMG).trim()
						.length() == 0){
					i++;
					sb.append(ParseCoreReceiveXml.PID_ANTI_IMG + ",");
				}
				if (map.get(ParseCoreReceiveXml.PIC) == null
						|| map.get(ParseCoreReceiveXml.PIC).trim()
						.length() == 0){
					i++;
					sb.append(ParseCoreReceiveXml.PIC + ",");
				}
				if (map.get(ParseCoreReceiveXml.PID_IMG_SIGN) == null
						|| map.get(ParseCoreReceiveXml.PID_IMG_SIGN).trim()
						.length() == 0){
					i++;
					sb.append(ParseCoreReceiveXml.PID_IMG_SIGN + ",");
				}
				if (map.get(ParseCoreReceiveXml.PID_ANTI_IMG_SIGN) == null
						|| map.get(ParseCoreReceiveXml.PID_ANTI_IMG_SIGN).trim()
						.length() == 0){
					i++;
					sb.append(ParseCoreReceiveXml.PID_ANTI_IMG_SIGN + ",");
				}
				if (map.get(ParseCoreReceiveXml.PIC_SIGN) == null
						|| map.get(ParseCoreReceiveXml.PIC_SIGN).trim()
						.length() == 0){
					i++;
					sb.append(ParseCoreReceiveXml.PIC_SIGN + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.MOBILE_NO+"="+
					map.get(ParseCoreReceiveXml.MOBILE_NO)+"&"+ParseCoreReceiveXml.CUSTOMER_ID+"="+
					map.get(ParseCoreReceiveXml.CUSTOMER_ID)+"&"+ParseCoreReceiveXml.PID_IMG+"="+
					URLEncoder.encode(map.get(ParseCoreReceiveXml.PID_IMG),"UTF-8")+"&"+ParseCoreReceiveXml.PID_ANTI_IMG+"="+
					URLEncoder.encode(map.get(ParseCoreReceiveXml.PID_ANTI_IMG),"UTF-8")+"&"+ParseCoreReceiveXml.PIC+"="+
					URLEncoder.encode(map.get(ParseCoreReceiveXml.PIC),"UTF-8")+"&"+ParseCoreReceiveXml.PID_IMG_SIGN+"="+
					map.get(ParseCoreReceiveXml.PID_IMG_SIGN)+"&"+ParseCoreReceiveXml.PID_ANTI_IMG_SIGN+"="+
					map.get(ParseCoreReceiveXml.PID_ANTI_IMG_SIGN)+"&"+ParseCoreReceiveXml.PIC_SIGN+"="+
					map.get(ParseCoreReceiveXml.PIC_SIGN);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "insert");
				}
			}
			//个人用户信息查询
			if(application.equals(ParseCoreReceiveXml.application_UserInfoQuery)){
				if(i==0){
					String parameter = ParseCoreReceiveXml.MOBILE_NO + "="
					+ map.get(ParseCoreReceiveXml.MOBILE_NO)+"&"+ParseCoreReceiveXml.CUSTOMER_ID+"="
					+ map.get(ParseCoreReceiveXml.CUSTOMER_ID);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "search");
				}
			}
			//沿海银行卡绑定激活
			if(application.equals(ParseCoreReceiveXml.application_YHBankCardBind)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCOUNT_NO) == null
						|| map.get(ParseCoreReceiveXml.ACCOUNT_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCOUNT_NO + ",");
				} 
				if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			//沿海银行卡密码修改
			if(application.equals(ParseCoreReceiveXml.application_YHBankCardChangePass)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				} 
				if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.NEW_CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.NEW_CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.NEW_CARD_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			//沿海银行卡挂失
			if(application.equals(ParseCoreReceiveXml.application_YHBankCardLoss)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				} 
				if (map.get(ParseCoreReceiveXml.LOSS_TYPE) == null
						|| map.get(ParseCoreReceiveXml.LOSS_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.LOSS_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.CERT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.CERT_TYPE).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CERT_TYPE + ",");
				}
				if (map.get(ParseCoreReceiveXml.CERT_PID) == null
						|| map.get(ParseCoreReceiveXml.CERT_PID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CERT_PID + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			//沿海银行卡余额查询
			if(application.equals(ParseCoreReceiveXml.application_YHBankCardBalance)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				} 
			}
			//沿海银行卡转账
			if(application.equals(ParseCoreReceiveXml.application_YHBankCardTransfer)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.BRANCH_BANK_ID) == null
						|| map.get(ParseCoreReceiveXml.BRANCH_BANK_ID).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.BRANCH_BANK_ID + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCOUNT_NAME) == null
						|| map.get(ParseCoreReceiveXml.ACCOUNT_NAME).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCOUNT_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCOUNT_NO) == null
						|| map.get(ParseCoreReceiveXml.ACCOUNT_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCOUNT_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CASH_AMT) == null
						|| map.get(ParseCoreReceiveXml.CASH_AMT).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CASH_AMT + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			//沿海银行卡结清
			if(application.equals(ParseCoreReceiveXml.application_YHBankCardSettle)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCOUNT_NAME) == null
						|| map.get(ParseCoreReceiveXml.ACCOUNT_NAME).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCOUNT_NAME + ",");
				}
				if (map.get(ParseCoreReceiveXml.ACCOUNT_NO) == null
						|| map.get(ParseCoreReceiveXml.ACCOUNT_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.ACCOUNT_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null
						|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.PASSWD) == null
						|| map.get(ParseCoreReceiveXml.PASSWD).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.PASSWD + ",");
				}
				if (map.get(ParseCoreReceiveXml.MOBILE_MAC) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_MAC).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_MAC + ",");
				}
			}
			//沿海银行卡列表获取
			if(application.equals(ParseCoreReceiveXml.application_GetYHBankCardList)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null
						|| map.get(ParseCoreReceiveXml.CARD_INDEX).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
				}
				if (map.get(ParseCoreReceiveXml.CARD_NUM) == null
						|| map.get(ParseCoreReceiveXml.CARD_NUM).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_NUM + ",");
				}
				if(i==0){
					String parameter = ParseCoreReceiveXml.MOBILE_NO + "="
							+ map.get(ParseCoreReceiveXml.MOBILE_NO) + "&"
							+ ParseCoreReceiveXml.CARD_INDEX + "="
							+ map.get(ParseCoreReceiveXml.CARD_INDEX) + "&"
							+ ParseCoreReceiveXml.CARD_NUM + "="
							+ map.get(ParseCoreReceiveXml.CARD_NUM);
					map.put(ParseCoreReceiveXml.PARAMETER, parameter);
					map.put("postUrl", UrlCache.POST_URL_NOTE);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put("requestType", "newSearch");
				}
			}
			//福彩可售期次信息
			if(application.equals(ParseCoreReceiveXml.application_GetLotteryCurrentPeriod)){
				if(i==0){
					JSONObject reqJson = new JSONObject();
					reqJson.put("application", application);
					reqJson.put("serialID", Format.getLocalTrmSeqNum());
					JSONArray jsonArray = new JSONArray();
					JSONObject reqBeanJson = new JSONObject();
					reqBeanJson.put("detail",jsonArray.toString());
					JSONObject json = new JSONObject();
					json.put("Req", reqJson);
					json.put("ReqBean", reqBeanJson);
					map.put("postUrl", UrlCache.POST_URL_LOTTERY);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put(ParseCoreReceiveXml.PARAMETER, json.toString());		
				}
			}
			//福彩开奖号码查询
			if(application.equals(ParseCoreReceiveXml.application_GetLotteryAwardNumber)){
				if (map.get(ParseCoreReceiveXml.QUERY_FLAG) == null
						|| map.get(ParseCoreReceiveXml.QUERY_FLAG).trim()
						.length() ==0){
					i++;
					sb.append(ParseCoreReceiveXml.QUERY_FLAG + ",");
				}
				if(i==0){
					JSONObject reqJson = new JSONObject();
					reqJson.put("application", application);
					reqJson.put("serialID", Format.getLocalTrmSeqNum());
					JSONObject detail = new JSONObject();
					detail.put("lotteryCode",(map.containsKey("lotteryCode")?map.get("lotteryCode"):""));
					detail.put("offset",(map.containsKey("offset")?map.get("offset"):""));
					detail.put("queryFlag",map.get("queryFlag"));
					JSONArray jsonArray = new JSONArray();
					jsonArray.add(detail);
					JSONObject reqBeanJson = new JSONObject();
					reqBeanJson.put("detail",jsonArray.toString());
					JSONObject json = new JSONObject();
					json.put("Req", reqJson);
					json.put("ReqBean", reqBeanJson);
					map.put("postUrl", UrlCache.POST_URL_LOTTERY);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put(ParseCoreReceiveXml.PARAMETER, json.toString());
				}
			}
			//福彩订单投注
			if(application.equals(ParseCoreReceiveXml.application_SaveLotteryBetOrder)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if(map.get(ParseCoreReceiveXml.REAL_NAME) == null
						|| map.get(ParseCoreReceiveXml.REAL_NAME).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.REAL_NAME + ",");
				}
				if(map.get(ParseCoreReceiveXml.CERT_TYPE) == null
						|| map.get(ParseCoreReceiveXml.CERT_TYPE).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.CERT_TYPE + ",");
				}
				if(map.get(ParseCoreReceiveXml.CERT_PID) == null
						|| map.get(ParseCoreReceiveXml.CERT_PID).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.CERT_PID + ",");
				}
				if(map.get(ParseCoreReceiveXml.PERIOD_ID) == null
						|| map.get(ParseCoreReceiveXml.PERIOD_ID).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.PERIOD_ID + ",");
				}
				if(map.get(ParseCoreReceiveXml.LOTTERY_ID) == null
						|| map.get(ParseCoreReceiveXml.LOTTERY_ID).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.LOTTERY_ID + ",");
				}
				if(map.get(ParseCoreReceiveXml.BOOK_PERIODS) == null
						|| map.get(ParseCoreReceiveXml.BOOK_PERIODS).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.BOOK_PERIODS + ",");
				}
				if(map.get(ParseCoreReceiveXml.TOTAL_AMT) == null
						|| map.get(ParseCoreReceiveXml.TOTAL_AMT).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.TOTAL_AMT + ",");
				}
				if(map.get(ParseCoreReceiveXml.BET_DATA) == null
						|| map.get(ParseCoreReceiveXml.BET_DATA).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.BET_DATA + ",");
				}
				if(i==0){
					JSONObject reqJson = new JSONObject();
					reqJson.put("application", application);
					reqJson.put("serialID", Format.getLocalTrmSeqNum());
					JSONObject detail = new JSONObject();
					detail.put("mobileNo",map.get("mobileNo"));
					detail.put("realName",map.get("realName"));
					detail.put("certType",map.get("certType"));
					detail.put("certPid",map.get("certPid"));	
					detail.put("periodNo",map.get("periodNo"));	
					detail.put("lotteryCode",map.get("lotteryCode"));	
					detail.put("customerID",map.get("mobileNo"));
					detail.put("superAddPeriods ",map.get("superAddPeriods"));
					detail.put("totalAmount ",map.get("totalAmount"));
					detail.put("betData",JSONObject.fromObject(map.get("betData")).getString("requestBean"));
					JSONArray jsonArray = new JSONArray();
					jsonArray.add(detail);
					JSONObject reqBeanJson = new JSONObject();
					reqBeanJson.put("detail",jsonArray.toString());
					JSONObject json = new JSONObject();
					json.put("Req", reqJson);
					json.put("ReqBean", reqBeanJson);
					map.put("postUrl", UrlCache.POST_URL_LOTTERY);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put(ParseCoreReceiveXml.PARAMETER, json.toString());
				}
			}
			//个人购彩信息统计
			if(application.equals(ParseCoreReceiveXml.application_GetUserLotteryInfo)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if(i==0){
					JSONObject reqJson = new JSONObject();
					reqJson.put("application", application);
					reqJson.put("serialID", Format.getLocalTrmSeqNum());
					JSONObject detail = new JSONObject();
					detail.put("mobileNo",map.get("mobileNo"));
					detail.put("customerID",map.get("mobileNo"));
					JSONArray jsonArray =new JSONArray();
					jsonArray.add(detail);
					JSONObject reqBeanJson = new JSONObject();
					reqBeanJson.put("detail",jsonArray.toString());
					JSONObject json = new JSONObject();
					json.put("Req", reqJson);
					json.put("ReqBean", reqBeanJson);
					map.put("postUrl", UrlCache.POST_URL_LOTTERY);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put(ParseCoreReceiveXml.PARAMETER, json.toString());
				}
			}
			//个人投注订单查询
			if(application.equals(ParseCoreReceiveXml.application_GetUserLotteryBetRecord)){
				if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
						|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
								.length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
				}
				if(map.get(ParseCoreReceiveXml.OFFSET) == null
						|| map.get(ParseCoreReceiveXml.OFFSET).trim()
						.length()==0){
					i++;
					sb.append(ParseCoreReceiveXml.OFFSET + ",");
				}
				if(i==0){
					JSONObject reqJson = new JSONObject();
					reqJson.put("application", application);
					reqJson.put("serialID", Format.getLocalTrmSeqNum());
					JSONObject detail = new JSONObject();
					detail.put("mobileNo",map.get("mobileNo"));
					detail.put("customerID",map.get("mobileNo"));
					detail.put("offset", map.get("offset"));
					JSONArray jsonArray = new JSONArray();
					jsonArray.add(detail);
					JSONObject reqBeanJson = new JSONObject();
					reqBeanJson.put("detail",jsonArray.toString());
					JSONObject json = new JSONObject();
					json.put("Req", reqJson);
					json.put("ReqBean", reqBeanJson);
					map.put("postUrl", UrlCache.POST_URL_LOTTERY);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_HTTP));//http
					map.put(ParseCoreReceiveXml.PARAMETER, json.toString());
				}
			}
			//公缴城市、区域信息查询
			if(application.equals(ParseCoreReceiveXml.application_GetPublicUtilitiesArea)){
				if(i==0){
					HashMap<String,String> webserviceInput = new HashMap<String,String>();
					webserviceInput.put("TRADE_CODE", "GJ0003");// 公共事业缴费城市、区域查询
					webserviceInput.put("TRN_TX_DATE", Format.formatDate());
					webserviceInput.put("TRN_TX_TIME", Format.formatTime());
					webserviceInput.put("TRADE_KEY", Format.getLocalTrmSeqNum());// 20位交易流水号
					String inputJson = JSONObject.fromObject(webserviceInput).toString();
					webserviceInput = null;
					map.put("postUrl", UrlCache.POST_URL_PUBLIC);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_WEBSERVICE));//webservice
					map.put(ParseCoreReceiveXml.PARAMETER, inputJson);
				}
			}
			//公缴缴费项目列表
			if(application.equals(ParseCoreReceiveXml.application_GetPublicUtilitiesItems)){
				if(i==0){
					HashMap<String,String> webserviceInput = new HashMap<String,String>();
					webserviceInput.put("TRADE_CODE", "GJ0004");// 缴费项目列表
					webserviceInput.put("TRN_TX_DATE", Format.formatDate());
					webserviceInput.put("TRN_TX_TIME", Format.formatTime());
					webserviceInput.put("TRADE_KEY", Format.getLocalTrmSeqNum());// 20位交易流水号
					webserviceInput.put("TX_CITY", map.get(ParseCoreReceiveXml.CITY_CODE));//城市ID
					String inputJson = JSONObject.fromObject(webserviceInput).toString();
					webserviceInput = null;
					map.put("postUrl", UrlCache.POST_URL_PUBLIC);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_WEBSERVICE));//webservice
					map.put(ParseCoreReceiveXml.PARAMETER, inputJson);
				}
			}
			//公缴缴费项目明细查询
			if(application.equals(ParseCoreReceiveXml.application_GetPublicUtilitiesItemDetail)){
				if(i==0){
					HashMap<String,String> webserviceInput = new HashMap<String,String>();
					webserviceInput.put("TRADE_CODE", "GJ0005");// 缴费项目明细查询
					webserviceInput.put("TRN_TX_DATE", Format.formatDate());
					webserviceInput.put("TRN_TX_TIME", Format.formatTime());
					webserviceInput.put("TRADE_KEY", Format.getLocalTrmSeqNum());// 20位交易流水号
					webserviceInput.put("TX_ITEM", map.get(ParseCoreReceiveXml.ITEM_ID));//缴费项目ID
					String inputJson = JSONObject.fromObject(webserviceInput).toString();
					webserviceInput = null;
					map.put("postUrl", UrlCache.POST_URL_PUBLIC);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_WEBSERVICE));//webservice
					map.put(ParseCoreReceiveXml.PARAMETER, inputJson.toString());
				}
			}
			//公缴账单查询
			if(application.equals(ParseCoreReceiveXml.application_QueryPublicUtilities)){
				if(i==0){
					HashMap<String,String> webserviceInput = new HashMap<String,String>();
					webserviceInput.put("TRADE_CODE", "GJ0001");// 公共事业账单查询
					webserviceInput.put("TRN_TX_DATE", Format.formatDate());
					webserviceInput.put("TRN_TX_TIME", Format.formatTime());
					webserviceInput.put("TRADE_KEY", Format.getLocalTrmSeqNum());// 20位交易流水号
					webserviceInput.put("TX_ORDER_NO", map.get(ParseCoreReceiveXml.UUID));//查询流水号
					webserviceInput.put("TX_BILL_KEY", map.get(ParseCoreReceiveXml.BILL_KEY));//机表号
					webserviceInput.put("TX_COMPANY_ID", map.get(ParseCoreReceiveXml.COMPANY_ID));//收费公司ID
					webserviceInput.put("TX_FIELD_SET", map.get(ParseCoreReceiveXml.FIELD_SET));//域集
					String inputJson = JSONObject.fromObject(webserviceInput).toString();
					webserviceInput = null;
					map.put("postUrl", UrlCache.POST_URL_PUBLIC);
					map.put("invokeType", String.valueOf(UrlCache.INVOKE_WEBSERVICE));//webservice
					map.put(ParseCoreReceiveXml.PARAMETER, inputJson.toString());
				}
			}
		}
		if (i > 0) {
			log.error(map.get(ParseCoreReceiveXml.TRANSDATE)+
					map.get(ParseCoreReceiveXml.TRANSTIME)+map.get(ParseCoreReceiveXml.TRANSLOGNO)+
					"本次请求参数缺失："+sb.substring(0, sb.length() - 1).toString());
			// 校验失败 缺少必填参数
			resultMap.put("respCode", SystemCode.MISSING_ARGUMENT.getCode());
			resultMap.put("respDesc", sb.substring(0, sb.length() - 1).toString());
		}else{
			long systemTime = System.currentTimeMillis();
			String time = map.get(ParseCoreReceiveXml.TRANSDATE)+map.get(ParseCoreReceiveXml.TRANSTIME);
			// 客户端与前置系统时间相差10分钟
			if(Math.abs(Format.StrToTime(time).getTime()-systemTime)>=600000){
				log.error("客户端系统时间与服务端系统时间不允许相差10分钟以上："+map.get(ParseCoreReceiveXml.TRANSDATE)+
						map.get(ParseCoreReceiveXml.TRANSTIME)+map.get(ParseCoreReceiveXml.TRANSLOGNO));
				resultMap.put("respCode", SystemCode.REQ_EXCEPTION.getCode());
				resultMap.put("respDesc", SystemCode.REQ_EXCEPTION.getDesc());
			}
		}
		return resultMap;
		
	}
}
