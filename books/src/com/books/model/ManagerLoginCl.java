package com.books.model;

import java.sql.*;

import com.books.model.Tools;

public class ManagerLoginCl {
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * 从数据库中检索管理员登录信息
	 * @param managername 管理员用户名，String类型
	 * @param password 管理员密码，String类型
	 * @return 返回String类型数据，判断是否合法
	 */
	public String checkmanager(String managername, String password) {
		String flag = "";
		String sql = "select managerpassword from manageraccount where manageraccount='" + managername + "'";
		try {
			//调用数据库连接
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()){
				if (password.equals(rs.getString(1))){
					//合法
					flag = "1";
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
