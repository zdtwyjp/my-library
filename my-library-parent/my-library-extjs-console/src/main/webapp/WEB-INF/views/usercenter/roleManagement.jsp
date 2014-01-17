<%@ page language="java" pageEncoding="UTF-8"%>
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
			{name:'srolId', type:'string'},
			{name:'srolName', type:'string'},
			{name:'srolCode', type:'string'},
			{name:'srolDesc', type:'string'},
			{name:'srolIsSysRole', type:'string'}
		]);
		
		// 页面数据仓库
	    var store = new Ext.data.Store({
	        reader: new Ext.data.JsonReader({ //使用JSON传输入数据
                root : 'root',
                totalProperty : 'totalProperty'           // 定义根节点和分页总条数属性，root与totalProperty已经在CommonUtil中包含，这里只需要在页面属性添加即可
            }, workerRecords),
	        proxy:new Ext.data.HttpProxy({url:'<%=basePath%>console/data/usercenter/roleManage/initAllRoles.htm'}),
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
	    
	    var cm = new Ext.grid.ColumnModel(
	    	[
	    		new Ext.grid.RowNumberer(),
	    	{
	    		id:'srolName', 
	    		header: '角色名', 
	    		width: 210, 
	    		sortable: true, 
	    		dataIndex:'srolName'
            },{
	    		id:'srolCode', 
	    		header: '角色编码', 
	    		width: 120, 
	    		sortable: true, 
	    		dataIndex:'srolCode'
            },{
	    		id:'srolIsSysRole', 
	    		header: '是否系统角色', 
	    		width: 120,
	    		align : 'center',
	    		sortable: true, 
	    		dataIndex:'srolIsSysRole'
            },{
	    		id:'srolDesc', 
	    		header: '角色职务描述', 
	    		width: 360, 
	    		sortable: true, 
	    		dataIndex:'srolDesc'
            }
	    ]);
		
		var managerEditForm = new Ext.form.FormPanel({
        	             border : false,
    					 frame	: true,
        				 labelAlign: 'right',
        				 buttonAlign: 'center',
        				 bodyStyle:'padding:5px 5px 0',
        				 labelWidth:100,
        				 autoScroll:true,
						 containerScroll: true,
        				 width: 350,
					     height: 150,
        				 items:[{
        				        xtype:'textfield',
					        	fieldLabel:'角色名',
        				        id:'srolName',
        				        name:'srolName',
				        		anchor:'90%',
				                blankText:'该字段不允许为空',
				                allowBlank: false
        				 },{
        				        xtype:'textfield',
					        	fieldLabel:'角色编码',
        				        id:'srolCode',
        				        name:'srolCode',
				        		anchor:'90%',
				                blankText:'该字段不允许为空',
				                allowBlank: false
        				 },{
        				        xtype:'textfield',
					        	fieldLabel:'角色职务描述',
        				        id:'srolDesc',
        				        name:'srolDesc',
				        		anchor:'90%',
				                allowBlank: true
        				 }]
        	   });
		
	    
	    // GRID构造
	    var grid = new Ext.grid.GridPanel({
			border: false,
			region : 'center',
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
			autoScroll:true,
			enableColumnHide : false,
			tbar: new Ext.Toolbar({
        		items:['角色名称：',
        			{
        				xtype:'textfield',
        				emptyText:'请输入角色名称',
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
        					managerEditForm.form.reset();
        					editRole('add');
        				}
        			},'-',{
        				text: '&nbsp;编&nbsp;辑&nbsp;',
        				enableToggle: false,
        				iconCls:'common_edit',
        				handler: function(){
        					var selectRow = grid.getSelectionModel().getSelected();
        					if(selectRow)
        					{
        						editRole(selectRow);
        					}
        					else
        					{
        						alertWarring("请选择需要编辑的角色!");
        					}
        				}
        			},'-',{
        				text: '&nbsp;删&nbsp;除&nbsp;',
        				enableToggle: false,
        				iconCls:'common_delete',
        				handler: function(){
        					var selectRow = grid.getSelectionModel().getSelected();
        					if(selectRow)
        					{
        						Ext.MessageBox.show({
								    title: 'INFORMATION',
								    msg: "确定是否要删除当前角色信息!",
								    width:300,
								    buttons: Ext.MessageBox.YESNO,
								    fn: function(e) {
								    		if(e == 'yes') {
								    			gridSubAjax(selectRow.get('srolId'));
								    		}
								    	},
								    icon : Ext.MessageBox.QUESTION
								});
        					}
        					else
        					{
        						Ext.MessageBox.show({
        							title: 'INFORMATION',
        							msg: "请选择需要删除的角色!",
        							width: 250,
        							buttons: Ext.MessageBox.OK,
        							buttonAlign: 'center'
        						});
        					}
        					
        				}
        			},'-',{
        				text: '分配权限',
        				enableToggle: false,
        				iconCls:'common_go', 
        				handler: function() {
        				var selectRow = grid.getSelectionModel().getSelected();
        					if(selectRow)
        					{
        						document.location.href='<%=basePath%>console/data/usercenter/roleFunManage/initPage.htm?roleId='+grid.getSelectionModel().getSelected().json.srolId;
        					}
        					else
        					{
        						alertWarring("请选择需要分配权限的角色!");
        					}
        				}      				
        			}]
        	}),
	        bbar: new Ext.PagingToolbar({
	            pageSize:LIMIT,
		        store:store,
		        displayInfo: true,
       		    displayMsg: '<%=ConstantsObj.PAGING_TOOLBAR_DISPLAY_MSG%>',
   			    emptyMsg: "没有记录",
   			    plugins: new Ext.ux.ProgressBarPager()
	        })
	    });
	    
	    function editRole(row){
	    	this.row = row;
	    	var that = this;
	         if(!editWin){
                editWin = new Ext.Window({
		        id: 'editWin',
				layout: 'fit',
				width: 400,
				height: 180,
				plain:false,
		        closeAction:'hide',
		        buttonAlign: 'center',
				maximizable:false,
				modal:true,
			    items :[managerEditForm],
			    buttons:[{
     				    text: '保存',
     				    handler:function(){
      				          if(managerEditForm.form.isValid()){
   				          	       managerEditForm.form.submit({
			                       method:'POST',
			                       waitMsg:'正在提交.....',
								   waitTitle:'请稍等',
			                       success:function(form, action){
			                      	  alertInformation(action.result.msg);
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
			                  if(that.row != "add") {
				                  loadData(row1);
							  } 
      				      }
     				  }]
  				});
				setTitleAndUrl(row, editWin);
				editWin.show();
              }else{
              	setTitleAndUrl(row, editWin);
				editWin.show();
              }
              if(row != 'add') {
              	loadData(row);
			  }	
	    }
	    
	    function loadData(row) {
              managerEditForm.form.setValues({
					srolName  : row.get('srolName'),
					srolDesc    : row.get('srolDesc'),
					srolCode  : row.get('srolCode') 
			});
	    }
	    
	    function setTitleAndUrl(row, win) {
        	if(row == 'add') {
         		win.setTitle('增加角色');
         		managerEditForm.form.url = '<%=basePath%>console/data/usercenter/roleManage/addRole.htm';
        	} else {
         		win.setTitle('修改角色');
          		managerEditForm.form.url = '<%=basePath%>console/data/usercenter/roleManage/modifyRole.htm?roleId='+row.get('srolId');
        	}
	    }
	    
	    
		var displayPanel = new Ext.Viewport({
			renderTo : 'mainDiv',
			layout   : 'border',
			items 	 : [grid]
		});

	    store.on('beforeload',function(store, options){
			Ext.apply(this.baseParams, 
 	     	{ condition: Ext.getCmp('searchCondition').getValue(), start:0, limit:LIMIT}); 
		});
	
			    
		function gridSubAjax(param)
		{
			Ext.Ajax.request({
				url : '<%=basePath%>console/data/usercenter/roleManage/deleteRole.htm?roleId=' + param, 
//				params : param,
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
