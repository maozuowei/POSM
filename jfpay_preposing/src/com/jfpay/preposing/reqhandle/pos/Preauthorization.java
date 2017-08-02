package com.jfpay.preposing.reqhandle.pos;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;
/**
 * 预授权
 * @author linser
 *
 */
public class Preauthorization extends ClientReqHandle implements
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
		if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null || map.get(ParseCoreReceiveXml.MERCHANT_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.PRODUCT_ID) == null || map.get(ParseCoreReceiveXml.PRODUCT_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.PRODUCT_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null || map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}
		if (map.get(ParseCoreReceiveXml.ORDER_ID) == null || map.get(ParseCoreReceiveXml.ORDER_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.CARD_INFO) == null	|| map.get(ParseCoreReceiveXml.CARD_INFO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CARD_INFO + ",");
		}
		if (map.get(ParseCoreReceiveXml.AMOUNT) == null	|| map.get(ParseCoreReceiveXml.AMOUNT).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.AMOUNT + ",");
		}
		if (map.get(ParseCoreReceiveXml.TX_HOST_EXPIRATION_DATE) == null	|| map.get(ParseCoreReceiveXml.TX_HOST_EXPIRATION_DATE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TX_HOST_EXPIRATION_DATE + ",");
		}
		if (map.get(ParseCoreReceiveXml.TX_HOST_IC_CSN) == null	|| map.get(ParseCoreReceiveXml.TX_HOST_IC_CSN).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TX_HOST_IC_CSN + ",");
		}
		if (map.get(ParseCoreReceiveXml.CARD_PASSWD) == null	|| map.get(ParseCoreReceiveXml.CARD_PASSWD).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CARD_PASSWD + ",");
		}
		if (map.get(ParseCoreReceiveXml.TX_HOST_IC_DATA) == null	|| map.get(ParseCoreReceiveXml.TX_HOST_IC_DATA).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TX_HOST_IC_DATA + ",");
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
		localResp.setRespCode(SystemCode.SUCCESS.getCode());
		localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
//		localResp = checkLoginToken(map);
		return localResp;
	}

}
