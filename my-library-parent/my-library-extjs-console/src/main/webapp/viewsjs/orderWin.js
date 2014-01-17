function showAddOrderWin(url,width,height){
	var bgDiv = document.getElementById("orderFrame_bgdiv");
	if(bgDiv == null){
		$(document.body).append("<div id='orderFrame_div' class='open_login_div' style='display:block;width:"+width+"px;height:"+height+"px;overflow:hidden;margin-top:"+(-(height/2) + $(document).scrollTop())+"px;'>"+
			"<div class='box' style='margin-top:0px;height:"+height+"px;'>"+
			  "<div class='box_title'>"+
			    "<h2>添加预约</h2>"+
			    "<span class='btn_login_close' onclick='closeAddOrderWin();' title='关闭'></span>"+
			  "</div>"+
			   "<iframe id='orderFrame_openframediv' src=\""+url+"\" allowTransparency='true'  width='"+(width)+"px' height='"+(height - 35)+"px' frameborder='0' scrolling='no' style=\"background-color: #transparent; border:none;\"></iframe>"+
			"</div></div>"+
			"<div id='orderFrame_bgdiv' class='open_login_bgdiv' style='height:"+$(document).height()+"px;display:block;width:100%;'></div>");
	}else{
		$("#orderFrame_div").show();
		$("#orderFrame_openframediv").attr("src",url);
		$("#orderFrame_div").css("marginTop",(-(height/2) + $(document).scrollTop())+"px");
		$("#orderFrame_bgdiv").show();
	}
}

function closeAddOrderWin(){
	$("#orderFrame_div").hide();
	$("#orderFrame_bgdiv").hide();
}