package com.jfpay.preposing.reqhandle.note;

import java.util.HashMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class GetInAndOutDetail extends ClientReqHandle implements IClientReqHandleService {
	private static Logger log = Logger.getLogger(GetInAndOutDetail.class);

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + applicationToSYSTEM + ".do");
		String parameter = ParseCoreReceiveXml.MOBILE_NO + "=" + map.get(ParseCoreReceiveXml.MOBILE_NO) + "&" + ParseCoreReceiveXml.SEARCH_DATE + "=" + map.get(ParseCoreReceiveXml.SEARCH_DATE) + "&" + ParseCoreReceiveXml.TRANS_TYPE + "=" + 
				map.get(ParseCoreReceiveXml.TRANS_TYPE) + "&" + ParseCoreReceiveXml.OFFSET + "=" + map.get(ParseCoreReceiveXml.OFFSET) + "&" + 
				ParseCoreReceiveXml.TRANS_NO + "=" + map.get(ParseCoreReceiveXml.TRANS_NO);
		commu.setMessage(parameter);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		super.handleReturnMessage(map);
		JSONObject jsonObj = JSONObject.fromObject(returnMessage);
		JSONObject result = (JSONObject) jsonObj.get("result");
		resultMap.put("respCode", result.getString("resultCode"));
		resultMap.put("respDesc", result.getString("message"));
		resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
		jsonObj.remove("result");
		resultMap.put("data", jsonObj.toString());
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
		if (map.get(ParseCoreReceiveXml.SEARCH_DATE) == null || map.get(ParseCoreReceiveXml.SEARCH_DATE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.SEARCH_DATE + ",");
		}
		if (map.get(ParseCoreReceiveXml.TRANS_TYPE) == null || map.get(ParseCoreReceiveXml.TRANS_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.TRANS_TYPE + ",");
		}
		if (map.get(ParseCoreReceiveXml.OFFSET) == null || map.get(ParseCoreReceiveXml.OFFSET).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.OFFSET + ",");
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
