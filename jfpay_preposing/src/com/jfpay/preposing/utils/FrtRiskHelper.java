/**
 * Created on Jan 3, 2015
 */
package com.jfpay.preposing.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.log4j.Logger;

import com.jfpay.preposing.db.DataSourceFactory;
import com.jfpay.preposing.domain.BmCardSet;
import com.jfpay.preposing.domain.BmDailyDetail;
import com.jfpay.preposing.domain.BmDailyTotal;
import com.jfpay.preposing.domain.BmRechargeRule;
import com.jfpay.preposing.domain.BmRiskRule;
import com.jfpay.preposing.domain.Payorder;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;

import cn.hnae.tuxedojms.utils.DBHelper;

/**
 * Title : <br>
 * Project : jfpay_preposing <br>
 * Class : com.jfpay.preposing.utils.FrtRiskHelper <br>
 * Description : <br>
 * CopyRights (c) 2015 <br>
 * Company Yoolink Co,.Ltd <br>
 * Date : Jan 3, 2015 <br>
 * 
 * @author L7
 * @version 1.0
 */
public class FrtRiskHelper {
    static Logger logger = Logger.getLogger(FrtRiskHelper.class);
    private static FrtRiskHelper riskHelper = null;
    private Properties prop = null;
    final String PROP_PATH = "/home/weblogic/etc/riskctrl.properties";
    //final String PROP_PATH = "D:/kuaifu/workspace/jfpay_preposing1/src/com/jfpay/preposing/properties/riskctrl.properties";
    
    public static final String APP_TYPE_CASH = "JFPalCash.Req";
    public static final String APP_TYPE_ACCPAY = "JFPalAcctPay.Req";
    public static final String APP_TYPE_CDPAY = "JFPalCardPay.Req";
    public static final String APP_TYPE_ACCPAY_FOR_SOTRE = "JFPalCardPayforStore.Req";
    public static final String APP_TYPE_QUICKPAY = "QuickBankCardApply.Req";
    public static final String MCHT_ID_0001000001 = "0001000001";
    public static final String MCHT_ID_0002000002 = "0002000002";
    public static final String MCHT_ID_0002000003 = "0002000003";
    public static final String MCHT_ID_0005000001 = "0005000001";
    public static final String MCHT_ID_0002000001 = "0002000001";
    public static final String MCHT_ID_0001000006 = "0001000006";
    public static final String MCHT_ID_0004000001 = "0004000001";
    public static final String PRODUCT_ID_0000000001 = "0000000001";
    public static final String PRODUCT_ID_0000000000 = "0000000000";
    public static final String CASHTYPE_QUICK = "1";
    public static final String TX_TRANS_TYPE_1 = "1";
    public static final String SERVICE_CODE_100017 = "100017";
    public static final String SERVICE_CODE_100020 = "100020";
    
    private FrtRiskHelper() throws IOException {
        prop = new Properties();
        try {
            FileInputStream propFile = new FileInputStream(PROP_PATH);
            prop.load(propFile);
            propFile.close();
        } catch (IOException e) {
            prop = null;
            logger.error("|> Read configuration file error. " + e.toString(), e);
            throw e;
        }
    }
    
    public static FrtRiskHelper getInstance() throws IOException {
        if (null == riskHelper) {
            riskHelper = new FrtRiskHelper();
        }
        return riskHelper;
    }
    
    public Properties getProp() {
        return prop;
    }

