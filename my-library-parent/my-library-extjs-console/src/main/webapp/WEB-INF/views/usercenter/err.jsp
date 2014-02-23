<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title><fmt:message key="login.error.title"></fmt:message></title>
<style type="text/css">
<!--
body {
	margin: 0px;
	font-size: 12px;
	color: #FFF;
	overflow: hidden;
}

a {
	color: #FFF;
	text-decoration: none;
}

a:hover {
	color: #ff0000;
	text-decoration: underline;
}

td {
	font-size: 12px;
	line-height: 200%;
}
-->
</style>

</head>

<body>
	<table width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td bgcolor="#1075b1">&nbsp;</td>
		</tr>
		<tr>
			<td height="608" background="images/login_03.gif" align="center">
				<table width="750" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="167"><img src="images/logon_01.jpg"></td>
					</tr>
					<tr>
						<td height="249" background="images/logon_02.jpg">

							<table width="280" height="60" align="center" border="0"
								cellspacing="0" cellpadding="0">
								<tr>
									<td width="100" align="center"><img src="images/err.jpg"
										width="49" height="52"></td>
									<td width="180">很抱歉你所输入的用户名或密码有误或者账户被停用，请点击 <a
										href="<c:url value='console/index.htm'/>">返回</a> 回到登录页面。
									</td>
								</tr>
								<tr>
									<td colspan="2" height="50">&nbsp;</td>
								</tr>
							</table>

						</td>
					</tr>
					<tr>
						<td height="192"><img src="images/logon_03.jpg"></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#152753">&nbsp;</td>
		</tr>
	</table>
</body>
</html>
