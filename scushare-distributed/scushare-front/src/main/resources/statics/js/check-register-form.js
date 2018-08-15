function checkRegForm(){

	if(!checkName()){
		return false;
	}

	if(!checkPwd()){
		return false;
	}
	if($("#user_mail").val() == ''){
		alert("请填写邮箱");
		return false;
	}
	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+\.([a-zA-Z0-9])+$/;
	if(!pattern.test($("#user_mail").val())){
		alert("请填写正确格式的邮箱");
		return false;
	}
	if($("#user_phone_num").val() == ''){
		alert("请填写手机");
		return false;
	}
	if(!(/^1[3|4|5|8|7][0-9]\d{8}$/.test($("#user_phone_num").val()))){ 
		alert("请输入11位手机号"); 
		return false; 
	}
	if($("#user_grade").find("option:selected").text() == "请选择"){
		alert("请选择年级");
		return false;
	}
	if($("#first").find("option:selected").text() == "请选择"){
		alert("请选择学院");
		return false;
	}
	if($("#second").find("option:selected").text() == "请选择"){
		alert("请选择专业");
		return false;
	}
	
	if(!nameNotExisted()){
		alert("用户名已存在");
		return false;
	}
	if(!mailNotExisted()){
		alert("邮箱已被注册");
		return false;
	}
	
	return true;
}

function checkPwd(){
	
	if($("#user_password").val() == ''){
		alert("请填写密码");
		return false;
	}
	if($("#user_password").val().length < 4 || $("#user_password").val().length > 16){
		alert("密码应在4-16位");
		return false;
	}
	var reg = /^[0-9A-Za-z]+$/;
	if(!reg.test($("#user_password").val())){
		alert("密码只能由数字和字母构成");
		return false;
	}
	if($("#user_password").val() != $("#user_password_confirm").val()){
		alert("两次密码输入不一致");
		return false;
	}
	return true;
}


function submitCheck(){
	if(checkRegForm()){
		return true;
	}
	return false;
}

function checkName(){
	if($("#user_name").val() == ''){
		alert("请填写账号");
		return false;
	}
	if($("#user_name").val().length > 20){
		alert("账号长度不能超过20个字");
		return false;
	}
	var containSpecial = RegExp(/[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/);
	if(containSpecial.test($("#user_name").val())){
		alert("账号不能包含特殊字符");
		return false;
	}
	
	return true;
}

function nameNotExisted(){
	var request_url = "checkUserNameExisted?user_name=" + $("#user_name").val();
	var result = "";
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.open("GET",request_url,false);
	xmlhttp.send();
	result = xmlhttp.responseText;
	if(result != "true"){
		return false;
	}
	return true;
}
function mailNotExisted(){
	var request_url = "checkUserMailExisted?user_mail=" + $("#user_mail").val();
	var result = "";
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.open("GET",request_url,false);
	xmlhttp.send();
	result = xmlhttp.responseText;
	if(result != "true"){
		return false;
	}
	return true;
}
