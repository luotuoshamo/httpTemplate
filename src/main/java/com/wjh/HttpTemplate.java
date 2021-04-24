package com.wjh;

import com.wjh.get.HttpClientHttpGet;
import com.wjh.get.HttpGet;
import com.wjh.get.JdkHttpGet;
import com.wjh.post.HttpClientHttpPost;
import com.wjh.post.HttpPost;
import com.wjh.post.JdkHttpPost;
import com.wjh.util.HttpRes;
import com.wjh.util.ImplWayEnum;
import com.wjh.util.RowType;

import java.io.File;
import java.util.Map;

/**
 * 入口
 */
public class HttpTemplate {
    private ImplWayEnum implWay; // 底层实现
    private HttpGet httpGet;
    private HttpPost httpPost;

    public HttpTemplate() {
        this(ImplWayEnum.JDK);
    }

    public HttpTemplate(ImplWayEnum implWay) {
        this.implWay = implWay;

        switch (this.implWay) {
            case JDK:
                this.httpGet = new JdkHttpGet();
                this.httpPost = new JdkHttpPost();
                break;
            case HTTP_CLIENT:
                this.httpGet = new HttpClientHttpGet();
                this.httpPost = new HttpClientHttpPost();
                break;
            default:
                this.httpGet = new JdkHttpGet();
                this.httpPost = new JdkHttpPost();
        }
    }


    public HttpRes get(String urlString, Map<String, String> headerMap, Map<String, String> textParamMap) throws Exception {
        return httpGet.get(urlString, headerMap, textParamMap);
    }

    public HttpRes postFormData(String urlString, Map<String, String> headerMap, Map<String, String> textParamMap, Map<String, File> fileParamMap) throws Exception {
        return httpPost.postFormData(urlString, headerMap, textParamMap, fileParamMap);
    }

    public HttpRes postFormUrlEncoded(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception {
        return httpPost.postFormUrlEncoded(urlString, headMap, textParamMap);
    }

    public HttpRes postJson(String urlString, Map<String, String> headMap, String jsonParam) throws Exception {
        return httpPost.postRow(RowType.JSON, urlString, headMap, jsonParam);
    }

    public HttpRes postXml(String urlString, Map<String, String> headMap, String xmlParam) throws Exception {
        return httpPost.postRow(RowType.XML, urlString, headMap, xmlParam);
    }


}
