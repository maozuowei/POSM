package com.jfpay.preposing.xml;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLStreamException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import net.sf.json.JSONObject;

/**
 * 解析核心系统返回的xml报文
 * 
 * @author Rex_xu 2011.09.15
 */
public class ParseCoreReceiveJSON {

	/** -------------------公共部分开始-----------------* */
	public static final String TRANSDATE = "transDate";

	public static final String TRANSTIME = "transTime";

	public static final String TRANSLOGNO = "transLogNo";

	public static final String QT_PAY = "QtPay";

	public static final String APPLICATION = "application";

	public static final String VERSION = "version";

	public static final String OS_TYPE = "osType";

	public static final String MOBILE_SERIAL_NUM = "mobileSerialNum";

	public static final String USER_IP = "userIP";

	public static final String TOKEN = "token";

	public static final String PHONE = "phone";

	public static final String CLIENT_TYPE = "clientType";

	public static final String APP_USER = "appUser";

	public static final String SIGN = "sign";

	public static final String XML_DATA_TYPE = "xml";

	public static final String JSON_DATA_TYPE = "json";

	public static final String VERSION_FLAG = "versionFlag";

	/** -------------------公共部分结束-----------------* */

	public static final String PARAMETER = "parameter";

	public static final String PASSWD = "password";

	public static final String MOBILE_NO = "mobileNo";

	public static final String MOBILE_MAC = "mobileMac";

	public static final String REAL_NAME = "realName";

	public static final String CERT_TYPE = "certType";

	public static final String CERT_PID = "certPid";

	public static final String EMAIL = "email";

	public static final String NEW_PASSWD = "newPassword";

	public static final String APP_TYPE = "appType";

	public static final String TERM_ID = "termId";

	public static final String ACCT_TYPE = "acctType";

	public static final String DETAIL_SEQUENCE = "detailSequence";

	public static final String CARD_NO = "cardNo";

	public static final String CARD_PASSWD = "cardPassword";

	public static final String NEW_CARD_PASSWD = "newCardPassword";

	public static final String CARD_INDEX = "cardIdx";

	public static final String CARD_TAG = "cardTag";

	public static final String BANK_ID = "bankId";

	public static final String BANK_NAME = "bankName";

	public static final String BANK_PROVINCE = "bankProvince";

	public static final String BANK_CITY = "bankCity";

	public static final String BANK_PROVINCE_ID = "bankProvinceId";

	public static final String BANK_CITY_ID = "bankCityId";

	public static final String BIND_TYPE = "bindType";

	public static final String ACCOUNT_NO = "accountNo";

	public static final String CARD_NUM = "cardNum";

	public static final String CARD_INFO = "cardInfo";

	public static final String ORDER_ID = "orderId";

	public static final String MERCHANT_ID = "merchantId";

	public static final String PRODUCT_ID = "productId";

	public static final String ORDER_AMT = "orderAmt";

	public static final String ORDER_DESC = "orderDesc";

	public static final String CASH_AMT = "cashAmt";
	
	public static final String REAL_AMT = "realAmt";

	public static final String USER_NAME = "userName";

	public static final String PAY_TOOL = "payTool";

	public static final String ORDER_REMARK = "orderRemark";

	public static final String INSTRUCTION_VERSION = "instrVersion";

	public static final String NOTICE_CODE = "noticeCode";

	public static final String BANK_VERSION = "bankVersion";

	public static final String CONDITION = "condition";

	public static final String OFFSET = "offset";

	public static final String ENTER_NAME = "enterName";

	public static final String ENTER_CERT_TYPE = "enterCertType";

	public static final String ENTER_CERT_PID = "enterCertPid";

	public static final String ENTER_ADDRESS = "enterAddress";

	public static final String LEGAL_NAME = "legalName";

	public static final String LEGAL_CERT_TYPE = "legalCertType";

	public static final String LEGAL_CERT_PID = "legalCertPid";

	public static final String CONTACT_NAME = "contactName";

	public static final String CONTACT_PHONE = "contactPhone";

	public static final String ENTER_EMAIL = "enterEmail";

	public static final String LONGITUDE = "longitude";

	public static final String LATITUDE = "latitude";

	public static final String SIGN_PIC_ASCII = "signPicAscii";

	public static final String PIC_SIGN = "picSign";

	public static final String ORDER_NO = "orderNo";

	public static final String ORDER_CONTENT = "orderContent";
	
	public static final String ORGANIZATION = "organization";

	public static final String PID_IMG = "pidImg";

	public static final String PID_ANTI_IMG = "pidAntiImg";

	public static final String PIC = "pic";

	public static final String PID_IMG_SIGN = "pidImgSign";

	public static final String PID_ANTI_IMG_SIGN = "pidAntiImgSign";

	public static final String CUSTOMER_ID = "customerId";

	public static final String BRANCH_BANK_ID = "branchBankId";

	public static final String ACCOUNT_NAME = "accountName";

	public static final String LOSS_TYPE = "lossType";

	public static final String QUERY_FLAG = "queryFlag";

	public static final String LOTTERY_ID = "lotteryId";

	public static final String PERIOD_ID = "periodId";

	public static final String BOOK_PERIODS = "bookPeriods";

	public static final String TOTAL_AMT = "totalAmt";

	public static final String BET_DATA = "betData";

	public static final String CITY_CODE = "cityCode";

	public static final String ITEM_ID = "itemId";

	public static final String UUID = "uuid";

	public static final String BILL_KEY = "billKey";

	public static final String COMPANY_ID = "companyId";

	public static final String FIELD_SET = "fieldSet";

