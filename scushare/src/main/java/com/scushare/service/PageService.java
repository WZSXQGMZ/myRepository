package com.scushare.service;

import java.util.List;
import scu.pojo.User;

public interface PageService {
		//��ҳ�ӿ�
		public List<User> GetPage(String userCollege,String userMajor,String userName,int currentPage,int pageSize);
		//ҳ����
		public int pageCount(String userCollege,String userMajor,String userName);

		//ɾ���û���¼
		public boolean userdelet(String userId);
}
