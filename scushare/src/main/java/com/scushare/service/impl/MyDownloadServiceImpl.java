package com.scushare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.service.MyDownloadService;
import com.scushare.mapper.MyDownloadMapper;
import scu.pojo.Download;
import scu.pojo.File;

import java.util.List;
/**
 * User��ҵ���ӿ�
 * @author Peng
 * @Date2018��8��18������9:54:40
 */
@Service
public class MyDownloadServiceImpl implements MyDownloadService {
	@Autowired
	private MyDownloadMapper myDownloadMapper;
	
	public List<Download> GetPage(int user_id, int startIndex, int limitCount) {

		return myDownloadMapper.getDownloadList(user_id,startIndex,limitCount);
		
	}

	public int pageCount(int user_id) {
		return myDownloadMapper.pageCount(user_id);
		
		
	}





    
}