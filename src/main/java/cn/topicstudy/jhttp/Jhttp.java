package cn.topicstudy.jhttp;

import cn.topicstudy.jhttp.common.JhttpErrorCodeEnum;
import cn.topicstudy.jhttp.entity.HttpRes;
import cn.topicstudy.jhttp.enums.HttpSendWayEnum;
import cn.topicstudy.jhttp.enums.RowType;
import cn.topicstudy.jhttp.get.HttpGet;
import cn.topicstudy.jhttp.get.impl.HttpClientHttpGetImpl;
import cn.topicstudy.jhttp.get.impl.JdkHttpGetImpl;
import cn.topicstudy.jhttp.post.HttpPost;
import cn.topicstudy.jhttp.post.impl.HttpClientHttpPostImpl;
import cn.topicstudy.jhttp.post.impl.JdkHttpPostImpl;
import cn.topicstudy.jutil.basic.error.CommonAssertUtil;
import com.alibaba.fastjson2.JSON;

import java.io.File;
import java.util.Map;

/**
 * 入口
 */
public class Jhttp {
    private HttpSendWayEnum implWay;
    private HttpGet httpGet;
    private HttpPost httpPost;

    public Jhttp() {
        this(HttpSendWayEnum.JDK);
    }

    public Jhttp(HttpSendWayEnum implWay) {
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

    /**
     * @param urlString
     * @param headerMap
     * @param textParamMap 自动追加到URL
     * @return
     * @throws Exception
     */
    public HttpRes get(String urlString,
                       Map<String, String> headerMap,
                       Map<String, String> textParamMap) {
        try {
            HttpRes httpRes = httpGet.get(urlString, headerMap, textParamMap);
            checkHttpRes(httpRes);
            return httpRes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getForObject(String urlString,
                              Map<String, String> headerMap,
                              Map<String, String> textParamMap,
                              Class<T> cls) {
        HttpRes httpRes = get(urlString, headerMap, textParamMap);
        return JSON.parseObject(httpRes.getTextResponseBody(), cls);
    }

    public HttpRes postFormData(String urlString,
                                Map<String, String> headerMap,
                                Map<String, String> textParamMap,
                                Map<String, File> fileParamMap) {

        try {
            HttpRes httpRes = httpPost.postFormData(urlString, headerMap, textParamMap, fileParamMap);
            checkHttpRes(httpRes);
            return httpRes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T postFormDataForObject(String urlString,
                                       Map<String, String> headerMap,
                                       Map<String, String> textParamMap,
                                       Map<String, File> fileParamMap,
                                       Class<T> cls) {
        HttpRes httpRes = postFormData(urlString, headerMap, textParamMap, fileParamMap);
        return JSON.parseObject(httpRes.getTextResponseBody(), cls);
    }

    public HttpRes postFormUrlEncoded(String urlString,
                                      Map<String, String> headMap,
                                      Map<String, String> textParamMap) {
        try {
            HttpRes httpRes = httpPost.postFormUrlEncoded(urlString, headMap, textParamMap);
            checkHttpRes(httpRes);
            return httpRes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T postFormUrlEncodedForObject(String urlString,
                                             Map<String, String> headMap,
                                             Map<String, String> textParamMap,
                                             Class<T> cls) {
        HttpRes httpRes = postFormUrlEncoded(urlString, headMap, textParamMap);
        return JSON.parseObject(httpRes.getTextResponseBody(), cls);
    }

    public HttpRes postJson(String urlString, Map<String, String> headMap, String jsonParam) {
        try {
            HttpRes httpRes = httpPost.postRow(RowType.JSON, urlString, headMap, jsonParam);
            checkHttpRes(httpRes);
            return httpRes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T postJsonForObject(String urlString, Map<String, String> headMap, String jsonParam, Class<T> cls) {
        HttpRes httpRes = postJson(urlString, headMap, jsonParam);
        return JSON.parseObject(httpRes.getTextResponseBody(), cls);
    }

    public HttpRes postXml(String urlString,
                           Map<String, String> headMap,
                           String xmlParam) {
        try {
            HttpRes httpRes = httpPost.postRow(RowType.XML, urlString, headMap, xmlParam);
            checkHttpRes(httpRes);
            return httpRes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T postXmlForObject(String urlString,
                                  Map<String, String> headMap,
                                  String xmlParam,
                                  Class<T> cls) {
        HttpRes httpRes = postXml(urlString, headMap, xmlParam);
        return JSON.parseObject(httpRes.getTextResponseBody(), cls);
    }

    public HttpRes postRow(String urlString,
                           Map<String, String> headMap,
                           String rowText, RowType rowType) {
        try {
            HttpRes httpRes = httpPost.postRow(rowType, urlString, headMap, rowText);
            checkHttpRes(httpRes);
            return httpRes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T postRowForObject(String urlString,
                                  Map<String, String> headMap,
                                  String rowText, RowType rowType,
                                  Class<T> cls) {
        HttpRes httpRes = postRow(urlString, headMap, rowText, rowType);
        return JSON.parseObject(httpRes.getTextResponseBody(), cls);
    }

    /**
     * @param res must be not null.
     */
    private void checkHttpRes(HttpRes res) {
        CommonAssertUtil.throwException(!"200".equals(res.getResponseCode()), JhttpErrorCodeEnum.STATUS_CODE_NOT_200,
                res.getResponseCode(), res.getResponseMessage());

    }
}
