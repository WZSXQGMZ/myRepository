package com.books.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.books.model.UserLoginCl;

public class UserLoginServlet extends HttpServlet {

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
		
		String checkcode=request.getParameter("checkCode");  
        if(checkcode.equals("")||checkcode==null){  
        	request.setAttribute("flag", "codeNull");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }else{  
            if(!checkcode.equalsIgnoreCase((String)request.getSession().getAttribute("randCheckCode"))){  
            	request.setAttribute("flag", "codeError");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            } 
        } 

		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		
		if(username!=null && password!=null){
			//调用模型层来验证用户是否合法
			UserLoginCl ulc = new UserLoginCl();	//实例化对象
			String flag = ulc.checkuser(username, password);
			if (flag!=null && flag=="1"){
				//合法
				String userid = ulc.userid;
				HttpSession hs = request.getSession();

				hs.setAttribute("username", username);
				hs.setAttribute("userid", userid);
				//跳转
				request.getRequestDispatcher("Main.jsp").forward(request, response);
			}else if(flag!=null && flag=="2"){
				//密码不合法 跳转ת
				request.setAttribute("flag", "passError");
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}else if(flag!=null && flag=="3"){
				//用户名不合法  跳转ת
				request.setAttribute("flag", "userError");
				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
		}
	}

}
