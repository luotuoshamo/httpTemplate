package com.wjh.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResponseUtil {
    private ResponseUtil() {
    }

    /**
     * 包装用jdk发送得http请求的响应结果
     */
    public static HttpRes packJdkHttpRes(HttpURLConnection httpURLConnection) throws Exception {
        HttpRes httpRes = new HttpRes();
        // 响应码
        int responseCode = httpURLConnection.getResponseCode();
        httpRes.setResponseCode(responseCode + "");

        // 响应码对应的解释
        String responseMessage = httpURLConnection.getResponseMessage();
        httpRes.setResponseMessage(responseMessage);

        // 响应头
        HashMap responseHeadMap = new HashMap();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields != null) {
            for (String headerField : headerFields.keySet()) {
                responseHeadMap.put(headerField, httpURLConnection.getHeaderField(headerField));
            }
            httpRes.setResponseHeadMap(responseHeadMap);
        }

        // 响应体
        InputStream is = httpURLConnection.getInputStream();
        String responseCharset = ResponseUtil.getCharsetFromMap(responseHeadMap);
        httpRes.setData(IOUtil.inputStreamToString(is, responseCharset));
        return httpRes;
    }

    /**
     * application/javascript;charset=GBK
     */
    public static String getCharsetFromMap(Map<String, String> map) {
        if (map == null) return null;
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if ("Content-Type".equalsIgnoreCase(key)) {
                String value = map.get(key);
                int index = value.indexOf("charset");
                if (index != -1) return value.split("=")[1];
            }
        }
        return null;
    }
}
