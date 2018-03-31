function checkUserInfo(){
	if($("#first").find("option:selected").text() == "请选择"){
		alert("请选择学院");
		return false;
	}
	if($("#second").find("option:selected").text() == "请选择"){
		alert("请选择专业");
		return false;
	}
	if($("#user_phone_num").val() == ''){
		alert("请填写手机");
		return false;
	}
	if(!(/^1[3|4|5|8][0-9]\d{8}$/.test($("#user_phone_num").val()))){ 
		alert("请输入11位手机号"); 
		return false; 
	}
}
