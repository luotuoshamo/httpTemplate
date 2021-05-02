package com.wjh.post;

import com.wjh.enm.RowType;
import com.wjh.entity.HttpRes;

import java.io.File;
import java.util.Map;

public interface HttpPost {
    HttpRes postFormData(String urlString, Map<String, String> headMap,
                         Map<String, String> textParamMap,
                         Map<String, File> binaryParamMap) throws Exception;

    HttpRes postFormUrlEncoded(String urlString, Map<String, String> headMap, Map<String, String> textParamMap) throws Exception;

    HttpRes postRow(RowType rowType, String urlString, Map<String, String> headMap, String textParamString) throws Exception;
}
