package com.scushare.service;

import javax.servlet.http.HttpServletRequest;

public interface ModifyPasswordService {
	
	public boolean modify(String mpassword,String mname);
	public boolean modifyPassWord(HttpServletRequest request);

}
