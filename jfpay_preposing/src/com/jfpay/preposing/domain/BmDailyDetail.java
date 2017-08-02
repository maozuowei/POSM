package com.jfpay.preposing.domain;

/**
 * Title : <br>
 * Project : jfpay_preposing <br>
 * Class : com.jfpay.preposing.domain.BmDailyDetail <br>
 * Description : <br>
 * CopyRights (c) 2015 <br>
 * Company Yoolink Co,.Ltd <br>
 * Date : Jan 13, 2015 <br>
 * 
 * @author L7
 * @version 1.0
 */
public class BmDailyDetail implements java.io.Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4410892901486229441L;

    // Fields

    private String daily_det_id;
    private String curr_date;
    private String curr_time;
    private String rule_no;
    private String risk_key;
    private Long amount;
    private String islegal;
    private String memo;

    // Property accessors

    public String getDaily_det_id() {
        return daily_det_id;
    }

    public void setDaily_det_id(String dailyDetId) {
        daily_det_id = dailyDetId;
    }

    public String getCurr_date() {
        return curr_date;
    }

    public void setCurr_date(String currDate) {
        curr_date = currDate;
    }

    public String getCurr_time() {
        return curr_time;
    }

    public void setCurr_time(String currTime) {
        curr_time = currTime;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getIslegal() {
        return islegal;
    }

    public void setIslegal(String islegal) {
        this.islegal = islegal;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}