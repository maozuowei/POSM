package com.jfpay.preposing.reqhandle.note;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * 个人用户信息查询
 * 
 * @author linser
 * 
 */
public class UserInfoQuery extends ClientReqHandle implements IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + applicationToSYSTEM + ".do");
		String parameter = ParseCoreReceiveXml.APP_USER + "=" + map.get(ParseCoreReceiveXml.APP_USER) + "&" + ParseCoreReceiveXml.MOBILE_NO + "="
				+ map.get(ParseCoreReceiveXml.MOBILE_NO) + "&" + ParseCoreReceiveXml.CUSTOMER_ID + "=" + map.get(ParseCoreReceiveXml.CUSTOMER_ID) + "&"
				+ ParseCoreReceiveXml.TRANS_TYPE + "=" + (map.containsKey(ParseCoreReceiveXml.TRANS_TYPE) ? map.get(ParseCoreReceiveXml.TRANS_TYPE) : "00");
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
		String version = map.get(ParseCoreReceiveXml.VERSION);
		if(version.equals("2.0.6") || version.equals("2.0.7") || version.equals("2.1.0") || version.equals("2.1.4")){
			localResp.setRespCode(SystemCode.SUCCESS.getCode());
			localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
		}else{
			localResp = checkLoginToken(map);
		}
		return localResp;
	}

}
