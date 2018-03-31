package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.Return;
import com.books.model.Tools;
import com.books.model.Reborrow;

public class BorrowReturnServlet extends HttpServlet {


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
		String usrid = request.getParameter("usrid");
		String borrowid = request.getParameter("borrowid");
		String bookid = request.getParameter("bookid");
		String ISBN = request.getParameter("ISBN");
		//应当归还的日期
		String shouldreturn = request.getParameter("shouldreturn");
		//当前日期
		String dateNow = Tools.getDateNow();
		//执行续借操作
		if (flag!=null && flag.equals("reborrow")){
			if (Tools.compare_date(shouldreturn, dateNow)==2){
				//时间已过，无法续借
				request.setAttribute("flag", "timeout");
				request.getRequestDispatcher("Error.jsp").forward(request, response);
			} else{
				//时间未过，可以续借
				Reborrow rb = new Reborrow();
				if (rb.reborrow(borrowid,shouldreturn)){
					request.setAttribute("flag", "timein");
				}
			}
			request.getRequestDispatcher("Success.jsp").forward(request, response);
		}
		
		//执行还书操作
		if (flag!=null && flag.equals("return")){
			int fine = 0;
			Return r = new Return();
			if (Tools.compare_date(shouldreturn, dateNow)==2){
				//时间已过
				fine = 100;
			}
			r.returnbook(borrowid, usrid, bookid, ISBN, dateNow, fine);
			request.setAttribute("flag", "returnSuccess");
			request.getRequestDispatcher("Success.jsp").forward(request, response);
		}
	}

}
