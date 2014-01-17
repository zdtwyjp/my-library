<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>密码修改</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<link type="text/css"
			href="<c:url value='/ext/resources/css/ext-all.css'/>"
			rel="stylesheet" />
		<link type="text/css" href="<c:url value='/css/button.css'/>"
			rel="stylesheet" />
		<script type="text/javascript"
			src="<c:url value='/ext/ext-base.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ext/ext-all.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/ext/ext-lang-zh_CN.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/viewsjs/common/sha1.js'/>"></script>
		<script type="text/javascript"
			src="<c:url value='/ext/ux/ProgressBarPager.js'/>"></script>
		<script type="text/javascript">
		var changePasswordWin;	//修改密码窗口	

		Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.form.Field.prototype.msgTarget = 'side';
			
		    var changepwd = new Ext.form.FormPanel({
		        baseCls: 'x-plain',
		        labelWidth: 100,
		        labelAlign: 'right',
		        border: true,
		    	frame: true,
		        defaultType: 'textfield',

		        items: [{
		            fieldLabel: '原始密码',
		            //maxLength:3,
		            id:'startpwd',
		            name: 'startpwd',
		            inputType : 'password',
		            allowBlank:false,
		            blankText:'不能为空'
		        },{
		            fieldLabel: '最新密码',
		            minLength:1,
		            maxLength:20,
		            id:'newpwd',
		            inputType : 'password',
		            name: 'newpwd',
		            allowBlank:false,
		            blankText:'不能为空',
		            validator:function check(){
		            	if(Ext.get('newpwd').dom.value.length < 1 ||Ext.get('newpwd').dom.value.length > 20){
		            		return "密码长度必须为1-20位";
		            	}else{
		            		return true;
		            	}
		            }
		        },{
		            fieldLabel: '确认密码',
		            minLength:1,
		            maxLength:20,
		            id:'centainpwd',
		            name: 'centainpwd',
		            inputType : 'password',
		            allowBlank:false,
		            blankText:'不能为空',
		            validator:function check(){
		            	if(Ext.get('centainpwd').dom.value.length < 1 ||Ext.get('centainpwd').dom.value.length > 20){
		            		return "密码长度必须为1-20位";
		            	}else{
		            		return true;
		            	}
		            }
		        }]
		    });
		    
		    changePasswordWin = new Ext.Window({
		    	y : 100,
		        width: 320,
		        height: 180,
		        closable : false,
		        resizable : false,
		        title : '请记住您填写的新密码',
		        expandOnShow:true,
		        bodyStyle:'padding:15px;',
		        buttonAlign:'center',
		        modal : true,
		        items: [changepwd],

		        buttons: [{
		            text: '修 改',
		            handler: function(){
		            	if(document.getElementById('newpwd').value != document.getElementById('centainpwd').value){
		            		Ext.MessageBox.alert('提示', '密码输入不一致，请确认密码正确');
		            		return false;
		            	}else{
			            	if(changepwd.form.isValid()){
			            		var pwdStartpwdField = changepwd.form.findField('startpwd');
				            	var pwdNewpwdField = changepwd.form.findField('newpwd');
				            	var pwdCentainpwdField = changepwd.form.findField('centainpwd');
				            	
								var pwdStartpwd = pwdStartpwdField.getValue();
								var pwdNewpwd = pwdNewpwdField.getValue();
								var pwdCentainpwd = pwdCentainpwdField.getValue();
								
								var tempPwd =''; //将填充值设置跟密码一样长
								for(var i=0;i<pwdStartpwd.length;i++)
									tempPwd +='*';
								pwdStartpwdField.setValue(tempPwd);
								var tempPwd ='';
								for(var i=0;i<pwdNewpwd.length;i++)
									tempPwd +='*';
								pwdNewpwdField.setValue(tempPwd);
								var tempPwd ='';
								for(var i=0;i<pwdCentainpwd.length;i++)
									tempPwd +='*';
								pwdCentainpwdField.setValue(tempPwd);
								changepwd.form.submit({
										waitTitle : '请稍候',
										waitMsg : '正在修改.......',	
										params:{
											pwdStart:hex_sha1(pwdStartpwd),
											pwdNew:hex_sha1(pwdNewpwd),
											pwdCentain:hex_sha1(pwdCentainpwd)
										},
										url : '<%=basePath%>console/data/changePassword/changepwd.htm',
										method : 'POST',
										scope : this,
										success : function(form, action) {
											Ext.MessageBox.alert('提示', action.result.msg)
											changepwd.form.reset();
										},
										failure : function(form, action) {
											Ext.MessageBox.alert('警告', action.result.errors);
											changepwd.form.reset();
										}
								}); 
			            	}
		            	}
		            }
		        },{
		            text: '重 置',
		            handler: function(){
		            	changepwd.form.reset();
		            }
		        }]
		    });
		    
		    changePasswordWin.show();
		    
		});
		</script>
	</head>

	<body>
	</body>
</html>