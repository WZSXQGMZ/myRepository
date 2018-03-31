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
    
    <title>My JSP 'UserInfoModify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" type="text/css" href="css/styles.css">
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
    
	<script type="text/javascript" src="javascript/userInfoModifyCheck.js"></script>

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
		int usrid = Integer.parseInt((String)request.getParameter("userid"));
    	String modifyState = (String)request.getParameter("modifyState");
    	UserBean ub = (UserBean)request.getAttribute("UserBean");
    	if(ub != null) {
			pw.print("<script>alert('ub!=null')");
			
		} else {
			pw.print("<script>alert('请求错误')");
		}
    	if(modifyState != null && modifyState.equals("4")){
    		
    		pw.print("<script>alert('手机号已存在');form_userinfo.usrtel.focus();</script>");
    	} else if(modifyState != null && modifyState.equals("0")){    		
    		
    	} else {
    		//pw.print("<script>alert('请求错误1')");
      	}
    	
    	
    %>
    <form action="PersonalUserInfoModifyServlet?userid=<%=String.valueOf(usrid) %>" name="form_userinfo">
    	<ul>
	    	<li>姓名&nbsp;&nbsp;&nbsp;&nbsp;<input name="usrname" type="text" value=<%=(String)ub.getUsrname() %> /></li>
	    	<li>性别&nbsp;&nbsp;&nbsp;&nbsp;<input name="usrsex" type="text" value=<%=(String)ub.getUsrsex() %> /></li>
	    	<li>年龄&nbsp;&nbsp;&nbsp;&nbsp;<input name="usrage" type="text" value=<%=String.valueOf(ub.getUsrage()) %> /></li>
	    	<li>职业&nbsp;&nbsp;&nbsp;&nbsp;<input name="vocation" type="text" value=<%=(String)ub.getVocation() %> /></li>
	    	<li>电话&nbsp;&nbsp;&nbsp;&nbsp;<input name="usrtel" type="text" value=<%=(String)ub.getUsrtel() %> /></li>
	    	<li>邮箱&nbsp;&nbsp;&nbsp;&nbsp;<input name="email" type="text" value=<%=(String)ub.getEmail() %> /></li>
	    	<li>地址&nbsp;&nbsp;&nbsp;&nbsp;<input name="address" type="text" value=<%=(String)ub.getAddress() %> /></li>
    	</ul>
    	<input type="submit" value="提交" onclick="return infoCheck(this)" />
    </form>

  </body>
  
</html>
