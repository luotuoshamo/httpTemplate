package cn.topicstudy.jhttp.post.impl;

import cn.topicstudy.jhttp.entity.HttpRes;
import cn.topicstudy.jhttp.enums.RowType;
import cn.topicstudy.jhttp.post.HttpPost;
import cn.topicstudy.jhttp.util.*;
import cn.topicstudy.jutil.basic.text.StringUtil;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Struct;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class JdkHttpPostImpl implements HttpPost {
    @Override
    public HttpRes postFormData(String urlString,
                                Map<String, String> headMap,
                                Map<String, String> textParamMap,
                                Map<String, File> binaryParamMap) throws Exception {
        final String BOUNDARY = new Date().getTime() + "wjhHttpTemplate";
        URL url = new URL(urlString);
        String mimeType = "multipart/form-data";
        String charset = HeadUtil.getCharsetFromHeadMap(headMap, Constant.DEFAULT_CHARSET);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        // 请求头
        String contentType = mimeType + ";charset=" + charset + ";boundary=" + BOUNDARY;
        HeadUtil.setJdkRequestHeader(httpURLConnection, headMap, contentType);

        // 必需放在设置请求头之后，否则报错： Already connected
        OutputStream os = httpURLConnection.getOutputStream();

        // 请求体-文本参数
        if (textParamMap != null) {
            Set<Map.Entry<String, String>> textParamEntrySet = textParamMap.entrySet();
            for (Map.Entry<String, String> entry : textParamEntrySet) {
                String k = entry.getKey();
                String v = entry.getValue();
                StringBuffer sb = new StringBuffer();
                sb.append("--" + BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data; name=\"" + k + "\"");
                sb.append("\r\n\r\n");
                sb.append(v);
                sb.append("\r\n");
                os.write(sb.toString().getBytes(charset));
            }
        }

        // 请求体-二进制参数
        if (binaryParamMap != null) {
            Set<Map.Entry<String, File>> binaryParamEntrySet = binaryParamMap.entrySet();
            for (Map.Entry<String, File> entry : binaryParamEntrySet) {
                String k = entry.getKey();
                File v = entry.getValue();
                String fileName = v.getName();
                StringBuffer sb = new StringBuffer();
                sb.append("--" + BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition:form-data; name=" + k + "; filename=" + fileName);
                sb.append("\r\n\r\n");// 这里必需是2个\r\n
                os.write(sb.toString().getBytes(charset));
                os.write(sb.toString().getBytes(charset));// 为什么这句代码要写2遍，jdk的bug?
                os.write(IOUtil.fileToBytes(v));
            }

        }
        byte[] end = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(charset);
        os.write(end);

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }

    @Override
    public HttpRes postFormUrlEncoded(String urlString,
                                      Map<String, String> headMap,
                                      Map<String, String> textParamMap) throws Exception {
        URL url = new URL(urlString);
        String mimeType = "application/x-www-form-urlencoded";
        String charset = HeadUtil.getCharsetFromHeadMap(headMap, Constant.DEFAULT_CHARSET);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        String contentType = mimeType + ";charset=" + charset;
        HeadUtil.setJdkRequestHeader(httpURLConnection, headMap, contentType);

        // 必需放在设置请求头之后，否则报错： Already connected
        OutputStream os = httpURLConnection.getOutputStream();

        // 请求体
        if (textParamMap != null) {
            String textParam = MapUtil.mapToQueryString(textParamMap, charset, true);
            os.write(textParam.getBytes(charset));
        }

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }


    @Override
    public HttpRes postRow(RowType rowType,
                           String urlString,
                           Map<String, String> headMap,
                           String textParamString) throws Exception {
        URL url = new URL(urlString);
        String mimeType;
        String charset = HeadUtil.getCharsetFromHeadMap(headMap, Constant.DEFAULT_CHARSET);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);

        // 请求头
        switch (rowType) {
            case JSON:
                mimeType = "application/json";
                break;
            case XML:
                mimeType = "application/xml";
                break;
            default:
                throw new Exception("rowType错误");
        }
        String contentType = mimeType + ";charset=" + charset;
        HeadUtil.setJdkRequestHeader(httpURLConnection, headMap, contentType);

        // 必需放在设置请求头之后，否则报错： Already connected
        OutputStream os = httpURLConnection.getOutputStream();

        // 请求体
        if (StringUtil.isNotBlank(textParamString)) {
            os.write(textParamString.getBytes(charset));
        }

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }
}
