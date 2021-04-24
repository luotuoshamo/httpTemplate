package com.wjh;

import com.wjh.util.HttpRes;
import com.wjh.util.ImplWayEnum;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpTemplateTest {
    private HttpTemplate httpTemplate = new HttpTemplate(ImplWayEnum.HTTP_CLIENT);

    @Test
    public void get() throws Exception {
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
    public void postFormData() throws Exception {
        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 文本参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("p1", "v1");
        textParamMap.put("p2", "v2中文");

        // 文件参数
        Map<String, File> binaryParamMap = new HashMap();
        binaryParamMap.put("myFile", new File("d:/tmp/1.jpg"));
        binaryParamMap.put("myFile2", new File("d:/tmp/2.jpg"));
        binaryParamMap.put("myFile3", new File("d:/tmp/2.jpg"));

        HttpRes httpRes = httpTemplate.postFormData("http://localhost:8080/mockApi/postFormData",
                headMap, textParamMap, binaryParamMap);
        System.out.println(httpRes);
    }

    @Test
    public void postUrlencoded() throws Exception {
        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("p1", "v1");
        textParamMap.put("p2", "v2中文");

        HttpRes httpRes = httpTemplate.postFormUrlEncoded(
                "http://localhost:8080/mockApi/postUrlencoded",
                headMap,
                textParamMap);
        System.out.println(httpRes);
    }

    @Test
    public void postJson() throws Exception {
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
        // 请求头
        Map<String, String> headMap = new HashMap();
        headMap.put("h1", "v1");
        headMap.put("h2", "v2");

        // 参数
        StringBuffer xmlSb = new StringBuffer();
        xmlSb.append("<xml charSet='UTF-8'>");
        xmlSb.append("<root>");
        xmlSb.append("<name>wjh汉字</name>");
        xmlSb.append("<age>10</age>");
        xmlSb.append("</root>");

        HttpRes httpRes = httpTemplate.postXml(
                "http://localhost:8080/mockApi/postXml",
                headMap,
                xmlSb.toString());
        System.out.println(httpRes);
    }

    @Test
    public void testGetFile() throws Exception {
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