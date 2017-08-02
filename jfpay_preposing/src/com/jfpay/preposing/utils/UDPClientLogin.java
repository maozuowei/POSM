package com.jfpay.preposing.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Map;
import javax.ejb.EJB;
import org.apache.log4j.Logger;
import cn.hnae.tuxedojms.ejb.LogLocal;

public class UDPClientLogin {

	public static String UDP_HOST_LOGIN;//udp服务端地址
	
	public static int UDP_PORT_LOGIN;//服务端端口
	
	public static DatagramSocket socket_LOGIN;
	
	public static DatagramPacket outgoing_LOGIN;
	
	/**
	 * 组播发送
	 * @param udpStr
	 */
	@EJB
	private LogLocal loger;

	static Logger log = Logger.getLogger(UDPClientLogin.class);

	
	private static void multicastSend(String udpStr){
		
		InetAddress ia = null;
		
		byte ttl = (byte) 1;

		try {
			//"all-systems.mcast.net"
			ia = InetAddress.getByName(UDP_HOST_LOGIN);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] data = udpStr.getBytes();

		DatagramPacket dp = new DatagramPacket(data, data.length, ia, UDP_PORT_LOGIN);

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
			
			InetAddress server = InetAddress.getByName(UDP_HOST_LOGIN);
			
		//	log.info(UDP_HOST_LOGIN+"===================================================="+UDP_PORT_LOGIN);
			//发送UDP数据报
			DatagramPacket outgoing = new DatagramPacket(data,data.length,server,UDP_PORT_LOGIN);
			
			DatagramSocket socket = new DatagramSocket();
			
			socket.send(outgoing);
			
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("异常"+e.getMessage());
		}
	}
	
	/**
	 * 生成UDP报文
	 * @param strMap
	 */
	public static void CreateUDPDataAndSendLogin(Map<String, String> map){
	//	log.info("第一步");
		if(map.get("application").equals("UserLogin.Rsp")){
		//	log.info("第二不走");
//			log.info("开始发送登录同步"); 
			StringBuffer strBuffer=new StringBuffer();
			strBuffer.append((map.get("application").split("[.]"))[0]).append("|");
			strBuffer.append(map.get("appUser")).append("|");
			strBuffer.append(map.get("realName")).append("|");
			strBuffer.append(map.get("certPid")).append("|");
			strBuffer.append(map.get("mobileNo"));
			String udpData = strBuffer.toString();
			send(udpData);
			strBuffer= null;
//			log.info("登录同步成功"); 
		//	log.info(udpData+"发送完成。。。");
		}		
	}
}
