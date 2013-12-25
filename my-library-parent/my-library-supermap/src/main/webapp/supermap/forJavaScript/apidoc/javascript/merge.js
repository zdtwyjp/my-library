(function() {
    var content = document.body.childNodes[1];
    content.className += " container";
  
    $('.container .navbar .navbar-inner .container .nav #smtitle').text('首页');
	$('.container .navbar .navbar-inner .container .nav #smintro').text('产品介绍');
	$('.container .navbar .navbar-inner .container .nav #smdev').text('开发指南');
	$('.container .navbar .navbar-inner .container .nav #smexample').text('示范程序');
	$('.container .navbar .navbar-inner .container .nav #smdoc').text('类参考');
	$('.container .navbar .navbar-inner .container .nav #smtopic').text('技术专题');
	$('.container .navbar .navbar-inner .container .nav #smtopic1').text('动态分段专题');
	$('.container .navbar .navbar-inner .container .nav #smtopic2').text('矢量渲染专题');
	$('.container .navbar .navbar-inner .container .nav #smtopic3').text('离线缓存与 APP 专题');
})();
