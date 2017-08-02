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
 * 企业账户资料完善
 * @author Owner
 *
 */
public class EnterpriseUserUpdateInfo extends ClientReqHandle implements
		IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION)
				.split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + applicationToSYSTEM
				+ ".do");
		String parameter = ParseCoreReceiveXml.MOBILE_NO + "="
				+ map.get(ParseCoreReceiveXml.MOBILE_NO) + "&"
				+ ParseCoreReceiveXml.ENTER_NAME + "="
				+ map.get(ParseCoreReceiveXml.ENTER_NAME) + "&"
				+ ParseCoreReceiveXml.ENTER_CERT_TYPE + "="
				+ map.get(ParseCoreReceiveXml.ENTER_CERT_TYPE) + "&"
				+ ParseCoreReceiveXml.ENTER_CERT_PID + "="
				+ map.get(ParseCoreReceiveXml.ENTER_CERT_PID) + "&"
				+ ParseCoreReceiveXml.ENTER_ADDRESS + "="
				+ map.get(ParseCoreReceiveXml.ENTER_ADDRESS) + "&"
				+ ParseCoreReceiveXml.LEGAL_NAME + "="
				+ map.get(ParseCoreReceiveXml.LEGAL_NAME) + "&"
				+ ParseCoreReceiveXml.LEGAL_CERT_TYPE + "="
				+ map.get(ParseCoreReceiveXml.LEGAL_CERT_TYPE) + "&"
				+ ParseCoreReceiveXml.LEGAL_CERT_PID + "="
				+ map.get(ParseCoreReceiveXml.LEGAL_CERT_PID) + "&"
				+ ParseCoreReceiveXml.CONTACT_NAME + "="
				+ map.get(ParseCoreReceiveXml.CONTACT_NAME) + "&"
				+ ParseCoreReceiveXml.CONTACT_PHONE + "="
				+ map.get(ParseCoreReceiveXml.CONTACT_PHONE) + "&"
				+ ParseCoreReceiveXml.ENTER_EMAIL + "="
				+ map.get(ParseCoreReceiveXml.ENTER_EMAIL) + "&"
				+ ParseCoreReceiveXml.TRANSDATE + "="
				+ map.get(ParseCoreReceiveXml.TRANSDATE) + "&"
				+ ParseCoreReceiveXml.TRANSTIME + "="
				+ map.get(ParseCoreReceiveXml.TRANSTIME);
		commu.setMessage(parameter);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(
			HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		super.handleReturnMessage(map);
		JSONObject jsonObj = JSONObject.fromObject(returnMessage);
		JSONObject result = (JSONObject)jsonObj.get("result");
		if(result.getString("resultCode").equals("0001")){
			resultMap.put("respCode", 
			        SystemCode.TRANSACTION_FAILED.getCode());
			resultMap.put("respDesc", 
			        SystemCode.TRANSACTION_FAILED.getDesc());
		}else{
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
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null
				|| map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}
		if (map.get(ParseCoreReceiveXml.ENTER_NAME) == null
				|| map.get(ParseCoreReceiveXml.ENTER_NAME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ENTER_NAME + ",");
		}
		if (map.get(ParseCoreReceiveXml.ENTER_CERT_TYPE) == null
				|| map.get(ParseCoreReceiveXml.ENTER_CERT_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ENTER_CERT_TYPE + ",");
		}
		if (map.get(ParseCoreReceiveXml.ENTER_CERT_PID) == null
				|| map.get(ParseCoreReceiveXml.ENTER_CERT_PID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ENTER_CERT_PID + ",");
		}
		if (map.get(ParseCoreReceiveXml.ENTER_ADDRESS) == null
				|| map.get(ParseCoreReceiveXml.ENTER_ADDRESS).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ENTER_ADDRESS + ",");
		}
		if (map.get(ParseCoreReceiveXml.LEGAL_NAME) == null
				|| map.get(ParseCoreReceiveXml.LEGAL_NAME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.LEGAL_NAME + ",");
		}
		if (map.get(ParseCoreReceiveXml.LEGAL_CERT_TYPE) == null
				|| map.get(ParseCoreReceiveXml.LEGAL_CERT_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.LEGAL_CERT_TYPE + ",");
		}
		if (map.get(ParseCoreReceiveXml.LEGAL_CERT_PID) == null
				|| map.get(ParseCoreReceiveXml.LEGAL_CERT_PID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.LEGAL_CERT_PID + ",");
		}
		if (map.get(ParseCoreReceiveXml.CONTACT_NAME) == null
				|| map.get(ParseCoreReceiveXml.CONTACT_NAME).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CONTACT_NAME + ",");
		}
		if (map.get(ParseCoreReceiveXml.CONTACT_PHONE) == null
				|| map.get(ParseCoreReceiveXml.CONTACT_PHONE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CONTACT_PHONE + ",");
		}
		if (map.get(ParseCoreReceiveXml.ENTER_EMAIL) == null
				|| map.get(ParseCoreReceiveXml.ENTER_EMAIL).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ENTER_EMAIL + ",");
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
