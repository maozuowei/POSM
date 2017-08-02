package com.jfpay.preposing.reqhandle.note;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class MerchantIdentity extends ClientReqHandle implements IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + applicationToSYSTEM + ".do");
		String parameter = "";
		try {
			parameter = ParseCoreReceiveXml.APP_USER + "=" + map.get(ParseCoreReceiveXml.APP_USER) + "&" + ParseCoreReceiveXml.MOBILE_NO + "="
					+ map.get(ParseCoreReceiveXml.MOBILE_NO) + "&" + ParseCoreReceiveXml.CUSTOMER_ID + "=" + map.get(ParseCoreReceiveXml.CUSTOMER_ID) + "&"
					+ ParseCoreReceiveXml.IMG1 + "=" + URLEncoder.encode(map.get(ParseCoreReceiveXml.IMG1), "UTF-8") + "&" + ParseCoreReceiveXml.IMG2 
					+ "=" + URLEncoder.encode(map.get(ParseCoreReceiveXml.IMG2), "UTF-8") + "&" + ParseCoreReceiveXml.IMG_SIGN1 + "=" + map.get(ParseCoreReceiveXml.IMG_SIGN1)+ "&" 
					+ ParseCoreReceiveXml.IMG_SIGN2 + "=" + map.get(ParseCoreReceiveXml.IMG_SIGN2)+ "&"
					+ ParseCoreReceiveXml.MERCHANT_NAME + "=" + map.get(ParseCoreReceiveXml.MERCHANT_NAME) + "&" + ParseCoreReceiveXml.MERCHANT_ADDRESS + "=" + map.get(ParseCoreReceiveXml.MERCHANT_ADDRESS)+ "&"
					+ ParseCoreReceiveXml.MCC_ID + "=" + map.get(ParseCoreReceiveXml.MCC_ID) + "&" + ParseCoreReceiveXml.BUSINESS_LICENCE + "=" + map.get(ParseCoreReceiveXml.BUSINESS_LICENCE)
					+ "&" + ParseCoreReceiveXml.USER_IP + "=" + map.get(ParseCoreReceiveXml.USER_IP);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		commu.setMessage(parameter);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		super.handleReturnMessage(map);
		resultMap.put("data", returnMessage.toString());
		resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
		resultMap.put("respCode", SystemCode.SUCCESS.getCode());
		resultMap.put("respDesc", SystemCode.SUCCESS.getDesc());
		return resultMap;
	}

	@Override
	public LocalResp verifyMessage(HashMap<String, String> map) {
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
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null || map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}else{
			map.put(ParseCoreReceiveXml.PHONE, map.get(ParseCoreReceiveXml.MOBILE_NO));
		}
		if (map.get(ParseCoreReceiveXml.CUSTOMER_ID) == null || map.get(ParseCoreReceiveXml.CUSTOMER_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CUSTOMER_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.IMG1) == null || map.get(ParseCoreReceiveXml.IMG1).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.IMG1 + ",");
		}
		if (map.get(ParseCoreReceiveXml.IMG2) == null || map.get(ParseCoreReceiveXml.IMG2).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.IMG2 + ",");
		}
		if (map.get(ParseCoreReceiveXml.IMG_SIGN1) == null || map.get(ParseCoreReceiveXml.IMG_SIGN1).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.IMG_SIGN1 + ",");
		}
		if (map.get(ParseCoreReceiveXml.IMG_SIGN2) == null || map.get(ParseCoreReceiveXml.IMG_SIGN2).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.IMG_SIGN2 + ",");
		}
		if (map.get(ParseCoreReceiveXml.MERCHANT_NAME) == null || map.get(ParseCoreReceiveXml.MERCHANT_NAME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MERCHANT_NAME + ",");
		}
		if (map.get(ParseCoreReceiveXml.MERCHANT_ADDRESS) == null || map.get(ParseCoreReceiveXml.MERCHANT_ADDRESS).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MERCHANT_ADDRESS + ",");
		}
		if (map.get(ParseCoreReceiveXml.MCC_ID) == null || map.get(ParseCoreReceiveXml.MCC_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MCC_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.BUSINESS_LICENCE) == null || map.get(ParseCoreReceiveXml.BUSINESS_LICENCE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.BUSINESS_LICENCE + ",");
		}
		if (map.get(ParseCoreReceiveXml.USER_IP) == null || map.get(ParseCoreReceiveXml.USER_IP).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.USER_IP + ",");
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
		localResp.setRespCode(SystemCode.SUCCESS.getCode());
		localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
		// 校验登录session信息
		localResp = checkLoginToken(map);
		return localResp;
	}

}
