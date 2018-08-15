function checkLoginForm(){
	if($("#user_name").val() == ''){
		alert("请输入用户名");
		return false;
	}
	if($("#user_name").val().length > 20){
		alert("用户名在20字以内");
		return false;
	}
	if($("#user_password").val() == ''){
		alert("请输入密码");
		return false;
	}
	if($("#user_password").val().length < 4 || $("#user_password").val().length > 16){
		alert("密码应在4-16位");
		return false;
	}
	return true;
}