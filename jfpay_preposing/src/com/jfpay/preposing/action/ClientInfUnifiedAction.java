package com.jfpay.preposing.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import cn.hnae.tuxedojms.ejb.LogLocal;
import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.control.RequestPoolObject;
import com.jfpay.preposing.properties.DataDictInitialize;
import com.jfpay.preposing.remote.SendPayService;
import com.jfpay.preposing.reqhandle.ClientReqHandleProxy;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.ReturnMapCreateTool;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UDPClient;
import com.jfpay.preposing.utils.UDPClientLogin;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.utils.HttpUtil;
import com.jfpay.preposing.utils.MD5;
import com.jfpay.preposing.utils.WebServiceUtil;
import com.jfpay.preposing.xml.DynamicCreateCoreXmlForJFPay;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class ClientInfUnifiedAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1796194778382733548L;

	// 注入日志服务
	@EJB
	private LogLocal loger;

	static Logger log = Logger.getLogger(ClientInfUnifiedAction.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)// 接收get方法的请求，转到dopost方法处理
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 交易开始时间
		// long startTime = System.currentTimeMillis();

		String ip = this.getIpAddr(request);
		String requestXmlWithoutDecode = request.getParameter("requestXml");
		log.info("发起交易 requestXmlWithoutDecode: " + ip + requestXmlWithoutDecode);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String msg = "";
		PrintWriter out = response.getWriter();
		String rpoKey = "";
		LocalResp localResp = null;
		CommunicationInfo commu = null;
		Object returnMsg = null;

		// 客户端请求前置map
		HashMap<String, String> map = new HashMap<String, String>();
		// 前置响应客户端map
		HashMap<String, String> resultMap = new HashMap<String, String>();
		try {

			map = ParseCoreReceiveXml.parseCoreXml(requestXmlWithoutDecode);

			map.put("signStr", requestXmlWithoutDecode);

			// 判断当前请求池是否小于请求最大数限定(小于则放入请求池)
			if (DataDictInitialize.REQUEST_POOL.size() < DataDictInitialize.REQUEST_LIMITED) {
				IClientReqHandleService service = new ClientReqHandleProxy(map);
				// 调用请求参数校验方法
				localResp = service.verifyMessage(map);
				map.remove("signStr");
				if (!localResp.getRespCode().equals(SystemCode.SUCCESS.getCode())) {
					map.remove(ParseCoreReceiveXml.VERSION_FLAG);
					map.put("respCode", localResp.getRespCode());
					map.put("respDesc", localResp.getRespDesc());
					resultMap = ReturnMapCreateTool.createMapError(map);
					msg = DynamicCreateCoreXmlForJFPay.dynamicCreateXml(resultMap);
					// 返回报文签名
					msg = MD5.respXmlMD5(msg);
					log.info("完成交易 responseXml:" + msg);
					out.write(msg);
					out.flush();
					out.close();
					return;
				}
				RequestPoolObject rpo = new RequestPoolObject();
				rpo.setCreateTime(System.currentTimeMillis());
				// 1、处理完毕；2、超时；3、出错；0、待处理；4、处理中
				rpo.setStatus(0);

				rpoKey = map.get(ParseCoreReceiveXml.TRANSDATE).toString().trim() + map.get(ParseCoreReceiveXml.TRANSTIME).toString().trim()
						+ map.get(ParseCoreReceiveXml.TRANSLOGNO).toString().trim();

				DataDictInitialize.REQUEST_POOL.put(rpoKey, rpo);
				commu = service.getCommuInfo(map);
				switch (commu.getInvokeType()) {
				case UrlCache.INVOKE_HTTP:
					returnMsg = HttpUtil.send(commu.getInvokeUrl(), commu.getMessage());
					break;

				case UrlCache.INVOKE_TPCALL:
					returnMsg = SendPayService.sendPay(map);
					break;

				case UrlCache.INVOKE_WEBSERVICE:
					returnMsg = WebServiceUtil.invokeWebService(commu.getInvokeUrl(), commu.getMessage());
					break;

				case UrlCache.SYNCHEONIZE:
					returnMsg = "同步处理";
					break;

				}
				resultMap = service.handleReturnMessage(map, returnMsg);
				service = null;// 清除对象
				for (String key : resultMap.keySet()) {
					System.out.println("********" + key + "*****:" + resultMap.get(key));
				}
				// 完成交易则清除请求池中对应的请求
				DataDictInitialize.REQUEST_POOL.remove(rpoKey);
			} else {
				log.error("请求服务被拒绝(请求池请求数已满)! requestXml: " + requestXmlWithoutDecode);

				map.put("respCode", SystemCode.REQ_POOL_FULL.getCode());
				map.put("respDesc", SystemCode.REQ_POOL_FULL.getDesc());
				resultMap = ReturnMapCreateTool.createMapError(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + " 用户： " + map.get(ParseCoreReceiveXml.APP_USER) + map.get(ParseCoreReceiveXml.PHONE) + " 调用服务发生异常: "
					+ map.get(ParseCoreReceiveXml.APPLICATION));
			map.remove(ParseCoreReceiveXml.VERSION_FLAG);
			map.put("respCode", SystemCode.CALL_HOST_EXCEPTION.getCode());
			map.put("respDesc", SystemCode.CALL_HOST_EXCEPTION.getDesc());
			resultMap = ReturnMapCreateTool.createMapError(map);
			// 完成交易则清除请求池中对应的请求
			DataDictInitialize.REQUEST_POOL.remove(rpoKey);
		}
		localResp = null;// 清除对象
		commu = null;// 清除对象
		returnMsg = null;// 清除对象
		// 填充原始sign(填充了key值)
		resultMap.put("sign", map.get(ParseCoreReceiveXml.SIGN));
		resultMap.put("appUser", map.get(ParseCoreReceiveXml.APP_USER));
		msg = DynamicCreateCoreXmlForJFPay.dynamicCreateXml(resultMap);
		// 计算返回报文签名
		msg = MD5.respXmlMD5(msg);
		log.info("完成交易 responseXml:" + msg);
		
		
		log.info("开始发送登录同步");
		UDPClientLogin.CreateUDPDataAndSendLogin(resultMap);
		log.info("登录同步成功");
		
		
		out.write(msg);
		out.flush();
		out.close();
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE-HOST");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}