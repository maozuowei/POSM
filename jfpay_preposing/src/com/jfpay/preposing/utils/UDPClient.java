package com.jfpay.preposing.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

public class UDPClient {

	public static String UDP_HOST;//udp服务端地址
	
	public static int UDP_PORT;//服务端端口
	
	public static DatagramSocket socket;
	
	public static DatagramPacket outgoing;
	
	/**
	 * 组播发送
	 * @param udpStr
	 */
	private static void multicastSend(String udpStr){
		
		InetAddress ia = null;
		
		byte ttl = (byte) 1;

		try {
			//"all-systems.mcast.net"
			ia = InetAddress.getByName(UDP_HOST);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] data = udpStr.getBytes();

		DatagramPacket dp = new DatagramPacket(data, data.length, ia, UDP_PORT);

		MulticastSocket ms;
		try {
			ms = new MulticastSocket();

			ms.joinGroup(ia);
			for (int i = 1; i < 10; i++) {
				ms.send(dp, ttl);
			}
			ms.leaveGroup(ia);
			ms.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 单播发送
	 * @param udpStr
	 */
	private static void send(String udpStr){	
		
		try {
			
			byte[] data = udpStr.getBytes("UTF-8");		
			
			InetAddress server = InetAddress.getByName(UDP_HOST);
			
			//发送UDP数据报
			DatagramPacket outgoing = new DatagramPacket(data,data.length,server,UDP_PORT);
			
			DatagramSocket socket = new DatagramSocket();
			
			socket.send(outgoing);
			
		}   catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}	catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成UDP报文
	 * @param strMap
	 */
	public static void CreateUDPDataAndSend(Map<String, String> strMap){
		
		StringBuffer udpBuffer = new StringBuffer();
		udpBuffer.append("Message001").append("|");
		udpBuffer.append(strMap.get("appUser")).append("|");
		udpBuffer.append(strMap.get("version")).append("|");
		udpBuffer.append("PREP").append("|");
		udpBuffer.append("手机前置").append("|");
		udpBuffer.append((strMap.get("application").split("[.]"))[0]).append("|");
		udpBuffer.append("").append("|");
		udpBuffer.append(strMap.containsKey("mobileNo")?strMap.get("mobileNo"):"").append("|");
		udpBuffer.append(strMap.get("transDate")).append("|");
		udpBuffer.append(strMap.get("transTime")).append("|");
		udpBuffer.append(strMap.get("transLogNo")).append("|");
		udpBuffer.append(strMap.get("respCode")).append("|");
		udpBuffer.append(strMap.get("respDesc").equals("")?"无描述":strMap.get("respDesc"));
		String udpData = udpBuffer.toString();
		//调用单播发送
		send(udpData);
		//multicastSend(udpData);
		udpBuffer= null;
	}
}
