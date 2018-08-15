package com.scushare.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.scushare.entity.FileInfo;
import com.scushare.entity.UserLogInfo;

import scu.pojo.User;

public interface UserServiceMapper {

	/**
	 * ͨ���û�����ȡ��½��Ϣ
	 * @param user_name
	 * @return
	 */
	@Select("select user_name,user_password,user_limit from user where user_name=#{user_name}")
	UserLogInfo getUserLogInfoByName(@Param("user_name") String user_name);
	/**
	 * �ж��û����Ƿ����
	 * @param user_name
	 * @return
	 */
	@Select("select user_name from user where user_name=#{user_name}")
	String findUserName(@Param("user_name") String user_name);
	/**
	 * �ж������Ƿ����
	 * @param user_mail
	 * @return
	 */
	@Select("select user_mail from user where user_mail=#{user_mail}")
	String findUserMail(@Param("user_mail") String user_mail);
	/**
	 * ͨ���û�����ȡ�û�ID
	 * @param user_name
	 * @return
	 */
	@Select("SELECT user_id FROM user WHERE user_name=#{user_name}")
	int getUserIdByName(@Param("user_name") String user_name);
	/**
	 * �����ݿ�����û�ע����Ϣ
	 * @param userInfo
	 * @param user_reg_date
	 * @return
	 */
	@Insert("insert into user(user_name, user_gender, user_college, user_major, user_mail, "
			+ "user_phone_num, user_reg_time, user_reg_ip, user_limit, user_grade, user_password)"
			+ "values(#{userInfo.userName},#{userInfo.userGender},#{userInfo.userCollege},#{userInfo.userMajor},#{userInfo.userMail},"
			+ "#{userInfo.userPhoneNum},#{user_reg_date},#{userInfo.userRegIp},'inactive',#{userInfo.userGrade},#{userInfo.userPassword})")
	//@Options(useGeneratedKeys = true, keyProperty = "user_id")
	int insertRegisterInfo(@Param("userInfo") User userInfo, @Param("user_reg_date") Timestamp user_reg_date);
	/**
	 * �����ݿ�����ļ��ϴ���Ϣ
	 * @param file_name
	 * @param file_size
	 * @param date
	 * @param user_id
	 * @param file_college
	 * @param file_major
	 * @param file_path
	 * @param file_md5
	 * @param file_abstract
	 * @return
	 */
	@Insert("INSERT INTO file(file_name,file_size,file_up_time,file_up_user_id,file_college,file_major,file_path,file_md5,file_abstract,file_pic_path,file_page_num,file_isverify,file_isdelete)"
			+ " VALUES(#{file_name},#{file_size},#{date},#{user_id},#{file_college},#{file_major},#{file_path},#{file_md5},#{file_abstract},#{file_pic_path},#{file_page_num},'�����','0')")
	int insertFileInfo(@Param("file_name") String file_name, @Param("file_size") Long file_size, @Param("date") Timestamp date
						, @Param("user_id") int user_id, @Param("file_college") String file_college, @Param("file_major") String file_major
						, @Param("file_path") String file_path, @Param("file_md5") String file_md5,@Param("file_abstract") String file_abstract
						, @Param("file_pic_path") String file_pic_path, @Param("file_page_num") int file_page_num );
	/**
	 * �����ݿ�����ļ���Ϣ����ȡ����id
	 * @param fileInfo
	 * @return
	 */
	@Insert("INSERT INTO file(file_name,file_size,file_up_time,file_up_user_id,file_college,file_major,file_path,file_md5,file_abstract,file_pic_path,file_page_num,file_isverify,file_isdelete)"
			+ " VALUES(#{fileInfo.file_name},#{fileInfo.file_size}"
			+ ",#{fileInfo.file_up_time},#{fileInfo.file_up_user_id}"
			+ ",#{fileInfo.file_college},#{fileInfo.file_major}"
			+ ",#{fileInfo.file_path},#{fileInfo.file_md5}"
			+ ",#{fileInfo.file_abstract},#{fileInfo.file_pic_path}"
			+ ",#{fileInfo.file_page_num},'�����','0')")
	@Options(useGeneratedKeys = true, keyProperty = "fileInfo.file_id")
	int insertFileInfoByFileClass(@Param("fileInfo") FileInfo fileInfo);
	/**
	 * �����ݿ����ؼ���
	 * @param file_id
	 * @param key_word
	 * @return
	 */
	@Insert("INSERT INTO keyword(file_id,key_word) VALUES(#{file_id},#{key_word})")
	int insertFileKeyWord(@Param("file_id") int file_id, @Param("key_word") String key_word);
	
	/**
	 * �������������ݿ����ͷ��·��
	 * @param user_id �û�id
	 * @param chathead_path ͷ��·��
	 * @return ���ظ��¼�¼����
	 */
	@Update("UPDATE user SET user_chathead=#{chathead_path} WHERE user_id=#{user_id}")
	int updateChatheadInfo(@Param("user_id") int user_id, @Param("chathead_path") String chathead_path);
	
	/**
	 * ͨ���ļ�MD5��ѧԺ��רҵ��ȡ�ļ�·��
	 * @param file_college
	 * @param file_major
	 * @param file_md5
	 * @return
	 */
	@Select("SELECT file_path FROM file WHERE file_college=#{file_college} AND file_major=#{file_major} AND file_md5=#{file_md5}")
	List<String> getFilePathByMd5AndMajor(@Param("file_college") String file_college, @Param("file_major") String file_major, @Param("file_md5")String file_md5);
	/**
	 * ͨ���ļ�MD5��ȡ�ļ�·��
	 * @param file_md5
	 * @return
	 */
	@Select("SELECT file_path FROM file WHERE file_md5=#{file_md5}")
	List<String> getFilePathByMd5(@Param("file_md5")String file_md5);
	/**
	 * ͨ���ļ�MD5��ȡ�ļ���Ϣ
	 * @param file_md5
	 * @return
	 */
	@Select("SELECT file_path,file_pic_path,file_page_num FROM file WHERE file_md5=#{file_md5}")
	List<FileInfo> getExistedMD5FileInfo(@Param("file_md5")String file_md5);
	
	//@Insert("INSERT INTO user(user_name, user_mail) values(#{user_name},#{user_mail}")
	//int insertUserMail(@Param("user_name") String user_name, @Param("user_mail") String user_mail);
	/**
	 * ͨ���û����������ж��û��Ƿ����
	 * @param user_name
	 * @param user_mail
	 * @return
	 */
	@Select("SELECT user_name FROM user WHERE user_name=#{user_name} AND user_mail=#{user_mail}")
	String getUserByNameAndMail(@Param("user_name") String user_name, @Param("user_mail") String user_mail);
	/**
	 * �����û�Ȩ��
	 * @param user_name
	 * @param user_limit
	 * @return
	 */
	@Update("UPDATE user SET user_limit=#{user_limit} WHERE user_name=#{user_name}")
	int updateUserLimitByName(@Param("user_name") String user_name, @Param("user_limit") String user_limit);
	@Select("SELECT user_chathead FROM user WHERE user_id=#{user_id}")
	String getUserChatheadById(@Param("user_id") int user_id);
	
}