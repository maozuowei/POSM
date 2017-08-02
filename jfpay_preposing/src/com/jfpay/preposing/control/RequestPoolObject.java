package com.jfpay.preposing.control;

import java.util.HashMap;

public class RequestPoolObject {
	
	private String requestXml;
	
	private String returnXml;
	
	private HashMap<String,String> returnMap;

	private long createTime;
	
	private int status;

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getReturnXml() {
		return returnXml;
	}

	public void setReturnXml(String returnXml) {
		this.returnXml = returnXml;
	}

	public HashMap<String, String> getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(HashMap<String, String> returnMap) {
		this.returnMap = returnMap;
	}
}
