package com.books.model;

import java.sql.*;

public class BooksBorrowHandle {

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = "";
	
	public boolean checkCredit(String s_usrid) {
		
		boolean checkResult = false;
		int credit = 0;
		int booksBorrowed = 0;
		sql = "SELECT usrcredit,hasborrow FROM userinfo WHERE usrid='" + s_usrid + "'";
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				credit = rs.getInt(1);
				booksBorrowed = rs.getInt(2);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(booksBorrowed < credit) {
			checkResult = true;
		}
		
		close_all();
		
		return checkResult;
	}
	
	public int checkBooksRemain(String isbn) {
		int checkResult = 0;
		sql = "SELECT avaliable FROM bookquantity WHERE ISBN='" + isbn + "'";
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				checkResult = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		close_all();
		
		return checkResult;
	}
	
	/**
	 * 此函数用于借阅书籍时修改数据库信息
	 * @param s_usrid String类型用户ID
	 * @param isbn 借阅书籍的ISBN号
	 * @return
	 */
	public String borrowBook(String s_usrid, String isbn) {
		String borrowResult = "";
		int credit = 0;	//用户信誉度，即可借书数量
		int booksBorrowed = 0;	//用户已借书数量
		int booksRemain = 0;	//用户欲借阅书籍剩余数量
		//查询用户的可借书数量和已借书数量
		sql = "SELECT usrcredit,hasborrow FROM userinfo WHERE usrid='" + s_usrid + "'";
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			//如果查询有结果进入比较可借书数量和已借书数量
			if(rs.next()){
				credit = rs.getInt(1);
				booksBorrowed = rs.getInt(2);
				if(booksBorrowed >= credit) {
					//如果可借书数量已满，返回"creditLimit"字符串
					borrowResult = "creditLimit";
				} else {
					//如果未满查询书籍是否有剩余
					sql = "SELECT avaliable FROM bookquantity WHERE ISBN='" + isbn + "'";
					close_rs();
					close_ps();
					ps = ct.prepareStatement(sql);
					rs = ps.executeQuery();
					if(rs.next()) {
						booksRemain = rs.getInt(1);
						if(booksRemain == 0) {
							//如果没有剩余，返回"noBooksRemain"字符串
							borrowResult = "noBooksRemain";
						} else {
							//如果有剩余，修改数据库信息
							close_rs();
							close_ps();
							//向borrowinlib表查询可借书籍
							sql = "SELECT bookid FROM bookinlib WHERE bookinlib='1' AND ISBN='" + isbn +"'";
							ps = ct.prepareStatement(sql);
							rs = ps.executeQuery();
							if(rs.next()) {
								borrowResult = rs.getString(1);
								close_rs();
								close_ps();
								//如果有，修改第一本可借书记录为已借出
								sql = "UPDATE bookinlib SET bookinlib='0' WHERE bookid='" + borrowResult + "'";
								ps = ct.prepareStatement(sql);
								int updateResult = ps.executeUpdate();
								if(updateResult == 0) {
									//修改失败返回"borrowFail"字符串
									borrowResult = "borrowFail";
								} else {
									//修改成功
									int borrowid = 0;
									close_rs();
									close_ps();
									//向borrowinfo表添加借阅记录
									sql = "select borrowid from borrowinfo order by borrowid desc limit 1";
									ps = ct.prepareStatement(sql);
									rs = ps.executeQuery();
									//生成borrowid
									if(rs.next()) {
										borrowid = Integer.parseInt(rs.getString(1)) + 1;
									} else {
										borrowid = 1;
									}
									close_rs();
									close_ps();
									//生成sql语句
									sql = "INSERT INTO borrowinfo VALUES ('" 
									+ borrowid + "', '" + s_usrid + "', '" 
									+ borrowResult + "', '" + Tools.getDateNow() + "', '" 
									+ Tools.getSpecifiedDayAfter(Tools.getDateNow(), 7) + "', '" 
									+ "0000-00-00" + "', '" 
									+ "0" + "', '" 
									+ "0" + "')";
									ps = ct.prepareStatement(sql);
									updateResult = ps.executeUpdate();
									
									close_rs();
									close_ps();
									
									//向bookQuantity修改书籍剩余量
									sql = "UPDATE bookquantity SET avaliable='" + (booksRemain-1) + "' WHERE ISBN='" + isbn + "'";
									ps = ct.prepareStatement(sql);
									updateResult = ps.executeUpdate();
									
									close_rs();
									close_ps();
									
									//向userinfo修改已借书数量
									sql = "UPDATE userinfo SET hasborrow='" + (booksBorrowed+1) + "' WHERE usrid='" + s_usrid + "'";
									ps = ct.prepareStatement(sql);
									updateResult = ps.executeUpdate();
								}
								
							} else {
								borrowResult = "noBooksRemain";
							} 
						}
					} else {
						borrowResult = "noBooksRemain";
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		close_all();
		
		return borrowResult;
	}
	
	
	
	private void close_ct(){
		try{
			if(ct != null)ct.close();ct = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close_ps(){
		try{
			if(ps != null)ps.close();ps = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close_rs(){
		try{
			if(rs != null)rs.close();rs = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close_all(){
		close_rs();
		close_ps();
		close_ct();
	}
}
