package cn.topicstudy.jhttp.entity;

import lombok.Data;

import java.util.Map;

@Data
public class HttpRes {
    // 响应码（属于响应行）
    private String responseCode;

    private String responseMessage;
    // 响应头
    private Map<String, String> responseHeadMap;
    // 响应体-文本
    private String textResponseBody;
    // 响应体-二进制
    private byte[] binaryResponseBody;


    public HttpRes() {
    }

    @Override
    public String toString() {
        String s = "";
        if (binaryResponseBody != null) {
            for (int i = 0; i < Math.min(10, binaryResponseBody.length); i++) {
                s += ((int) binaryResponseBody[i] + ",");
            }
            s += "...total byte array length is " + binaryResponseBody.length;
        }

        return "HttpRes{" +
                "responseCode='" + responseCode + '\'' +
                "responseMessage='" + responseMessage + '\'' +
                ", responseHeadMap=" + responseHeadMap +
                ", textResponseBody='" + textResponseBody + '\'' +
                ", binaryResponseBody=" + s +
                '}';
    }
}
