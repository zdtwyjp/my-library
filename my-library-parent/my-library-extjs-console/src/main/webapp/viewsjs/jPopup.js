(function(jQuery){
	var $name='xPopup';
	var $version='2.5.3';
	var $creator='Gin';
	var $time='2015-5-5 12:50:12';
	var $o;
	var $opt;
	var $x, $y;
	var $bWidth, $bHeight;
	var $isDraged=0;
	var $sLeft, $sTop;
	var $fTime, $fCount=0;
	var $eventBindCounter=0;
	var $eventMousemoveFlag=0;
	var $isIE = (document.all) ? true : false;
	var $isIE6 = (!window.XMLHttpRequest) ? true : false;
	this.init=function(options){
		$opt = $.extend({title: $name+' '+$version, width: 380, height: 220, left: '50%', top: '50%', html: '', url: '', mask: true, drag: false, btnClose: true, load: function(){}, unload: function(){}}, options);
		$opt.oleft = $opt.left;
		$opt.otop = $opt.top;
		if($opt.width<100)$opt.width=100;
		if($opt.height<50)$opt.height=50;
		if($opt.left<6)$opt.left=6;
		if($opt.top<6)$opt.top=6;
		$isDraged=0;
		var $html='<div id="xPopupMask">';
			if($isIE6){
				$html+='<iframe id="xPopupMaskIframe" frameborder="0"></iframe>';
			};
			$html+='	<div id="xPopupBlank" onclick="$.xPopup.xFlash()" tabindex="0"></div>';
			$html+='</div>';
			$html+='<div id="xPopup">'
			+'	<iframe id="xPopupBg" frameborder="0"></iframe>'
			+'	<div id="xPopupContainer" class="xPopupContainer">'
			+'		<h2 class="xPopupTitle"><span id="xPopupTitle">\u63d0\u793a</span><a id="xPopupBtnClose" class="xPopupBtnClose" href="javascript:void(0)" title="\u5173\u95ed"></a></h2>'
			+'		<div id="xPopupContent" class="xPopupContent"></div>'
			+'	</div>'
			+'</div>';
		if(!$('#xPopup').size()){
			$('body').prepend($html);
		};
		var $tmps='#xPopup';
		if($opt.mask){
			$tmps='#xPopupMask,#xPopup';
		}else{
			$('#xPopupMask').hide();
		};
		$($tmps).show();
		$('#xPopupContent').css({width: $opt.width +'px', height: $opt.height - 39 +'px'});
		if($isIE6){
			$('#xPopupContainer').css({height: $opt.height +'px', overflow: 'hidden'});
		};
		$('#xPopupTitle').html($opt.title);
		if($opt.url!=''){
			$opt.html='<iframe id="xPopupIframe" name="xPopupIframe" style="width:100%;height:100%" frameborder="0" src="'+ $opt.url +'"></iframe>';
			$('#xPopupContent').css({overflow: 'hidden'});
		};
		$('#xPopupContent').html($opt.html);
		$o = $('#xPopup');
		if(!$opt.btnClose){
			$('#xPopupBtnClose').hide();
		}else{
			$('#xPopupBtnClose').show();
		};
		$bWidth = $(window).width();
		$bHeight = $(window).height();
		if($isIE6){
			if($opt.left == '50%'){
				$opt.left = parseInt($bWidth/2 - $opt.width/2);
			};
			if($opt.top == '50%'){
				$opt.top = parseInt($bHeight/2 - $opt.height/2);
			};
		}else{
			if($opt.left == '50%'){
				$opt.marginLeft = '-'+ parseInt($opt.width/2) +'px';
			}else{
				$opt.left = $opt.left;
				$opt.marginLeft = 'auto';
			};
			if($opt.top == '50%'){
				$opt.marginTop = '-'+ parseInt($opt.height/2) +'px';
			}else{
				$opt.top = $opt.top;
				$opt.marginTop = 'auto';
			};
		};
		xMove();
		//if((typeof $opt.load=='function') && $opt.load()==false){return true;}
		if(typeof $opt.load != 'function'){
			return;
		};
		$opt.load.apply($opt, [$opt]);
		if($eventBindCounter==0){
			$('#xPopupTitle').mousedown(function(event){
				if($opt.drag==false){
					$(this).css('cursor','default');
					$('#xPopupContent').css('visibility','visible');
					return false;
				};
				$eventMousemoveFlag=1;
				$(this).css('cursor','move');
				$('#xPopupContent').css({visibility:'hidden'});
				$x = event.clientX - $o.offset().left;
				$y = event.clientY - $o.offset().top;
				$sLeft = $(document).scrollLeft();
				$sTop = $(document).scrollTop();
				var $witchButton = false;
				if(document.all && event.button == 1){
					$witchButton = true;
				}else{
					if(event.button == 0){
						$witchButton = true;
					};
				};
				if($isIE){this.setCapture();};
				if($witchButton){
					$(document).mousemove(function(event){
						if($eventMousemoveFlag!=0){
							window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
							$opt.left = parseInt(event.clientX - $x - $sLeft);
							$opt.top = parseInt(event.clientY - $y - $sTop);
							$isDraged=1;
							xMove();
						};
					});
				};
			});
			$(document).mouseup(function(){
				$eventMousemoveFlag=0;
				$('#xPopupTitle').css('cursor','default');
				$('#xPopupContent').css({visibility:'visible'});
				if($isIE){$('#xPopupTitle').get(0).releaseCapture();};
			});
			$(window).resize(function(){
				xMove();
			});
			$('#xPopupBtnClose').click(function(){
				if((typeof $opt.unload=='function') && $opt.unload()==false){return false;}
				xHide();
			});
			$eventBindCounter=1;
		};
	};
	this.xMove=function(){
		$bWidth = $(window).width() - $opt.width;
		$bHeight = $(window).height() - $opt.height;
		if($opt.left > $bWidth){
			$opt.left = $bWidth;
		}else if($opt.left < 6){
			$opt.left = 6;
		};
		if($(window).width() < $opt.width && $opt.left!='50%'){
			$opt.left = $bWidth;
		}
		if($opt.top > $bHeight){
			$opt.top = $bHeight;
		};
		if($opt.top < 6){
			$opt.top = 6;
		};
		if($(window).height() < $opt.top){
			$opt.top = 6;
		}
		if($isDraged){
			$opt.marginLeft='auto';
			$opt.marginTop='auto';
		};
		if($isIE6){
			var $oo = $o.get(0);
			$oo.style.position = 'absolute';
			$oo.style.width = $opt.width +'px';
			$oo.style.height = $opt.height +'px';
			if($isDraged==1){
				$oo.style.setExpression('left', 'eval((document.documentElement.scrollLeft || document.body.scrollLeft) + ' + $opt.left + ') + \'px\'');
				$oo.style.setExpression('top', 'eval((document.documentElement.scrollTop || document.body.scrollTop) + ' + $opt.top + ') + \'px\'');
			}else{
				if($opt.oleft!='50%'){
					$oo.style.setExpression('left', 'eval((document.documentElement.scrollLeft || document.body.scrollLeft) + ' + $opt.left + ') + \'px\'');
				}else{
					$oo.style.setExpression('left', 'eval((document.documentElement.scrollLeft || document.body.scrollLeft) + ' + ($(window).width()/2 - $opt.width/2) + ') + \'px\'');
				};
				if($opt.otop!='50%'){
					$oo.style.setExpression('top', 'eval((document.documentElement.scrollTop || document.body.scrollTop) + ' + $opt.top + ') + \'px\'');
				}else{
					$oo.style.setExpression('top', 'eval((document.documentElement.scrollTop || document.body.scrollTop) + ' + ($(window).height()/2 - $opt.height/2) + ') + \'px\'');
				};
			};
		}else{
			$o.css({position: 'fixed', width: $opt.width+'px', height: $opt.height+'px', left: $opt.left, top: $opt.top, marginLeft: $opt.marginLeft, marginTop: $opt.marginTop});
		};
	};
	this.xHide=function(){
		$opt='';
		$('#xPopupMask,#xPopup').hide();
	};
	this.xFlash=function(){
		if(!$fTime==undefined){
			return false;
		};
		$('#xPopupTitle').toggleClass('xFlash');
		if($fCount>2){
			clearTimeout($fTime);
			$fTime=undefined;
			$fCount=0;
		}else{
			$fTime=setTimeout(function(){$.xPopup.xFlash();}, 70);
			$fCount++;
		};
	};
    jQuery.xPopup = this;
	return jQuery;
})(jQuery);
