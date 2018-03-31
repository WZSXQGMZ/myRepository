package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import scu.pojo.Download;

public interface MyDownloadMapper {
	
	

	@Select("select count(*) from download where user_id=#{user_id}")
	int pageCount(@Param("user_id") int user_id);
	
	@Select ("select file.file_name as fileName,file.file_size as fileSize,download.download_time as downloadTime FROM file,download WHERE file.file_id=download.file_id and user_id=#{user_id} limit #{startIndex},#{limitCount}")
	List<Download> getDownloadList(
			@Param("user_id") int user_id,
			 @Param("startIndex") int startIndex
				, @Param("limitCount") int limitCount);

	
}