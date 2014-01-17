(function($){
	$.fn.xFocus = function(options){
		if(!$(this).size()){
			return false;
		};
		var $self = $(this);
		var $opt = $.extend({direction: 'h', order: 'asc', time: 1500, speed: 300, position: 'first', action: 'click', text: true}, options);
		var $i = 0;
		var $isstop = false;
		var $boxWidth = $self.width();
		var $boxHeight = $self.height();
		var $box = $('.focus-box', $(this).wrapInner('<div class="focus-box"><div class="focus-pics"></div></div>')).css({width: $boxWidth +'px', height: $boxHeight +'px'});
		var $pics = $('.focus-pics',$box);
		var $size = $('a', $box).size();
		if($opt.order == 'desc')$i = ($size -1);
		switch($opt.direction){
			case 'v':
				$pics.css({width: $boxWidth +'px', height: (($boxHeight + 1) * $size) +'px'});
				break;
			default:
				$pics.css({width: (($boxWidth + 1) * $size) +'px', height: $boxHeight +'px'});
				$('a', $pics).css({float: 'left'});
		};
		$('a, img', $pics).css({width: $boxWidth  +'px', height: $boxHeight +'px'});
		var $html = '<div class="controls">';
		if($opt.text){
			$html+='<div class="bg" style="width:'+ $boxWidth +'px;"></div><div class="info"></div>';
		};
		$html+='<ul class="l-btn">';
		$('.focus-pics a',this).each(function(i){
			var xclass = (i==0) ? ' class="selected"' : '';
			var xdata = ' xdata="'+ $('img',this).attr('alt') +'"';
			$html+='<li'+xclass+xdata+'>'+ (i+1) +'</li>';
		});
		$html+='</ul></div>';
		$(this).append($html);
		$opt.lis = $('.l-btn li', this);
		switch($opt.action){
			case 'hover':
				$opt.lis.hover(function(){
					$i = $(this).index();
					$isstop = true;
					$opt.actionTimer = setTimeout(xSwitch, 200);
				},function(){
					$isstop = false;
					clearTimeout($opt.actionTimer);
				});
				break;
			default:
				$opt.lis.click(function(){
					$i = $(this).index();
					xSwitch();
				}).hover(function(){
					$isstop = true;
				},function(){
					$isstop = false;
				});
		};
		function xSwitch(){
			$('.l-btn li', $self).removeClass().eq($i).addClass('selected');
			xText();
			switch($opt.direction){
				case 'v':
					$pics.animate({marginTop: - $i * $boxHeight +'px'}, $opt.speed);
					break;
				default:
					$pics.animate({marginLeft: - $i * $boxWidth +'px'}, $opt.speed);
			};
		};
		function xText(){
			if(!$opt.text){
				return false;
			};
			var $data = $('.l-btn li', $self).eq($i).attr('xdata');
			if($data == undefined){
				$data = '';
			};
			if($data.indexOf('$$') > -1){
				$data = $data.split('$$');
				$data = '<b>'+ $data[0] +'</b>'+ $data[1];
			};
			$('.info', $self).html($data);
		};
		if(isNaN($opt.position)){
			if($opt.position == 'first'){
				xText();
			}else if($opt.position == 'last'){
				$('.l-btn li', this).eq($size -1).click();
			}else{
				alert('error: [position:'+ $opt.position +']');
			};
		}else{
			if($opt.position > ($size - 1))$opt.position = ($size-1);
			$('.l-btn li', this).eq($opt.position).click();
		};	
		var $autoplay = setInterval(function(){
			if($isstop)return false;
			if($i > ($size - 1)){$i = 0;};
			if($i < 0){$i = ($size - 1);};
			xSwitch();
			if($opt.order == 'asc'){
				$i++;
			}else if($opt.order == 'desc'){
				$i--;
			}else{
				alert('error: [order:'+ $opt.position +']');
				clearInterval($autoplay);
			};
		}, $opt.time);
		return this;
	};
})(jQuery);