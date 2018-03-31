function infoCheck(form){
	
	if(form.usrname.value == null) {
		return false;
	} else if(form.usrsex.value == null){
		return false;
	} else if(form.usrage.value == null){
		return false;
	} else if(form.vocation.value == null){
		return false;
	} else if(form.usrtel.value == null){
		return false;
	} else if(form.email.value == null){
		return false;
	} else if(form.address.value == null){
		return false;
	}
	
	return true;
}

function newPassConfirm(form){
	alert("123");
	if(form.newPass.value != null){
		if(form.newPass.value == form.newPassConfirm.value) {
			return true;
		} else {
			alert("新密码确认失败");
			return false;
		}
	} else {
		alert("新密码不能为空");
		return false;
	}
}