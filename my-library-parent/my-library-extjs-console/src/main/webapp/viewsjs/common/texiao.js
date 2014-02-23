 var closetime;
function showmsg(showtime){
    var md=document.getElementById("msgdiv");
    md.style.display="block";
    if(closetime)window.clearTimeout(closetime);
    closetime=setTimeout(hiddenmsg,showtime*1000);    
}
function hiddenmsg(){
    var md=document.getElementById("msgdiv");
    md.innerHTML = "";
    md.visibility = "none";
}