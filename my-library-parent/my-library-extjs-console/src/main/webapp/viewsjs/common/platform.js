// var Speed = 10;
// var Space = 5;
// var PageWidth = 200;
// var fill = 0;
// var MoveLock = false;
// var MoveTimeObj;
// var Comp = 0;
// var AutoPlayObj = null;
// GetObj("List2").innerHTML = GetObj("List1").innerHTML;
// GetObj('syd_Cont').scrollLeft = fill;
// GetObj("syd_Cont").onmouseover = function(){clearInterval(AutoPlayObj);}
// GetObj("syd_Cont").onmouseout = function(){AutoPlay();}
// AutoPlay();
// function GetObj(objName){if(document.getElementById){return eval('document.getElementById("'+objName+'")')}else{return
// eval('document.all.'+objName)}}
//
// function syd_GoUp(){
// if(MoveLock) return;
// clearInterval(AutoPlayObj);
// MoveLock = true;
// MoveTimeObj = setInterval('ISL_ScrUp();',Speed);
// }
// function syd_StopUp(){ ֹ
// clearInterval(MoveTimeObj);
// if(GetObj('syd_Cont').scrollLeft % PageWidth - fill != 0){
// Comp = fill - (GetObj('syd_Cont').scrollLeft % PageWidth);
// CompScr();
// }else{
// MoveLock = false;
// }
// AutoPlay();
// }
// function ISL_ScrUp(){
// if(GetObj('syd_Cont').scrollLeft <= 0){GetObj('syd_Cont').scrollLeft = GetObj('syd_Cont').scrollLeft + GetObj('List1').offsetWidth}
// GetObj('syd_Cont').scrollLeft -= Space ;
// }
// function syd_GoDown(){
// clearInterval(MoveTimeObj);
// if(MoveLock) return;
// clearInterval(AutoPlayObj);
// MoveLock = true;
// ISL_ScrDown();
// MoveTimeObj = setInterval('ISL_ScrDown()',Speed);
// }
// function syd_StopDown(){ ֹ
// clearInterval(MoveTimeObj);
// if(GetObj('syd_Cont').scrollLeft % PageWidth - fill != 0 ){
// Comp = PageWidth - GetObj('syd_Cont').scrollLeft % PageWidth + fill;
// CompScr();
// }else{
// MoveLock = false;
// }
// AutoPlay();
// }
// function ISL_ScrDown(){
// if(GetObj('syd_Cont').scrollLeft >= GetObj('List1').scrollWidth){GetObj('syd_Cont').scrollLeft = GetObj('syd_Cont').scrollLeft -
// GetObj('List1').scrollWidth;}
// GetObj('syd_Cont').scrollLeft += Space ;
// }
// function CompScr(){
// var num;
// if(Comp == 0){MoveLock = false;return;}
// if(Comp < 0){
// if(Comp < -Space){
// Comp += Space;
// num = Space;
// }else{
// num = -Comp;
// Comp = 0;
// }
// GetObj('syd_Cont').scrollLeft -= num;
// setTimeout('CompScr()',Speed);
// }else{
// if(Comp > Space){
// Comp -= Space;
// num = Space;
// }else{
// num = Comp;
// Comp = 0;
// }
// GetObj('syd_Cont').scrollLeft += num;
// setTimeout('CompScr()',Speed);
// }
// }

