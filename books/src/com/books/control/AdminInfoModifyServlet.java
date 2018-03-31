package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.AdminBean;
import com.books.model.AdminInfoHandle;
import com.books.model.Tools;

public class AdminInfoModifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doPost solve all
		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String flag = request.getParameter("flag");
		if(flag.equals("modifyInfo")) {
			//如果flag为"modifyInfo"
			String s_adminid = request.getParameter("adminid");
			
			String adminName = Tools.getCovertCode(request.getParameter("adminName"));
			String adminTel = request.getParameter("adminTel");
			String adminEmail = request.getParameter("adminEmail");
			//打包进adminBean
			AdminBean adminBean = new AdminBean();
			
			adminBean.setAdminId(s_adminid);
			adminBean.setAdminName(adminName);
			adminBean.setAdminTel(adminTel);
			adminBean.setAdminEmail(adminEmail);
			
			//实例化AdminInfoHandle
			AdminInfoHandle aih = new AdminInfoHandle();
			int result = aih.setAdminBean(adminBean);
			if(result == 0) {
				//如果修改成功
				request.setAttribute("adminBean", adminBean);
				request.getRequestDispatcher("AdminInfo.jsp?flag=modifyInfo&modifyResult=0&adminid=" 
											+ s_adminid).forward(request, response);
			} else {
				//如果修改失败
				//重新向数据库获取用户信息
				adminBean = aih.getAdminBean(s_adminid);
				request.setAttribute("adminBean", adminBean);
				request.getRequestDispatcher("AdminInfoModify.jsp?flag=modifyInfoReturn&modifyResult=" + result + "&adminid=" 
											+ s_adminid).forward(request, response);
			}
		} else if(flag.equals("modifyPass")) {
			//如果flag为"modifyPass"
			String s_adminid = request.getParameter("adminid");
			String oldPass = request.getParameter("oldPass");
			String newPass = request.getParameter("newPass");
			//实例化AdminInfoHandle
			AdminInfoHandle aih = new AdminInfoHandle();
			int result = aih.setAdminPassword(s_adminid, oldPass, newPass);
			if(result == 0) {
				AdminBean adminBean = aih.getAdminBean(s_adminid);
				request.setAttribute("adminBean", adminBean);
				request.getRequestDispatcher("AdminInfo.jsp?flag=modifyPass&modifyResult=" + result + "&adminid=" 
											+ s_adminid).forward(request, response);
			} else {
				request.getRequestDispatcher("AdminPassModify.jsp?flag=modifyPassReturn&modifyResult=" + result + "&adminid=" 
						+ s_adminid).forward(request, response);
			}
		}
	} 

}
