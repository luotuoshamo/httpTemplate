package com.wjh.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class PrintUtil {
    public static void printHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            System.out.println("header=" + s + ":" + request.getHeader(s));
        }
    }

    public static void printParams(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            System.out.println("param=" + s + ":" + request.getParameter(s));
        }
    }
}
