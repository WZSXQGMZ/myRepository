package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.scushare.entity.FileInfo;

import scu.pojo.DataFile;

public interface UserSearchMapper {
	
	@Select("SELECT file.*, user.user_name as file_user_name FROM file INNER JOIN user ON file.file_up_user_id = user.user_id WHERE file_id=#{file_id}")
	FileInfo getFileInfoById(String file_id);
	
	@Select("SELECT * FROM file WHERE file_id=#{file_id}")
	DataFile getDataFileById(String file_id);
	
	@Select("SELECT file_id FROM file WHERE file_id!=#{file_id} AND file_college=#{file_college} AND file_isverify='ÊÇ' AND file_isdelete='0' limit 0,5 ")
	List<String> getFileInfoByCollege(@Param("file_id") String file_id, @Param("file_college") String file_college);

	@Select("SELECT file_id FROM file WHERE file_id!=#{file_id} AND file_major=#{file_major} AND file_isverify='ÊÇ' AND file_isdelete='0' limit #{randomNum},5 ")
	List<String> getFileInfoByMajor(@Param("file_id") String file_id, @Param("file_major") String file_major, @Param("randomNum") int randomNum);
	
	@Select("SELECT file_id FROM file WHERE file_isverify='ÊÇ' AND file_isdelete='0' limit #{randomNum},5")
	List<String> getRandomFile(@Param("randomNum") int randomNum);

}