var currentPage = 1; // 首页 特色库 当前使用页
var xmlHttp;
function createXMLHttpRequest() {
	if(window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}else if(window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
}

//图片滚动列表 mengjia 070927
var Speed_1 = 10; //速度(毫秒)
var Space_1 = 20; //每次移动(px)
var PageWidth_1 = 225 * 1; //翻页宽度
var interval_1 = 7000; //翻页间隔
var fill_1 = 0; //整体移位
var MoveLock_1 = false;
var MoveTimeObj_1;
var MoveWay_1="right";
var Comp_1 = 0;
var AutoPlayObj_1=null;
function GetObj(objName){if(document.getElementById){return eval('document.getElementById("'+objName+'")')}else{return eval('document.all.'+objName)}}
function AutoPlay_1(){clearInterval(AutoPlayObj_1);AutoPlayObj_1=setInterval('ISL_GoDown_1();ISL_StopDown_1();',interval_1)}
function ISL_GoUp_1(){if(MoveLock_1)return;clearInterval(AutoPlayObj_1);MoveLock_1=true;MoveWay_1="left";MoveTimeObj_1=setInterval('ISL_ScrUp_1();',Speed_1);}
function ISL_StopUp_1(){if(MoveWay_1 == "right"){return};clearInterval(MoveTimeObj_1);if((GetObj('ISL_Cont_1').scrollLeft-fill_1)%PageWidth_1!=0){Comp_1=fill_1-(GetObj('ISL_Cont_1').scrollLeft%PageWidth_1);CompScr_1()}else{MoveLock_1=false}
AutoPlay_1()}
function ISL_ScrUp_1(){if(GetObj('ISL_Cont_1').scrollLeft<=0){GetObj('ISL_Cont_1').scrollLeft=GetObj('ISL_Cont_1').scrollLeft+GetObj('List1_1').offsetWidth}
GetObj('ISL_Cont_1').scrollLeft-=Space_1}
function ISL_GoDown_1(){clearInterval(MoveTimeObj_1);if(MoveLock_1)return;clearInterval(AutoPlayObj_1);MoveLock_1=true;MoveWay_1="right";ISL_ScrDown_1();MoveTimeObj_1=setInterval('ISL_ScrDown_1()',Speed_1)}
function ISL_StopDown_1(){if(MoveWay_1 == "left"){return};clearInterval(MoveTimeObj_1);if(GetObj('ISL_Cont_1').scrollLeft%PageWidth_1-(fill_1>=0?fill_1:fill_1+1)!=0){Comp_1=PageWidth_1-GetObj('ISL_Cont_1').scrollLeft%PageWidth_1+fill_1;CompScr_1()}else{MoveLock_1=false}
AutoPlay_1()}
function ISL_ScrDown_1(){if(GetObj('ISL_Cont_1').scrollLeft>=GetObj('List1_1').scrollWidth){GetObj('ISL_Cont_1').scrollLeft=GetObj('ISL_Cont_1').scrollLeft-GetObj('List1_1').scrollWidth}
GetObj('ISL_Cont_1').scrollLeft+=Space_1}
function CompScr_1(){if(Comp_1==0){MoveLock_1=false;return}
var num,TempSpeed=Speed_1,TempSpace=Space_1;if(Math.abs(Comp_1)<PageWidth_1/2){TempSpace=Math.round(Math.abs(Comp_1/Space_1));if(TempSpace<1){TempSpace=1}}
if(Comp_1<0){if(Comp_1<-TempSpace){Comp_1+=TempSpace;num=TempSpace}else{num=-Comp_1;Comp_1=0}
GetObj('ISL_Cont_1').scrollLeft-=num;setTimeout('CompScr_1()',TempSpeed)}else{if(Comp_1>TempSpace){Comp_1-=TempSpace;num=TempSpace}else{num=Comp_1;Comp_1=0}
GetObj('ISL_Cont_1').scrollLeft+=num;setTimeout('CompScr_1()',TempSpeed)}}
function picrun_ini(){
GetObj("List2_1").innerHTML=GetObj("List1_1").innerHTML;
GetObj('ISL_Cont_1').scrollLeft=fill_1>=0?fill_1:GetObj('List1_1').scrollWidth-Math.abs(fill_1);
GetObj("ISL_Cont_1").onclick=function(){clearInterval(AutoPlayObj_1)}
GetObj("ISL_Cont_1").onmouseout=function(){AutoPlay_1()}
AutoPlay_1();
}
//产品展示滚动图片结束

//资源滑动门
function scrollDoor(){
}
scrollDoor.prototype = {
	sd : function(menus,divs,openClass,closeClass){
		var _this = this;
		if(menus.length != divs.length)
		{
			alert("菜单层数量和内容层数量不一样!");
			return false;
		}				
		for(var i = 0 ; i < menus.length ; i++)
		{	
		    if(_this.$(menus[i])) {
				_this.$(menus[i]).value = i;				
				_this.$(menus[i]).onclick = function(){
						
					for(var j = 0 ; j < menus.length ; j++)
					{	
                        if(_this.$(menus[j]) && _this.$(divs[j])) {
							_this.$(menus[j]).className = closeClass;
							_this.$(divs[j]).style.display = "none";
                        }
					}
                    if(_this.$(menus[this.value]) && 
                            _this.$(divs[this.value])) { 
						_this.$(menus[this.value]).className = openClass;	
						_this.$(divs[this.value]).style.display = "block";				
						_this.$(divs[this.value]).className = "fenlei_mid";
                    }
                }
            }
        }
      },
	$ : function(oid){
		if(typeof(oid) == "string")
		return document.getElementById(oid);
		return oid;
	}
}
window.onload = function(){
	var SDmodel = new scrollDoor();
	SDmodel.sd(["m01","m02","m03","m04"],["c01","c02","c03","c04"],"sd01","sd02","sd03","sd04");
	//SDmodel.sd(["mm01","mm02","mm03"],["cc01","cc02","cc03"],"sd01","sd02");
	//SDmodel.sd(["mmm01","mmm02","mmm03"],["ccc01","ccc02","ccc03"],"sd01","sd02");
}


function drop_mouseover(pos) {
	try {
		window.clearTimeout(timer);
	}catch(e) {
	}
}
function drop_mouseout(pos) {
	var posSel = document.getElementById(pos + "Sel").style.display;
	if(posSel == "block") {
		timer = setTimeout("drop_hide('" + pos + "')", 1000);
	}
}
function drop_hide(pos) {
	document.getElementById(pos + "Sel").style.display = "none";
}
function search_show(pos, searchType, href) {
	document.getElementById(pos + "SearchType").value = searchType;
	document.getElementById(pos + "Sel").style.display = "none";
	document.getElementById(pos + "Slected").innerHTML = href.innerHTML;
	document.getElementById(pos + 'q').focus();
	var sE = document.getElementById("searchExtend");
	if(sE != undefined && searchType == "bar") {
		sE.style.display = "block";
	}else if(sE != undefined) {
		sE.style.display = "none";
	}
	try {
		window.clearTimeout(timer);
	}catch(e) {
	}
	return false;
}

// tool
var saveBackgroundColor = "";
function mouseout(count) {
	var tr = document.getElementById("tr" + count);
	tr.style.backgroundColor = saveBackgroundColor;
	var span = document.getElementById("span" + count);

	if(span != null) span.style.display = "none";
}

function mouseover(count) {
	var tr = document.getElementById("tr" + count);
	saveBackgroundColor = tr.style.backgroundColor;
	tr.style.backgroundColor = "#fdfde7";

	var span = document.getElementById("span" + count);
	if(span != null) span.style.display = "";
}
function MM_preloadImages() { // v3.0
	var d = document;
	if(d.images) {
		if(!d.MM_p) d.MM_p = new Array();
		var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
		for(i = 0; i < a.length; i++)
			if(a[i].indexOf("#") != 0) {
				d.MM_p[j] = new Image;
				d.MM_p[j++].src = a[i];
			}
	}
}

function MM_swapImgRestore() { // v3.0
	var i, x, a = document.MM_sr;
	for(i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
		x.src = x.oSrc;
}

function MM_findObj(n, d) { // v4.01
	var p, i, x;
	if(!d) d = document;
	if((p = n.indexOf("?")) > 0 && parent.frames.length) {
		d = parent.frames[n.substring(p + 1)].document;
		n = n.substring(0, p);
	}
	if(!(x = d[n]) && d.all) x = d.all[n];
	for(i = 0; !x && i < d.forms.length; i++)
		x = d.forms[i][n];
	for(i = 0; !x && d.layers && i < d.layers.length; i++)
		x = MM_findObj(n, d.layers[i].document);
	if(!x && d.getElementById) x = d.getElementById(n);
	return x;
}

function MM_swapImage() { // v3.0
	var i, j = 0, x, a = MM_swapImage.arguments;
	document.MM_sr = new Array;
	for(i = 0; i < (a.length - 2); i += 3)
		if((x = MM_findObj(a[i])) != null) {
			document.MM_sr[j++] = x;
			if(!x.oSrc) x.oSrc = x.src;
			x.src = a[i + 2];
		}
}

function $(id) {
	return document.getElementById(id);
}

function foou(a) {
	var obj = "" + a + "d";
	$(obj).style.display = ($(obj).style.display == "none") ? "" : "none";
}

// pifu
function zConfirm() {
	var showBox = document.getElementById("aaa");
	var bgalpaha = document.getElementById("alphaBox");
	var content = document.getElementById("content");
	if(showBox.style.display == "none") {
		showBox.style.display = "block";
		showBox.style.height = document.documentElement.scrollHeight;
		bgalpaha.style.height = document.documentElement.scrollHeight + "px";
		if(navigator.appName == "Microsoft Internet Explorer")
			bgalpaha.style.width = document.documentElement.scrollWidth + "px";
		else
			bgalpaha.style.width = document.documentElement.scrollWidth + "px";
		// alert(document.documentElement.scrollHeight);
	}else
		showBox.style.display = "none";
}

// 首页简单查询
function dosearch(typeId, value) {
	var form = document.getElementById('headSearchForm');
	var action = basePath + '/platform/search/simpleSeekView.htm';
	if(!form.keyWord.value && typeId != "headSearchType" && typeId != "headType") {
		alert("关键字不以为空！");
		return;
	}
	// 重置数据
	if(typeId == "headSearchType") {
		form.more.value = false;
		form.keyWord.value = "";
	}
	if(document.getElementById(typeId)) {
		document.getElementById(typeId).value = value;
	}
	form.action = action;
	form.submit();
}
// 二次查询
function seekAction(typeId, value) {
	var form = document.getElementById('seekActionForm');
	form.action = basePath + '/platform/search/simpleSeekView.htm';
	// if(!form.keyWord.value) {
	// alert("关键字不以为空！");
	// return;
	// }
	// 重置数据
	if(typeId == "rsdaRstyId" || typeId == "rsdaDate" || typeId == "rsdaRdtyId" || typeId == "rsdaRmstId") {
		form.rsdaRstyId.value = "";
		form.rsdaDate.value = "";
		form.rsdaRltyId.value = "";
		form.rsdaRmstId.value = "";
	}
	if(document.getElementById(typeId)) {
		document.getElementById(typeId).value = value;
	}
	form.submit();
}
// 首页 特色库左翻
function trunLeft(totalPage) {
	if(currentPage == 1) {
		document.getElementById("List0").innerHTML = document.getElementById("List" + totalPage).innerHTML;
		currentPage = totalPage;
	}else {
		--currentPage;
		document.getElementById("List0").innerHTML = document.getElementById("List" + currentPage).innerHTML;
	}
	document.getElementById("storePage").innerHTML = currentPage+"/"+totalPage;
}

// 首页 特色库右翻
function turnRight(totalPage) {
	if(currentPage == totalPage) {
		document.getElementById("List0").innerHTML = document.getElementById("List1").innerHTML;
		currentPage = 1;
	}else {
		++currentPage;
		document.getElementById("List0").innerHTML = document.getElementById("List" + currentPage).innerHTML;
	}
	document.getElementById("storePage").innerHTML = currentPage+"/"+totalPage;
}

// 会员登录
function login() {
	document.getElementById("errorMsg").innerHTML = "";
	createXMLHttpRequest();
	var loginName = document.getElementById("loginName").value;
	if(!loginName) {
		alert("请输入用户名！");
		return false;
	}
	var password = document.getElementById("password").value;
	if(!password) {
		alert("请输入密码！");
		return false;
	}
	password = hex_sha1(password);
	var url = basePath + "/member/memberLogin.htm?name=" + loginName + "&pwd=" + password;
	xmlHttp.open("GET", url, true);
	xmlHttp.onreadystatechange = loginCallback;
	xmlHttp.send(null);
}
function loginCallback() {
	if(xmlHttp.readyState == 4) {
		if(xmlHttp.status == 200) {
			var result = eval("(" + xmlHttp.responseText + ")");
			if(result.isSuccess) {
				document.getElementById("errorMsg").style.display = "none";
				hideLoginDiv();
				document.getElementById("loginName").value = "";
				document.getElementById("password").value = "";
				if(forwardUrl){
					if(forwardUrl == 'source'){
						forwardUrl = "";
						resSourceReview(true);	// 保存评论
						document.getElementById('loginDiv').style.display="none";
					}else{
						window.location = forwardUrl; // 成功登录后的跳转页面
						forwardUrl = "";
					}
				}else{
					window.location.reload(); //刷新当前页面 
				}
			}else {
				document.getElementById("errorMsg").innerHTML = result.msg; 
				document.getElementById("loginName").value = document.getElementById("loginName").value;
				document.getElementById("password").value = document.getElementById("password").value;
				document.getElementById("errorMsg").style.display = "block";
				// 将焦点移到用户名输入框中
				document.getElementById("loginName").focus();
			}
		}
	}
}


function onFormEnterKeyDown(e) {
	e = window.event ? window.event : e;
	var keyCode = e.keyCode ? e.keyCode : e.which;
	if(keyCode == 13) {
		login();
	}
}

// 点击标签时检索
function searchForTag(keyWord,rtinId){
	var form = document.getElementById('headSearchForm');
	//form.resourceType.value = 0;
	form.keyWord.value = keyWord;
	if(rtinId!=0){
		form.rtinId.value = rtinId;
	}
	form.submit();
}

//显示层
function showDiv(flag,info){
	if(flag==''){return;}
	if(info==''){return;}
	switch (flag){
		case 'login':
			document.getElementById('loginDiv').style.display="";
			break;
		default:
			return;
	}
	// 将焦点移到用户名输入框中
	document.getElementById("loginName").focus();
	// 登录时隐蔽资源详细页面中的播放器
	var video_bf = document.getElementById('video_bf');
	var video_bf_login = document.getElementById('video_bf_login');
	if(video_bf && video_bf_login){
		video_bf.style.display = 'none';
		video_bf_login.style.display = 'block';
	}
}
//隐藏层
function hideLoginDiv(){
	document.getElementById('comeOut').style.display="none";
	document.getElementById('loginDiv').style.display="none";
	// 清空表单
	document.getElementById("loginName").value='';
	document.getElementById("password").value='';
	document.getElementById("errorMsg").innerHTML = "";
	//退出时显示资源详细页面中的播放器
	var video_bf = document.getElementById('video_bf');
	var video_bf_login = document.getElementById('video_bf_login');
	if(video_bf && video_bf_login){
		video_bf.style.display = 'block';
		video_bf_login.style.display = 'none';
	}
}