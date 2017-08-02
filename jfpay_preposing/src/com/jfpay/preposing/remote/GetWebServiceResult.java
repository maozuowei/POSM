package com.jfpay.preposing.remote;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.jfpay.preposing.utils.WebServiceUtil;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * webservice调用
 * @author linser
 *
 */
public class GetWebServiceResult {

	
	static Logger log = Logger.getLogger(GetWebServiceResult.class);
			
	public static HashMap<String,String> getResult(HashMap<String,String> map,HashMap<String,String> resultMap) throws RemoteException, ServiceException{
		
		String applicationToSYSTEM = (map
				.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0]
				.toString();
		String postUrl = map.get("postUrl") + applicationToSYSTEM + ".do";
		String postData = map.get(ParseCoreReceiveXml.PARAMETER);
		log.info("postUrl:" + postUrl + " postData:" + postData);
		String returnMsg = WebServiceUtil.invokeWebService(postUrl,postData);
		resultMap.put("application", applicationToSYSTEM+ ".Rsp");
		resultMap.put("version", map
				.get(ParseCoreReceiveXml.VERSION));
		resultMap.put("mobileSerialNum", map
				.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM));
		resultMap.put("appUser", map.get(ParseCoreReceiveXml.APP_USER));
		resultMap.put("clientType", map.get(ParseCoreReceiveXml.CLIENT_TYPE));
		resultMap.put("osType", map
				.get(ParseCoreReceiveXml.OS_TYPE));
		resultMap.put("transDate", map
				.get(ParseCoreReceiveXml.TRANSDATE));
		resultMap.put("transTime", map
				.get(ParseCoreReceiveXml.TRANSTIME));
		resultMap.put("transLogNo", map
				.get(ParseCoreReceiveXml.TRANSLOGNO));
		JSONObject jsonObj = JSONObject.fromObject(returnMsg);	
		JSONObject resultJson = new JSONObject();
		resultJson.put("resultBean", jsonObj.getString("TX_RECORD_SET"));
		resultMap.put("respCode", jsonObj.getString("MSG_CODE"));
		resultMap.put("respDesc", jsonObj.getString("MSG_TEXT"));
		resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
		resultMap.put("data", resultJson.toString());
		return resultMap;
		
	}
}
