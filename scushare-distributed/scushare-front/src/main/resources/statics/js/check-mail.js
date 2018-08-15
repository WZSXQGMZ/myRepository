function checkMail(mail){
	if(mail == ""){
		return false;
	}
	
	var pattern = /[0-9A-z]+@[0-9A-z]+\.[A-z]+/;
	return pattern.test(mail);
}
