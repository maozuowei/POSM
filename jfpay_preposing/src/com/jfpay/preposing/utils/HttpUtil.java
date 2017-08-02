package com.jfpay.preposing.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

/**
 * 无视Https证书是否正确的Java Http Client
 * 
 * 
 * @author huangxuebin
 * 
 * @create 2012.8.17
 * @version 1.0
 */
public class HttpUtil {

	static Logger log = Logger.getLogger(HttpUtil.class);

	public static String send(String callURL, String postData) throws Exception {

		log.info("call url is:" + callURL);
		log.info("call postData is:" + postData);
		try {
			URL url = new URL(callURL);
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			out.write(postData.getBytes("UTF-8"));
			out.flush();
			out.close();
			int rc = connection.getResponseCode();
			log.info("connect result is:" + rc);
			// 响应成功
			if (rc == 200) {
				String temp;
				InputStream in = null;
				in = connection.getInputStream();
				BufferedReader data = new BufferedReader(new InputStreamReader(in, "utf-8"));
				StringBuffer result = new StringBuffer();
				while ((temp = data.readLine()) != null) {
					result.append(temp);
					temp = null;
				}
				data.close();
				in.close();
				log.info("returnData is:" + result.toString());
				return result.toString();
			}
		} catch (IOException io) {
			log.error(io.toString());
			throw io;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		return null;
	}

	/**
	 * 忽视证书HostName
	 */
	private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
		public boolean verify(String s, SSLSession sslsession) {
			System.out.println("WARNING: Hostname is not matched for cert.");
			return true;
		}
	};

	/**
	 * Ignore Certification
	 */
	private static TrustManager ignoreCertificationTrustManger = new X509TrustManager() {

		private X509Certificate[] certificates;

		public void checkClientTrusted(X509Certificate certificates[], String authType) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = certificates;
				System.out.println("init at checkClientTrusted");
			}
		}

		public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {
			if (this.certificates == null) {
				this.certificates = ax509certificate;
				System.out.println("init at checkServerTrusted");
			}
			// for (int c = 0; c < certificates.length; c++) {
			// X509Certificate cert = certificates[c];
			// System.out.println(" Server certificate " + (c + 1) + ":");
			// System.out.println(" Subject DN: " + cert.getSubjectDN());
			// System.out.println(" Signature Algorithm: "
			// + cert.getSigAlgName());
			// System.out.println(" Valid from: " + cert.getNotBefore());
			// System.out.println(" Valid until: " + cert.getNotAfter());
			// System.out.println(" Issuer: " + cert.getIssuerDN());
			// }

		}

		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public static String getMethod(String urlString, String postData) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
		String result = "";
		try {
			URL url = new URL(urlString);

			/*
			 * use ignore host name verifier
			 */
			HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

			// Prepare SSL Context
			TrustManager[] tm = { ignoreCertificationTrustManger };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			connection.setSSLSocketFactory(ssf);

			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);

			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			out.write(postData.getBytes("UTF-8"));
			out.flush();
			out.close(); // flush and close
			System.out.println("发送完毕~~~~~~~~~~~~~~~~~");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			reader.close();
			System.out.println(connection.getHeaderField(0));
			System.out.println(connection.getHeaderField(1));
			System.out.println(connection.getHeaderField(2));
			System.out.println(connection.getHeaderField(3));
			System.out.println(connection.getHeaderField(4));
			connection.disconnect();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8"); 
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

	// public static void main(String[] args) {
	// String urlString = "https://localhost:5556/EMFS/queryProduct.do";
	// String output = new String(
	// HttpUtil
	// .getMethod(
	// urlString,
	// "roductType=%E9%9E%8B%E5%8C%85%E9%85%8D%E9%A5%B0&osType=iOS6.0&uuid=3C:07:54:43:3E:43&smallType=C2&sign=C138D6A2E82BFCB0FDEC13647AA93D5E&version=100000"));
	// System.out.println(output);
	// }
}