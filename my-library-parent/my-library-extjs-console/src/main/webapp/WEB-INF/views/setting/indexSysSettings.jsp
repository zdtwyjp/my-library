<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
   <%@ include file="/WEB-INF/views/common/extInclude.jsp" %>
   <%@ include file="/WEB-INF/views/common/taglibInclude.jsp" %>
   
   <script type="text/javascript">
	// 系统参数设置
	Ext.onReady(function() {
		// 如果要体现FORM效验效果，必须添加下面两行代码
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';

		var updateVersion = new Ext.form.RadioGroup({
			fieldLabel : '是否有版本更新',
			name : 'sys_parameter_version_code',
			id : 'sys_parameter_version_code',
			anchor : '75%',
			items : [{
				boxLabel : '否',
				name : 'sys_parameter_version_code',
				inputValue : 0
			}, {
				boxLabel : '是',
				name : 'sys_parameter_version_code',
				inputValue : 1
			}]
		});
		
		var androidPhoneVersion = new Ext.form.TextField({
			fieldLabel : 'Android手机版',
			id : 'androidPhoneVersion',
			name : 'androidPhoneVersion',
			anchor : '90%',
			inputType : 'file'
		});
		
		var iphonePhoneVersion = new Ext.form.TextField({
			fieldLabel : 'IPhone版',
			id : 'iphonePhoneVersion',
			name : 'iphonePhoneVersion',
			anchor : '90%',
			inputType : 'file'
		});
		
		var ipadPhoneVersion = new Ext.form.TextField({
			fieldLabel : 'IPad版',
			id : 'ipadPhoneVersion',
			name : 'ipadPhoneVersion',
			anchor : '90%',
			inputType : 'file'
		});
		
		var androidPhoneVersionNumber = new Ext.form.TextField({
			fieldLabel : 'Android手机版本号',
			id : 'sys_parameter_version_number',
			name : 'sys_parameter_version_number',
			anchor : '85%',
			allowBlank : false,
			blankText : '请输入版本号！',
			maxLength : 4,
			maxLengthText : '版本号最大长度不能超过4位数！'
		});
		
		var androidPhoneVersionQuery = new Ext.Button({
			fieldLabel : '当前版本安装文件',
			id : 'androidPhoneVersionQuery',
			name : 'androidPhoneVersionQuery',
			anchor : '85%',
			text : '下载',
			listeners : {
				click : function(){
					getVersionUrl();
				}
			}
		});
		
		var settingForm = new Ext.form.FormPanel({
			region : 'center',
			border : false,
			fileUpload : true,
			frame : false,
			bodyStyle : 'background-color:#dfe8f6;padding:10px 0px 0',
			labelAlign : 'right',
			buttonAlign : 'center',
			id : 'settingForm',
			autoScroll : true,
			layout : 'hbox',
			layoutConfig : {
				align : 'middle',
				pack : 'center'
			},
			labelWidth : 250,
			items : [{
				xtype : 'fieldset',
				layout : 'form',
				width : 550,
				title : '版本更新设置',
				items : [androidPhoneVersionNumber,androidPhoneVersion,androidPhoneVersionQuery,iphonePhoneVersion,ipadPhoneVersion]
			}],
			buttons : [{
				text : '提&nbsp;交',
				handler : submitForm
			}, {
				text : '重&nbsp;置',
				handler : initData
			}]
		});
		new Ext.Viewport({
			renderTo : 'mainDiv',
			layout : 'border',
			items : [settingForm]
		});

		function initData() {
			Ext.Ajax.request({
				url : '<%=path%>/console/data/setting/getSysSettings.htm',
				success : function(response, options) {
					var txt = Ext.util.JSON.decode(response.responseText);
					if(txt.success) {
						// 初始化Form
						settingForm.form.setValues({
							sys_parameter_version_code : txt.sys_parameter_version_code ,
							sys_parameter_version_number : txt.sys_parameter_version_number
						});

					}else {
						alertError(txt.msg);
					}
				},
				failure : function(form, action) {
					alertError('系统忙，请稍后访问！');
				}
			});
		}
		
		function getVersionUrl(){
			Ext.Ajax.request({
				url : '<%=path%>/console/data/setting/getVersionUrl.htm',
				success : function(response, options) {
					var txt = Ext.util.JSON.decode(response.responseText);
					if(txt.success) {
						var sys_parameter_update_url_phone_android = txt.sys_parameter_update_url_phone_android;	
						executeDownload(sys_parameter_update_url_phone_android);
					}else {
						alertError(txt.msg);
					}
				},
				failure : function(form, action) {
					alertError('系统忙，请稍后访问！');
				}
			});
		}
		
		function executeDownload(url){
			var versionIframe = document.getElementById("versionIframeId");
			if(versionIframe){
				versionIframe.src = '<%=path%>/'+url;
			}
		}

		function submitForm() {
			if(settingForm.form.isValid()) {
				settingForm.getForm().submit({
					url : '<%=path%>/console/data/setting/saveSysSettings.htm',
					method : 'post',
					waitMsg : '正在保存数据，请稍候...',
					clientValidation : true,
					success : function(form, action) {
						alertSuccess(action.result.msg);
					},
					failure : function(form, action) {
						alertError(action.result.msg);
					}
				});
			}
		}
		// 初始化网站参数
		initData();
	});
   
    
	</script>

  </head>
  
  <body>
      <div id="mainDiv"></div>
      <!-- 下载IFrame -->
      <iframe id="versionIframeId" frameborder="no" src=""></iframe>
  </body>
</html>
