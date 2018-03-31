package com.books.model;

import java.sql.*;

import com.books.model.Tools;;

public class Return {
	Connection ct = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	/**
	 * 连接数据库修改图书归还信息
	 * @param borrowid 唯一借阅编号，负责从数据库中检索相应信息
	 * @param dateNow 当前系统日期，设定归还时间
	 * @param fine 设定罚金
	 * @return 返回boolean类型表示是否归还成功
	 */
	public boolean returnbook(String borrowid, String usrid, String bookid, String ISBN, String dateNow, int fine) {
		boolean result = false;
		//连接数据库
		try {
			ct = Tools.getConnection();
			st = ct.createStatement();
			String sql = "UPDATE borrowinfo SET returndate='" + dateNow + "',"
					+ "hasreturn='1',fine='" + fine + "' WHERE borrowid='" + borrowid + "'";
			String sql0 = "select avaliable from bookquantity where ISBN='" + ISBN + "'";
			String sql1 = "select hasborrow from userinfo where usrid='" + usrid + "'";
			int a = st.executeUpdate(sql);
			if (a!=0){
				sql = "UPDATE bookinlib SET bookinlib='" + 1 + "' WHERE bookid='" + bookid + "'";
				a = st.executeUpdate(sql);
				if(a!=0){
					rs = st.executeQuery(sql0);
					if (rs.next()){
						int avaliable = rs.getInt(1);
						sql = "UPDATE bookquantity SET avaliable='" + (avaliable+1) + "' WHERE ISBN='" + ISBN + "'";
						a = st.executeUpdate(sql);
						if (a!=0){
							rs = st.executeQuery(sql1);
							if (rs.next()){
								int hasborrow = rs.getInt(1);
								sql = "UPDATE userinfo SET hasborrow='" + (hasborrow-1) +"' WHERE usrid='" + usrid + "'";
								a = st.executeUpdate(sql);
								if (a!=0){
									result = true;
								}
							}
						}
					}
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
		return result;
	}

}
