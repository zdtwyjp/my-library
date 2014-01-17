var replyEditor_DOM = '<div class="cEditor" id="cEditor"><form id="cReplyForm" action="?act=reply" method="post"><input type="hidden" id="cid" value="$cid"/><textarea id="cReplyTxt" maxlength="500"></textarea><div class="fm-box"><div class="commentUploader"><iframe frameborder="0" src="/advise/upload?t=reply" width="70px" height="25px"></iframe><span class="uploadstatus" id="cReply_uploderStatus"></span></div><input type="hidden" id="cReplyImg" value=""/><input type="submit" class="btnSubmit" value="\u56de\u590d"/></div></form>';

var addEditor_DOM = '<div class="cListBox" id="cListBox"><p class="commentStatus" id="commentStatus">评论载入中....</p></div><div class="cAdd" id="cAdd"><h2>你要说的内容&nbsp;<span>（最多&nbsp;<b id="cAddCounter">500</b>&nbsp;字）</span></h2><div class="cAddEditor" id="cAddEditor"><form id="cAddForm" action="?act=add" method="post"><textarea id="cAddTxt" maxlength="500"></textarea><div class="fm-box"><input type="hidden" id="cAddImg" value=""/><input type="submit" class="btnSubmit" value="\u53d1\u8868\u8bc4\u8bba"/></div></form></div></div>';
/**
  functions
*******************/
String.prototype.xEnCode = function(){
	return encodeURI(this);
};
String.prototype.xDeCode = function(){
	return unescape(decodeURI(this).replace(/\+/g, ' ').replace(/\n/g, '<br/>'));
};

function comment_Reply(v){
	if($('#cr_'+ v).hasClass('selected')){
		$('#cr_'+ v).removeClass('selected');
		$('#cEditor').remove();
	}else{
		var DOM = replyEditor_DOM;
		DOM = DOM.replace(/\$cid/g, v);
		var isReply = $('#rid_'+ v).html()!='' ? true : false;
		$('.cReply').removeClass('selected');
		$('#cEditor').remove();
		$('#cr_'+ v).addClass('selected').append(DOM);
		$('#cReplyTxt').maxlength();
		$('#cReplyForm').submit(function(){
			var cid = v;
			var txt = $('#cReplyTxt').val();
			var img = $('#cReplyImg').val();
			if(txt==''){
				alert('请填写评论内容');
				$('#cReplyTxt').focus();
				return false;
			};
			if(txt.length>500){
				alert('评论内容不超过 500 个字符');
				$('#cReplyTxt').focus();
				return false;
			};
			txt = txt.xEnCode();
			$.ajax({
				type: 'POST',
				cache: false,
				url: '/advise/reply',
				data: {tid: cid, img_file_reply: img,reply_txt: txt, re_projectid:project_id},
				success: function(data){
					$('#cr_'+ v).removeClass('selected');
					$('#cEditor').remove();
					if(!isReply){
						$('#rid_'+ v).append('<ul class="rList">'+ data +'</ul>');
					}else{
						$('#rid_'+ v).find('ul').append(data);
					};
				}
			});
			return false;
		});
		$('#cReplyTxt').focus();
	};
};

function comment_Add(){
	var txt = $('#cAddTxt').val();
	var img = $('#cAddImg').val();
	if(txt==''){
		alert('请填写评论内容');
		$('#cAddTxt').focus();
		return false;
	};
	if(txt.length>500){
		alert('评论内容不超过 500 个字符');
		$('#cAddTxt').focus();
		return false;
	};
	txt = txt.xEnCode();
	$.ajax({
		type: 'POST',
		cache: false,
		url: addCommentURL,
		data: {img_file: img, content: txt,projectSn:project_id},
		success: function(data){
			if(data == 'no_user'){
				alert('请先登录!');
			    showOpenLoginDiv();
			    $(document).scrollTop(0);				
				return;
			}
			$("#commentListBox").css("display","");
			if($('#commentStatus').size()){
				$('#cListBox').html('<ul class="cList" id="cList"></ul>');
			};
			//alert(data);
			$('#cList').prepend(data).find('li').last().find('a.btnReply').click(function(){
				var v = $(this).attr('rel');
				comment_Reply(v);
				return false;
			});
			$('#cAddCounter').html('500');
			$('#cAddForm').get(0).reset();
			$('#cAddTxt').val('');
			$('#cAddImg').val('');
			$('#cAdd_uploderStatus').html('');
			//comment_Page();
		}
	});
};

function comment_Page(i){
	if(i!=''){
		$('#commentPageStatus').html('正在加载...');
	}else{
		i = 0;
	};
	// load page
	$.ajax({
		type: 'GET',
		cache: false,
		url: commentURL+'&currentPage='+ i,
		success: function(data){
			$('#cListBox').html(data);
			comment_Event();
		}
	});
};

function comment_Event(){
	// bind Reply
	$('a.btnReply').unbind().click(function(){
		var v = $(this).attr('rel');
		comment_Reply(v);
		return false;
	});
};

function comment_Load(){
	var DOM = addEditor_DOM;
	$('#commentBox').html(DOM);
	$('#cAddTxt').maxlength({'feedback':'#cAddCounter'});

	// bind Reply
	comment_Event();
	
	// bind add
	$('#cAddForm').submit(function(){
		comment_Add();
		return false;
	});
	
	// load
	comment_Page('');
};