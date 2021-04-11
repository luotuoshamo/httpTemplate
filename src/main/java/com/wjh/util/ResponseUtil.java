package com.wjh.util;

import java.io.InputStream;
import java.net.HttpURLConnection;

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

        // 响应信息（响应码对应的解释）
        String responseMessage = httpURLConnection.getResponseMessage();
        httpRes.setResponseMessage(responseMessage);

        // 响应体
        InputStream is = httpURLConnection.getInputStream();
        httpRes.setData(IOUtil.inputStreamToString(is));
        return httpRes;
    }
}
