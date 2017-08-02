package com.jfpay.preposing.reqhandle.publicutilities;

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
 * 缴费项目详情
 * @author linser
 *
 */
public class GetPublicUtilitiesItemDetail extends ClientReqHandle implements
		IClientReqHandleService {

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_WEBSERVICE);
		commu.setInvokeUrl(UrlCache.POST_URL_PUBLIC);
		HashMap<String,String> webserviceInput = new HashMap<String,String>();
		webserviceInput.put("TRADE_CODE", "GJ0005");// 公共事业缴费项目详情
		webserviceInput.put("TRN_TX_DATE", Format.formatDate());
		webserviceInput.put("TRN_TX_TIME", Format.formatTime());
		webserviceInput.put("TRADE_KEY", Format.getLocalTrmSeqNum());// 20位交易流水号
		webserviceInput.put("TX_ITEM", map.get(ParseCoreReceiveXml.ITEM_ID));//缴费项目ID
		String inputJson = JSONObject.fromObject(webserviceInput).toString();
		commu.setMessage(inputJson);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(
			HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		super.handleReturnMessage(map);
		JSONObject jsonObj = JSONObject.fromObject(returnMessage);	
		JSONObject resultJson = new JSONObject();
		JSONObject recordObj = JSONObject.fromObject(jsonObj.get("TX_RECORD_SET"));

		recordObj.put("fieldsMsg", recordObj.get("fieldsMsg"));
		resultJson.put("resultBean", recordObj);
		resultMap.put("respCode", jsonObj.getString("MSG_CODE"));
		resultMap.put("respDesc", jsonObj.getString("MSG_TEXT"));
		resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
		resultMap.put("data", resultJson.toString());
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
		if(map.get(ParseCoreReceiveXml.ITEM_ID) == null
				||map.get(ParseCoreReceiveXml.ITEM_ID).trim()
				.length() == 0){
			i++;
			sb.append(ParseCoreReceiveXml.ITEM_ID+",");
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
		localResp.setRespCode(SystemCode.SUCCESS.getCode());
		localResp.setRespDesc(SystemCode.SUCCESS.getDesc());
		return localResp;
	}

}
