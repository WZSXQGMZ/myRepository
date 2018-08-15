package com.scushare.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.backgroundService.SearchService;
import com.scushare.entity.FileInfo;
import com.scushare.mapper.SearchKeyWordsMapper;
import com.scushare.mapper.UserSearchMapper;
import com.scushare.service.KeyWordsService;
import com.scushare.service.UserSearchService;
import com.scushare.utils.Page;

import scu.pojo.DataFile;

public class UserSearchServiceImpl implements UserSearchService{
	@Autowired
	UserSearchMapper userSearchMapper;

	@Autowired
	SearchKeyWordsMapper searchKeyWordsMapper;
	
	@Autowired
	KeyWordsService keyWordsService;
	
	@Autowired
	SearchService searchService;
	
	@Override
	public FileInfo getFileInfoById(String file_id) {
		return searchService.getFileInfoById(file_id);
	}

	@Override
	public List<String> getFileInfoByCollege(String file_id, String file_college){
		return userSearchMapper.getFileInfoByCollege(file_id, file_college);
	}
	
	@Override
	public List<String> getFileInfoByMajor(String file_id, String file_major, int randomNum) {
		return userSearchMapper.getFileInfoByMajor(file_id, file_major, randomNum);
	}
	
	@Override
	public List<String> getRandomFile(int randomNum) {
		return userSearchMapper.getRandomFile(randomNum);
	}

	@Override
	public List<DataFile> getIndexFile(String keyword, int currPage, int pageSize, Page page) {
		int currIndex = (currPage - 1) * pageSize;
		int endIndex = currIndex + pageSize - 1;
		String[] keywords = keyword.split(" ");
		
		keyWordsService.handleKeyWord(keywords);
		
		List<String> idList = getSearchFileId(keywords);
		int length = idList.size();
		if(length < pageSize) {
			currIndex = 0;
			endIndex = currIndex + length - 1;
		}
		if(endIndex >= length) {
			endIndex = length;
		}
		if(currIndex >= length) {
			currIndex = length;
		}
		List<DataFile> fileList = new ArrayList<DataFile>();
		for(int i = currIndex; i <= endIndex; i++) {
			DataFile dataFile = new DataFile();
			FileInfo fileInfo = getFileInfoById(idList.get(i));
			copyFileType(dataFile, fileInfo);
			fileList.add(dataFile);
		}
		
		page.setCurrentPageNo(currPage);
		page.setPageSize(pageSize);
		page.setTotalPageCount((length + pageSize - 1) / pageSize);
		page.setTotalCount(length);
		page.setStartPage();
		page.setEndPage();
		
		return fileList;
	}
	
	private List<String> getSearchFileId(String[] keywords){
		return searchKeyWordsMapper.searchFileIdByKeywords(keywords);
	}

	private void copyFileType(DataFile dataFile, FileInfo fileInfo) {
		dataFile.setFileCollege(fileInfo.getFile_college());
		dataFile.setFileId(fileInfo.getFile_id());
		dataFile.setFileIntroduction(fileInfo.getFile_abstract());
		dataFile.setFileIsVerify(fileInfo.getFile_isverify());
		dataFile.setFileMajor(fileInfo.getFile_major());
		dataFile.setFileMd5(fileInfo.getFile_md5());
		dataFile.setFileName(fileInfo.getFile_name());
		dataFile.setFileSize((int)fileInfo.getFile_size());
		dataFile.setFilePath(fileInfo.getFile_path());
		dataFile.setFileUpTime(new Date(fileInfo.getFile_up_time().getTime()));
		dataFile.setFileUsername(fileInfo.getFile_user_name());
	}

	@Override
	public Set<String> getHotestKeywords() {
		return searchService.getHotKeywordsSet();
	}
}
