package com.scushare.service;

import java.util.List;
import java.util.Set;

import com.scushare.entity.FileInfo;
import com.scushare.utils.Page;

import scu.pojo.DataFile;

public interface UserSearchService {
	FileInfo getFileInfoById(String file_id);
	List<String> getFileInfoByCollege(String file_id, String file_college);
	List<String> getFileInfoByMajor(String file_id, String file_major, int randomNum);
	List<String> getRandomFile(int randomNum);
	List<DataFile> getIndexFile(String keyword, int currPage, int pageSize, Page page);
	Set<String> getHotestKeywords();
}
