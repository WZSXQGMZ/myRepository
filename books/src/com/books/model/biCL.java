package com.books.model;

import java.sql.*;
import java.util.ArrayList;

import com.books.model.bookbean;

public class biCL {
	Connection ct=null;
	Statement st=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	int pageSize=10;
	int pageCount=0;

	public int getPageCount() {
		try {
			ct=Tools.getConnection();
			st=ct.createStatement();
			String sql="select count(*) from bookinfo";
			rs=st.executeQuery(sql);
			if(rs.next()){
				int rowCount=rs.getInt(1);
				if(rowCount%pageSize==0){
					pageCount=rowCount/pageSize;
				}else {
					pageCount=rowCount/pageSize+1;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				Tools.CloseDB(ct, st, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pageCount;
	}
	
		public ArrayList<bookbean> getDpage(int pageSize,int pageNow) {
			ArrayList<bookbean> list=new ArrayList<bookbean>();

			try {
				ct=Tools.getConnection();
				st=ct.createStatement();
				String sql="select * from bookinfo,bookquantity where bookinfo.ISBN=bookquantity.ISBN limit "+pageSize*(pageNow-1)+","+pageSize;
				rs=st.executeQuery(sql);
				while (rs.next()) {
					bookbean ub=new bookbean();

					ub.setISBN(rs.getString(1));
					ub.setBookname(rs.getString(2));
					ub.setAuthor(rs.getString(3));
					ub.setPress(rs.getString(4));
					ub.setShelfid(rs.getString(5));
					ub.setBooktype(rs.getString(6));
					ub.setTotal(rs.getInt(8));
					ub.setAvaliable(rs.getInt(9));
					list.add(ub);					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}finally{
				try {
					Tools.CloseDB(ct, st, rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return list;
		}
}
