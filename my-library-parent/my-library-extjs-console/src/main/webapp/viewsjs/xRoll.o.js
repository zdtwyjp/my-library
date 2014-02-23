(function($){
	$.fn.xRoll = function(opt){
		var $version = '3.0';
		var $self = this;
		var $xRoll_flag = false;
		var $opt = $.extend({direction: 'up', item: 1, speed: 600, time: 2000, mouseStop: true}, opt);
		var $o = $('ul', $self);
		var $oc = $('ul li', $self);
		var $h = $oc.height();
		var $w = $oc.width();
		var $bw_t = parseInt($oc.css('border-top-width').replace(/[^\d.]/g, ''));
		var $bw_r = parseInt($oc.css('border-right-width').replace(/[^\d.]/g, ''));
		var $bw_b = parseInt($oc.css('border-bottom-width').replace(/[^\d.]/g, ''));
		var $bw_l = parseInt($oc.css('border-left-width').replace(/[^\d.]/g, ''));
		$bw_t = isNaN($bw_t) ? 0 : $bw_t;
		$bw_r = isNaN($bw_r) ? 0 : $bw_r;
		$bw_b = isNaN($bw_b) ? 0 : $bw_b;
		$bw_l = isNaN($bw_l) ? 0 : $bw_l;
		var $bw = eval($bw_t + $bw_r + $bw_b + $bw_l) / 2;
		$h = $h + $bw;
		$w = $w + $bw;
		var $size = $oc.size();
		var $timer;
		var $direction = 'top';
		$($self).css({position:'relative',left:'0px',top:'0px'});
		$o.css({position:'absolute'});
		switch($opt.direction){
			case 'left':
				$direction = 'left';
				$o.css({left:'0px', top:'0px', width:($w*$size+$bw)+'px'});
				$oc.css({float:'left'});
				break;
			case 'right':
				$direction = 'right';
				$o.css({right:'0px', top:'0px', width:($w*$size) +'px'});
				$oc.css({float:'left'});
				break;
			case 'up':
				$direction = 'top';
				$o.css({left:'0px', top:'0px', height:($h*$size+$bw)+'px'});
				break;
			case 'down':
				$direction = 'bottom';
				$o.css({left:'0px', bottom:'0px', height:($h*$size+$bw)+'px'});
				break;
			default:
				alert('error direction');
		};
		if($opt.direction == 'left' || $opt.direction == 'right'){
			if($($self).width() > ($w * $size)){
				return false;
			};
		};
		if($opt.direction == 'up' || $opt.direction == 'down'){
			if($($self).height() > ($h * $size)){
				return false;
			};
		};
		$o.hover(function(){
			if(!$opt.mouseStop)return false;
			$xRoll_flag = false;
		}, function(){
			$xRoll_flag = true;
		}).trigger('mouseleave');
		$timer = setInterval(function(){
			if(!$xRoll_flag)return false;
			switch($opt.direction){
				case 'left':
					$oc = $o.find('li:lt('+ $opt.item +')');
					$o.animate({left: - ($w * $opt.item) + 'px'}, $opt.speed, function(){
						$o.css({left:'0px'});
						$oc.appendTo($o);
					});
					break;
				case 'right':
					$oc = $o.find('li:gt('+ ($size - $opt.item - 1) +')');
					$o.animate({right: - ($w * $opt.item) + 'px'}, $opt.speed, function(){
						$o.css({right:'0px'});
						$o.prepend($oc);
					});
					break;
				case 'up':
					$oc = $o.find('li:lt('+ $opt.item +')');
					$o.animate({top: - ($h * $opt.item) + 'px'}, $opt.speed, function(){
						$o.css({top:'0px'});
						$oc.appendTo($o);
					});
					break;
				case 'down':
					$oc = $o.find('li:gt('+ ($size - $opt.item - 1) +')');
					$o.animate({bottom: - ($h * $opt.item) + 'px'}, $opt.speed, function(){
						$o.css({bottom:'0px'});
						$o.prepend($oc);
					});
					break;
				default:
					alert('error direction');
			};
		}, $opt.time);
		return this;
	};
})(jQuery);