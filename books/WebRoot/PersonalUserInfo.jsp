<%@page import="java.io.PrintWriter"%>
<%@page import="com.books.model.UserBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'PersonalUserInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

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
     HttpSession hs = request.getSession();		
	 String usrid = request.getParameter("userid");
	 String userBeanState = (String)request.getAttribute("userBeanState");
	 //String modifyState = (String)request.getParameter("modifyState");
     if((userBeanState != null) && (userBeanState.equals("getsuccess") || userBeanState.equals("0"))){
     	UserBean ub = (UserBean)request.getAttribute("UserBean");
     	if(userBeanState.equals("0")){
     		pw.print("<script>alert('修改成功')</script>");
     	}
    %>    
    
    <ul class="userInfo">
    	<li>姓名&nbsp;&nbsp;&nbsp;&nbsp;<%=(String)ub.getUsrname() %></li>
    	<li>性别&nbsp;&nbsp;&nbsp;&nbsp;<%=(String)ub.getUsrsex() %></li>
    	<li>年龄&nbsp;&nbsp;&nbsp;&nbsp;<%=String.valueOf(ub.getUsrage()) %></li>
    	<li>职业&nbsp;&nbsp;&nbsp;&nbsp;<%=(String)ub.getVocation() %></li>
    	<li>手机&nbsp;&nbsp;&nbsp;&nbsp;<%=(String)ub.getUsrtel() %></li>
    	<li>邮箱&nbsp;&nbsp;&nbsp;&nbsp;<%=(String)ub.getEmail() %></li>
    	<li>地址&nbsp;&nbsp;&nbsp;&nbsp;<%=(String)ub.getAddress() %></li>
    	<li>信誉&nbsp;&nbsp;&nbsp;&nbsp;<%=String.valueOf(ub.getUsrcredit()) %></li>
    </ul>
    <a href="PersonalUserInfoServlet?flag=modify&userid=<%=usrid %>"><input type="button" value="修改信息" /></a>
    <a href="PersonalUserInfoServlet?flag=passModify&userid=<%=usrid %>"><input type="button" value="修改密码" /></a>
    <%} else if((String)request.getAttribute("userBeanState") == "getfail"){
    	
    	pw.print("<script>alert('获取信息失败')</script>");
    } else {
    	pw.print("<script>alert('修改信息失败')</script>");
    }
    
    %>
    
  </body>
</html>
