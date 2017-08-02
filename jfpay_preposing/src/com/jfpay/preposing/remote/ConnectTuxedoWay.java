package com.jfpay.preposing.remote;

import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;

public abstract class ConnectTuxedoWay {
	
	public abstract Object connectTuxedo(Object param) throws TPReplyException,TPException;
	
	public abstract void receiveTuxedo();
}
