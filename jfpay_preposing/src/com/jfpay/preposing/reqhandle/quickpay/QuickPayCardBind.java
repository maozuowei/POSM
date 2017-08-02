package com.jfpay.preposing.reqhandle.quickpay;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;
/**
 * 快捷支付 绑卡
 * @author WJG
 *
 */
public class QuickPayCardBind extends ClientReqHandle implements
		IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_TPCALL);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(
			HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		if(returnMessage instanceof HashMap){
			resultMap = (HashMap)returnMessage;
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
		//持卡人银行卡卡号
		if (map.get(ParseCoreReceiveXml.CARD_NO) == null || map.get(ParseCoreReceiveXml.CARD_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CARD_NO + ",");
		}
		//userid
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
				|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim()
						.length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}
		//UUID
		if (map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM) == null
				|| map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM).trim()
				.length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_SERIAL_NUM + ",");
		}
	/*	//联行号
		if (map.get(ParseCoreReceiveXml.CNAPS) == null
				|| map.get(ParseCoreReceiveXml.CNAPS).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CNAPS + ",");
		}
		//省ID
		if (map.get(ParseCoreReceiveXml.BANK_PROVINCE_ID) == null
				|| map.get(ParseCoreReceiveXml.BANK_PROVINCE_ID).trim()
						.length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.BANK_PROVINCE_ID + ",");
		}
		//城市名称
		if (map.get(ParseCoreReceiveXml.BANK_CITY) == null
				|| map.get(ParseCoreReceiveXml.BANK_CITY).trim()
						.length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.BANK_CITY + ",");
		}
		//银行名称
		if (map.get(ParseCoreReceiveXml.BANK_NAME) == null
				|| map.get(ParseCoreReceiveXml.BANK_NAME).trim()
						.length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.BANK_NAME + ",");
		}*/
		//加密参数
		if (map.get(ParseCoreReceiveXml.EXP_INFO) == null
				|| map.get(ParseCoreReceiveXml.EXP_INFO).trim()
				.length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.EXP_INFO + ",");
		}
		if (i > 0) {
			log.error(map.get(ParseCoreReceiveXml.TRANSDATE)
					+ map.get(ParseCoreReceiveXml.TRANSTIME)
					+ map.get(ParseCoreReceiveXml.TRANSLOGNO) + "本次请求参数缺失："
					+ sb.substring(0, sb.length() - 1).toString());
			// 校验失败 缺少必填参数
			localResp.setRespCode(SystemCode.MISSING_ARGUMENT.getCode());
			localResp.setRespDesc(sb.substring(0, sb.length() - 1).toString());
			sb = null;
			return localResp;
		}
		sb = null;
		//校验登录session信息
		localResp = checkLoginToken(map);
		return localResp;
	}

}
