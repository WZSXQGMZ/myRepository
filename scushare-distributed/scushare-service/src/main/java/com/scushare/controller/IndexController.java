package com.scushare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scushare.entity.FileInfo;
import com.scushare.entity.UserInfo;
import com.scushare.mapper.UserRecMapper;
import com.scushare.service.ManagerService;
import com.scushare.service.UserLogService;
import com.scushare.service.UserSearchService;

import scu.pojo.DataFile;
import com.scushare.utils.*;

@Controller
public class IndexController {
	@Autowired
	private UserRecMapper userRecMapper;
	@Autowired
	private UserSearchService userSearchService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private UserLogService userLogService;
	
	@RequestMapping("/")
	public String index0() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request){
		userLogService.updateLoginStatu(request);
		List<Map<String, String>>list = new ArrayList<Map<String, String>>();
		List<String> fileIds = new ArrayList<String>();
		fileIds = userSearchService.getRandomFile(0);	
		try{
			for (int i = 0; i < fileIds.size(); i++) {
				String fileIdStr = fileIds.get(i);
				FileInfo fileInfo = userSearchService.getFileInfoById(fileIdStr);
				UserInfo user = userRecMapper.getUserInfoByFileId(fileIds.get(i));
				String FolderName = fileInfo.getFile_path();
				String FolderName2 = FolderName.substring(FolderName.lastIndexOf("/")+1,FolderName.lastIndexOf("."));
				user = userRecMapper.getUserInfoByFileId(fileIds.get(i));
				
				Map<String, String> currMap = new HashMap<String, String>();
				currMap.put("fileName", fileInfo.getFile_name());
				currMap.put("fileId", fileIdStr);
				currMap.put("userName", user.getUser_name());
				currMap.put("FolderName", FolderName2);
				currMap.put("fileCollege",fileInfo.getFile_college());
				
				list.add(currMap);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("查找准确资料失败！");
			return "redirect:/error";
		}
		model.addAttribute("list", list);
		return "index";
	}
	@RequestMapping("/document")
	public String document(HttpServletRequest request, Model model){
		String fileType = request.getParameter("type");
		String college = request.getParameter("college");
		String major = request.getParameter("major");
		String tempPage = request.getParameter("page");
		if(tempPage == null)
			tempPage = "1";
		Integer pageNum = Integer.parseInt(tempPage);
		List<DataFile> files= new ArrayList<DataFile>();
		List<Map>list = new ArrayList<Map>();
		if(fileType != null){
			if(fileType=="all")
				fileType = null;
			else
				fileType = "." + fileType;
		}
			
		if(college == null)
			college = "请选择学院";
		if(major == null)
			major = "请选择专业";
		System.out.println(college);
		System.out.println(major);
		System.out.println(fileType);
		Page page = managerService.getIndexPage(college,
												major,
												"",
												pageNum,
												4,
												fileType);
		files = managerService.getIndexFile(college,
											major,
											"",
											pageNum,
											4,
											fileType);

		for (DataFile dataFile : files) {
			
			String FolderName2 = null;
			String FolderName = null;
			Map map=new HashMap();
			String type = FileType.judge(dataFile.getFileName());
			map.put("type", type);
			map.put("fileId",dataFile.getFileId());
			map.put("fileName", dataFile.getFileName());
			map.put("fileIntro", dataFile.getFileIntroduction());
			map.put("userName", dataFile.getFileUsername());
			map.put("upTime", dataFile.getFileUpTime());
			map.put("fileCollege", dataFile.getFileCollege());
			FolderName = dataFile.getFilePath();
			FolderName2 = FolderName.substring(FolderName.lastIndexOf("/")+1,FolderName.lastIndexOf("."));
			map.put("image", FolderName2);
			list.add(map);
			
			System.out.println("--------");
			System.out.println(dataFile.getFileName());
			}
		model.addAttribute("list", list);
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("pages", page.getTotalCount());
		return "document";
	}
	@RequestMapping("/searchResult")
	public String searchResult(HttpServletRequest request, Model model){
		
		String keyWord = request.getParameter("search");
		String tempPage = request.getParameter("page");
		String fileType = request.getParameter("type");
		if(keyWord == null) 
			return "redirect:/error";
		if(fileType != null)
			fileType = "." + fileType;
		Integer num = null;
		Integer recommendNum = null;
		Integer size = 6;
		Integer pageMin = null;
		Integer pageMax = null;
		Integer pages = null;
		List<DataFile> files= new ArrayList<DataFile>();
		List<String> fileTypes = new ArrayList<String>();
		List<Map>list = new ArrayList<Map>();
		//recommend-file
		List<DataFile> recommendFiles= new ArrayList<DataFile>();
		List<String> recommendFileTypes = new ArrayList<String>();
		List<Map>recommendList = new ArrayList<Map>();
		if(tempPage == null){
			tempPage = "1";
		}
		int pageNum = Integer.parseInt(tempPage);
		Page page = new Page();
		files = userSearchService.getIndexFile(keyWord, pageNum, size, page);
		Page recommendPage = managerService.getIndexPage("请选择学院",
														 "请选择专业",
														 "",
														 1,
														 4 ,
														 "");
		
		num = page.getTotalCount();
		recommendNum = recommendPage.getTotalCount();
		if(num !=0)
			pageMin = pageNum*6-5;
		else
			pageMin = 0;
		if(num >= pageNum*6)
			pageMax = pageNum*6;
		else
			pageMax = num;
		if(num%6 == 0)
			pages = num/6;
		else 
			pages = num/6+1;
		recommendFiles = managerService.getIndexFile("请选择学院",
													 "请选择专业",
													 "",
													 1,
													 4 ,
													 "");
		
		for (DataFile dataFile : files) {
				String FolderName2 = null;
				String FolderName = null;
				Map map=new HashMap();
				String type = FileType.judge(dataFile.getFileName());
				map.put("type", type);
				map.put("fileId",dataFile.getFileId());
				map.put("fileName", dataFile.getFileName());
				map.put("fileIntro", dataFile.getFileIntroduction());
				map.put("userName", dataFile.getFileUsername());
				map.put("upTime", dataFile.getFileUpTime());
				map.put("fileCollege", dataFile.getFileCollege());
				FolderName = dataFile.getFilePath();
				FolderName2 = FolderName.substring(FolderName.lastIndexOf("/")+1,FolderName.lastIndexOf("."));
				map.put("image", FolderName2);
				list.add(map);
				}
		for (DataFile dataFile : recommendFiles) {
			Map map=new HashMap();
			String type = FileType.judge(dataFile.getFileName());
			map.put("type", type);
			map.put("fileId",dataFile.getFileId());
			map.put("fileName", dataFile.getFileName());
			map.put("pages", dataFile.getFilePageNum());
			recommendList.add(map);
			}
		//当前页数
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("pageMin",pageMin);
		model.addAttribute("pageMax",pageMax);
		//可分页数
		model.addAttribute("pages",pages);
		model.addAttribute("pageTotalNum",num);
 		model.addAttribute("fileTypes",fileTypes);
		model.addAttribute("list",list);
		model.addAttribute("recommendList",recommendList);
		Set<String> keywordsSet = userSearchService.getHotestKeywords();
		model.addAttribute("keywordsSet", keywordsSet);
		return "searchResult";
	}
	
	@RequestMapping("/preview")
	public String preview(HttpServletRequest request, Model model){
		String fileId = request.getParameter("fileID");	
		if(fileId == null) {
			return "redirect:/error";
		}else {
			//temp
			DataFile tempFile = null;
			Map map1=new HashMap();
			Map map2=new HashMap();
			Map map3=new HashMap();
			Map map4=new HashMap();
			Map map5=new HashMap();
			List<Map>list = new ArrayList<Map>();
			String tempFolderName =null;
			String tempFileType = null;
			List<String> fileIds = new ArrayList<String>();
			
			DataFile file = null;
			String fileType = null;
			String FolderName ="";
			String FolderName2 = "";
				
			try{
				file = managerService.getAccDataInfo(fileId);		 
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("查找准确资料失败！");
				return "redirect:/error";
			}
			FolderName = file.getFilePath();
			FolderName2 = FolderName.substring(FolderName.lastIndexOf("/")+1,FolderName.lastIndexOf("."));
			fileType = FileType.judge(FolderName);
			//请求文档用户信息
			UserInfo user = userRecMapper.getUserInfoByFileId(fileId);
			//请求相似文档
			
			fileIds = userSearchService.getFileInfoByCollege(fileId, file.getFileCollege());
			try{
				for (int i = 0; i < fileIds.size(); i++) {
					tempFile = managerService.getAccDataInfo(fileIds.get(i));
					tempFolderName = tempFile.getFilePath();
					tempFileType = FileType.judge(tempFolderName);
					switch (i) {
						case 0:
							map1.put("fileName", tempFile.getFileName());
							map1.put("fileId", fileIds.get(i));
							map1.put("pageNum", tempFile.getFilePageNum());
							map1.put("fileType", tempFileType);
							break;
						case 1:
							map2.put("fileName", tempFile.getFileName());
							map2.put("fileId", fileIds.get(i));
							map2.put("pageNum", tempFile.getFilePageNum());
							map2.put("fileType", tempFileType);
							break;
						case 2:
							map3.put("fileName", tempFile.getFileName());
							map3.put("fileId", fileIds.get(i));
							map3.put("pageNum", tempFile.getFilePageNum());
							map3.put("fileType", tempFileType);
							break;
						case 3:
							map4.put("fileName", tempFile.getFileName());
							map4.put("fileId", fileIds.get(i));
							map4.put("pageNum", tempFile.getFilePageNum());
							map4.put("fileType", tempFileType);
							break;
						case 4:
							map5.put("fileName", tempFile.getFileName());
							map5.put("fileId", fileIds.get(i));
							map5.put("pageNum", tempFile.getFilePageNum());
							map5.put("fileType", tempFileType);
							break;
					}
				}
				list.add(map1);
				list.add(map2);
				list.add(map3);
				list.add(map4);
				list.add(map5);
				file = managerService.getAccDataInfo(fileId);		 
				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("查找准确资料失败！");
				return "redirect:/error";
			}
			
			model.addAttribute("fileName", file.getFileName());
			model.addAttribute("fileIntroduce", file.getFileIntroduction());
			model.addAttribute("fileCollege", file.getFileCollege());
			model.addAttribute("fileMajor", file.getFileMajor());
			model.addAttribute("fileUpTime", file.getFileUpTime());
			model.addAttribute("fileId", fileId);
			model.addAttribute("FolderName", FolderName2);
			model.addAttribute("pageNum", file.getFilePageNum());
			model.addAttribute("fileType", fileType);
			model.addAttribute("userName", user.getUser_name());
			model.addAttribute("userImage", user.getUser_chathead());
			//推荐相似文档信息
			model.addAttribute("list", list);
			return "preview";
		}
		
	}
}
