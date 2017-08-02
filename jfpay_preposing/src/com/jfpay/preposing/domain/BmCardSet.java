package com.jfpay.preposing.domain;

/**
 * Title : <br>
 * Project : jfpay_preposing <br>
 * Class : com.jfpay.preposing.domain.BmCardSet <br>
 * Description : <br>
 * CopyRights (c) 2015 <br>
 * Company Yoolink Co,.Ltd <br>
 * Date : Jan 13, 2015 <br>
 * 
 * @author L7
 * @version 1.0
 */
public class BmCardSet implements java.io.Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2880989504124303373L;

    // Fields

    private String cardno;
    private String merchantid;
    private String productid;
    private String acc_type;
    private String status;
    private String last_req_time;
    private String last_txn_time;
    private String isnew;
    private String isallow;
    private String memo;

    // Property accessors

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
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

    public String getAcc_type() {
        return acc_type;
    }

    public void setAcc_type(String accType) {
        acc_type = accType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_req_time() {
        return last_req_time;
    }

    public void setLast_req_time(String lastReqTime) {
        last_req_time = lastReqTime;
    }

    public String getLast_txn_time() {
        return last_txn_time;
    }

    public void setLast_txn_time(String lastTxnTime) {
        last_txn_time = lastTxnTime;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getIsallow() {
        return isallow;
    }

    public void setIsallow(String isallow) {
        this.isallow = isallow;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
