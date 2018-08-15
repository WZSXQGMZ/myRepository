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
			//���µ�½״̬
			userLogService.updateLoginStatu(request);
			//��ȡ�û�id
			Integer user_id = (Integer)request.getSession().getAttribute("user_id");
			if(user_id == null) {
				//idΪ��������ҳ
				return "redirect:/homePage";
			}

			//�����ݿ��ȡ�ܼ�¼��
			int recordCount = myDownloadService.pageCount(user_id.intValue());
			if(recordCount == 0) {
				//�����¼Ϊ0��ֱ�ӷ���
				request.setAttribute("downloadList", null);
				return "userhomepage_userDownloadRec";
			}
			//������ҳ��
			int endPage = recordCount % recordsCountPerPage;
			if(endPage != 0) {
				endPage = 1;
			}
			int maxPageNum = recordCount/recordsCountPerPage + endPage;
			
			//��request��ȡҪȡ��ҳ��
			String startPage_str = request.getParameter("pageNum");
			int startPage = 1;
			//�ж�ҳ����ȷ��
			if(startPage_str != null && StringUtil.isInteger(startPage_str)) {
				startPage = Integer.valueOf(startPage_str);
				if(startPage < 1) {
					startPage = 1;
				}else if(startPage > maxPageNum){
					startPage = maxPageNum;
				}
			}
			
			//��ҳ��תΪ����
			int startIndex = (startPage - 1) * recordsCountPerPage;
					
			//�����ݿ�ȡ��Ӧ��¼
			List<Download> downloadList = myDownloadService.GetPage(user_id, startIndex, recordsCountPerPage);
			request.setAttribute("downloadList", downloadList);
			request.setAttribute("startPage", startPage);
			request.setAttribute("maxPageNum", maxPageNum);
			request.setAttribute("recordsCountPerPage", recordsCountPerPage);
			return "userhomepage_userDownloadRec";
		}
	}
