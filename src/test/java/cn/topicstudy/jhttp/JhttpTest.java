package cn.topicstudy.jhttp;

import cn.topicstudy.jhttp.dto.VivoCurrentTimeResponse;
import cn.topicstudy.jhttp.entity.HttpRes;
import cn.topicstudy.jhttp.enums.HttpSendWayEnum;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class JhttpTest {
    private Jhttp jhttpJdk = new Jhttp();
    private Jhttp jhttpHC = new Jhttp(HttpSendWayEnum.HTTP_CLIENT);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {
    }

    @Test
    public void testGet() {
    }

    @Test
    public void postFormData() {
    }

    @Test
    public void postFormUrlEncoded() {
    }

    @Test
    public void postJson() {
    }

    @Test
    public void postXml() {
    }

    @Test
    public void postRow() {
        Jhttp jhttp = new Jhttp(HttpSendWayEnum.JDK);
        HttpRes httpRes = jhttp.postJson("http://127.0.0.1:8080/test/postJSON", null,
                "{}");
        System.out.println(httpRes.getResponseHeadMap());
        System.out.println(httpRes.getTextResponseBody());

    }

    @Test
    public void getCurrentTime() {
        String vivoCurrentTimeUrl = "http://mshopact.vivo.com.cn/tool/config";
        HttpRes httpRes_jdk = jhttpJdk.get(vivoCurrentTimeUrl, null, null);
        System.out.println("httpRes_jdk:" + httpRes_jdk);
        System.out.println("httpRes_jdk.textRes:" + httpRes_jdk.getTextResponseBody());

        VivoCurrentTimeResponse vivoCurrentTimeResponse_jdk = jhttpJdk.getForObject(vivoCurrentTimeUrl, null, null, VivoCurrentTimeResponse.class);
        System.out.println("vivoCurrentTimeResponse_jdk:" + vivoCurrentTimeResponse_jdk);

        // hc
        HttpRes httpRes_hc = jhttpHC.get(vivoCurrentTimeUrl, null, null);
        System.out.println("httpRes_hc:" + httpRes_hc);
        System.out.println("httpRes_hc.textRes:" + httpRes_jdk.getTextResponseBody());

        VivoCurrentTimeResponse vivoCurrentTimeResponse_hc = jhttpHC.getForObject(vivoCurrentTimeUrl, null, null, VivoCurrentTimeResponse.class);
        System.out.println("vivoCurrentTimeResponse_hc:" + vivoCurrentTimeResponse_hc);
    }
}
