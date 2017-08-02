package com.jfpay.preposing.reqhandle.heliquickpay;

import java.util.HashMap;

import com.jfpay.preposing.bean.CommunicationInfo;
import com.jfpay.preposing.bean.LocalResp;
import com.jfpay.preposing.reqhandle.ClientReqHandle;
import com.jfpay.preposing.reqhandle.service.IClientReqHandleService;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.UrlCache;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

/**
 * 发送快捷支付短信，
 * @author leepon
 *
 */
public class QuickPaySendMsg extends ClientReqHandle implements IClientReqHandleService{

	@Override
	public CommunicationInfo getCommuInfo(HashMap<String, String> map) {
		CommunicationInfo commu = new CommunicationInfo();
		commu.setInvokeType(UrlCache.INVOKE_TPCALL);
		return commu;
	}

	@Override
	public HashMap<String, String> handleReturnMessage(
			HashMap<String, String> map, Object returnMessage) {
		// TODO Auto-generated method stub
		if(returnMessage instanceof HashMap){
			resultMap = (HashMap)returnMessage;
		}
		return resultMap;
	}
	
	@Override
	public LocalResp verifyMessage(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		LocalResp localResp = new LocalResp();
		if (!super.checkMD5(map)) {// SIGN校验失败
			localResp.setRespCode(SystemCode.SIGN_ERROR.getCode());
			localResp.setRespDesc(SystemCode.SIGN_ERROR.getDesc());
			return localResp;
		}
		if (!super.checkReqTime(map)) {// 请求交易时间异常
			localResp.setRespCode(SystemCode.REQ_EXCEPTION.getCode());
			localResp.setRespDesc(SystemCode.REQ_EXCEPTION.getDesc());
			return localResp;
		}
		super.checkMessageUnify(map);
		//...........
		if (map.get(ParseCoreReceiveXml.MOBILE_NO) == null || map.get(ParseCoreReceiveXml.MOBILE_NO).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.MOBILE_NO + ",");
		}
		
		//短信发送类型 smsType：00-开通，01-确认支付
		if (map.get(ParseCoreReceiveXml.SMS_TYPE) == null || map.get(ParseCoreReceiveXml.SMS_TYPE).trim().length() == 0) {
			i++;
			sb.append(ParseCoreReceiveXml.SMS_TYPE + ",");
		} else if(map.get(ParseCoreReceiveXml.SMS_TYPE) != null && "00".equals(map.get(ParseCoreReceiveXml.SMS_TYPE).trim())){
			//身份证号码
			if (map.get(ParseCoreReceiveXml.CERT_PID) == null || map.get(ParseCoreReceiveXml.CERT_PID).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.CERT_PID + ",");
			}
			
			//姓名
			if (map.get(ParseCoreReceiveXml.REAL_NAME) == null || map.get(ParseCoreReceiveXml.REAL_NAME).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.REAL_NAME + ",");
			}
			//持卡人开户行
			if (map.get(ParseCoreReceiveXml.BANK_NAME) == null || map.get(ParseCoreReceiveXml.BANK_NAME).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.BANK_NAME + ",");
			}
			
			//持卡人银行卡卡号
			if (map.get(ParseCoreReceiveXml.CARD_NO) == null || map.get(ParseCoreReceiveXml.CARD_NO).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.CARD_NO + ",");
			}
			
			//银行卡在银行预留手机号码
			if (map.get(ParseCoreReceiveXml.CARD_PHONE_NO) == null || map.get(ParseCoreReceiveXml.CARD_PHONE_NO).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.CARD_PHONE_NO + ",");
			}
			
			//先判断卡类型，为信用卡时需要有效期和校验码
			if (map.get(ParseCoreReceiveXml.CARD_TYPE) != null && "03".equals(map.get(ParseCoreReceiveXml.CARD_TYPE).trim())) {
				//持卡人信用卡有效期
				if (map.get(ParseCoreReceiveXml.CARD_VALIDATE) == null || map.get(ParseCoreReceiveXml.CARD_VALIDATE).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CARD_VALIDATE + ",");
				}
				//安全码
				if (map.get(ParseCoreReceiveXml.CVN) == null || map.get(ParseCoreReceiveXml.CVN).trim().length() == 0) {
					i++;
					sb.append(ParseCoreReceiveXml.CVN + ",");
				}
			}
		} else if(map.get(ParseCoreReceiveXml.SMS_TYPE) != null && "01".equals(map.get(ParseCoreReceiveXml.SMS_TYPE).trim())){
			//持卡人银行卡卡号
			if (map.get(ParseCoreReceiveXml.CARD_NO) == null || map.get(ParseCoreReceiveXml.CARD_NO).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.CARD_NO + ",");
			}
			
			//交易金额
			if (map.get(ParseCoreReceiveXml.AMOUNT) == null || map.get(ParseCoreReceiveXml.AMOUNT).trim().length() == 0) {
				i++;
				sb.append(ParseCoreReceiveXml.AMOUNT + ",");
			}
		}
		
		if (i > 0) {
			log.error(map.get(ParseCoreReceiveXml.TRANSDATE)
					+ map.get(ParseCoreReceiveXml.TRANSTIME)
					+ map.get(ParseCoreReceiveXml.TRANSLOGNO) + "本次请求参数缺失："
					+ sb.substring(0, sb.length() - 1).toString());
			// 校验失败 缺少必填参数
			localResp.setRespCode(SystemCode.MISSING_ARGUMENT.getCode());
			localResp.setRespDesc(sb.substring(0, sb.length() - 1).toString());
			sb = null;
			return localResp;
		}
		sb = null;
		//校验登录session信息
		localResp = checkLoginToken(map);
		return localResp;
	}

}
