<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/views/common/extInclude.jsp" %>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp" %>
<script type="text/javascript" src="<c:url value='/viewsjs/common/sha1.js'/>"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript">
    var LIMIT = <%=ConstantsObj.DEFAULT_PAGE_SIZE%>;
	Ext.onReady(function()
	{
		// 如果要体现FORM效验效果，必须添加下面两行代码
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		var fm = Ext.form;
		
		var editWin;
		
		var workerRecords = new Ext.data.Record.create([
			{name:'smanId', type:'string'},
			{name:'smanName', type:'string'},
			{name:'smanLoginName', type:'string'},
			{name:'smanEmail', type:'string'},
			{name:'smanTel', type:'string'},
			{name:'smanRemark', type:'string'},
			{name:'smanIsStop', type:'string'},
			{name:'createTime', type:'string'},
			{name:'loginedTime', type:'string'},
			{name:'loginedCount', type:'string'},
			{name:'handledOrders', type:'string'},
			{name:'handlingOrders', type:'string'},
			{name:'roles', type:'string'}
		]);
		
		
		// 页面数据仓库
	    var store = new Ext.data.Store({
	        reader: new Ext.data.JsonReader({ //使用JSON传输入数据
                root : 'root',
                totalProperty : 'totalProperty'           // 定义根节点和分页总条数属性，root与totalProperty已经在CommonUtil中包含，这里只需要在页面属性添加即可
            }, workerRecords),
	        proxy:new Ext.data.HttpProxy({url:'<%=basePath%>console/data/usercenter/adminManage/initAllManagers.htm'}),
	        sortInfo: {field:'updateTime', direction:'DESC'},
	        remoteStore:true,    // 是否远程调用数据
	        remoteSort:true      // 是否远程排序
	    });
		
	    

		// 加载数据
	    store.load({params:{start:0, limit:LIMIT}});
	    store.on('beforeload',function(store, options){
	 	     Ext.apply(this.baseParams, 
	 	     	{ 
	 	    	 condition: Ext.getCmp('searchCondition').getValue(),
	 	    	 start : pagingToolBar.cursor,
	 	    	 limit:LIMIT
	 	    	 }); 
		});
		
		
	    
	    var cm = new Ext.grid.ColumnModel([
	    	new Ext.grid.RowNumberer(),
	    	{
	    		id:'smanName', 
	    		header: '用户姓名', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'smanName'
            },{
	    		id:'smanLoginName', 
	    		header: '登录名', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'smanLoginName'
            },{
	    		id:'smanEmail', 
	    		header: '邮箱', 
	    		width: 150, 
	    		sortable: true, 
	    		allowBlank: true,
	    		dataIndex:'smanEmail'
            },{
	    		id:'smanTel', 
	    		header: '联系电话', 
	    		width: 140, 
	    		sortable: true, 
	    		allowBlank: true,
	    		dataIndex:'smanTel'
            },{
            	xtype: 'booleancolumn',
	    		id:'smanIsStop', 
	    		falseText: '启用',
	    		trueText: '停用',
	    		header: '状态', 
	    		width: 70, 
	    		sortable: true, 
	    		allowBlank: true,
	    		dataIndex:'smanIsStop'
            },{
	    		id:'roles', 
	    		header: '管理员角色', 
	    		width: 150, 
	    		sortable: false, 
	    		dataIndex:'roles'
            },{
	    		id:'createTime', 
	    		header: '创建时间', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'createTime'
            },{
	    		id:'loginedTime', 
	    		header: '最后登录时间', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'loginedTime'
            },{
	    		id:'loginedCount', 
	    		header: '登录次数', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'loginedCount'
            },{
	    		id:'handledOrders', 
	    		header: '处理完订单数量', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'handledOrders'
            }
            ,{
	    		id:'handlingOrders', 
	    		header: '处理中订单数量', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'handlingOrders'
            },{
	    		id:'smanRemark', 
	    		header: '备注', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'smanRemark'
            }
            
	    ]);
	    
		
		var managerEditForm = new Ext.form.FormPanel({
       	             border : false,
   					 frame	: true,
       				 labelAlign: 'right',
       				 buttonAlign: 'center',
       				 bodyStyle:'padding:5px 5px 0',
       				 labelWidth:50,
       				 autoScroll:true,
					 containerScroll: true,
       				 width: 400,
				     height: 150,
       				 items:[{
       				        xtype:'textfield',
				        	fieldLabel:'账号',
       				        id:'userName',
       				        name:'userName',
			        		anchor:'90%',
			        		readOnly : true
       				 },{
       				        xtype:'textfield',
				        	fieldLabel:'新密码',
							inputType : 'password',
       				        id:'smanPwd',
       				        name:'smanPwd',
			        		anchor:'90%',
			                blankText:'该字段不允许为空',
			                allowBlank: false
       				 },{
       				        xtype:'textfield',
				    		hidden: true, 
							hideLabel:true, 
			        		id:'userId',
			        		name:'userId'
       				 }]
       	   });
		
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
			region : 'center',
			border: false,
			columnLines:true,
			loadMask:true,
    		store: store,
    		frame:false,
    		cm:cm,
    		sm: new Ext.grid.RowSelectionModel({
                singleSelect: true
            }),
            viewConfig : {
				forceFit : true
			},
			autoScroll:false,
			enableColumnHide : false,
			tbar: new Ext.Toolbar({
        		items:['用户姓名：',
        			{
        				xtype:'textfield',
        				emptyText:'请输入用户姓名',
        				id:'searchCondition',
        				width:150,
        				listeners : {
							'specialkey' : function(field, e) {
								if(e.getKey() == Ext.EventObject.ENTER) {
									store.load({params:{ condition: Ext.getCmp('searchCondition').getValue(), start:0, limit:LIMIT}});
								}
							}
						}
        			},'-',{
        				text: '&nbsp;查&nbsp;询&nbsp;',
        				enableToggle: false,
        				iconCls:'common_search',
        				handler: function(){
        					store.load({params:{ condition: Ext.getCmp('searchCondition').getValue(), start:0, limit:LIMIT}});
        				}
        			},'-',{
        				text: '&nbsp;新&nbsp;增&nbsp;',
        				enableToggle: false,
        				iconCls:'common_add',
        				handler: function(){
        					document.location.href="<%=basePath%>console/data/usercenter/adminManage/adminManagement.htm?type=addManager";
        				}
        			},'-',{
        				text: '&nbsp;编&nbsp;辑&nbsp;',
        				enableToggle: false,
        				iconCls:'common_edit',
        				handler: function(){
        					var selectRow = grid.getSelectionModel().getSelected();
        					if(selectRow)
        					{
        						if(selectRow.json.smanLoginName == 'root'){
        							alertWarring("【root】为系统内置管理员，不允许编辑基本信息和权限!");
        							return;
        						}
        						var userId = selectRow.get('smanId'); 
        						document.location.href="<%=basePath%>console/data/usercenter/adminManage/adminManagement.htm?type=editManager"
        							+ "&userId=" + userId;
        					}
        					else
        					{
        						alertWarring("请选择需要编辑的管理员!");
        					}
        				}
        			},'-',{
        				text: '停用/启用',
        				enableToggle: false,
        				iconCls:'common_delete',
        				handler: function(){
        					var selectRow = grid.getSelectionModel().getSelected();
        					if(selectRow)
        					{
        						if(selectRow.json.smanLoginName == 'root'){
        							alertWarring("【root】为系统内置管理员，不允许停用!");
        							return;
        						}
        						var msg = "是否要停用此管理员?";
        						//if(selectRow.json.smanLoginName == 'root'){
        						//	alertWarring("【root】为系统内置管理员，不允许删除!");
        						//	return;
        						//}
        						if(selectRow.json.smanIsStop === true) {
        							msg = "是否要启用此管理员?";
        						}
        						Ext.MessageBox.show({
								    title: 'INFORMATION',
								    msg: msg,
								    width:300,
								    buttons: Ext.MessageBox.YESNO,
								    fn: dropGM,
								    icon : Ext.MessageBox.QUESTION
								});
        					}
        					else
        					{
        						alertWarring("请选择需要停用/启用的管理员!");
        					}
        					
        				}
        			},'-',{
        			    text: '修改密码',
        				enableToggle: false,
        				iconCls:'common_go',
        				handler: function(){
        				    var selectRow = grid.getSelectionModel().getSelected();
        				    if(selectRow){
        				         editManagerPwd(selectRow);
        				    }else{
        				       alertWarring("请选择需要修改密码的管理员!");
        				    }
        				    
        				}
        			}
        		]
        	}),
	        bbar: pagingToolBar
	    });
	    
	    function dropGM(e)
	    {
	    	if(e == "yes")
	    	{
	    		var selectRow = grid.getSelectionModel().getSelected();
	    		var param = "userId=" + selectRow.get('smanId');
				gridSubAjax(param);
	    	}
	    }
	    
	    function editManagerPwd(row){
	         if(!editWin){
	                      var id = row.get('smanId');
			                   editWin = new Ext.Window({
								        id: 'editWin',
										layout: 'fit',
										width: 350,
										height: 160,
										plain:false,
				    			        closeAction:'hide',
				    			        buttonAlign: 'center',
										title: '修改密码',
										maximizable:false,
										modal:true,
									    items :[managerEditForm],
									    buttons:[{
			        				     text: '保存',
			        				     handler:function(){
			        				     	
				        				          if(managerEditForm.form.isValid()){
				        				          	       managerEditForm.form.submit({
				        				                   url:'<%=basePath%>console/data/usercenter/adminManage/modifyManagerPwd.htm?userId='+id,
									                       params:{
																newPassword:hex_sha1(Ext.getCmp('smanPwd').getValue())
														   },
									                       method:'POST',
									                       waitMsg:'正在提交.....',
														   waitTitle:'请稍等',
									                       success:function(form, action){
									                      	  alertWarring(action.result.msg);
									                      	  store.reload();
									                      	  editWin.hide();
									                       },
									                       failure:function(form, action){
									                          alertWarring(action.result.msg);
									                       }
				        				              });   
				        				          }else{
				        				               alertWarring("内容填写不完整");
				        				          }
				        				     }
				        				 },{
				        				      text:'重置',
				        				      handler:function(){
								                   managerEditForm.form.reset(); 
								                   var row1=grid.getSelectionModel().getSelected();
								                   managerEditForm.form.setValues({
														userName  : row1.get('smanLoginName')
														
												});
				        				      }
			        				  }]
				    				});
				    				editWin.show();
			              }else{
			                  editWin.show();
			              }
			                managerEditForm.form.reset();
			               managerEditForm.form.setValues({
									userName  : row.get('smanLoginName')
									
							});
	    
	    }
	    
		var displayPanel = new Ext.Viewport({
			renderTo : 'mainDiv',
			layout   : 'border',
			items 	 : [grid]
		});

	    store.on('beforeload',function(store, options){
			Ext.apply(this.baseParams, 
 	     	{ condition: Ext.getCmp('searchCondition').getValue(), start:pagingToolBar.cursor, limit:LIMIT}); 
		});
			    
		function gridSubAjax(param)
		{
			Ext.Ajax.request({
				url : '<%=basePath%>console/data/usercenter/adminManage/modifyAdminStatus.htm', 
				params : param,
				method : 'POST',
				success: function (result, request) 
				{
					var msg = result.responseText;
					alertInformation(msg);
					store.reload();
				},
				failure: function ( result, request) {
					alertError("系统运行超时或执行失败");
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
