package com.books.model;

import java.sql.*;

public class AdminInfoHandle {
	enum ModifyExceptionType {NONE_EXCEPTON, USERBEAN_NULL, CONNECTION_FAIL, MODIFY_FAIL, TEL_EXIST};
	enum ModifyPasswordExceptionType {NONE_EXCEPTION, PASSWORD_INCORRECT, MODIFY_FAIL};
	
	
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	String sql = null;
	
	/**
	 * 此函数用于通过管理员id获取管理员信息
	 * @param s_adminid String类型的管理员id
	 * @return 返回AdminBean类型的管理员信息
	 */
	public AdminBean getAdminBean(String s_adminid) {
		AdminBean ab = null;
		sql = "SELECT * FROM managerinfo WHERE managerid='" + s_adminid + "'";
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				ab = new AdminBean();
				ab.setAdminId(rs.getString(1));
				ab.setAdminName(rs.getString(2));
				ab.setAdminTel(rs.getString(3));
				ab.setAdminEmail(rs.getString(4));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		close_all();
		return ab;
	}
	
	
	/**
	 * 此函数用于修改管理员信息
	 * @param adminBean AdminBean类型存放用户信息
	 * @return 返回修改结果：0: 无异常，1: 参数adminBean为空，2: 连接数据库失败，3: 修改失败，4: 手机号已存在
	 */
	public int setAdminBean(AdminBean adminBean) {
		int modifyResult = ModifyExceptionType.MODIFY_FAIL.ordinal();
		int updateResult = 0;
		if(adminBean != null) {
			//生成查询相同手机号语句
			String sql_tel_query = "SELECT managertel FROM managerinfo WHERE managerid!='" + adminBean.getAdminId() + "' AND managertel='" + adminBean.getAdminTel() + "'";
			//生成更新数据库语句
			sql = "UPDATE managerinfo SET" 
					+ " managername='" + adminBean.getAdminName() + "'," 
					+ " managertel='" + adminBean.getAdminTel() + "'," 
					+ " email='"	+ adminBean.getAdminEmail() + "'" 
					+ " WHERE managerid='" + adminBean.getAdminId() + "'";
			try {
				ct = Tools.getConnection();
				//检查相同手机号
				ps = ct.prepareStatement(sql_tel_query);
				rs = ps.executeQuery();
				if(rs.next()) {
					//如果存在重复手机号则返回4
					modifyResult = ModifyExceptionType.TEL_EXIST.ordinal();
					close_all();
					return modifyResult;
				} else {
					close_rs();
					close_ps();
				}
				ps = ct.prepareStatement(sql);
				updateResult = ps.executeUpdate();
				if(updateResult == 0) {
					modifyResult = ModifyExceptionType.MODIFY_FAIL.ordinal();
					close_all();
					return modifyResult;
				} else {
					modifyResult = ModifyExceptionType.NONE_EXCEPTON.ordinal();
					close_all();
					return modifyResult;
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		close_all();
		return modifyResult;
	}
	
	
	/**
	 * 此函数用于修改管理员账户密码
	 * @param s_adminid String类型管理员id
	 * @param oldPass 旧账户密码
	 * @param newPass 新账户密码
	 * @return 返回修改结果：0: 无异常，1: 密码不正确，2: 修改失败
	 */
	public int setAdminPassword(String s_adminid, String oldPass, String newPass) {
		int setResult = ModifyPasswordExceptionType.MODIFY_FAIL.ordinal();
		//生成密码匹配语句
		String sql_match_old_pass = "SELECT managerpassword FROM manageraccount WHERE managerid='" + s_adminid 
				+ "' AND managerpassword='" + oldPass + "'";
		try {
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql_match_old_pass);
			rs = ps.executeQuery();
			if(rs.next()) {
				//如过id和密码匹配
				close_rs();
				close_ps();
				sql = "UPDATE manageraccount SET managerpassword='" + newPass + "' WHERE managerid='" + s_adminid + "'";
				ps = ct.prepareStatement(sql);
				int updateResult = ps.executeUpdate();
				if(updateResult == 0) {
					setResult = ModifyPasswordExceptionType.MODIFY_FAIL.ordinal();
					close_all();
					return setResult;
				} else {
					setResult = ModifyPasswordExceptionType.NONE_EXCEPTION.ordinal();
					close_all();
					return setResult;
				}
			} else {
				setResult = ModifyPasswordExceptionType.PASSWORD_INCORRECT.ordinal();
				close_all();
				return setResult;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		close_all();
		return setResult;
	}
	
	
	/**
	 * 此函数用于通过管理员账号获取管理员id
	 * @param adminAccount String类型管理员账号
	 * @return 返回Int类型管理员id
	 */
	public String getAdminID(String adminAccount) {
		String adminid = "";
		
		try {
			ct = Tools.getConnection();
			String sql_get_adminid = "SELECT managerid FROM manageraccount WHERE manageraccount='" + adminAccount + "'";
			ps = ct.prepareStatement(sql_get_adminid);
			rs = ps.executeQuery();
			if(rs.next()) {
				adminid = rs.getString(1);
			} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return adminid;
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
