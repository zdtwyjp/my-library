<%@ page contentType="text/html;charset=UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%="错误页面"%></title>	
		<script language="JavaScript">
		    function closeWin() {
				window.close();
		    }
		</script>		
	</head>
	
	<body>
		<div class="error">
			<p><%= ((Exception)request.getAttribute("exception")).getMessage() %></p>
			<a href="javascript:void(0)"><img src="<%=path %>images/close.gif" onclick="closeWin()" /></a>
		</div>
	</body>
</html>