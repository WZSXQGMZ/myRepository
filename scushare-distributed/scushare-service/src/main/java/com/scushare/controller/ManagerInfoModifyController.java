package com.scushare.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scushare.service.ManagerService;

import scu.pojo.Manager;



@Controller
public class ManagerInfoModifyController {
	
	@Autowired
	private ManagerService managerService;
	
	@RequestMapping("/MInfoModify")
	public String MInfoModify(){
		return "Manager_modifyPassword";
	}
	
	
	
	//验证原密码
	@RequestMapping("/MOldpassword")
	public String MOldpassword(Model model,@RequestParam(value="oldpwd") String oldPassword,
							   @RequestParam(value="newpwd" )String newPassword,
							   @RequestParam(value="newpwdcof" )String newpwdcof,
							   HttpServletRequest request){
		System.out.println("sadasd1");
		HttpSession session = request.getSession();
		Manager manager =(Manager) session.getAttribute("managerSession");
		if(manager !=null){
		System.out.println("管理员："+manager.getManagerName());
		String OrgPassword = "";//原密码
		
		OrgPassword = managerService.getOldPWd(manager.getManagerName());
		if(OrgPassword.equals(oldPassword)){
			if(newPassword.equals(newpwdcof)){
				try{
					managerService.UpdataPwd(newPassword,manager.getManagerName());
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("更新失败");
				}
			}
			model.addAttribute("ModifySuccess", "修改成功");
			return "Manager_modifyPassword";
			
		}else{
			model.addAttribute("ConError", "密码错误");
			
		    return "Manager_modifyPassword";	
		}
		
		}else{
			model.addAttribute("ConError", "请重新登录");
			return "Manager_modifyPassword";
		}
	}	
	
		
			
		
	
}
