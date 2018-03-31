package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.AdminInfoHandle;
import com.books.model.ManagerLoginCl;

public class ManagerLoginServlet extends HttpServlet {


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

		String managername = request.getParameter("Managername");
		String password = request.getParameter("Password");
		
		if(managername!=null && password!=null){
			//调用模型层来验证用户是否合法
			ManagerLoginCl mlc = new ManagerLoginCl();	//实例化对象
			String flag = mlc.checkmanager(managername, password);
			if (flag!=null && flag=="1"){
				//合法 跳转
				AdminInfoHandle aih = new AdminInfoHandle();
				String adminid = aih.getAdminID(managername);
				String s_adminid = adminid + "";
				request.getRequestDispatcher("Admin.jsp?adminid=" + s_adminid).forward(request, response);
			}else if(flag!=null && flag=="2"){
				//密码不合法 跳转ת
				request.setAttribute("flag", "passError");
				request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
			}else if(flag!=null && flag=="3"){
				//用户名不合法  跳转ת
				request.setAttribute("flag", "userError");
				request.getRequestDispatcher("AdminLogin.jsp").forward(request, response);
			}
		}
	}

}
