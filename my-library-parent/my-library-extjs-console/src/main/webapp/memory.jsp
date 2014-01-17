<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.net.InetAddress"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	
	long maxmemory = Runtime.getRuntime().maxMemory(); 
	maxmemory = maxmemory/(1024*1024);

	long freememory = Runtime.getRuntime().freeMemory(); 
	freememory = freememory/(1024*1024);
	
	long totalmemory = Runtime.getRuntime().totalMemory(); 
	totalmemory = totalmemory/(1024*1024);
	
	
	%>
	<b>maxmemory:<%=maxmemory %>MB</b>
	<b>freememory:<%=freememory %>MB</b>
	<b>totalmemory:<%=totalmemory %>MB</b>
	
	<b>IP:<%=InetAddress.getLocalHost().getHostAddress() %></b>

</body>
</html>