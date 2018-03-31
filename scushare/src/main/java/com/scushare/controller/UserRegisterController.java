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
	 * ����ע��������
	 * @param request
	 * @param response
	 * @return �ɹ�����ע��ɹ����棬ʧ�ܷ��ش������
	 */
	@RequestMapping("/doRegister")
	public String doRegister(RedirectAttributes redirectAttributes, HttpServletRequest request,HttpServletResponse response) {
		//����ע�����
		UserRegisterResult registerResult = userRegisterService.register(request);
		//�ж�ע����
		switch (registerResult) {
		case REGISTER_SUCCESS:
			//��ȡ��תע��ǰ����
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
	 * ����������֤���ҳ��
	 * @param request
	 * @return �ɹ�������֤�ɹ����棬ʧ�ܷ��ش������
	 */
	@RequestMapping("/checkMail")
	public String checkMail(HttpServletRequest request) {
		//������֤����
		UserRegisterResult registerResult = userRegisterService.mailCheck(request);
		//������֤���
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
	 * ��̨���������û����Ƿ��ѱ�ʹ��
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
	 * ��̨�������������Ƿ��ѱ�ʹ��
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
