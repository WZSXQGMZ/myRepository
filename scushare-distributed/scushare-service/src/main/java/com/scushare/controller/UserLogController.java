package com.scushare.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scushare.service.UserLogService;
import com.scushare.service.UserLoginStatu;

@Controller
public class UserLogController {

	@Autowired
	private UserLogService userLogService;
	
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String doLogin(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		//��½��������ȡ��½���
		UserLoginStatu uls = userLogService.login(request);
		//���ݵ�½���
		request.setAttribute("login_statu", uls);
	
		if(uls == UserLoginStatu.PWD_OR_UNAME_ERROR) {
			redirectAttributes.addFlashAttribute("login_statu", uls);
			return "redirect:/loginPage";
		}else if(uls == UserLoginStatu.USER_INACTIVE) {
			redirectAttributes.addFlashAttribute("login_statu", uls);
			return "redirect:/loginPage";
		}
		
		//����cookie��session
		userLogService.addLoginStatu(request.getParameter("user_name"), request, response);
		
		//��ȡ��ת��½ǰ����
		String originUrl = (String)request.getParameter("originUrl");
		if(originUrl == null || originUrl.equals("")) {
			originUrl = null;
		}
		
		if(originUrl == null) {
			originUrl = "userHomepage";
		}
		
		//��ת��������ҳ
		return "redirect:/index";
	}
	
	@RequestMapping("/doLogout")
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {
		userLogService.logout(request, response);
		return "redirect:/index";
	}
}