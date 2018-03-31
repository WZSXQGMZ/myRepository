package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.UserBean;
import com.books.model.UserBeanCl;

public class UserModifyServlet extends HttpServlet {

	
	/**
	 * @author 杨旭
	 */
	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//获取要修改的一行数据
		UserBeanCl ubl = new UserBeanCl();
		UserBean ub = new UserBean();
		String pageNow = request.getParameter("pageNow");
		String usrid = request.getParameter("usrid");
		
		ub = ubl.getModifyInfo(usrid);
		request.setAttribute("ub", ub);
		request.setAttribute("pageNow", pageNow);
		request.getRequestDispatcher("UserModify.jsp").forward(request, response);
	}

}
