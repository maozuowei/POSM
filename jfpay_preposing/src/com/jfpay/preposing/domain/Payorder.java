package com.jfpay.preposing.domain;

/**
 * Title : <br>
 * Project : jfpay_preposing <br>
 * Class : com.jfpay.preposing.domain.Payorder <br>
 * Description : <br>
 * CopyRights (c) 2015 <br>
 * Company Yoolink Co,.Ltd <br>
 * Date : Jan 25, 2015 <br>
 * 
 * @author L7
 * @version 1.0
 */
public class Payorder implements java.io.Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4332225436241075811L;

    // Fields

    private String orderid;
    private String orderdate;
    private String ordertime;
    private String overduedate;
    private String overduetime;
    private String compdate;
    private String comptime;
    private String userid;
    private String customerid;
    private String channelid;
    private String termid;
    private String orgtxlogno;
    private String orgtxrefno;
    private String orgtxdate;
    private String orgtxtime;
    private String orgsettlmtdate;
    private String sndtxlogno;
    private String sndtxdate;
    private String sndtxtime;
    private String merchantid;
    private String productid;
    private String productmsg;
    private Long orderamt;
    private Long payamt;
    private Long fee;
    private String status;
    private String flag;
    private String remark;
    private String paytype;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getOverduedate() {
        return overduedate;
    }

    public void setOverduedate(String overduedate) {
        this.overduedate = overduedate;
    }

    public String getOverduetime() {
        return overduetime;
    }

    public void setOverduetime(String overduetime) {
        this.overduetime = overduetime;
    }

    public String getCompdate() {
        return compdate;
    }

    public void setCompdate(String compdate) {
        this.compdate = compdate;
    }

    public String getComptime() {
        return comptime;
    }

    public void setComptime(String comptime) {
        this.comptime = comptime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getOrgtxlogno() {
        return orgtxlogno;
    }

    public void setOrgtxlogno(String orgtxlogno) {
        this.orgtxlogno = orgtxlogno;
    }

    public String getOrgtxrefno() {
        return orgtxrefno;
    }

    public void setOrgtxrefno(String orgtxrefno) {
        this.orgtxrefno = orgtxrefno;
    }

    public String getOrgtxdate() {
        return orgtxdate;
    }

    public void setOrgtxdate(String orgtxdate) {
        this.orgtxdate = orgtxdate;
    }

    public String getOrgtxtime() {
        return orgtxtime;
    }

    public void setOrgtxtime(String orgtxtime) {
        this.orgtxtime = orgtxtime;
    }

    public String getOrgsettlmtdate() {
        return orgsettlmtdate;
    }

    public void setOrgsettlmtdate(String orgsettlmtdate) {
        this.orgsettlmtdate = orgsettlmtdate;
    }

    public String getSndtxlogno() {
        return sndtxlogno;
    }

    public void setSndtxlogno(String sndtxlogno) {
        this.sndtxlogno = sndtxlogno;
    }

    public String getSndtxdate() {
        return sndtxdate;
    }

    public void setSndtxdate(String sndtxdate) {
        this.sndtxdate = sndtxdate;
    }

    public String getSndtxtime() {
        return sndtxtime;
    }

    public void setSndtxtime(String sndtxtime) {
        this.sndtxtime = sndtxtime;
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

    public String getProductmsg() {
        return productmsg;
    }

    public void setProductmsg(String productmsg) {
        this.productmsg = productmsg;
    }

    public Long getOrderamt() {
        return orderamt;
    }

    public void setOrderamt(Long orderamt) {
        this.orderamt = orderamt;
    }

    public Long getPayamt() {
        return payamt;
    }

    public void setPayamt(Long payamt) {
        this.payamt = payamt;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

}