package com.scushare.service;

import scu.pojo.Download;
import java.util.List;

/**
 * User类业务层接口
 * @author Peng
 * @Date2018年3月14日
 */
public interface MyDownloadService {
	//分页接口
	public List<Download> GetPage(int user_id,int startIndex, int limitCount);
	//也总数
	public int pageCount(int user_id);
    
}