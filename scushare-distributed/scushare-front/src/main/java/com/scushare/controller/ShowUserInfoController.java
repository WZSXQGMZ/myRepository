package com.scushare.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scushare.service.PageService;

import scu.pojo.User;

@Controller
public class ShowUserInfoController {
	
	
	@RequestMapping(value="/MshowUser")
	public String userQuery(Model model,
			@RequestParam(value="userId",required=false) String userId,
			@RequestParam(value="userName",required=false) String userName,
			@RequestParam(value="userGender",required=false) String userGender,
			@RequestParam(value="userCollege",required=false) String userCollege,
			@RequestParam(value="userMajor",required=false) String userMajor,
			@RequestParam(value="userGrade",required=false) String userGrade,
			@RequestParam(value="userMail",required=false) String userMail,
			@RequestParam(value="userPhoneNum",required=false) String userPhoneNum,
			@RequestParam(value="userRegTime",required=false) String userRegTime,
			@RequestParam(value="userRegIp",required=false) String userRegIp) throws UnsupportedEncodingException{
		    
		model.addAttribute("userId",userId);
		model.addAttribute("userName",userName);
		model.addAttribute("userGender", userGender);
		model.addAttribute("userCollege",userCollege);
		model.addAttribute("userMajor",userMajor);
		model.addAttribute("userGrade",userGrade);
		model.addAttribute("userMail",userMail);
		model.addAttribute("userPhoneNum",userPhoneNum);
		model.addAttribute("userRegTime",userRegTime);
		model.addAttribute("userRegIp",userRegIp);
		
		return "ManagerPage_userinfo";
		
	}
}
