package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.books.model.PersonalUserInfoHandle;

public class PersonalUserPasswordModifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//post solve all
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usrid = request.getParameter("userid");
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		
		PersonalUserInfoHandle puih = new PersonalUserInfoHandle();
		int modifyResult = puih.setUserPassword(usrid, oldPass, newPass);
		if(modifyResult == 0){
			request.getRequestDispatcher("PersonalUserInfoServlet?modifyState=0&flag=getInfo&userid=" + usrid).forward(request, response);
		} else if(modifyResult == 1){
			request.getRequestDispatcher("PersonalUserPasswordModify.jsp?modifyState=1&userid=" + usrid).forward(request, response);
		} else if(modifyResult == 2){
			request.getRequestDispatcher("PersonalUserPasswordModify.jsp?modifyState=2&userid=" + usrid).forward(request, response);
		}
		
	}

}
