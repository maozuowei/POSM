package com.jfpay.preposing.bean;

import java.util.List;

import com.jfpay.preposing.utils.Message;

public class ResultForClientUpdateBean {

	private List<ClientUpdateJSONBean> resultBean;
	
	private Message result;
	
	private ClientUpdateSummaryBean summary;

	public List<ClientUpdateJSONBean> getResultBean() {
		return resultBean;
	}

	public void setResultBean(List<ClientUpdateJSONBean> resultBean) {
		this.resultBean = resultBean;
	}

	public Message getResult() {
		return result;
	}

	public void setResult(Message result) {
		this.result = result;
	}

	public ClientUpdateSummaryBean getSummary() {
		return summary;
	}

	public void setSummary(ClientUpdateSummaryBean summary) {
		this.summary = summary;
	}
}
