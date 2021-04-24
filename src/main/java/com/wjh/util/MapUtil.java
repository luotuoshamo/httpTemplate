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
    public static String mapToQueryString(Map<String, String> map) throws Exception {
        return mapToQueryString(map, null, false);
    }

    public static String mapToQueryString(Map<String, String> map, String charset, boolean isUrlEncodeV) throws Exception {
        String queryString = "";
        if (map != null && !map.isEmpty()) {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String k = entry.getKey();
                String v = entry.getValue();
                if (isUrlEncodeV) {
                    v = URLEncoder.encode(v, charset);
                }
                queryString += ("&" + k + "=" + v);
            }
            queryString = queryString.substring(1);
        }
        return queryString;
    }

    /**
     * application/javascript;charset=GBK
     */
    public static String getCharsetFromMap(Map<String, String> map) {
        if (map == null) return null;
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if ("Content-Type".equalsIgnoreCase(key)) {
                String value = map.get(key);
                int index = value.indexOf("charset");
                if (index != -1) return value.split("=")[1];
            }
        }
        return null;
    }
}
