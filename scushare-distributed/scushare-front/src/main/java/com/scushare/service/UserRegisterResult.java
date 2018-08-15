package com.scushare.service;

/**
 * 注册结果枚举类
 *
 */
public enum UserRegisterResult {
	REGISTER_SUCCESS,
	MAIL_CHECK_SUCCESS,
	USER_INFO_ERROR,
	DB_INSERT_ERROR,
	MAIL_SEND_ERROR,
	MAIL_CHECK_ERROR,
	MAIL_OVERDUE_ERROR,
	UNDEFINED_ERROR,
}
