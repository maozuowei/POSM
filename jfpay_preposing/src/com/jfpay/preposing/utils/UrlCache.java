package com.jfpay.preposing.utils;

import java.util.HashMap;
import java.util.Map;

public class UrlCache
{
  public static final Map<String, String> appConfig = new HashMap();
  public static final int INVOKE_HTTP = 1;
  public static final int INVOKE_TPCALL = 2;
  public static final int INVOKE_QUEUE = 3;
  public static final int INVOKE_WEBSERVICE = 4;
  public static final int SYNCHEONIZE = 5;
  public static String POST_URL_RESOURCE = "";

  public static String POST_URL_NOTE = "";

  public static String POST_URL_LOTTERY = "http://192.168.1.237:7001/Lottery/lotteryService.do";

  public static String POST_URL_ORDER = "";

  public static String POST_URL_PUBLIC = "";

  public static String POST_URL_TRAIN = "";
		

  public static String POST_URL_ACCOUNT = "";

  static
  {
    appConfig.put("UserRegister.Req", "com.jfpay.preposing.reqhandle.UserRegister");

    appConfig.put("UserLogin.Req", "com.jfpay.preposing.reqhandle.UserLogin");

    appConfig.put("UserUpdatePwd.Req", "com.jfpay.preposing.reqhandle.UserUpdatePwd");

    appConfig.put("UserUpdateInfo.Req", "com.jfpay.preposing.reqhandle.UserUpdateInfo");

    appConfig.put("GetMobileMac.Req", "com.jfpay.preposing.reqhandle.GetMobileMac");

    appConfig.put("JFPalAcctEnquiry.Req", "com.jfpay.preposing.reqhandle.JFPalAcctEnquiry");

    appConfig.put("GetJFPalDetail.Req", "com.jfpay.preposing.reqhandle.GetJFPalDetail");

    appConfig.put("UserCardBind.Req", "com.jfpay.preposing.reqhandle.UserCardBind");

    appConfig.put("GetUserCardList.Req", "com.jfpay.preposing.reqhandle.GetUserCardList");

    appConfig.put("UserCardBalanceEnquiry.Req", "com.jfpay.preposing.reqhandle.UserCardBalanceEnquiry");

    appConfig.put("UserCardMerger.Req", "com.jfpay.preposing.reqhandle.UserCardMerger");

    appConfig.put("UserCardUnBind.Req", "com.jfpay.preposing.reqhandle.UserCardUnBind");

    appConfig.put("BankCardBind.Req", "com.jfpay.preposing.reqhandle.BankCardBind");

    appConfig.put("GetBankCardList.Req", "com.jfpay.preposing.reqhandle.GetBankCardList");

    appConfig.put("QueryUserCash.Req", "com.jfpay.preposing.reqhandle.QueryUserCash");

    appConfig.put("JFPalCash.Req", "com.jfpay.preposing.reqhandle.JFPalCash");

    appConfig.put("QueryCreditInfo.Req", "com.jfpay.preposing.reqhandle.QueryCreditInfo");

    appConfig.put("RequestOrder.Req", "com.jfpay.preposing.reqhandle.RequestOrder");

    appConfig.put("JFPalAcctPay.Req", "com.jfpay.preposing.reqhandle.JFPalAcctPay");

    appConfig.put("PrepaidPay.Req", "com.jfpay.preposing.reqhandle.PrepaidPay");

    appConfig.put("JFPalCardPay.Req", "com.jfpay.preposing.reqhandle.JFPalCardPay");
    
    appConfig.put("JFPalCardPayforStore.Req", "com.jfpay.preposing.reqhandle.JFPalCardPayforStore");

    appConfig.put("BankCardUnBind.Req", "com.jfpay.preposing.reqhandle.BankCardUnBind");

    appConfig.put("BankCardBalance.Req", "com.jfpay.preposing.reqhandle.BankCardBalance");

    appConfig.put("GetUserInstruction.Req", "com.jfpay.preposing.reqhandle.note.GetUserInstruction");

    appConfig.put("GetPublicNotice.Req", "com.jfpay.preposing.reqhandle.note.GetPublicNotice");

    appConfig.put("GetBankHeadQuarter.Req", "com.jfpay.preposing.reqhandle.note.GetBankHeadQuarter");

    appConfig.put("GetBankBranch.Req", "com.jfpay.preposing.reqhandle.note.GetBankBranch");

    appConfig.put("GetBankCardList2.Req", "com.jfpay.preposing.reqhandle.note.GetBankCardList2");

    appConfig.put("ClientUpdate2.Req", "com.jfpay.preposing.reqhandle.note.ClientUpdate2");

    appConfig.put("RetrievePassword.Req", "com.jfpay.preposing.reqhandle.RetrievePassword");

    appConfig.put("EnterpriseUserUpdateInfo.Req", "com.jfpay.preposing.reqhandle.note.EnterpriseUserUpdateInfo");

    appConfig.put("UserSignatureUpload.Req", "com.jfpay.preposing.reqhandle.note.UserSignatureUpload");

    appConfig.put("EnquiryOrder.Req", "com.jfpay.preposing.reqhandle.note.EnquiryOrder");

    appConfig.put("UserIdentityPicUpload.Req", "com.jfpay.preposing.reqhandle.note.UserIdentityPicUpload");

    appConfig.put("UserInfoQuery.Req", "com.jfpay.preposing.reqhandle.note.UserInfoQuery");

    appConfig.put("YHBankCardBind.Req", "com.jfpay.preposing.reqhandle.YHBankCardBind");

    appConfig.put("YHBankCardChangePass.Req", "com.jfpay.preposing.reqhandle.YHBankCardChangePass");

    appConfig.put("YHBankCardLoss.Req", "com.jfpay.preposing.reqhandle.YHBankCardLoss");

    appConfig.put("YHBankCardBalance.Req", "com.jfpay.preposing.reqhandle.YHBankCardBalance");

    appConfig.put("YHBankCardTransfer.Req", "com.jfpay.preposing.reqhandle.YHBankCardTransfer");

    appConfig.put("YHBankCardSettle.Req", "com.jfpay.preposing.reqhandle.YHBankCardSettle");

    appConfig.put("GetYHBankCardList.Req", "com.jfpay.preposing.reqhandle.note.GetYHBankCardList");

    appConfig.put("GetLotteryCurrentPeriod.Req", "com.jfpay.preposing.reqhandle.lottery.GetLotteryCurrentPeriod");

    appConfig.put("GetLotteryAwardNumber.Req", "com.jfpay.preposing.reqhandle.lottery.GetLotteryAwardNumber");

    appConfig.put("SaveLotteryBetOrder.Req", "com.jfpay.preposing.reqhandle.lottery.SaveLotteryBetOrder");

    appConfig.put("GetUserLotteryInfo.Req", "com.jfpay.preposing.reqhandle.lottery.GetUserLotteryInfo");

    appConfig.put("GetUserLotteryBetRecord.Req", "com.jfpay.preposing.reqhandle.lottery.GetUserLotteryBetRecord");

    appConfig.put("GetAwardRecord.Req", "com.jfpay.preposing.reqhandle.lottery.GetAwardRecord");

    appConfig.put("QueryXGPeriod.Req", "com.jfpay.preposing.reqhandle.lottery.QueryXGPeriodOrAwardNumber");

    appConfig.put("QueryXGAwardNumber.Req", "com.jfpay.preposing.reqhandle.lottery.QueryXGPeriodOrAwardNumber");

    appConfig.put("EnquiryOrder2.Req", "com.jfpay.preposing.reqhandle.order.EnquiryOrder2");

    appConfig.put("EnquiryOrder2.Req", "com.jfpay.preposing.reqhandle.order.EnquiryOrder2");

    appConfig.put("GetStationHouseList.Req", "com.jfpay.preposing.reqhandle.station.GetStationHouseList");
    appConfig.put("GetTrainNumberInfo.Req", "com.jfpay.preposing.reqhandle.station.GetTrainNumberInfo");
    appConfig.put("GetHistoryLinkManInfo.Req", "com.jfpay.preposing.reqhandle.station.GetHistoryLinkManInfo");
    appConfig.put("SaveTrainOrderInfo.Req", "com.jfpay.preposing.reqhandle.station.SaveTrainOrderInfo");
    appConfig.put("BackTrainTicket.Req", "com.jfpay.preposing.reqhandle.station.BackTrainTicket");
    appConfig.put("GetHistoryOrderList.Req", "com.jfpay.preposing.reqhandle.station.GetHistoryOrderList");
    appConfig.put("GetHistoryOrderInfo.Req", "com.jfpay.preposing.reqhandle.station.GetHistoryOrderInfo");
    appConfig.put("SaveLinkManInfo.Req", "com.jfpay.preposing.reqhandle.station.SaveLinkManInfo");
    appConfig.put("UpdateLinkManInfo.Req", "com.jfpay.preposing.reqhandle.station.UpdateLinkManInfo");
    appConfig.put("DeleteLinkManInfo.Req", "com.jfpay.preposing.reqhandle.station.DeleteLinkManInfo");

    appConfig.put("GetPublicUtilitiesArea.Req", "com.jfpay.preposing.reqhandle.publicutilities.GetPublicUtilitiesArea");

    appConfig.put("GetPublicUtilitiesItems.Req", "com.jfpay.preposing.reqhandle.publicutilities.GetPublicUtilitiesItems");

    appConfig.put("GetPublicUtilitiesItemDetail.Req", "com.jfpay.preposing.reqhandle.publicutilities.GetPublicUtilitiesItemDetail");

    appConfig.put("QueryPublicUtilities.Req", "com.jfpay.preposing.reqhandle.publicutilities.QueryPublicUtilities");
    
	// 消息列表查询
	appConfig.put("MsgList.Req", "com.jfpay.preposing.reqhandle.note.MsgList");
	// 消息详情查询
	appConfig.put("MsgDetail.Req", "com.jfpay.preposing.reqhandle.note.MsgDetail");
	// 贴牌数据资源获取
	appConfig.put("UserAgreement.Req", "com.jfpay.preposing.reqhandle.note.UserAgreement");
	// 功能频道开关
	appConfig.put("Channel.Req", "com.jfpay.preposing.reqhandle.note.Channel");
	// 添加商品
	appConfig.put("AddCommodity.Req", "com.jfpay.preposing.reqhandle.store.AddCommodity");
	// 商品列表
	appConfig.put("CommodityList.Req", "com.jfpay.preposing.reqhandle.store.CommodityList");
	// 删除商品
	appConfig.put("DeleteCommodity.Req", "com.jfpay.preposing.reqhandle.store.DeleteCommodity");
	// 修改商品
	appConfig.put("EditCommodity.Req", "com.jfpay.preposing.reqhandle.store.EditCommodity");
	// 省市区
	appConfig.put("CitiesCode.Req", "com.jfpay.preposing.reqhandle.note.CitiesCode");
	//交易记录列表
	appConfig.put("RecordList.Req", "com.jfpay.preposing.reqhandle.note.RecordList");
	//交易详情
	appConfig.put("RecordDetail.Req", "com.jfpay.preposing.reqhandle.note.RecordDetail");
	//我的刷卡器
	appConfig.put("MyPos.Req", "com.jfpay.preposing.reqhandle.note.MyPOS");
	// 获取无卡支付绑定银行卡列表
	appConfig.put("GetQuickBankCard.Req", "com.jfpay.preposing.reqhandle.note.GetQuickBankCard");
	// 解绑无卡支付银行卡
	appConfig.put("UnbindQuickBankCard.Req", "com.jfpay.preposing.reqhandle.note.UnbindQuickBankCard");
	//汇率查询接口
	appConfig.put("ExchangeFee.Req", "com.jfpay.preposing.reqhandle.note.ExchangeFee");
	
	//科信领购金和消费金
	appConfig.put("KxJinQuery.Req", "com.jfpay.preposing.reqhandle.kx.KxJinQuery");
	
	//根据手机号获取商户名称
	appConfig.put("KxBusNameQuery.Req", "com.jfpay.preposing.reqhandle.kx.KxBusNameQuery");
	
	appConfig.put("KxLGJPay.Req", "com.jfpay.preposing.reqhandle.kx.KxLGJPay");
	
	appConfig.put("KxXFJPay.Req", "com.jfpay.preposing.reqhandle.kx.KxXFJPay");
	
	appConfig.put("KxLGJPay.Req", "com.jfpay.preposing.reqhandle.kx.KxLGJPay");
	
//	// 银行卡信息查询
//	appConfig.put("QuickBankCardQuery.Req", "com.jfpay.preposing.reqhandle.QuickBankCardQuery.");
//	// 获取短信验证码
//	appConfig.put("QuickBankCardMsg.Req", "com.jfpay.preposing.reqhandle.QuickBankCardMsg");
//	// 确认支付请求
//	appConfig.put("QuickBankCardConfirm.Req", "com.jfpay.preposing.reqhandle.QuickBankCardConfirm");
//	// 信用卡储蓄卡支付请求
//	appConfig.put("QuickBankCardApply.Req", "com.jfpay.preposing.reqhandle.QuickBankCardApply");
  }
}