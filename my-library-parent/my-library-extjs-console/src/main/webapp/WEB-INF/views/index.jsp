<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    String path = request.getContextPath();
    
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="<%=path %>/extjs4/resources/css/ext-all.css" /> 
<!--引入自定义CSS--> 
<script type="text/javascript" src="<%=path %>/extjs4/ext-all-debug.js"></script> 
<style type="text/css">
</style>
<script type="text/javascript">
Ext.require([
             'Ext.window.MessageBox',
             'Ext.tip.*'
         ]);
Ext.onReady(function(){
    Ext.get('mb1').on('click', function(e){
        Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', showResult);
    });
    
    function showResult(btn){
        Ext.example.msg('Button Click', 'You clicked the {0} button', btn);
    };
});

</script> 



</head>
<body>
	index <button id="mb1">Show</button>
</body>
</html>