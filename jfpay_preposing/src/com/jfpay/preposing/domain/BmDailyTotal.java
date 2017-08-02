package com.jfpay.preposing.domain;

/**
 * Title : <br>
 * Project : jfpay_preposing <br>
 * Class : com.jfpay.preposing.domain.BmDailyTotal <br>
 * Description : <br>
 * CopyRights (c) 2015 <br>
 * Company Yoolink Co,.Ltd <br>
 * Date : Jan 4, 2015 <br>
 * 
 * @author L7
 * @version 1.0
 */
public class BmDailyTotal implements java.io.Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 318047495602263237L;

    // Fields

    private String curr_date;
    private String rule_no;
    private String risk_key;
    private Long free_amount;
    private Long free_count;
    private Long nfree_amount;
    private Long nfree_count;
    private String memo;
    private String input_t;
    private String edit_t;

    // Property accessors

    public String getCurr_date() {
        return curr_date;
    }

    public void setCurr_date(String currDate) {
        curr_date = currDate;
    }

    public String getRule_no() {
        return rule_no;
    }

    public void setRule_no(String ruleNo) {
        rule_no = ruleNo;
    }

    public String getRisk_key() {
        return risk_key;
    }

    public void setRisk_key(String riskKey) {
        risk_key = riskKey;
    }

    public Long getFree_amount() {
        return free_amount;
    }

    public void setFree_amount(Long freeAmount) {
        free_amount = freeAmount;
    }

    public Long getFree_count() {
        return free_count;
    }

    public void setFree_count(Long freeCount) {
        free_count = freeCount;
    }

    public Long getNfree_amount() {
        return nfree_amount;
    }

    public void setNfree_amount(Long nfreeAmount) {
        nfree_amount = nfreeAmount;
    }

    public Long getNfree_count() {
        return nfree_count;
    }

    public void setNfree_count(Long nfreeCount) {
        nfree_count = nfreeCount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

}