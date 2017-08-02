package com.jfpay.preposing.support;

import java.util.HashMap;

import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class ReturnMapCreateTool {

	/**
	 * 生成本地错误信息map
	 * 
	 * @param reqMap
	 * @return
	 */
	public static HashMap<String,String> createMapError(HashMap<String,String> map){
//		HashMap<String,String> errorMap = new HashMap<String,String>();
//		// 获取前置--〉客户端的业务域信息
//		List<String> transformRspLs = TxServiceHelper.getFieldTransformDef(map
//				.get(ParseCoreReceiveXml.APPLICATION),
//				TuxedoConst.PREPOSE_TO_CLIENT_TRANSFORM, Boolean.valueOf(map
//						.get("cacheFlag")));
//		// 获取前置--〉客户端的统一域信息([version]\[osType]\[mobileSerialNum]\[userIP])
//		List<String> unifiedRspLs = TxServiceHelper.getFieldTransformDef(
//				"999999", TuxedoConst.PREPOSE_TO_CLIENT_TRANSFORM, Boolean
//						.valueOf(map.get("cacheFlag")));
//		if (unifiedRspLs != null) {
//			// 前置-->客户端之间统一域转换
//			for (String ud : unifiedRspLs) {
//				String[] trans = ud.split("#");
//					errorMap.put(trans[1], map.get(trans[1]));
//				}
//		}
//		if (transformRspLs != null) {
//			// 前置-->客户端之间业务域转换
//			for (String tf : transformRspLs) {
//				String[] trans = tf.split("#");
//					errorMap.put(trans[1], map.get(trans[1]));
//				}
//		}
//		errorMap.put("application",map.get(ParseCoreReceiveXml.APPLICATION).split("[.]")[0]+".Rsp");
//		return errorMap;
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
