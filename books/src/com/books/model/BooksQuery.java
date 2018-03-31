package com.books.model;

import java.sql.*;
import java.util.ArrayList;

import com.books.model.bookbean;

public class BooksQuery {

	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int booksCount;
	
	/**
	 * 该函数用于获取查询对应页的书籍
	 * @param booksQuery String类型，查询关键字
	 * @param queryRange String类型，查询范围类型
	 * @param pageNow 当前页数
	 * @param pageSize 每页记录数量
	 * @return
	 */
	public ArrayList<bookbean> booksQuery(String booksQuery, String queryRange, int pageNow, int pageSize) {
		ArrayList<bookbean> bookList = new ArrayList<bookbean>();
		
		if (pageNow<1) {
			pageNow = 1;
		}
		if(queryRange.equals("isbn")){
			queryRange = "ISBN";
		} else if(queryRange.equals("publisher")) {
			queryRange = "press";
		}
		
		String sql = "SELECT * FROM bookinfo WHERE " + queryRange + " LIKE '%" + booksQuery + "%' " + "limit " + pageSize*(pageNow-1) + "," + pageSize;
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				bookbean bookBean = new bookbean();
				bookBean.setISBN(rs.getString(1));
				bookBean.setBookname(rs.getString(2));
				bookBean.setAuthor(rs.getString(3));
				bookBean.setPress(rs.getString(4));
				bookBean.setShelfid(rs.getString(5));
				bookBean.setBooktype(rs.getString(6));
				bookList.add(bookBean);
			}
			close_all();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}
	
	/**
	 * 该函数用于获取需要查询的书籍总数
	 * @param booksQuery String类型，查询关键字
	 * @param queryRange String类型，查询范围类型
	 * @return
	 */
	public int getBooksCount(String booksQuery, String queryRange){
		
		String sql_count_books = "SELECT COUNT(*) FROM bookinfo WHERE " + queryRange + " LIKE'%" + booksQuery + "%'";
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql_count_books);
			rs = ps.executeQuery();
			if(rs.next()) {
				booksCount = rs.getInt(1);
			}
			close_all();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return booksCount;
	}
	
	private void close_ct(){
		try{
			ct.close();ct = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close_ps(){
		try{
			ps.close();ps = null;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close_rs(){
		try{
			rs.close();rs = null;
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
