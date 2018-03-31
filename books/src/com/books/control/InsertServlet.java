package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.Tools;
import com.books.model.inCL;

public class InsertServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String pageNow = request.getParameter("pageNow");
		String cISBN=request.getParameter("cISBN");
		String cName=request.getParameter("cName");
		String cAuthor=request.getParameter("cAuthor");
		String cPress=request.getParameter("cPress");
		String cShelt=request.getParameter("cShelt");
		String cKind=request.getParameter("cKind");
		String cAc=request.getParameter("cAc");
		String cAv=request.getParameter("cAv");
		cName = Tools.getCovertCode(cName);
		cAuthor = Tools.getCovertCode(cAuthor);
		cPress = Tools.getCovertCode(cPress);
		cKind = Tools.getCovertCode(cKind);

		inCL ic=new inCL();
		boolean bool=ic.insert(cISBN, cName, cAuthor, cPress, cShelt, cKind, cAc, cAv);
		if (bool){
			request.setAttribute("flag", "insertSuccess");
			request.setAttribute("pageNow", pageNow+"");
			request.getRequestDispatcher("Success.jsp").forward(request, response);
		}else {
			request.setAttribute("flag", "insertFailure");
			request.setAttribute("pageNow", pageNow+"");
			request.getRequestDispatcher("Error.jsp").forward(request, response);
		}
	}
}
