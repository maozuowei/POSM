package com.jfpay.preposing.utils;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


   /**
     * 无视Https证书是否正确的Java Http Client
     * 
     * 
     * @author huangxuebin
     *
     * @create 2012.8.17
     * @version 1.0
     */
public class HttpsUtil {


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

        public void checkClientTrusted(X509Certificate certificates[],
                String authType) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
                System.out.println("init at checkClientTrusted");
            }
        }


        public void checkServerTrusted(X509Certificate[] ax509certificate,
                String s) throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
                System.out.println("init at checkServerTrusted");
            }
//            for (int c = 0; c < certificates.length; c++) {
//                X509Certificate cert = certificates[c];
//                System.out.println(" Server certificate " + (c + 1) + ":");
//                System.out.println("  Subject DN: " + cert.getSubjectDN());
//                System.out.println("  Signature Algorithm: "
//                        + cert.getSigAlgName());
//                System.out.println("  Valid from: " + cert.getNotBefore());
//                System.out.println("  Valid until: " + cert.getNotAfter());
//                System.out.println("  Issuer: " + cert.getIssuerDN());
//            }

        }


        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    };


    public static String getMethod(String urlString,String postData) {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream(512);
        String result = "";
        try {
            URL url = new URL(null,urlString,new sun.net.www.protocol.https.Handler());
            
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
            
            connection.setRequestProperty("Content-Type",
            "application/x-www-form-urlencoded");

            connection.connect();
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            
            out.write(postData.getBytes("UTF-8")); 
            out.flush();
            out.close(); // flush and close
            System.out.println("发送完毕~~~~~~~~~~~~~~~~~");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
    		while ((line = reader.readLine()) != null) {
    			result += line;
    		}
            reader.close();
            System.out.println( connection.getHeaderField(0));
            System.out.println( connection.getHeaderField(1));
            System.out.println( connection.getHeaderField(2));
            System.out.println( connection.getHeaderField(3));
            System.out.println( connection.getHeaderField(4));
            connection.disconnect();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        String urlString = "https://localhost:5556/EMFS/queryProduct.do";
        String output = new String(HttpsUtil.getMethod(urlString,"roductType=%E9%9E%8B%E5%8C%85%E9%85%8D%E9%A5%B0&osType=iOS6.0&uuid=3C:07:54:43:3E:43&smallType=C2&sign=C138D6A2E82BFCB0FDEC13647AA93D5E&version=100000"));
        System.out.println(output);
    }
}