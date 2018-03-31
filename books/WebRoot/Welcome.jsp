<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Welcome.jsp' starting page</title>
    
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
	body{
		background-color:#fff;
		text-align:center;
		margin:0 auto;
	}
	img{
		margin-top:50px;
	}

	</style>

  </head>
  
  <body>
    <%
	  	String flag=(String)request.getAttribute("flag");
	  	if(flag!=null && flag.equals("timeout")){
	  		out.println("<script>alert('时间已过，无法续借！')</script>");
	  	}
	  	if(flag!=null && flag.equals("timein")){
	  		out.println("<script>alert('续借成功！')</script>");
	  	}
	  	if(flag!=null && flag.equals("returnSuccess")){
	  		out.println("<script>alert('还书成功！')</script>");
	  	}
  	%>
    <img src="images/Welcome.jpg" width="1200" height="651" />
  </body>
</html>
