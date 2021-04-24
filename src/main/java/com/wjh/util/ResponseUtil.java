package com.wjh.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseUtil {
    private ResponseUtil() {
    }

    /**
     * 包装用jdk发http请求的响应
     */
    public static HttpRes packJdkHttpRes(HttpURLConnection httpURLConnection) throws Exception {
        HttpRes httpRes = new HttpRes();
        // 响应码
        int responseCode = httpURLConnection.getResponseCode();
        httpRes.setResponseCode(responseCode + "");

        // 响应头
        HashMap responseHeadMap = new HashMap();
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        if (headerFields != null) {
            for (String headerField : headerFields.keySet()) {
                responseHeadMap.put(headerField, httpURLConnection.getHeaderField(headerField));
            }
            httpRes.setResponseHeadMap(responseHeadMap);
        }

        // 响应体（无论是文本还是二进制，在HttpRes以文本和二进制各保存一次）
        String responseCharset = MapUtil.getCharsetFromMap(responseHeadMap);
        if (responseCharset == null || responseCharset.trim().isEmpty()) {
            responseCharset = Constant.CHARSET_UTF8;
        }
        // 错误信息
        InputStream errIs = httpURLConnection.getErrorStream();
        if (errIs != null) {
            String errMsg = IOUtil.inputStreamToString(errIs, responseCharset);
            httpRes.setTextErrorResponseBody(errMsg);
            return httpRes;
        }
        // 正常数据
        InputStream is = httpURLConnection.getInputStream();
        byte[] bytes = IOUtil.inputStreamToBytes(is);
        httpRes.setBinaryResponseBody(bytes);
        httpRes.setTextResponseBody(new String(bytes, responseCharset));

        return httpRes;
    }

    /**
     * 包装用httpClient发http请求的响应
     */
    public static HttpRes packHttpClientHttpRes(CloseableHttpResponse response) throws Exception {
        HttpRes httpRes = new HttpRes();
        // 响应行
        StatusLine responseStatusLine = response.getStatusLine();
        int statusCode = responseStatusLine.getStatusCode();
        httpRes.setResponseCode(statusCode + "");

        // 响应头
        Map<String, String> responseHeadMap = new HashMap();
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            responseHeadMap.put(header.getName(), header.getValue());
        }
        httpRes.setResponseHeadMap(responseHeadMap);

        String charset = MapUtil.getCharsetFromMap(responseHeadMap);
        if (charset == null || charset.trim().isEmpty()) {
            charset = Constant.CHARSET_UTF8;
        }

        // 响应体
        HttpEntity entity = response.getEntity();
        byte[] bytes = EntityUtils.toByteArray(entity);
        httpRes.setBinaryResponseBody(bytes);
        httpRes.setTextResponseBody(new String(bytes, charset));

        return httpRes;
    }

}
