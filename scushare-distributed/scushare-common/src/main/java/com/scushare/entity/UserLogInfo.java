package com.scushare.entity;

import java.io.Serializable;

/**
 * �û���½��Ϣ��
 * �����û�ID���û��������롢ͷ��·��
 *
 */
public class UserLogInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user_name;
	private String user_password;
	private String user_limit;
	public String getUser_limit() {
		return user_limit;
	}
	public void setUser_limit(String user_limit) {
		this.user_limit = user_limit;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	
	
	
}
