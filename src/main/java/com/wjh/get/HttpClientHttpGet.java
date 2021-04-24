package com.wjh.get;

import com.wjh.util.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;
import java.util.Set;

public class HttpClientHttpGet implements HttpGet {
    @Override
    public HttpRes get(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception {
        CloseableHttpClient httpClient = RequestUtil.getHttpClient(urlString);

        String charset = MapUtil.getCharsetFromMap(headMap);
        if (charset == null || charset.trim().isEmpty()) {
            charset = Constant.CHARSET_UTF8;
        }

        // 参数(请求体)
        String queryString = MapUtil.mapToQueryString(textParamMap, charset, true);
        if (urlString.contains("?")) {
            urlString += ("&" + queryString);
        } else {
            urlString += ("?" + queryString);
        }
        org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client.methods.HttpGet(urlString);

        // 请求头
        if (headMap != null) {
            Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpRes httpRes = ResponseUtil.packHttpClientHttpRes(response);
        return httpRes;
    }
}
