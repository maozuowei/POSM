package com.jfpay.preposing.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;
import cn.hnae.tuxedojms.allocate.TxServiceHelper;

public class LocalSignGet {

	public static String getLocalSignStr(String requestXml){
		
		HashMap<String, String> allPara = null;
		try {
			allPara = ParseCoreReceiveXml.parseCoreXml(requestXml);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		allPara.remove(ParseCoreReceiveXml.SIGN);
		//获取相关版本号对应的sign的key值
		String key=TxServiceHelper.getSignKey(allPara.get(ParseCoreReceiveXml.VERSION),true);
		System.out.println("key:"+key);
		List<String> paraList = new ArrayList<String>();
		Iterator<Map.Entry<String, String>> it = allPara.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
			//将值不为空的项进行sign处理
			if(entry.getValue()!=null&&!entry.getValue().equals(""))
				paraList.add(entry.getKey()+entry.getValue());
		}
		List<String> sortParaList = sort(paraList);
		StringBuffer paraBuffer = new StringBuffer();
		Iterator<String> sort = sortParaList.iterator();
		while (sort.hasNext()) {
			paraBuffer.append(sort.next());
		}
		paraBuffer.append(key);
		String md5Str = paraBuffer.toString();
		System.out.println(md5Str);
		// 生成本地签名
		String localSign = MD5.md5(md5Str).toUpperCase();
		return localSign;
	}
	
	public static String getLocalSign(HashMap<String,String> allPara){
		//获取相关版本号对应的sign的key值
		String key=TxServiceHelper.getSignKey(allPara.get(ParseCoreReceiveXml.VERSION),true);
		System.out.println("key:"+key);
		List<String> paraList = new ArrayList<String>();
		Iterator<Map.Entry<String, String>> it = allPara.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,String> entry = (Map.Entry<String,String>)it.next();
			//将值不为空的项进行sign处理
			if(entry.getValue()!=null&&!entry.getValue().equals(""))
				paraList.add(entry.getKey()+entry.getValue());
		}
		List<String> sortParaList = sort(paraList);
		StringBuffer paraBuffer = new StringBuffer();
		Iterator<String> sort = sortParaList.iterator();
		while (sort.hasNext()) {
			paraBuffer.append(sort.next());
		}
		paraBuffer.append(key);
		String md5Str = paraBuffer.toString();
		System.out.println(md5Str);
		// 生成本地签名
		String localSign = MD5.md5(md5Str).toUpperCase();
		return localSign;
	}
	
	
//	public static void main(String[] args){
//		
//		String requestXML="<JFPay application=\"getUserInstruction.Req\" version=\"100002\" osType=\"iOS6.0\" clientType=\"00\" userIP=\"192.168.2.133\" mobileSerialNum=\"997E12E01149D4FA9AA8A3E8B770C6AD00000000\" phone=\"0000\" token=\"0000\"><transDate>20130121</transDate><transLogNo>000160</transLogNo><transTime>112547</transTime><instrVersion>0.0.0</instrVersion><sign>71D0AD5C00B1789FA10926BC878AB02D</sign></JFPay>";
//		
//		//String requestXML="<JFPay application=\"ClientUpdate2.Req\" version=\"100002\" osType=\"iOS6.0\" clientType=\"00\" userIP=\"192.168.2.133\" mobileSerialNum=\"997E12E01149D4FA9AA8A3E8B770C6AD00000000\" phone=\"\" token=\"0000\"><transTime>110639</transTime><transLogNo>000151</transLogNo><transDate>20130121</transDate><sign>1624D4105637A0777B49727678AECBEF</sign></JFPay>";
//		HashMap<String, String> allPara=new HashMap<String, String>();
//		System.out.print("requestXML:"+requestXML);
//		try {
//			allPara = ParseCoreReceiveXml.parseCoreXml(requestXML);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String sign = allPara.get("sign");
//		allPara.remove("sign");
//		String localSign=getLocalSign(allPara);
//		System.out.println("sign:"+sign);
//		System.out.println("localSign:"+localSign);
//	}
//	
	/**
	 * 按照升序排列输出
	 * 
	 * @param a
	 * @return
	 */
	public static List<String> sort(List<String> input) {

		String str1 = "";
		String str2 = "";
		int flag, y;
		y = input.size();
		for (int x = 0; x < input.size() - 1; x++) {
			// 冒泡排序
			for (int i = 0; i < y - 1; i++) {
				str1 = input.get(i);
				str2 = input.get(i + 1);
				flag = str1.compareToIgnoreCase(str2);
				if (flag >= 0) {
					input.set(i, str2);
					input.set(i + 1, str1);
				}
			}
			y = y - 1;
		}
		return input;
	}
}
