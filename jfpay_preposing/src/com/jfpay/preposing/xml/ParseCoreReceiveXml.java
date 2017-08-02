package com.jfpay.preposing.xml;

import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLStreamException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 解析核心系统返回的xml报文
 * 
 * @author Rex_xu 2011.09.15
 */
public class ParseCoreReceiveXml {

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

	public static final String PASSWD = "password";//密码旧式表达
	
	public static final String LOGINPWD = "loginPwd";//登录密码
	
	public static final String PAYPWD = "payPwd";//支付密码

	public static final String MOBILE_NO = "mobileNo";

	public static final String MOBILE_MAC = "mobileMac";

	public static final String REAL_NAME = "realName";

	public static final String CERT_TYPE = "certType";

	public static final String CERT_PID = "certPid";

	public static final String EMAIL = "email";

	public static final String NEW_PASSWD = "newPassword";//旧式新密码
	
	public static final String NEW_LOGINPWD = "newLoginPwd";//新登录密码
	
	public static final String NEW_PAYPWD = "newPayPwd";//新支付密码

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

	public static final String BANK_PROVINCE_ID = "provinceId";

	public static final String BANK_CITY_ID = "cityId";

	public static final String BIND_TYPE = "bindType";

	public static final String ACCOUNT_NO = "accountNo";

	public static final String CARD_NUM = "cardNum";

	public static final String CARD_INFO = "cardInfo";

	public static final String ORDER_ID = "orderId";
	
	public static final String ORGI_ORDER_ID = "origOrderId";
	
	public static final String ORGI_DATE = "origDate";

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
	
	public static final String INVITATION_CODE = "invitationCode";

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

	public static final String SMS_TYPE = "smsType";
	
	public static final String CARD_PHONE_NO = "cardPhoneNo";
	// 完善用户信息上传图片
	public static final String IMG = "img";
	public static final String IMG1 = "img1";
	public static final String IMG2 = "img2";
	// 图片类型
	public static final String IMG_APPLY_TYPE = "imgApplyType";
	// 图片MD5
	public static final String IMG_SIGN = "imgSign";
	
	public static final String IMG_SIGN1 = "imgSign1";
	
	public static final String IMG_SIGN2 = "imgSign2";
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
	
	// 商户地址
	public static final String MCC_ID = "mccId";

	// 商户营业执照号
	public static final String BUSINESS_LICENCE = "businessLicence";
	
	public static final String IP = "ip";
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
	public static final String PROVINCE_CODE="provinceId";
	
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
	
	// 安全码
	public static final String CVN = "cvn";
	
	// 卡有效期
	public static final String CARD_VALIDATE = "cardValidate";
	
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
	
	public static final String CNAPS = "cnaps";
	
	//币种
	public static final String CURRENCY_CODE="currencyCode";
	
	//最后一条的RecordId
	public static final String LAST_RECORDID="lastRecordId";
	
	public static final String TYPE_ID="typeId";
	
	/*卡券*/
	public static final String CITYID = "citycode";
	public static final String COUPON_NAME = "coupon_name";
	public static final String ID = "id";
	public static final String COUPON_ORDER_ID = "order_id";
	public static final String BUY_PRICE = "buy_price";
	public static final String BUY_NUM = "buy_num";
	public static final String BUY_TOTALMONEY = "buy_totalmoney";
	public static final String PAYTYPE= "paytype";
	public static final String PAY_STATUS = "pay_status";
	public static final String PAY_ORDERID = "pay_orderid";
	
	
	public static final String CUSTOMER_NO = "customerNo";
	
	//意见反馈
	public static final String FEEDBACK = "feedBack";
	
	public static final String FEE = "fee";
	
	public static final String QRCODEURL = "qrCodeUrl";
	
	public static final String CITYNAME = "cityName";
	
	public static final String BANKCARDPIC = "bankCardPic";
	
	/** 无卡支付  */
	//设备编码
	public static final String DEVICE_ID = "deviceId";
	
