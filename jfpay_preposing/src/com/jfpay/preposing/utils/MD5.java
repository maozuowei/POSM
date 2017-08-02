package com.jfpay.preposing.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;

import org.apache.log4j.Logger;

import com.jfpay.preposing.reqhandle.ClientReqHandle;

public class MD5 {
	protected static Logger log = Logger.getLogger(ClientReqHandle.class);
	
	public final static String md5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * md5加密(16进制转字节数组md5加密)
	 * 
	 * @param
	 * @return
	 */
	public final static String md52(byte[] b) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = b;
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean reqXmlMD5IsCorrect(String requestXml, String key) {

		// System.out.println("签名字符串:"+requestXml);
		log.info(requestXml);
		log.info(key);

		String signStr = requestXml.substring(requestXml.indexOf("<sign>"), requestXml.indexOf("</sign>") + 7);
		log.info(signStr);
		try {
			requestXml = URLEncoder.encode(requestXml.replace(signStr, "<sign>" + key + "</sign>"), "UTF-8");
			log.info(requestXml);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String localSign = MD5.md5(requestXml).toUpperCase();
		System.out.println("本地加密报文：" + requestXml); 
		String sign = signStr.substring(signStr.indexOf("<sign>") + 6, signStr.indexOf("</sign>"));
		System.out.println("客户端送的SIGN：" + sign + "本地算的SIGN:" + localSign); 
		if (sign.equals(localSign))
			return true;
		else
			return false;
	}

	/**
	 * 返回报文签名处理
	 * 
	 * @param responseXml
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String respXmlMD5(String responseXml) throws UnsupportedEncodingException {
		String signStr = responseXml.substring(responseXml.indexOf("<sign>"), responseXml.indexOf("</sign>") + 7);
		String key = MD5.md5(URLEncoder.encode(responseXml, "UTF-8")).toUpperCase();
		// 签名
		responseXml = responseXml.replace(signStr, "<sign>" + key + "</sign>");
		return responseXml;
	}

	public static void main(String[] args) {
		String key = null;
		try {
			key = MD5
					.md5(
							URLEncoder
									.encode(
											"<?xml version=\"1.0\" encoding=\"UTF-8\"?><QtPay application=\"JFPalCardPay.Req\" appUser=\"qtpay\" version=\"1.0.0\" osType=\"android4.3\" mobileSerialNum=\"3555330521048830000000000000000000000000\" userIP=\"localhost/127.0.0.1\" clientType=\"02\" token=\"1FB412ED67CD486F782E036334E40B06\" phone=\"13645667986\"><mobileNo>13645667986</mobileNo><termID>30709B45FD08</termID><cardInfo>FF000100018380412146217920102592578D24032200000091400000FFFFFFFFFFF996217920102592578D15615600000000000008800000002404040000000000000000000D000000000000D000000000000000000FFFFFFFF09656031F233900000010000000190000001000000013632313739323031303235393235373824037CEDD4EE10704368</cardInfo><cardPassword>9BE7EFBF190116649F96C3540FF32528BB3ACECFD136AB318D614B786B838346CC4978D7B3B9601244D4B0BA625F959655259164473EC6749F6305888DE0D2E4</cardPassword><merchantId>0001000001</merchantId><productId>0000000000</productId><orderId>2014062586430256</orderId><orderAmt>000000005000</orderAmt><transDate>20140625</transDate><transTime>161345</transTime><transLogNo>000064</transLogNo><sign>412fadsfoinhuc450f8jcnalzq08mfja</sign></QtPay>",
											"UTF-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(URLEncoder.encode(key,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
