package cn.topicstudy.jhttp;

import cn.topicstudy.jhttp.entity.HttpRes;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class OtherCompanyAPITest {
    private Jhttp httpTemplate = new Jhttp();
    private Jhttp jhttp = new Jhttp();

    @Test
    public void testJd() throws Exception {
        Map<String, String> textParamMap = new HashMap<>();
        textParamMap.put("appkey", "");
        textParamMap.put("city", "南京");
        HttpRes httpRes = httpTemplate.get("https://way.jd.com/jisuapi/weather", null, textParamMap);
        System.out.println(httpRes);
    }

    @Test
    public void getTaobaoMobileApi() throws Exception {
        // 参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("tel", "13585766229");

        HttpRes httpRes = httpTemplate.get(
                "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm",
                null,
                textParamMap
        );
        System.out.println(httpRes);
    }

    @Test
    public void getTaobaoMobileApiNew() throws Exception {
        // 参数
        Map<String, String> textParamMap = new HashMap();
        textParamMap.put("tel", "13585766229");

        HttpRes httpRes = jhttp.get(
                "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm",
                null,
                textParamMap
        );
        System.out.println(httpRes);
    }
}
