package com.books.model;
import java.sql.*;
import com.books.model.UserBean;

public class PersonalUserInfoHandle {
	enum ModifyExceptionType {NONE_EXCEPTON, USERBEAN_NULL, CONNECTION_FAIL, MODIFY_FAIL, TEL_EXIST};
	enum ModifyPasswordExceptionType {NONE_EXCEPTION, PASSWORD_INCORRECT, MODIFY_FAIL};
	
	int modifyResult = 0;
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int updateReturnResult = 0;
	String sql = null;
	
	/**
	 * 此函数用于获取用户信息，传递通过用户ID参数，获取数据库中对应的用户记录
	 * @param usrid Int型，用户ID
	 * @return UserBean类型，存放用户信息
	 */
	public UserBean getUserBean(String usrid){
		UserBean ub = new UserBean();
		sql = "SELECT * FROM userinfo WHERE usrid='" + usrid + "'";
		
		try{
			ct = Tools.getConnection();
			if(ct == null) {
				close_DB();
				return null;
			}
			
			ps = ct.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				ub.setUsrid(rs.getString(1));
				ub.setUsrname(rs.getString(2));
				ub.setUsrsex(rs.getString(3));
				ub.setUsrage(rs.getInt(4));
				ub.setVocation(rs.getString(5));
				ub.setUsrtel(rs.getString(6));
				ub.setEmail(rs.getString(7));
				ub.setAddress(rs.getString(8));
				ub.setUsrcredit(rs.getInt(9));
			} else {
				close_DB();
				return null;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return ub;
	}
	
	/**
	 * 此函数用于修改用户信息，传递UserBean类型参数，修改数据库中的对应记录
	 * @param userBean UserBean类型，存放用户信息
	 * @return Int类型：0 无异常, 1 UserBean参数为空, 2 数据库连接为空, 3 修改记录失败, 4手机号已存在
	 */
	public int setUserBean(UserBean userBean){
	
		if(userBean != null){
			//生成查询相同手机号语句
			String sql_tel_query = "SELECT usrtel FROM userinfo WHERE usrid!='" + userBean.getUsrid() + "' AND usrtel='" + userBean.getUsrtel() + "'";
			//生成更新数据库语句
			sql = "UPDATE userinfo SET" 
					+ " usrname='" + userBean.getUsrname() + "'," 
					+ " usrsex='" + userBean.getUsrsex() + "'," 
					+ " usrage='" + userBean.getUsrage() + "'," 
					+ " vocation='" + userBean.getVocation() + "'," 
					+ " usrtel='" + userBean.getUsrtel() + "'," 
					+ " email='" + userBean.getEmail() + "'," 
					+ " address='" + userBean.getAddress() + "'" 
					+ " WHERE usrid='" + userBean.getUsrid() + "'";
			try{
				ct = Tools.getConnection();
				
				//如果ct为空则返回2
				if(ct == null) {
					modifyResult = ModifyExceptionType.CONNECTION_FAIL.ordinal();
					close_DB();
					return modifyResult;
				}
				ps = ct.prepareStatement(sql_tel_query);
				rs = ps.executeQuery();
				//如果有相同手机号则返回4
				if(rs.next()){
					modifyResult = ModifyExceptionType.TEL_EXIST.ordinal();
					close_DB();
					return modifyResult;
				} else {
					ps.close();
					ps = null;
				}
				
				ps = ct.prepareStatement(sql);
				updateReturnResult = ps.executeUpdate();
				//如果更新失败则返回3，正常则返回0
				if(updateReturnResult == 0) {
					modifyResult = ModifyExceptionType.MODIFY_FAIL.ordinal();
					close_DB();
					return modifyResult;
				} else {
					modifyResult = ModifyExceptionType.NONE_EXCEPTON.ordinal();
					close_DB();
					return modifyResult;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			modifyResult = ModifyExceptionType.USERBEAN_NULL.ordinal();
			close_DB();
			return modifyResult;
		}
		
		close_DB();
		return modifyResult;
	}

	/**
	 * 此函数用于修改用户密码
	 * @param usrid 用户ID
	 * @param oldPass 用户旧密码
	 * @param newPass 用户新密码
	 * @return 返回Int型，0: 修改正常，1: 旧密码不正确，2: 修改出错
	 */
	public int setUserPassword(String usrid, String oldPass, String newPass){
		int setResult = 0;
		
		String sql_match_old_pass = "SELECT usrpassword FROM useraccount WHERE usrid='" + usrid + "' AND usrpassword='" + oldPass + "'";
		try{
			ct = Tools.getConnection();
			ps = ct.prepareStatement(sql_match_old_pass);
			rs = ps.executeQuery();
			if(rs.next()){
				rs.close();rs = null;
				ps.close();ps = null;
				sql = "UPDATE useraccount SET usrpassword='" + newPass + "' WHERE usrid='" + usrid + "'";
				ps = ct.prepareStatement(sql);
				int updateResult = ps.executeUpdate();
				if(updateResult == 0) {
					setResult = ModifyPasswordExceptionType.MODIFY_FAIL.ordinal();
				}
				rs.close();rs = null;
				ps.close();ps = null;
			} else {
				setResult = ModifyPasswordExceptionType.PASSWORD_INCORRECT.ordinal();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return setResult;
	}
	
	/**
	 * 此函数用于通过用户账号获取用户ID
	 * @param usraccount String类型用户账号
	 * @return 返回Int型用户ID
	 */
	public int getUserID(String usraccount){
		int usrid = -1;
		
		try{
			ct = Tools.getConnection();
			String sql_get_userid = "SELECT usrid FROM useraccount WHERE usraccount='" + usraccount + "'";
			ps = ct.prepareStatement(sql_get_userid);
			rs = ps.executeQuery();
			if(rs.next()){
				usrid = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return usrid;
	}
	
	//关闭数据库连接函数
	private void close_DB(){
		if(rs != null){
			try{
				rs.close();
				rs = null;
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		if(ps != null){
			try{
				ps.close();
				ps = null;
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		if(ct != null){
			try{
				ct.close();
				ct = null;
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
