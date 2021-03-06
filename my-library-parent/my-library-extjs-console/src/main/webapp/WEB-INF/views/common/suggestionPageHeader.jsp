<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibInclude.jsp"%>
<%!String top_menu_id = null; %>
<%!public String setMenuStyle(String mi){
    if(top_menu_id.equals(mi)){
         return "class=\"cur\"";
    }
    return "";
} %>
<%
top_menu_id = request.getParameter("s_mi");
if(top_menu_id == null){
    top_menu_id = (String) session.getAttribute("s_top_menu_id");
}
if(top_menu_id == null || "".equals(top_menu_id)){
    top_menu_id = "1";
}
session.setAttribute("s_top_menu_id", top_menu_id);
String contextPath = request.getContextPath();
%>

<div class="top_mini_nav base_layout">
  <c:if test="${sessionScope.consultantSession == null }">
    <a class="a_cheng" href="javascript:showOpenLoginDiv();">请登录</a>&nbsp;<a class="a_cheng" href="<%=contextPath%>/toRegister.htm">免费注册</a>
  </c:if>
  <c:if test="${sessionScope.consultantSession != null }">
    欢迎您！
    <c:if test="${sessionScope.consultantSession.niceName == null || sessionScope.consultantSession.niceName == '' }">
      ${sessionScope.consultantSession.mobilePhone } 
    </c:if>
    <c:if test="${sessionScope.consultantSession.niceName != null }">
      ${sessionScope.consultantSession.niceName } 
    </c:if>
    &nbsp;&nbsp;<a class="ahui" href="<%=contextPath%>/plateform/logout.htm">安全退出</a>
  </c:if>&nbsp;
  <a class="ahui" href="javascript:;" onclick="try {window.external.addFavorite(window.location.href, document.title);}catch(e){try {window.sidebar.addPanel(window.location.href, document.title, '');} catch (ex) {alert('加入收藏失败，请使用Ctrl+D进行添加');}};">加入收藏</a>&nbsp;
</div>
<div class="top_header base_layout">
  <div class="logo">
  	<div style="float: left;">
    	<a href="<%=contextPath%>/index.htm"><img src="<%=contextPath%>/images/platform/logo2.png" title="有房喔全名卖房网" /></a>
  	</div>
	<div style="text-align: right;">
	  	<img class="service" src="<%=path %>/images/common/common_service.jpg" alt="服务热线" />
	</div>
  </div>
