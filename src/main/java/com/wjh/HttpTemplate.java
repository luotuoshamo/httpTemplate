package com.wjh;

import com.wjh.get.HttpGet;
import com.wjh.get.JdkHttpGet;
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

        if (this.implWay == ImplWayEnum.JDK) {
            this.httpGet = new JdkHttpGet();
            this.httpPost = new JdkHttpPost();
        }//else if...
    }


    public HttpRes get(String urlString, Map<String, String> headerMap, Map<String, String> textParamMap) throws Exception {
        return httpGet.get(urlString, headerMap, textParamMap);
    }

    public HttpRes postFormData(String urlString, Map<String, String> headerMap, Map<String, String> textParamMap, Map<String, File> fileParamMap) throws Exception {
        return httpPost.postFormData(urlString, headerMap, textParamMap, fileParamMap);
    }

    public HttpRes postUrlencoded(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception {
        return httpPost.postUrlencoded(urlString, headMap, textParamMap);
    }

    public HttpRes postJson(String urlString, Map<String, String> headMap, String jsonParam) throws Exception {
        return httpPost.postRow(RowType.JSON, urlString, headMap, jsonParam);
    }

    public HttpRes postXml(String urlString, Map<String, String> headMap, String xmlParam) throws Exception {
        return httpPost.postRow(RowType.XML, urlString, headMap, xmlParam);
    }


}
