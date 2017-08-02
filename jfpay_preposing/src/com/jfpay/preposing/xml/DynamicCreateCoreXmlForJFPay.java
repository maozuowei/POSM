package com.jfpay.preposing.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class DynamicCreateCoreXmlForJFPay {

	static Logger log = Logger.getLogger(DynamicCreateCoreXmlForJFPay.class);

	/**
	 * 根据传入的map动态生成核心系统的xml
	 * 
	 * @param strMap
	 *            存有报文元素及值的map
	 * @return 生成的xml的String
	 * @throws Exception
	 */
	public static String dynamicCreateXml(Map<String, String> strMap) {

		strMap.remove(ParseCoreReceiveXml.USER_IP);
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");

		// 构建root节点（mobilePay节点）
		Element root = document.addElement(ParseCoreReceiveXml.QT_PAY);

		// 循环构建子节点；
		Iterator<Map.Entry<String, String>> it = strMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> map = (Map.Entry<String, String>) it.next();
			if (map.getKey().equals(ParseCoreReceiveXml.APPLICATION)) {
				root.addAttribute(ParseCoreReceiveXml.APPLICATION, strMap.get(ParseCoreReceiveXml.APPLICATION).toString());
			} else if (map.getKey().equals(ParseCoreReceiveXml.VERSION)) {
				root.addAttribute(ParseCoreReceiveXml.VERSION, strMap.get(ParseCoreReceiveXml.VERSION).toString());
			} else if (map.getKey().equals(ParseCoreReceiveXml.OS_TYPE)) {
				root.addAttribute(ParseCoreReceiveXml.OS_TYPE, strMap.get(ParseCoreReceiveXml.OS_TYPE).toString());
			} else if (map.getKey().equals(ParseCoreReceiveXml.MOBILE_SERIAL_NUM)) {
				root.addAttribute(ParseCoreReceiveXml.MOBILE_SERIAL_NUM, strMap.get(ParseCoreReceiveXml.MOBILE_SERIAL_NUM).toString());
			} else if (map.getKey().equals(ParseCoreReceiveXml.USER_IP)) {
				root.addAttribute(ParseCoreReceiveXml.USER_IP, strMap.get(ParseCoreReceiveXml.USER_IP).toString());
			} else if (map.getKey().equals(ParseCoreReceiveXml.APP_USER)) {
				root.addAttribute(ParseCoreReceiveXml.APP_USER, strMap.get(ParseCoreReceiveXml.APP_USER).toString());
			} else if (map.getKey().equals(ParseCoreReceiveXml.CLIENT_TYPE)) {
				root.addAttribute(ParseCoreReceiveXml.CLIENT_TYPE, strMap.get(ParseCoreReceiveXml.CLIENT_TYPE).toString());
			} else if (map.getKey().equals("data")) {
				root.addElement((map.getKey() != null ? map.getKey().toString() : "")).addCDATA((map.getValue() != null ? map.getValue().toString() : ""));
			} else if (map.getKey().equals("printInfo")) {// 打印信息
				root.addElement((map.getKey() != null ? map.getKey().toString() : "")).addCDATA((map.getValue() != null ? map.getValue().toString() : ""));
			} else {
				root.addElement((map.getKey() != null ? map.getKey().toString() : "")).setText((map.getValue() != null ? map.getValue().toString() : ""));
			} 
		}
		String result = document.asXML().trim().replace("\n", "");
		return result;
	}

	// /**
	// * @param args
	// */
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("application", "15820092750");
		map.put("version", "15820092750");
		map.put("osType", "15820092750");
		map.put("uuid", "15820092750");
		map.put("mobileNo", "15820092750");
		map.put("userName", "xj@gmail.com");
		map.put("pidType", "00");
		map.put("userPid", "aaa");
		map.put("passwd", "123dasdr3esd32");
		map.put("mobileMac", "1234");
		map.put("transDate", "20111216");
		map.put("transTime", "1710");
		map.put("transLogNo", "0001");
		try {
			System.out.println(dynamicCreateXml(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
