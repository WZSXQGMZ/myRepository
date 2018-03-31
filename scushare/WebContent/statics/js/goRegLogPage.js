function goRegister(){
	var currUrl = window.location.href;
	window.location = "/scushare/loginPage?originUrl="+currUrl.substring(currUrl.lastIndexOf("/") + 1);
	window.event.returnValue=false;
}

function goLoginPage(){
	var currUrl = window.location.href;
	window.location = "/scushare/loginPage?originUrl="+currUrl.substring(currUrl.lastIndexOf("/") + 1);
	window.event.returnValue=false;
}