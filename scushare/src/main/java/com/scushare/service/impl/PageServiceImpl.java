package com.scushare.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.mapper.PageMapper;
import com.scushare.mapper.UserInfoMapper;
import com.scushare.service.PageService;
import com.scushare.utils.Page;

import scu.pojo.User;

@Service
public class PageServiceImpl implements PageService {
	@Autowired
	private PageMapper pageMapper;

	public List<User> GetPage(String userCollege, String userMajor, String userName, int currentPage, int pageSize) {
		currentPage =(currentPage-1)*pageSize;
		List<User> userList = pageMapper.getUserList(userCollege, userMajor, userName, currentPage, pageSize);
		return userList;
	}

	public int pageCount(String userCollege, String userMajor, String userName) {
		int pageTotalCount = pageMapper.pageCount(userCollege,userMajor,userName);
		
		return pageTotalCount;
	}

	public boolean userdelet(String userId) {
		boolean flag = true;
		
		if(pageMapper.userDelet(userId)<1){
			flag=false;
		}
		return flag;
	}

}
