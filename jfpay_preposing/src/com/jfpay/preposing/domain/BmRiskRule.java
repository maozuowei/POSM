package com.jfpay.preposing.domain;

/**
 * Title : <br>
 * Project : jfpay_preposing <br>
 * Class : com.jfpay.preposing.domain.BmRiskRule <br>
 * Description : <br>
 * CopyRights (c) 2015 <br>
 * Company Yoolink Co,.Ltd <br>
 * Date : Jan 4, 2015 <br>
 * 
 * @author L7
 * @version 1.0
 */
public class BmRiskRule implements java.io.Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3429899451537175861L;

    // Fields

    private String record_no;
    private String merchantid;
    private String productid;
    private String key_type;
    private String ctrl_mode;
    private String st_flag;
    private String ed_flag;
    private Long max_amount;
    private Long max_count;
    private String effect_time;
    private String status;
    private String input_t;
    private String edit_t;
    private String nowFlag;

    // Property accessors

    public String getRecord_no() {
        return record_no;
    }

    public void setRecord_no(String recordNo) {
        record_no = recordNo;
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

    public void setKey_type(String keyType) {
        key_type = keyType;
    }

    public String getCtrl_mode() {
        return ctrl_mode;
    }

    public void setCtrl_mode(String ctrlMode) {
        ctrl_mode = ctrlMode;
    }

    public String getSt_flag() {
        return st_flag;
    }

    public void setSt_flag(String stFlag) {
        st_flag = stFlag;
    }

    public String getEd_flag() {
        return ed_flag;
    }

    public void setEd_flag(String edFlag) {
        ed_flag = edFlag;
    }

    public Long getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(Long maxAmount) {
        max_amount = maxAmount;
    }

    public String getEffect_time() {
        return effect_time;
    }

    public void setEffect_time(String effectTime) {
        effect_time = effectTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInput_t() {
        return input_t;
    }

    public void setInput_t(String inputT) {
        input_t = inputT;
    }

    public String getEdit_t() {
        return edit_t;
    }

    public void setEdit_t(String editT) {
        edit_t = editT;
    }

    public String getNowFlag() {
        return nowFlag;
    }

    public void setNowFlag(String nowFlag) {
        this.nowFlag = nowFlag;
    }
    
    public Long getMax_count() {
        return max_count;
    }

    public void setMax_count(Long maxCount) {
        max_count = maxCount;
    }

}