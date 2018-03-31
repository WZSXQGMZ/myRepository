package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.deCL;

public class DeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String delete=request.getParameter("delete");
		delete=new String(delete.getBytes("ISO-8859-1"), "utf-8");
		deCL dc=new deCL();
		boolean bool=dc.delete(delete);
		if (bool){
			request.getRequestDispatcher("Welcome.jsp").forward(request, response);
		}else {
			request.getRequestDispatcher("delete.jsp").forward(request, response);
		}
	}
}
