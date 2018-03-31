package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.books.model.*;

public class PersonalUserInfoModifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PersonalUserInfoHandle puih = new PersonalUserInfoHandle();
		
		HttpSession hs = request.getSession();		
		String usrid = (String)hs.getAttribute("userid");
		//int usrid = Integer.parseInt((String)request.getParameter("userid"));
		UserBean ub = packageUserInfo(request, usrid);
		int modifyResult = puih.setUserBean(ub);
		if(modifyResult == 0){
			request.setAttribute("modifyState", "0");
			request.getRequestDispatcher("PersonalUserInfoServlet?flag=getInfo&modifyState=0&userid=" + usrid).forward(request, response);
		} else if(modifyResult == 4) {
			request.setAttribute("modifyState", "4");
			request.getRequestDispatcher("PersonalUserInfoServlet?flag=modify&modifyState=4&userid=" + usrid).forward(request, response);
		} else {
			ub = puih.getUserBean(usrid+"");
			request.setAttribute("UserBean", ub);
			request.setAttribute("userBean", String.valueOf(modifyResult));
			request.getRequestDispatcher("PersonalUserInfoServlet?flag=getInfo&userid=" + usrid).forward(request, response);
		}
	}
	
	/**
	 * 此函数用于打包request中的用户信息
	 * @param request 传递进来的request参数，包含usrid, usrname等信息
	 * @return 打包后的UserBean类型数据
	 */
	private UserBean packageUserInfo(HttpServletRequest request, String userid) {
		PersonalUserInfoHandle puih = new PersonalUserInfoHandle();
		UserBean ub = puih.getUserBean(userid);
		//打包数据
		ub.setUsrname(request.getParameter("usrname"));
		ub.setUsrsex(request.getParameter("usrsex"));
		ub.setUsrage(Integer.parseInt(request.getParameter("usrage")));
		ub.setVocation(request.getParameter("vocation"));
		ub.setUsrtel(request.getParameter("usrtel"));
		ub.setEmail(request.getParameter("email"));
		ub.setAddress(request.getParameter("address"));
		
		return ub;
	}
}
