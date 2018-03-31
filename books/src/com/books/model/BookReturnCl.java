package com.books.model;

import java.sql.*;
import java.util.ArrayList;

import com.books.model.Tools;

public class BookReturnCl {
	Connection ct = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement ps = null;
	
	/**
	 * 从数据库中检索出用户借阅书籍列表
	 * @param userid 用户id，负责从数据库中检索相应信息
	 * @return 返回ArrayList数组存储数据
	 */
	public ArrayList<BookReturn> getAllBorrow(String userid) {
		ArrayList<BookReturn> list = new ArrayList<BookReturn>();
		
		//连接数据库
		try {
			ct = Tools.getConnection();
			st = ct.createStatement();
			String sql = "select borrowid,bookid,ISBN,bookname,borrowdate,shouldreturndate,returndate,hasreturn,fine "
					+ "from borrowinfo bwi natural join bookinlib bil natural join bookinfo bi "
					+ "where bil.ISBN=bi.ISBN and bwi.usrid='" + userid + "'";
			rs = st.executeQuery(sql);
			
			while (rs.next()){
				BookReturn br = new BookReturn();
				br.setBorrowid(rs.getString(1));
				br.setBookid(rs.getString(2));
				br.setISBN(rs.getString(3));
				br.setBookname(rs.getString(4));
				br.setBorrowdate(rs.getString(5));
				br.setShouldreturn(rs.getString(6));
				br.setReturndate(rs.getString(7));
				br.setHasreturn(rs.getInt(8));
				br.setFine(rs.getInt(9));
				list.add(br);
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
	
	public ArrayList<BookReturn> getHasBorrow(String userid) {
		ArrayList<BookReturn> list = new ArrayList<BookReturn>();
		
		//连接数据库
		try {
			ct = Tools.getConnection();
			st = ct.createStatement();
			String sql = "select borrowid,bookid,ISBN,bookname,borrowdate,shouldreturndate,returndate,hasreturn,fine "
					+ "from borrowinfo bwi natural join bookinlib bil natural join bookinfo bi "
					+ "where bil.ISBN=bi.ISBN and bwi.usrid='" + userid + "' and bwi.hasreturn='" + 0 + "'";
			rs = st.executeQuery(sql);
			
			while (rs.next()){
				BookReturn br = new BookReturn();
				br.setBorrowid(rs.getString(1));
				br.setBookid(rs.getString(2));
				br.setISBN(rs.getString(3));
				br.setBookname(rs.getString(4));
				br.setBorrowdate(rs.getString(5));
				br.setShouldreturn(rs.getString(6));
				br.setReturndate(rs.getString(7));
				br.setHasreturn(rs.getInt(8));
				br.setFine(rs.getInt(9));
				list.add(br);
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
