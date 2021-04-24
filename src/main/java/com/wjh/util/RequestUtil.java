package com.wjh.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.util.Map;

public class RequestUtil {
    public static CloseableHttpClient getHttpClient(boolean unsafeHttps) throws Exception {
        return unsafeHttps ? httpClientForUnsafeHttps() : HttpClients.createDefault();
    }

    public static CloseableHttpClient getHttpClient(String url) throws Exception {
        return getHttpClient(isHttpsUrl(url));
    }

    /**
     * 创建用于请求不安全https的httpClent
     */
    public static CloseableHttpClient httpClientForUnsafeHttps() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        // 实现一个X509TrustManager接口，用于绕过安全验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[]{trustManager}, null);

        // 实现验证主机
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // HTTPClient方式发起HTTPS调用
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }

    /**
     * 判断url是否是https协议
     */
    public static boolean isHttpsUrl(String url) {
        if (url == null || url.trim().isEmpty()) return false;
        return url.toLowerCase().startsWith("https");
    }

    /**
     * 从请求头/响应头中获取charset
     */
    public static String getCharsetFromHeadMap(Map<String, String> headMap) {
        String charset = MapUtil.getCharsetFromMap(headMap);
        if (charset == null || charset.trim().isEmpty()) {
            charset = Constant.CHARSET_UTF8;
        }
        return charset;
    }
}