	public static final String CONTRACT_NO = "contractNo";

	public static final String CUSTOMER_NAME = "customerName";

	public static final String PAY_AMOUNT = "payAmount";
	// 提现类型
	public static final String CASH_TYPE = "cashType";

	public static final String TRANS_TYPE = "transType";
	// 注册推荐人手机号
	public static final String REFERRER_MOBILE_NO = "referrerMobileNo";

	public static final String CARD_PHONE_NO = "cardPhoneNo";
	// 完善用户信息上传图片
	public static final String IMG = "img";
	// 图片类型
	public static final String IMG_APPLY_TYPE = "imgApplyType";
	// 图片MD5
	public static final String IMG_SIGN = "imgSign";
	
	//消息ID
	public static final String MSG_ID = "msgID";
	
	// 第一条数据ID
	public static final String FIRST_MSG_ID = "firstMsgID";
	
	// 最后一条数据ID
	public static final String LAST_MSG_ID = "lastMsgID";
	
	// 分页大小(单页数据条数)
	public static final String MSG_SIZE = "msgSize";
	
	// 分页请求类型
	public static final String REQUEST_TYPE = "requestType";
	
	// 设备标识
	public static final String DEVICE_TOKEN = "deviceToken";
	
	// 公司简称
	public static final String SHORT_COMPARY = "shortCompary";
	
	// 图标地址
	public static final String APP_ICON = "appIcon";

	// 商标图地址
	public static final String LOGO = "logo";
	
	/** 火车票 */

	public static final String BEGIN_DATE = "beginDate";

	public static final String END_DATE = "endDate";

	public static final String PAGE = "page";

	public static final String PAGE_SIZE = "pageSize";

	public static final String OUT_CITY = "outCity";

	public static final String ARR_CITY = "arrCity";

	public static final String OUT_DATE = "outDate";

	public static final String TRAIN_TYPE = "trainType";

	public static final String TRAIN_NO = "trainNo";

	public static final String TIME_QUAN_TUM = "timeQuantum";

	public static final String HOUSE_FLAG = "houseFlag";

	public static final String TRAIN_INFO = "trainInfo";

	public static final String PASSENGER_INFO = "passenageInfo";

	public static final String CONTACT_INFO = "contactInfo";

	public static final String LINK_MAN_NAME = "linkManName";

	public static final String LINK_MAN_SEX = "linkManSex";

	public static final String CARD_TYPE = "cardType";

	public static final String LINK_MAN_TYPE = "linkManType";

	public static final String SEQUENCE_NUM = "sequenceNum";

	public static final String LINK_MAN_NUM = "linkManNum";

	public static final String EXP_INFO = "expInfo";

	public static final String LINK_MAN_PHONE = "linkManPhone";

	public static final String CHARGE_ACCOUNT = "chargeAccount";

	public static final String CUS_TYPE = "";

	// SDK 接入机构ID
	public static final String PARTNER_ID = "partnerId";
	// 扣率编号
	public static final String FEE_CODE = "feeCode";

	// 商户让利率
	public static final String PARTNER_ID_CODE = "partnerIdCode";

	// 商户名称
	public static final String MERCHANT_NAME = "merchantName";

	// 商户地址
	public static final String MERCHANT_ADDRESS = "merchantAddres";

	// 商户营业执照号
	public static final String BUSINESS_LICENCE = "businessLicence";
	/**
	 * 平安付参数设置
	 */

	public static final String SEARCH_DATE = "searchDate";

	public static final String TRANS_NO = "transNo";

	public static final String AMOUNT = "amount";

	public static final String MUGSHOT = "mugshot";

	public static final String USER_TYPE = "userType";

	public static final String USER_IDENCCID = "userIdenCcid";

	/**
	 * 游戏接口
	 * 
	 */
	public static final String TX_GAME_ID = "gameId";

	public static final String TX_PARVALUE = "gameValue";

	public static final String ORDERID = "orderId";

	public static final String TX_ACCOUNT = "gameAccount";

	public static final String TX_ACCOUNT_TYPE = "gameAccountType";

	public static final String TX_ROLE_ID = "gameAccountId";

	public static final String TX_ROLE_NAME = "gameAccountName";

	public static final String TX_FILLNUM = "gameNum";

	public static final String TX_PAYMETHOD = "payTool";

	public static final String TX_AREAID = "areaId";

	public static final String TX_SERVERID = "serverId";

	public static final String TX_CHARGE_TYPE = "chargeType";

	/**
	 * 商品管理参数
	 */
	public static final String PRICE = "price";

	public static final String GOOD_NAME = "goodName";

	public static final String GOOD_PIC = "goodPic";

	public static final String GOOD_STATUS = "goodStatus";

	public static final String GOOD_ID = "goodId";

	public static final String REASON = "reason";

	public static final String STATUS = "status";
	
	public static final String REASON_ID = "reasonId";
	
	public static final String ADDREE_PIC = "addreePic"; ///

//	public static final String SYN_FLAG = "synFlag";

//	public static final String SIMP_NAME = "simpName";

	// SDK 转账 收单 参数
	public static final String MER_ORDER_ID = "merOrderId";
	
