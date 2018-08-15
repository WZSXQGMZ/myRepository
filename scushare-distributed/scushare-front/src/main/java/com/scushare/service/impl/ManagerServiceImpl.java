package com.scushare.service.impl;



import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.mapper.ManagerMapper;
import com.scushare.service.ManagerService;
import com.scushare.utils.Page;

import scu.pojo.DataFile;
import scu.pojo.Manager;
@Service
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private ManagerMapper managerMapper;
	
	public Manager login(String managerName, String managerPassword) {
		Manager manager = null;
		manager = managerMapper.Login(managerName);
		if(null != manager){
			if(!manager.getManagerPassword().equals(managerPassword)){
				manager = null;
			}
		}
		
		return manager;
	}

	public List<DataFile> GetPage(String fileCollege,String fileMajor,String fileName,int currentPage, int pageSize) {
		currentPage =(currentPage-1)*pageSize;
		List<DataFile> fileList = managerMapper.getFileList(fileCollege, fileMajor, fileName, currentPage, pageSize);
		return fileList;
	}

	public int pageCount(String fileCollege,String fileMajor,String fileName) {
		int pageTotalCount = managerMapper.pageCount(fileCollege,fileMajor,fileName);
		
		return pageTotalCount;
	}

	public boolean datadelet(String fileId) {
		boolean flag = true;
		
		if(managerMapper.dataDelet(fileId)<1){
			flag=false;
		}
		return flag;
		
	}

	public String dataDownload(String fileId) {
		String fileUrl = managerMapper.getFileUrlById(fileId);
		return fileUrl;
	}

	
	public int checkPageCount() {
		int pageCount = managerMapper.checkPageCount();
		return pageCount;
	}

	public List<DataFile> getFileCheck(int currentPage, int pageSize) {
		currentPage =(currentPage-1)*pageSize;
		List<DataFile> datafileList = managerMapper.getFileCheck(currentPage,pageSize);
		return datafileList;
	}

	public List<DataFile> getFileChecking(int currentPage, int pageSize) {
		currentPage =(currentPage-1)*pageSize;
		List<DataFile> datafileList = managerMapper.getFileChecking(currentPage,pageSize);
		return datafileList;
	}

	public int checkingPageCount() {
		int pageCount = managerMapper.checkingPageCount();
		return pageCount;
	}

	public DataFile getAccDataInfo(String fileId) {
		DataFile file = managerMapper.getAccuDataInfo(fileId);
		return file;
	}

	public int UpdataVerify(String fileId, String isverify) {
		int Update = managerMapper.UpdataVerify(fileId, isverify);
		if(Update<1){
			return 0;
		}else{
		return Update;
		}
	}

	public String getOldPWd(String managerName) {
		
		return managerMapper.getOldPWd(managerName);
	}

	public int UpdataPwd(String newPwd, String managerName) {
		
		return managerMapper.UpdataPwd(newPwd, managerName);
	}

	public DataFile getPreview(String fileId) {
		DataFile file = managerMapper.getPreview(fileId);
		return  file;
	}

	@Override
	public List<DataFile> getIndexFile(String fileCollege, String fileMajor, String fileName, int currentPage,
			int pageSize, String fileType) {
		List<DataFile> fileList = null;
		if(fileName == null){
			fileName ="";
		}
		if(fileCollege.equals("请选择学院")){
			fileCollege = null;
		}
		if(fileMajor.equals("请选择专业")){
			fileMajor = null;
		}
		 int currentPageNo = (currentPage-1)*pageSize;
				try{
					if(fileType == null){
						fileList = managerMapper.NgetIndexfileId(fileCollege, fileMajor, fileName, currentPageNo, pageSize);
					}else{
						fileList = managerMapper.IgetIndexfileId(fileCollege, fileMajor, fileName, currentPageNo, pageSize, fileType);
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("获取文件失败!");
				}
		return fileList;
	}

	@Override
	public Page getIndexPage(String fileCollege, String fileMajor, String fileName, int currentPage, int pageSize,
			String fileType) {
		Page page = new Page();
		if(fileName == null){
			fileName ="";
		}
		//
		if(fileCollege.equals("请选择学院")){
			fileCollege = null;
		}
		if(fileMajor.equals("请选择专业")){
			fileMajor = null;
		}
		//总数量表
				int totalCount = 0;
				try{
					if(fileType == null){
						totalCount = managerMapper.CountNIndexfileId(fileCollege, fileMajor, fileName, currentPage, pageSize);
					}else{
						totalCount = managerMapper.CountIndexfileId(fileCollege, fileMajor, fileName, currentPage, pageSize, fileType);
					}
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("获得总数量失败！");
				}
				page.setCurrentPageNo(currentPage);
				page.setPageSize(pageSize);
				page.setTotalPageCount(totalCount);
				page.setTotalCount(totalCount);
				page.setStartPage();
				page.setEndPage();
		return page;
	}
	
	
}
