package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import scu.pojo.User;


public interface PageMapper {

	@Select("<script>"
			 +"select user_id as userId, user_name as userName, user_gender as userGender, user_college as userCollege, user_major as userMajor,"
			 +" user_mail as userMail, user_phone_num as userPhoneNum, user_reg_time as userRegTime, user_reg_ip as userRegIp,"
			 +" user_password as userPassword, user_grade as userGrade from user"
		        +"<where>"
		            +"<if test=\"user_college == null and user_major == null and user_name ==''\">"
		               + "and 1=1 order by user_id DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"user_college != null and user_major != null and user_name == ''\">"
		             +  "and user_college =#{user_college} and user_major =#{user_major} order by user_id DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"user_college == null and user_major == null and user_name != ''\">"
		              +"and user_name  like CONCAT('%',#{user_name},'%') order by user_id DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"user_college != null and user_major != null and user_name != ''\">"
		                +"and user_college =#{user_college} and user_major =#{user_major} and user_name  like CONCAT('%',#{user_name},'%') order by user_id DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		        +"</where>"
			+ "</script>")
	List<User> getUserList(@Param("user_college") String user_college,
           @Param("user_major") String user_major,
           @Param("user_name") String user_name,
           @Param("currentPage") int currentPage,
           @Param("pageSize") int pageSize);

	@Select("<script>"
			+"select COUNT(*) from user"
	        +"<where>"
	            +"<if test=\"user_college == null and user_major == null and user_name ==''\">"
	               + "and 1=1  "
	            +"</if>"
	            +"<if test=\"user_college != null and user_major != null and user_name == ''\">"
	             +  "and  user_college =#{user_college} and user_major =#{user_major} "
	            +"</if>"
	            +"<if test=\"user_college == null and user_major == null and user_name != ''\">"
	              +"and user_name like CONCAT('%',#{user_name},'%') "
	            +"</if>"
	            +"<if test=\"user_college != null and user_major != null and user_name != ''\">"
	                +"and user_college =#{user_college} and user_major =#{user_major} and user_name like CONCAT('%',#{user_name},'%') "
	            +"</if>"

	        +"</where>"
			+ "</script>")
	int pageCount(@Param("user_college") String user_college,
           @Param("user_major") String user_major,
           @Param("user_name") String user_name
           );
	
	@Delete("Delete from user where user_id = #{userId}")
	int userDelet(@Param("userId") String userId);
}
