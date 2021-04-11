package com.wjh.post;

import com.wjh.util.HttpRes;
import com.wjh.util.RowType;

import java.io.File;
import java.util.Map;

public interface HttpPost {
    HttpRes postFormData(String urlString, Map<String, String> headMap,
                         Map<String, String> textParamMap,
                         Map<String, File> fileParamMap) throws Exception;


    HttpRes postRow(RowType rowType, String urlString, Map<String, String> headMap, String jsonParam) throws Exception;

    HttpRes postUrlencoded(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception;
}
