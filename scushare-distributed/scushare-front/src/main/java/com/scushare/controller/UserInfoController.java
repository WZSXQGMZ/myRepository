package com.scushare.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scushare.entity.UserModifiableInfo;
import com.scushare.service.UserInfoModifyResult;
import com.scushare.service.UserInfoService;
import com.scushare.service.UserLogService;

@Controller
public class UserInfoController {
	
	@Autowired
	UserLogService userLogService;
	@Autowired
	UserInfoService userInfoService;
	
	@RequestMapping("/userInfoPage")
	public String getUserInfoPage(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		request.setAttribute("userInfo", userInfoService.getUserInfo(user_id));
		return "userhomepage_userInfo";
	}
	
	@RequestMapping("/userInfoModifyPage")
	public String getUserInfoModifyPage(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		String user_gender = request.getParameter("user_gender");
		String user_college = request.getParameter("user_college");
		String user_major = request.getParameter("user_major");
		String user_phone_num = request.getParameter("user_phone_num");
		UserModifiableInfo userMlbInfo = new UserModifiableInfo();
		userMlbInfo.setUser_gender(user_gender);
		userMlbInfo.setUser_college(user_college);
		userMlbInfo.setUser_major(user_major);
		userMlbInfo.setUser_phone_num(user_phone_num);
		request.setAttribute("userMlbInfo", userMlbInfo);
		
		return "userhomepage_userInfoModify";
	}
	
	@RequestMapping("/doUserInfoModify")
	public String doUserInfoModify(RedirectAttributes redirectAttributes, HttpServletRequest request) {
		//判断登陆状态
		userLogService.updateLoginStatu(request);
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		String user_id_str = request.getParameter("user_id");
		if(user_id.toString().equals(user_id_str) == false) {
			return "redirect:/userInfoPage";
		}
		
		//获取表单参数
		String user_gender = request.getParameter("user_gender");
		String user_college = request.getParameter("user_college");
		String user_major = request.getParameter("user_major");
		String user_phone_num = request.getParameter("user_phone_num");
		
		UserInfoModifyResult result = userInfoService.modifyUserInfo(user_id, user_gender, user_college, user_major, user_phone_num);
		switch (result) {
		case MODIFY_SUCCESS:
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyInfoSuccess");
			return "redirect:/userInfoPage";
		case MODIFY_FAILED:
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyInfoFailed");
			return "redirect:/userInfoPage";
		default:
			return "redirect:/userInfoPage";
		}
	}
	
	@RequestMapping("/userPasswordModifyPage")
	public String getUserPasswordModifyPage(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		return "userhomepage_userPasswordModify";
	}
	
	@RequestMapping("/doUserPasswordModify")
	public String doUserPasswordModify(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		//判断登陆状态
		userLogService.updateLoginStatu(request);
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		String user_id_str = request.getParameter("user_id");
		if(user_id.toString().equals(user_id_str) == false) {
			return "redirect:/userInfoPage";
		}
		
		//获取表单参数
		String old_user_password = request.getParameter("oldpwd");
		String new_user_password = request.getParameter("newpwd");
		
		boolean result = userInfoService.modifyUserPassword(user_id, new_user_password, old_user_password);
		if(result == true) {
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyPasswordSuccess");
			userLogService.logout(request, response);
			return "redirect:/loginPage";
		}else {
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyPasswordFailed");
			return "redirect:/userInfoPage";
		}
	}
	
}
