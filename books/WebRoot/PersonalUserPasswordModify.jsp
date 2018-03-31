<%@page import="java.io.PrintWriter"%>
<%@ page language="java" import="java.util.*,com.books.control.*,com.books.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'PersonalUserPasswordModify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
	<style type="text/css">
	body {
		background:#FFF
	}
	</style>
  </head>
  
  <body>
    	<%
    	PrintWriter pw = response.getWriter();
    	//HttpSession hs = request.getSession();		
		//int usrid = Integer.parseInt((String)hs.getAttribute("userid"));
		//int usrid = Integer.parseInt((String)request.getParameter("userid"));
		String usrid = request.getParameter("userid");
    	String modifyState = request.getParameter("modifyState");
    	
    	
    %>
    <form action="PersonalUserPasswordModifyServlet?userid=<%=usrid %>" name="form_userpass" method="post">
    	<ul>
	    	<li>旧密码<input id="oldPass" name="oldPass" type="password" /></li>
	    	<li>新密码<input id="newPass" name="newPass" type="password" /></li>
	    	<li>确认新密码<input id="rePass" name="newPassConfirm" type="password" /></li>
	    	<input id="changePass" name="submitButton" type="submit"/>
    	</ul>
    	<div class="warn"></div>
    </form>

  </body>
    <script src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	$("#changePass").click(function (){
		if ($("#newPass").val() != $("#rePass").val()) {
			$(".warn").text("两次输入密码不同！");
            return false;
		}
	});
	</script>
</html>
