package com.jfpay.preposing.reqhandle;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class CardPayAutoWithDraw extends ClientReqHandle implements IClientReqHandleService {

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
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null || map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}
		if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null || map.get(ParseCoreReceiveXml.MERCHANT_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.PRODUCT_ID) == null || map.get(ParseCoreReceiveXml.PRODUCT_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.PRODUCT_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.ORDER_ID) == null || map.get(ParseCoreReceiveXml.ORDER_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.ORDER_AMT) == null || map.get(ParseCoreReceiveXml.ORDER_AMT).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ORDER_AMT + ",");
		}
		if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null || map.get(ParseCoreReceiveXml.CARD_PASSWD).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
		}
		if (map.get(ParseCoreReceiveXml.LONGITUDE) == null || map.get(ParseCoreReceiveXml.LONGITUDE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.LONGITUDE + ",");
		}
		if (map.get(ParseCoreReceiveXml.LATITUDE) == null || map.get(ParseCoreReceiveXml.LATITUDE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.LATITUDE + ",");
		}
		if (map.get(ParseCoreReceiveXml.CARD_INDEX) == null || map.get(ParseCoreReceiveXml.CARD_INDEX).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CARD_INDEX + ",");
		}
		if (map.get(ParseCoreReceiveXml.CITYNAME) == null || map.get(ParseCoreReceiveXml.CITYNAME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CITYNAME + ",");
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
		// 校验登录session信息
		localResp = checkLoginToken(map);
		return localResp;
	}

}
