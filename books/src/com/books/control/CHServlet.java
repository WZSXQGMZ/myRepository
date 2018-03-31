package com.books.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.chCL;

public class CHServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String pageNow = request.getParameter("pageNow");
			String ccISBN=request.getParameter("ccISBN");
			ccISBN=new String(ccISBN.getBytes("ISO-8859-1"), "utf-8");
			String ccBookname=request.getParameter("ccBookname");
			ccBookname=new String(ccBookname.getBytes("ISO-8859-1"), "utf-8");
			String ccAuthor=request.getParameter("ccAuthor");
			ccAuthor=new String(ccAuthor.getBytes("ISO-8859-1"), "utf-8");
			String ccPress=request.getParameter("ccPress");
			ccPress=new String(ccPress.getBytes("ISO-8859-1"), "utf-8");
			String ccShelfid=request.getParameter("ccShelfid");
			ccShelfid=new String(ccShelfid.getBytes("ISO-8859-1"), "utf-8");
			String ccBooktype=request.getParameter("ccBooktype");
			ccBooktype=new String(ccBooktype.getBytes("ISO-8859-1"), "utf-8");
			String ccTotal=request.getParameter("ccTotal");
			ccTotal=new String(ccTotal.getBytes("ISO-8859-1"), "utf-8");
			String ccAvaliable=request.getParameter("ccAvaliable");
			ccAvaliable=new String(ccAvaliable.getBytes("ISO-8859-1"), "utf-8");
	
			chCL cCl=new chCL();
			boolean bool=cCl.change(ccISBN, ccBookname, ccAuthor, ccPress, ccShelfid, ccBooktype, ccTotal, ccAvaliable);
			if(bool){
				request.setAttribute("flag", "bookModifySuccess");
				request.setAttribute("pageNow", pageNow);
				request.getRequestDispatcher("Success.jsp").forward(request, response);
			}
			else {
				request.setAttribute("flag", "bookModifyFailure");
				request.setAttribute("pageNow", pageNow);
				request.getRequestDispatcher("Error.jsp").forward(request, response);
			}


	}
}
