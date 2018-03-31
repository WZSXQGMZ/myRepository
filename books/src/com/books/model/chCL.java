package com.books.model;

import java.sql.*;

public class chCL{
	
	Connection ct = null;
	Statement st = null;
	int rs = 0;
	PreparedStatement ps = null;
	
	public boolean change(String ccISBN,String ccName,String ccAuthor,String ccPress,String ccShelt,String ccKind,String ccAc,String ccAv) {
		try {
			ct=Tools.getConnection();
			ps=ct.prepareStatement("update bookinfo set bookname='"+ccName+"',author='"+ccAuthor+"',press='"+ccPress+"',shelfid='"+ccShelt+"',booktype='"+ccKind+"' where ISBN='"+ccISBN+"'");
			rs=rs+ps.executeUpdate();
			ps=ct.prepareStatement("UPDATE bookquantity set avaliable='"+ccAv+"',total='"+ccAc+"' where ISBN='"+ccISBN+"'");
			rs=rs+ps.executeUpdate();
			if(rs!=0)
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}