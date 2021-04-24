package com.wjh.controller;

import com.wjh.util.PrintUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * 提供的接口
 */
@RestController
@RequestMapping("/mockApi")
public class MockApiController {
    @GetMapping(value = "/get",produces = "application/json")
    public String get(HttpServletRequest request) {
        PrintUtil.printHeaders(request);
        PrintUtil.printParams(request);
        return "{res:getRes}";
    }

    @PostMapping(value = "/postFormData", consumes = "multipart/form-data")
    public String postFormData(MultipartFile myFile, MultipartFile myFile2, MultipartFile myFile3, HttpServletRequest request) {
        PrintUtil.printHeaders(request);
        PrintUtil.printParams(request);
        if (myFile == null) {
            System.out.println("myFile is null");
        } else {
            System.out.println("myFile=" + myFile + ",myFileName=" + myFile.getOriginalFilename());
        }
        if (myFile2 == null) {
            System.out.println("myFile2 is null");
        } else {
            System.out.println("myFile2=" + myFile2 + ",myFileName2=" + myFile2.getOriginalFilename());
        }
        return "{postFormData:postFormDataRes}";
    }


    @PostMapping(value = "/postUrlencoded", consumes = "application/x-www-form-urlencoded")
    public String postUrlencoded(HttpServletRequest request) {
        PrintUtil.printHeaders(request);
        PrintUtil.printParams(request);
        return "{postUrlencoded:postUrlencodedRes}";
    }

    @PostMapping(value = "/postJson", consumes = "application/json")
    public String postJson(HttpServletRequest request) throws Exception {
        PrintUtil.printHeaders(request);
        PrintUtil.printParams(request);

        // 获取json参数
        BufferedReader br = new BufferedReader(new InputStreamReader(
                request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String jsonParam = sb.toString();// {k1:v1,k2:v2}

        return "{postJson:postJsonRes}, jsonParam=" + jsonParam;
    }

    @PostMapping(value = "/postXml", consumes = "application/xml")
    public String postXml(HttpServletRequest request) throws Exception {
        PrintUtil.printHeaders(request);
        PrintUtil.printParams(request);

        // 获取xml参数
        BufferedReader br = new BufferedReader(new InputStreamReader(
                request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String xmlParam = sb.toString();

        return "{postXml:postXmlRes}, xmlParam=" + xmlParam;
    }

}
