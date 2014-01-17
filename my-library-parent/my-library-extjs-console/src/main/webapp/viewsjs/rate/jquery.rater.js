jQuery.fn.rater = function(options)
{
	var settings = {
		active    : true, //是否激活投票功能
		maxvalue  : 10,   //星星的个数
		curvalue  : 0,     //激活的星星个数
		style     : 'normal',  //默认样式(25px)
		info	  : ''
	};
	//如果options有数值,复制给settings
	if(options) { jQuery.extend(settings, options); };
	level = settings.curvalue;
	var container = jQuery(this);
	jQuery.extend(container, { averageRating: settings.curvalue });

	//默认样式(25px)
	if(!settings.style || settings.style == null || settings.style == 'normal') {
		var raterwidth = settings.maxvalue * 25;
		var ratingparent = '<ul class="star-rating" style="width:'+raterwidth+'px">';
	}
	//小星星(10px)
	if(settings.style == 'small') {
		var raterwidth = settings.maxvalue * 10;
		var ratingparent = '<ul class="star-rating small-star" style="width:'+raterwidth+'px">';
	}
	//将投票插件添加在同一行(25px)
	if(settings.style == 'inline-normal') {
		var raterwidth = settings.maxvalue * 25;
		var ratingparent = '<span class="inline-rating"><ul class="star-rating" style="width:'
			+raterwidth+'px">';
	}
	//将投票插件添加在同一行(10px)
	if(settings.style == 'inline-small') {
		var raterwidth = settings.maxvalue * 10;
		var ratingparent = '<span class="inline-rating"><ul class="star-rating small-star" style="width:'
			+raterwidth+'px">';
	}
	container.append(ratingparent);
	var desc = settings.info.split(",");
	var starWidth, starIndex, listitems = '';
	var curvalueWidth = Math.floor(100 / settings.maxvalue * settings.curvalue);
	if(settings.active) { //允许投票
		for(var i = 0; i <= settings.maxvalue ; i++) {
			if(settings.info.length > 0){
				if (i == 0) { //创建激活的星星
					listitems+='<li class="current-rating" style="width:'+curvalueWidth+'%;" title="'
					+settings.curvalue+'/'+settings.maxvalue+'">'
					+settings.curvalue+'/'+settings.maxvalue+'</li>';
				} else {
					starWidth = Math.floor(100 / settings.maxvalue * i);
					starIndex = (settings.maxvalue - i) + 2;
					//'#'的作用是为了防止页面跳转,方便ajax应用
					listitems+='<li class="star"><a href="#'+i+'" title="'+desc[i-1]
						+'" style="width:'+starWidth+'%;z-index:'+starIndex+'">'+i+'</a></li>';
				}
			}else{
				if (i == 0) { //创建激活的星星
					listitems+='<li class="current-rating" style="width:'+curvalueWidth+'%;" title="'
					+settings.curvalue+'/'+settings.maxvalue+'">'
					+settings.curvalue+'/'+settings.maxvalue+'</li>';
				} else {
					starWidth = Math.floor(100 / settings.maxvalue * i);
					starIndex = (settings.maxvalue - i) + 2;
					//'#'的作用是为了防止页面跳转,方便ajax应用
					listitems+='<li class="star"><a href="#'+i+'" title="'+i+'/'+settings.maxvalue
						+'" style="width:'+starWidth+'%;z-index:'+starIndex+'">'+i+'</a></li>';
				}								
			}
		}
	} else { //不允许投票
		listitems+='<li class="current-rating" style="width:'+curvalueWidth+'%;" title="'
				+settings.curvalue+'/'+settings.maxvalue+'">'
				+settings.curvalue+'/'+settings.maxvalue+'</li>';
	}
	container.find('.star-rating').append(listitems);
	//原来的js文件没有下面一行,我觉得应该加上.
	container.find('.star-rating').append('</ul>');

	//这一行的作用不清楚,如果不加的话会导致显示混乱
	container.append('<span class="star-rating-result"></span>'); 

	var stars = jQuery(container).find('.star-rating').children('.star');//获得所有的.star
	stars.click(function()
	{
		raterValue = jQuery(this).children('a')[0].href.split('#')[1];
		//在这里可以进行具体的操作,比如说调用ajax方法等.这里只是弹出了所选的按钮值,
		//然后不再允许投票
		container.find('.star-rating').remove();
		container.find('.inline-rating').remove();
		container.rater({active:true,maxvalue:settings.maxvalue,curvalue:raterValue,style:settings.style,info:settings.info});
//		alert(raterValue);
		level = raterValue;
		return false;
	});
};