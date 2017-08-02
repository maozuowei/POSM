package com.jfpay.preposing.utils;

public class JfpayClientUpdate {
    /**
     * 自增序号
     */
    private Integer id;
    
    private String alias;

    /**
     * 客户端类型
     */
    private String clientType;

    /**
     * 客户端版本号
     */
    private String clientVersion;

    /**
     * 客户端下载地址
     */
    private String updatePath;

    /**
     * 是否必须更新, 0为否, 1为是
     */
    private Integer isMustUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getUpdatePath() {
        return updatePath;
    }

    public void setUpdatePath(String updatePath) {
        this.updatePath = updatePath;
    }

    public Integer getIsMustUpdate() {
        return isMustUpdate;
    }

    public void setIsMustUpdate(Integer isMustUpdate) {
        this.isMustUpdate = isMustUpdate;
    }

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
