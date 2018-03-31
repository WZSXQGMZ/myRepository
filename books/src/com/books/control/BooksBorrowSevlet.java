package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.BooksBorrowHandle;

public class BooksBorrowSevlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//doPost solve all
		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String s_usrid = request.getParameter("userid");
		String isbn = request.getParameter("isbn");
		String queryRange = request.getParameter("queryRange");
		String booksQuery = request.getParameter("booksQuery");
		String s_pageNow = request.getParameter("pageNow");
		
		BooksBorrowHandle bbh = new BooksBorrowHandle();
		String borrowResult = bbh.borrowBook(s_usrid, isbn);
		if(borrowResult == null) {
			request.setAttribute("booksQuery", booksQuery);
			request.setAttribute("queryRange", queryRange);
			request.getRequestDispatcher("BooksQueryServlet?&userid=" + s_usrid + "&queryRange=" + queryRange 
					+ "&booksQuery=" + booksQuery + "&pageNow=" + s_pageNow).forward(request, response);
		} else {
			request.setAttribute("booksQuery", booksQuery);
			request.setAttribute("queryRange", queryRange);
			request.getRequestDispatcher("BooksQueryServlet?&userid=" + s_usrid + "&queryRange=" + queryRange 
					+ "&booksQuery=" + booksQuery + "&pageNow=" + s_pageNow + "&borrowResult=" + borrowResult).forward(request, response);
		}
	}

}
