package com.jfpay.preposing.bean;

import java.util.List;

import com.jfpay.preposing.utils.Message;

public class ResultForInstructionBean {

	private List<InstructionJSONBean> resultBean;
	
	private Message result;
	
	private VersionSummaryBean summary;

	public VersionSummaryBean getSummary() {
		return summary;
	}

	public void setSummary(VersionSummaryBean summary) {
		this.summary = summary;
	}

	public List<InstructionJSONBean> getResultBean() {
		return resultBean;
	}

	public void setResultBean(List<InstructionJSONBean> resultBean) {
		this.resultBean = resultBean;
	}

	public Message getResult() {
		return result;
	}

	public void setResult(Message result) {
		this.result = result;
	}

}
