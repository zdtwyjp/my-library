<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.fxpgy.lfd.core.ConstantsObj"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <base href="<%=basePath%>">
<link type="text/css" href="<%=path %>/ext/resources/css/ext-all.css" rel="stylesheet"/>
<link type="text/css" href="<%=path %>/css/button.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=path %>/viewsjs/jquery.js"></script>
<script type="text/javascript" src="<%=path %>/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=path %>/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=path %>/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path %>/ext/ux/ProgressBarPager.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>有房喔-后台管理系统</title>
		<script type="text/javascript">
	    var LIMIT = <%=ConstantsObj.DEFAULT_PAGE_SIZE%>;
	    var curYear="-1";
	    var curDate="-1";
		Ext.BLANK_IMAGE_URL = "<%=path %>/ext/resources/images/default/s.gif";
		Ext.basePath = Ext.emptyFn;
		Ext.apply(Ext.basePath.prototype, {
			path : ''
		});
		var tree;
		Ext.onReady(function() {
			// 配置视图
			viewport = new Ext.Viewport({
				layout : 'border',
				items : [_leftForm,_centerForm]
			});
			
            tree=new Ext.tree.TreePanel({   
                el:'root1',   
                loader:new Ext.tree.TreeLoader(),                                
			    autoHeight:true,
				autoWidth : true,
				border : false,
				autoScroll : true
            });
            var root=new Ext.tree.AsyncTreeNode({   
                text:'全部',   
                children:eval("${items}"),
   //             	[   
   //                 {text:'2012',qtip:'No1',leaf:false,group:'2012',date:'-1',children:[{text:'Leaf No.3',qtip:'No3',leaf:true,group:'2012',date:'08-15'}]},   
    //                {text:'Leaf No.1',qtip:'No2',group:'2013',date:'-1',leaf:true}   
    //            ],
                group:-1,
                date:-1,
				rootVisible : true
            });
            tree.setRootNode(root);     
            tree.on("click",function(node){
        	    curYear = node.attributes.group;
        	    curDate = node.attributes.date;
//            	alert(store.sortInfo.field);
//				alert(store.sortInfo.direction);
//pagingToolBar.cursor --当前页
				store.reload({params:{ condition: "",group:node.attributes.group,date:node.attributes.date, start:0, limit:LIMIT}});  
            });   
            tree.render();			

    		var workerRecords = new Ext.data.Record.create([
    		                                    			{name:'account', type:'string'},
    		                                    			{name:'loginedCount', type:'int'}
    		                                    		]);
    		                                    		
    		                                    		// 页面数据仓库
    		                                    	    var store = new Ext.data.Store({
    		                                    	        reader: new Ext.data.JsonReader({ //使用JSON传输入数据
    		                                                    root : 'root',
    		                                                    totalProperty : 'totalProperty'           // 定义根节点和分页总条数属性，root与totalProperty已经在CommonUtil中包含，这里只需要在页面属性添加即可
    		                                                }, workerRecords),
    		                                    	        proxy:new Ext.data.HttpProxy({url:'<%=path%>/console/data/usercenter/consultantCountInit/initAllConsultants.htm'}),
    		                                    	        sortInfo: {field:'loginedCount', direction:'DESC'},
    		                                    	        remoteStore:true,    // 是否远程调用数据
    		                                    	        remoteSort:true      // 是否远程排序
    		                                    	    });
    		                                    	    
    		                                    		// 加载数据
    		                                     		store.load({params:{start:0, limit:LIMIT}});
    		                                     	    store.on('beforeload',function(store, options){
    		                                     	     Ext.apply(this.baseParams, 
    		                                     	     	{ condition: "1", start:0, limit:LIMIT}); 
    		                                    		});
    		                                    		var sm = new Ext.grid.CheckboxSelectionModel(); 
    		                                    	  
    		                                    	    
    		                                    	    var cm = new Ext.grid.ColumnModel([
    		                                    	    	new Ext.grid.RowNumberer()
    		                                    	    	,{
    		                                    	    		id:'account', 
    		                                    	    		header: '手机号', 
    		                                    	    		width: 150, 
    		                                    	    		sortable: true, 
    		                                    	    		dataIndex:'account'
    		                                                },{
    		                                    	    		id:'loginedCount', 
    		                                    	    		header: '登录次数', 
    		                                    	    		width: 150, 
    		                                    	    		sortable: true, 
    		                                    	    		dataIndex:'loginedCount'
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
    		                                    			height:Ext.getBody().getHeight()*0.98,
    		                                    			renderTo : 'mainDiv',
    		                                    			columnLines:true,
    		                                    			loadMask:true,
    		                                        		store: store,
    		                                        		frame:false,
    		                                        		sm: sm,
    		                                        		cm:cm,
    		                                     			autoScroll:true,
//    		                                     			autoHeight:true,
    		                                     			viewConfig : {
    		                                    				forceFit : true
    		                                    			},
    		                                    			enableColumnHide : false,
    		                                    			tbar: new Ext.Toolbar({
    		                                            		items:[{
    		                                    		             	text: '&nbsp;导&nbsp;出&nbsp;',
    		                                            				enableToggle: false,
    		                                            				iconCls:'common_excel',
    		                                            				handler:function(){
    		                                            					var param="sort="+store.sortInfo.field;
    		                                            					param+="&order="+store.sortInfo.direction;
    		                                            					param+="&group="+curYear;
    		                                            					param+="&date="+curDate;
    		                                            					$("#excelExport").attr("src","<%=path %>/console/data/usercenter/consultantCountInit/countExcel.htm?"+param);
    		                                            				}
    		                                            			}

    		                                            		]
    		                                            	}),
    		                                    	        bbar: pagingToolBar            
            
		});	                                    	    
		});	  
		Ext.basePath.path = '<%=basePath%>';
		var rootPath = '<%=basePath%>';
		var _leftForm = new Ext.Panel({
			id : 'west-panel',
			region : 'west',
			title : '统计日期',
			split : false,
			width : 200,
			height: Ext.getBody().getHeight()*0.99,
//			minSize : 200,
//			maxSize : 200,
			margins : '5 0 5 5',
			cmargins : '5 5 5 5',
			collapsible : false,
			html:"<div id='root1'></div>",
//			layout : 'accordion',
			autoScroll : true,
			layoutConfig : {
				animate : true
			}
//		,items : [${items}]
		});
		var _centerForm = new Ext.Panel({
			id : 'center-panel',
			region : 'center',
//			title : '数据展示',
			split : false,
			width : 200,
			height: Ext.getBody().getHeight()*0.99,
//			minSize : 200,
//			maxSize : 200,
			margins : '5 0 5 5',
			cmargins : '5 5 5 5',
			collapsible : false,
			html:"<div id='mainDiv'></div>",
//			layout : 'accordion',
			autoScroll : true,
			layoutConfig : {
				animate : true
			}
//		,items : [${items}]
		});		
</script>
</head>
<body>

	<iframe id="excelExport" src="" style="display: none">
	
	</iframe>
</body>
</html>