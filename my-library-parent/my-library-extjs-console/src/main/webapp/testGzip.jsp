<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="org.apache.commons.httpclient.HttpClient"%>
<%@ page import="org.apache.commons.httpclient.HttpException"%>
<%@ page import="org.apache.commons.httpclient.methods.GetMethod"%>
<%@ page import="java.io.IOException"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
	
	HttpClient httpClient = new HttpClient();
	GetMethod getMethod = new GetMethod("http://localhost:8082/lfd/console/index.htm");
	try {
	    getMethod.addRequestHeader("accept-encoding", "gzip,deflate");
	    getMethod.addRequestHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; Alexa Toolbar; Maxthon 2.0)");
	    int result = httpClient.executeMethod(getMethod);
	    if (result == 200) {
		out.println(getMethod.getResponseContentLength());
		String html = getMethod.getResponseBodyAsString();
		out.println(html);
		out.println(html.getBytes().length);
	    }
	} catch (HttpException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    getMethod.releaseConnection();
	}
	
	
	%>

</body>
</html>