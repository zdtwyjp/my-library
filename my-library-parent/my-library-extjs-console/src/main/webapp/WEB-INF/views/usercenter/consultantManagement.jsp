<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <%@ include file="../common/extInclude.jsp" %>
   
  <script type="text/javascript" src="<c:url value='/viewsjs/common/sha1.js'/>"></script>
    <script type="text/javascript">
    var LIMIT = <%=ConstantsObj.DEFAULT_PAGE_SIZE%>;
	Ext.onReady(function()
	{
		// 如果要体现FORM效验效果，必须添加下面两行代码
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		var consultantAdd_Win = null;
		
		var workerRecords = new Ext.data.Record.create([
			{name:'SN', type:'string'},
			{name:'name', type:'string'},
			{name:'loginName', type:'string'},
			{name:'idcardNumber', type:'string'},
			{name:'mailAddress', type:'string'},
			{name:'city', type:'string'},
			{name:'creditCount', type:'string'},
			{name:'appointedCount', type:'string'},
			{name:'status', type:'string'},
			{name:'isAuthentication', type:'string'},
			{name:'mobilePhone', type:'string'},
			{name:'updateTime', type:'string'},
			{name:'createTime', type:'string'},
			{name:'consultantType', type:'string'}
		]);
		
		// 页面数据仓库
	    var store = new Ext.data.Store({
	        reader: new Ext.data.JsonReader({ //使用JSON传输入数据
                root : 'root',
                totalProperty : 'totalProperty'           // 定义根节点和分页总条数属性，root与totalProperty已经在CommonUtil中包含，这里只需要在页面属性添加即可
            }, workerRecords),
	        proxy:new Ext.data.HttpProxy({url:'<%=path%>/console/data/usercenter/consultantManage/initAllConsultants.htm'}),
	        sortInfo: {field:'updateTime', direction:'DESC'},
	        remoteStore:true,    // 是否远程调用数据
	        remoteSort:true      // 是否远程排序
	    });
	    
		// 加载数据
 		store.load({params:{start:0, limit:LIMIT}});
 	    store.on('beforeload',function(store, options){
 	     Ext.apply(this.baseParams, 
 	     	{ condition: Ext.getCmp('searchCondition').getValue(), start:0, limit:LIMIT}); 
		});
		var sm = new Ext.grid.CheckboxSelectionModel(); 
	  
	    
	    var cm = new Ext.grid.ColumnModel([
	    	new Ext.grid.RowNumberer(),
	    	sm,{
	    		id:'mobilePhone', 
	    		header: '手机号', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'mobilePhone'
            },{
	    		id:'name', 
	    		header: '真实姓名', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'name'
            },{
	    		id:'status', 
	    		header: '状态', 
	    		width: 150, 
	    		sortable: true, 
	    		renderer : checkState,
	    		dataIndex:'status'
            },{
	    		id:'consultantType', 
	    		header: '类型', 
	    		width: 150, 
	    		sortable: true, 
	    		renderer : checkConsultantType,
	    		dataIndex:'consultantType'
            },{
	    		id:'isAuthentication', 
	    		header: '是否认证', 
	    		width: 150, 
	    		sortable: true, 
	    		renderer : checkAuthentication,
	    		dataIndex:'isAuthentication'
            },{
	    		id:'idcardNumber', 
	    		header: '身份证号', 
	    		width: 120, 
	    		sortable: true, 
	    		dataIndex:'idcardNumber'
            },{
	    		id:'mailAddress', 
	    		header: '邮箱', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'mailAddress'
            },{
	    		id:'city', 
	    		header: '城市', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'city'
            },{
	    		id:'creditCount', 
	    		header: '积分(分)', 
	    		width: 90, 
	    		sortable: true, 
	    		dataIndex:'creditCount'
            },{
	    		id:'appointedCount', 
	    		header: '预约数(次)', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'appointedCount'
            },{
	    		id:'createTime', 
	    		header: '创建时间', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'createTime'
            },{
	    		id:'updateTime', 
	    		header: '更新时间', 
	    		width: 150, 
	    		sortable: true, 
	    		dataIndex:'updateTime'
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
        			'用户:','-',{
        				xtype:'textfield',
        				emptyText:'请输入姓名或者手机号',
        				id:'searchCondition',
        				width:180,
        				listeners : {
							'specialkey' : function(field, e) {
								if(e.getKey() == Ext.EventObject.ENTER) {
									store.load({params:{ condition: Ext.getCmp('searchCondition').getValue(), start:pagingToolBar.cursor, limit:LIMIT}});
								}
							}
						}
        			},'-',{
        				text: '&nbsp;查&nbsp;询&nbsp;',
        				enableToggle: false,
        				iconCls:'common_search',
        				handler: function(){
        					store.load({params:{ condition: Ext.getCmp('searchCondition').getValue(), start:pagingToolBar.cursor, limit:LIMIT}});
        				}
        			},'-',{
        				text: '启用',
        				enableToggle: false,
        				iconCls:'common_edit',
        				handler:function(){
        					 var userIds = "";
        				     var rows=grid.getSelectionModel().getSelections();
        				     if(rows.length < 1) {
        				    	 alertWarring("请选择需要启用的顾问！");
        				    	 return;
        				     }
        				     for(var i=0;i<rows.length;i++){
        				          var result=rows[i];
        				          userIds += result.get('SN')+" ";
        				     }
        				     if(userIds !=""){
        				     		Ext.util.Format.trim(userIds);
        				              Ext.MessageBox.show({
        				                    title: '提示',
									        msg: "是否启用被选中的顾问？",
									      //  width:300,
									        buttons: Ext.MessageBox.YESNO,
									        fn: function(e) {
									        	if(e == 'yes') {
									        		gridSubAjax('modifyState.htm?type=use', "userIds=" + userIds);
									        	}
									        },
									        icon : Ext.MessageBox.QUESTION
        				              });
        				     }else{
        				    	 alertWarring("请选择需要启用的顾问！");
        				     }		
        				}
        			},'-',{
        				text: '禁用',
        				enableToggle: false,
        				iconCls:'common_edit',
        				handler:function(){
        					 var userIds = "";
	       				     var rows=grid.getSelectionModel().getSelections();
	       				     if(rows.length < 1) {
	       				    	 alertWarring("请选择需要禁用的顾问！");
	       				    	 return;
	       				     }
	       				     for(var i=0;i<rows.length;i++){
	       				          var result=rows[i];
	       				          userIds += result.get('SN')+" ";
	       				     }
	       				     if(userIds !=""){
	       				     		Ext.util.Format.trim(userIds);
	       				              Ext.MessageBox.show({
	       				                    title: '提示',
										        msg: "是否禁用被选中的顾问？",
										      //  width:300,
										        buttons: Ext.MessageBox.YESNO,
										        fn: function(e) {
										        	if(e == 'yes') {
										        		gridSubAjax('modifyState.htm?type=unuse', "userIds=" + userIds);
										        	}
										        },
										        icon : Ext.MessageBox.QUESTION
	       				              });
	       				     }else{
	       				    	 alertWarring("请选择需要禁用的顾问！");
	       				     }		
	       				}		
        			},'-',{
        				text: '换号',
        				enableToggle: false,
        				iconCls:'common_edit',
        				handler:function(){
        					 var userIds = "";
	       				     var rows = grid.getSelectionModel().getSelections();
	       				     if(rows.length < 1) {
	       				    	 alertWarring("请选择需要换号的顾问！");
	       				    	 return;
	       				     }else if(rows.length > 1) {
	       				    	 alertWarring("一次只能对一个顾问进行换号！");
	       				    	 return;
	       				     }
	       				     changeConsultantPhoneNumber();
	       				}		
        			},'-',{
		             	text: '&nbsp;删&nbsp;除&nbsp;',
        				enableToggle: false,
        				iconCls:'common_delete',
        				handler:function(){
        					 userIds="";
        				     var rows=grid.getSelectionModel().getSelections();
        				     for(var i=0;i<rows.length;i++){
        				          var result=rows[i];
        				          userIds+=result.get('SN')+" ";
        				     }
        				     if(userIds !=""){
        				     		Ext.util.Format.trim(userIds);
        				              Ext.MessageBox.show({
        				                    title: '提示',
									        msg: "是否要删除选中的顾问？",
									        width:300,
									        buttons: Ext.MessageBox.YESNO,
									        fn: function(e) {
									        	if(e == 'yes') {
									        		gridSubAjax('deleteConsultant.htm', "userIds=" + userIds);
									        	}
									        },
									        icon : Ext.MessageBox.QUESTION
        				              });
        				     }else{
        				    	 alertWarring("请选择需要删除的顾问！");
        				     }		
        				}
        			}

        		]
        	}),
	        bbar: pagingToolBar
	    });
	     
	      
		var displayPanel = new Ext.Viewport({
			renderTo : 'mainDiv',
			layout   : 'border',
			items 	 : [grid]
		});
			
		function gridSubAjax(action,param){
			Ext.Ajax.request({
				url : '<%=path%>/console/data/usercenter/consultantManage/' + action, 
				params : param,
				method : 'POST',
				success: function (result, request) 
				{
					var msg = result.responseText;
					alertInformation(msg);
					store.reload({params:{ condition: Ext.getCmp('searchCondition').getValue(), start:pagingToolBar.cursor, limit:LIMIT}});
				},
				failure: function ( result, request) {
					alertError("系统运行超时或执行失败");
				}
			});
		}
		
		
		var phone_add = new Ext.form.NumberField({
			id : 'phone_add',
			name : 'phone_add',
			fieldLabel : '手机号',
			allowDecimals : false, // 不允许输入小数
			nanText : '请输入有效手机号！', // 无效数字提示
			allowNegative : false,
			anchor : '90%',
			allowBlank : false,
			maxLength : 11,
			maxLengthText : '请输入有效手机号！'
		});
		
		var phone_add2 = new Ext.form.NumberField({
			id : 'phone_add2',
			name : 'phone_add2',
			fieldLabel : '再次输入手机号',
			allowDecimals : false, // 不允许输入小数
			nanText : '请输入有效手机号！', // 无效数字提示
			allowNegative : false,
			allowBlank : false,
			anchor : '90%',
			maxLength : 11,
			maxLengthText : '请输入有效手机号！'
		});
		
		var consultantAdd_Form = new Ext.form.FormPanel({
			id : 'consultantAdd_Form',
			border : false,
			frame : false,
			waitMsgTarget : true,
			labelAlign : 'right',
			bodyStyle : 'background-color:#dfe8f6;padding:10px 0px 0',
			labelWidth : 100,
			buttonAlign : 'center',
			defaulttype : 'textfield',
			items : [phone_add,phone_add2],
			buttons : [{
				text : '保&nbsp;存',
				handler : saveChangePhoneConsultant
			}, {
				text : '取&nbsp;消',
				handler : function() {
					consultantAdd_Win.hide();
				}
			}]
		});
		
		function saveChangePhoneConsultant() {
			if(consultantAdd_Form.form.isValid()) {
				var phoneV = phone_add.getValue();
				var phoneV2 = phone_add2.getValue();
 				if(phoneV != phoneV2){
 					alertWarring("两次输入的手机号不一致！");
 					return;
 				}
				consultantAdd_Form.getForm().submit({
					url : '<%=path%>/console/data/usercenter/consultantManage/saveChangePhoneConsultant.htm',
					method : 'post',
					params : {
						mobilePhone : phoneV,
						sn : grid.getSelectionModel().getSelected().json.SN
					},
					waitMsg : '正在保存数据，请稍候...',
					success : function(form, action) {
						// 提示信息
						alertSuccess(action.result.msg);
						// 刷新列表
						store.load({params:{ condition: Ext.getCmp('searchCondition').getValue(), start:pagingToolBar.cursor, limit:LIMIT}});
						// 隐蔽窗口
						consultantAdd_Win.hide();
					},
					failure : function(form, action) {
						alertError(action.result.msg);
					}
				});
			}
		}
		
		function changeConsultantPhoneNumber(){
			if(!consultantAdd_Win) {
				consultantAdd_Win = new Ext.Window({
					closeAction : 'hide',
					maximizable : false,
					resizable : false,
					width : 350,
					height : 170,
					title : '换号',
					layout : 'fit',
					modal : true,
					buttonAlign : 'center',
					items : [consultantAdd_Form],
					listeners : {
						hide : function() {
							consultantAdd_Form.form.reset();
						}
					}
				});
			}
			consultantAdd_Win.add(consultantAdd_Form);
			consultantAdd_Win.show();
		}
		
		

	//自定义函数
	function checkState(val) {
		if(val == '1')
			return '可用';
		else
			return '禁用';
	}
	
	function checkConsultantType(val){
		if(val == '1')
			return '内部员工';
		else if(val == '2')
			return '自由经纪人';
		else
			return '其它';
	}
	
	function checkAuthentication(val){
		if(val == '1')
			return '已认证';
		else if(val == '0')
			return '未认证';
		else 
			return '其它';
	}
	});
	</script>

  </head>
  
  <body>
      <div id="mainDiv"></div>
  </body>
</html>
