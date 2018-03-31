package com.scushare.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface FileRecMapper {
	@Update("UPDATE file SET file_page_num=#{file_page_num} WHERE file_id=#{file_id}")
	int updateFilePageNum(@Param("file_page_num") int file_page_num, @Param("file_id") int file_id);
	
}
