package com.jfpay.preposing.remote;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.ArrayList;
import cn.hnae.tuxedojms.allocate.TuxedoConst;
import cn.hnae.tuxedojms.allocate.TxServiceHelper;
import com.jfpay.preposing.support.SystemCode;
import com.jfpay.preposing.utils.Format;
import com.jfpay.preposing.utils.FrtRiskHelper;
import com.jfpay.preposing.xml.ParseCoreReceiveXml;
import org.apache.log4j.Logger;
import com.jfpay.preposing.support.ReturnMapCreateTool;

public class SendPayService {

	static Logger log = Logger.getLogger(SendPayService.class);

	public static HashMap<String, String> sendPay(HashMap<String, String> map)
			throws Exception {
		// 判断读取的内存地址
		if (TuxedoConst.cacheFlag) {
			map.put("cacheFlag", "true");
		} else {
			map.put("cacheFlag", "false");
		}

		map = sendMapFormat(map);
		// 如果MAP转换过程中产生respCode，则交易失败
		if (map.containsKey("respCode")) {
			if (map.get("respCode").equals(
					SystemCode.NO_SUCH_TRANSACTION.getCode()))
				return map;
			else
				return ReturnMapCreateTool.createMapError(map);
		}
		try {
			map = ConnectTuxedoFactory.createConnectWay(map);
			log.info("|> FRT_recv_RES fm CORE :: " + showmap(map));
		} catch (Exception e) {
			log.info(map.get("ORG_TX_DATE") + map.get("ORG_TX_TIME")
					+ map.get("ORG_TX_LOGNO") + " 调用服务:"
					+ map.get("SERVICE_CODE") + " 时发生异常失败");
			log.error(e.toString(), e);
			throw e;
		}

		return resultMapFormat(map);
	}

