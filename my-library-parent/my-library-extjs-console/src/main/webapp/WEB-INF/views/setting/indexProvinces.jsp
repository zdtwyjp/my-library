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
    var LIMIT = <%=ConstantsObj.DEFAULT_PAGE_SIZE%>;
	Ext.onReady(function()
	{
		// 如果要体现FORM效验效果，必须添加下面两行代码
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		var add_Win = null;
		var edit_Win = null;
		var isEditing = false;
		var searchCondition = null;
		
		var workerRecords = new Ext.data.Record.create([
			{name:'id', type:'string'},
			{name:'name', type:'string'},
			{name:'serialNumber', type:'string'},
			{name:'shortName', type:'string'}
		]);
		
		// 页面数据仓库
	    var store = new Ext.data.Store({
	        reader: new Ext.data.JsonReader({ //使用JSON传输入数据
                root : 'root',
                totalProperty : 'totalProperty'           // 定义根节点和分页总条数属性，root与totalProperty已经在CommonUtil中包含，这里只需要在页面属性添加即可
            }, workerRecords),
	        proxy:new Ext.data.HttpProxy({url:'<%=path%>/console/data/setting/initProvinces.htm'}),
	        sortInfo: {field:'createTime', direction:'DESC'},
	        remoteStore:true,    // 是否远程调用数据
	        remoteSort:true      // 是否远程排序
	    });
	    
		// 加载数据
 		store.load({params:{start:0, limit:LIMIT}});
 		store.on('beforeload',function(store, options){
 	 	     Ext.apply(this.baseParams, 
 	 	     	{ 
 	 	    		searchCondition: Ext.getCmp('searchCondition').getValue(), 
 	 	    		start : pagingToolBar.cursor, 
 	 	    	 	limit:LIMIT
 	 	    	 }); 
 			});
 		
 		
 		
 		

		var sm = new Ext.grid.CheckboxSelectionModel(); 
		
	    var cm = new Ext.grid.ColumnModel([
	    	new Ext.grid.RowNumberer(),
	    	sm,{
	    		id:'name', 
	    		header: '省（市）', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'name'
            },{
	    		id:'shortName', 
	    		header: '简称', 
	    		width: 150, 
	    		sortable: true,
	    		dataIndex:'shortName'
            },{
	    		id:'serialNumber', 
	    		header: '排序列', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'serialNumber'
            }
	    ]);
		
		var pagingToolBar = new Ext.PagingToolbar({
	            pageSize:LIMIT,
		        store:store,
		        displayInfo: true,
       		    displayMsg: '<%=ConstantsObj.PAGING_TOOLBAR_DISPLAY_MSG%>',
   			    emptyMsg: "没有记录",
   			    plugins: new Ext.ux.ProgressBarPager()
	        });
	    // GRID构造
	    var grid = new Ext.grid.GridPanel({
			border: false,
			region : 'center',
			columnLines:true,
			loadMask:true,
    		store: store,
    		frame:false,
    		sm: sm,
    		cm:cm,
 			autoScroll:true,
 			viewConfig : {
				forceFit : true
			},
			enableColumnHide : false,
			tbar: new Ext.Toolbar({
        		items:[
        			'省(市):',{
        				xtype:'textfield',
        				emptyText:'请输入省(市)',
        				id:'searchCondition',
        				width:180,
        				listeners : {
							'specialkey' : function(field, e) {
								if(e.getKey() == Ext.EventObject.ENTER) {
									searchMan();
								}
							}
						}
        			},'-',{
        				text: '&nbsp;查&nbsp;询&nbsp;',
        				enableToggle: false,
        				iconCls:'common_search',
        				handler: function(){
        					searchMan();
        				}
        			},'-',{
        				text: '&nbsp;新&nbsp;增&nbsp;',
        				enableToggle: false,
        				iconCls:'common_add',
        				handler: addFun
        			},'-',{
        				text: '&nbsp;编&nbsp;辑&nbsp;',
        				enableToggle: false,
        				iconCls:'common_edit',
        				handler: editFun
        			},'-',{
		             	text: '&nbsp;删&nbsp;除&nbsp;',
        				enableToggle: false,
        				iconCls:'common_delete',
        				handler:function(){
        					 snIds="";
        				     var rows=grid.getSelectionModel().getSelections();
        				     for(var i=0;i<rows.length;i++){
        				          var result=rows[i];
        				          snIds+=result.get('id')+" ";
        				     }
        				     if(snIds !=""){
        				     		Ext.util.Format.trim(snIds);
        				              Ext.MessageBox.show({
        				                    title: '提示',
									        msg: "是否要删除选中项？",
									        width:300,
									        buttons: Ext.MessageBox.YESNO,
									        fn: function(e) {
									        	if(e == 'yes') {
									        		deleteFun(snIds);
									        	}
									        },
									        icon : Ext.MessageBox.QUESTION
        				              });
        				     }else{
        				    	 alertWarring("请选择需要删除项！");
        				     }		
        				}
        			}
        		]
        	}),
	        bbar: pagingToolBar
	    });
	    
	    function searchMan() {
			searchCondition = Ext.getCmp('searchCondition').getValue()
			store.load({
				params : {
					start : 0,
					limit : LIMIT,
					start : pagingToolBar.cursor,
					searchCondition : searchCondition
				}
			});
		}
	     
	      
		new Ext.Viewport({
			renderTo : 'mainDiv',
			layout   : 'border',
			items 	 : [grid]
		});
		
		
		var name_Add = new Ext.form.TextField({
			fieldLabel : '省(市)',
			id : 'name_Add',
			name : 'name_Add',
			anchor : '90%',
			allowBlank : false,
			blankText : '省(市)名称不能为空!',
			maxLength : 30,
			maxLengthText : '省(市)名称不得超过30个字符!'
		});
		
		var shortname_Add = new Ext.form.TextField({
			fieldLabel : '简称',
			id : 'shortname_Add',
			name : 'shortname_Add',
			anchor : '90%',
			allowBlank : false,
			blankText : '简称不能为空!',
			maxLength : 30,
			maxLengthText : '简称不得超过30个字符!'
		});
		
		var serialNumber_add = new Ext.form.NumberField({
			id : 'serialNumber_add',
			name : 'serialNumber',
			fieldLabel : '排序',
			allowDecimals : false, // 不允许输入小数
			nanText : '请输入有效整数', // 无效数字提示
			allowNegative : false,
			emptyText : 1,
			anchor : '90%',
			maxLength : 6,
			maxLengthText : '排序号最大长度不能超过6位数！'
		});
		
		var add_Form = new Ext.form.FormPanel({
			id : 'add_Form',
			border : false,
			frame : false,
			waitMsgTarget : true,
			labelAlign : 'right',
			bodyStyle : 'background-color:#dfe8f6;padding:10px 0px 0',
			labelWidth : 100,
			buttonAlign : 'center',
			defaulttype : 'textfield',
			items : [name_Add,shortname_Add,serialNumber_add],
			buttons : [{
				text : '保&nbsp;存',
				handler : saveForm
			}, {
				text : '取&nbsp;消',
				handler : function() {
					if(isEditing)
						edit_Win.hide();
					else
						add_Win.hide();
				}
			}]
		});
		
		function saveForm() {
			if(add_Form.form.isValid()) {
				add_Form.getForm().submit({
					url : '<%=path%>/console/data/setting/saveProvinces.htm',
					method : 'post',
					params : {
						isEditing : isEditing,
						sn : isEditing == true ? grid.getSelectionModel().getSelected().json.id : 0
					},
					waitMsg : '正在保存数据，请稍候...',
					success : function(form, action) {
						// 提示信息
						alertSuccess(action.result.msg);
						// 刷新列表
						searchMan();
						// 隐蔽窗口
						if(isEditing){
							edit_Win.hide();
						}else{
							add_Win.hide();
						}
					},
					failure : function(form, action) {
						alertError(action.result.msg);
					}
				});
			}
		}
		
		function addFun(){
			if(!add_Win) {
				add_Win = new Ext.Window({
					closeAction : 'hide',
					maximizable : false,
					resizable : false,
					width : 400,
					height : 180,
					title : '新增省(市)',
					layout : 'fit',
					modal : true,
					buttonAlign : 'center',
					items : [add_Form],
					listeners : {
						hide : function() {
							add_Form.form.reset();
						}
					}
				});
			}
			add_Win.add(add_Form);
			add_Win.show();
		}
		
		function editFun(){
			var selectRow = grid.getSelectionModel().getSelected();
			if(selectRow) {
				isEditing = true;
				if(!edit_Win) {
					edit_Win = new Ext.Window({
						closeAction : 'hide',
						width : 400,
						height : 180,
						title : '编辑省(市)',
						layout : 'fit',
						maximizable : false,
						modal : true,
						resizable : false,
						buttonAlign : 'center',
						items : [add_Form],
						listeners : {
							hide : function() {
								isEditing = false;
								add_Form.form.reset();
							}
						}
					});
				}
				edit_Win.add(add_Form);
				
				add_Form.getForm().findField('name_Add').setValue(selectRow.json.name);
				add_Form.getForm().findField('serialNumber').setValue(selectRow.json.serialNumber);
				add_Form.getForm().findField('shortname_Add').setValue(selectRow.json.shortName);
				
				edit_Win.show();
			}else
				alertWarring('请至少选择一行数据然后才能够操作！');
		}
		
		
		function deleteFun(snIds){
			Ext.Ajax.request({
				url : '<%=path%>/console/data/setting/delProvinces.htm',
				params : {
					snIds : snIds
				},
				success : function(response, options) {
					var txt = Ext.util.JSON.decode(response.responseText);
					if(txt.success) {
						alertSuccess(txt.msg);
						searchMan();
					}else {
						alertError(txt.msg);
					}
				},
				failure : function(form, action) {
					alertError('系统忙，请稍后访问！');
				}
			});
		}
			
		

		

	});
	</script>

  </head>
  
  <body>
      <div id="mainDiv"></div>
  </body>
</html>