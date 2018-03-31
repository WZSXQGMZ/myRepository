package com.books.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.BookReturn;
import com.books.model.BookReturnCl;

public class BookReturnServlet extends HttpServlet {


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
		
		//显示历史借阅信息
		if (flag!=null && flag.equals("allborrow")){
			String userid = (String)request.getSession().getAttribute("userid");
			BookReturnCl brc = new BookReturnCl();
			ArrayList<BookReturn> list = brc.getAllBorrow(userid);
			request.setAttribute("list", list);
			request.getRequestDispatcher("BookReturn.jsp").forward(request, response);
		}
		//显示当前借阅信息
		if (flag!=null && flag.equals("hasborrow")){
			String userid = (String)request.getSession().getAttribute("userid");
			BookReturnCl brc = new BookReturnCl();
			ArrayList<BookReturn> list = brc.getHasBorrow(userid);
			request.setAttribute("list", list);
			request.getRequestDispatcher("BookReturn.jsp").forward(request, response);
		}
		
	}

}
