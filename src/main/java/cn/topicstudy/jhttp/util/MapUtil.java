package cn.topicstudy.jhttp.util;

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
     * 从map中获取entry，忽略key的大小写
     */
    public static Map.Entry<String, Object> getEntryIgnoreCase(Map map, String key) {
        map = (Map<String, Object>) map;
        if (map == null) return null;
        if (key == null || key.trim().isEmpty()) return null;
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            String k = entry.getKey();
            if (key.equalsIgnoreCase(k)) return entry;
        }
        return null;
    }

    /**
     * 从map中获取value，忽略key的大小写
     */
    public static Object getIgnoreCase(Map map, String key) {
        return getEntryIgnoreCase(map, key).getValue();
    }

    /**
     * 判断map中是否有给定的key，忽略key的大小写
     */
    public static boolean containsKeyIgnoreCase(Map map, String key) {
        return getIgnoreCase(map, key) != null;
    }

    /**
     * 从map删除元素，忽略key的大小写
     */
    public static void removeIgnoreCase(Map map, String key) {
        Map.Entry<String, Object> entry = getEntryIgnoreCase(map, key);
        if (entry != null) map.remove(entry.getKey());
    }
}
