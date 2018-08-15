package com.scushare.service;

import java.util.List;

import com.scushare.entity.UserUploadRec;

public interface UserRecService {
	List<UserUploadRec> getUploadPaging(int user_id, int startIndex, int limitCount);
	int getUploadRecCount(int user_id);
	int setFileIsDelete(String file_id, String user_id);
}
