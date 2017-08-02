package com.jfpay.preposing.reqhandle.note;

import java.util.HashMap;

import net.sf.json.JSONObject;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * 订单手写签名信息上传
 * 
 * @author Owner
 * 
 */
public class UserSignatureUpload extends ClientReqHandle implements IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + applicationToSYSTEM + ".do");
		String parameter = ParseCoreReceiveXml.LONGITUDE + "=" + map.get(ParseCoreReceiveXml.LONGITUDE) + "&" + ParseCoreReceiveXml.LATITUDE + "="
				+ map.get(ParseCoreReceiveXml.LATITUDE) + "&" + ParseCoreReceiveXml.MERCHANT_ID + "=" + map.get(ParseCoreReceiveXml.MERCHANT_ID) + "&"
				+ ParseCoreReceiveXml.ORDER_ID + "=" + map.get(ParseCoreReceiveXml.ORDER_ID) + "&" + ParseCoreReceiveXml.SIGN_PIC_ASCII + "="
				+ map.get(ParseCoreReceiveXml.SIGN_PIC_ASCII) + "&" + ParseCoreReceiveXml.PIC_SIGN + "=" + map.get(ParseCoreReceiveXml.PIC_SIGN) + "&"
				+ ParseCoreReceiveXml.TRANSDATE + "=" + map.get(ParseCoreReceiveXml.TRANSDATE) + "&" + ParseCoreReceiveXml.TRANSTIME + "=" + map.get(ParseCoreReceiveXml.TRANSTIME);
		commu.setMessage(parameter);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		super.handleReturnMessage(map);
		JSONObject jsonObj = JSONObject.fromObject(returnMessage);
		JSONObject result = (JSONObject) jsonObj.get("result");
		if (result.getString("resultCode").equals("0001")) {
			resultMap.put("respCode", SystemCode.TRANSACTION_FAILED.getCode());
			resultMap.put("respDesc", SystemCode.TRANSACTION_FAILED.getDesc());
		} else {
			resultMap.put("respCode", result.getString("resultCode"));
			resultMap.put("respDesc", result.getString("message"));
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
		if (map.get(ParseCoreReceiveXml.LONGITUDE) == null || map.get(ParseCoreReceiveXml.LONGITUDE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.LONGITUDE + ",");
		}
		if (map.get(ParseCoreReceiveXml.LATITUDE) == null || map.get(ParseCoreReceiveXml.LATITUDE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.LATITUDE + ",");
		}
		if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null || map.get(ParseCoreReceiveXml.MERCHANT_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.ORDER_ID) == null || map.get(ParseCoreReceiveXml.ORDER_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ORDER_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.SIGN_PIC_ASCII) == null || map.get(ParseCoreReceiveXml.SIGN_PIC_ASCII).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.SIGN_PIC_ASCII + ",");
		}
		if (map.get(ParseCoreReceiveXml.PIC_SIGN) == null || map.get(ParseCoreReceiveXml.PIC_SIGN).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.PIC_SIGN + ",");
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
		// localResp = checkLoginToken(map);
		localResp.setRespCode(SystemCode.SUCCESS.getCode());
		localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
		return localResp;
	}

}
