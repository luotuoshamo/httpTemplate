# 概述

* 该框架用于发送HTTP请求，目前支持发送GET、POST请求，其中POST请求支持form textResponseBody、x-www-form-urlencoded、json、xml
* 该框架目前只依赖于JDK8 ，不久后会加入Apache HttpClient的实现

# 发GET请求

```java
@Test
public void get() throws Exception {
	HttpTemplate httpTemplate = new HttpTemplate();
	
    // 请求头
	Map<String, String> headMap = new HashMap();
	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 参数
	Map<String, String> textParamMap = new HashMap();
	textParamMap.put("p1", "v1");
	textParamMap.put("p2", "v1");
    
	HttpRes httpRes = httpTemplate.get(
                "http://localhost:8080/mockApi/get",
                 headMap, 
                 textParamMap
    );
	System.out.println(httpRes);
}
```

#  发POST请求

## form textResponseBody

```java
@Test
public void postFormData() throws Exception {
	HttpTemplate httpTemplate = new HttpTemplate();
    
    // 请求头
	Map<String, String> headMap = new HashMap();

	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 文本参数
	Map<String, String> textParamMap = new HashMap();
	textParamMap.put("p1", "v1");
	textParamMap.put("p2", "v2");

	// 文件参数
	Map<String, File> fileParamMap = new HashMap();
	fileParamMap.put("myFile", new File("d:/tmp/1.jpg"));
	fileParamMap.put("myFile2", new File("d:/tmp/2.jpg"));
	fileParamMap.put("myFile3", new File("d:/tmp/2.jpg"));

	HttpRes httpRes = httpTemplate.postFormData(
        "http://localhost:8080/mockApi/postFormData",
		headMap, 
         textParamMap, 
         fileParamMap
     );
	System.out.println(httpRes);
}
```

## x-www-form-urlencode

```java
@Test
public void postUrlencoded() throws Exception {
	HttpTemplate httpTemplate = new HttpTemplate();

    // 请求头
	Map<String, String> headMap = new HashMap();
	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 参数
	Map<String, String> textParamMap = new HashMap();
	textParamMap.put("p1", "v1");
	textParamMap.put("p2", "v2");

	HttpRes httpRes = httpTemplate.postUrlencoded(
		"http://localhost:8080/mockApi/postUrlencoded",
		 headMap,
		 textParamMap
      );
	System.out.println(httpRes);
}
```

## json

```java
@Test
public void postJson() throws Exception {
	HttpTemplate httpTemplate = new HttpTemplate();

	// 请求头
	Map<String, String> headMap = new HashMap();
	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 参数
	String jsonParam = "{'k1':'v1','k2':'v2'}";

	HttpRes httpRes = httpTemplate.postJson(
			"http://localhost:8080/mockApi/postJson",
			headMap,
			jsonParam);
	System.out.println(httpRes);
}
```

## xml

```java
@Test
public void postXml() throws Exception {
	HttpTemplate httpTemplate = new HttpTemplate();

	// 请求头
	Map<String, String> headMap = new HashMap();
	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 参数
	StringBuffer xmlSb = new StringBuffer();
	xmlSb.append("<xml charSet='UTF-8'>");
	xmlSb.append("<root>");
	xmlSb.append("<name>wjh</name>");
	xmlSb.append("<age>wjh</age>");
	xmlSb.append("</root>");

	HttpRes httpRes = httpTemplate.postXml(
			"http://localhost:8080/mockApi/postXml",
			headMap,
			xmlSb.toString());
	System.out.println(httpRes);
}
```

# 反馈

新功能、BUG、建议...都可在issues中提，也欢迎pull requestヾ(≧▽≦*)o







