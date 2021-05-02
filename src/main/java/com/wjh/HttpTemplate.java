package com.wjh;

import com.wjh.enm.ImplWayEnum;
import com.wjh.enm.RowType;
import com.wjh.entity.HttpRes;
import com.wjh.get.HttpGet;
import com.wjh.get.impl.HttpClientHttpGetImpl;
import com.wjh.get.impl.JdkHttpGetImpl;
import com.wjh.post.HttpPost;
import com.wjh.post.impl.HttpClientHttpPostImpl;
import com.wjh.post.impl.JdkHttpPostImpl;

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
                this.httpGet = new JdkHttpGetImpl();
                this.httpPost = new JdkHttpPostImpl();
                break;
            case HTTP_CLIENT:
                this.httpGet = new HttpClientHttpGetImpl();
                this.httpPost = new HttpClientHttpPostImpl();
                break;
        }
    }

    public HttpRes get(String urlString,
                       Map<String, String> headerMap,
                       Map<String, String> textParamMap) throws Exception {
        return httpGet.get(urlString, headerMap, textParamMap);
    }

    public HttpRes postFormData(String urlString,
                                Map<String, String> headerMap,
                                Map<String, String> textParamMap,
                                Map<String, File> fileParamMap) throws Exception {
        return httpPost.postFormData(urlString, headerMap, textParamMap, fileParamMap);
    }

    public HttpRes postFormUrlEncoded(String urlString,
                                      Map<String, String> headMap,
                                      Map<String, String> textParamMap) throws Exception {
        return httpPost.postFormUrlEncoded(urlString, headMap, textParamMap);
    }

    public HttpRes postJson(String urlString, Map<String, String> headMap, String jsonParam) throws Exception {
        return httpPost.postRow(RowType.JSON, urlString, headMap, jsonParam);
    }

    public HttpRes postXml(String urlString,
                           Map<String, String> headMap,
                           String xmlParam) throws Exception {
        return httpPost.postRow(RowType.XML, urlString, headMap, xmlParam);
    }
}
