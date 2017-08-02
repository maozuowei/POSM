package com.jfpay.preposing.reqhandle.order;

import java.util.HashMap;
import net.sf.json.JSONObject;
import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.Format;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * 查询账户信息
 * 
 * @author linser
 * 
 */
public class QueryAccountInfo extends ClientReqHandle implements IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		commu.setInvokeUrl(UrlCache.POST_URL_ACCOUNT + applicationToSYSTEM + ".do");
		JSONObject reqJson = new JSONObject();
		reqJson.put("application", applicationToSYSTEM);
		reqJson.put("serialID", Format.getLocalTrmSeqNum());
		JSONObject reqBeanJson = new JSONObject();
		reqBeanJson.put("merchantId", map.get(ParseCoreReceiveXml.MERCHANT_ID));
		reqBeanJson.put("chargeAccount", map.get(ParseCoreReceiveXml.CHARGE_ACCOUNT));
		JSONObject json = new JSONObject();
		json.put("Req", reqJson);
		json.put("ReqBean", reqBeanJson);
		commu.setMessage("requestContext=" + json.toString());
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		super.handleReturnMessage(map);
		JSONObject jsonObj = JSONObject.fromObject(returnMessage);
		JSONObject resp = (JSONObject) jsonObj.get("Resp");
		JSONObject respBean = (JSONObject) jsonObj.get("RespBean");
		resultMap.put("respCode", resp.getString("respCode"));
		resultMap.put("respDesc", resp.getString("message"));
		resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
		resultMap.put("data", respBean.toString());
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
		if (map.get(ParseCoreReceiveXml.CHARGE_ACCOUNT) == null || map.get(ParseCoreReceiveXml.CHARGE_ACCOUNT).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CHARGE_ACCOUNT + ",");
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
