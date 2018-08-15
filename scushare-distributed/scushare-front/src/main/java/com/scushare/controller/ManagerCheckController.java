package com.scushare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scushare.service.ManagerService;
import com.scushare.utils.Page;

import scu.pojo.DataFile;


@Controller
public class ManagerCheckController {
	@Autowired
	private ManagerService managerService;
	//审核历史
	@RequestMapping("/MCheckHistory")
	public String CheckHistory(Model model,
							   @RequestParam(value="pageIndex",required=false) String pageIndex){
		List<DataFile> fileList = null;
		//设置页码容量
		int pageSize = 5;
		//设置当前页码
		int currentPageNo = 1;
		//获得当前页码
				if(pageIndex != null){
					currentPageNo = Integer.valueOf(pageIndex);
				}
				
				
				//总数量表
				int totalCount = 0;
				try{
					totalCount = managerService.checkPageCount();
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("获得总数量失败！");
				}
				//总页数
				Page pages = new Page();
				pages.setCurrentPageNo(currentPageNo);
				pages.setPageSize(pageSize);
				pages.setTotalPageCount(totalCount);
				pages.setStartPage();
				pages.setEndPage();
				System.out.println("总量"+totalCount);
				int totalPageCount =pages.getTotalPageCount();
				int startPage = pages.getStartPage();//获取分页导航初始页
				int endPage = pages.getEndPage();//获取分页导航最后页
				System.out.println("总页数"+totalPageCount+":pageSize:"+pages.getPageSize()+"起始页"+pages.getStartPage()+"最后页"+pages.getEndPage());
				//控制首页与尾页
				if(currentPageNo <1){
					currentPageNo = 1;
				}else if(currentPageNo >totalPageCount){
					currentPageNo =totalPageCount;
				}
				if( totalPageCount == 0){

					
					model.addAttribute("totalCount", totalCount);
					return "Manager_CheckHistory";
				}
				try{
					System.out.println(currentPageNo+":"+pageSize);
					fileList = managerService.getFileCheck(currentPageNo,pageSize);
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("资料获取异常");
				}
				for(DataFile file:fileList){
					System.out.println("资料名称"+file.getFileName());
				}
				model.addAttribute("fileList", fileList);
				model.addAttribute("totalPageCount", totalPageCount);
				model.addAttribute("currentPageNo", currentPageNo);
				model.addAttribute("startPage",startPage );
				model.addAttribute("endPage", endPage);
				model.addAttribute("totalCount", totalCount);
		
		return "Manager_CheckHistory";
	}
	//管理员资料查询
	@RequestMapping("/MDataCheck")
	public String DataCheck(Model model,
			   @RequestParam(value="pageIndex",required=false) String pageIndex){
		List<DataFile> fileList = null;
		//设置页码容量
		int pageSize = 5;
		//设置当前页码
		int currentPageNo = 1;
		//获得当前页码
				if(pageIndex != null){
					currentPageNo = Integer.valueOf(pageIndex);
				}
				
				
				//总数量表
				int totalCount = 0;
				try{
					totalCount = managerService.checkingPageCount();
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("获得总数量失败！");
				}
				//总页数
				Page pages = new Page();
				pages.setCurrentPageNo(currentPageNo);
				pages.setPageSize(pageSize);
				pages.setTotalPageCount(totalCount);
				pages.setStartPage();
				pages.setEndPage();
				System.out.println("总量"+totalCount);
				int totalPageCount =pages.getTotalPageCount();
				int startPage = pages.getStartPage();//获取分页导航初始页
				int endPage = pages.getEndPage();//获取分页导航最后页
				System.out.println("总页数"+totalPageCount+":pageSize:"+pages.getPageSize()+"起始页"+pages.getStartPage()+"最后页"+pages.getEndPage());
				//控制首页与尾页
				if(currentPageNo <1){
					currentPageNo = 1;
				}else if(currentPageNo >totalPageCount){
					currentPageNo =totalPageCount;
				}
				if( totalPageCount == 0){

					
					model.addAttribute("totalCount", totalCount);
					return "Manager_CheckHistory";
				}
				try{
					System.out.println(currentPageNo+":"+pageSize);
					fileList = managerService.getFileChecking(currentPageNo, pageSize);
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("资料获取异常");
				}
				model.addAttribute("fileList", fileList);
				model.addAttribute("totalPageCount", totalPageCount);
				model.addAttribute("currentPageNo", currentPageNo);
				model.addAttribute("startPage",startPage );
				model.addAttribute("endPage", endPage);
				model.addAttribute("totalCount", totalCount);
		return "Manager_DataCheck";
	}
	@RequestMapping("/MDataCheckDeeper")
	public String DataCheckDeeper(Model model,@RequestParam(value="fileId") String fileId){
		DataFile file = null;
		String FolderName ="";
		String FolderName2 = "";
		try{
			file = managerService.getAccDataInfo(fileId);
			 
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("查找准确资料失败！");
		}
		FolderName = file.getFilePath();
		
		FolderName2 = FolderName.substring(FolderName.lastIndexOf("/")+1,FolderName.lastIndexOf("."));
		String fileName = file.getFileName();
		if(fileName.endsWith("doc") || fileName.endsWith("docx")){
			model.addAttribute("imgCon", "word");
		}else if(fileName.endsWith("ppt") || fileName.endsWith("pptx")){
			model.addAttribute("imgCon", "ppt");
		}else if(fileName.endsWith("xls") || fileName.endsWith("xlsx")){
			model.addAttribute("imgCon", "excel");
		}else if(fileName.endsWith("pdf")){
			model.addAttribute("imgCon", "pdf");
		}
		model.addAttribute("fileName", file.getFileName());
		model.addAttribute("fileSize", file.getFileSize());
		model.addAttribute("fileIntroduce", file.getFileIntroduction());
		model.addAttribute("fileCollege", file.getFileCollege());
		model.addAttribute("fileMajor", file.getFileMajor());
		model.addAttribute("fileUpTime", file.getFileUpTime());
		model.addAttribute("fileId", fileId);
		model.addAttribute("FolderName", FolderName2);
		return "Manager_DataCheckDeaper";
	}
	
	@RequestMapping("/MPassCheck")
	@ResponseBody
	public String MPassCheck(@RequestParam(value="optionsRadios") String optionsRadios,
							 @RequestParam(value="fileID") String fileId){
		System.out.println(optionsRadios);
		System.out.println(fileId);
		String isverify = "";
		if(optionsRadios.equals("option1")){
			 isverify = "是";
		}else{
			 isverify = "否";
		}
		try{
			int Update = managerService.UpdataVerify(fileId,isverify);
			if(Update<1){
				System.out.println("插入失败");
			}else{
				System.out.println("成功插入");
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("插入失败");
		}
		
		return "VBNM";
	}
	
	@RequestMapping("/Mpreview")
	public String Mpreview(Model model,
						   @RequestParam(value="fileID") String fileId){
		int imgNum = 0;
		String FolderCollege = "";
		String FolderName = ""; 
		DataFile file = new DataFile();
		try{
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("获取失败！");
		}
		file = managerService.getPreview(fileId);
		imgNum = file.getFilePageNum();
		FolderCollege = file.getFileCollege();
		FolderName = file.getFilePath();
		String FolderName2 = FolderName.substring(FolderName.lastIndexOf("/")+1,FolderName.lastIndexOf("."));
		model.addAttribute("imgNum", imgNum);
		model.addAttribute("FolderCollege", FolderCollege);
		model.addAttribute("FolderName", FolderName2);
		return "ManagerPreview";
	}
}
