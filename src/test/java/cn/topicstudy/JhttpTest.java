package cn.topicstudy;

import cn.topicstudy.enums.HttpSendWayEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    }
}
