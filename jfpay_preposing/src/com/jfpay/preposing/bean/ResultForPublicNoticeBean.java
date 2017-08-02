package com.jfpay.preposing.bean;

import java.util.List;

import com.jfpay.preposing.utils.Message;

public class ResultForPublicNoticeBean {

	
	private List<PublicNoticeJSONBean> resultBean;
	
	private Message result;

	public List<PublicNoticeJSONBean> getResultBean() {
		return resultBean;
	}

	public void setResultBean(List<PublicNoticeJSONBean> resultBean) {
		this.resultBean = resultBean;
	}

	public Message getResult() {
		return result;
	}

	public void setResult(Message result) {
		this.result = result;
	}
	
}
