package cn.topicstudy.jhttp.get.impl;

import cn.topicstudy.jhttp.entity.HttpRes;
import cn.topicstudy.jhttp.util.Constant;
import cn.topicstudy.jhttp.util.HeadUtil;
import cn.topicstudy.jhttp.util.MapUtil;
import cn.topicstudy.jhttp.util.ResponseUtil;
import cn.topicstudy.jhttp.get.HttpGet;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class JdkHttpGetImpl implements HttpGet {
    /**
     * 发get请求
     * 标准get请求无request body，不是所有的服务器可以接收get请求的request body
     */
    @Override
    public HttpRes get(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception {
        String charset =  HeadUtil.getCharsetFromHeadMap(headMap, Constant.DEFAULT_CHARSET);

        // 参数
        if (textParamMap != null && !textParamMap.isEmpty()) {
            String textParam = MapUtil.mapToQueryString(textParamMap, charset, true);
            if (urlString.indexOf("?") != -1) {
                urlString += ("&" + textParam);
            } else {
                urlString += ("?" + textParam);
            }
        }

        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setRequestMethod("GET");

        // 请求头
        HeadUtil.setJdkRequestHeader(httpURLConnection, headMap, null);

        return ResponseUtil.packJdkHttpRes(httpURLConnection);
    }
}
