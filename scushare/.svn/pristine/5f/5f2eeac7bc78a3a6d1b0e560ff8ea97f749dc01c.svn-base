package com.scushare.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scushare.service.ModifyPasswordService;

@Controller
public class ModifyPasswordController {
	
	@Autowired
	ModifyPasswordService modifyPasswordService;
	
	@RequestMapping(value="/doManagerPasswordModify",method = RequestMethod.POST)
	public String doManagerPasswordModify(RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) {
		
		boolean modifyResult = modifyPasswordService.modifyPassWord(request);
		
		if (modifyResult) {
			return "redirect:/modifySuccess";
		} else {
			return "redirect:/error";
		}
		
	}

}
