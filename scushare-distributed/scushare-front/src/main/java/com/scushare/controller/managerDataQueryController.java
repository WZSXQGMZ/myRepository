package com.scushare.controller;


import java.io.File;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scushare.service.ManagerService;
import com.scushare.utils.Page;

import scu.pojo.DataFile;

@Controller
public class managerDataQueryController {

	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(value="/MdataQuery")
	public String dataQuery(Model model,
							@RequestParam(value="fileCollege",required=false) String fileCollege,
							@RequestParam(value="fileMajor",required=false) String fileMajor,
							@RequestParam(value="fileName",required=false) String fileName,
							@RequestParam(value="pageIndex",required=false) String pageIndex) throws UnsupportedEncodingException{
		
		
		
		List<DataFile> fileList = null;
		//设置页码容量
		int pageSize = 5;
		//设置当前页码
		int currentPageNo = 1;
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
		//获得当前页码
		if(pageIndex != null){
			currentPageNo = Integer.valueOf(pageIndex);
		}
		
		
		//总数量表
		int totalCount = 0;
		try{
			totalCount = managerService.pageCount(fileCollege,fileMajor,fileName);
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
			return "Manager_DataQuery";
		}
		try{
			fileList = managerService.GetPage(fileCollege, fileMajor, fileName, currentPageNo, pageSize);
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
		if(fileCollege == null){
			model.addAttribute("fileCollege","请选择学院");
		}else{
		model.addAttribute("fileCollege",fileCollege);
		}
		if(fileMajor == null){
			model.addAttribute("fileMajor","请选择专业");
		}else{
		model.addAttribute("fileMajor", fileMajor);
		}
		model.addAttribute("fileName", fileName);
		
		
		return "Manager_DataQuery";
	}
	
	@RequestMapping("/MDeletData")
	@ResponseBody
	public String dataDelet(@Param(value="fileId") String fileId){
		boolean flag = true;
		flag = managerService.datadelet(fileId);
		return "rua";
	}
	
	@RequestMapping("/MDownload")
	@ResponseBody
	public ResponseEntity<byte[]> DownloadData(
								Model model,
								@Param(value="fileId") String fileId,
								@Param(value="fileName") String fileName,
								HttpServletResponse response,
								HttpServletRequest request){
		String fileUrl = managerService.dataDownload(fileId);
		String ReaFileUrl = "E:\\SCUSHARE_DATA"+fileUrl;
		System.out.println("真实路径"+ReaFileUrl);
		
		try{
			// 得到要下载的文件
            File file = new File(ReaFileUrl);
            System.out.println(ReaFileUrl);
            //如果文件不存在，则显示下载失败
            if (!file.exists()) {
               model.addAttribute("NoFile", "Sorry!文件丢失");
                return null;
            } else {
//            	下面开始设置HttpHeaders,使得浏览器响应下载
                HttpHeaders headers = new HttpHeaders();
//                    设置响应方式
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                    设置响应文件
                fileName = URLEncoder.encode(fileName,"UTF-8");
                headers.setContentDispositionFormData("attachment", fileName);
//                    把文件以二进制形式写回
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);

            }  
            
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
	
