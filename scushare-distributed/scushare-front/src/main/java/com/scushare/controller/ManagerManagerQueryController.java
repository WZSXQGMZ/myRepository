package com.scushare.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scushare.service.ManagerInfoService;

import scu.pojo.Manager;

@Controller
public class ManagerManagerQueryController {
	@Autowired
	private ManagerInfoService managerInfoService;
	
	@RequestMapping(value="/MEnterManagerQuery")
	public String directReturn() {
		return "Manager_ManagerQuery";
	}
	
	@RequestMapping(value="/MmanagerQuery")
	public String managerQuery(Model model,@RequestParam(value="managerName",required=false)String managerName ) 
			throws UnsupportedEncodingException{
		List<Manager> managerList= null;
		
		if(managerName == null){
			managerName ="";
		}
		
		try{
			managerList = managerInfoService.find(managerName);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("����Ա���ݻ�ȡ�쳣");
		}
		model.addAttribute("managerList", managerList);
		return "Manager_ManagerQuery";
	}

}