package cn.topicstudy.jhttp.get;

import cn.topicstudy.jhttp.entity.HttpRes;

import java.util.Map;

public interface HttpGet {
    HttpRes get(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception;
}
