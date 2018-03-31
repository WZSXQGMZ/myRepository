package com.books.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.books.model.Tools;

public class UserLoginCl {
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	public String userid = "";
	
	/**
	 * 从数据库中检索用户信息是否合法
	 * @param username 用户名，String
	 * @param password 密码，String
	 * @return 返回String类型表示账号密码是否合法
	 */
	public String checkuser(String username, String password) {
		String flag = "";
		String sql = "select usrid,usrpassword from useraccount where usraccount='" + username + "'";
		try {
			//调用数据库连接
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()){
				if (password.equals(rs.getString(2))){
					//合法
					flag = "1";
					userid = rs.getString(1);
				}else{
					//密码不合法
					flag = "2";
				}
			}else{
				//用户名不合法
				flag = "3";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				Tools.CloseDB(ct, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

}
