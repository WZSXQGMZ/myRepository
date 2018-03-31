package com.scushare.service;

import java.util.List;

import com.scushare.entity.UserUploadRec;

public interface UserRecService {
	List<UserUploadRec> getUploadPaging(int user_id, int startIndex, int limitCount);
	int getUploadRecCount(int user_id);
	int setFileIsDelete(int file_id, int user_id);
}
