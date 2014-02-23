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
			id: 'workerForm',
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
	        	title:'编辑管理员信息',
	        	items:[{
        			xtype:'textfield',
        			fieldLabel:'用户姓名',
    				id:'smanName',
    				anchor:'95%',
    				allowBlank: false
        		},{
        			xtype:'textfield',
        			fieldLabel:'登录名',
    				id:'smanLoginName',
    				anchor:'95%',
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
        		}, {
        			xtype: 'hidden',
        			name: 'smanId',
        			id: 'smanId'
        		}, {
        			xtype: 'hidden',
        			name: 'smanPwd',
        			id: 'smanPwd'
        		}, {
        			xtype: 'panel',
        			anchor: '95%',
        			frame: true,
					labelAlign	: 'left',         			
        			layout: 'table',
        			layoutConfig:{columns:2},
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
        			loadData();
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
		
		function loadData() {
			workerForm.form.setValues({
				smanId		  : '${resultMap["manager"][0].smanId}',
				smanName 	  : '${resultMap["manager"][0].smanName}',
				smanLoginName : '${resultMap["manager"][0].smanLoginName}',
				smanEmail     : '${resultMap["manager"][0].smanEmail}',
				smanTel 	  : '${resultMap["manager"][0].smanTel}',
				smanRemark 	  : '${resultMap["manager"][0].smanRemark}'
			});
		}
		
		eval('loadData()');
		
		function subEvent()
		{
			workerForm.form.submit({
				url : '<%=basePath%>console/data/usercenter/adminManage/modifyManagerInfo.htm',
				method : 'post',
				waitMsg:'数据正在提交,请稍等.....',
				waitTitle:'请稍等',
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
