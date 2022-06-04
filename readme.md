# 概述

jhttp用于发HTTP请求，目前支持发送GET和POST请求，其中POST请求支持form-data、x-www-form-urlencoded、json和xml；GET请无请求体，数据拼接在URL中，POST请求数据都放在请求体，URL不带数据。支持2种发请求的实现：JDK原生和Apache httpclient，默认JDK原生

# 使用
* 依赖

```xml
<dependency>
    <groupId>io.github.topicstudy</groupId>
    <artifactId>jhttp</artifactId>
    <version>1.1.2</version>
</dependency>
```


* 指定实现方式

```java
// 默认是直接用jdk发请求
Jhttp jhttp = new Jhttp();

// jdk
Jhttp jhttp = new Jhttp(HttpSendWayEnum.JDK);

// httpClient
Jhttp jhttp = new Jhttp(HttpSendWayEnum.HTTP_CLIENT);
```

# 示例

## 发GET请求

get请求无请求体，参数放在地址中

```java
@Test
public void get() throws Exception {
	Jhttp jhttp = new Jhttp();
	
    // 请求头
	Map<String, String> headMap = new HashMap();
	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 参数
	Map<String, String> textParamMap = new HashMap();
	textParamMap.put("p1", "v1");
	textParamMap.put("p2", "v1");
    
	HttpRes httpRes = jhttp.get(
                "http://localhost:8080/mockApi/get",
                 headMap, 
                 textParamMap
    );
	System.out.println(httpRes);
}
```

## 发POST请求

### formData

```java
@Test
public void postFormData() throws Exception {
	Jhttp jhttp = new Jhttp();
    
    // 请求头
	Map<String, String> headMap = new HashMap();

	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 文本参数
	Map<String, String> textParamMap = new HashMap();
	textParamMap.put("p1", "v1");
	textParamMap.put("p2", "v2");

	// 文件参数
	Map<String, File> binaryParamMap = new HashMap();
	binaryParamMap.put("myFile", new File("d:/tmp/1.jpg"));
	binaryParamMap.put("myFile2", new File("d:/tmp/2.jpg"));
	binaryParamMap.put("myFile3", new File("d:/tmp/2.jpg"));

	HttpRes httpRes = jhttp.postFormData(
        "http://localhost:8080/mockApi/postFormData",
		headMap, 
         textParamMap, 
         binaryParamMap
     );
	System.out.println(httpRes);
}
```

### x-www-form-urlencode

```java
@Test
public void postUrlencoded() throws Exception {
	Jhttp jhttp = new Jhttp();

    // 请求头
	Map<String, String> headMap = new HashMap();
	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 参数
	Map<String, String> textParamMap = new HashMap();
	textParamMap.put("p1", "v1");
	textParamMap.put("p2", "v2");

	HttpRes httpRes = jhttp.postFormUrlEncoded(
		"http://localhost:8080/mockApi/postUrlencoded",
		 headMap,
		 textParamMap
      );
	System.out.println(httpRes);
}
```

### json

```java
@Test
public void postJson() throws Exception {
	Jhttp jhttp = new Jhttp();

	// 请求头
	Map<String, String> headMap = new HashMap();
	headMap.put("h1", "v1");
	headMap.put("h2", "v2");

	// 参数
	String jsonParam = "{'k1':'v1','k2':'v2'}";

	HttpRes httpRes = jhttp.postJson(
			"http://localhost:8080/mockApi/postJson",
			headMap,
			jsonParam);
	System.out.println(httpRes);
}
```

### xml

```java
@Test
public void postXml() throws Exception {
	Jhttp jhttp = new Jhttp();

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

	HttpRes httpRes = jhttp.postXml(
			"http://localhost:8080/mockApi/postXml",
			headMap,
			xmlSb.toString());
	System.out.println(httpRes);
}
```





