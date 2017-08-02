package com.jfpay.preposing.bean;

import java.util.List;

import com.jfpay.preposing.utils.Message;

public class ResultForBankCardListBean {

	private List<BankCardListJSONBean> resultBean;
	
	private Message result;
	
	private BankCardListSummaryBean summary;

	public List<BankCardListJSONBean> getResultBean() {
		return resultBean;
	}

	public void setResultBean(List<BankCardListJSONBean> resultBean) {
		this.resultBean = resultBean;
	}

	public Message getResult() {
		return result;
	}

	public void setResult(Message result) {
		this.result = result;
	}

	public BankCardListSummaryBean getSummary() {
		return summary;
	}

	public void setSummary(BankCardListSummaryBean summary) {
		this.summary = summary;
	}
}
