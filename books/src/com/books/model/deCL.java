package com.books.model;

import java.sql.*;

public class deCL{
	
	Connection ct = null;
	Statement st = null;
	int rs = 0;
	PreparedStatement ps = null;
	
	public boolean delete(String ISBN) {
		try {
			ct=Tools.getConnection();
			ps=ct.prepareStatement("delete from bookinfo where ISBN='"+ISBN+"'");
			rs=rs+ps.executeUpdate();
			ps=ct.prepareStatement("delete from bookinlib where ISBN='"+ISBN+"'");
			rs=rs+ps.executeUpdate();
			ps=ct.prepareStatement("delete from bookquantity where ISBN='"+ISBN+"'");
			rs=rs+ps.executeUpdate();
			if(rs!=0)
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}