# 概述

jhttp用于发HTTP请求，目前支持发送GET和POST请求，其中POST请求支持form-data、x-www-form-urlencoded、json和xml；GET请无请求体，数据拼接在URL中，POST请求数据都放在请求体，URL不带数据。支持2种发请求的实现：JDK原生和Apache httpclient，默认JDK原生。

# 依赖

| 依赖              | 版本   | maven自动下载依赖，使用时无需手动添加 |
| ----------------- | ------ | ------------------------------------- |
| JDK               | 1.8    | N                                     |
| Apache httpclient | 4.5.13 | Y                                     |
| Apache httpmime   | 4.5.13 | Y                                     |

# 使用

* pom.xml中添加依赖

```xml
<repositories>
    <repository>
        <id>jmail</id>
        <name>GitHub OWNER Apache Maven Packages</name>
        <url>https://topicstudy.github.io/jhttp/maven-repo/</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.wjh</groupId>
        <artifactId>jhttp</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

>加入上面的配置后`mvn install`一下；如果还是爆红需要重启IDEA
>
>如果`mvn install`失败过，请删掉本地maven仓库中安装失败的项目，在执行`mvn 


* 指定发请求的实现

```java
// jdk
HttpTemplate httpTemplate = new HttpTemplate();

// jdk
HttpTemplate httpTemplate = new HttpTemplate(ImplWayEnum.JDK);

// httpClient
HttpTemplate httpTemplate = new HttpTemplate(ImplWayEnum.HTTP_CLIENT);
```

# 示例-发GET请求

get请求无请求体，参数放在地址中

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

#  示例-发POST请求

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
	Map<String, File> binaryParamMap = new HashMap();
	binaryParamMap.put("myFile", new File("d:/tmp/1.jpg"));
	binaryParamMap.put("myFile2", new File("d:/tmp/2.jpg"));
	binaryParamMap.put("myFile3", new File("d:/tmp/2.jpg"));

	HttpRes httpRes = httpTemplate.postFormData(
        "http://localhost:8080/mockApi/postFormData",
		headMap, 
         textParamMap, 
         binaryParamMap
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

	HttpRes httpRes = httpTemplate.postFormUrlEncoded(
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

 # 联系我

| 微信   | topicstudy                    |
| ------ | ----------------------------- |
| Gitee  | https://gitee.com/topicstudy  |
| Github | https://github.com/topicstudy |







