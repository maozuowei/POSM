package com.jfpay.preposing.support;

import java.io.Serializable;

public enum SystemCode implements Serializable {

	SUCCESS("0000", "成功！"),
//	LOGIN_INFO_EXCEPTION("0001","登录信息异常，请重新登录"),
	LOGIN_INFO_EXCEPTION("0001","您的账号在其他地方登录，您已被迫下线，如果不是您本人操作，请及时修改密码"),
	LOGIN_TIME_OUT("0002","登录超时"),
	SUCCESS_LOGOUT("0003", "退出成功！"),
	CALL_HOST_EXCEPTION("9999","调用系统服务失败，请稍后再试"),
	REQ_POOL_FULL("9998","超过系统最大请求数，请稍后再试"),
	NO_SUCH_TRANSACTION("9997","无此交易"),
	TRANSACTION_FAILURE("9996","系统维护中，交易暂不可用"),
	MISSING_ARGUMENT("9995","缺少必填参数"),
	TARGET_CHARGE_AMOUNT_OVERRUN("0084","当日充值金额超限"),
	SYSTEM_MAINTENANCE("0085","系统升级中，交易暂不可用"),
	NPASS_REALNAME_AUTH("0086","为了保障您的资金安全，请先进行实名认证"),
	QCKCASH_AMOUNT_OVERRUN("0087","当日提现金额超限"),
	NO_UPDATED_VERSION("0088","无可升级版本"),
	SIGN_ERROR("9991","SIGN校验失败"),
	REQ_EXCEPTION("9990","请检查您的手机时间是否正常"),
	TRANSACTION_FAILED("9989","交易失败");

	private String code;
	private String desc;

	private SystemCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
}