	/* added by lw@2014-11-20: IC卡信息域x5 --------------------- */
	//IC卡使用交易信息
	public static final String TX_HOST_IC_DATA = "ICCardInfo";
	//IC卡序列号
	public static final String TX_HOST_IC_CSN = "ICCardSerial";
	//IC卡有效期
//	public static final String TX_HOST_FLD_60 = "ICCardValidDate";
	public static final String TX_HOST_EXPIRATION_DATE = "ICCardValidDate"; // modified by lw@2014-12-25
	//
//	public static final String TX_HOST_PIN_COND = "PinCond"; // canceled by lw@2014-12-25
	//
//	public static final String TX_HOST_EXPIRATION_DATE = "ExpirationDate"; // canceled by lw@2014-12-25
	/* --------------------------------------------------------- */
	
	/**
	 * 商城接口
	 */
	
	// 当前页第一条数据ID
	public static final String FIRST_DATA_ID = "firstDataID";
	
	// 当前页最后一条数据ID
	public static final String LAST_DATA_ID = "lastDataID";
	
	// 数据大小
	public static final String DATA_SIZE = "dataSize";
	
	// 商城名
	public static final String ICON = "icon";
	
	// 商品标题
	public static final String TITLE = "title";
	
	// 商品描述
	public static final String DESC = "desc";
	
	// 商品ID
	public static final String COMMODITY_ID = "commodityID";
	
	// 商品IDs
	public static final String TX_COMMODITYIDS = "commodityIDs";
	
	//交易纪录列表
	
	public static final String FILTER = "filter";
	
	//交易详情
	public static final String RECORDID = "recordID";
	
	public static final String TX_IP_IME_ICPH = "ipImeiCph";
	
	public static final String RESERVE = "reserve";
	
	// 省编码
	public static final String PROVINCE_CODE="provinceCode";
	
	/**
	 * 无卡支付
	 */
	// 绑定ID
	public static final String BIND_ID = "bindID";
	
	// 支付信息
	public static final String PAY_INFO = "payInfo";
	
	// 加密方式
	public static final String ENCODE_TYPE = "encodeType";
	
	// 短信校验码
	public static final String VALIDATE_CODE = "validateCode";
	
	// 绑卡类型
	public static final String BAND_TYPE = "bandType";
	
	// 支付类型
	public static final String PAY_TYPE="payType";
	
	//原始币种
	public static final String EXCHANGE_SOURCE = "source";
	
	//目标币种
	public static final String EXCHANGE_GOAL = "goal";
	
	//转换金额
	public static final String MONEY = "money";
	
	//科信费率信息
	public static final String RATE_CODE = "rateCode";
	
	
	//终端绑定
	//设备编码
	public static final String DEVICE_ID = "deviceId";
	
	public static final String DEVICE_TYPE = "deviceType";
	
	//psamid
	public static final String PSAM_ID = "psamId";
	
	//操作类型
	public static final String OPT_TYPE = "optType";//1、查询，2、新增，3、删除
	
	//四要素手机号
	public static final String FOUR_PHONE="fourPhone";
	
	//头像
	public static final String HEAD_PIC="headPic";
	
	//app类型，vip版还是普通
	public static final String VERSION_STATUS="versionStatus";
	
	//延迟类型
	public static final String TRADEDELAY="tradeDelay";
	
	//钱包类型
	public static final String WALLET_TYPE="walletType";
	
	//付款账号
	public static final String PAY_ACC="payAcc";
	
	//
	/**
	 * 用户注册
	 */
	public static String application_UserRegister = "UserRegister.Req";

	/**
	 * 用户登录
	 */
	public static String application_UserLogin = "UserLogin.Req";

	/**
	 * 用户资料完善
	 */
	public static String application_UserUpdateInfo = "UserUpdateInfo.Req";

	/**
	 * 余额查询
	 */
	public static String application_BalanceEnquiry = "BalanceEnquiry.Req";

	/**
	 * 修改密码
	 */
	public static String application_UserUpdatePwd = "UserUpdatePwd.Req";

	/**
	 * 终端合法性验证
	 */
	public static String application_JFTermVerify = "JFTermVerify.Req";

	/**
	 * 余额查询
	 */
	public static String application_JFPalAcctEnquiry = "JFPalAcctEnquiry.Req";

	/**
	 * 收支明细
	 */
	public static String application_GetJFPalDetail = "GetJFPalDetail.Req";

	/**
	 * 即付宝预付卡业务
	 */
	public static String application_UserCardBind = "UserCardBind.Req";

	/**
	 * 预付卡列表查询
	 */
	public static String application_GetUserCardList = "GetUserCardList.Req";

	/**
	 * 预付卡余额查询
	 */
	public static String application_UserCardBalanceEnquiry = "UserCardBalanceEnquiry.Req";
	/**
	 * 预付卡余额合并
	 */
	public static String application_UserCardMerger = "UserCardMerger.Req";

	/**
	 * 解除绑定
	 */
	public static String applicate_UserCardUnBind = "UserCardUnBind.Req";
	/**
	 * 银行卡登记
	 */
	public static String application_BankCardBind = "BankCardBind.Req";

	/**
	 * 银行卡列表查询
	 */
	public static String application_GetBankCardList = "GetBankCardList.Req";

	/**
	 * 获取短信验证码
	 */
	public static String application_GetMobileMac = "GetMobileMac.Req";

	/**
	 * 银行卡解除绑定
	 */
	public static String application_BankCardUnBind = "BankCardUnBind.Req";

	/**
	 * 银行卡余额查询
	 */
	public static String application_BankCardBalance = "BankCardBalance.Req";

	/**
	 * 即付宝订单请求
	 */
	public static String application_RequestOrder = "RequestOrder.Req";

	/**
	 * 即付宝账户支付
	 */
	public static String application_JFPalAcctPay = "JFPalAcctPay.Req";

	/**
	 * 即付宝刷卡支付
	 */
	public static String application_JFPalCardPay = "JFPalCardPay.Req";

