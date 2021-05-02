package com.wjh.post.impl;

import com.wjh.enm.RowType;
import com.wjh.entity.HttpRes;
import com.wjh.post.HttpPost;
import com.wjh.util.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * httpClient会根据所用API自动加上请求头content-type，
 * 所以不要自己手动加content-type，
 * 但为了防止传入的content-type影响httpClient设置content-type，
 * 需在获取到charset将headMap中的content-type删除
 */
public class HttpClientHttpPostImpl implements HttpPost {
    @Override
    public HttpRes postFormData(String urlString,
                                Map<String, String> headMap,
                                Map<String, String> textParamMap,
                                Map<String, File> binaryParamMap) throws Exception {
        CloseableHttpClient httpClient = RequestUtil.getHttpClient(urlString);
        org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(urlString);
        String mimeType = "application/form-data";
        String charset = HeadUtil.getCharsetFromHeadMap(headMap, "UTF-8");

        // 请求头
        MapUtil.removeIgnoreCase(headMap, "content-type");// 防止传入的content-type影响httpClient设置content-type
        HeadUtil.setHttpClientRequestHeader(httpPost, headMap, null);

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

    @Override
    public HttpRes postFormUrlEncoded(String urlString,
                                      Map<String, String> headMap,
                                      Map<String, String> textParamMap) throws Exception {
        CloseableHttpClient httpClient = RequestUtil.getHttpClient(urlString);
        org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(urlString);
        String charset = HeadUtil.getCharsetFromHeadMap(headMap, Constant.DEFAULT_CHARSET);

        // 请求头
        MapUtil.removeIgnoreCase(headMap, "content-type");// 防止传入的content-type影响httpClient设置content-type
        HeadUtil.setHttpClientRequestHeader(httpPost, headMap, null);

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
    public HttpRes postRow(RowType rowType,
                           String urlString,
                           Map<String, String> headMap,
                           String textParamString) throws Exception {
        CloseableHttpClient httpClient = RequestUtil.getHttpClient(urlString);
        org.apache.http.client.methods.HttpPost httpPost = new org.apache.http.client.methods.HttpPost(urlString);
        String mimeType = null;
        String charset = HeadUtil.getCharsetFromHeadMap(headMap, Constant.DEFAULT_CHARSET);

        // 请求头
        switch (rowType) {
            case JSON:
                mimeType = "application/json";
                break;
            case XML:
                mimeType = "application/xml";
                break;
        }
        String contentType = mimeType + ";charset=" + charset;
        MapUtil.removeIgnoreCase(headMap, "content-type");// 防止传入的content-type影响httpClient设置content-type
        HeadUtil.setHttpClientRequestHeader(httpPost, headMap, contentType);

        // 请求体
        StringEntity stringEntity = new StringEntity(textParamString, charset);
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        return ResponseUtil.packHttpClientHttpRes(response);
    }
}
