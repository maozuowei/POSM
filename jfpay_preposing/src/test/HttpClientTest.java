package test;

import java.io.IOException;
import java.util.HashMap;

import net.sf.json.JSONObject;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.utils.HttpUtil;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

public class HttpClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String url = "http://58.247.33.66:7001/jfpay_note/PartnerCodeQuery.do";
		//String url = "http://58.247.33.66:7001/jfpay_note/order/serial.do";
		String url = "http://localhost:8080/jfpay_note/BankCardQuery.do";
		String param = "{\"REQ_HEAD\":{},\"REQ_BODY\":{\"payOrdNo\":\"10006115073000711713\",\"orderNo\":\"1000611507301140431617\",\"orderDate\":\"20151109\",\"busType\":\"100061\",\"orgId\":\"999999999\",\"mobileNo\":\"18616341679\"}}";
		String result = "";
		try {
			//CommunicationInfo communicationInfo =getCommuInfo(); 
			result = HttpUtil.send(url, "cardNo=6228480050863939018");
			//result = HttpClientUtil.post(url, param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("--------->"+result);

	}
	
	private final static String BUS_TYPE = "busType";
	private final static String ORG_ID = "orgId";
	private final static String TERM_ID = "termId";
	
	
	public static CommunicationInfo getCommuInfo() {
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_HTTP);
		
		commu.setInvokeUrl("http://101.231.98.133:8890/hdcctp/SMPAY0001.json");

		JSONObject paramBody = new JSONObject();
		paramBody.put(ParseCoreReceiveXml.ORDER_ID, "1000611412261047367924");
		//业务类型 
		paramBody.put(BUS_TYPE, "100066");
		//机构号
		paramBody.put(ORG_ID, "000000001");
		//终端代码
		paramBody.put(TERM_ID, "SJ000001");
		JSONObject paramJson = new JSONObject();
		paramJson.put("REQ_HEAD", new JSONObject());
		paramJson.put("REQ_BODY", paramBody);
		String parameter = "REQ_MESSAGE="+paramJson.toString();
		System.out.println("--------->"+parameter);
		commu.setMessage(parameter);
		return commu;
	}


}
