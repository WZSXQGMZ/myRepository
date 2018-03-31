package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.books.model.*;

public class PersonalUserInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession hs = request.getSession();		
		//int usrid = Integer.parseInt((String)hs.getAttribute("userid"));
		//获取userid
		String usrid = request.getParameter("userid");
		PersonalUserInfoHandle puih = new PersonalUserInfoHandle();
		//获取userid对应的userbean记录
		UserBean ub = puih.getUserBean(String.valueOf(usrid));
		String flag = (String)request.getParameter("flag");
		if(ub == null){
			//如果userbean获取失败
			request.setAttribute("UserBean", null);
			request.setAttribute("userBeanState", "getfail");
			hs.setAttribute("userid", usrid + "");
			request.getRequestDispatcher("PersonalUserInfo.jsp").forward(request, response);
		} else if(flag == null){
			//如果flag获取失败
			request.setAttribute("UserBean", ub);
			request.setAttribute("userBeanState", "getfail");
			hs.setAttribute("userid", usrid + "");
			request.getRequestDispatcher("PersonalUserInfo.jsp").forward(request, response);
		} else if(flag.equals("modify")) {
			//flag为modify，跳转到修改页面
			request.setAttribute("UserBean", ub);
			request.setAttribute("userBeanState", "getsuccess");
			String modifyState = request.getParameter("modifyState");
			if(modifyState == null){
				modifyState = "0";
			}
			request.getRequestDispatcher("PersonalUserInfoModify.jsp?modifyState=" + modifyState + "&userid=" + String.valueOf(usrid)).forward(request, response);
		} else if(flag.equals("passModify")) {
			//flag为passModify，跳转到密码修改页面
			String modifyState = (String)request.getParameter("modifyState");
			if(modifyState == null){
				modifyState = "0";
			}
			request.getRequestDispatcher("PersonalUserPasswordModify.jsp?modifyState=" + modifyState + "&userid=" + String.valueOf(usrid)).forward(request, response);
		} else if(flag.equals("getInfo")) {
			//flag为get，跳转到个人信息页面
			request.setAttribute("UserBean", ub);
			
			String modifyState = (String)request.getParameter("modifyState");
			if(modifyState != null) {
				if(modifyState.equals("0")){
					request.setAttribute("userBeanState", "0");
				} else {
					request.setAttribute("userBeanState", "getsuccess");
				}
			} else {
				request.setAttribute("userBeanState", "getsuccess");
			}
			hs.setAttribute("userid", usrid + "");
			request.getRequestDispatcher("PersonalUserInfo.jsp?userid=" + usrid).forward(request, response);
		}
		
	}

}
