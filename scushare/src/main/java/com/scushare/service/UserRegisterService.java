package com.scushare.service;

import javax.servlet.http.HttpServletRequest;

public interface UserRegisterService {
	/**
	 * 此函数用于将form提交的注册信息进行处理后保存到数据库
	 * @param request 携带注册信息的request
	 * @return 返回注册结果
	 */
	UserRegisterResult register(HttpServletRequest request);
	
	/**
	 * 函数用于验证邮箱确认连接中的数据
	 * @param request 携带数据参数的request
	 */
	UserRegisterResult mailCheck(HttpServletRequest request);
	
	boolean userNameExistedCheck(String uesr_name);
	boolean userMailExistedCheck(String uesr_mail);
}
