package com.scushare.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserLogService {
	
	UserLoginStatu login(String uname, String pwd);
	UserLoginStatu login(HttpServletRequest request);

	void addLoginStatu(String user_name, HttpServletRequest request, HttpServletResponse response);
	void updateLoginStatu(HttpServletRequest request);
	public void logout(HttpServletRequest request, HttpServletResponse response);
}
