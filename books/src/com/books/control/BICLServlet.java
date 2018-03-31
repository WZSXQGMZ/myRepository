package com.books.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.biCL;
import com.books.model.bookbean;

public class BICLServlet extends HttpServlet {

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
		biCL uCl=new biCL();
		int pageCount=uCl.getPageCount();
		ArrayList<bookbean> list=uCl.getDpage(pageSize, pageNow);
		request.setAttribute("list", list);
		request.setAttribute("pageCount", pageCount+"");
		request.setAttribute("pageNow", pageNow+"");
		request.getRequestDispatcher("bookInfo.jsp").forward(request, response);
		
	}

}
