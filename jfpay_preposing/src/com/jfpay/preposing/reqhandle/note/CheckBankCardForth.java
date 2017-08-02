package com.jfpay.preposing.reqhandle.note;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class CheckBankCardForth extends ClientReqHandle 
		implements  IClientReqHandleService{
	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + applicationToSYSTEM + ".do");
		String parameter = ParseCoreReceiveXml.REAL_NAME + "=" + map.get(ParseCoreReceiveXml.REAL_NAME)+ "&" + 
						ParseCoreReceiveXml.CERT_PID + "=" + map.get(ParseCoreReceiveXml.CERT_PID) + "&" +
						ParseCoreReceiveXml.CARD_NO + "=" + map.get(ParseCoreReceiveXml.CARD_NO)+"&"+
						ParseCoreReceiveXml.CARD_PHONE_NO + "=" + map.get(ParseCoreReceiveXml.CARD_PHONE_NO);
		commu.setMessage(parameter);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		super.handleReturnMessage(map);
		resultMap.put("data", returnMessage.toString());
		resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
		resultMap.put("respCode", SystemCode.SUCCESS.getCode());
		resultMap.put("respDesc", SystemCode.SUCCESS.getDesc());
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
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null || 
				map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}
		if (map.get(ParseCoreReceiveXml.REAL_NAME) == null || 
				map.get(ParseCoreReceiveXml.REAL_NAME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.REAL_NAME + ",");
		}
		if (map.get(ParseCoreReceiveXml.CARD_NO) == null || 
				map.get(ParseCoreReceiveXml.CARD_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CARD_NO + ",");
		}
		if (map.get(ParseCoreReceiveXml.CERT_PID) == null || 
				map.get(ParseCoreReceiveXml.CERT_PID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CERT_PID + ",");
		}
		if (map.get(ParseCoreReceiveXml.CARD_PHONE_NO) == null || 
				map.get(ParseCoreReceiveXml.CARD_PHONE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CARD_PHONE_NO + ",");
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
