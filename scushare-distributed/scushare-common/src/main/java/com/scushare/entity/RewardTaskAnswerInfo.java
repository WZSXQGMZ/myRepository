package com.scushare.entity;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class RewardTaskAnswerInfo implements Serializable{
	private Integer answer_id;
	private Integer user_id;
	private Integer task_id;
	private Integer file_id;
	private Date date;
	
	public Integer getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(Integer answer_id) {
		this.answer_id = answer_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public Integer getFile_id() {
		return file_id;
	}
	public void setFile_id(Integer file_id) {
		this.file_id = file_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