	/**
	 * 即付宝预付卡支付
	 */
	public static String application_PrepaidPay = "PrepaidPay.Req";

	/**
	 * 提款信息查询
	 */
	public static String application_QueryUserCash = "QueryUserCash.Req";

	/**
	 * 即付宝待付款查询
	 */
	public static String application_GetJFPalPrePayDetail = "GetJFPalPrePayDetail.Req";

	/**
	 * 手机号提款
	 */
	public static String application_JFPalCash = "JFPalCash.Req";

	/**
	 * 信用卡信息查询
	 */
	public static String application_QueryCreditInfo = "QueryCreditInfo.Req";

	/**
	 * 获取用户须知
	 */
	public static String application_GetUserInstruction = "GetUserInstruction.Req";
	/**
	 * 获取公告通知
	 */
	public static String application_GetPublicNotice = "GetPublicNotice.Req";
	/**
	 * 客户端更新新版
	 */
	public static String application_ClientUpdate2 = "ClientUpdate2.Req";
	/**
	 * 获取总行列表
	 */
	public static String application_GetBankHeadQuarter = "GetBankHeadQuarter.Req";
	/**
	 * 获取支行列表
	 */
	public static String application_GetBankBranch = "GetBankBranch.Req";
	/**
	 * 获取银行卡列表
	 */
	public static String application_GetBankCardList2 = "GetBankCardList2.Req";

	/**
	 * 找回密码
	 */
	public static String application_RetrievePassword = "RetrievePassword.Req";
	/**
	 * 企业账户资料完善
	 */
	public static String application_EnterpriseUserUpdateInfo = "EnterpriseUserUpdateInfo.Req";
	/**
	 * 订单手写签名信息上传
	 */
	public static String application_UserSignatureUpload = "UserSignatureUpload.Req";
	/**
	 * 商户未支付订单查询
	 */
	public static String application_EnquiryOrder = "EnquiryOrder.Req";

	/**
	 * 用户身份照片上传
	 */
	public static String application_UserIdentityPicUpload = "UserIdentityPicUpload.Req";

	/**
	 * 个人用户信息查询
	 */
	public static String application_UserInfoQuery = "UserInfoQuery.Req";
	/**
	 * 沿海银行卡片激活绑定
	 */
	public static String application_YHBankCardBind = "YHBankCardBind.Req";
	/**
	 * 沿海银行卡密码修改
	 */
	public static String application_YHBankCardChangePass = "YHBankCardChangePass.Req";
	/**
	 * 沿海银行卡挂失
	 */
	public static String application_YHBankCardLoss = "YHBankCardLoss.Req";
	/**
	 * 沿海银行卡余额查询
	 */
	public static String application_YHBankCardBalance = "YHBankCardBalance.Req";
	/**
	 * 沿海银行卡转账
	 */
	public static String application_YHBankCardTransfer = "YHBankCardTransfer.Req";
	/**
	 * 沿海银行卡挂失销介
	 */
	public static String application_YHBankCardSettle = "YHBankCardSettle.Req";
	/**
	 * 沿海银行卡列表获取
	 */
	public static String application_GetYHBankCardList = "GetYHBankCardList.Req";

	/**
	 * 获取福彩可售期次信息
	 */
	public static String application_GetLotteryCurrentPeriod = "GetLotteryCurrentPeriod.Req";
	/**
	 * 获取开奖号码
	 */
	public static String application_GetLotteryAwardNumber = "GetLotteryAwardNumber.Req";
	/**
	 * 保存福彩投注订单
	 */
	public static String application_SaveLotteryBetOrder = "SaveLotteryBetOrder.Req";
	/**
	 * 获取用户购彩统计信息
	 */
	public static String application_GetUserLotteryInfo = "GetUserLotteryInfo.Req";
	/**
	 * 获取用户投注记录
	 */
	public static String application_GetUserLotteryBetRecord = "GetUserLotteryBetRecord.Req";
	/**
	 * 缴费城市、区域查询
	 */
	public static String application_GetPublicUtilitiesArea = "GetPublicUtilitiesArea.Req";
	/**
	 * 缴费项目列表查询
	 */
	public static String application_GetPublicUtilitiesItems = "GetPublicUtilitiesItems.Req";
	/**
	 * 缴费项目详情查询
	 */
	public static String application_GetPublicUtilitiesItemDetail = "GetPublicUtilitiesItemDetail.Req";
	/**
	 * 缴费账单查询
	 */
	public static String application_QueryPublicUtilities = "QueryPublicUtilities.Req";
	/**
	 * 缴费销账
	 */
	public static String application_PublicUtilitiesCharge = "PublicUtilitiesCharge.Req";

	/**
	 * 火车票申请退票接口
	 */

	public static String application_BackTrainTicket = "BackTrainTicket.Req";

	/**
	 * 获取历史联系人信息查询接口
	 */

	public static String application_GetHistoryLinkManInfo = "GetHistoryLinkManInfo.Req";

	/**
	 * 获取历史订单详情
	 */

	public static String application_GetHistoryOrderInfo = "GetHistoryOrderInfo.Req";

	/**
	 * 获取历史订单列表数据
	 */

	public static String application_GetHistoryOrderList = "GetHistoryOrderList.Req";

	/**
	 * 火车站列表查询接口
	 */

	public static String application_GetStationHouseList = "GetStationHouseList.Req";

	/**
	 * 获取车次时刻票价余票信息接口
	 */

	public static String application_GetTrainNumberInfo = "GetTrainNumberInfo.Req";

