
// 图片滚动列表 mengjia 070927
var Speed_1 = 10; // 速度(毫秒)
var Space_1 = 20; // 每次移动(px)
var PageWidth_1 = 225 * 1; // 翻页宽度
var interval_1 = 7000; // 翻页间隔
var fill_1 = 0; // 整体移位
var MoveLock_1 = false;
var MoveTimeObj_1;
var MoveWay_1 = "right";
var Comp_1 = 0;
var AutoPlayObj_1 = null;
function GetObj(objName) {
	if (document.getElementById) {
		return eval("document.getElementById(\"" + objName + "\")");
	} else {
		return eval("document.all." + objName);
	}
}
function AutoPlay_1() {
	clearInterval(AutoPlayObj_1);
	AutoPlayObj_1 = setInterval("ISL_GoDown_1();ISL_StopDown_1();", interval_1);
}
function ISL_GoUp_1() {
	if (MoveLock_1) {
		return;
	}
	clearInterval(AutoPlayObj_1);
	MoveLock_1 = true;
	MoveWay_1 = "left";
	MoveTimeObj_1 = setInterval("ISL_ScrUp_1();", Speed_1);
}
function ISL_StopUp_1() {
	if (MoveWay_1 == "right") {
		return;
	}
	clearInterval(MoveTimeObj_1);
	if ((GetObj("ISL_Cont_1").scrollLeft - fill_1) % PageWidth_1 != 0) {
		Comp_1 = fill_1 - (GetObj("ISL_Cont_1").scrollLeft % PageWidth_1);
		CompScr_1();
	} else {
		MoveLock_1 = false;
	}
	AutoPlay_1();
}
function ISL_ScrUp_1() {
	if (GetObj("ISL_Cont_1").scrollLeft <= 0) {
		GetObj("ISL_Cont_1").scrollLeft = GetObj("ISL_Cont_1").scrollLeft
				+ GetObj("List1_1").offsetWidth;
	}
	GetObj("ISL_Cont_1").scrollLeft -= Space_1;
}
function ISL_GoDown_1() {
	clearInterval(MoveTimeObj_1);
	if (MoveLock_1) {
		return;
	}
	clearInterval(AutoPlayObj_1);
	MoveLock_1 = true;
	MoveWay_1 = "right";
	ISL_ScrDown_1();
	MoveTimeObj_1 = setInterval("ISL_ScrDown_1()", Speed_1);
}
function ISL_StopDown_1() {
	if (MoveWay_1 == "left") {
		return;
	}
	clearInterval(MoveTimeObj_1);
	if (GetObj("ISL_Cont_1").scrollLeft % PageWidth_1
			- (fill_1 >= 0 ? fill_1 : fill_1 + 1) != 0) {
		Comp_1 = PageWidth_1 - GetObj("ISL_Cont_1").scrollLeft % PageWidth_1
				+ fill_1;
		CompScr_1();
	} else {
		MoveLock_1 = false;
	}
	AutoPlay_1();
}
function ISL_ScrDown_1() {
	if (GetObj("ISL_Cont_1").scrollLeft >= GetObj("List1_1").scrollWidth) {
		GetObj("ISL_Cont_1").scrollLeft = GetObj("ISL_Cont_1").scrollLeft
				- GetObj("List1_1").scrollWidth;
	}
	GetObj("ISL_Cont_1").scrollLeft += Space_1;
}
function CompScr_1() {
	if (Comp_1 == 0) {
		MoveLock_1 = false;
		return;
	}
	var num, TempSpeed = Speed_1, TempSpace = Space_1;
	if (Math.abs(Comp_1) < PageWidth_1 / 2) {
		TempSpace = Math.round(Math.abs(Comp_1 / Space_1));
		if (TempSpace < 1) {
			TempSpace = 1;
		}
	}
	if (Comp_1 < 0) {
		if (Comp_1 < -TempSpace) {
			Comp_1 += TempSpace;
			num = TempSpace;
		} else {
			num = -Comp_1;
			Comp_1 = 0;
		}
		GetObj("ISL_Cont_1").scrollLeft -= num;
		setTimeout("CompScr_1()", TempSpeed);
	} else {
		if (Comp_1 > TempSpace) {
			Comp_1 -= TempSpace;
			num = TempSpace;
		} else {
			num = Comp_1;
			Comp_1 = 0;
		}
		GetObj("ISL_Cont_1").scrollLeft += num;
		setTimeout("CompScr_1()", TempSpeed);
	}
}
function picrun_ini() {
	GetObj("List2_1").innerHTML = GetObj("List1_1").innerHTML;
	GetObj("ISL_Cont_1").scrollLeft = fill_1 >= 0
			? fill_1
			: GetObj("List1_1").scrollWidth - Math.abs(fill_1);
	GetObj("ISL_Cont_1").onmouseover = function() {
		clearInterval(AutoPlayObj_1);
	};
	GetObj("ISL_Cont_1").onmouseout = function() {
		AutoPlay_1();
	};
	AutoPlay_1();
}
// 产品展示滚动图片结束
//

// 资源滑动门
function scrollDoor() {
}
scrollDoor.prototype = {
	sd : function(menus, divs, openClass, closeClass) {
		var _this = this;
		if (menus.length != divs.length) {
			alert("\u83dc\u5355\u5c42\u6570\u91cf\u548c\u5185\u5bb9\u5c42\u6570\u91cf\u4e0d\u4e00\u6837!");
			return false;
		}
		for (var i = 0; i < menus.length; i++) {
            if(_this.$(menus[i])) {
				_this.$(menus[i]).value = i;
				_this.$(menus[i]).onmouseover = function() {
					for (var j = 0; j < menus.length; j++) {
						_this.$(menus[j]).className = closeClass;
						_this.$(divs[j]).style.display = "none";
					}
					_this.$(menus[this.value]).className = openClass;
					_this.$(divs[this.value]).style.display = "block";
				};
            }
		}
	},
	$ : function(oid) {
		if (typeof(oid) == "string") {
			return document.getElementById(oid);
		}
		return oid;
	}
};
window.onload = function() {
	var SDmodel = new scrollDoor();
	SDmodel.sd(["m01", "m02", "m03"], ["c01", "c02", "c03"], "sd01", "sd02");
	// SDmodel.sd(["mm01","mm02","mm03"],["cc01","cc02","cc03"],"sd01","sd02");
	// SDmodel.sd(["mmm01","mmm02","mmm03"],["ccc01","ccc02","ccc03"],"sd01","sd02");
};
