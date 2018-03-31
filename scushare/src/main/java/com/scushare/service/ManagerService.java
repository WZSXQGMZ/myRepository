package com.scushare.service;

import java.util.List;

import com.scushare.utils.Page;

import scu.pojo.DataFile;
import scu.pojo.Manager;

public interface ManagerService {
	//定义登录方法接口
	public Manager login(String managerName,String managerPassword);
	
	//分页接口
	public List<DataFile> GetPage(String fileCollege,String fileMajor,String fileName,int currentPage,int pageSize);
	//页总数
	public int pageCount(String fileCollege,String fileMajor,String fileName);
	//删除文件记录
	public boolean datadelet(String fileId);
	//下载文件
	public String dataDownload(String fileId);
	//查询审核历史
	public List<DataFile> getFileCheck(int currentPage,int pageSize); 
	//查询审核历史总数
	public int checkPageCount();
	//查询正在审核的资料
	public List<DataFile> getFileChecking(int currentPage,int pageSize); 
	//查询正在审核资料的总数
	public int checkingPageCount();
	//查询准确的资料
	public DataFile getAccDataInfo(String fileId);
	//审核资料，更新状态
	public int UpdataVerify(String fileId,String isverify);
	//查询原密码
	public String getOldPWd(String  managerName);
	//更新密码
	public int UpdataPwd(String newPwd,String managerName );
	////预览界面，查询文件的路径，学院，页数
	public DataFile getPreview(String fileId);
	//首页查询
	public List<DataFile> getIndexFile(String fileCollege,String fileMajor,String fileName,int currentPage,int pageSize ,String fileType);
	//首页查询分页
	public Page getIndexPage(String fileCollege,String fileMajor,String fileName,int currentPage,int pageSize ,String fileType);
}
