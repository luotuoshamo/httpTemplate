package com.wjh.post;

import com.wjh.util.*;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JdkHttpPost implements HttpPost {
    @Override
    public HttpRes postFormData(String urlString, Map<String, String> headMap,
                                Map<String, String> textParamMap,
                                Map<String, File> fileParamMap) throws Exception {
        final String BOUNDARY = new Date().getTime() + "wjhHttpTemplate";
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        // 请求超时时间
        httpURLConnection.setConnectTimeout(6_000_000);// 6 minutes
        httpURLConnection.setReadTimeout(6_000_000);

        // 请求头
        // jdk发请求莫默认携带的请求头：
        // user-agent:Java/1.8.0_211
        // host:127.0.01:8080
        // accept:text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
        // connection:keep - alive
        if (headMap == null) {
            headMap = new HashMap();
        }
        headMap.put("content-type", "multipart/form-data;boundary=" + BOUNDARY);
        if (headMap != null) {
            Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // 请求体
        OutputStream os = httpURLConnection.getOutputStream();// 必需放在设置请求头之后，否则报错： Already connected
        // 文本参数
        if (textParamMap != null) {
            Set<Map.Entry<String, String>> entrySet = textParamMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String k = entry.getKey();
                String v = entry.getValue();
                StringBuffer sb = new StringBuffer();
                sb.append("--" + BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + k + "\"");
                sb.append("\r\n\r\n");
                sb.append(v);
                sb.append("\r\n");
                os.write(sb.toString().getBytes());
            }
        }

        // 文件参数
        if (fileParamMap != null) {
            Set<Map.Entry<String, File>> entrySet = fileParamMap.entrySet();
            for (Map.Entry<String, File> entry : entrySet) {
                String k = entry.getKey();
                File v = entry.getValue();
                String fileName = v.getName();
                StringBuffer sb = new StringBuffer();
                sb.append("--" + BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition:form-data; name=" + k + "; filename=" + fileName);
                sb.append("\r\n\r\n");// 这里必需是2个\r\n
                os.write(sb.toString().getBytes());
                os.write(sb.toString().getBytes());// 为什么这句代码要写2遍，jdk的bug?
                os.write(IOUtil.fileToBytes(v));
            }

        }
        byte[] end = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
        os.write(end);

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }

    @Override
    public HttpRes postRow(RowType rowType, String urlString, Map<String, String> headMap,
                           String jsonParam) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        // 请求超时时间
        httpURLConnection.setConnectTimeout(6_000_000);// 6 minutes
        httpURLConnection.setReadTimeout(6_000_000);

        // 请求头
        // jdk发请求莫默认携带的请求头：
        // user-agent:Java/1.8.0_211
        // host:127.0.01:8080
        // accept:text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
        // connection:keep - alive
        if (headMap == null) {
            headMap = new HashMap();
        }
        switch (rowType) {
            case JSON:
                headMap.put("content-type", "application/json");
                break;
            case XML:
                headMap.put("content-type", "application/xml");
                break;
        }
        if (headMap != null) {
            Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // row参数
        OutputStream os = httpURLConnection.getOutputStream();
        os.write(jsonParam.getBytes(StandardCharsets.UTF_8));

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }

    @Override
    public HttpRes postUrlencoded(String urlString, Map<String, String> headMap,
                                  Map<String, String> textParamMap) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");

        // 请求超时时间
        httpURLConnection.setConnectTimeout(6_000_000);// 6 minutes
        httpURLConnection.setReadTimeout(6_000_000);

        // 请求头
        // jdk发请求莫默认携带的请求头：
        // user-agent:Java/1.8.0_211
        // host:127.0.01:8080
        // accept:text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2
        // connection:keep - alive
        if (headMap == null) {
            headMap = new HashMap();
        }
        headMap.put("content-type", "application/x-www-form-urlencoded");
        if (headMap != null) {
            Set<Map.Entry<String, String>> entrySet = headMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        // 请求体
        httpURLConnection.setDoOutput(true);
        OutputStream os = httpURLConnection.getOutputStream();
        // 参数
        if (textParamMap != null && !textParamMap.isEmpty()) {
            String textParam = MapUtil.mapToQueryString(textParamMap);
            os.write(textParam.getBytes(StandardCharsets.UTF_8));
        }

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }
}
