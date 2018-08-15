package com.scushare.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.scushare.entity.UserInfo;
import com.scushare.entity.UserLogInfo;

public interface UserInfoMapper {

	@Select("SELECT user_gender,user_college,user_major,user_mail,user_phone_num,user_reg_time FROM user WHERE user_id=#{user_id}")
	UserInfo getUserInfoById(@Param("user_id") int user_id);
	@Update("UPDATE user SET user_gender=#{user_gender},user_college=#{user_college},user_major=#{user_major},user_phone_num=#{user_phone_num} "
			+ "WHERE user_id=#{user_id}")
	int updateUserModifiableInfo(@Param("user_gender") int user_gender,@Param("user_college") String user_college,@Param("user_major") String user_major,@Param("user_phone_num") String user_phone_num,@Param("user_id") int user_id);
	
	@Select("SELECT user_name,user_password FROM user WHERE user_id=#{user_id}")
	UserLogInfo getUserLogInfoById(@Param("user_id") int user_id);
	@Update("UPDATE user SET user_password=#{new_user_password} WHERE user_id=#{user_id}")
	int updateUserPassword(@Param("user_id") int user_id, @Param("new_user_password") String new_user_password);
	
	@Select("SELECT user_name FROM user WHERE user_id = #{user_id}")
	String selectUnameByUid(@Param("user_id") Integer user_id);
}
