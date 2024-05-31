package cn.topicstudy.jhttp.util;

import cn.topicstudy.jhttp.entity.HttpRes;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        httpRes.setResponseMessage(httpURLConnection.getResponseMessage());

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
        String responseCharset = HeadUtil.getCharsetFromHeadMap(responseHeadMap, Constant.DEFAULT_CHARSET);
        // 错误信息
        InputStream errIs = httpURLConnection.getErrorStream();
        if (errIs != null) {
            String errMsg = IOUtil.inputStreamToString(errIs, responseCharset);
            httpRes.setTextErrorResponseBody(errMsg);
            return httpRes;
        }
        // 正常数据
        InputStream is = httpURLConnection.getInputStream();
        String str = inputStream2Str(is);
        if (str != null) {
            httpRes.setBinaryResponseBody(str.getBytes(responseCharset));
            httpRes.setTextResponseBody(str);
        }

        return httpRes;
    }

    private static String inputStream2Str(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String result = sb.toString();
        return result;
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
        httpRes.setResponseMessage(responseStatusLine.getReasonPhrase());

        // 响应头
        Map<String, String> responseHeadMap = new HashMap();
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            responseHeadMap.put(header.getName(), header.getValue());
        }
        httpRes.setResponseHeadMap(responseHeadMap);

        // 响应体
        String charset = HeadUtil.getCharsetFromHeadMap(responseHeadMap, Constant.DEFAULT_CHARSET);
        HttpEntity entity = response.getEntity();
        byte[] bytes = EntityUtils.toByteArray(entity);
        httpRes.setBinaryResponseBody(bytes);
        httpRes.setTextResponseBody(new String(bytes, charset));

        return httpRes;
    }
}
