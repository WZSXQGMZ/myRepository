package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.scushare.entity.UserInfo;
import com.scushare.entity.UserUploadRec;

public interface UserRecMapper {
	@Select("SELECT file_id,file_name,file_path,file_size,file_up_time,file_isverify FROM file WHERE file_up_user_id=#{user_id} AND file_isdelete='0' limit #{startIndex},#{limitCount}")
	List<UserUploadRec> getUploadRecByUid(@Param("user_id") int user_id
										, @Param("startIndex") int startIndex
										, @Param("limitCount") int limitCount);
	@Select("SELECT COUNT(*) FROM file WHERE file_up_user_id=#{user_id} AND file_isdelete='0'")
	int getUploadRecCountByUid(@Param("user_id") int user_id);
	
	@Select("SELECT a.user_name,a.user_chathead FROM user AS a,file AS b WHERE b.file_id=#{file_id} AND b.file_up_user_id=a.user_id")
	UserInfo getUserInfoByFileId(@Param("file_id") String file_id);
	
	@Update("UPDATE file SET file_isdelete='1' WHERE file_id=#{file_id} AND file_up_user_id=#{user_id}")
	int setFileIsDeleteById(@Param("file_id") String file_id,@Param("user_id") String user_id);
}