	private static HashMap<String, String> sendMapFormat(
			HashMap<String, String> map) {
		// 前置请求主机map
		HashMap<String, String> prepMap = null;
		// 删除空值的集合
		List<String> list = new ArrayList<String>();
		String[] transDef = TxServiceHelper.getTransTpcallDef(
				map.get(ParseCoreReceiveXml.APPLICATION),
				Boolean.valueOf(map.get("cacheFlag")));
		// 如果所请求的交易不存在(无此交易),原请求map进行处理并返回
		if (transDef == null) {
			map.put("respCode", SystemCode.NO_SUCH_TRANSACTION.getCode());
			map.put("respDesc", SystemCode.NO_SUCH_TRANSACTION.getDesc());
			return map;
		}
		// 交易是否可用(0：不可用 1:可用),原请求map进行处理并返回
		if (transDef[6].equals("0")) {
			map.put("respCode", SystemCode.TRANSACTION_FAILURE.getCode());
			map.put("respDesc", SystemCode.TRANSACTION_FAILURE.getDesc());
			return map;
		}
//		if (!checkRiskRule(map))
//			return map;
		String serveCode = "";
		// 如果服务代码为000000，则由产品类型来决定
		if (transDef[3].equals("1")) {
			serveCode = TxServiceHelper.getServeCode(transDef[0],
					map.get(ParseCoreReceiveXml.MERCHANT_ID),
					Boolean.valueOf(map.get("cacheFlag")));
		} else {
			serveCode = transDef[2];
		}
		prepMap = new HashMap<String, String>();
		// 添加系统级参数
		prepMap.put("SND_TX_DATE", Format.formatDate());
		prepMap.put("SND_TX_TIME", Format.formatTime());
		prepMap.put("SND_TX_CYCLE", Format.formatDate());
		prepMap.put("SND_TX_LOGNO", Format.getTrmSeqNum());
		prepMap.put("ORG_TX_REF_NO",
				prepMap.get("SND_TX_DATE") + prepMap.get("SND_TX_TIME")
						+ prepMap.get("SND_TX_LOGNO"));
		prepMap.put("ORG_TX_CYCLE", Format.formatDate());
		prepMap.put("SERVICE_CODE", serveCode);
		prepMap.put("CHANNEL_ID", "0001"); // 手机渠道
		prepMap.put("BRANCH_ID", "29000002"); // 机构码 测试00800002 正式29000002
		prepMap.put("TERMINAL_ID", map.get(ParseCoreReceiveXml.MOBILE_NO));
		prepMap.put("cacheFlag", map.get("cacheFlag"));
		prepMap.put(ParseCoreReceiveXml.APPLICATION,
				map.get(ParseCoreReceiveXml.APPLICATION));
		// 获取客户端--〉前置的业务域信息
		List<String> c2pbusinessReq = TxServiceHelper.getFieldTransformDef(
				map.get(ParseCoreReceiveXml.APPLICATION),
				TuxedoConst.CLIENT_TO_PREPOSE_TRANSFORM,
				Boolean.valueOf(map.get("cacheFlag")));
		// 获取客户端--〉前置的统一域信息([version]\[osType]\[mobileSerialNum]\[userIP])
		List<String> c2punifiedReq = TxServiceHelper.getFieldTransformDef(
				"999999", TuxedoConst.CLIENT_TO_PREPOSE_TRANSFORM,
				Boolean.valueOf(map.get("cacheFlag")));
		if (c2punifiedReq != null) {
			// 客户端--〉前置之间统一域转换
			for (String ud : c2punifiedReq) {
				String[] trans = ud.split("#");
				if (trans[2].equals("2")) {
					if (map.containsKey(trans[0])) {
						if (map.get(trans[0]) == null)
							prepMap.put(trans[1], trans[5]);
						else
							prepMap.put(trans[1], map.get(trans[0]));
					} else {
						prepMap.put(trans[1], trans[5]);
					}
				} else if (trans[2].equals("3")) {
					prepMap.put(trans[1], trans[5]);
				} else {
					prepMap.put(trans[1], map.get(trans[0]));
				}
			}
		}
		if (c2pbusinessReq != null) {
			// 客户端--〉前置之间业务域转换
			for (String tf : c2pbusinessReq) {
				String[] trans = tf.split("#");
				if (trans[2].equals("2")) {
					if (map.containsKey(trans[0])) {
						if (map.get(trans[0]) == null)
							prepMap.put(trans[1], trans[5]);
						else
							prepMap.put(trans[1], map.get(trans[0]));
					} else {
						prepMap.put(trans[1], trans[5]);
					}
				} else if (trans[2].equals("3")) {
					prepMap.put(trans[1], trans[5]);
				} else {
					prepMap.put(trans[1], map.get(trans[0]));
				}
			}
		}
		// 获取前置--〉主机的业务域信息
		List<String> p2hbusinessReq = TxServiceHelper.getFieldTransformDef(
				map.get(ParseCoreReceiveXml.APPLICATION),
				TuxedoConst.PREPOSE_TO_HOST_TRANSFORM,
				Boolean.valueOf(map.get("cacheFlag")));
		// 获取前置--〉主机的统一域信息([version]\[osType]\[mobileSerialNum]\[userIP])
		List<String> p2hunifiedReq = TxServiceHelper.getFieldTransformDef(
				"999999", TuxedoConst.PREPOSE_TO_HOST_TRANSFORM,
				Boolean.valueOf(map.get("cacheFlag")));
		StringBuilder sb = new StringBuilder();
		if (p2hunifiedReq != null) {
			// 前置--〉主机之间统一域转换
			for (String ud : p2hunifiedReq) {
				String[] trans = ud.split("#");
				// 2:没有原值填写默认值
				if (trans[2].equals("2")) {
					if (prepMap.containsKey(trans[0])) {
						if (prepMap.get(trans[0]) == null)
							prepMap.put(trans[0], trans[5]);
					} else {
						prepMap.put(trans[0], trans[5]);
					}
				}
				// 3:填写默认值
				if (trans[2].equals("3")) {
					prepMap.put(trans[0], trans[5]);
				}
				sb.append(trans[0] + ",");
			}
		}
		if (p2hbusinessReq != null) {
			// 前置--〉主机之间业务域转换
			for (String tf : p2hbusinessReq) {
				String[] trans = tf.split("#");
				// 2:没有原值填写默认值,3:填写默认值
				if (trans[2].equals("2")) {
					if (prepMap.containsKey(trans[0])) {
						if (prepMap.get(trans[0]) == null)
							prepMap.put(trans[0], trans[5]);
					} else {
						prepMap.put(trans[0], trans[5]);
					}
				}
				if (trans[2].equals("3")) {
					prepMap.put(trans[0], trans[5]);
				}
				sb.append(trans[0] + ",");
			}
		}
		// 请求域用于MAP转换成FML32
		prepMap.put("reqFieldReqInfo",
				sb.toString().substring(0, sb.lastIndexOf(",")));
		// log.info(map.get("rpoKey") + " 前置----〉核心主机的MAP报文转换完成！"
		// + prepMap.toString());
		log.info("|> FRT_send_REQ to CORE :: " + showmap(prepMap));

		/* added by lw@2014-12-26: 循环遍历map，将空值删除 --------------------- */
		Iterator<Entry<String, String>> entryKeyIterator = prepMap.entrySet()
				.iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, String> e = entryKeyIterator.next();
			if (null == e.getValue() || "null".equalsIgnoreCase(e.getValue())
					|| "".equals(e.getValue())) {
				list.add(e.getKey());
			} else {
				System.out.println("KEY:" + e.getKey() + "......" + "VALUE:"
						+ e.getValue());
			}
		}
		// 删除空值
		prepMap.keySet().removeAll(list);
		System.out.println(prepMap.toString());
		/* --------------------------------------------------------- */

