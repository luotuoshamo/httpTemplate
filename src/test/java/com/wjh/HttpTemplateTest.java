package com.wjh;

import com.wjh.util.HttpRes;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpTemplateTest {

    @Test
    public void get() throws Exception {
        HttpTemplate httpTemplate = new HttpTemplate();

        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("p1", "v1");
        textParamMap.put("p2", "v2中文");

        HttpRes httpRes = httpTemplate.get(
                "http://localhost:8080/mockApi/get",
                headMap,
                textParamMap
        );
        System.out.println(httpRes);
    }

    @Test
    public void getTaobaoMobileApi() throws Exception {
        HttpTemplate httpTemplate = new HttpTemplate();

        // 参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("tel", "13585766229");

        HttpRes httpRes = httpTemplate.get(
                "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm",
                null,
                textParamMap
        );
        System.out.println(httpRes);
    }

    @Test
    public void postFormData() throws Exception {
        HttpTemplate httpTemplate = new HttpTemplate();

        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 文本参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("p1", "v1");
        textParamMap.put("p2", "v2中文");

        // 文件参数
        Map<String, File> fileParamMap = new HashMap();
        fileParamMap.put("myFile", new File("d:/tmp/1.jpg"));
        fileParamMap.put("myFile2", new File("d:/tmp/2.jpg"));
        fileParamMap.put("myFile3", new File("d:/tmp/2.jpg"));

        HttpRes httpRes = httpTemplate.postFormData("http://localhost:8080/mockApi/postFormData",
                headMap, textParamMap, fileParamMap);
        System.out.println(httpRes);
    }

    @Test
    public void postUrlencoded() throws Exception {
        HttpTemplate httpTemplate = new HttpTemplate();

        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("p1", "v1");
        textParamMap.put("p2", "v2中文");

        HttpRes httpRes = httpTemplate.postUrlencoded(
                "http://localhost:8080/mockApi/postUrlencoded",
                headMap,
                textParamMap);
        System.out.println(httpRes);
    }

    @Test
    public void postJson() throws Exception {
        HttpTemplate httpTemplate = new HttpTemplate();

        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 参数
        String jsonParam = "{'k1':'v1','k2':'v2中文'}";

        HttpRes httpRes = httpTemplate.postJson(
                "http://localhost:8080/mockApi/postJson",
                headMap,
                jsonParam);
        System.out.println(httpRes);
    }


    @Test
    public void postXml() throws Exception {
        HttpTemplate httpTemplate = new HttpTemplate();

        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 参数
        StringBuffer xmlSb = new StringBuffer();
        xmlSb.append("<xml charSet='UTF-8'>");
        xmlSb.append("<root>");
        xmlSb.append("<name>wjh</name>");
        xmlSb.append("<age>wjh中文</age>");
        xmlSb.append("</root>");

        HttpRes httpRes = httpTemplate.postXml(
                "http://localhost:8080/mockApi/postXml",
                headMap,
                xmlSb.toString());
        System.out.println(httpRes);
    }

    @Test
    public void testGetFile() throws Exception {
        HttpTemplate httpTemplate = new HttpTemplate();
        String urlString = "https://dss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/topnav/yinyue@2x-c18adacacb.png";
        HttpRes httpRes = httpTemplate.get(urlString,
                null, null);
        System.out.println(httpRes);
        byte[] binaryResponseBody = httpRes.getBinaryResponseBody();
        FileOutputStream fos = new FileOutputStream("d:/tmp/1.png");
        fos.write(binaryResponseBody);
        fos.close();
    }
}