	/**
	 * 火车票下订单接口
	 */

	public static String application_SaveTrainOrderInfo = "SaveTrainOrderInfo.Req";

	/**
	 * 保存联系人信息接口
	 */

	public static String application_SaveLinkManInfo = "SaveLinkManInfo.Req";

	/**
	 * 修改联系人信息接口
	 */

	public static String application_UpdateLinkManInfo = "UpdateLinkManInfo.Req";

	/**
	 * 删除联系人接口
	 */

	public static String application_DeleteLinkManInfoextends = "DeleteLinkManInfoextends.Req";
	
	/**
	 * 消息列表接口
	 */
	public static String application_MsgList = "MsgList.Req";
	
	/**
	 * 消息详情接口
	 */
	public static String application_MsgDetail = "MsgDetail.Req";
	
	/**
	 * 交易纪录列表
	 */
	
	public static String application_RecordList = "RecordList.Req";
	
	/**
	 * 交易纪录详情
	 */
	
	public static String application_RecordDetail = "RecordDetail.Req";
	
	/**
	 * 获取无卡支付绑定银行卡列表
	 */
	public static String application_GetQuickBankCard = "GetQuickBankCard.Req";
	
	
	/**
	 * 银行卡信息查询
	 */
	public static String application_QuickBankCardQuery = "QuickBankCardQuery.Req";
	
	/**
	 * 获取短信验证码

	 */
	public static String application_QuickBankCardMsg = "QuickBankCardMsg.Req";
	
	/**
	 * 确认支付请求
	 */
	public static String application_QuickBankCardConfirm = "QuickBankCardConfirm.Req";
	
	/**
	 * 信用卡储蓄卡支付请求
	 */
	public static String application_QuickBankCardApply = "QuickBankCardApply.Req";
	/**
	 * 汇率查询请求
	 */
	public static String application_ExchangeFee = "ExchangeFee.Req";
	
	public static String application_KxJinQuery = "KxJinQuery.Req";
	
	public static String application_KxBusNameQuery = "KxBusNameQuery.Req";
	
	public static String application_KxXFJPay = "KxXFJPay.Req";
	
	

