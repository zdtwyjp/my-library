<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/WEB-INF/views/common/extInclude.jsp" %>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<script type="text/javascript">
		
       Ext.onReady(function(){
    	   	  //目录管理参数
			  var isEditing = false;
			  var selectCatalogId = -1;
			  var expendOldPath = null;
			  //功能点管理参数
			  var isFunEditing = false;
			  var selectFunId = -1;
       	
       	      //加载树
              var loader = new Ext.tree.TreeLoader({  
		        	dataUrl:'<%=basePath%>console/data/usercenter/roleFunManage/createTree.htm?nodeId=${id}'
		      });
		      //树的根结点
              var rootNode = new Ext.tree.AsyncTreeNode({
                    id:'0',
		      		draggable : false,
		      		expanded : true,
		      		text : '功能列表'
              });
              
              //左边的树菜单
              var leftPanel = new Ext.tree.TreePanel({
            	  		title            : '功能列表',
					    region 			 : 'west',
			        	border     		 : true,
			            split      		 : true,  
			        	animate    		 : true,  
			        	rootVisible		 : false,
			        	enableDragDrop : true,
			        	autoScroll 		 : true,
			        	autoHeight 		 : false,
			        	loader     		 : loader,                  
						root       	 	 : rootNode,
						width			 : 230,
						minSize 		 : 200,
						maxSize 		 : 230,
						tbar: new Ext.Toolbar({
			        		items:[{
			           				text: '&nbsp;刷&nbsp;新&nbsp;',
			           				enableToggle: false,
			           				iconCls:'common_add',
			           				handler: function(){
			           					refreshTree();
			           					clearCatTable();
			           				}
			           			},'-',{
			   		             	text: '展开全部',
			           				enableToggle: false,
			           				iconCls:'common_list',
			           				handler:function(){
			           					leftPanel.expandAll();
			           				}
			           			},'-',{
			   		             	text: '全部收起',
			           				enableToggle: false,
			           				iconCls:'common_details',
			           				handler:function(){
			           					leftPanel.collapseAll();
			           				}
			           			}
			           		]
			           	})
              });
              
             //在加载前重新传URL以及ID
            leftPanel.on('beforeload', function(node){
	    		leftPanel.loader.dataUrl = "<%=basePath%>/console/data/usercenter/roleFunManage/createTree.htm?nodeId=" + node.id;
	    	});
	    	rootNode.expand(false,false);
	    	
	    	//树的点击事件
	    	leftPanel.on("click", function(node) {
	    		expendOldPath = node.getPath();
	    		if(!node.leaf){
		    		selectCatalogId = node.id;
		    		
	    			// 初始化表单
					initCatalog(selectCatalogId);
	    			tabPanel.setActiveTab(0);
	    		}else{
	    			selectFunId = node.id;
	    			
	    			// 初始化表单
	    			initFun(selectFunId);
	    			tabPanel.setActiveTab(1);
	    		}
	    	}, leftPanel);
	    	
	    	// ------------------------  目录管理 Begin -----------------------------------
	    	
	    	function initCatalog(catId){
	    		Ext.Ajax.request({
					url : '<%=path%>/console/data/usercenter/adminManage/initCatalog.htm',
					params : {
						catId : catId
					},
					success : function(response, options) {
						var txt = Ext.util.JSON.decode(response.responseText);
						if(txt.success) {
							catalog_store_select.load({callback : function(){
								cat_Combox.setValue(txt.parentCatalogId);
							}});
							name_Add.setValue(txt.name_Add);
							desc_Add.setValue(txt.desc_Add);
							serialNumber_add.setValue(txt.serialNumber_add);
						}else {
							alertError(txt.msg);
						}
					},
					failure : function(form, action) {
						alertError('系统忙，请稍后访问！');
					}
				});
	    	}
	    	
	    	
			var catalog_store_select = new Ext.data.Store({
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : '<%=path%>/console/data/usercenter/adminManage/initCatalogJsonCombox.htm'
				}),
				reader : new Ext.data.JsonReader({}, [{
					name : 'scatId',
					type : 'int'
				}, {
					name : 'scatName',
					type : 'string'
				}, {
					name : 'path',
					type : 'string'
				}])
			});
	    	
			
			var cat_Combox = new Ext.form.ComboBox({
				fieldLabel : "上级目录",
				mode : 'remote',
				store : catalog_store_select,
				hiddenName : 'parentCatalogId',
				emptyText : '请选择上级目录',
				triggerAction : 'all',
				valueField : 'scatId',
				displayField : 'scatName',
				selectOnFocus : true,
				anchor : '90%',
				editable : false,
				allowBlank : false,
				blankText : '上级目录不能为空！',
				listeners : {
					'change' : function(object) {
						//获取对应的path
						var val = object.value;
						var y = cat_Combox.getStore().query("scatId",val);
						if(y.getCount() == 1){
							expendOldPath = y.items[0].json.path;
						}
						//刷新目录树
						refreshTree();
					},
					'focus' : function(){
						catalog_store_select.reload();
					}
				}
			});
	    	
	    	var name_Add = new Ext.form.TextField({
				fieldLabel : '目录名称',
				id : 'name_Add',
				name : 'name_Add',
				anchor : '90%',
				allowBlank : false,
				blankText : '目录名称不能为空!',
				maxLength : 15,
				maxLengthText : '目录名称不得超过15个字符!'
			});
		
			var desc_Add = new Ext.form.TextField({
				fieldLabel : '目录描述',
				id : 'desc_Add',
				name : 'desc_Add',
				anchor : '90%',
				allowBlank : false,
				blankText : '目录描述不能为空!',
				maxLength : 256,
				maxLengthText : '目录描述不得超过256个字符!'
			});
		
			var serialNumber_add = new Ext.form.NumberField({
				id : 'serialNumber_add',
				name : 'serialNumber',
				fieldLabel : '排序编码',
				allowDecimals : false, // 不允许输入小数
				nanText : '请输入有效整数', // 无效数字提示
				allowNegative : false,
				anchor : '90%',
				maxLength : 6,
				emptyText : 100,
				maxLengthText : '排序号最大长度不能超过6位数！'
			});
			
	    	var catalogForm = new Ext.form.FormPanel({
				frame 		: false,
				labelAlign	: 'right', 
				buttonAlign : 'center',
				border:false,
				bodyStyle : 'background-color:#dfe8f6;padding:10px 0px 0',
				autoScroll : true,
				labelWidth : 100,
				layout   : 'hbox',
	    		layoutConfig: {
	                align:'middle',
	                pack:'center'
	            },
	            items : [{
	            	xtype:'fieldset',
	            	layout:'form',
		        	width:500,
		        	buttonAlign:'center',
		        	title:'目录管理',
		        	items:[cat_Combox,name_Add,desc_Add,serialNumber_add],
		        	buttons: [{
				        text: '保存[新增]',
				        handler:function(){
				        	isEditing = false;
				        	saveForm();
				        }
				    },{
				        text: '保存[编辑]',
				        handler:function(){
				        	if(selectCatalogId == -1){
	        					alertWarring("请在左边目录中选择需要编辑的目录！");
        						return ;
	        				}
				        	isEditing = true;
				        	saveForm();
				        }
				    },{
				        text: '清&nbsp;&nbsp;空',
				        handler:function(){
				        	// 展开路径
				        	expendOldPath = "/0";
				        	// 刷新列表
							refreshTree();
							// 清空表单
							clearCatTable();
				        }
				    },{
				        text: '删&nbsp;除',
				        handler:function(){
				        	if(selectCatalogId == -1){
	        					alertWarring("请在左边目录中选择需要删除的目录！");
        						return ;
	        				}
	        				Ext.MessageBox.show({
				                    title: '提示',
							        msg: "是否要删除选中的目录？",
							        width:300,
							        buttons: Ext.MessageBox.YESNO,
							        fn: function(e) {
							        	if(e == 'yes') {
							        		delCatalog();
							        	}
							        },
							        icon : Ext.MessageBox.QUESTION
				               });
				        }
				    }]
	            }]
	    	});
	    	
	    	function saveForm() {
				if(catalogForm.form.isValid()) {
					catalogForm.getForm().submit({
						url : '<%=path%>/console/data/usercenter/adminManage/saveCatalog.htm',
						method : 'post',
						params : {
							isEditing : isEditing,
							selectCatalogId : selectCatalogId
						},
						waitMsg : '正在保存数据，请稍候...',
						success : function(form, action) {
							// 提示信息
							alertSuccess(action.result.msg);
							// 刷新列表
							refreshTree();
							// 清空表单
							clearCatTable();
						},
						failure : function(form, action) {
							alertError(action.result.msg);
						}
					});
				}
			}
	    	
			function delCatalog(){
				Ext.Ajax.request({
					url : '<%=path%>/console/data/usercenter/adminManage/delCatalog.htm',
					params : {
						selectCatalogId : selectCatalogId
					},
					success : function(response, options) {
						var txt = Ext.util.JSON.decode(response.responseText);
						if(txt.success) {
							alertSuccess(txt.msg);
							refreshTree();
							// 清空表单
							clearCatTable();
						}else {
							alertError(txt.msg);
						}
					},
					failure : function(form, action) {
						alertError('系统忙，请稍后访问！');
					}
				});
			}
	    	
	    	//刷新页面
	    	function refreshTree(){
	    		rootNode.reload();
	    		if(expendOldPath != null){
		    		leftPanel.expandPath(expendOldPath);
	    		}
	    	}
	    	
	    	function clearCatTable(){
	    		catalogForm.form.reset();
				selectCatalogId = -1;
	    	}
	    	
	    	// ------------------------  目录管理 End -----------------------------------
	    	
	    	
	    	// ------------------------  功能点管理 Begin ---------------------------------
	    	
	    	var fun_store_select = new Ext.data.Store({
				autoLoad : false,
				proxy : new Ext.data.HttpProxy({
					url : '<%=path%>/console/data/usercenter/adminManage/initCatalogJsonCombox.htm'
				}),
				reader : new Ext.data.JsonReader({}, [{
					name : 'scatId',
					type : 'int'
				}, {
					name : 'scatName',
					type : 'string'
				}, {
					name : 'path',
					type : 'string'
				}])
			});
	    	
			
			var fun_Combox = new Ext.form.ComboBox({
				fieldLabel : "上级目录",
				mode : 'remote',
				store : fun_store_select,
				hiddenName : 'parentCatalogId',
				emptyText : '请选择上级目录',
				triggerAction : 'all',
				valueField : 'scatId',
				displayField : 'scatName',
				selectOnFocus : true,
				anchor : '90%',
				editable : false,
				allowBlank : false,
				blankText : '上级目录不能为空！',
				listeners : {
					'change' : function(object) {
						//获取对应的path
						var val = object.value;
						var y = fun_Combox.getStore().query("scatId",val);
						if(y.getCount() == 1){
							expendOldPath = y.items[0].json.path;
						}
						//刷新目录树
						refreshTree();
					},
					'focus' : function(){
						fun_store_select.reload();
					}
				}
			});
			
			
	    	var funName_Add = new Ext.form.TextField({
				fieldLabel : '功能点名称',
				id : 'funName_Add',
				name : 'funName_Add',
				anchor : '90%',
				allowBlank : false,
				blankText : '功能点名称不能为空!',
				maxLength : 30,
				maxLengthText : '功能点名称不得超过30个字符!'
			});
			
	    	var funDes_Add = new Ext.form.TextField({
				fieldLabel : '功能点描述',
				id : 'funDes_Add',
				name : 'funDes_Add',
				anchor : '90%',
				allowBlank : false,
				blankText : '功能点描述不能为空!',
				maxLength : 100,
				maxLengthText : '功能点描述不得超过100个字符!'
			});
	    	
	    	var funUrl_Add = new Ext.form.TextField({
				fieldLabel : '功能点URL',
				id : 'funUrl_Add',
				name : 'funUrl_Add',
				anchor : '90%',
				allowBlank : false,
				blankText : '功能点URL不能为空!',
				maxLength : 256,
				maxLengthText : '功能点URL不得超过256个字符!'
			});
	    	
	    	var funSerial_add = new Ext.form.NumberField({
				id : 'funSerial_add',
				name : 'funSerial_add',
				fieldLabel : '排序',
				allowDecimals : false, // 不允许输入小数
				nanText : '请输入有效整数', // 无效数字提示
				allowNegative : false,
				anchor : '90%',
				maxLength : 6,
				emptyText : 100,
				maxLengthText : '排序号最大长度不能超过6位数！'
			});
	    	
	    	var funHidden_add = new Ext.form.RadioGroup({
	    		id : 'funHidden_add',
	    		name : 'funHidden_add',
	    		fieldLabel : '是否隐蔽功能点',
	    		anchor : '50%',
	    		items : [{
	    			boxLabel : '否', 
	    			name : 'isHidden', 
	    			inputValue : 1,
	    			checked : true
	    			},{
	    			boxLabel : '是', 
	    			name : 'isHidden',
	    			inputValue : 0
	    			}
	    		]
	    	});
	    	
	    	//上级目录，功能点名称，功能点描述，功能点URL，排序，功能点编码，是否隐蔽功能点
	    	var funForm = new Ext.form.FormPanel({
				frame 		: false,
				labelAlign	: 'right', 
				buttonAlign : 'center',
				border:false,
				bodyStyle : 'background-color:#dfe8f6;padding:10px 0px 0',
				autoScroll : true,
				labelWidth : 100,
				layout   : 'hbox',
	    		layoutConfig: {
	                align:'middle',
	                pack:'center'
	            },
	            items : [{
	            	xtype:'fieldset',
	            	layout:'form',
		        	width:500,
		        	buttonAlign:'center',
		        	title:'功能点管理',
		        	items:[fun_Combox,funName_Add,funDes_Add,funUrl_Add,funSerial_add,funHidden_add],
		        	buttons: [{
				        text: '保存[新增]',
				        handler:function(){
				        	isFunEditing = false;
				        	saveFunForm();
				        }
				    },{
				        text: '保存[编辑]',
				        handler:function(){
				        	if(selectFunId == -1){
	        					alertWarring("请在左边目录中选择需要编辑的功能点！");
        						return ;
	        				}
				        	isFunEditing = true;
				        	saveFunForm();
				        }
				    },{
				        text: '清&nbsp;&nbsp;空',
				        handler:function(){
				        	// 展开路径
				        	expendOldPath = "/0";
				        	// 刷新列表
							refreshTree();
							// 清空表单
							clearFunTable();
				        }
				    },{
				        text: '删&nbsp;除',
				        handler:function(){
				        	if(selectFunId == -1){
	        					alertWarring("请在左边目录中选择需要删除的功能点！");
        						return ;
	        				}
	        				Ext.MessageBox.show({
				                    title: '提示',
							        msg: "是否要删除选中的功能点？",
							        width:300,
							        buttons: Ext.MessageBox.YESNO,
							        fn: function(e) {
							        	if(e == 'yes') {
							        		delFun();
							        	}
							        },
							        icon : Ext.MessageBox.QUESTION
				               });
				        }
				    }]
	            }]
	    	});
	    	
	    	
	    	
	    	function clearFunTable(){
	    		funForm.form.reset();
				selectFunId = -1;
	    	}
	    	
	    	function saveFunForm() {
				if(funForm.form.isValid()) {
					funForm.getForm().submit({
						url : '<%=path%>/console/data/usercenter/adminManage/saveFunction.htm',
						method : 'post',
						params : {
							isFunEditing : isFunEditing,
							selectFunId : selectFunId
						},
						waitMsg : '正在保存数据，请稍候...',
						success : function(form, action) {
							// 提示信息
							alertSuccess(action.result.msg);
							// 刷新列表
							refreshTree();
							// 清空表单
							clearFunTable();
						},
						failure : function(form, action) {
							alertError(action.result.msg);
						}
					});
				}
			}
	    	
	    	function initFun(funId){
	    		Ext.Ajax.request({
					url : '<%=path%>/console/data/usercenter/adminManage/initFun.htm',
					params : {
						funId : funId
					},
					success : function(response, options) {
						var txt = Ext.util.JSON.decode(response.responseText);
						if(txt.success) {
							fun_store_select.load({callback : function(){
								fun_Combox.setValue(txt.parentCatalogId);
							}});
							funName_Add.setValue(txt.funName_Add);
							funDes_Add.setValue(txt.funDes_Add);
							funUrl_Add.setValue(txt.funUrl_Add);
							funSerial_add.setValue(txt.funSerial_add);
							if(txt.isHidden){
								funHidden_add.setValue(1);
							}else{
								funHidden_add.setValue(0);
							}
						}else {
							alertError(txt.msg);
						}
					},
					failure : function(form, action) {
						alertError('系统忙，请稍后访问！');
					}
				});
	    	}
	    	
	    	function delFun(){
				Ext.Ajax.request({
					url : '<%=path%>/console/data/usercenter/adminManage/delFun.htm',
					params : {
						selectFunId : selectFunId
					},
					success : function(response, options) {
						var txt = Ext.util.JSON.decode(response.responseText);
						if(txt.success) {
							alertSuccess(txt.msg);
							refreshTree();
							// 清空表单
							clearFunTable();
						}else {
							alertError(txt.msg);
						}
					},
					failure : function(form, action) {
						alertError('系统忙，请稍后访问！');
					}
				});
			}
	    	
	    	
	    	// ------------------------  功能点管理 End -----------------------------------
	    	
	    	
	    	var tabPanel = new Ext.TabPanel({
				id : 'center_tab',
				region : 'center',
				margins : '5 5 5 0',
				resizeTabs : true,
				enableTabScroll : true,
				autoScroll : true,
				autoShow : true,
				activeTab : 0,
				items : [{
					id : 'tab_first',
					title : '功能目录管理',
					autoWidth : true,
					bodyBorder : true,
					layout : 'fit',
					items : catalogForm
				},{
					id : 'tab_second',
					title : '功能点管理',
					autoWidth : true,
					bodyBorder : false,
					layout : 'fit',
					items : funForm
				}]
			});
	    	
	    	
	    	
	    	new Ext.Viewport({
				renderTo : 'mainDiv',
				layout : 'border',
				items : [leftPanel,tabPanel]
			});
	    	

        
       });
  </script>
	</head>
	<body>
		<div id="mainDiv"></div>
	</body>
</html>
