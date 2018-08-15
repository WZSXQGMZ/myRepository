package com.scushare.entity;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class RewardTaskInfo implements Serializable{
	private Integer task_id;
	private Date date;
	private Integer user_id;
	private String title;
	private String description;
	private String keywords;
	private Integer is_closed = 0;
	private Integer answer_id;
	private Integer reward = 0;
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Integer getIs_closed() {
		return is_closed;
	}
	public void setIs_closed(Integer is_closed) {
		this.is_closed = is_closed;
	}
	public Integer getAnswer_id() {
		return answer_id;
	}
	public void setAnswer_id(Integer answer_id) {
		this.answer_id = answer_id;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
