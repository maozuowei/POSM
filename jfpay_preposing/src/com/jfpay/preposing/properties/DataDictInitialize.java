package com.jfpay.preposing.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jfpay.preposing.control.CheckPoolSize;
import com.jfpay.preposing.control.LoginTimeOutControl;
import com.jfpay.preposing.control.RequestTimeOutControl;
import com.jfpay.preposing.jms.CacheSyn;
import com.jfpay.preposing.utils.UDPClient;
import com.jfpay.preposing.utils.UDPClientLogin;
import com.jfpay.preposing.utils.UrlCache;

public class DataDictInitialize {

	private static DataDictInitialize instance = null;

	static Logger log = Logger.getLogger(DataDictInitialize.class);

	public static ConcurrentHashMap REQUEST_POOL; // 交易请求池

	public static RequestTimeOutControl REQUEST_TIME_OUT_CONTROL; // 请求超时控制

	public static ConcurrentHashMap LOGIN_INFO_POOL; // 用户登录信息池

	public static LoginTimeOutControl LOGIN_TIME_OUT_CONTROL; // 登陆超时控制

	public static CheckPoolSize CHECK_POOL_SIZE;// 检查请求池大小控制

	public static CacheSyn cacheSyn;

	public static boolean isRun = true;// 线程运行的标志；

	public static boolean isExit = false;// 退出程序的标志；

	/** ****RequestPool-Parameter*** */

	public static long RESPONSE_TIMEOUT;// 同步请求交易超时时间

	public static int REQUEST_LIMITED;// 最大请求数

	public static long LOGIN_TIMEOUT;// 用户登录有效超时时间

	/** ****TPcall-Parameter*********** */

	public static String CONTEXT_URL;

	public static String HOST_CALL_SWITCH;// 主机调用方式开关(tpCall/queue）

	/** ********Queue-Parameter*********** */

	public static String NAME_SPACE;// 队列空间

	public static String SEND_QUEUE_NAME;// 发送队列名称

	public static String RECEIVE_QUEUE_NAME;// 接收队列名称

	public static String CORR_ID;// corrid

	public static int INIT_SEQUENCE;// 初始流水号

	public static int INTERVAL_SEQUENCE;// 流水号间隔段

	public static int LAST_SEQUENCE;// 结束末尾号 (初始流水号+间隔段)

	public static Map toSaveMap;

	private Properties property;

	private FileInputStream inputFile;

	/** ****MemcacheClient-Parameter*** */

	// public static String MEMCACHE_HOST1;//memcache服务器地址与端口(可以有多个)
	//	
	// public static boolean FAIL_OVER;//
	//	
	// public static int INIT_CONN;//初始化时对每个服务器建立的连接数目
	//	
	// public static int MIN_CONN;//每个服务器建立最小的连接数
	//	
	// public static int MAX_CONN;//每个服务器建立最大的连接数
	//	
	// public static long MAINT_SLEEP;//自查线程周期进行工作，其每次休眠时间
	//	
	// public static boolean NAGLE;//如果是true在写数据时不缓冲，立即发送出去
	//	
	// public static int SOCKET_TO;//Socket阻塞读取数据的超时时间
	//	
	// //根据key&hashCode获取SockIO时，通过hash bucket得到SockIO后，如果这个值
	// //是true会检查Socket是否已经连接，如果连接建立正常还会想服务器发送“version\r\n”的指令，并读取数据，这
	// //个过程没有出错才会返回SockIO给上层用，否则返回NULL。所以一般设置为false
	// public static boolean ALIVE_CHECK;
	// public DataDictInitialize(){
	//		
	// }
	/** ****Redis*** */
	public static String REDIS_HOST;//IP
	public static int  REDIS_PORT;//端口号
	public static int  REDIS_TIME_OUT;//超时时间
	public static String  REDIS_PASSWORD;//redis密码
	public static int  REDIS_DATABASE;//redis 第几个数据库
	
	/** ****Https登录异常报文推送*** */
	public static String TOKEN_ERROR_SEND_IP;//IP
	
	public static DataDictInitialize getInstance() {
		if (instance == null) {
			instance = new DataDictInitialize();
		}
		return instance;
	}

