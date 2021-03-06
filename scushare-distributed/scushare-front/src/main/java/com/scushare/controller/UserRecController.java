package com.scushare.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scushare.entity.UserUploadRec;
import com.scushare.service.UserLogService;
import com.scushare.service.UserRecService;
import com.scushare.utils.StringUtil;

@Controller
public class UserRecController {

	final private static int recordsCountPerPage = 5;
	
	@Autowired
	UserRecService userRecService;
	@Autowired
	UserLogService userLogService;
	
	/**
	 * 用户上传记录分页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userUploadRecPaging")
	public String getUploadPage(HttpServletRequest request, HttpServletResponse response) {
		//更新登陆状态
		userLogService.updateLoginStatu(request);
		//获取用户id
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			//id为空跳回登陆页
			return "redirect:/loginPage";
		}

		//从数据库获取总记录数
		int recordCount = userRecService.getUploadRecCount(user_id.intValue());
		if(recordCount == 0) {
			//如果记录为0则直接返回
			request.setAttribute("uploadRecList", null);
			return "userhomepage_userUploadRec";
		}
		//计算总页数
		int endPage = recordCount % recordsCountPerPage;
		if(endPage != 0) {
			endPage = 1;
		}
		int maxPageNum = recordCount/recordsCountPerPage + endPage;
		
		//从request获取要取的页数
		String startPage_str = request.getParameter("pageNum");
		int startPage = 1;
		//判断页数正确性
		if(startPage_str != null && StringUtil.isInteger(startPage_str)) {
			startPage = Integer.valueOf(startPage_str);
			if(startPage < 1) {
				startPage = 1;
			}else if(startPage > maxPageNum){
				startPage = maxPageNum;
			}
		}
		
		//将页数转为索引
		int startIndex = recordCount - (startPage * recordsCountPerPage);
				
		//从数据库取对应记录
		List<UserUploadRec> uploadRecList = userRecService.getUploadPaging(user_id, startIndex, recordsCountPerPage);
		request.setAttribute("uploadRecList", uploadRecList);
		request.setAttribute("startPage", startPage);
		request.setAttribute("maxPageNum", maxPageNum);
		request.setAttribute("recordsCountPerPage", recordsCountPerPage);
		return "userhomepage_userUploadRec";
	}
	
	@RequestMapping("/deleteFile")
	public String deleteFile(HttpServletRequest request) {
		//更新登陆状态
		userLogService.updateLoginStatu(request);
		//获取用户id
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			//id为空跳回登陆页
			return "redirect:/loginPage";
		}
		String file_id_str = request.getParameter("file_id");
		String pageNum_str = request.getParameter("pageNum");
		if(pageNum_str == null) {
			pageNum_str = "1";
		}
		if(file_id_str == null) {
			return "redirect:/userUploadRecPaging?pageNum=" + pageNum_str;
		}
		userRecService.setFileIsDelete(file_id_str, String.valueOf(user_id));
		return "redirect:/userUploadRecPaging?pageNum=" + pageNum_str;
	}
}
