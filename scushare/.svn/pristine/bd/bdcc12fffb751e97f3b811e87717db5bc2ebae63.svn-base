package com.scushare.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.scushare.service.MyDownloadService;
import com.scushare.service.UserLogService;
import com.scushare.utils.StringUtil;

import scu.pojo.Download;

@Controller
public class UserMyDownloadController {
	final private static int recordsCountPerPage = 5;

	@Autowired
	private MyDownloadService myDownloadService;
	@Autowired
	private UserLogService userLogService;
	
	@RequestMapping(value="/UMDownloadQuery")

		public String getDownloadPage(HttpServletRequest request, HttpServletResponse response) {
			//更新登陆状态
			userLogService.updateLoginStatu(request);
			//获取用户id
			Integer user_id = (Integer)request.getSession().getAttribute("user_id");
			if(user_id == null) {
				//id为空跳回首页
				return "redirect:/homePage";
			}

			//从数据库获取总记录数
			int recordCount = myDownloadService.pageCount(user_id.intValue());
			if(recordCount == 0) {
				//如果记录为0则直接返回
				request.setAttribute("downloadList", null);
				return "userhomepage_userDownloadRec";
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
			int startIndex = (startPage - 1) * recordsCountPerPage;
					
			//从数据库取对应记录
			List<Download> downloadList = myDownloadService.GetPage(user_id, startIndex, recordsCountPerPage);
			request.setAttribute("downloadList", downloadList);
			request.setAttribute("startPage", startPage);
			request.setAttribute("maxPageNum", maxPageNum);
			request.setAttribute("recordsCountPerPage", recordsCountPerPage);
			return "userhomepage_userDownloadRec";
		}
	}
