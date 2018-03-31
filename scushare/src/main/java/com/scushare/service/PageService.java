package com.scushare.service;

import java.util.List;
import scu.pojo.User;

public interface PageService {
		//分页接口
		public List<User> GetPage(String userCollege,String userMajor,String userName,int currentPage,int pageSize);
		//页总数
		public int pageCount(String userCollege,String userMajor,String userName);

		//删除用户记录
		public boolean userdelet(String userId);
}