	public void init() {
		log.info("开始加载系统数据:");
		String filePath = "/home/weblogic/etc/JfpayPrepoParam.properties";
		//String filePath = "D:/kuaifu/workspace/jfpay_preposing1/build/classes/com/jfpay/preposing/properties/JfpayPrepoParam.properties";
		property = new Properties();
		try {
			inputFile = new FileInputStream(filePath);
			property.load(inputFile);
			inputFile.close();
		} catch (FileNotFoundException ex) {
			System.out.println("读取属性文件 JfpayPrepoParam.properties--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("装载文件 JfpayPrepoParam.properties--->失败!");
			ex.printStackTrace();
		}
		DataDictInitialize.TOKEN_ERROR_SEND_IP = property.getProperty("token_error_send_ip").trim();
		
		DataDictInitialize.REDIS_HOST = property.getProperty("redis_host").trim();
		
		DataDictInitialize.REDIS_PORT = Integer.valueOf(property.getProperty("redis_port").trim());
		
		DataDictInitialize.REDIS_TIME_OUT = Integer.valueOf(property.getProperty("redis_time_out").trim());
		
		DataDictInitialize.REDIS_PASSWORD = property.getProperty("redis_password").trim();
		
		DataDictInitialize.REDIS_DATABASE = Integer.valueOf(property.getProperty("redis_database").trim());
		
		DataDictInitialize.RESPONSE_TIMEOUT = Long.valueOf(property.getProperty("response_timeout").trim());

		DataDictInitialize.REQUEST_LIMITED = Integer.valueOf(property.getProperty("request_limited").trim());

		DataDictInitialize.CONTEXT_URL = property.getProperty("contextUrl").trim();

		DataDictInitialize.LOGIN_TIMEOUT = Long.valueOf(property.getProperty("login_timeout").trim());

		DataDictInitialize.INIT_SEQUENCE = Integer.valueOf(property.getProperty("serial_logno").trim());

		DataDictInitialize.INTERVAL_SEQUENCE = Integer.valueOf(property.getProperty("interval_logno").trim());

		DataDictInitialize.LAST_SEQUENCE = DataDictInitialize.INIT_SEQUENCE + DataDictInitialize.INTERVAL_SEQUENCE;

		UrlCache.POST_URL_RESOURCE = property.getProperty("post_url_resource").trim();

		UrlCache.POST_URL_LOTTERY = property.getProperty("post_url_lottery").trim();

		UrlCache.POST_URL_NOTE = property.getProperty("post_url_note").trim();

		UrlCache.POST_URL_PUBLIC = property.getProperty("post_url_public").trim();

		UrlCache.POST_URL_ORDER = property.getProperty("post_url_order").trim();

		UrlCache.POST_URL_TRAIN = property.getProperty("post_url_train").trim();

		UrlCache.POST_URL_ACCOUNT = property.getProperty("post_url_acount").trim();

		UDPClient.UDP_HOST = property.getProperty("udp_host").trim();

		UDPClient.UDP_PORT = Integer.valueOf(property.getProperty("udp_port").trim());

		UDPClientLogin.UDP_HOST_LOGIN = property.getProperty("Login_udp_host").trim();

		UDPClientLogin.UDP_PORT_LOGIN = Integer.valueOf(property.getProperty("Login_udp_port").trim());
		
		// System.out.println("classpath:"+this.getClass().getResource("").getPath());
		// setPropertiesString(this.getClass().getResource("").getPath()+"param.properties",
		// "serial_logno", String
		// .valueOf(DataDictInitialize.LAST_SEQUENCE));
		// DataDictInitialize.RESPONSE_TIMEOUT =
		// Long.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","response_timeout"));
		// DataDictInitialize.REQUEST_LIMITED =
		// Integer.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","request_limited"));
		// DataDictInitialize.CONTEXT_URL =
		// getPropertiesString("com/jfpay/preposing/properties/param","contextUrl");
		// DataDictInitialize.LOGIN_TIMEOUT =
		// Long.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","login_timeout"));
		// DataDictInitialize.MEMCACHE_HOST1 =
		// getPropertiesString("com/jfpay/preposing/properties/param","memcache_host1");
		// DataDictInitialize.FAIL_OVER =
		// Boolean.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","failOver"));
		// DataDictInitialize.INIT_CONN =
		// Integer.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","initConn"));
		// DataDictInitialize.MIN_CONN =
		// Integer.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","minConn"));
		// DataDictInitialize.MAX_CONN =
		// Integer.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","maxConn"));
		// DataDictInitialize.MAINT_SLEEP =
		// Long.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","maintSleep"));
		// DataDictInitialize.NAGLE =
		// Boolean.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","nagle"));
		// DataDictInitialize.SOCKET_TO =
		// Integer.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","socketTO"));
		// DataDictInitialize.ALIVE_CHECK =
		// Boolean.valueOf(getPropertiesString("com/jfpay/preposing/properties/param","aliveCheck"));
		// //初始化memcache客户端的socket请求池
		// String[] servers={DataDictInitialize.MEMCACHE_HOST1};
		//		
		// SockIOPool pool = SockIOPool.getInstance();
		//		
		// pool.setServers(servers);
		//		
		// pool.setFailover(DataDictInitialize.FAIL_OVER);
		//		
		// pool.setInitConn(DataDictInitialize.INIT_CONN);
		//		
		// pool.setMinConn(DataDictInitialize.MIN_CONN);
		//		
		// pool.setMaxConn(DataDictInitialize.MAX_CONN);
		//		
		// pool.setMaintSleep(DataDictInitialize.MAINT_SLEEP);
		//		
		// pool.setNagle(DataDictInitialize.NAGLE);
		//		
		// pool.setSocketTO(DataDictInitialize.SOCKET_TO);
		//		
		// pool.setAliveCheck(DataDictInitialize.ALIVE_CHECK);
		//		
		// pool.initialize();

		log.info("开始初始化请求池");
		if (DataDictInitialize.REQUEST_POOL == null) {
			DataDictInitialize.REQUEST_POOL = new ConcurrentHashMap();
		}

		if (DataDictInitialize.LOGIN_INFO_POOL == null) {
			DataDictInitialize.LOGIN_INFO_POOL = new ConcurrentHashMap();
		}

		if (DataDictInitialize.REQUEST_TIME_OUT_CONTROL == null) {
			DataDictInitialize.REQUEST_TIME_OUT_CONTROL = new RequestTimeOutControl();
		}

		if (DataDictInitialize.LOGIN_TIME_OUT_CONTROL == null) {
			DataDictInitialize.LOGIN_TIME_OUT_CONTROL = new LoginTimeOutControl();
		}

		// if(DataDictInitialize.CHECK_POOL_SIZE == null){
		// DataDictInitialize.CHECK_POOL_SIZE = new CheckPoolSize();
		// }
		if (DataDictInitialize.cacheSyn == null) {
			DataDictInitialize.cacheSyn = new CacheSyn();
		}

		log.info("开始启动请求池中请求超时控制线程");
		// 运行处理请求池内请求超时的线程
		DataDictInitialize.REQUEST_TIME_OUT_CONTROL.setDaemon(true);
		DataDictInitialize.REQUEST_TIME_OUT_CONTROL.start();

		log.info("开始启动登录池中用户登录超时控制线程");
		// 运行处理请求池内请求超时的线程
		DataDictInitialize.LOGIN_TIME_OUT_CONTROL.setDaemon(true);
		DataDictInitialize.LOGIN_TIME_OUT_CONTROL.start();

		// log.info("开始启动检测池大小线程");
		// //运行处理请求池内请求超时的线程
		// DataDictInitialize.CHECK_POOL_SIZE.setDaemon(true);
		// DataDictInitialize.CHECK_POOL_SIZE.start();

		// 启动JMS订阅者
		log.info("开始启动JMS TOPIC 订阅者线程:");
		DataDictInitialize.cacheSyn.setDaemon(true);
		DataDictInitialize.cacheSyn.start();
	}

	public static String getPropertiesString(String fileName, String valueName) {
		ResourceBundle resource = ResourceBundle.getBundle(fileName);
		return resource.getString(valueName);
	}

	/**
	 * 获取原配置属性文件的key value列表
	 * 
	 * @return
	 * @throws IOException
	 */
	private void getAllProperties() throws IOException {
		Properties prop = new Properties();// 属性集合对象
		InputStream is = getClass().getResourceAsStream("JfpayPrepoParam.properties");
		prop.load(is);
		toSaveMap = new HashMap();
		Set keys = prop.keySet();
		for (Iterator itr = keys.iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			Object value = prop.get(key);
			toSaveMap.put(key, value);
		}
	}

	/**
	 * 更新配置文件某一属性或者新增
	 * 
	 * @param fileName
	 * @param name
	 * @param value
	 * @throws IOException
	 */
	public static void setPropertiesString(String fileName, String name, String value) throws IOException {
		Properties prop = new Properties();// 属性集合对象
		toSaveMap.put(name, value);
		System.out.println("toSaveMap:" + toSaveMap.toString());
		prop.putAll(toSaveMap);
		FileOutputStream fos = new FileOutputStream(fileName);
		// 将Properties集合保存到流中
		prop.store(fos, "Copyright (c) Boxcode Studio");
		fos.close();// 关闭流
	}

	public static void destory() {
		try {
			log.info("系统正在退出，等待线程停止");
			DataDictInitialize.isRun = false;
			while (!DataDictInitialize.isExit) {
				TimeUnit.SECONDS.sleep(1L);
				DataDictInitialize.REQUEST_TIME_OUT_CONTROL.close();
				DataDictInitialize.LOGIN_TIME_OUT_CONTROL.close();
				// DataDictInitialize.cacheSyn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
