package com.books.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.Tools;
import com.books.model.UserBean;
import com.books.model.UserBeanCl;

public class UserClServlet extends HttpServlet {

	int pageSize = 10;
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

		String s_pageNow=request.getParameter("pageNow");
		int pageNow = Integer.parseInt(request.getParameter("pageNow"));

		if(s_pageNow!=null){
			pageNow=Integer.parseInt(s_pageNow);
		}
		String flag=request.getParameter("flag");
		
		//执行单行修改调度
		if(flag!=null&&flag.equals("modify")){
			//获取表单元素中的值
			String usrid = request.getParameter("usrid");
			String usraccount = request.getParameter("usraccount");
			String usrpassword = request.getParameter("usrpassword");
			String usrname = request.getParameter("usrname");
			String usrsex = request.getParameter("usrsex");
			String usrage = request.getParameter("usrage");
			String vocation = request.getParameter("vocation");
			String usrtel = request.getParameter("usrtel");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String usrcredit = request.getParameter("usrcredit");
			usrname = Tools.getCovertCode(usrname);
			usrsex = Tools.getCovertCode(usrsex);
			vocation = Tools.getCovertCode(vocation);
			address = Tools.getCovertCode(address);
			//调用逻辑层
			//userCl uCl=new userCl();
			if(new UserBeanCl().getModuser(usrid, usraccount, usrpassword, usrname, usrsex, usrage, vocation, usrtel, email, address, usrcredit)){
				//修改成功
				request.setAttribute("pageNow", pageNow+"");
				request.setAttribute("flag", "modifySuccess");
				request.getRequestDispatcher("Success.jsp").forward(request, response);
			}else {
				//修改失败
				request.setAttribute("pageNow", pageNow+"");
				request.setAttribute("flag", "modifyFailure");
				request.getRequestDispatcher("Error.jsp").forward(request, response);
			}
		}
		
		//执行删除调度
		if(flag!=null && flag.equals("del")){
			String userid=request.getParameter("userid");
			if(userid!=null){
				int usrid=Integer.parseInt(userid);
				//调用逻辑层中删除功能
				UserBeanCl ubc=new UserBeanCl();
				if(ubc.getDeluser(usrid)){
					//删除成功
					request.setAttribute("pageNow", pageNow+"");
					request.setAttribute("flag", "delSuccess");
					request.getRequestDispatcher("Success.jsp").forward(request, response);
				}else {
					//删除失败
					request.setAttribute("pageNow", pageNow+"");
					request.setAttribute("flag", "delFailure");
					request.getRequestDispatcher("Error.jsp").forward(request, response);
				}
			}
		}
		//执行添加调度
		if(flag!=null && flag.equals("add")){
			request.setAttribute("flag", "adminRegister");
			request.getRequestDispatcher("Register.jsp").forward(request, response);
		}

		UserBeanCl ubl = new UserBeanCl();
		ArrayList<UserBean> list = ubl.getPage(pageSize, pageNow);
		request.setAttribute("list", list);
		int pageCount = ubl.getPageCount(pageSize);
		request.setAttribute("pageCount", pageCount+"");
		request.setAttribute("pageNow", pageNow+"");
		request.getRequestDispatcher("UserManagement.jsp").forward(request, response);
	}

}
