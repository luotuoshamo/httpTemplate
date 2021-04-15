package com.wjh.util;

import java.util.Map;

public class HttpRes {
    private String responseCode;
    private String responseMessage;
    private String data;
    private Map<String,String> responseHeadMap;

    public HttpRes() {
    }

    public HttpRes(String responseCode, String responseMessage, String data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, String> getResponseHeadMap() {
        return responseHeadMap;
    }

    public void setResponseHeadMap(Map<String, String> responseHeadMap) {
        this.responseHeadMap = responseHeadMap;
    }

    @Override
    public String toString() {
        return "HttpRes{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", data='" + data + '\'' +
                ", responseHeadMap=" + responseHeadMap +
                '}';
    }
}
