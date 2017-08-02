package com.jfpay.preposing.domain;

/**
 * 充值规则(比如流量充值与手机充值)
 * @author horace
 *
 */
public class BmRechargeRule implements java.io.Serializable{

	/**
     * Comment for <code>serialVersionUID</code>
     */
	private static final long serialVersionUID = 7806946176648446425L;
	
	 private String record_no;
	 private String merchantid;
	 private String productid;
	 private String key_type;
	 private String ctrl_mode;
	 private Long max_amount;
	 private Long max_count;
	 private String effect_time;
	 private String status;
	 
	 
	public String getRecord_no() {
		return record_no;
	}
	public void setRecord_no(String record_no) {
		this.record_no = record_no;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getKey_type() {
		return key_type;
	}
	public void setKey_type(String key_type) {
		this.key_type = key_type;
	}
	public String getCtrl_mode() {
		return ctrl_mode;
	}
	public void setCtrl_mode(String ctrl_mode) {
		this.ctrl_mode = ctrl_mode;
	}
	public Long getMax_amount() {
		return max_amount;
	}
	public void setMax_amount(Long max_amount) {
		this.max_amount = max_amount;
	}
	public Long getMax_count() {
		return max_count;
	}
	public void setMax_count(Long max_count) {
		this.max_count = max_count;
	}
	public String getEffect_time() {
		return effect_time;
	}
	public void setEffect_time(String effect_time) {
		this.effect_time = effect_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
