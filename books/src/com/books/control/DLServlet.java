package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.deCL;;

public class DLServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
		this.doPost(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageNow=1;
		String s_pageNow=request.getParameter("pageNow");
		if(s_pageNow!=null){
			pageNow=Integer.parseInt(s_pageNow);
		}
			String ISBN=request.getParameter("ISBN");
			if(ISBN!=null){
				deCL uCl=new deCL();
				if(uCl.delete(ISBN)){
					//删除成功
					request.setAttribute("pageNow", pageNow+"");
					request.setAttribute("flag", "delSuccess");
					request.getRequestDispatcher("Success.jsp").forward(request, response);
				}else {
					//删除失败
					request.setAttribute("pageNow", pageNow+"");
					request.setAttribute("flag", "deleteFailure");
					request.getRequestDispatcher("Error.jsp").forward(request, response);
				}
			}

	}
}
