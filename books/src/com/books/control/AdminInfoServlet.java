package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.AdminBean;
import com.books.model.AdminInfoHandle;

public class AdminInfoServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//doPost solve all
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String flag = request.getParameter("flag");
		String s_adminid = request.getParameter("adminid");
		if(flag.equals("modifyInfo")) {
			AdminInfoHandle aih = new AdminInfoHandle();
			AdminBean adminBean = aih.getAdminBean(s_adminid);
			request.setAttribute("adminBean", adminBean);
			request.getRequestDispatcher("AdminInfoModify.jsp?flag=modifyInfo&adminid=" 
										+ s_adminid).forward(request, response);
		} else if(flag.equals("getInfo")) {
			AdminInfoHandle aih = new AdminInfoHandle();
			AdminBean adminBean = aih.getAdminBean(s_adminid);
			request.setAttribute("adminBean", adminBean);
			request.getRequestDispatcher("AdminInfo.jsp?flag=getInfo&adminid=" 
					+ s_adminid).forward(request, response);
		}
	}

}
