(function($){	$.fn.xSlider = function(opt){		if(!$(this).size()){			return false;		};		var $vertion = '3.0';		var $this = $(this);		var $slider = $this;		var $size = $('a', $slider).size();		if($size<1)return false;		var $opt = $.extend({btn: true, thum: true, auto: true, time: 1500, speed: 200}, opt);		var $animateFlag = false;		var _thumHTML = '';		var _thumTEXT = '';		if($opt.thum){			var $tmpTxt = '';			var $tmpArr = ['',''];			$('a', $this).each(function(){				_thumHTML += '<li><a href="javascript:void(0)"><img src="'+ $('img', this).attr('thum') +'" alt=""/></a></li>';				$tmpTxt = $(this).attr('rel');				if($tmpTxt == undefined || $tmpTxt==''){					$tmpArr[0] = '';					$tmpArr[1] = '';				};				if($tmpTxt.indexOf('$$') > -1){					$tmpArr = $tmpTxt.split('$$');				}else{					$tmpArr[0] = $tmpTxt;				};				_thumTEXT += '<div class="sliderTextItem" style="display:none;"><h3>'+ $tmpArr[0] +'</h3><p>'+ $tmpArr[1] +'</p></div>';			});			_thumHTML = '<div class="sliderThum"><div class="sliderThumBG"></div><div class="sliderText">'+ _thumTEXT +'</div><ul class="sliderThumList">'+ _thumHTML +'</ul></div>';		};		if($opt.btn)_thumHTML += '<a href="javascript:void(0)" class="sliderPrev" title="\u4e0a\u4e00\u5f20" alt="" /></a><a href="javascript:void(0)" class="sliderNext" title="\u4e0b\u4e00\u5f20" alt="" /></a>';		$slider.wrapInner('<div class="sliderBox"></div>').append(_thumHTML);		var $i = 0;		var $isstop = false;		var $width = $(this).width();		var $height = $(this).height();		var $sliderBox = $('.sliderBox', $this).css({width: ($width * $size + 10), height: $height});		var $sliderThum = $('.sliderThum', $this);		$('a, img', $sliderBox).css({width: $width, height: $height});					$slider.hover(function(){			$isstop = true;		}, function(){			$isstop = false;		});		$this.roll = function(){			if($opt.thum){				$('li', $sliderThum).removeClass('selected').eq($i).addClass('selected');				$('.sliderTextItem', $sliderThum).hide().eq($i).show();			};			$('.sliderBox', $this).animate({marginLeft: - ($width * $i) }, $opt.speed, function(){				$animateFlag = false;			});		};		if($opt.btn){			$('.sliderPrev', $this).click(function(){				$i--;				if($i<0)$i = ($size - 1);				$this.roll();			});			$('.sliderNext', $this).click(function(){				$i++;				if($i > ($size - 1))$i=0;				$this.roll();			});		};		if($opt.thum){			$('li', $sliderThum).click(function(){				var _this = this;				if($animateFlag)return true;				$i = $(_this).index();				$this.roll();				$animateFlag = true;			}).eq(0).addClass('selected');			$('.sliderTextItem', $sliderThum).eq(0).show();		};		if($opt.auto){			$this.$autoplay = setInterval(function(){				if($isstop)return false;				$i++;				if($i > ($size - 1))$i=0;				$this.roll();			}, $opt.time);		};		return this;	};})(jQuery);