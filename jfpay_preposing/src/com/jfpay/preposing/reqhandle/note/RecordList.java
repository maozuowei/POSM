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
 * 获取交易纪录列表
 * 
 * @author LIWEI
 * 
 */
public class RecordList extends ClientReqHandle implements IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + applicationToSYSTEM + ".do");
		String parameter = ParseCoreReceiveXml.APP_USER + "=" + map.get(ParseCoreReceiveXml.APP_USER) +"&"+ ParseCoreReceiveXml.MOBILE_NO + "=" + map.get(ParseCoreReceiveXml.MOBILE_NO) + "&" + ParseCoreReceiveXml.FIRST_MSG_ID + "="
				+ map.get(ParseCoreReceiveXml.FIRST_MSG_ID) + "&" + ParseCoreReceiveXml.LAST_MSG_ID + "="
				+ map.get(ParseCoreReceiveXml.LAST_MSG_ID) + "&" + ParseCoreReceiveXml.MSG_SIZE + "="
				+ map.get(ParseCoreReceiveXml.MSG_SIZE) + "&" + ParseCoreReceiveXml.FILTER + "="
				+ map.get(ParseCoreReceiveXml.FILTER)+ "&" + ParseCoreReceiveXml.REQUEST_TYPE + "="
				+ map.get(ParseCoreReceiveXml.REQUEST_TYPE);
		commu.setMessage(parameter);
		return commu;
	}
	
	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		super.handleReturnMessage(map);
		JSONObject jsonObj = JSONObject.fromObject(returnMessage);
		JSONObject result = (JSONObject) jsonObj.get("result");
		jsonObj.remove("result");
		resultMap.put("respCode", result.getString("resultCode"));
		resultMap.put("respDesc", result.getString("message"));
		resultMap.put("data", jsonObj.toString());
		resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);

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
		if (map.get(ParseCoreReceiveXml.APP_USER) == null || map.get(ParseCoreReceiveXml.APP_USER).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.APP_USER + ",");
		}
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null || map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}
		if (map.get(ParseCoreReceiveXml.FILTER) == null || map.get(ParseCoreReceiveXml.FILTER).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.FILTER + ",");
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
		//localResp.setRespCode(SystemCode.SUCCESS.getCode());
		//localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
		return localResp;
	}

}
