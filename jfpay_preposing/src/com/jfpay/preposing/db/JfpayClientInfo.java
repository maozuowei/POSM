package com.jfpay.preposing.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import cn.hnae.tuxedojms.utils.DBHelper;

public class JfpayClientInfo {

	static Logger log = Logger.getLogger(JfpayClientInfo.class);

	private static DBHelper db;

	public static HashMap<String, String> getClientUpdateInfo(String clientType) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		try {
			db = new DBHelper(ResourceBundle
					.getBundle("com.jfpay.preposing.properties.database"));
			String sql = "select * from jfpay_client_update where client_type = ?";
			String param[] = { clientType };
			ResultSet rs = db.query(sql, param);
			while (rs.next()) {
				resultMap.put("clientType", rs.getString("client_type"));
				resultMap.put("clientVersion", rs.getString("client_version"));
				resultMap.put("updatePath", rs.getString("update_path"));
				resultMap.put("is_must_update", rs.getString("is_must_update"));
			}
			db.close();
		} catch (Exception e) {
			log.error("Exception:" + e.getMessage());
		} finally {
			// 关闭数据库连接
			if (db != null)
				try {
					db.close();
				} catch (SQLException e) {
					log.error("SQLException:" + e.getMessage());
				}
			db = null;
		}
		return resultMap;
	}
}
