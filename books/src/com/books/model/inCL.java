package com.books.model;

import java.sql.*;

public class inCL {
	
	Connection ct = null;
	Statement st = null;
	int rs = 0;
	PreparedStatement ps = null;

	public boolean insert(String cISBN,String cName,String cAuthor,String cPress,String cShelt,String cKind,String cAc,String cAv){
		try {
			ct=Tools.getConnection();
			ps=ct.prepareStatement("insert bookinfo value('"+cISBN+"','"+cName+"','"+cAuthor+"','"+cPress+"','"+cShelt+"','"+cKind+"')");
			rs=rs+ps.executeUpdate();
			ps=ct.prepareStatement("insert bookquantity value('"+cISBN+"','"+cAc+"','"+cAv+"')");
			rs=rs+ps.executeUpdate();
			if(rs!=0)
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

	
	