package com.scushare.entity;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserInfo implements Serializable{
	private String user_gender;
	private String user_college;
	private String user_major;
	private String user_mail;
	private String user_phone_num;
	private Date user_reg_time;
	private String user_name;
	private String user_chathead;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_chathead() {
		return user_chathead;
	}
	public void setUser_chathead(String user_chathead) {
		this.user_chathead = user_chathead;
	}
	public String getUser_gender() {
		return user_gender;
	}
	public void setUser_gender(String user_gender) {
		if(user_gender.equals("1")) {
			this.user_gender = "ÄÐ";
		}else {
			this.user_gender = "Å®";
		}
	}
	public String getUser_college() {
		return user_college;
	}
	public void setUser_college(String user_college) {
		this.user_college = user_college;
	}
	public String getUser_major() {
		return user_major;
	}
	public void setUser_major(String user_major) {
		this.user_major = user_major;
	}
	public String getUser_mail() {
		return user_mail;
	}
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	public String getUser_phone_num() {
		return user_phone_num;
	}
	public void setUser_phone_num(String user_phone_num) {
		this.user_phone_num = user_phone_num;
	}
	public Date getUser_reg_time() {
		return user_reg_time;
	}
	public void setUser_reg_time(Date user_reg_time) {
		this.user_reg_time = user_reg_time;
	}
}
