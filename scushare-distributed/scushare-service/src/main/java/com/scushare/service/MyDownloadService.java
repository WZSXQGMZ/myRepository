package com.scushare.service;

import scu.pojo.Download;
import java.util.List;

/**
 * User��ҵ���ӿ�
 * @author Peng
 * @Date2018��3��14��
 */
public interface MyDownloadService {
	//��ҳ�ӿ�
	public List<Download> GetPage(int user_id,int startIndex, int limitCount);
	//Ҳ����
	public int pageCount(int user_id);
    
}