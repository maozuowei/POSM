package com.jfpay.preposing.bean;

public class CommunicationInfo {

	//通讯方式 1：http 2:tpcall 3:queue
	private int invokeType;
	//通讯地址
	private String invokeUrl;
	//通讯报文
	private String message;

	public int getInvokeType() {
		return invokeType;
	}

	public void setInvokeType(int invokeType) {
		this.invokeType = invokeType;
	}

	public String getInvokeUrl() {
		return invokeUrl;
	}

	public void setInvokeUrl(String invokeUrl) {
		this.invokeUrl = invokeUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
