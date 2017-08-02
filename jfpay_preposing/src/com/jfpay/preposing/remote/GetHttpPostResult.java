package com.jfpay.preposing.remote;

import java.util.HashMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.utils.HttpUtil;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * HTTP调用
 * 
 * @author linser
 * 
 */
public class GetHttpPostResult {

	static Logger log = Logger.getLogger(GetHttpPostResult.class);

	public static HashMap<String, String> getResult(HashMap<String, String> map, HashMap<String, String> resultMap) throws Exception {
		String applicationToSYSTEM = (map.get(ParseCoreReceiveXml.APPLICATION).split("[.]"))[0].toString();
		String postUrl = map.get("postUrl") + applicationToSYSTEM + ".do";
		String postData = map.get(ParseCoreReceiveXml.PARAMETER);
		log.info("postUrl:" + postUrl + " postData:" + postData);
		String returnMsg = HttpUtil.send(postUrl, postData);
		resultMap.put("application", applicationToSYSTEM + ".Rsp");
		resultMap.put("version", map.get(ParseCoreReceiveXml.VERSION));
		resultMap.put("mobileSerialNum", map.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM));
		resultMap.put("appUser", map.get(ParseCoreReceiveXml.APP_USER));
		resultMap.put("clientType", map.get(ParseCoreReceiveXml.CLIENT_TYPE));
		resultMap.put("osType", map.get(ParseCoreReceiveXml.OS_TYPE));
		resultMap.put("transDate", map.get(ParseCoreReceiveXml.TRANSDATE));
		resultMap.put("transTime", map.get(ParseCoreReceiveXml.TRANSTIME));
		resultMap.put("transLogNo", map.get(ParseCoreReceiveXml.TRANSLOGNO));
		if (map.get("postUrl").equals(UrlCache.POST_URL_NOTE)) { // 如果是查询操作
			if (map.get("requestType").equals("search")) {
				resultMap.put("data", returnMsg);
				resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
				resultMap.put("respCode", "0000");
				resultMap.put("respDesc", "成功");
			}// 新的查询报文格式
			else if (map.get("requestType").equals("newSearch")) {
				JSONObject jsonObj = JSONObject.fromObject(returnMsg);
				JSONObject result = (JSONObject) jsonObj.get("result");
				resultMap.put("respCode", result.getString("resultCode"));
				resultMap.put("respDesc", result.getString("message"));
				resultMap.put("dataType", ParseCoreReceiveXml.JSON_DATA_TYPE);
				jsonObj.remove("result");
				resultMap.put("data", jsonObj.toString());
			} else {
				JSONObject jsonObj = JSONObject.fromObject(returnMsg);
				JSONObject result = (JSONObject) jsonObj.get("result");
				if (result.getString("resultCode").equals("0001")) {
					resultMap.put("respCode", SystemCode.TRANSACTION_FAILED.getCode());
					resultMap.put("respDesc", SystemCode.TRANSACTION_FAILED.getDesc());
				} else {
					resultMap.put("respCode", result.getString("resultCode"));
					resultMap.put("respDesc", result.getString("message"));
				}
				// 如果是用户身份照片上传接口，则需要返回用户状态
				if (map.get(ParseCoreReceiveXml.APPLICATION).equals(ParseCoreReceiveXml.application_UserIdentityPicUpload)) {
					JSONObject summary = (JSONObject) jsonObj.get("summary");
					resultMap.put("authenFlag", summary.getString("authenFlag"));
				}
			}
		} 
		return resultMap;
	}

}
