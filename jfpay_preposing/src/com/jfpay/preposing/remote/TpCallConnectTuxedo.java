package com.jfpay.preposing.remote;

import java.util.HashMap;

import weblogic.wtc.jatmi.TPException;

public class TpCallConnectTuxedo extends ConnectTuxedoWay {

	@Override
	public Object connectTuxedo(Object param) throws TPException{
		// TODO Auto-generated method stub
		Object result = TuxedoJndi.getInstance("TpCall").test.CallTuxedo((HashMap<String, String>)param);
		return result;
	}

	@Override
	public void receiveTuxedo() {
		// TODO Auto-generated method stub
	}

}
