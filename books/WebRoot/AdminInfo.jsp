<%@page import="com.books.model.AdminBean"%>
<%@page import="javax.swing.RepaintManager"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'AdminInfo.jsp' starting page</title>
    
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
    	String flag = request.getParameter("flag");
    	String s_adminid = request.getParameter("adminid");
    	if(flag.equals("modifyInfo")) {
    		String modifyResult = request.getParameter("modifyResult");
    		if(modifyResult.equals("0")) {
    			pw.print("<script>alert('修改成功')</script>");
    		}
    	} else if(flag.equals("modifyPass")) {
    		String modifyResult = request.getParameter("modifyResult");
    		if(modifyResult.equals("0")) {
    			pw.print("<script>alert('密码修改成功')</script>");
    		}
    	}
    	AdminBean adminBean = (AdminBean)request.getAttribute("adminBean");
    	
    %>
    
    <ul>
    	<li>姓名&nbsp;&nbsp;<%=adminBean.getAdminName() %></li>
    	<li>电话&nbsp;&nbsp;<%=adminBean.getAdminTel() %></li>
    	<li>邮箱&nbsp;&nbsp;<%=adminBean.getAdminEmail() %></li>
    	<a href="AdminInfoServlet?flag=modifyInfo&adminid=<%=s_adminid %>" ><input type="button" value="修改信息" /></a>
    	<a href="AdminPassModify.jsp?flag=modifyPass&adminid=<%=s_adminid %>" ><input type="button" value="修改密码" /></a>
    </ul>
  </body>
</html>