	public static final String DEVICE_TYPE = "deviceType";
		
		
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
		Document doc = null;
		try {
			// xmlStr = new String(xmlStr.getBytes(),"UTF-8");
			doc = DocumentHelper.parseText(xmlStr);
			Element rootElt = doc.getRootElement(); // 获取根节点
			if (rootElt.getName().equals("QtPay")) {
				Iterator it_attr = rootElt.attributeIterator();
				while (it_attr.hasNext()) {
					Attribute attr = (Attribute) it_attr.next();
					if (attr.getName().equals(APPLICATION)) {
						resultMap.put(APPLICATION, attr.getValue());
					}
					if (attr.getName().equals(VERSION)) {
						resultMap.put(VERSION, attr.getValue());
					}
					if (attr.getName().equals(OS_TYPE)) {
						resultMap.put(OS_TYPE, attr.getValue());
					}
					if (attr.getName().equals(MOBILE_SERIAL_NUM)) {
						resultMap.put(MOBILE_SERIAL_NUM, attr.getValue());
					}
					if (attr.getName().equals(USER_IP)) {
						resultMap.put(USER_IP, attr.getValue());
					}
					if (attr.getName().equals(CLIENT_TYPE)) {
						resultMap.put(CLIENT_TYPE, attr.getValue());
					}
					if (attr.getName().equals(PHONE)) {
						resultMap.put(PHONE, attr.getValue());
					}
					if (attr.getName().equals(TOKEN)) {
						resultMap.put(TOKEN, attr.getValue());
					}
					if (attr.getName().equals(APP_USER)) {
						resultMap.put(APP_USER, attr.getValue());
					}

				}
				Iterator it_element = rootElt.elementIterator();
				while (it_element.hasNext()) {
					Element element = (Element) it_element.next();
					if (element.getName().equals(PARAMETER)) {
						resultMap.put(PARAMETER, element.getText());
					}
					if (element.getName().equals(TRANSDATE)) {
						resultMap.put(TRANSDATE, element.getText());
					}
					if (element.getName().equals(TRANSTIME)) {
						resultMap.put(TRANSTIME, element.getText());
					}
					if (element.getName().equals(TRANSLOGNO)) {
						resultMap.put(TRANSLOGNO, element.getText());
					}
					if (element.getName().equals(USER_NAME)) {
						resultMap.put(USER_NAME, element.getText());
					}
					if (element.getName().equals(PASSWD)) {
						resultMap.put(PASSWD, element.getText());
					}
					if (element.getName().equals(MOBILE_NO)) {
						resultMap.put(MOBILE_NO, element.getText());
					}
					if (element.getName().equals(MOBILE_MAC)) {
						resultMap.put(MOBILE_MAC, element.getText());
					}
					if (element.getName().equals(REAL_NAME)) {
						resultMap.put(REAL_NAME, element.getText());
					}
					if (element.getName().equals(CERT_TYPE)) {
						resultMap.put(CERT_TYPE, element.getText());
					}
					if (element.getName().equals(CERT_PID)) {
						resultMap.put(CERT_PID, element.getText());
					}
					if (element.getName().equals(EMAIL)) {
						resultMap.put(EMAIL, element.getText());
					}
					if (element.getName().equals(NEW_PASSWD)) {
						resultMap.put(NEW_PASSWD, element.getText());
					}
					if (element.getName().equals(APP_TYPE)) {
						resultMap.put(APP_TYPE, element.getText());
					}
					if (element.getName().equals(TERM_ID)) {
						resultMap.put(TERM_ID, element.getText());
					}
					if (element.getName().equals(ACCT_TYPE)) {
						resultMap.put(ACCT_TYPE, element.getText());
					}
					if (element.getName().equals(DETAIL_SEQUENCE)) {
						resultMap.put(DETAIL_SEQUENCE, element.getText());
					}
					if (element.getName().equals(CARD_NO)) {
						resultMap.put(CARD_NO, element.getText());
					}
					if (element.getName().equals(CARD_PASSWD)) {
						resultMap.put(CARD_PASSWD, element.getText());
					}
					if (element.getName().equals(NEW_CARD_PASSWD)) {
						resultMap.put(NEW_CARD_PASSWD, element.getText());
					}
					if (element.getName().equals(CARD_INDEX)) {
						resultMap.put(CARD_INDEX, element.getText());
					}
					if (element.getName().equals(CARD_TAG)) {
						resultMap.put(CARD_TAG, element.getText());
					}
					if (element.getName().equals(BANK_ID)) {
						resultMap.put(BANK_ID, element.getText());
					}
					if (element.getName().equals(BANK_NAME)) {
						resultMap.put(BANK_NAME, element.getText());
					}
					if (element.getName().equals(BANK_PROVINCE)) {
						resultMap.put(BANK_PROVINCE, element.getText());
					}
					if (element.getName().equals(BANK_CITY)) {
						resultMap.put(BANK_CITY, element.getText());
					}
					if (element.getName().equals(CUSTOMER_ID)) {
						resultMap.put(CUSTOMER_ID, element.getText());
					}
					if (element.getName().equals(BIND_TYPE)) {
						resultMap.put(BIND_TYPE, element.getText());
					}
					if (element.getName().equals(ACCOUNT_NO)) {
						resultMap.put(ACCOUNT_NO, element.getText());
					}
					if (element.getName().equals(CARD_NUM)) {
						resultMap.put(CARD_NUM, element.getText());
					}
					if (element.getName().equals(CARD_INFO)) {
						resultMap.put(CARD_INFO, element.getText());
					}
					if (element.getName().equals(CARD_PASSWD)) {
						resultMap.put(CARD_PASSWD, element.getText());
					}
					if (element.getName().equals(ORDER_ID)) {
						resultMap.put(ORDER_ID, element.getText());
					}
					if (element.getName().equals(ORGI_ORDER_ID)) {
						resultMap.put(ORGI_ORDER_ID, element.getText());
					}
					if (element.getName().equals(ORGI_DATE)) {
						resultMap.put(ORGI_DATE, element.getText());
					}
					if (element.getName().equals(MERCHANT_ID)) {
						resultMap.put(MERCHANT_ID, element.getText());
					}
					if (element.getName().equals(PRODUCT_ID)) {
						resultMap.put(PRODUCT_ID, element.getText());
					}
					if (element.getName().equals(ORDER_AMT)) {
						resultMap.put(ORDER_AMT, element.getText());
					}
					if (element.getName().equals(ORDER_DESC)) {
						resultMap.put(ORDER_DESC, element.getText());
					}
					if (element.getName().equals(CASH_AMT)) {
						resultMap.put(CASH_AMT, element.getText());
					}
					if (element.getName().equals(PAY_TOOL)) {
						resultMap.put(PAY_TOOL, element.getText());
					}
					if (element.getName().equals(ORDER_REMARK)) {
						resultMap.put(ORDER_REMARK, element.getText());
					}
					if (element.getName().equals(ORDER_NO)) {
						resultMap.put(ORDER_NO, element.getText());
					}
					if (element.getName().equals(ORDER_CONTENT)) {
						resultMap.put(ORDER_CONTENT, element.getText());
					}
					if (element.getName().equals(INSTRUCTION_VERSION)) {
						resultMap.put(INSTRUCTION_VERSION, element.getText());
					}
					if (element.getName().equals(NOTICE_CODE)) {
						resultMap.put(NOTICE_CODE, element.getText());
					}
					if (element.getName().equals(INVITATION_CODE)) {
						resultMap.put(INVITATION_CODE, element.getText());
					}
					if (element.getName().equals(BANK_VERSION)) {
						resultMap.put(BANK_VERSION, element.getText());
					}
					if (element.getName().equals(BANK_PROVINCE_ID)) {
						resultMap.put(BANK_PROVINCE_ID, element.getText());
					}
					if (element.getName().equals(BANK_CITY_ID)) {
						resultMap.put(BANK_CITY_ID, element.getText());
					}
					if (element.getName().equals(CONDITION)) {
						resultMap.put(CONDITION, element.getText());
					}
					if (element.getName().equals(OFFSET)) {
						resultMap.put(OFFSET, element.getText());
					}
					if (element.getName().equals(SIGN)) {
						resultMap.put(SIGN, element.getText());
					}
					if (element.getName().equals(ENTER_NAME)) {
						resultMap.put(ENTER_NAME, element.getText());
					}
					if (element.getName().equals(ENTER_CERT_TYPE)) {
						resultMap.put(ENTER_CERT_TYPE, element.getText());
					}
					if (element.getName().equals(ENTER_CERT_PID)) {
						resultMap.put(ENTER_CERT_PID, element.getText());
					}
					if (element.getName().equals(ENTER_ADDRESS)) {
						resultMap.put(ENTER_ADDRESS, element.getText());
					}
					if (element.getName().equals(LEGAL_NAME)) {
						resultMap.put(LEGAL_NAME, element.getText());
					}
					if (element.getName().equals(LEGAL_CERT_TYPE)) {
						resultMap.put(LEGAL_CERT_TYPE, element.getText());
					}
					if (element.getName().equals(LEGAL_CERT_PID)) {
						resultMap.put(LEGAL_CERT_PID, element.getText());
					}
					if (element.getName().equals(CONTACT_NAME)) {
						resultMap.put(CONTACT_NAME, element.getText());
					}
					if (element.getName().equals(CONTACT_PHONE)) {
						resultMap.put(CONTACT_PHONE, element.getText());
					}
					if (element.getName().equals(ENTER_EMAIL)) {
						resultMap.put(ENTER_EMAIL, element.getText());
					}
					if (element.getName().equals(LONGITUDE)) {
						resultMap.put(LONGITUDE, element.getText());
					}
					if (element.getName().equals(LATITUDE)) {
						resultMap.put(LATITUDE, element.getText());
					}
					if (element.getName().equals(SIGN_PIC_ASCII)) {
						resultMap.put(SIGN_PIC_ASCII, element.getText());
					}
					if (element.getName().equals(PIC_SIGN)) {
						resultMap.put(PIC_SIGN, element.getText());
					}
					if (element.getName().equals(PID_IMG)) {
						resultMap.put(PID_IMG, element.getText());
					}
					if (element.getName().equals(PID_ANTI_IMG)) {
						resultMap.put(PID_ANTI_IMG, element.getText());
					}
					if (element.getName().equals(PIC)) {
						resultMap.put(PIC, element.getText());
					}
					if (element.getName().equals(PID_IMG_SIGN)) {
						resultMap.put(PID_IMG_SIGN, element.getText());
					}
					if (element.getName().equals(PID_ANTI_IMG_SIGN)) {
						resultMap.put(PID_ANTI_IMG_SIGN, element.getText());
					}
					if (element.getName().equals(BRANCH_BANK_ID)) {
						resultMap.put(BRANCH_BANK_ID, element.getText());
					}
					if (element.getName().equals(ACCOUNT_NAME)) {
						resultMap.put(ACCOUNT_NAME, element.getText());
					}
					if (element.getName().equals(LOSS_TYPE)) {
						resultMap.put(LOSS_TYPE, element.getText());
					}
					if (element.getName().equals(CITY_CODE)) {
						resultMap.put(CITY_CODE, element.getText());
					}
					if (element.getName().equals(ITEM_ID)) {
						resultMap.put(ITEM_ID, element.getText());
					}
					if (element.getName().equals(UUID)) {
						resultMap.put(UUID, element.getText());
					}
					if (element.getName().equals(BILL_KEY)) {
						resultMap.put(BILL_KEY, element.getText());
					}
					if (element.getName().equals(COMPANY_ID)) {
						resultMap.put(COMPANY_ID, element.getText());
					}
					if (element.getName().equals(FIELD_SET)) {
						resultMap.put(FIELD_SET, element.getText());
					}
					if (element.getName().equals(CONTRACT_NO)) {
						resultMap.put(CONTRACT_NO, element.getText());
					}
					if (element.getName().equals(CUSTOMER_NAME)) {
						resultMap.put(CUSTOMER_NAME, element.getText());
					}
					if (element.getName().equals(PAY_AMOUNT)) {
						resultMap.put(PAY_AMOUNT, element.getText());
					}
					if (element.getName().equals(PERIOD_ID)) {
						resultMap.put(PERIOD_ID, element.getText());
					}
					if (element.getName().equals(BOOK_PERIODS)) {
						resultMap.put(BOOK_PERIODS, element.getText());
					}
					if (element.getName().equals(TOTAL_AMT)) {
						resultMap.put(TOTAL_AMT, element.getText());
					}
					if (element.getName().equals(QUERY_FLAG)) {
						resultMap.put(QUERY_FLAG, element.getText());
					}
					if (element.getName().equals(LOTTERY_ID)) {
						resultMap.put(LOTTERY_ID, element.getText());
					}
					if (element.getName().equals(BET_DATA)) {
						resultMap.put(BET_DATA, element.getText());
					}
					if (element.getName().equals(CASH_TYPE)) {
						resultMap.put(CASH_TYPE, element.getText());
					}
					if (element.getName().equals(TRANS_TYPE)) {
						resultMap.put(TRANS_TYPE, element.getText());
					}
					if (element.getName().equals(REFERRER_MOBILE_NO)) {
						resultMap.put(REFERRER_MOBILE_NO, element.getText());
					}
					if (element.getName().equals(HOUSE_FLAG)) { // 获取火车站列表查询
						resultMap.put(HOUSE_FLAG, element.getText());
					}
					if (element.getName().equals(OUT_CITY)) { // 获取火车车次余票信息查询
						resultMap.put(OUT_CITY, element.getText());
					}
					if (element.getName().equals(ARR_CITY)) {
						resultMap.put(ARR_CITY, element.getText());
					}
					if (element.getName().equals(OUT_DATE)) {
						resultMap.put(OUT_DATE, element.getText());
					}
					if (element.getName().equals(TRAIN_TYPE)) {
						resultMap.put(TRAIN_TYPE, element.getText());
					}
					if (element.getName().equals(TRAIN_NO)) {
						resultMap.put(TRAIN_NO, element.getText());
					}
					if (element.getName().equals(TIME_QUAN_TUM)) {
						resultMap.put(TIME_QUAN_TUM, element.getText());
					}
					if (element.getName().equals(PAGE_SIZE)) {
						resultMap.put(PAGE_SIZE, element.getText());
					}
					if (element.getName().equals(PAGE)) {
						resultMap.put(PAGE, element.getText());
					}
					if (element.getName().equals(TRAIN_INFO)) {
						resultMap.put(TRAIN_INFO, element.getText());
					}
					if (element.getName().equals(PASSENGER_INFO)) {
						resultMap.put(PASSENGER_INFO, element.getText());
					}
					if (element.getName().equals(CONTACT_INFO)) {
						resultMap.put(CONTACT_INFO, element.getText());
					}
					if (element.getName().equals(EXP_INFO)) {
						resultMap.put(EXP_INFO, element.getText());
					}
					if (element.getName().equals(BEGIN_DATE)) {
						resultMap.put(BEGIN_DATE, element.getText());
					}
					if (element.getName().equals(END_DATE)) {
						resultMap.put(END_DATE, element.getText());
					}
					if (element.getName().equals(LINK_MAN_NAME)) {
						resultMap.put(LINK_MAN_NAME, element.getText());
					}
					if (element.getName().equals(LINK_MAN_SEX)) {
						resultMap.put(LINK_MAN_SEX, element.getText());
					}
					if (element.getName().equals(CARD_TYPE)) {
						resultMap.put(CARD_TYPE, element.getText());
					}
					if (element.getName().equals(LINK_MAN_TYPE)) {
						resultMap.put(LINK_MAN_TYPE, element.getText());
					}
					if (element.getName().equals(SEQUENCE_NUM)) {
						resultMap.put(SEQUENCE_NUM, element.getText());
					}
					if (element.getName().equals(LINK_MAN_NUM)) {
						resultMap.put(LINK_MAN_NUM, element.getText());
					}
					if (element.getName().equals(LINK_MAN_PHONE)) {
						resultMap.put(LINK_MAN_PHONE, element.getText());
					}
					if (element.getName().equals(CHARGE_ACCOUNT)) {
						resultMap.put(CHARGE_ACCOUNT, element.getText());
					}
					if (element.getName().equals(PARTNER_ID)) {
						resultMap.put(PARTNER_ID, element.getText());
					}
					if (element.getName().equals(SEARCH_DATE)) {
						resultMap.put(SEARCH_DATE, element.getText());
					}
					if (element.getName().equals(TRANS_NO)) {
						resultMap.put(TRANS_NO, element.getText());
					}
					if (element.getName().equals(AMOUNT)) {
						resultMap.put(AMOUNT, element.getText());
					}
					if (element.getName().equals(MUGSHOT)) {
						resultMap.put(MUGSHOT, element.getText());
					}
					if (element.getName().equals(FEE_CODE)) {
						resultMap.put(FEE_CODE, element.getText());
					}
					if(element.getName().equals(SMS_TYPE)) {
						resultMap.put(SMS_TYPE, element.getText());
					}
					if (element.getName().equals(IMG)) {
						resultMap.put(IMG, element.getText());
					}
					if (element.getName().equals(IMG1)) {
						resultMap.put(IMG1, element.getText());
					}
					if (element.getName().equals(IMG2)) {
						resultMap.put(IMG2, element.getText());
					}
					if (element.getName().equals(IMG_APPLY_TYPE)) {
						resultMap.put(IMG_APPLY_TYPE, element.getText());
					}
					if (element.getName().equals(IMG_SIGN)) {
						resultMap.put(IMG_SIGN, element.getText());
					}
					if (element.getName().equals(IMG_SIGN1)) {
						resultMap.put(IMG_SIGN1, element.getText());
					}
					if (element.getName().equals(IMG_SIGN2)) {
						resultMap.put(IMG_SIGN2, element.getText());
					}
					if (element.getName().equals(CARD_PHONE_NO)) {
						resultMap.put(CARD_PHONE_NO, element.getText());
					}
					if (element.getName().equals(USER_TYPE)) {
						resultMap.put(USER_TYPE, element.getText());
					}
					if (element.getName().equals(USER_IDENCCID)) {
						resultMap.put(USER_IDENCCID, element.getText());
					}
					if (element.getName().equals(PARTNER_ID_CODE)) {
						resultMap.put(PARTNER_ID_CODE, element.getText());
					}
					if (element.getName().equals(PRICE)) {
						resultMap.put(PRICE, element.getText());
					}
					if (element.getName().equals(GOOD_NAME)) {
						resultMap.put(GOOD_NAME, element.getText());
					}
					if (element.getName().equals(GOOD_PIC)) {
						resultMap.put(GOOD_PIC, element.getText());
					}
					if (element.getName().equals(GOOD_STATUS)) {
						resultMap.put(GOOD_STATUS, element.getText());
					}
					if (element.getName().equals(GOOD_ID)) {
						resultMap.put(GOOD_ID, element.getText());
					}
					if (element.getName().equals(REASON)) {
						resultMap.put(REASON, element.getText());
					}
					if (element.getName().equals(STATUS)) {
						resultMap.put(STATUS, element.getText());
					}
					if (element.getName().equals(REASON_ID)) {
						resultMap.put(REASON_ID, element.getText());
					}
					if (element.getName().equals(MER_ORDER_ID)) {
						resultMap.put(MER_ORDER_ID, element.getText());
					}
					if (element.getName().equals(BUSINESS_LICENCE)) {
						resultMap.put(BUSINESS_LICENCE, element.getText());
					}
					if (element.getName().equals(MERCHANT_ADDRESS)) {
						resultMap.put(MERCHANT_ADDRESS, element.getText());
					}
					if (element.getName().equals(MERCHANT_NAME)) {
						resultMap.put(MERCHANT_NAME, element.getText());
					}
					if (element.getName().equals(MCC_ID)) {
						resultMap.put(MCC_ID, element.getText());
					}
					if (element.getName().equals(ADDREE_PIC)) {
					    resultMap.put(ADDREE_PIC, element.getText());
				    }
					/*
					if (element.getName().equals(SYN_FLAG)) {
						resultMap.put(SYN_FLAG, element.getText());
					}
					if (element.getName().equals(SIMP_NAME)) {
						resultMap.put(SIMP_NAME, element.getText());
					}
					if (element.getName().equals(TX_GAME_ID)) {
						resultMap.put(TX_GAME_ID, element.getText());
					}
					if (element.getName().equals(ORDERID)) {
						resultMap.put(ORDERID, element.getText());
					}
					*/
					
					//IC卡使用交易信息
					if (element.getName().equals(TX_HOST_IC_DATA)) {
						resultMap.put(TX_HOST_IC_DATA, element.getText());
					}
					//IC卡序列号
					if (element.getName().equals(TX_HOST_IC_CSN)) {
						resultMap.put(TX_HOST_IC_CSN, element.getText());
					}
					//IC卡有效期
					/*
					if (element.getName().equals(TX_HOST_FLD_60)) {
						resultMap.put(TX_HOST_FLD_60, element.getText());
					}
					//
					if (element.getName().equals(TX_HOST_PIN_COND)) {
						resultMap.put(TX_HOST_PIN_COND, element.getText());
					}
					*/
					//
					if (element.getName().equals(TX_HOST_EXPIRATION_DATE)) {
						resultMap.put(TX_HOST_EXPIRATION_DATE, element.getText());
					}
					if (element.getName().equals(MSG_SIZE)) {
						resultMap.put(MSG_SIZE, element.getText());
					}
					if (element.getName().equals(FIRST_MSG_ID)) {
						resultMap.put(FIRST_MSG_ID, element.getText());
					}
					if (element.getName().equals(LAST_MSG_ID)) {
						resultMap.put(LAST_MSG_ID, element.getText());
					}
					if (element.getName().equals(REQUEST_TYPE)) {
						resultMap.put(REQUEST_TYPE, element.getText());
					}
					if (element.getName().equals(MSG_ID)) {
						resultMap.put(MSG_ID, element.getText());
					}
					
					if (element.getName().equals(DEVICE_TOKEN)) {
						resultMap.put(DEVICE_TOKEN, element.getText());
					}
					
					if (element.getName().equals(SHORT_COMPARY)) {
						resultMap.put(SHORT_COMPARY, element.getText());
					}
					if (element.getName().equals(APP_ICON)) {
						resultMap.put(APP_ICON, element.getText());
					}
					if (element.getName().equals(LOGO)) {
						resultMap.put(LOGO, element.getText());
					}
					// 商城
					if (element.getName().equals(FIRST_DATA_ID)) {
						resultMap.put(FIRST_DATA_ID, element.getText());
					}
					if (element.getName().equals(LAST_DATA_ID)) {
						resultMap.put(LAST_DATA_ID, element.getText());
					}
					if (element.getName().equals(DATA_SIZE)) {
						resultMap.put(DATA_SIZE, element.getText());
					}
					if (element.getName().equals(ICON)) {
						resultMap.put(ICON, element.getText());
					}
					if (element.getName().equals(TITLE)) {
						resultMap.put(TITLE, element.getText());
					}
					if (element.getName().equals(PRICE)) {
						resultMap.put(PRICE, element.getText());
					}
					if (element.getName().equals(AMOUNT)) {
						resultMap.put(AMOUNT, element.getText());
					}
					if (element.getName().equals(COMMODITY_ID)) {
						resultMap.put(COMMODITY_ID, element.getText());
					}
					if (element.getName().equals(DESC)) {
						resultMap.put(DESC, element.getText());
					}
					//交易记录列表
					
					if (element.getName().equals(FILTER)) {
						resultMap.put(FILTER, element.getText());
					}
					if (element.getName().equals(RECORDID)) {
						resultMap.put(RECORDID, element.getText());
					}
					
					if (element.getName().equals(TX_IP_IME_ICPH)) {
						resultMap.put(TX_IP_IME_ICPH, element.getText());
					}
					
					if (element.getName().equals(RESERVE)) {
						resultMap.put(RESERVE, element.getText());
					}
					
					if (element.getName().equals(TX_COMMODITYIDS)) {
						resultMap.put(TX_COMMODITYIDS, element.getText());
					}
					
					if (element.getName().equals(BIND_ID)) {
						resultMap.put(BIND_ID, element.getText());
					}
					
					if (element.getName().equals(PAY_INFO)) {
						resultMap.put(PAY_INFO, element.getText());
					}
					
					if (element.getName().equals(ENCODE_TYPE)) {
						resultMap.put(ENCODE_TYPE, element.getText());
					}
					
					if (element.getName().equals(VALIDATE_CODE)) {
						resultMap.put(VALIDATE_CODE, element.getText());
					}
					
					if (element.getName().equals(PROVINCE_CODE)) {
						resultMap.put(PROVINCE_CODE, element.getText());
					}
					
					if (element.getName().equals(BAND_TYPE)) {
						resultMap.put(BAND_TYPE, element.getText());
					}
					
					if (element.getName().equals(PAY_TYPE)) {
						resultMap.put(PAY_TYPE, element.getText());
					}
					if (element.getName().equals(ORGANIZATION)) {
						resultMap.put(ORGANIZATION, element.getText());
					}
					
					//汇率转换
					if (element.getName().equals(EXCHANGE_SOURCE)) {
						resultMap.put(EXCHANGE_SOURCE, element.getText());
					}
					if (element.getName().equals(EXCHANGE_GOAL)) {
						resultMap.put(EXCHANGE_GOAL, element.getText());
					}
					if (element.getName().equals(MONEY)) {
						resultMap.put(MONEY, element.getText());
					}
					
					if (element.getName().equals(RATE_CODE)) {
						resultMap.put(RATE_CODE, element.getText());
					}
					
					if (element.getName().equals(DEVICE_ID)) {
						resultMap.put(DEVICE_ID, element.getText());
					}
					
					if (element.getName().equals(DEVICE_TYPE)) {
						resultMap.put(DEVICE_TYPE, element.getText());
					}
					
					if (element.getName().equals(PSAM_ID)) {
						resultMap.put(PSAM_ID, element.getText());
					}
					if (element.getName().equals(OPT_TYPE)) {
						resultMap.put(OPT_TYPE, element.getText());
					}
					if (element.getName().equals(FOUR_PHONE)) {
						resultMap.put(FOUR_PHONE, element.getText());
					}
					if (element.getName().equals(HEAD_PIC)) {
						resultMap.put(HEAD_PIC, element.getText());
					}
					if (element.getName().equals(VERSION_STATUS)) {
						resultMap.put(VERSION_STATUS, element.getText());
					}
					if (element.getName().equals(TRADEDELAY)) {
						resultMap.put(TRADEDELAY, element.getText());
					}
					if (element.getName().equals(WALLET_TYPE)) {
						resultMap.put(WALLET_TYPE, element.getText());
					}
					if (element.getName().equals(PAY_ACC)) {
						resultMap.put(PAY_ACC, element.getText());
					}
					if (element.getName().equals(CVN)) {
						resultMap.put(CVN, element.getText());
					}
					if (element.getName().equals(CARD_VALIDATE)) {
						resultMap.put(CARD_VALIDATE, element.getText());
					}
					if (element.getName().equals(CNAPS)) {
						resultMap.put(CNAPS, element.getText());
					}
					if (element.getName().equals(CURRENCY_CODE)) {
						resultMap.put(CURRENCY_CODE, element.getText());
					}
					if (element.getName().equals(LAST_RECORDID)) {
						resultMap.put(LAST_RECORDID, element.getText());
					}
					if (element.getName().equals(TYPE_ID)) {
						resultMap.put(TYPE_ID, element.getText());
					}
					/*卡券*/
					if (element.getName().equals(CITYID)) {
						resultMap.put(CITYID, element.getText());
					}
					if (element.getName().equals(COUPON_NAME)) {
						resultMap.put(COUPON_NAME, element.getText());
					}
					if (element.getName().equals(ID)) {
						resultMap.put(ID, element.getText());
					}
					if (element.getName().equals(COUPON_ORDER_ID)) {
						resultMap.put(COUPON_ORDER_ID, element.getText());
					}
					if (element.getName().equals(BUY_PRICE)) {
						resultMap.put(BUY_PRICE, element.getText());
					}
					if (element.getName().equals(BUY_NUM)) {
						resultMap.put(BUY_NUM, element.getText());
					}
					if (element.getName().equals(BUY_TOTALMONEY)) {
						resultMap.put(BUY_TOTALMONEY, element.getText());
					}
					if (element.getName().equals(PAYTYPE)) {
						resultMap.put(PAYTYPE, element.getText());
					}
					if (element.getName().equals(PAY_STATUS)) {
						resultMap.put(PAY_STATUS, element.getText());
					}
					if (element.getName().equals(PAY_ORDERID)) {
						resultMap.put(PAY_ORDERID, element.getText());
					}
					if (element.getName().equals(FEEDBACK)) {
						resultMap.put(FEEDBACK, element.getText());
					}
					if (element.getName().equals(LOGINPWD)) {
						resultMap.put(LOGINPWD, element.getText());
					}
					if (element.getName().equals(PAYPWD)) {
						resultMap.put(PAYPWD, element.getText());
					}
					if (element.getName().equals(NEW_LOGINPWD)) {
						resultMap.put(NEW_LOGINPWD, element.getText());
					}
					if (element.getName().equals(NEW_PAYPWD)) {
						resultMap.put(NEW_PAYPWD, element.getText());
					}
					if (element.getName().equals(CUSTOMER_NO)) {
						resultMap.put(CUSTOMER_NO, element.getText());
					}
					if (element.getName().equals(FEE)) {
						resultMap.put(FEE, element.getText());
					}
					if (element.getName().equals(QRCODEURL)) {
						resultMap.put(QRCODEURL, element.getText());
					}
					if (element.getName().equals(CITYNAME)) {
						resultMap.put(CITYNAME, element.getText());
					}
					if (element.getName().equals(BANKCARDPIC)) {
						resultMap.put(BANKCARDPIC, element.getText());
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

}
