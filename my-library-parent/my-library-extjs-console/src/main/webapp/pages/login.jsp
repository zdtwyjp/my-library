<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>有房喔</title>
	
    <meta http-equiv="keywords" content="">
    <meta http-equiv="description" content="">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<script>if( self != top ){ window.top.location.href ="<c:url value='/pages/login.jsp'/>";}</script>
    
    <script type="text/javascript" src="<c:url value="/viewsjs/common/sha1.js"/>"></script>
	<style type="text/css">
<!--
	body {
	    margin:0;
		font-size:12px;
		color:#FFF;
		overflow:hidden;
	}
	a{  
	    color:#FFF;
		text-decoration: none;
	}
	a:hover {
	    color: #FFF;
		text-decoration:underline;
	}
	td{
	    font-size:12px;
	}
	.k_input{
	    width:110px; 
		height:18px; 
		background:#1d5889;
		color:#FFF;
		border:1px solid #fff;
	}
	span{ 
	    color: #ff0000;
	}

-->
</style>
<script type="text/javascript">
    function onSubmit(){
        if(document.getElementById("userName").value.trim() == '' || document.getElementById("pwd1").value.trim() == '') {
    		return;
    	}
       	var form1 = document.getElementById('form1')
        var password = form1.pwd1.value;
        form1.pwd.value = hex_sha1(password);
        //form1.pwd.value = password;
        form1.submit();
    }
    function onFormEnterKeyDown(e) {
    	e = window.event ? window.event : e;
    	var keyCode = e.keyCode ? e.keyCode : e.which;
    	if(keyCode == 13) {
    		onSubmit();
    	}
    }
</script>
  </head>
  
  <body onkeydown="onFormEnterKeyDown(event)">
      <table width="100%"  height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#1075b1">&nbsp;</td>
  </tr>
  <tr>
    <td height="608" background="<c:url value='/images/login_03.gif'/>" align="center">
	<table width="750" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="167"><img src="<c:url value='/images/logon_01.jpg'/>"></td>
      </tr>
      <tr>
        <td height="249" background="<c:url value='/images/logon_02.jpg'/>">
		
		<table width="375" height="180" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
    <td colspan="2" height="15"></td>
    </tr>
  <tr>
    <td colspan="2" height="63"><img src="<c:url value='/images/logo.png'/>"></td>
    </tr>
  <tr>
    <td colspan="2" height="10"></td>
    </tr>
  <tr>
    <td width="195" align="center"><img src="<c:url value='/images/tp2.jpg'/>"></td>
    <td width="180">
	  <form name="form1" id="form1" method="post" action="<c:url value='/managerLogin.htm'/>" onsubmit="javascript: return false;">
	    <input type="hidden" value="" name="pwd"/>
	    <table width="100%" border="0" cellspacing="5" cellpadding="3">
          <tr>
            <td width="33%" align="right">用户名</td>
            <td width="67%"><input type="text" name="userName" class=k_input id="userName"></td>
          </tr>
          <tr>
            <td align="right">密&nbsp;&nbsp;码</td>
            <td><input type="password" name="pwd1" class=k_input id="pwd1"></td>
          </tr>
          <tr>
           <td colspan="2" align="right"><input type="image" src="<c:url value='/images/dl.jpg'/>" width="50" height="17" onclick="onSubmit()">&nbsp;&nbsp;</td>
            </tr>
        </table>
            </form>	  </td>
  </tr>
</table>

		</td>
      </tr>
      <tr>
        <td height="192"><img src="<c:url value='/images/logon_03.jpg'/>"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="#152753">&nbsp;</td>
  </tr>
</table>
  </body>
</html>