		return prepMap;
	}

	private static HashMap<String, String> resultMapFormat(
			HashMap<String, String> map) {

		// 前置响应客户端map
		HashMap<String, String> resultMap = new HashMap<String, String>();
		// 获取前置--〉客户端的业务域信息
		List<String> transformRspLs = TxServiceHelper.getFieldTransformDef(
				map.get(ParseCoreReceiveXml.APPLICATION),
				TuxedoConst.PREPOSE_TO_CLIENT_TRANSFORM,
				Boolean.valueOf(map.get("cacheFlag")));
		// 获取前置--〉客户端的统一域信息([version]\[osType]\[mobileSerialNum]\[userIP])
		List<String> unifiedRspLs = TxServiceHelper.getFieldTransformDef(
				"999999", TuxedoConst.PREPOSE_TO_CLIENT_TRANSFORM,
				Boolean.valueOf(map.get("cacheFlag")));
		if (unifiedRspLs != null) {
			// 前置-->客户端之间统一域转换
			for (String ud : unifiedRspLs) {
				String[] trans = ud.split("#");
				if (trans[2].equals("2")) {
					if (map.containsKey(trans[0])) {
						if (map.get(trans[0]) == null)
							resultMap.put(trans[1], trans[5]);
						else
							resultMap.put(trans[1], map.get(trans[0]));
					} else {
						resultMap.put(trans[1], trans[5]);
					}
				} else if (trans[2].equals("3")) {
					resultMap.put(trans[1], trans[5]);
				} else {
					resultMap.put(trans[1], map.get(trans[0]));
				}
			}
		}
		if (transformRspLs != null) {
			// 前置-->客户端之间业务域转换
			for (String tf : transformRspLs) {
				String[] trans = tf.split("#");
				if (trans[2].equals("2")) {
					if (map.containsKey(trans[0])) {
						if (map.get(trans[0]) == null)
							resultMap.put(trans[1], trans[5]);
						else
							resultMap.put(trans[1], map.get(trans[0]));
					} else {
						resultMap.put(trans[1], trans[5]);
					}
				} else if (trans[2].equals("3")) {
					resultMap.put(trans[1], trans[5]);
				} else {
					resultMap.put(trans[1], map.get(trans[0]));
				}
			}
		}
		return resultMap;
	}

	/**
	 * Description : <br>
	 * Created on Nov 21, 2014 11:39:01 AM <br>
	 * 
	 * @param argMap
	 * @return
	 */
	public static String showmap(Map argMap) {
		StringBuffer sb = new StringBuffer("Map Data: ");
		if (argMap != null) {
			sb.append(System.getProperty("line.separator"));
			Set entrySet = argMap.entrySet();
			Iterator it = entrySet.iterator();
			while (it.hasNext()) {
				Entry aEntry = (Entry) it.next();
				sb.append(aEntry.getKey());
				sb.append("=");
				sb.append(aEntry.getValue());
				sb.append(System.getProperty("line.separator"));
			}
		} else
			sb.append("mapData is null.");
		return sb.toString();
	}

}
