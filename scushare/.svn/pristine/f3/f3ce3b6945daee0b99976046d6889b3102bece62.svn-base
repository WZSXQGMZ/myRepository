package com.scushare.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scushare.service.UserRegisterResult;
import com.scushare.service.UserRegisterService;

@Controller
public class UserRegisterController {
	
	@Autowired
	UserRegisterService userRegisterService;
	
	/**
	 * 返回注册结果界面
	 * @param request
	 * @param response
	 * @return 成功返回注册成功界面，失败返回错误界面
	 */
	@RequestMapping("/doRegister")
	public String doRegister(RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		//进行注册操作
		UserRegisterResult registerResult = userRegisterService.register(request);
		//判断注册结果
		switch (registerResult) {
		case REGISTER_SUCCESS:
			//获取跳转注册前链接
			String originUrl = request.getParameter("oringinUrl");
			redirectAttributes.addFlashAttribute("oringinUrl", originUrl);
			redirectAttributes.addFlashAttribute("RigisterStatu", "registerSuccess");
			return "redirect:/loginPage";
		case USER_INFO_ERROR:
			return "redirect:/error";
		case MAIL_SEND_ERROR:
			return "redirect:/error";
		case DB_INSERT_ERROR:
			return "redirect:/error";
		case UNDEFINED_ERROR:
			return "redirect:/error";
		default:
			return "redirect:/error";
		}
	}
	@RequestMapping("/registerSuccess")
	public String registerSuccess() {
		return "registerSuccess";
	}
	
	/**
	 * 返回邮箱验证结果页面
	 * @param request
	 * @return 成功返回验证成功界面，失败返回错误界面
	 */
	@RequestMapping("/checkMail")
	public String checkMail(HttpServletRequest request) {
		//进行验证操作
		UserRegisterResult registerResult = userRegisterService.mailCheck(request);
		//返回验证结果
		switch (registerResult) {
		case MAIL_CHECK_SUCCESS:
			return "redirect:/mailCheckSuccess";
		case MAIL_OVERDUE_ERROR:
			return "redirect:/mailOverdueError";
		case MAIL_CHECK_ERROR:
			return "redirect:/error";
		default:
			return "redirect:/error";
		}
	}
	@RequestMapping("/mailCheckSuccess")
	public String mailCheckSuccess() {
		return "mailCheckSuccess";
	}
	@RequestMapping("/mailOverdueError")
	public String mailOverdueError() {
		return "mailOverdueError";
	}
	
	/**
	 * 后台访问请求用户名是否已被使用
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkUserNameExisted")
	public void checkUserNameExisted(HttpServletRequest request, HttpServletResponse response) {
		String user_name = request.getParameter("user_name");
		if(userRegisterService.userNameExistedCheck(user_name)) {
			try {
				response.getWriter().print("true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				response.getWriter().print("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 后台访问请求邮箱是否已被使用
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkUserMailExisted")
	public void checkUserMailExisted(HttpServletRequest request, HttpServletResponse response) {
		String user_mail = request.getParameter("user_mail");
		if(userRegisterService.userMailExistedCheck(user_mail)) {
			try {
				response.getWriter().print("true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				response.getWriter().print("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
