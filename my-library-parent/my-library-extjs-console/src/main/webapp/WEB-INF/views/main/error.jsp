<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp" %>
<%
	String path = request.getContextPath();
	String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/vnd.microsoft.icon">
		<link rel="icon" href="<%=path%>/images/favicon.ico" type="image/vnd.microsoft.icon">
		<title>有房喔</title>
<%-- 		<link rel="icon" href="<c:url value='/favicon.ico' />" type="image/x-icon" /> --%>
<%-- 		<link rel="shortcut icon" href="<c:url value='/favicon.ico' />" type="image/x-icon" /> --%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<style type="text/css">
		<!--
		body {
			margin:0;
			font-size:12px;
			color:#fff;
			overflow:hidden;
		}
		a {
			color:#fff;
			text-decoration:none;
		}
		a:hover {
			color:#ff0000;
			text-decoration:underline;
		}
		td {
			font-size:12px;
			line-height:200%;
		}
		.login_input {
			width:110px;
			height:18px;
			background:#1d5889;
			color:#FFF;
			border:1px solid #fff;
		}
		-->
		</style>
	</head>
	<body>
		<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
			<tr><td bgcolor="#1075b1">&nbsp;</td></tr>
			<tr>
				<td height="608" background="<c:url value='/images/login_03.gif' />" align="center">
					<table width="750" border="0" cellspacing="0" cellpadding="0">
						<tr><td height="167"><img src="<c:url value='/images/logon_01.jpg' />"></td></tr>
						<tr>
							<td height="249" background="<c:url value='/images/logon_02.jpg' />">
								<table width="280" height="60" align="center" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="100" align="center">
											<img src="<c:url value='/images/err.jpg' />" width="50" height="51">
										</td>
										<td width="180">${message}</td>
									</tr>
									<tr><td colspan="2" height="50">&nbsp;</td></tr>
								</table>
							</td>
						</tr>
						<tr><td height="192"><img src="<c:url value='/images/logon_03.jpg' />"></td></tr>
					</table>
				</td>
			</tr>
			<tr><td bgcolor="#152753">&nbsp;</td></tr>
		</table>
	</body>
</html>