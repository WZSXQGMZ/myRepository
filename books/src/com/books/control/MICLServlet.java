package com.books.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.miCL;
import com.books.model.managerbean;

public class MICLServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		this.doPost(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s_pageNow=request.getParameter("pageNow");
		int pageNow=1;
		if(s_pageNow!=null){
			pageNow=Integer.parseInt(s_pageNow);
		}
		int pageSize=10;
		miCL uCl=new miCL();
		int pageCount=uCl.getPageCount();
		ArrayList<managerbean> list=uCl.getDpage(pageSize, pageNow);
		request.setAttribute("list", list);
		request.setAttribute("pageCount", pageCount+"");
		request.setAttribute("pageNow", pageNow+"");
		request.getRequestDispatcher("managerInfo.jsp").forward(request, response);
		
	}

}
