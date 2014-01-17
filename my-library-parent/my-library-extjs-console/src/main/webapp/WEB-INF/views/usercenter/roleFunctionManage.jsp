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
		<link rel="stylesheet" type="text/css"
			href="<c:url value='/extjs/ux/css/RowEditor.css'/>" />
		<script type="text/javascript">
		var LIMIT = <%=ConstantsObj.DEFAULT_PAGE_SIZE%>;
		
       Ext.onReady(function(){
              //用户组传过来的id
       	      var funid= ${roleId}; 
       	
       	      //加载树
              var loader = new Ext.tree.TreeLoader({  
		        	dataUrl:'<%=basePath%>console/data/usercenter/roleFunManage/createTree.htm?nodeId=${id}'
		      });
		      //树的根结点
              var rootNode=new Ext.tree.AsyncTreeNode({
                    id:'0',
		      		draggable : false,
		      		expanded : true,
		      		text:'功能列表'
              });
              
              //左边的树菜单
              var leftPanel=new Ext.tree.TreePanel({ 
					    region 			 : 'west',
						title 			 : '系统目录项',
			        	border     		 : true,
			            split      		 : true,  
			        	animate    		 : true,  
			        	rootVisible		 : false,
			        	autoScroll 		 : true,
			        	autoHeight 		 : false,
			        	loader     		 : loader,                  
						root       	 	 : rootNode,
						width			 : 180
              });
             //在加载前重新传URL以及ID
             leftPanel.on('beforeload', function(node){
	    		leftPanel.loader.dataUrl = "<%=basePath%>/console/data/usercenter/roleFunManage/createTree.htm?nodeId=" + node.id;
	    	});
	    	rootNode.expand(false,false);
	    	//树的点击事件
    	    var id;
	    	leftPanel.on("click", function(node) {
	    		id=node.id;
	    		store.load({params:{start:0, limit:LIMIT, id: id}});
	    	}, leftPanel);

           //中间功能点展示的数据格式
            var userRecord=Ext.data.Record.create([{
               name: 'sfunId', type: 'string' 
             }, {
            	  name: 'sfunName', type: 'string' 
           	}]);	    	
         	var store =new Ext.data.Store({  //使用JSON传输入数据  	    	
        	       proxy: new Ext.data.HttpProxy({url:'<%=basePath%>/console/data/usercenter/roleFunManage/getAllFunctionsByCatalog.htm'}),
        	       reader:new Ext.data.JsonReader({
        	             totalProperty:'totalProperty',
        	             root:'root' 
        	       },userRecord),
        	      sortInfo: {field:'sfunId', direction:'ASC'},
        	      remoteStore:true    // 是否远程调用数据
        	 });
			
            store.on('beforeload',function(store, options){
				store.baseParams  = {
					id:id,
					start:0,
					limit:LIMIT	
				}
			});

               	
               	 //角色组功能点展示的数据格式
               var roleRecord=Ext.data.Record.create([{
                  name: 'sfunId', type: 'string' 
                }, {
               	  name: 'sfunName', type: 'string' 
               	}]);
               		
               	var cm=new Ext.grid.ColumnModel([
        	         new Ext.grid.RowNumberer(),
        	         {
        	           header:'功能点名称',
        	           dataIndex:'sfunName',
        	           id:'sfunName',
        	           width: 150, 
			    	   sortable: true
        	         }
        	    ]);
        	    

        	    
        	    var roleStore =new Ext.data.Store({  //使用JSON传输入数据  	    	
        	       proxy: new Ext.data.HttpProxy({url:'<%=basePath%>/console/data/usercenter/roleFunManage/getAllFunctionsByRole.htm?roleId='+funid}),
        	       reader:new Ext.data.JsonReader({
        	             totalProperty:'totalProperty',
        	             root:'root' 
        	       },roleRecord),
        	      sortInfo: {field:'sfunId', direction:'ASC'},
        	      remoteStore:true    // 是否远程调用数据
        	    });
        	    //加载数据
        	    roleStore.load({params:{start:0, limit:LIMIT}});
        	    

              //系统功能点面板
               var centerGrid=new Ext.grid.GridPanel({
                     title:'系统功能点',
				     border: true,
					 region : 'center',
					 store:store,
					 loadMask:true,
					 split:true,
					 autoScroll:true,
					 cm:cm,
					 enableDragDrop : true,
			    	 ddGroup: "centerGrid",
			         autoExpandColumn: 'sfunName',
					 viewConfig:{
					   forceFit:true
					 },
					 sm: new Ext.grid.RowSelectionModel({
			                singleSelect: false
			         }),
					 autoScroll:true/*,
					 bbar:new Ext.PagingToolbar({
					       pageSize:LIMIT,
					       store:store,
					       displayInfo: true,
	            		   displayMsg: '<%=ConstantsObj.PAGING_TOOLBAR_DISPLAY_MSG%>',
            			   emptyMsg: "没有记录",
            			   plugins: new Ext.ux.ProgressBarPager()
					 })*/
               }); 
              //角色组对应功能点面板
               var rightGrid=new Ext.grid.GridPanel({
               	         title:'【<%=request.getAttribute("roleName")%>】对应功能点',
				         border: true,
					     region : 'east',
					     store:roleStore,
					     loadMask:true,
					     split:true,
					     autoScroll:true,
					     cm:cm,
					     enableDragDrop : true,
					     //列表拖动
			    		 ddGroup: "rightGrid",
			    		 autoExpandColumn: 'sfunName',
					     viewConfig:{
					       forceFit:true
					     },
					     sm: new Ext.grid.RowSelectionModel({
			                singleSelect: false
			             }),
					     autoScroll:true,
					     width:360,
					    tbar: new Ext.Toolbar({
			        		items:[{
			        			text: '&nbsp;删&nbsp;除&nbsp;',
			        			enableToggle: false,
			        			iconCls:'common_delete',
			        			handler: function(){
			        				var rows=rightGrid.getSelectionModel().getSelections();
			        				if(rows.length>=1){
				        					Ext.MessageBox.show({
											title: 'INFORMATION',
											msg: "是否要从功能点中移除所选择的功能点？",
											width:300,
											buttons: Ext.MessageBox.YESNO,
											fn: removeRole,
											icon : Ext.MessageBox.QUESTION
										});
			        				}
        					        else{
        					            alertWarring("请选择需要删除的功能点");
        					        }		
			        			}
			        		},'-',{
						         text: '&nbsp;返&nbsp;回&nbsp;',
			        			 enableToggle: false,
			        			 iconCls:'common_back' ,
			        			 handler:function(){
			        			     document.location.href="<%=basePath%>console/data/usercenter/roleManage/roleManagement.htm"; 
			        			 }
						    }]
						})/*,
						bbar:new Ext.PagingToolbar({
					       pageSize:LIMIT,
					       store:roleStore,
					       displayInfo: true,
	            		   displayMsg: '<%=ConstantsObj.PAGING_TOOLBAR_DISPLAY_MSG%>',
            			   emptyMsg: "没有记录",
            			   plugins: new Ext.ux.ProgressBarPager()
					 })*/
              });
       	
       	      var displayPanel = new Ext.Viewport({
					renderTo : 'mainDiv',
					layout : 'border',
					items : [leftPanel,centerGrid,rightGrid]
				});
			//拖动事件	
		     var roleFunctionGridEL = rightGrid.getEl();
		     var ddrow = new Ext.dd.DropTarget(roleFunctionGridEL, 
				{
					ddGroup : 'centerGrid',
					copy : true,
					notifyDrop : function(ddSource, e, data)
					{	
							Ext.MessageBox.show({
								title: 'INFORMATION',
								msg: "确定需要将所选功能点移动到该角色组功能点？",
								width:300,
								buttons: Ext.MessageBox.YESNO,
								fn: changeMoveRole,
								icon : Ext.MessageBox.QUESTION
							});
					}
				});
			function changeMoveRole(e){
			     if(e=="yes"){
			         var rows=centerGrid.getSelectionModel().getSelections();
			         var tmp="";
			         var param = "roleId="+funid;
			         for(var i=0;i<rows.length;i++){
			              tmp+=rows[i].get('sfunId');
			              if(i != rows.length - 1)
			    		  {
			    				tmp += ",";
			    		  }
			         }
			        param += "&functionsId=" + tmp;
	    		    subAjax(param,'addFunctionsToRole'); 
			     }
			}
			
			function subAjax(param,methodName){
        	Ext.Ajax.request({
	        	url	  : '<%=basePath%>/console/data/usercenter/roleFunManage/'+methodName+'.htm',
	        	params: param ,
	        	method: 'POST',
	        	success:function(result,request)
	        	{
					alertInformation(result.responseText);
					roleStore.reload();
	        	},
	        	failure: function( result, request) 
	        	{
					alertError("系统运行超时或执行失败");
				}
        	});
        }
        function removeRole(e){
           if(e =="yes"){
           	    var rows=rightGrid.getSelectionModel().getSelections();
	    		var tmp = "";
	    		var param = "roleId="+funid;
			         for(var i=0;i<rows.length;i++){
			              tmp+=rows[i].get('sfunId');
			              if(i != rows.length - 1)
			    		  {
			    				tmp += ",";
			    		  }
			         }
			        param += "&functionsId="+ tmp;
	    		    subAjax(param,'deleteFunctionsFromRole'); 
           }
        }
        
       });
  </script>
	</head>
	<body>
		<div id="mainDiv"></div>
	</body>
</html>
