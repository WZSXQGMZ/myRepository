package com.scushare.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scushare.service.UserLogService;
import com.scushare.service.UserUploadResult;
import com.scushare.service.UserUploadService;

@Controller
public class UserUploadController {
	@Autowired
	UserUploadService userUploadService;
	
	@Autowired
	UserLogService userLogService;

	@RequestMapping("/doUpload")
	@ResponseBody
	public String doUpload(HttpServletRequest request, HttpServletResponse response) {
		try {
			//上传文件
			UserUploadResult result = userUploadService.doUpload(request, response);
			//判断上传结果
			switch (result) {
			case UPLOAD_SUCCESS:
				return "upload_success";
			case UPLOAD_FAILED:
				return "upload_failed";
			case FILE_SIZE_ERROR:
				return "upload_failed";
			case USER_NAME_NOT_FOUND:
				return "user_name_not_found";
			default:
				return "upload_failed";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@RequestMapping("/getUserUploadProgress")
	public void getUserUploadProgress(HttpServletRequest request, HttpServletResponse response) {
		//获取上传进度
		Long progress = userUploadService.getUploadProgress(request);
		if(progress == null) {
			try {
				response.getWriter().write("not_found");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				response.getWriter().write(progress.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("getProgress");
	}
	
	@RequestMapping("/doUploadChathead")
	public String doUploadChathead(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		//判断登陆状态
		userLogService.updateLoginStatu(request);
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
/*		String user_id_str = request.getParameter("user_id");
		if(user_id.toString().equals(user_id_str) == false) {
			return "redirect:/userInfoPage";
		}*/
		String user_name = (String)request.getSession().getAttribute("user_name");
		//上传头像
		UserUploadResult result = userUploadService.doUploadChathead(request, response);
		//判断上传结果
		switch (result) {
		case UPLOAD_SUCCESS:
			//更新cookie和session
			userLogService.addLoginStatu(user_name, request, response);
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyChatheadSuccess");
			return "redirect:/userInfoPage";
		case UPLOAD_FAILED:
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyChatheadFailed");
			return "redirect:/userInfoPage";
		case FILE_SIZE_ERROR:
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyChatheadFailed");
			return "redirect:/userInfoPage";
		case USER_NAME_NOT_FOUND:
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyChatheadFailed");
			return "redirect:/userInfoPage";
		default:
			redirectAttributes.addFlashAttribute("modifyStatu", "modifyChatheadFailed");
			return "redirect:/userInfoPage";
		}
	}
	
}
