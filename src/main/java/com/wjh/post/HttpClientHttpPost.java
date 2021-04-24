package com.wjh.post;

import com.wjh.util.HttpRes;
import com.wjh.util.RequestUtil;
import com.wjh.util.ResponseUtil;
import com.wjh.util.RowType;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.*;

/**
 * httpClient会根据所用API自动加上请求头content-type，所以不要自己手动加content-type
 */
public class HttpClientHttpPost implements HttpPost {
    /**
     * application/form-data
     */
    @Override
    public HttpRes postFormData(String urlString, Map<String, String> headMap, Map<String, String> textParamMap, Map<String, File> binaryParamMap) throws Exception {
        CloseableHttpClient httpClient = RequestUtil.getHttpClient(urlString);
        org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(urlString);
        String mimeType = "application/form-data";
        String charset = RequestUtil.getCharsetFromHeadMap(headMap);

        // 请求头
        if (headMap != null) {
            Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 请求体-文本参数
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (textParamMap != null) {
            Set<Map.Entry<String, String>> textParamEntrySet = textParamMap.entrySet();
            for (Map.Entry<String, String> entry : textParamEntrySet) {
                builder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.create(mimeType, charset)));
            }
        }

        // 请求体-二进制参数
        if (binaryParamMap != null) {
            Set<Map.Entry<String, File>> binaryParamSet = binaryParamMap.entrySet();
            for (Map.Entry<String, File> entry : binaryParamSet) {
                builder.addBinaryBody(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity httpEntity = builder.build();
        httpPost.setEntity(httpEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        return ResponseUtil.packHttpClientHttpRes(response);
    }

    /**
     * application/x-www-form-urlencoded
     */
    @Override
    public HttpRes postFormUrlEncoded(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception {
        CloseableHttpClient httpClient = RequestUtil.getHttpClient(urlString);
        org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(urlString);
        String charset = RequestUtil.getCharsetFromHeadMap(headMap);

        // 请求头
        if (headMap != null) {
            Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }

        // 请求体
        List<NameValuePair> nameValuePairs = new ArrayList();
        if (textParamMap != null) {
            Set<Map.Entry<String, String>> entrySet = textParamMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, charset);
        httpPost.setEntity(urlEncodedFormEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        return ResponseUtil.packHttpClientHttpRes(response);
    }

    @Override
    public HttpRes postRow(RowType rowType, String urlString, Map<String, String> headMap, String textParamString) throws Exception {
        CloseableHttpClient httpClient = RequestUtil.getHttpClient(urlString);
        org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(urlString);
        String charset = RequestUtil.getCharsetFromHeadMap(headMap);
        String mimeType = null;

        switch (rowType) {
            case JSON:
                mimeType = "application/json";
                break;
            case XML:
                mimeType = "application/xml";
                break;
        }

        // 请求头
        if (headMap == null) {
            headMap = new HashMap();
        }
        headMap.put("content-type", mimeType + ";charset=" + charset);
        Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }

        // 请求体
        StringEntity stringEntity = new StringEntity(textParamString, charset);
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        return ResponseUtil.packHttpClientHttpRes(response);
    }
}
