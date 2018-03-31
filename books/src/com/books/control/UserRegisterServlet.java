package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.Tools;
import com.books.model.UserRegisterCl;

public class UserRegisterServlet extends HttpServlet {

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
		
		String flag = request.getParameter("flag");
		//获取信息
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		String truename = request.getParameter("TrueName");
		String sex = request.getParameter("Sex");
		int age = Integer.parseInt(request.getParameter("Age"));
		String vocation = request.getParameter("Vocation");
		String tel = request.getParameter("Tel");
		String email = request.getParameter("Email");
		String address = request.getParameter("Address");
		//转码
		truename = Tools.getCovertCode(truename);
		sex = Tools.getCovertCode(sex);
		vocation = Tools.getCovertCode(vocation);
		address = Tools.getCovertCode(address);
		
		//调用模型层来将信息添加到数据库
		UserRegisterCl urc = new UserRegisterCl();
		boolean result = urc.register(username, password, truename, sex, age, vocation, tel, email, address);
		if (result){
			//注册成功
			request.setAttribute("flag", "Success");
			if (flag!=null && flag.equals("adminRegister"))
				request.getRequestDispatcher("UserClServlet?pageNow=1").forward(request, response);
			else
				request.getRequestDispatcher("Login.jsp").forward(request, response);
		}else {
			//注册失败
			request.setAttribute("flag", "Failure");
			request.getRequestDispatcher("Register.jsp").forward(request, response);
		}
	}

}
