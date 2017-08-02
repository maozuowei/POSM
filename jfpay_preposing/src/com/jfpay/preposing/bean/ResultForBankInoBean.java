package com.jfpay.preposing.bean;

import java.util.List;

import com.jfpay.preposing.utils.Message;

public class ResultForBankInoBean {

	
	private List<BankInfoJSONBean> resultBean;
	
	private Message result;
	
	private VersionSummaryBean summary;

	public List<BankInfoJSONBean> getResultBean() {
		return resultBean;
	}

	public void setResultBean(List<BankInfoJSONBean> resultBean) {
		this.resultBean = resultBean;
	}

	public Message getResult() {
		return result;
	}

	public void setResult(Message result) {
		this.result = result;
	}

	public VersionSummaryBean getSummary() {
		return summary;
	}

	public void setSummary(VersionSummaryBean summary) {
		this.summary = summary;
	}
}
