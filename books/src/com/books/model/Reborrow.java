package com.books.model;

import java.sql.*;

import com.books.model.Tools;;

public class Reborrow {
	Connection ct = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	/**
	 * 连接数据库，将应当归还图书的时间增加
	 * @param borrowid 唯一借阅编号，负责从数据库中检索相应信息
	 * @param shouldreturn 应当归还的日期
	 * @return 返回boolean类型标明是否修改成功
	 */
	public boolean reborrow(String borrowid, String shouldreturn) {
		String date = Tools.getSpecifiedDayAfter(shouldreturn, 7);
		//连接数据库
		try {
			ct = Tools.getConnection();
			st = ct.createStatement();
			String sql = "UPDATE borrowinfo SET shouldreturndate='" + date + "' WHERE borrowid='" + borrowid + "'";
			if (st.executeUpdate(sql)!=0){
				return true;
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
		return false;
	}
}
