package com.books.model;

import java.sql.*;
import java.util.ArrayList;

import com.books.model.managerbean;;

public class miCL {
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
			String sql="select count(*) from managerinfo";
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
	
		public ArrayList<managerbean> getDpage(int pageSize,int pageNow) {
			ArrayList<managerbean> list=new ArrayList<managerbean>();
				//������ݿ�
			try {
				ct=Tools.getConnection();
				st=ct.createStatement();
				String sql="select * from managerinfo limit "+pageSize*(pageNow-1)+","+pageSize;
				rs=st.executeQuery(sql);
				while (rs.next()) {
					managerbean ub=new managerbean();

					ub.setManagerid(rs.getString(1));
					ub.setManagername(rs.getString(2));
					ub.setManagertel(rs.getString(3));
					ub.setEmail(rs.getString(4));
					
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
