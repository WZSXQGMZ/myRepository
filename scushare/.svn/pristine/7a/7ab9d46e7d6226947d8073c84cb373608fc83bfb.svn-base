function checkChathead(){
	if($("#file").val() == ''){
		alert("请选择文件");
		return false;
	}
	if(document.getElementById("file").files[0].size > (1024*1024*2)){
		alert("文件大小不能超过2MB");
		return false;
	}
	var postfix = $("#file").val().toLowerCase().split('.').splice(-1);
	if(postfix != "jpg" && postfix != "jpeg" && postfix != "gif" && postfix != "png"){
		alert("请选择图片文件");
		return false;
	}
	return true;
}