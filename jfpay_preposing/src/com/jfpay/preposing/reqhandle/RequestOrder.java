package com.jfpay.preposing.reqhandle;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.HttpUtil;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * 订单请求
 * 
 * @author linser
 * 
 */
public class RequestOrder extends ClientReqHandle implements
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

		if (returnMessage instanceof HashMap) {
			resultMap = (HashMap) returnMessage;
			if (resultMap.get(ParseCoreReceiveXml.TX_COMMODITYIDS) != null
					&& !resultMap.get(ParseCoreReceiveXml.TX_COMMODITYIDS)
							.equals("")
					&& !resultMap.get(ParseCoreReceiveXml.TX_COMMODITYIDS)
							.equalsIgnoreCase("null")
					&& resultMap.get("respCode").equals(
							SystemCode.SUCCESS.getCode())) {
				// 订单信息入库
				resultMap = AddPayOrderCommo(resultMap);
			}
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
		if (map.get(ParseCoreReceiveXml.MERCHANT_ID) == null
				|| map.get(ParseCoreReceiveXml.MERCHANT_ID).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MERCHANT_ID + ",");
		}
		if (map.get(ParseCoreReceiveXml.ORDER_AMT) == null
				|| map.get(ParseCoreReceiveXml.ORDER_AMT).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.ORDER_AMT + ",");
		}
		if (map.get(ParseCoreReceiveXml.CUSTOMER_NO) == null
				|| map.get(ParseCoreReceiveXml.CUSTOMER_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.CUSTOMER_NO + ",");
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
		// 校验登录session信息
		localResp = checkLoginToken(map);
		return localResp;
	}

	/**
	 * 订单信息入库
	 * 
	 * @param map
	 */
	private static HashMap<String, String> AddPayOrderCommo(
			HashMap<String, String> map) {
		HashMap<String, String> rlt = new HashMap<String, String>();
		rlt.putAll(map);
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		commu.setInvokeUrl(UrlCache.POST_URL_NOTE + "PayOrderCommo.do");
		String parameter = ParseCoreReceiveXml.ORDERID + "="
				+ map.get(ParseCoreReceiveXml.ORDERID) + "&"
				+ ParseCoreReceiveXml.ORDER_AMT + "="
				+ map.get(ParseCoreReceiveXml.ORDER_AMT) + "&"
				+ ParseCoreReceiveXml.CUSTOMER_NO + "="
				+ map.get(ParseCoreReceiveXml.CUSTOMER_NO) + "&"
				+ ParseCoreReceiveXml.REAL_AMT + "="
				+ map.get(ParseCoreReceiveXml.REAL_AMT) + "&"
				+ ParseCoreReceiveXml.TX_COMMODITYIDS + "="
				+ map.get(ParseCoreReceiveXml.TX_COMMODITYIDS);
		commu.setMessage(parameter);

		try {
			Object returnMsg = HttpUtil.send(commu.getInvokeUrl(),
					commu.getMessage());
			JSONObject js = JSONObject.fromObject(returnMsg);
			JSONObject result = js.getJSONObject("result");
			if (result.getString("resultCode").equals("0000")) {
				rlt.put("respCode", SystemCode.SUCCESS.getCode());
				rlt.put("respDesc", SystemCode.SUCCESS.getDesc());
				log.info("订单信息入库成功");
			} else {
				rlt.put("respCode", SystemCode.TRANSACTION_FAILED.getCode());
				rlt.put("respDesc", SystemCode.TRANSACTION_FAILED.getDesc());
				log.error("订单信息入库失败!");
			}
		} catch (Exception e) {
			log.error("调用服务发生异常: " + e.getMessage());
			rlt.put("respCode", SystemCode.CALL_HOST_EXCEPTION.getCode());
			rlt.put("respDesc", SystemCode.CALL_HOST_EXCEPTION.getDesc());
		}
		return rlt;
	}

	// public static void main(String[] args) {
	// String requestXmlWithoutDecode =
	// "<QtPay ersion=\"1.4.1\" osType=\"iOS8.2\" clientType=\"01\" appUser=\"bmkushua\" userIP=\"192.168.34.101\" mobileSerialNum=\"0B2E99C8EFE7C233C1B0D49E4B4BB67000000000\" phone=\"13122223333\" token=\"B9611ABCB2423B307D06CE83F48E9632\"><respCode>0000</respCode><orderDesc>13122223333</orderDesc><respDesc>交易失败，请稍后重试</respDesc><realAmt>000000001000</realAmt><productId>0000000000</productId><commodityIDs>157,158,159</commodityIDs><sign>82E700449264E3D1D33EF68864809502</sign><transLogNo>000812</transLogNo><transDate>20150403</transDate><payTool>01</payTool><transTime>153312</transTime><merchantName>tr</merchantName><merchantId>0005000001</merchantId><orderId>2015040343506393</orderId><orderAmt>1000</orderAmt><mobileNo>13122223333</mobileNo></QtPay>";
	// HashMap<String, String> map = new HashMap<String, String>();
	// try {
	// map = ParseCoreReceiveXml.parseCoreXml(requestXmlWithoutDecode);
	// AddPayOrderCommo(map);
	// System.out.println(1111);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
