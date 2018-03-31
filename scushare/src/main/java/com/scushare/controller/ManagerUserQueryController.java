package com.scushare.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scushare.service.PageService;
import com.scushare.utils.Page;
import scu.pojo.User;
@Controller
public class ManagerUserQueryController {
	
	@Autowired
	private PageService pageService;
	
	@RequestMapping("/MEnterUserQuery")
	public String directReturn() {
		return "Manager_UserQuery";
	}
	
	@RequestMapping(value="/MuserQuery")
	public String userQuery(Model model,
			@RequestParam(value="userCollege",required=false) String userCollege,
			@RequestParam(value="userMajor",required=false) String userMajor,
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="pageIndex",required=false) String pageIndex) throws UnsupportedEncodingException{
		
		List<User> userList = null;
		//设置页码容量
		int pageSize = 5;
		//设置当前页码
		int currentPageNo = 1;
		if(userName == null){
			userName ="";
		}
		//
		if(userCollege.equals("请选择学院")){
			userCollege = null;
		}
		if(userMajor.equals("请选择专业")){
			userMajor = null;
		}
		//获得当前页码
		if(pageIndex != null){
			currentPageNo = Integer.valueOf(pageIndex);
		}
		
		//总数量表
		int totalCount = 0;
		try{
			totalCount = pageService.pageCount(userCollege,userMajor,userName);
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
		int startPage = pages.getStartPage();
		int endPage = pages.getEndPage();
		System.out.println("总页数"+totalPageCount+":pageSize:"+pages.getPageSize()+"起始页"+pages.getStartPage()+"最后页"+pages.getEndPage());
		//控制首页与尾页
		if(currentPageNo <1){
			currentPageNo = 1;
		}else if(currentPageNo >totalPageCount){
			currentPageNo =totalPageCount;
		}
		if( totalPageCount == 0){
			model.addAttribute("totalCount", totalCount);
			return "Manager_UserQuery";
		}
		try{
			userList = pageService.GetPage(userCollege, userMajor, userName, currentPageNo, pageSize);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("用户数据获取异常");
		}
		
		model.addAttribute("userList", userList);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPageNo", currentPageNo);	
		model.addAttribute("startPage",startPage );
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalCount", totalCount);
		
		if(userCollege == null){
			model.addAttribute("userCollege","请选择学院");
		}else{
			model.addAttribute("userCollege",userCollege);
		}
		if(userMajor == null){
			model.addAttribute("userMajor","请选择专业");
		}else{
			model.addAttribute("userMajor", userMajor);
		}
			model.addAttribute("userName", userName);
		
		return "Manager_UserQuery";
	}
	
	@RequestMapping("/MDeleteUser")
	@ResponseBody
	public String userDelet(@Param(value="userId") String userId){
		boolean flag = true;
		flag = pageService.userdelet(userId);
		return "rua";
	}
}
