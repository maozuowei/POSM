package com.jfpay.preposing.control;

import java.io.Serializable;

public class LoginInfoObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String token;
	
	private String mobileSerialNum;
	
	private long loginTime;
	
	private String logNo;
	
	public LoginInfoObject(){
		
	}
	
	public LoginInfoObject(String token,String mobileSerialNum,long loginTime,String logNo){
		
		this.token = token;
		this.mobileSerialNum = mobileSerialNum;
		this.loginTime = loginTime;
		this.logNo = logNo;
	}

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobileSerialNum() {
		return mobileSerialNum;
	}

	public void setMobileSerialNum(String mobileSerialNum) {
		this.mobileSerialNum = mobileSerialNum;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
