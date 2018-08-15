package com.scushare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.scushare.entity.UserUploadRec;
import com.scushare.mapper.UserRecMapper;
import com.scushare.service.UserRecService;

@Service("UserRecService")
public class UserRecServiceImpl implements UserRecService{

	@Autowired
	UserRecMapper userRecMapper;
	
	public List<UserUploadRec> getUploadPaging(int user_id, int startIndex, int limitCount){
		if(startIndex < 0) {
			limitCount += startIndex;
			startIndex = 0;
		}
		if(limitCount < 1) {
			limitCount = 1;
		}
		return userRecMapper.getUploadRecByUid(user_id, startIndex, limitCount);
	}

	public int getUploadRecCount(int user_id) {
		return userRecMapper.getUploadRecCountByUid(user_id);
	}

	@Override
	@CacheEvict(value = "FILE_ID_CACHE", key = "#file_id + '-fid'")
	public int setFileIsDelete(String file_id, String user_id) {
		return userRecMapper.setFileIsDeleteById(file_id, user_id);
	}
}
