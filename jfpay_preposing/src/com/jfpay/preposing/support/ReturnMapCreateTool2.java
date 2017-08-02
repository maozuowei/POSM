package com.jfpay.preposing.support;

import java.util.HashMap;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class ReturnMapCreateTool2 {

	/**
	 * 生成本地错误信息map
	 * 
	 * @param reqMap
	 * @return
	 */
	public static HashMap<String,String> createMapError(HashMap<String,String> map){
		
		map.put("application",map.get(ParseCoreReceiveXml.APPLICATION).split("[.]")[0]+".Rsp");
		return map;
	}
	
	public static HashMap<String,String> createMapSuccess(HashMap<String,String> map){
		
		map.put("application",map.get(ParseCoreReceiveXml.APPLICATION).split("[.]")[0]+".Rsp");
		map.put("respCode", "0000");
		map.put("respDesc", "成功");
		return map;
	}
	
//	public static void main(String[] args){
//		
//		Map<String, String> reqMap = new HashMap<String, String>();
//		
//		reqMap.put(ParseCoreReceiveXml.APPLICATION, null);
//		boolean s = (reqMap.get(ParseCoreReceiveXml.APPLICATION)==null)?true:false;
//		System.out.print(s);
//	}
}