    /**
     * Description : <br>
     * Created on Jan 4, 2015 1:32:00 PM <br>
     * 
     * @param reqmap
     * @return
     */
    public boolean vldQckcash(Map<String, String> reqmap) {
        boolean rlt = true;

        DBHelper dbcon = null;
        try {
            logger.info("VALIDATE_QUICK_CASH --------- reqmap:" + reqmap);
            dbcon = new DBHelper(DataSourceFactory.getDataSource());

            String appUser = reqmap.get(ParseCoreReceiveXml.APP_USER);
            String userid = reqmap.get(ParseCoreReceiveXml.MOBILE_NO);
            String cashamt = reqmap.get(ParseCoreReceiveXml.CASH_AMT);
            Long cashAmt = Long.parseLong(cashamt);
            Map<String, String> map = getCdNbrByUserid(appUser, userid, dbcon);
            String idcdNbr = map.get("customerpid");

            BmRiskRule bmRiskRule = new BmRiskRule();
            bmRiskRule.setMerchantid("0009000001");
            bmRiskRule.setProductid("0000000001");
            bmRiskRule = getRule4Qckcash(bmRiskRule, dbcon);
            if (null != bmRiskRule) { // 有效规则
                logger.info("------- BM_RISK_RULE: id["
                        + bmRiskRule.getRecord_no() + "], effect_time["
                        + bmRiskRule.getEffect_time() + "]");

                Long maxAmt = bmRiskRule.getMax_amount();

                Date effectTime = Format.parse(bmRiskRule.getEffect_time(), "yyyyMMddHHmmss");
                String currTime = Format.time();
                Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");

                if (inTime.after(effectTime)) { // 生效时间
                    BmDailyTotal bmDailyTotal = new BmDailyTotal();
                    bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                    bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                    bmDailyTotal.setRisk_key(idcdNbr);
                    bmDailyTotal = getBmDailyTotal(bmDailyTotal, dbcon);
                    if (null != bmDailyTotal) { // 更新_当日累计 nfree
                        Long totFreeAmt = bmDailyTotal.getFree_amount();
                        Long nowTotFreeAmt = totFreeAmt + cashAmt;
                        if (nowTotFreeAmt > maxAmt) { // 已超额
                            bmDailyTotal.setEdit_t(currTime);
                            updateBmDailyTotal(new String[] { "0", "0",
                                    cashamt, "1" }, bmDailyTotal, dbcon);
                            rlt = false;
                        } else { // 未超额
                        }
                    } else { // 新增_当日累计 nfree
                        bmDailyTotal = new BmDailyTotal();
                        Long totFreeAmt = 0L;
                        Long nowTotFreeAmt = totFreeAmt + cashAmt;
                        if (nowTotFreeAmt > maxAmt) { // 已超额
                            bmDailyTotal.setNfree_amount(cashAmt);
                            bmDailyTotal.setNfree_count(1L);
                            bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                            bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                            bmDailyTotal.setRisk_key(idcdNbr);
                            bmDailyTotal.setInput_t(currTime);
                            insertBmDailyTotal(bmDailyTotal, dbcon);
                            rlt = false;
                        } else { // 未超额
                        }
                    }
                }

            }

            dbcon.close();
        } catch (Exception e1) {
            rlt = false;
            logger.error("GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
        } finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("SQLException:" + e.getMessage());
                }
            dbcon = null;
        }
        return rlt;
    }

    /**
     * Description : 返回_实名身份证号 <br>
     * Created on Jan 20, 2015 10:49:11 PM <br>
     * 
     * @param appUser
     * @param userid
     * @param dbcon
     * @return
     * @throws SQLException
     */
    private Map<String, String> getCdNbrByUserid(String appUser,
            String userid, DBHelper dbcon) throws SQLException {
        Map<String, String> resultMap = new HashMap<String, String>();
        StringBuffer strBuf = new StringBuffer("select c.customerpid from PAYCUSTOMER c where c.customertag = '3'");
        strBuf.append(" and c.customerid =");
        strBuf.append("  (");
        strBuf.append("    select u.customerid from PAYUSER u");
        strBuf.append("    where u.userid = ? and u.branchid =");
        strBuf.append("      (");
        strBuf.append("        select t.branchid from PREP_CLIENT_VERSION t");
        strBuf.append("        where t.appuser = ? and rownum = 1");
        strBuf.append("      )");
        strBuf.append("  )");
        String sql = strBuf.toString();
        logger.info(sql);
        String param[] = { userid, appUser }; // /
        ResultSet rs = dbcon.query(sql, param);
        while (rs.next()) {
            resultMap.put("customerpid", rs.getString("customerpid"));
            // resultMap.put("cnt", rs.getString("cnt"));
        }
        logger.debug("--------------------------------------------- resultMap:"
                + resultMap);
        return resultMap;
    }
    
    /**
     * Description : <br>
     * Created on Jan 13, 2015 10:43:43 AM <br>
     * 
     * @param appUser
     * @param userid
     * @param dbcon
     * @return
     * @throws SQLException
     */
    private String getCustIdByUserid(String appUser, String userid,
            DBHelper dbcon) throws SQLException {
        String rlt = null;
        StringBuffer strBuf = new StringBuffer("select u.customerid from PAYUSER u");
        strBuf.append(" where u.userid = '").append(userid).append("' and u.branchid =");
        strBuf.append("   (");
        strBuf.append("      select t.branchid from PREP_CLIENT_VERSION t");
        strBuf.append("      where t.appuser = '").append(appUser).append("' and rownum = 1");
        strBuf.append("    )");
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rs.getString("customerId");
            logger.debug("--------------------------------------------- customerId:"
                    + rlt);
        }
        return rlt;
    }
    
    /**
     * Description : <br>
     * Created on Jan 13, 2015 11:02:40 AM <br>
     * 
     * @param customerId
     * @param cardIdx
     * @param cardTag
     * @param dbcon
     * @return
     * @throws SQLException
     */
    private String getCardnoByCustid(String customerId, String cardIdx,
            String cardTag, DBHelper dbcon) throws SQLException {
        String rlt = null;
        StringBuffer strBuf = new StringBuffer("select t.cardno from paybindbankcard2 t");
        strBuf.append(" where t.customerid = '").append(customerId).append("'");
        strBuf.append(" and substr(t.cardno, length(t.cardno) - 3, length(t.cardno)) = '").append(cardTag).append("'");
        strBuf.append(" and t.cardidx = ").append(cardIdx);
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rs.getString("cardno");
            logger.debug("--------------------------------------------- cardNo:"
                    + rlt);
        }
        return rlt;
    }
    
    /**
     * Description : <br>
     * Created on Jan 25, 2015 1:44:58 PM <br>
     * 
     * @param bmRiskRule
     * @param dbcon
     * @return
     * @throws SQLException
     */
    private BmRiskRule getEffRule(BmRiskRule bmRiskRule, DBHelper dbcon)
            throws SQLException {
        BmRiskRule rlt = null;
        StringBuffer strBuf = new StringBuffer("select * from BM_RISK_RULE t");
        strBuf.append(" where t.MERCHANTID = '").append(bmRiskRule.getMerchantid()).append("'");
        strBuf.append(" and t.PRODUCTID = '").append(bmRiskRule.getProductid()).append("'");
        strBuf.append(" and t.STATUS = '0'");
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rp.toBean(rs, BmRiskRule.class);
            logger.debug("--------------------------------------------- Rule4Qckcash:"
                    + rlt.getCtrl_mode());
        }
        return rlt;
    }

    private BmRiskRule getRule4Qckcash(BmRiskRule bmRiskRule,
            DBHelper dbcon) throws SQLException {
        BmRiskRule rlt = null;
        StringBuffer strBuf = new StringBuffer("select * from BM_RISK_RULE t");
        strBuf.append(" where t.MERCHANTID = '").append(bmRiskRule.getMerchantid()).append("'");
        strBuf.append(" and t.PRODUCTID = '").append(bmRiskRule.getProductid()).append("'");
        strBuf.append(" and t.KEY_TYPE = '1' and t.CTRL_MODE = 'yyyyMMdd'");
        strBuf.append(" and (ST_FLAG = ED_FLAG and ED_FLAG = substr(EFFECT_TIME, 1, 8))");
        strBuf.append(" and t.STATUS = '0'");
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rp.toBean(rs, BmRiskRule.class);
            logger.debug("--------------------------------------------- Rule4Qckcash:"
                    + rlt.getCtrl_mode());
        }
        return rlt;
    }
    
    private BmCardSet ldBmCardSet(BmCardSet bmCardSet, DBHelper dbcon)
            throws SQLException {
        BmCardSet rlt = null;
        StringBuffer strBuf = new StringBuffer("select * from BM_CARD_SET t");
        strBuf.append(" where t.CARDNO = '").append(bmCardSet.getCardno()).append("'");
        strBuf.append(" where t.MERCHANTID = '").append(bmCardSet.getMerchantid()).append("'");
        strBuf.append(" and t.PRODUCTID = '").append(bmCardSet.getProductid()).append("'");
        strBuf.append(" and t.ACC_TYPE = '1'");
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rp.toBean(rs, BmCardSet.class);
            logger.debug("--------------------------------------------- BmCardSet:"
                    + bmCardSet.getCardno());
        }
        return rlt;
    }
    
    private Payorder ldPayorder(Payorder payorder, DBHelper dbcon)
            throws SQLException {
        Payorder rlt = null;
        StringBuffer strBuf = new StringBuffer("select * from PAYORDER t");
        strBuf.append(" where t.orderid = '").append(payorder.getOrderid()).append("'");
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rp.toBean(rs, Payorder.class);
            logger.debug("--------------------------------------------- Payorder:"
                    + payorder.getProductmsg());
        }
        return rlt;
    }
    
    private BmRiskRule getRule4Newcard(BmRiskRule bmRiskRule,
            DBHelper dbcon) throws SQLException {
        BmRiskRule rlt = null;
        StringBuffer strBuf = new StringBuffer("select * from BM_RISK_RULE t");
        strBuf.append(" where t.MERCHANTID = '").append(bmRiskRule.getMerchantid()).append("'");
        strBuf.append(" and t.PRODUCTID = '").append(bmRiskRule.getProductid()).append("'");
        strBuf.append(" and t.KEY_TYPE = '3' and t.CTRL_MODE = 'HHmmss'");
        strBuf.append(" and (ST_FLAG = ED_FLAG and ED_FLAG = substr(EFFECT_TIME, 9, 14))");
        strBuf.append(" and t.STATUS = '0'");
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rp.toBean(rs, BmRiskRule.class);
            logger.debug("--------------------------------------------- Rule4Newcard:"
                    + rlt.getCtrl_mode());
        }
        return rlt;
    }

    private BmDailyTotal getBmDailyTotal(BmDailyTotal bmDailyTotal,
            DBHelper dbcon) throws SQLException {
        BmDailyTotal rlt = null;
        StringBuffer strBuf = new StringBuffer("select * from BM_DAILY_TOTAL t");
        strBuf.append(" where t.curr_date = '").append(bmDailyTotal.getCurr_date()).append("'");
        strBuf.append(" and t.rule_no = '").append(bmDailyTotal.getRule_no()).append("'");
        strBuf.append(" and t.risk_key = '").append(bmDailyTotal.getRisk_key()).append("'");
        String sql = strBuf.toString();
        logger.info(sql);
        PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        if (null != rs && rs.first()) {
            BasicRowProcessor rp = new BasicRowProcessor();
            rlt = rp.toBean(rs, BmDailyTotal.class);
            logger.debug("--------------------------------------------- BmDailyTotal:"
                    + rlt.getFree_amount());
        }
        return rlt;
    }

    private int updateBmDailyTotal(String[] cashAmt,
            BmDailyTotal bmDailyTotal, DBHelper dbcon) throws SQLException {
        StringBuffer strBuf = new StringBuffer("update BM_DAILY_TOTAL");
        strBuf.append(" set free_amount = free_amount + ").append(cashAmt[0]);
        strBuf.append(", free_count = free_count + ").append(cashAmt[1]);
        strBuf.append(", nfree_amount = nfree_amount + ").append(cashAmt[2]);
        strBuf.append(", nfree_count = nfree_count + ").append(cashAmt[3]);
        strBuf.append(", edit_t = '").append(bmDailyTotal.getEdit_t()).append("'");
        strBuf.append(" where curr_date = '").append(bmDailyTotal.getCurr_date()).append("'");
        strBuf.append(" and rule_no = '").append(bmDailyTotal.getRule_no()).append("'");
        strBuf.append(" and risk_key = '").append(bmDailyTotal.getRisk_key()).append("'");
        String sql = strBuf.toString();
        int n = dbcon.update(sql);
        logger.info(sql + " [" + n + "]");
        return n;
    }

    private int insertBmDailyTotal(BmDailyTotal bmDailyTotal,
            DBHelper dbcon) throws SQLException {
        StringBuffer strBuf = new StringBuffer("insert into BM_DAILY_TOTAL(curr_date, rule_no, risk_key, free_amount, free_count, nfree_amount, nfree_count, input_t)");
        strBuf.append(" values('").append(bmDailyTotal.getCurr_date()).append("'");
        strBuf.append(", '").append(bmDailyTotal.getRule_no()).append("'");
        strBuf.append(", '").append(bmDailyTotal.getRisk_key()).append("'");
        strBuf.append(", ").append(null != bmDailyTotal.getFree_amount() ? bmDailyTotal.getFree_amount() : "0");
        strBuf.append(", ").append(null != bmDailyTotal.getFree_count() ? bmDailyTotal.getFree_count() : "0");
        strBuf.append(", ").append(null != bmDailyTotal.getNfree_amount() ? bmDailyTotal.getNfree_amount() : "0");
        strBuf.append(", ").append(null != bmDailyTotal.getNfree_count() ? bmDailyTotal.getNfree_count() : "0");
        strBuf.append(", '").append(bmDailyTotal.getInput_t()).append("')");
        String sql = strBuf.toString();
        int n = dbcon.insert(sql);
        logger.info(sql + " [" + n + "]");
        return n;
    }
    
    private int insertBmDailyDetail(BmDailyDetail bmDailyDetail,
            DBHelper dbcon) throws SQLException {
        StringBuffer strBuf = new StringBuffer("insert into BM_DAILY_DETAIL(daily_det_id");
        strBuf.append(", curr_date, curr_time, rule_no, risk_key");
        strBuf.append(", amount, islegal)");
        strBuf.append(" values(SEQ_BM_DAILY_DETAIL.nextval");
        strBuf.append(", '").append(bmDailyDetail.getCurr_date()).append("'");
        strBuf.append(", '").append(bmDailyDetail.getCurr_time()).append("'");
        strBuf.append(", '").append(bmDailyDetail.getRule_no()).append("'");
        strBuf.append(", '").append(bmDailyDetail.getRisk_key()).append("'");
        strBuf.append(", ").append(bmDailyDetail.getAmount());
        strBuf.append(", '").append(bmDailyDetail.getIslegal()).append("')");
        String sql = strBuf.toString();
        int n = dbcon.insert(sql);
        logger.info(sql + " [" + n + "]");
        return n;
    }
    
    private int insertBmCardSet(BmCardSet bmCardSet,
            DBHelper dbcon) throws SQLException {
        StringBuffer strBuf = new StringBuffer("insert into BM_CARD_SET(");
        strBuf.append("cardno, merchantid, productid, acc_type");
        strBuf.append(", status, last_req_time, last_txn_time, isnew, isallow)");
        strBuf.append(" values(");
        strBuf.append("  '").append(bmCardSet.getCardno()).append("'");
        strBuf.append(", '").append(bmCardSet.getMerchantid()).append("'");
        strBuf.append(", '").append(bmCardSet.getProductid()).append("'");
        strBuf.append(", '").append(bmCardSet.getAcc_type()).append("'");
        strBuf.append(", '").append(bmCardSet.getStatus()).append("'");
        strBuf.append(", '").append(bmCardSet.getLast_txn_time()).append("'");
        strBuf.append(", '").append(bmCardSet.getIsnew()).append("'");
        strBuf.append(", '").append(bmCardSet.getIsallow()).append("')");
        String sql = strBuf.toString();
        int n = dbcon.insert(sql);
        logger.info(sql + " [" + n + "]");
        return n;
    }

    /**
     * Description : <br>
     * Created on Jan 6, 2015 2:25:54 PM <br>
     * 
     * @param reqmap
     */
    public void updQckcash(Map<String, String> resmap) {

        DBHelper dbcon = null;
        try {
            if ("0000".equals(resmap.get("MSG_CODE"))) {
                logger.info("|> UPDATE_QUICK_CASH --------- resmap:" + resmap);
                dbcon = new DBHelper(DataSourceFactory.getDataSource());

                String appUser = resmap.get("TX_APP_USER");
                String userid = resmap.get("TX_USER_ID");
                String cashamt = resmap.get("TX_ORDER_AMOUNT");
                Long cashAmt = Long.parseLong(cashamt);
                Map<String, String> map = getCdNbrByUserid(appUser, userid, dbcon);
                String idcdNbr = map.get("customerpid");

                BmRiskRule bmRiskRule = new BmRiskRule();
                bmRiskRule.setMerchantid("0009000001");
                bmRiskRule.setProductid("0000000001");
                bmRiskRule = getRule4Qckcash(bmRiskRule, dbcon);
                if (null != bmRiskRule) { // 有效规则
                    logger.info("|> ------- BM_RISK_RULE: id["
                            + bmRiskRule.getRecord_no() + "], effect_time["
                            + bmRiskRule.getEffect_time() + "]");

                    // Long maxAmt = bmRiskRule.getMax_amount();

                    Date effectTime = Format.parse(bmRiskRule.getEffect_time(), "yyyyMMddHHmmss");
                    String currTime = Format.time();
                    Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");

                    if (inTime.after(effectTime)) { // 生效时间
                        BmDailyTotal bmDailyTotal = new BmDailyTotal();
                        bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                        bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                        bmDailyTotal.setRisk_key(idcdNbr);
                        bmDailyTotal = getBmDailyTotal(bmDailyTotal, dbcon);
                        if (null != bmDailyTotal) { // 更新_当日累计 free
                            bmDailyTotal.setFree_count(bmDailyTotal.getFree_count() + 1);
                            bmDailyTotal.setEdit_t(currTime);
                            updateBmDailyTotal(new String[] { cashamt, "1",
                                    "0", "0" }, bmDailyTotal, dbcon);
                        } else { // 新增_当日累计 free
                            bmDailyTotal = new BmDailyTotal();
                            bmDailyTotal.setFree_amount(cashAmt);
                            bmDailyTotal.setFree_count(1L);
                            bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                            bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                            bmDailyTotal.setRisk_key(idcdNbr);
                            bmDailyTotal.setInput_t(currTime);
                            insertBmDailyTotal(bmDailyTotal, dbcon);
                        }
                    }
                }
            }
        } catch (Exception e1) {
            logger.error("|> GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
        } finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("|> SQLException:" + e.getMessage());
                }
            dbcon = null;
        }

    }
    
    /**
     * Description : <br>
     * Created on Jan 25, 2015 6:23:04 PM <br>
     * 
     * @param resmap
     */
    public void updTgtMobnbr(Map<String, String> resmap) {

        DBHelper dbcon = null;
        try {
            if ("0000".equals(resmap.get("MSG_CODE"))) {
                logger.info("|> UPDATE_TARGET_CHARGE --------- resmap:"
                        + resmap);
                dbcon = new DBHelper(DataSourceFactory.getDataSource());

                String orderId = resmap.get("TX_ORDER_NO");
                String chargeamt = resmap.get("TX_ORDER_AMOUNT");
                Long chargeAmt = Long.parseLong(chargeamt);

                BmRiskRule bmRiskRule = new BmRiskRule();
                bmRiskRule.setMerchantid("0001000001");
                bmRiskRule.setProductid("0000000000");
                bmRiskRule = getEffRule(bmRiskRule, dbcon);
                if (null != bmRiskRule) {
                    String ctrlMode = bmRiskRule.getCtrl_mode();
                    String effTime = bmRiskRule.getEffect_time();

                    if (bmRiskRule.getSt_flag().equals(bmRiskRule.getEd_flag())
                            && bmRiskRule.getEd_flag().equals(effTime.substring(0, ctrlMode.length()))) { // 有效规则
                        logger.info("------- BM_RISK_RULE: id["
                                + bmRiskRule.getRecord_no() + "], effect_time["
                                + bmRiskRule.getEffect_time() + "]");

                        // Long maxAmt = bmRiskRule.getMax_amount();

                        Date effectTime = Format.parse(effTime, "yyyyMMddHHmmss");
                        String currTime = Format.time();
                        Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");

                        if (inTime.after(effectTime)) { // 生效时间
                            // String keyType = bmRiskRule.getKey_type();
                            Payorder payorder = new Payorder();
                            payorder.setOrderid(orderId);
                            payorder = ldPayorder(payorder, dbcon);
                            if (null != payorder) {
                                String tgtMobnbr = payorder.getProductmsg();

                                BmDailyTotal bmDailyTotal = new BmDailyTotal();
                                bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                                bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                                bmDailyTotal.setRisk_key(tgtMobnbr);
                                bmDailyTotal = getBmDailyTotal(bmDailyTotal, dbcon);
                                if (null != bmDailyTotal) { // 更新_当日累计 free
                                    bmDailyTotal.setFree_count(bmDailyTotal.getFree_count() + 1);
                                    bmDailyTotal.setEdit_t(currTime);
                                    updateBmDailyTotal(new String[] {
                                            chargeamt, "1", "0", "0" }, bmDailyTotal, dbcon);
                                } else { // 新增_当日累计 free
                                    bmDailyTotal = new BmDailyTotal();
                                    bmDailyTotal.setFree_amount(chargeAmt);
                                    bmDailyTotal.setFree_count(1L);
                                    bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                                    bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                                    bmDailyTotal.setRisk_key(tgtMobnbr);
                                    bmDailyTotal.setInput_t(currTime);
                                    insertBmDailyTotal(bmDailyTotal, dbcon);
                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e1) {
            logger.error("|> GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
        } finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("|> SQLException:" + e.getMessage());
                }
            dbcon = null;
        }

    }
    
    /**
     * Description : <br>
     * Created on Jan 14, 2015 11:04:54 AM <br>
     * 
     * @param resmap
     */
    public void updNewcard(Map<String, String> resmap) {
        DBHelper dbcon = null;
        try {
            if ("0000".equals(resmap.get("MSG_CODE"))) {
                logger.info("|> UPDATE_NEW_CARD --------- resmap:" + resmap);
                dbcon = new DBHelper(DataSourceFactory.getDataSource());
                
                String appUser = resmap.get(ParseCoreReceiveXml.APP_USER);
                String userid = resmap.get(ParseCoreReceiveXml.MOBILE_NO);
                String cashamt = resmap.get(ParseCoreReceiveXml.CASH_AMT);
                Long cashAmt = Long.parseLong(cashamt);
                String cardIdx = resmap.get(ParseCoreReceiveXml.CARD_INDEX);
                String cardTag = resmap.get(ParseCoreReceiveXml.CARD_TAG);
                
                BmRiskRule bmRiskRule = new BmRiskRule();
                bmRiskRule.setMerchantid("0009000001");
                bmRiskRule.setProductid("0000000001");
                bmRiskRule = getRule4Newcard(bmRiskRule, dbcon);
                if (null != bmRiskRule) { // 有效规则
                    logger.info("|> ------- BM_RISK_RULE: id["
                            + bmRiskRule.getRecord_no() + "], effect_time["
                            + bmRiskRule.getEffect_time() + "]");
                    
//                    Long maxAmt = bmRiskRule.getMax_amount();
                    // Long maxCnt = bmRiskRule.getMax_count();

                    Date effectTime = Format.parse(bmRiskRule.getEffect_time(), "yyyyMMddHHmmss");
                    String currTime = Format.time();
                    Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");

                    if (inTime.after(effectTime)) { // 生效时间
                        String customerId = getCustIdByUserid(appUser, userid, dbcon);
                        String cardNo = getCardnoByCustid(customerId, cardIdx, cardTag, dbcon);

                        BmCardSet bmCardSet = new BmCardSet();
                        bmCardSet.setCardno(cardNo);
                        bmCardSet.setMerchantid("0009000001");
                        bmCardSet.setProductid("0000000001");
                        bmCardSet = ldBmCardSet(bmCardSet, dbcon);

                        if (null != bmCardSet) { // 卡库不存在
                            bmCardSet = new BmCardSet(); // 新增_卡库
                            bmCardSet.setCardno(cardNo);
                            bmCardSet.setMerchantid("0009000001");
                            bmCardSet.setProductid("0000000001");
                            bmCardSet.setAcc_type("1");
                            bmCardSet.setStatus("00");
                            bmCardSet.setLast_txn_time(currTime);
                            bmCardSet.setIsnew("1");
                            bmCardSet.setIsallow("1");
                            insertBmCardSet(bmCardSet, dbcon);
                        }
                    }
                }

            }
        } catch (Exception e1) {
            logger.error("|> GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
        } finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("|> SQLException:" + e.getMessage());
                }
            dbcon = null;
        }
    }
    
    /**
     * Description : 验证_提现新卡 <br>
     * Created on Jan 12, 2015 7:24:51 PM <br>
     * 
     * @param reqmap
     * @return
     */
    public boolean vldNewcard(Map<String, String> reqmap) {
        boolean rlt = true;

        DBHelper dbcon = null;
        try {
            logger.info("VALIDATE_NEW_CARD --------- reqmap:" + reqmap);
            dbcon = new DBHelper(DataSourceFactory.getDataSource());

            String appUser = reqmap.get(ParseCoreReceiveXml.APP_USER);
            String userid = reqmap.get(ParseCoreReceiveXml.MOBILE_NO);
            String cashamt = reqmap.get(ParseCoreReceiveXml.CASH_AMT);
            Long cashAmt = Long.parseLong(cashamt);
            String cardIdx = reqmap.get(ParseCoreReceiveXml.CARD_INDEX);
            String cardTag = reqmap.get(ParseCoreReceiveXml.CARD_TAG);

            BmRiskRule bmRiskRule = new BmRiskRule();
            bmRiskRule.setMerchantid("0009000001");
            bmRiskRule.setProductid("0000000001");
            bmRiskRule = getRule4Newcard(bmRiskRule, dbcon);
            if (null != bmRiskRule) { // 有效规则
                logger.info("------- BM_RISK_RULE: id["
                        + bmRiskRule.getRecord_no() + "], effect_time["
                        + bmRiskRule.getEffect_time() + "]");

                Long maxAmt = bmRiskRule.getMax_amount();
                // Long maxCnt = bmRiskRule.getMax_count();

                Date effectTime = Format.parse(bmRiskRule.getEffect_time(), "yyyyMMddHHmmss");
                String currTime = Format.time();
                Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");

                if (inTime.after(effectTime)) { // 生效时间
                    String customerId = getCustIdByUserid(appUser, userid, dbcon);
                    String cardNo = getCardnoByCustid(customerId, cardIdx, cardTag, dbcon);

                    BmCardSet bmCardSet = new BmCardSet();
                    bmCardSet.setCardno(cardNo);
                    bmCardSet.setMerchantid("0009000001");
                    bmCardSet.setProductid("0000000001");
                    bmCardSet = ldBmCardSet(bmCardSet, dbcon);

                    if (null != bmCardSet) { // 卡库不存在
                        if (cashAmt > maxAmt) { // 已超额
                            BmDailyDetail bmDailyDetail = new BmDailyDetail(); // 新增_当日明细
                            // nfree
                            bmDailyDetail.setCurr_date(Format.getNow("yyyyMMdd"));
                            bmDailyDetail.setCurr_time(Format.getNow("HHmmss"));
                            bmDailyDetail.setRule_no(bmRiskRule.getRecord_no());
                            bmDailyDetail.setRisk_key(cardNo);
                            bmDailyDetail.setAmount(cashAmt);
                            bmDailyDetail.setIslegal("1");
                            insertBmDailyDetail(bmDailyDetail, dbcon);
                            rlt = false;
                        } else { // 未超额
                        }

                    } else { // 卡库存在
                        if (!"00".equals(bmCardSet.getStatus())
                                || "0".equals(bmCardSet.getIsallow())) { // 状态异常，或不允许交易
                            // 对应返回相关错误提示 FIXME
                            rlt = false;
                        } else if ("1".equals(bmCardSet.getIsnew())) { // 新卡
                            String allowTxnTime = Format.getOfsHour(bmCardSet.getLast_txn_time(), "yyyyMMddHHmmss", 24);
                            Date allowTime = Format.parse(allowTxnTime, "yyyyMMddHHmmss");
                            if (!inTime.after(allowTime)) { // 非法时间
                                BmDailyDetail bmDailyDetail = new BmDailyDetail(); // 新增_当日明细
                                // nfree
                                bmDailyDetail.setCurr_date(Format.getNow("yyyyMMdd"));
                                bmDailyDetail.setCurr_time(Format.getNow("HHmmss"));
                                bmDailyDetail.setRule_no(bmRiskRule.getRecord_no());
                                bmDailyDetail.setRisk_key(cardNo);
                                bmDailyDetail.setAmount(cashAmt);
                                bmDailyDetail.setIslegal("1");
                                insertBmDailyDetail(bmDailyDetail, dbcon);
                                rlt = false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e1) {
            rlt = false;
            logger.error("GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
        } finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("SQLException:" + e.getMessage());
                }
            dbcon = null;
        }
        return rlt;

    }
    
    /**
     * Description : <br>
     * Created on Jan 25, 2015 1:36:27 PM <br>
     * 
     * @param reqmap
     * @return
     */
    public boolean vldTgtMobnbr(Map<String, String> reqmap) {
        boolean rlt = true;

        DBHelper dbcon = null;
        try {
            logger.info("VALIDATE_TARGET_CHARGE --------- reqmap:" + reqmap);
            dbcon = new DBHelper(DataSourceFactory.getDataSource());

            String orderId = reqmap.get(ParseCoreReceiveXml.ORDER_ID);
            String chargeamt = reqmap.get(ParseCoreReceiveXml.ORDER_AMT);
            Long chargeAmt = Long.parseLong(chargeamt);

            BmRiskRule bmRiskRule = new BmRiskRule();
            bmRiskRule.setMerchantid("0001000001");
            bmRiskRule.setProductid("0000000000");
            bmRiskRule = getEffRule(bmRiskRule, dbcon);
            if (null != bmRiskRule) {
                String ctrlMode = bmRiskRule.getCtrl_mode();
                String effTime = bmRiskRule.getEffect_time();

                if (bmRiskRule.getSt_flag().equals(bmRiskRule.getEd_flag())
                        && bmRiskRule.getEd_flag().equals(effTime.substring(0, ctrlMode.length()))) { // 有效规则
                    logger.info("------- BM_RISK_RULE: id["
                            + bmRiskRule.getRecord_no() + "], effect_time["
                            + bmRiskRule.getEffect_time() + "]");

                    Long maxAmt = bmRiskRule.getMax_amount();

                    Date effectTime = Format.parse(effTime, "yyyyMMddHHmmss");
                    String currTime = Format.time();
                    Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");

                    if (inTime.after(effectTime)) { // 生效时间
                        // String keyType = bmRiskRule.getKey_type();
                        Payorder payorder = new Payorder();
                        payorder.setOrderid(orderId);
                        payorder = ldPayorder(payorder, dbcon);
                        if (null != payorder) {
                            String tgtMobnbr = payorder.getProductmsg();

                            BmDailyTotal bmDailyTotal = new BmDailyTotal();
                            bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                            bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                            bmDailyTotal.setRisk_key(tgtMobnbr);
                            bmDailyTotal = getBmDailyTotal(bmDailyTotal, dbcon);
                            if (null != bmDailyTotal) { // 更新_当日累计 nfree
                                Long totFreeAmt = bmDailyTotal.getFree_amount();
                                Long nowTotFreeAmt = totFreeAmt + chargeAmt;
                                if (nowTotFreeAmt > maxAmt) { // 已超额
                                    bmDailyTotal.setEdit_t(currTime);
                                    updateBmDailyTotal(new String[] { "0", "0",
                                            chargeamt, "1" }, bmDailyTotal, dbcon);
                                    rlt = false;
                                } else { // 未超额
                                }
                            } else { // 新增_当日累计 nfree
                                bmDailyTotal = new BmDailyTotal();
                                Long totFreeAmt = 0L;
                                Long nowTotFreeAmt = totFreeAmt + chargeAmt;
                                if (nowTotFreeAmt > maxAmt) { // 已超额
                                    bmDailyTotal.setNfree_amount(chargeAmt);
                                    bmDailyTotal.setNfree_count(1L);
                                    bmDailyTotal.setCurr_date(Format.getNow("yyyyMMdd"));
                                    bmDailyTotal.setRule_no(bmRiskRule.getRecord_no());
                                    bmDailyTotal.setRisk_key(tgtMobnbr);
                                    bmDailyTotal.setInput_t(currTime);
                                    insertBmDailyTotal(bmDailyTotal, dbcon);
                                    rlt = false;
                                } else { // 未超额
                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e1) {
            rlt = false;
            logger.error("GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
        } finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("SQLException:" + e.getMessage());
                }
            dbcon = null;
        }
        return rlt;

    }
    /**
     * 单个用户手机流量限额控制
     * @author horace
     * @since 1.6
     * created on 2016-05-20
     * @param reqmap xml 请求的xml
     * @return boolean true代表未超额。false代表已超额
     */
    public boolean vrdPhoneFlowLimit(Map<String, String> reqmap){
    	boolean rlt = true;
    	DBHelper dbcon = null;
    	try {
    		 logger.info("VALIDATE_vrdPhoneFlowLimit --------- reqmap:" + reqmap);
    		 dbcon = new DBHelper(DataSourceFactory.getDataSource());
    		 String appUser = reqmap.get(ParseCoreReceiveXml.APP_USER);//app名称
             String userid = reqmap.get(ParseCoreReceiveXml.MOBILE_NO);//
             String chargeamt = reqmap.get(ParseCoreReceiveXml.ORDER_AMT);//订单金额
             Long chargeAmt = Long.parseLong(chargeamt);
             String now=Format.getNow("yyyyMMdd");
             String merchantid="0001000006";
             String productid="0000000000";//merchantid为0001000006和productid为0000000000的为流量充值
             BmRechargeRule bmRechargeRule = new BmRechargeRule();
             bmRechargeRule.setMerchantid(merchantid);
             bmRechargeRule.setProductid(productid);
             bmRechargeRule=getRechargeRlue(bmRechargeRule,dbcon);//获取规则
             if (null!=bmRechargeRule) {
				if ("0".equals(bmRechargeRule.getStatus())) {//使用规则
	                String effTime = bmRechargeRule.getEffect_time();//获取生效时间
	                logger.info("------- BM_Recharge_RULE: id["
                            + bmRechargeRule.getRecord_no() + "], effect_time["
                            + bmRechargeRule.getEffect_time() + "]");
                    Long maxAmt = bmRechargeRule.getMax_amount();//获取规则最大金额
                    Date effectTime = Format.parse(effTime, "yyyyMMddHHmmss");
                    String currTime = Format.time();
                    Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");
                    if (inTime.after(effectTime)) { // 生效时间
                    	String amt=getPhoneRechargeSum(appUser,userid,now,merchantid,productid,dbcon);//获取总金额
                    	logger.info("**********************************amt*****************"+amt);
                    	if (amt!=null&&!"".equals(amt)) {
                    		Long nowTotFreeAmt = Long.parseLong(amt) + chargeAmt;//已有总金额和订单金额之和
                    		logger.info("**********************************nowTotFreeAmt*****************"+nowTotFreeAmt);
                    		if (nowTotFreeAmt>maxAmt) {//已超额
                    			logger.info("*****************已超额*****************");
                    			rlt=false;//修改标识
                    		}
                    	}
                    }
				}
			}
		} catch (Exception e1) {
			  rlt = false;
	         logger.error("GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
		}finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("SQLException:" + e.getMessage());
                }
            dbcon = null;
        }
    	return rlt;
    }
    /**
     * 获取充值限额规则
     * @param bmRechargeRule
     * @param dbcon 数据源对象
     * @return 规则
     * @throws SQLException
     */
    private BmRechargeRule getRechargeRlue(BmRechargeRule bmRechargeRule, DBHelper dbcon) throws SQLException {
		// TODO Auto-generated method stub
    	  BmRechargeRule rlt = null;
          StringBuffer strBuf = new StringBuffer("select * from BM_RECHARGE_RULE t");
          strBuf.append(" where t.MERCHANTID = '").append(bmRechargeRule.getMerchantid()).append("'");
          strBuf.append(" and t.PRODUCTID = '").append(bmRechargeRule.getProductid()).append("'");
          String sql = strBuf.toString();
          logger.info(sql);
          PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = ps.executeQuery();
          if (null != rs && rs.first()) {
              BasicRowProcessor rp = new BasicRowProcessor();
              rlt = rp.toBean(rs, BmRechargeRule.class);
              logger.debug("--------------------------------------------- Rule4Qckcash:"
                      + rlt.getCtrl_mode());
          }
          return rlt;
	}

	/**
     * 每个用户手机充值限制
     * @param reqmap 请求的xml
     * @author horace 
     * @version  1.0
     * @since jdk 1.6
     * Created on 2016-05-20
     * @return true 代表 未超额， false 代表已超额
     */
    public boolean vrdPhoneRechargeLimit(Map<String, String> reqmap){
    	boolean rlt = true;
    	DBHelper dbcon = null;
    	try {
    		 logger.info("VALIDATE_PhoneRechargeLimit --------- reqmap:" + reqmap);
    		 dbcon = new DBHelper(DataSourceFactory.getDataSource());
    		 String appUser = reqmap.get(ParseCoreReceiveXml.APP_USER);//app名称
             String userid = reqmap.get(ParseCoreReceiveXml.MOBILE_NO);
             String chargeamt = reqmap.get(ParseCoreReceiveXml.ORDER_AMT);//订单金额
             Long chargeAmt = Long.parseLong(chargeamt);
             String now=Format.getNow("yyyyMMdd");
             String merchantid="0001000001";
             String productid="0000000000";//merchantid为0001000001和productid为0000000000的手机充值
             BmRiskRule bmRiskRule = new BmRiskRule();
             bmRiskRule.setMerchantid(merchantid);
             bmRiskRule.setProductid(productid);
             BmRechargeRule bmRechargeRule = new BmRechargeRule();
             bmRechargeRule.setMerchantid(merchantid);
             bmRechargeRule.setProductid(productid);
             bmRechargeRule=getRechargeRlue(bmRechargeRule,dbcon);//获取充值规则
             if (null!=bmRechargeRule) {
				if ("0".equals(bmRechargeRule.getStatus())) {//当status为0的时候，使用充值规则
	                String effTime = bmRechargeRule.getEffect_time();//生效时间
	                logger.info("------- BM_Recharge_RULE: id["
                            + bmRechargeRule.getRecord_no() + "], effect_time["
                            + bmRechargeRule.getEffect_time() + "]");
                    Long maxAmt = bmRechargeRule.getMax_amount();//规则最大金额
                    Date effectTime = Format.parse(effTime, "yyyyMMddHHmmss");
                    String currTime = Format.time();
                    Date inTime = Format.parse(currTime, "yyyyMMddHHmmss");
                    if (inTime.after(effectTime)) { // 生效时间
                    	String amt=getPhoneRechargeSum(appUser,userid,now,merchantid,productid,dbcon);
                    	logger.info("**********************************amt*****************"+amt);
                    	if (amt!=null&&!"".equals(amt)) {
                    		Long nowTotFreeAmt = Long.parseLong(amt) + chargeAmt;//已有总金额加上传送过来的订单金额的和
                    		logger.info("**********************************nowTotFreeAmt*****************"+nowTotFreeAmt);
                    		if (nowTotFreeAmt>maxAmt) {//已超额
                    			logger.info("*****************已超额*****************");
                    			rlt=false;
                    		}
                    	}
                    }
				}
			}
    	} catch (Exception e1) {
			  rlt = false;
	         logger.error("GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
		}finally {
          // 关闭数据库连接
          if (dbcon != null)
              try {
                  dbcon.close();
              } catch (SQLException e) {
                  logger.error("SQLException:" + e.getMessage());
              }
          dbcon = null;
      }
    	return rlt;
    }
    /**
     * 根据下列条件统计已有金额
     * @author horace
     * @param appUser app名称
     * @param userid 用户id
     * @param now 现在的时间
     * @param merchantid 商户id
     * @param productid 产品id
     * @param dbcon 数据源对象
     * @throws SQLException 
     */
    private String getPhoneRechargeSum(String appUser, String userid, String now,
			String merchantid, String productid,DBHelper dbcon) throws SQLException {
    	 String rlt = null;
         StringBuffer strBuf = new StringBuffer("select  case when sum(payamt) is null then 0 else sum(payamt) end as amt from payorder where ");
         strBuf.append("orderdate='"+now+"' and merchantid='"+merchantid+"' and productid='"+productid+"' and flag='0' ");
         strBuf.append(" and customerid=(select customerid from payuser where userid='"+userid+"' ");
         strBuf.append(" and branchid=(select description from appuser where name='"+appUser+"'))");
         String sql = strBuf.toString();
         logger.info(sql);
         PreparedStatement ps = dbcon.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
         ResultSet rs = ps.executeQuery();
         while (rs.next()) {
        	rlt=rs.getString("amt");
		}
         return rlt;
		
	}
    /**
     * Description : 验证_实名认证 <br>
     * Created on Jan 20, 2015 6:01:43 PM <br>
     * 
     * @param reqmap
     * @return
     */
    public boolean vldRealname(Map<String, String> reqmap) {
        boolean rlt = true;

        DBHelper dbcon = null;
        try {
            logger.info("VALIDATE_REAL_NAME --------- reqmap:" + reqmap);
            dbcon = new DBHelper(DataSourceFactory.getDataSource());

            String appUser = reqmap.get(ParseCoreReceiveXml.APP_USER);
            String userid = reqmap.get(ParseCoreReceiveXml.MOBILE_NO);

            Map<String, String> map = getCdNbrByUserid(appUser, userid, dbcon);
            String idcdNbr = map.get("customerpid");
            if (null == idcdNbr) rlt = false;

        } catch (Exception e1) {
            rlt = false;
            logger.error("GET_DATABASE_CONNECTION_ERROR " + e1.toString(), e1);
        } finally {
            // 关闭数据库连接
            if (dbcon != null)
                try {
                    dbcon.close();
                } catch (SQLException e) {
                    logger.error("SQLException:" + e.getMessage());
                }
            dbcon = null;
        }
        logger.info("VALIDATE_REAL_NAME --------- result:" + rlt);
        return rlt;

    }

}
