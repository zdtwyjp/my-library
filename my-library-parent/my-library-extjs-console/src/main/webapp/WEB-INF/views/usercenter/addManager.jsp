<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/views/common/extInclude.jsp" %>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="<c:url value='/viewsjs/common/sha1.js'/>"></script>
	<script type="text/javascript">
	Ext.onReady(function(){
		var emailRegex = new RegExp(<%=ConstantsObj.FIND_IS_E_MAIL_REGEX%>);
		// 如果要体现FORM效验效果，必须添加下面两行代码
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		
		var workerForm = new Ext.form.FormPanel({
			region		: 'center',
			frame 		: false,
			labelAlign	: 'right', 
			buttonAlign : 'center',
			border:false,
			bodyStyle : 'background-color:#dfe8f6;padding:10px 0px 0',
			autoScroll : true,
			layout   : 'hbox',
	    		layoutConfig: {
	                align:'middle',
	                pack:'center'
	            },
			labelWidth:100,
            items		:[{
            	xtype:'fieldset',
            	layout:'form',
	        	width:450,
	        	title:'新增管理员',
	        	items:[{
        			xtype:'textfield',
        			fieldLabel:'登录名',
    				id:'smanLoginName',
    				anchor:'95%',
    				allowBlank: false
        		},{
        			xtype:'textfield',
        			fieldLabel:'用户姓名',
    				id:'smanName',
    				anchor:'95%',
    				allowBlank: false
        		},{
        			xtype:'textfield',
        			fieldLabel:'登录密码',
    				id:'smanPwd',
    				anchor:'95%',
    				inputType: 'password',
    				allowBlank: false
        		},{
        			xtype:'textfield',
        			fieldLabel:'确认密码',
        			inputType: 'password',
    				id:'validPassword',
    				anchor:'95%',
    				validator:function()
    				{
    					if(workerForm.form.findField('smanPwd').getValue() == 
    							workerForm.form.findField('validPassword').getValue())
    						return true;
    					else
    						return false;
    				},
    				allowBlank: false
        		},{
        			xtype:'textfield',
        			fieldLabel:'邮箱',
    				id:'smanEmail',
    				anchor:'95%',
    				allowBlank: true,
    				regex : emailRegex, // 允许空串  
    				regexText : '输入格式错误！',
					maxLength : 40,
					maxLengthText : '不能超过40个字符！'
        		},{
        			xtype:'textfield',
        			fieldLabel:'联系电话',
    				id:'smanTel',
    				anchor:'95%',
    				allowBlank: true
        		},{
        			xtype:'textfield',
        			fieldLabel:'备注',
    				id:'smanRemark',
    				anchor:'95%',
    				allowBlank: true
        		},{
        			xtype: 'panel',
        			anchor:'95%',
        			layout: 'table',
        			layoutConfig:{columns:2,columnWidth:.5},
        			frame: true,        			
					labelAlign	: 'left',         			        			
        			fieldLabel:'管理员类型',
         			isFormField:true,//非常重要,否则panel默认不显示fieldLabel
        			items: [${result}]
        		}]
            }],
            buttons: [{
		        text: '提&nbsp;交',
		        handler:function()
		        {
		        	if(workerForm.form.isValid())
		        	{
		        		subEvent();
		        	}
		        	else
		        	{
		        		alertWarring('请认真填写管理员信息');
		        	}
		        }
		    },{
		        text: '重&nbsp;置',
		        handler:function()
		        {
		        	workerForm.form.reset();
		        }
		    },{
		        text: '返&nbsp;回',
		        handler:function()
		        {
		        	window.location.href = "<%=basePath%>console/data/usercenter/adminManage/adminManagement.htm";
		        }
		    }]
		    
		});	
		
		var displayPanel = new Ext.Viewport({
			layout   : 'border',
			items 	 : [workerForm]
		});
		
		function subEvent()
		{
			workerForm.form.submit({
				url : '<%=basePath%>console/data/usercenter/adminManage/addManager.htm',
				method : 'post',
				waitMsg:'数据正在提交,请稍等.....',
				waitTitle:'请稍等',
				params: {
					smanPwd1: hex_sha1(Ext.getCmp('smanPwd').getValue())
				},
				success:function(form, action){
					alertInformation(action.result.msg);
					document.location.href = "<%=basePath%>console/data/usercenter/adminManage/adminManagement.htm";
				},
				failure:function(form, action){
					alertWarring(action.result.msg);
				}
			});
		}
	});
	
	</script>
	<style>
		* {
			font-size: 12px;
		}
		html {
			font-size: 12px;
		}
		html body {
			font-size: 12px;
		}
	</style>
	
  </head>
  
  <body>
  </body>
</html>
