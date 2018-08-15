package com.scushare.entity;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class UserSearchKeyword implements Serializable{
	private String key_word;
	private int search_times;
	private Date search_date;
	public String getKey_word() {
		return key_word;
	}
	public void setKey_word(String key_word) {
		this.key_word = key_word;
	}
	public int getSearch_times() {
		return search_times;
	}
	public void setSearch_times(int search_times) {
		this.search_times = search_times;
	}
	public Date getSearch_date() {
		return search_date;
	}
	public void setSearch_date(Date search_date) {
		this.search_date = search_date;
	}
}
