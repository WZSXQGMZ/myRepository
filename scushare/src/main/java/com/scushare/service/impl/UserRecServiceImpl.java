package com.scushare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.entity.UserUploadRec;
import com.scushare.mapper.UserRecMapper;
import com.scushare.service.UserRecService;

@Service
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
	public int setFileIsDelete(int file_id, int user_id) {
		return userRecMapper.setFileIsDeleteById(file_id, user_id);
	}
}
