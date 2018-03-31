package com.scushare.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.scushare.service.UserLogService;

@Controller
public class UserController {
	@Autowired
	UserLogService userLogService;
	
	@RequestMapping("/homePage")
	public ModelAndView homePage(ModelAndView mv, HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		mv.setViewName("homePage");
		return mv;
	}
	
	@RequestMapping("/login")
	public String login() {
		return "redirect:/loginPage";
	}
	@RequestMapping("/loginPage")
	public String getLoginPage(HttpServletRequest request) {
		//获取跳转登陆前链接
		String originUrl = (String)request.getParameter("originUrl");
		if(originUrl == null || originUrl.equals("")) {
			originUrl = (String)request.getAttribute("originUrl");
			if(originUrl == null || originUrl.equals("")) {
				originUrl = null;
			}
		}
		if(originUrl != null) {
			request.setAttribute("originUrl", originUrl);
		}
		
		return "U-login";
	}
	
	@RequestMapping("/registerPage")
	public String getRregisterPage(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		return "register";
	}
	@RequestMapping("/register")
	public String register(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		return "redirect:/registerPage";
	}
	
	@RequestMapping("/uploadPage")
	public String uploadPage(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		return "userhomepage_userUpload";
	}
	
	@RequestMapping("/uploadRecPage")
	public String getUploadRecPage(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		//重定向
		return "redirect:/userUploadRecPaging";
	}
	@RequestMapping("/userHomepage")
	public String getUserHomepage() {
		return "redirect:/uploadRecPage";
	}

	@RequestMapping("/uploadChatheadPage")
	public String getUploadChatheadPage(HttpServletRequest request) {
		userLogService.updateLoginStatu(request);
		return "userhomepage_userChatheadModify";
	}
}
