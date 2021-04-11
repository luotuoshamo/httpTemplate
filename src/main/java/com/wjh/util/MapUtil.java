package com.wjh.util;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

public class MapUtil {
    private MapUtil() {
    }

    /**
     * 将Map<String,String>转成queryString
     * 例如map={k1:v1,k2:v2}时queryString=k1=v1&k2=v2
     */
    public static String mapToQueryString(Map<String, String> map) {
        return mapToQueryString(map, false);
    }

    public static String mapToQueryString(Map<String, String> map, boolean isUrlEncodeV) {
        String queryString = "";
        if (map != null && !map.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String k = entry.getKey();
                String v = entry.getValue();
                if (isUrlEncodeV) {
                    v = URLEncoder.encode(v);
                }
                queryString += ("&" + k + "=" + v);
            }
            queryString = queryString.substring(1);
        }
        return queryString;
    }
}
