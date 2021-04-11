package com.wjh.get;

import com.wjh.util.HttpRes;

import java.util.Map;

public interface HttpGet {
    HttpRes get(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception;
}