</div>
<div class="nav_bar">
		<div class="base_layout nav_bar_content">
			<div class="nav_left">
				<a href="<%=contextPath%>/to_index.htm" <%=setMenuStyle("1")%>>所有建议</a>
				<c:if test="${sessionScope.consultantSession == null}">
				  	<a href="javascript:;" onclick="showOpenLoginDiv()" <%=setMenuStyle("2")%>>我的建议</a>
          			<a href="javascript:;" onclick="showOpenLoginDiv()" <%=setMenuStyle("3")%>>我要提建议</a>
				</c:if>
				<c:if test="${sessionScope.consultantSession != null}">
		          <a href="<%=contextPath%>/plateform/suggestion/mySuggestion.htm" <%=setMenuStyle("2")%>>我的建议</a>
		          <a href="<%=contextPath%>/plateform/suggestion/suggestion.htm" <%=setMenuStyle("3")%>>我要提建议</a>
        		</c:if>
			</div>
			<script type="text/javascript">
			function navBarKeywordsSearch(){
				var obj = document.getElementById("txtNavBarKeywordsSearch");
				var keywords = "";
				if(obj != null){
          keywords = obj.value;
          if(keywords == "请输入问答关键字"){
        	  keywords = "";
          }
        }
				keywords = encodeURIComponent(keywords).replace(/%/g,"*");
				document.getElementById("hidNavBarKeywords").value = keywords;
				document.getElementById("nav_bar_searchform").submit();
			}
			
			function txtNavBarKeywordsSearch_focus(obj){
				if(obj != null){
					var val = obj.value;
					if(val == "请输入问答关键字"){
						  obj.value = "";
					}
				}
			}
			
			function txtNavBarKeywordsSearch_blur(obj){
				if(obj != null){
          var val = obj.value;
          if(val == ""){
              obj.value = "请输入问答关键字";
          }
        }
			}
			
			function closeOpenLoginDiv(){
			  $("#open_login_div").hide();
			  $("#open_login_bgdiv").hide();
			}

			function showOpenLoginDiv(){
			  $("#open_login_div").fadeIn();
			  $("#open_login_bgdiv").fadeIn();
			}
			
			function loginValidate_loginDiv(){
				var userName = $("#txtName_loginDiv").val();
				if($.trim(userName) == ""){
					$("#txtName_loginDiv").focus();
					$("#errorMsg_loginDiv").html("请填写手机号码！");
					return;
				}
				var pwd = $("#txtPwd_loginDiv").val();
				if($.trim(pwd) == ""){
					$("#txtPwd_loginDiv").focus();
          $("#errorMsg_loginDiv").html("请填写密码！");
          return;
        }
				
				var rememberName = $("#txtRememberName_loginDiv").attr("checked") ? "yes" : "no";
				$.ajax({
				    url: "<%=contextPath%>/loginValidate.htm",
	          data : {
	            "userName" : userName,
	            "pwd" : pwd,
	            "rememberName" : rememberName,
	            "loginType" : "json"
	          },
	          type : 'post',
	          success : function(data) {
	            if ($.trim(data) != '') {
	              var obj = eval('(' + data + ')');
	              if(obj.status == 'ok'){
	            	  $("#errorMsg_loginDiv").html("");
	            	  window.location.href = '<%=contextPath%>/to_index.htm';
	              }else{
	            	  $("#errorMsg_loginDiv").html(obj.msg);
	              }
	            }
	          }
	        });
			}
			
			$(function(){
				 var open_login_bgdiv = $("#open_login_bgdiv");
				 if(open_login_bgdiv){
					 open_login_bgdiv.css("height",$(document).height());
				 }
			});
			</script>
			<div class="search_box">
			     <%
			     String navbar_keywords = (String)request.getAttribute("keywords");
			     if(navbar_keywords == null || "".equals(navbar_keywords)){
				       navbar_keywords = "请输入问答关键字";
			     }
			     %>
			     <form id="nav_bar_searchform" action="<%=contextPath%>/to_index.htm" method="post">
			     <input type="hidden" id="hidNavBarKeywords" name="keyword" />
					<input type="text" id="txtNavBarKeywordsSearch" onfocus="txtNavBarKeywordsSearch_focus(this)" onkeydown="try{if(event.keyCode==13){navBarKeywordsSearch();}}catch(ex){}" onblur="txtNavBarKeywordsSearch_blur(this)" class="input_keyword" value="<%=navbar_keywords%>" />
					<span class="search_button" onclick="navBarKeywordsSearch()"></span>
					</form>
			</div>
		</div>
</div>
<div class="clear"></div>
<div id="open_login_div" class="open_login_div">
       <div class="box" style="margin-top:0px;height:332px;">
         <div class="box_title">
           <h2>登录到有房喔个人账户</h2>
           <span class="btn_login_close" onclick="closeOpenLoginDiv();" title="关闭"></span>
         </div>
          <div>
             <table>
                 <tr>
                   <td class="nowrap">手机号码：</td>
                   <td >
                     <input type="text" class="login_form_textbox" id="txtName_loginDiv" style="width:150px;height: 18px;line-height: 18px;" name="userName" value="${requestScope.userNameCookie}"/>
                   </td>
                   <td>
                     <a href="<%=contextPath%>/plateform/consultant/toRegister.htm" class="ahui">立即注册</a>
                   </td>
                 </tr>
                 <tr>
                   <td>密　　码：</td>
                   <td>
                     <input type="password" class="login_form_textbox" style="width:150px;" id="txtPwd_loginDiv" name="pwd"/>
                   </td>
                   <td>
                     <a href="<%=contextPath%>/plateform/findPwd.htm" class="red">忘记密码？</a>
                   </td>
                 </tr>
                 <tr>
                   <td>&nbsp;</td>
                   <td colspan="2">
                     <label><input type="checkbox"  id="txtRememberName_loginDiv" name="rememberName" value="yes" />&nbsp;<span style="font-size:12px;">记住手机号码</span></label>
                   </td>
                 </tr>
                 <tr>
                   <td>&nbsp;</td>
                   <td colspan="2">
                     <div id="errorMsg_loginDiv" class="red"></div>
                     <br />
                     <input type="button" class="btn_long_login" onclick="loginValidate_loginDiv()" />
                   </td>
                 </tr>
               </table>
          </div>
       </div>
      </div>
    <div id="open_login_bgdiv" class="open_login_bgdiv" style="filter:alpha(opacity=70);opacity:0.7;-moz-opacity:0.7;"></div>