	/**
	 * 
	 * 解析手机客户端请求的xml。 解析结果放在map中，只需要以需要的key就可取得相应的值
	 * 
	 * @param xmlStr
	 *            String型的xml报文
	 * @return
	 * @throws XMLStreamException
	 */
	public static HashMap<String, String> parseCoreXml(String xmlStr) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		JSONObject attr;
		try {
			attr=JSONObject.fromObject(xmlStr);
			resultMap.put(APPLICATION, attr.optString(APPLICATION));
			resultMap.put(VERSION, attr.optString(VERSION));
			resultMap.put(OS_TYPE, attr.optString(OS_TYPE));
			resultMap.put(MOBILE_SERIAL_NUM, attr.optString(MOBILE_SERIAL_NUM));
			resultMap.put(USER_IP, attr.optString(USER_IP));
			resultMap.put(CLIENT_TYPE, attr.optString(CLIENT_TYPE));
			resultMap.put(PHONE, attr.optString(PHONE));
			resultMap.put(TOKEN, attr.optString(TOKEN));
			resultMap.put(APP_USER, attr.optString(APP_USER));
			resultMap.put(CUSTOMER_ID, attr.optString(CUSTOMER_ID));
			
			JSONObject jsonData = attr.optJSONObject("data");
			if(jsonData == null){
				return resultMap;
			}

			resultMap.put(PARAMETER, jsonData.optString(PARAMETER));
			resultMap.put(TRANSDATE, jsonData.optString(TRANSDATE));
			resultMap.put(TRANSTIME, jsonData.optString(TRANSTIME));
			resultMap.put(TRANSLOGNO, jsonData.optString(TRANSLOGNO));
			resultMap.put(USER_NAME, jsonData.optString(USER_NAME));
			resultMap.put(PASSWD, jsonData.optString(PASSWD));
			resultMap.put(MOBILE_NO, jsonData.optString(MOBILE_NO));
			resultMap.put(MOBILE_MAC, jsonData.optString(MOBILE_MAC));
			
			resultMap.put(REAL_NAME, jsonData.optString(REAL_NAME));
			resultMap.put(CERT_TYPE, jsonData.optString(CERT_TYPE));
			resultMap.put(CERT_PID, jsonData.optString(CERT_PID));
			resultMap.put(EMAIL, jsonData.optString(EMAIL));
			resultMap.put(NEW_PASSWD, jsonData.optString(NEW_PASSWD));
			resultMap.put(APP_TYPE, jsonData.optString(APP_TYPE));
			resultMap.put(TERM_ID, jsonData.optString(TERM_ID));
			
			
			resultMap.put(ACCT_TYPE, jsonData.optString(ACCT_TYPE));
			resultMap.put(DETAIL_SEQUENCE, jsonData.optString(DETAIL_SEQUENCE));
			resultMap.put(CARD_NO, jsonData.optString(CARD_NO));
			resultMap.put(CARD_PASSWD, jsonData.optString(CARD_PASSWD));
			resultMap.put(NEW_CARD_PASSWD, jsonData.optString(NEW_CARD_PASSWD));
			resultMap.put(CARD_INDEX, jsonData.optString(CARD_INDEX));
			resultMap.put(CARD_TAG, jsonData.optString(CARD_TAG));
			
			resultMap.put(BANK_ID, jsonData.optString(BANK_ID));
			resultMap.put(BANK_NAME, jsonData.optString(BANK_NAME));
			resultMap.put(BANK_PROVINCE, jsonData.optString(BANK_PROVINCE));
			resultMap.put(BANK_CITY, jsonData.optString(BANK_CITY));
			resultMap.put(BIND_TYPE, jsonData.optString(BIND_TYPE));
			resultMap.put(ACCOUNT_NO, jsonData.optString(ACCOUNT_NO));
			resultMap.put(CARD_NUM, jsonData.optString(CARD_NUM));
			
			
			resultMap.put(CARD_INFO, jsonData.optString(CARD_INFO));
			resultMap.put(CARD_PASSWD, jsonData.optString(CARD_PASSWD));
			resultMap.put(ORDER_ID, jsonData.optString(ORDER_ID));
			resultMap.put(MERCHANT_ID, jsonData.optString(MERCHANT_ID));
			resultMap.put(PRODUCT_ID, jsonData.optString(PRODUCT_ID));
			resultMap.put(ORDER_AMT, jsonData.optString(ORDER_AMT));
			resultMap.put(ORDER_DESC, jsonData.optString(ORDER_DESC));
			
			resultMap.put(CASH_AMT, jsonData.optString(CASH_AMT));
			resultMap.put(PAY_TOOL, jsonData.optString(PAY_TOOL));
			resultMap.put(ORDER_REMARK, jsonData.optString(ORDER_REMARK));
			resultMap.put(ORDER_NO, jsonData.optString(ORDER_NO));
			resultMap.put(ORDER_CONTENT, jsonData.optString(ORDER_CONTENT));
			resultMap.put(INSTRUCTION_VERSION, jsonData.optString(INSTRUCTION_VERSION));
			resultMap.put(NOTICE_CODE, jsonData.optString(NOTICE_CODE));
			
			resultMap.put(BANK_VERSION, jsonData.optString(BANK_VERSION));
			resultMap.put(BANK_PROVINCE_ID, jsonData.optString(BANK_PROVINCE_ID));
			resultMap.put(BANK_CITY_ID, jsonData.optString(BANK_CITY_ID));
			resultMap.put(CONDITION, jsonData.optString(CONDITION));
			resultMap.put(OFFSET, jsonData.optString(OFFSET));
			resultMap.put(SIGN, jsonData.optString(SIGN));
			resultMap.put(ENTER_NAME, jsonData.optString(ENTER_NAME));
			
			resultMap.put(ENTER_CERT_TYPE, jsonData.optString(ENTER_CERT_TYPE));
			resultMap.put(ENTER_CERT_PID, jsonData.optString(ENTER_CERT_PID));
			resultMap.put(ENTER_ADDRESS, jsonData.optString(ENTER_ADDRESS));
			resultMap.put(LEGAL_NAME, jsonData.optString(LEGAL_NAME));
			resultMap.put(LEGAL_CERT_TYPE, jsonData.optString(LEGAL_CERT_TYPE));
			resultMap.put(LEGAL_CERT_PID, jsonData.optString(LEGAL_CERT_PID));
			resultMap.put(CONTACT_NAME, jsonData.optString(CONTACT_NAME));
			
			resultMap.put(CONTACT_PHONE, jsonData.optString(CONTACT_PHONE));
			resultMap.put(ENTER_EMAIL, jsonData.optString(ENTER_EMAIL));
			resultMap.put(LONGITUDE, jsonData.optString(LONGITUDE));
			resultMap.put(LATITUDE, jsonData.optString(LATITUDE));
			resultMap.put(SIGN_PIC_ASCII, jsonData.optString(SIGN_PIC_ASCII));
			resultMap.put(PIC_SIGN, jsonData.optString(PIC_SIGN));
			resultMap.put(PID_IMG, jsonData.optString(PID_IMG));
			
			resultMap.put(PID_ANTI_IMG, jsonData.optString(PID_ANTI_IMG));
			resultMap.put(PIC, jsonData.optString(PIC));
			resultMap.put(PID_IMG_SIGN, jsonData.optString(PID_IMG_SIGN));
			resultMap.put(PID_ANTI_IMG_SIGN, jsonData.optString(PID_ANTI_IMG_SIGN));
			resultMap.put(BRANCH_BANK_ID, jsonData.optString(BRANCH_BANK_ID));
			resultMap.put(ACCOUNT_NAME, jsonData.optString(ACCOUNT_NAME));
			resultMap.put(LOSS_TYPE, jsonData.optString(LOSS_TYPE));
			
			resultMap.put(CITY_CODE, jsonData.optString(CITY_CODE));
			resultMap.put(ITEM_ID, jsonData.optString(ITEM_ID));
			resultMap.put(UUID, jsonData.optString(UUID));
			resultMap.put(BILL_KEY, jsonData.optString(BILL_KEY));
			resultMap.put(COMPANY_ID, jsonData.optString(COMPANY_ID));
			resultMap.put(FIELD_SET, jsonData.optString(FIELD_SET));
			resultMap.put(CONTRACT_NO, jsonData.optString(CONTRACT_NO));
			
			resultMap.put(CUSTOMER_NAME, jsonData.optString(CUSTOMER_NAME));
			resultMap.put(PAY_AMOUNT, jsonData.optString(PAY_AMOUNT));
			resultMap.put(PERIOD_ID, jsonData.optString(PERIOD_ID));
			resultMap.put(BOOK_PERIODS, jsonData.optString(BOOK_PERIODS));
			resultMap.put(TOTAL_AMT, jsonData.optString(TOTAL_AMT));
			resultMap.put(QUERY_FLAG, jsonData.optString(QUERY_FLAG));
			resultMap.put(LOTTERY_ID, jsonData.optString(LOTTERY_ID));
			
			resultMap.put(BET_DATA, jsonData.optString(BET_DATA));
			resultMap.put(CASH_TYPE, jsonData.optString(CASH_TYPE));
			resultMap.put(TRANS_TYPE, jsonData.optString(TRANS_TYPE));
			resultMap.put(REFERRER_MOBILE_NO, jsonData.optString(REFERRER_MOBILE_NO));
			resultMap.put(HOUSE_FLAG, jsonData.optString(HOUSE_FLAG));
			resultMap.put(OUT_CITY, jsonData.optString(OUT_CITY));
			resultMap.put(ARR_CITY, jsonData.optString(ARR_CITY));
			
			resultMap.put(OUT_DATE, jsonData.optString(OUT_DATE));
			resultMap.put(TRAIN_TYPE, jsonData.optString(TRAIN_TYPE));
			resultMap.put(TRAIN_NO, jsonData.optString(TRAIN_NO));
			resultMap.put(TIME_QUAN_TUM, jsonData.optString(TIME_QUAN_TUM));
			resultMap.put(PAGE_SIZE, jsonData.optString(PAGE_SIZE));
			resultMap.put(PAGE, jsonData.optString(PAGE));
			resultMap.put(TRAIN_INFO, jsonData.optString(TRAIN_INFO));
			
			resultMap.put(PASSENGER_INFO, jsonData.optString(PASSENGER_INFO));
			resultMap.put(CONTACT_INFO, jsonData.optString(CONTACT_INFO));
			resultMap.put(EXP_INFO, jsonData.optString(EXP_INFO));
			resultMap.put(BEGIN_DATE, jsonData.optString(BEGIN_DATE));
			resultMap.put(END_DATE, jsonData.optString(END_DATE));
			resultMap.put(LINK_MAN_NAME, jsonData.optString(LINK_MAN_NAME));
			resultMap.put(LINK_MAN_SEX, jsonData.optString(LINK_MAN_SEX));
			
			resultMap.put(CARD_TYPE, jsonData.optString(CARD_TYPE));
			resultMap.put(LINK_MAN_TYPE, jsonData.optString(LINK_MAN_TYPE));
			resultMap.put(SEQUENCE_NUM, jsonData.optString(SEQUENCE_NUM));
			resultMap.put(LINK_MAN_NUM, jsonData.optString(LINK_MAN_NUM));
			resultMap.put(LINK_MAN_PHONE, jsonData.optString(LINK_MAN_PHONE));
			resultMap.put(CHARGE_ACCOUNT, jsonData.optString(CHARGE_ACCOUNT));
			resultMap.put(PARTNER_ID, jsonData.optString(PARTNER_ID));
			
			resultMap.put(SEARCH_DATE, jsonData.optString(SEARCH_DATE));
			resultMap.put(TRANS_NO, jsonData.optString(TRANS_NO));
			resultMap.put(AMOUNT, jsonData.optString(AMOUNT));
			resultMap.put(MUGSHOT, jsonData.optString(MUGSHOT));
			resultMap.put(FEE_CODE, jsonData.optString(FEE_CODE));
			resultMap.put(IMG, jsonData.optString(IMG));
			resultMap.put(IMG_APPLY_TYPE, jsonData.optString(IMG_APPLY_TYPE));
			
			resultMap.put(IMG_SIGN, jsonData.optString(IMG_SIGN));
			resultMap.put(CARD_PHONE_NO, jsonData.optString(CARD_PHONE_NO));
			resultMap.put(USER_TYPE, jsonData.optString(USER_TYPE));
			resultMap.put(USER_IDENCCID, jsonData.optString(USER_IDENCCID));
			resultMap.put(PARTNER_ID_CODE, jsonData.optString(PARTNER_ID_CODE));
			resultMap.put(PRICE, jsonData.optString(PRICE));
			resultMap.put(GOOD_NAME, jsonData.optString(GOOD_NAME));
			
			resultMap.put(GOOD_PIC, jsonData.optString(GOOD_PIC));
			resultMap.put(GOOD_STATUS, jsonData.optString(GOOD_STATUS));
			resultMap.put(GOOD_ID, jsonData.optString(GOOD_ID));
			resultMap.put(REASON, jsonData.optString(REASON));
			resultMap.put(STATUS, jsonData.optString(STATUS));
			resultMap.put(REASON_ID, jsonData.optString(REASON_ID));
			resultMap.put(MER_ORDER_ID, jsonData.optString(MER_ORDER_ID));
			
			resultMap.put(BUSINESS_LICENCE, jsonData.optString(BUSINESS_LICENCE));
			resultMap.put(MERCHANT_ADDRESS, jsonData.optString(MERCHANT_ADDRESS));
			resultMap.put(MERCHANT_NAME, jsonData.optString(MERCHANT_NAME));
			resultMap.put(ADDREE_PIC, jsonData.optString(ADDREE_PIC));
			
			resultMap.put(STATUS, jsonData.optString(STATUS));
			resultMap.put(REASON_ID, jsonData.optString(REASON_ID));
			resultMap.put(MER_ORDER_ID, jsonData.optString(MER_ORDER_ID));
			
			//IC卡使用交易信息
			resultMap.put(TX_HOST_IC_DATA, jsonData.optString(TX_HOST_IC_DATA));
			//IC卡序列号
			resultMap.put(TX_HOST_IC_CSN, jsonData.optString(TX_HOST_IC_CSN));
			
			resultMap.put(TX_HOST_EXPIRATION_DATE, jsonData.optString(TX_HOST_EXPIRATION_DATE));
			resultMap.put(MSG_SIZE, jsonData.optString(MSG_SIZE));
			resultMap.put(FIRST_MSG_ID, jsonData.optString(FIRST_MSG_ID));
			resultMap.put(LAST_MSG_ID, jsonData.optString(LAST_MSG_ID));
			resultMap.put(REQUEST_TYPE, jsonData.optString(REQUEST_TYPE));
			resultMap.put(MSG_ID, jsonData.optString(MSG_ID));
			resultMap.put(DEVICE_TOKEN, jsonData.optString(DEVICE_TOKEN));
			
			resultMap.put(SHORT_COMPARY, jsonData.optString(SHORT_COMPARY));
			resultMap.put(APP_ICON, jsonData.optString(APP_ICON));
			resultMap.put(LOGO, jsonData.optString(LOGO));
			//商城
			resultMap.put(FIRST_DATA_ID, jsonData.optString(FIRST_DATA_ID));
			resultMap.put(LAST_DATA_ID, jsonData.optString(LAST_DATA_ID));
			resultMap.put(DATA_SIZE, jsonData.optString(DATA_SIZE));
			resultMap.put(ICON, jsonData.optString(ICON));
			
			resultMap.put(TITLE, jsonData.optString(TITLE));
			resultMap.put(PRICE, jsonData.optString(PRICE));
			resultMap.put(AMOUNT, jsonData.optString(AMOUNT));
			resultMap.put(COMMODITY_ID, jsonData.optString(COMMODITY_ID));
			resultMap.put(DESC, jsonData.optString(DESC));
			
			//交易记录列表
			resultMap.put(FILTER, jsonData.optString(FILTER));
			resultMap.put(RECORDID, jsonData.optString(RECORDID));
			resultMap.put(TX_IP_IME_ICPH, jsonData.optString(TX_IP_IME_ICPH));
			
			resultMap.put(RESERVE, jsonData.optString(RESERVE));
			resultMap.put(TX_COMMODITYIDS, jsonData.optString(TX_COMMODITYIDS));
			resultMap.put(BIND_ID, jsonData.optString(BIND_ID));
			
			resultMap.put(PAY_INFO, jsonData.optString(PAY_INFO));
			resultMap.put(ENCODE_TYPE, jsonData.optString(ENCODE_TYPE));
			resultMap.put(VALIDATE_CODE, jsonData.optString(VALIDATE_CODE));
			resultMap.put(PROVINCE_CODE, jsonData.optString(PROVINCE_CODE));
			resultMap.put(BAND_TYPE, jsonData.optString(BAND_TYPE));
			resultMap.put(PAY_TYPE, jsonData.optString(PAY_TYPE));
			resultMap.put(ORGANIZATION, jsonData.optString(ORGANIZATION));
			
			//汇率转换
			resultMap.put(EXCHANGE_SOURCE, jsonData.optString(EXCHANGE_SOURCE));
			resultMap.put(EXCHANGE_GOAL, jsonData.optString(EXCHANGE_GOAL));
			resultMap.put(MONEY, jsonData.optString(MONEY));
			resultMap.put(RATE_CODE, jsonData.optString(RATE_CODE));
			resultMap.put(DEVICE_ID, jsonData.optString(DEVICE_ID));
			resultMap.put(DEVICE_TYPE, jsonData.optString(DEVICE_TYPE));
			resultMap.put(PSAM_ID, jsonData.optString(PSAM_ID));
			resultMap.put(OPT_TYPE, jsonData.optString(OPT_TYPE));
			
			resultMap.put(FOUR_PHONE, jsonData.optString(FOUR_PHONE));
			resultMap.put(HEAD_PIC, jsonData.optString(HEAD_PIC));
			resultMap.put(VERSION_STATUS, jsonData.optString(VERSION_STATUS));
			resultMap.put(TRADEDELAY, jsonData.optString(TRADEDELAY));
			resultMap.put(WALLET_TYPE, jsonData.optString(WALLET_TYPE));
			resultMap.put(PAY_ACC, jsonData.optString(PAY_ACC));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}
	
