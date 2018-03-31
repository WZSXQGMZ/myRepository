package com.scushare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.entity.UserInfo;
import com.scushare.entity.UserLogInfo;
import com.scushare.mapper.UserInfoMapper;
import com.scushare.service.UserInfoModifyResult;
import com.scushare.service.UserInfoService;
import com.scushare.utils.PwdUtil;

@Service
public class UserInfoServiceImpl implements UserInfoService{
	@Autowired
	UserInfoMapper userInfoMapper;
	
	public UserInfo getUserInfo(int user_id) {
		return userInfoMapper.getUserInfoById(user_id);
	}

	/**
	 * 函数用于修改用户基本信息
	 * @return 返回用户基本信息返回结果
	 */
	public UserInfoModifyResult modifyUserInfo(int user_id, String user_gender, String user_college, String user_major,
			String user_phone_num) {
		//处理性别(String->int)
		int gender = 1;
		if(user_gender.equals("男") == false) {
			gender = 0;
		}
		//判断手机号正确性
		if(user_phone_num.length() != 11) {
			return UserInfoModifyResult.MODIFY_FAILED;
		}
		if(userInfoMapper.updateUserModifiableInfo(gender, user_college, user_major, user_phone_num, user_id) == 1) {
			return UserInfoModifyResult.MODIFY_SUCCESS;
		}else {
			return UserInfoModifyResult.MODIFY_FAILED;
		}
	}

	/**
	 * 函数用于修改用户密码
	 * @param user_id 用户id
	 * @param new_user_password 用户新密码
	 * @param old_user_password 用户旧密码
	 * @return 成功返回ture，失败返回false
	 */
	public boolean modifyUserPassword(int user_id, String new_user_password, String old_user_password) {
		UserLogInfo userLogInfo = userInfoMapper.getUserLogInfoById(user_id);
		if(userLogInfo == null) {
			return false;
		}
		//旧密码明文与数据库中的密文比较
		if(PwdUtil.confirmPwd(old_user_password, userLogInfo.getUser_password()) == false) {
			return false;
		}
		//加密新密码
		String encryptedNewPass = PwdUtil.encryptPwd(new_user_password);
		//更新密码
		if(userInfoMapper.updateUserPassword(user_id, encryptedNewPass) != 1) {
			return false;
		}
		
		return true;
	}
}
