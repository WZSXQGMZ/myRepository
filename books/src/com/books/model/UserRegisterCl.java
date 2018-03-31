package com.books.model;

import java.sql.*;

import com.books.model.Tools;

public class UserRegisterCl {
	Connection ct = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	/**
	 * 连接数据库添加用户信息
	 * @param username 用户名
	 * @param password 密码
	 * @param truename 姓名
	 * @param sex 性别
	 * @param age 年龄
	 * @param vocation 职业
	 * @param tel 联系电话
	 * @param email 邮箱
	 * @param address 地址
	 * @return 返回boolean类型判断是否注册成功
	 */
	public boolean register(String username, String password, String truename, String sex,
			int age, String vocation, String tel, String email, String address) {
		boolean result = false;
		boolean a = false;
		boolean b = false;
		int row_count = 0;
		int id = 0;
		
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement("select usrid from userinfo order by usrid desc limit 1");
			rs = ps.executeQuery();
			
			if (rs.next()){
				id = Integer.valueOf(rs.getString(1)) + 1;
			} else{
				id = 1;
			}
			
			//添加用户个人信息
			String sql1 = "insert ignore into userinfo values ('" + 
					String.format("%04d", id) + "','" + truename + "','" + sex + "','" + age + "','" 
					+ vocation + "','" + tel + "','" + email + "','" + address + "','" + 10 
					+ "','" + 0 + "')";
			ps = ct.prepareStatement(sql1);
			row_count = ps.executeUpdate();
			//检测是否添加成功
			if (row_count!=0)
				a = true;
			
			//添加用户账户信息
			String sql2 = "insert ignore into useraccount values ('" + 
					String.format("%04d", id) + "','" + username + "','" + password + "')";
			ps = ct.prepareStatement(sql2);
			row_count = ps.executeUpdate();
			//检测是否添加成功
			if (row_count!=0)
				b = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				Tools.CloseDB(ct, ps, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (a && b)
			result = true;
		return result;
	}
}