	public static void main(String[] args) {
		String string = "{\"clientType\": \"02\",\"osType\": \"android5.1\",\"token\": \"0000\",\"mobileSerialNum\": \"8672470239201000000000000000000000000000\",\"version\": \"2.0.6\",\"phone\": \"17002171196\",\"application\": \"UserLogin.Req\",\"appUser\": \"bmqhchqvip\",\"mobileNo\": \"17002171196\",\"transTime\": \"204807\",\"password\": \"8B4301CE1B75C89DBC6D488EB7601AE1D146186EDD851F5F080212081F4720543B963D6AE9AAFABAABF05035257C8998FCE5B87509958F406B07C6E7CFBE3E19475C3FEE67C3284F9474EB104F2C29170A530B3A3C7A5B99212B0675E72F5734AAE60BF64B2B81150C5F77EAD2E0292704E9E532EBFD94D23553550B18730580\",\"transLogNo\": \"000004\",\"sign\": \"1sml1e47mdq3m6l8bci5rm0ffjekfjk2\",\"transDate\": \"20160706\"}";
		try {
			HashMap<String, String> parseCoreXml = parseCoreXml(string);
////			System.out.println("parseCoreXml"+parseCoreXml.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String testrString = "{\"abc\" : \"[123]\"}";

//		JSONObject reqjson = JSONObject.fromObject(string);
//		System.out.println("reqjson"+reqjson.toString());
	}

}
