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
	 * �û��ϴ���¼��ҳ
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userUploadRecPaging")
	public String getUploadPage(HttpServletRequest request, HttpServletResponse response) {
		//���µ�½״̬
		userLogService.updateLoginStatu(request);
		//��ȡ�û�id
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			//idΪ�����ص�½ҳ
			return "redirect:/loginPage";
		}

		//�����ݿ��ȡ�ܼ�¼��
		int recordCount = userRecService.getUploadRecCount(user_id.intValue());
		if(recordCount == 0) {
			//�����¼Ϊ0��ֱ�ӷ���
			request.setAttribute("uploadRecList", null);
			return "userhomepage_userUploadRec";
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
		int startIndex = recordCount - (startPage * recordsCountPerPage);
				
		//�����ݿ�ȡ��Ӧ��¼
		List<UserUploadRec> uploadRecList = userRecService.getUploadPaging(user_id, startIndex, recordsCountPerPage);
		request.setAttribute("uploadRecList", uploadRecList);
		request.setAttribute("startPage", startPage);
		request.setAttribute("maxPageNum", maxPageNum);
		request.setAttribute("recordsCountPerPage", recordsCountPerPage);
		return "userhomepage_userUploadRec";
	}
	
	@RequestMapping("/deleteFile")
	public String deleteFile(HttpServletRequest request) {
		//���µ�½״̬
		userLogService.updateLoginStatu(request);
		//��ȡ�û�id
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			//idΪ�����ص�½ҳ
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