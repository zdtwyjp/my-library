<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/extInclude.jsp"%>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp"%>
<%@ page import="com.fxpgy.lfd.core.ServiceUtil"%>

<%
	String path = request.getContextPath();
	String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>有房喔-后台管理系统</title>
		<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = "../ext/resources/images/default/s.gif";
		Ext.onReady(function() {
			// 顶层控制区
			var topPanel = new Ext.Panel({
				id : 'topPanel',
				region : 'north',
				split : false,
				collapsible : true,
				margins : '5 5 0 5',
				cmargins : '5 5 0 5',
				bodyStyle : 'background-color:#cedfef;',
				html : '<img src="' + Ext.basePath.path + 'images/logoback.jpg" />',
				height : 120,
				bbar : ['当前用户：<%=ServiceUtil.getCurrentUserName(request) %>',{
					xtype : "tbfill"
				}, {
					xtype : 'tbbutton',
					text : '主页',
					listeners : {
						click:function() {
							var _tabPanel = Ext.getCmp("main_tab");
							//var tp = _tabPanel.getComponent('tab_first');
							//_tabPanel.setActiveTab(tp);
							_tabPanel.setActiveTab('tab_first');
						}
					}
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'tbbutton',
					text : '修改密码',
					listeners : {
						click:function() {
							var url = 'console/data/changePassword/initializePage.htm';
							var id = 'tabChangepwd';
							var tabPanel = Ext.getCmp('main_tab');
							var n = tabPanel.getComponent(id);
							if(!n) {
								addTab(tabPanel, id, '修改密码', rootPath+url);
							}else {
								tabPanel.setActiveTab(id);
							}
						}
					}
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'tbbutton',
					text : '退出登录',
					listeners : {
						click : function() {
							Ext.Msg.confirm("提示", "确认要退出系统吗？\n", function(btn) {
								if(btn == "yes") window.location.href = rootPath + "console/logout.htm";
							})
						}
					}
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'tbbutton',
					text : '关闭标签',
					handler:function() {
		                //删除
		                Ext.getCmp('main_tab').removeAll();
		                Ext.getCmp('main_tab').add({
		                    id:'0',
		                    title:'首页',
		                    bodyStyle:'border:solid 1px #ffffff',
		                    iconCls:"main_tab",
							html : '<iframe width="100%" height="100%" frameborder="no" src="' + Ext.basePath.path + 'pages/background.jsp"></iframe>'
		                });
		                Ext.getCmp('main_tab').activate(0);
		            }
					
				}, {
					xtype : 'tbseparator'
				}, {
					xtype : 'tbbutton',
					text : '帮助',
					handler:function() {
		                
		            }
				}]
			});
			// 主面板(已有Tab)
			var _tabPanel = new Ext.TabPanel({
				id : 'main_tab',
				region : 'center',
				margins : '5 5 5 0',
				resizeTabs : false,
				enableTabScroll : true,
				autoScroll : true,
				autoShow : true,
				activeTab : 0,
				items : [{
					id : 'tab_first',
					title : "首页",
					autoWidth : true,
					bodyBorder : false,
					iconCls:"main_tab",
					layout : 'fit',
					html : '<iframe width="100%" height="100%" frameborder="no" src="' + Ext.basePath.path + 'pages/background.jsp"></iframe>'
				}]
			});
			// 配置视图
			viewport = new Ext.Viewport({
				layout : 'border',
				items : [topPanel, _leftForm, _tabPanel]
			});
			initTree();
		});
		Ext.basePath = Ext.emptyFn;
		Ext.apply(Ext.basePath.prototype, {
			path : ''
		})
		Ext.basePath.path = '<%=rootPath%>';
		var rootPath = '<%=rootPath%>';
		var _leftForm = new Ext.Panel({
			id : 'west-panel',
			region : 'west',
			title : '功能导航',
			split : true,
			width : 200,
			minSize : 200,
			maxSize : 200,
			margins : '5 0 5 5',
			cmargins : '5 5 5 5',
			collapsible : true,
			layout : 'accordion',
			autoScroll : true,
			layoutConfig : {
				animate : true
			},
			items : [${items}]
		});
		function addTab(tabPanel, tabId, tabTitle, targetUrl) {
			if(tabPanel.items.length > 10){
				tabPanel.remove(1);
			}
			tabPanel.add({ 
				id : tabId, 
				title : tabTitle,
				autoWidth : true,
				bodyBorder : false,
				layout : 'fit',
				html : '<iframe id="' + tabId + '" width="100%" height="100%" frameborder="no" src="' + targetUrl + '"></iframe>',
				closable : true
			}).show();
		}
		function clickNode(node, event) {
			if(node.isLeaf()) {
				var nodeid = node.id;
				var url = node.attributes.url;
				var id = 'tab'+nodeid;
				var tabPanel = Ext.getCmp('main_tab');
				var n = tabPanel.getComponent(id);
				if(!n) {
					if(url.indexOf("http") == -1)
						addTab(tabPanel, id, node.text, "<%=request.getContextPath()%>" + url);
					else
						addTab(tabPanel, id, node.text, url);
				}else {
					tabPanel.setActiveTab(id);
				}
			}
		}
		function initTree() {
			<c:forEach items="${catalogList}" var="catalog">
			var number = ${fn:length(catalogList)};
			var node${catalog.scatId} = new Ext.tree.AsyncTreeNode({
				id : "node${catalog.scatId}",
				text : "<c:out value="${catalog.scatName}" />"});
				var tree${catalog.scatId} = new Ext.tree.TreePanel({
				renderTo : "root${catalog.scatId}",
				root : node${catalog.scatId},
				rootVisible : false,
				autoScroll : true,
				height : Ext.getCmp('west-panel').getInnerHeight() - 24 * number,
				autoWidth : true,
				border : false,
				loader : new Ext.tree.TreeLoader({
					url : '<c:url value="/console/createTree.htm" />?id=${catalog.scatId}'
				}),
				width : 300
			});
			tree${catalog.scatId}.on("click", clickNode);
			</c:forEach>
		}
		</script>
		<style type="text/css">
			.message_tit {display:block; float:left;}
			.message {display:block; float:right; font-size:12px; color:white;}
			.message a {text-decoration:none; color:white;}
			.message img {vertical-align:bottom; float:left;}
			.message b {display:block; padding-left:2px; float:left; font-weight:100;}
		</style>
	</head>
	<body>
		<c:out value="${catalogDiv}" escapeXml="false" />
	</body>
</html>