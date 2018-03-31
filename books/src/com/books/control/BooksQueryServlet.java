package com.books.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.books.model.bookbean;
import com.books.model.BooksQuery;
import com.books.model.Tools;

public class BooksQueryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private int pageSize = 9;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doPost solve all
		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String booksQuery = request.getParameter("booksQuery");
		String booksQuery_utf8 = Tools.getCovertCode(booksQuery);
		String queryRange = request.getParameter("queryRange");
		HttpSession hs = request.getSession();
		String usrid = (String)hs.getAttribute("userid");
		int pageNow = Integer.parseInt(request.getParameter("pageNow"));
		int pageCount = 0;
		
		BooksQuery bq = new BooksQuery();
		//获取查询书籍总数
		int booksCount = bq.getBooksCount(booksQuery_utf8, queryRange);
		//计算总页数
		if(booksCount%pageSize == 0) {
			pageCount = booksCount/pageSize;
		} else {
			pageCount = booksCount/pageSize + 1;
		}
		if(pageNow > pageCount) {
			pageNow = pageCount;
		}
		if(pageNow < 1) {
			pageNow = 1;
		}
		//查询对应页的书籍
		ArrayList<bookbean> bookList = bq.booksQuery(booksQuery_utf8, queryRange, pageNow, pageSize);
		//booksQuery = new String(booksQuery.getBytes("utf-8"), "ISO-8859-1");
		if(bookList == null) {
			request.setAttribute("bookList", null);
			request.setAttribute("booksQuery", booksQuery_utf8);
			request.setAttribute("queryRange", queryRange);
			request.getRequestDispatcher("booksQuery.jsp?queryResult=none&userid=" + usrid + "&pageNow=1&pageCount=" + pageCount + "&booksQuery=" + booksQuery_utf8).forward(request, response);
		} else {
			request.setAttribute("bookList", bookList);
			request.setAttribute("booksQuery", booksQuery_utf8);
			request.setAttribute("queryRange", queryRange);
			request.getRequestDispatcher("booksQuery.jsp?queryResult=success&userid=" + usrid + "&pageNow=" + pageNow + "&pageCount=" + pageCount + "&booksQuery=" + booksQuery_utf8).forward(request, response);
		}
	}

}
