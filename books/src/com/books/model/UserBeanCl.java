package com.books.model;

import java.sql.*;
import java.util.*;

import com.books.model.UserBean;
import com.books.model.Tools;

public class UserBeanCl {
	Connection ct = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	int pageCount = 0;
	
	/**
	 * 获取单行修改信息
	 * @param usrid 用户编号，方便从数据库中查询该用户信息
	 * @return 返回UserBean类型，用来存储该用户信息
	 */
	public UserBean getModifyInfo(String usrid){
		UserBean ub = new UserBean();
		String sql = "select * from useraccount natural join userinfo where usrid='" + usrid + "'";
		try {
			ct=Tools.getConnection();
			st=ct.createStatement();
			rs = st.executeQuery(sql);
			
			while (rs.next()) {
				ub.setUsrid(rs.getString(1));
				ub.setUsraccount(rs.getString(2));
				ub.setUsrpassword(rs.getString(3));
				ub.setUsrname(rs.getString(4));
				ub.setUsrsex(rs.getString(5));
				ub.setUsrage(rs.getInt(6));
				ub.setVocation(rs.getString(7));
				ub.setUsrtel(rs.getString(8));
				ub.setEmail(rs.getString(9));
				ub.setAddress(rs.getString(10));
				ub.setUsrcredit(rs.getInt(11));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				Tools.CloseDB(ct, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ub;
	}
	
	/**
	 * 修改用户信息
	 * @param usrid 用户编号
	 * @param usraccount 用户名
	 * @param usrpassword 密码
	 * @param usrname 姓名
	 * @param usrsex 性别
	 * @param usrage 年龄
	 * @param vocation 职业
	 * @param usrtel 电话
	 * @param email 邮箱
	 * @param address 地址
	 * @param usrcredit 信誉度
	 * @return 返回boolean类型表示是否修改成功
	 */
	//执行单行修改操作
	public boolean getModuser(String usrid, String usraccount, String usrpassword, String usrname, String usrsex, String usrage, String vocation, String usrtel, String email, String address, String usrcredit) {
		boolean result=false;
		
		String sql1="update userinfo set usrname='"+usrname+"',usrsex='"+usrsex+"',usrage='"+usrage+"',vocation='"+vocation+"',usrtel='"+usrtel+"',email='"+email+"',address='"+address+"',usrcredit='"+usrcredit+"' where usrid='"+usrid+"'";
		String sql2="update useraccount set usraccount='"+usraccount+"',usrpassword='"+usrpassword+"' where usrid='" + usrid+"'";
		try {
			ct=Tools.getConnection();
			st=ct.createStatement();
			int i=st.executeUpdate(sql1);
			int j=st.executeUpdate(sql2);
			if(i==1 && j==1){
				result=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				Tools.CloseDB(ct, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 负责删除用户信息
	 * @param usrid 用户id，负责从数据库中检索到该条数据删除
	 * @return 返回boolean类型表示是否删除成功
	 */
	public boolean getDeluser(int usrid) {
		String sql1="delete from userinfo where usrid="+usrid;
		String sql2="delete from useraccount where usrid="+usrid;
		boolean b=false;
		//连接数据库
		try {
			ct=Tools.getConnection();
			st=ct.createStatement();
			int i=st.executeUpdate(sql1);
			int j=st.executeUpdate(sql2);
			if(i==1 && j==1){
				b=true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				Tools.CloseDB(ct, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return b;
	}
	
	/**
	 * 获取页数
	 * @param pageSize 每页所显示的最大数目
	 * @return 返回int类型表示页数
	 */
	public int getPageCount(int pageSize) {
		try {
			ct = Tools.getConnection();
			st = ct.createStatement();
			String sql = "select count(*) from userinfo";
			rs = st.executeQuery(sql);
			if (rs.next()){
				int rowCount = rs.getInt(1);
				if (rowCount%pageSize == 0){
					pageCount = rowCount / pageSize;
				}else{
					pageCount = rowCount / pageSize + 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				Tools.CloseDB(ct, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pageCount;
	}
	
	/**
	 * 得到用户分页显示信息，返回一个ArrayList集合
	 * @param pageSize 表示每一页要显示的条目数，是一个Integer类型
	 * @param pageNow 表示当前要显示第几页
	 * @return 返回一个ArrayList集合
	 * @throws SQLException 
	 */
	public ArrayList<UserBean> getPage(int pageSize, int pageNow) {
		ArrayList<UserBean> list = new ArrayList<UserBean>();
		
		//连接数据库
		try {
			ct = Tools.getConnection();
			st = ct.createStatement();
			String sql = "select * from useraccount NATURAL JOIN userinfo limit "+ pageSize*(pageNow-1) + "," + pageSize;
			rs = st.executeQuery(sql);
			
			while (rs.next()) {
				UserBean ub = new UserBean();
				ub.setUsrid(rs.getString(1));
				ub.setUsraccount(rs.getString(2));
				ub.setUsrpassword(rs.getString(3));
				ub.setUsrname(rs.getString(4));
				ub.setUsrsex(rs.getString(5));
				ub.setUsrage(rs.getInt(6));
				ub.setVocation(rs.getString(7));
				ub.setUsrtel(rs.getString(8));
				ub.setEmail(rs.getString(9));
				ub.setAddress(rs.getString(10));
				ub.setUsrcredit(rs.getInt(11));
				list.add(ub);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				Tools.CloseDB(ct, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
