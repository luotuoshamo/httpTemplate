package com.wjh.get;

import com.wjh.util.HttpRes;
import com.wjh.util.MapUtil;
import com.wjh.util.ResponseUtil;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JdkHttpGet implements HttpGet {
    /**
     * 发get请求
     * 标准get请求无request body，不是所有的服务器可以接收get请求的request body
     */
    @Override
    public HttpRes get(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception {
        // 参数
        if (textParamMap != null && !textParamMap.isEmpty()) {
            String textParam = MapUtil.mapToQueryString(textParamMap, true);
            if (urlString.indexOf("?") != -1) {
                urlString += ("&" + textParam);
            } else {
                urlString += ("?" + textParam);
            }
        }

        // 创建http对象
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setRequestMethod("GET");

        // 请求超时时间
        httpURLConnection.setConnectTimeout(6_000_000);// 6 minutes
        httpURLConnection.setReadTimeout(6_000_000);

        // 请求头
        // jdk发请求莫默认携带的请求头：
        // user-agent:Java/1.8.0_211
        // host:127.0.01:8080
        // accept:text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
        // connection:keep - alive
        if (headMap == null) {
            headMap = new HashMap();
        }
        headMap.put("content-type", "text/*");
        Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }
}
