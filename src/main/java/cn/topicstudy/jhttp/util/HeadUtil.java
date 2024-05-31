package cn.topicstudy.jhttp.util;

import org.apache.http.client.methods.HttpRequestBase;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class HeadUtil {
    /**
     * set header when send http by jdk
     * @param httpURLConnection 1
     * @param headMap 1
     * @param contentType 1
     */
    public static void setJdkRequestHeader(HttpURLConnection httpURLConnection, Map<String, String> headMap, String contentType) {
        // jdk发请求莫默认携带的请求头：
        // user-agent:Java/1.8.0_211
        // host:127.0.01:8080
        // accept:text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
        // connection:keep - alive
        if (httpURLConnection == null) return;
        if (contentType != null && !contentType.trim().isEmpty()) {
            if (headMap == null) headMap = new HashMap();
            // 防止由于大小写的原因，没有覆盖原来的content-type
            MapUtil.removeIgnoreCase(headMap, "content-type");
            headMap.put("content-type", contentType);
        }
        if (headMap == null || headMap.isEmpty()) return;

        Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 1
     * @param httpRequestBase 1
     * @param headMap 1
     * @param contentType 1
     */
    public static void setHttpClientRequestHeader(HttpRequestBase httpRequestBase, Map<String, String> headMap, String contentType) {
        if (httpRequestBase == null) return;
        if (contentType != null && !contentType.trim().isEmpty()) {
            if (headMap == null) headMap = new HashMap();
            // 防止由于大小写的原因，没有覆盖原来的content-type
            MapUtil.removeIgnoreCase(headMap, "content-type");
            headMap.put("content-type", contentType);
        }
        if (headMap == null || headMap.isEmpty()) return;

        Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            httpRequestBase.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     *  extract charset from content-type
     *       content-type=application/javascript;charset=GBK
     * @param map 1
     * @param defaultCharset 1
     * @return 1
     */
    public static String getCharsetFromHeadMap(Map<String, String> map, String defaultCharset) {
        if (map == null) return defaultCharset;
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if ("Content-Type".equalsIgnoreCase(key)) {
                String value = map.get(key);
                int index = value.indexOf("charset");
                if (index != -1) return value.split("=")[1];
            }
        }
        return defaultCharset;
    }
}
