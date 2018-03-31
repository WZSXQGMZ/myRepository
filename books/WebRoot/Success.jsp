<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Success.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="target" content="right">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
    
	<style type="text/css">
	body{
		background:#fff;
	}
	</style>
  </head>
  
  <body>
  	<%
	  	String flag=(String)request.getAttribute("flag");
  		String d_pageNow=(String)request.getAttribute("pageNow");
	  	if(flag!=null && flag.equals("timein")){
	%>
	   	<div class="div1">	
		续借成功！稍后系统自动返回主界面！		
		</div>
	<%
		response.setHeader("Refresh", "2;URL='BookReturnServlet?flag=hasborrow'");
	  	}
	  	if(flag!=null && flag.equals("returnSuccess")){
	%>
		<div class="div1">	
		还书成功！稍后系统自动返回主界面！		
		</div>
	<%
		response.setHeader("Refresh", "2;URL='BookReturnServlet?flag=allborrow'");
	  	}
	  	if(flag!=null && flag.equals("delSuccess")){
	%>
		<div class="div1">	
		删除成功！稍后系统自动返回！
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow="+d_pageNow);
			}else
			{
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow=1'");
			}
	  	}
		if(flag!=null && flag.equals("modifySuccess")){
  	%>
  		<div class="div1">	
		修改成功！稍后系统自动返回！
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow="+d_pageNow);
			}else
			{
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow=1'");
			}
	  	}
		if(flag!=null && flag.equals("bookModifySuccess")){
  	%>
  		<div class="div1">	
		修改成功！稍后系统自动返回！
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow="+d_pageNow);
			}else
			{
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow=1'");
			}
	  	}
		if(flag!=null && flag.equals("insertSuccess")){
  	%>
  		<div class="div1">	
		添加成功！稍后系统自动返回！
		</div>
  	<%
		  	if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow="+d_pageNow);
			}else
			{
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow=1'");
			}
		}
		if(flag!=null && flag.equals("deleteSuccess")){
  	%>
  		<div class="div1">	
		删除成功！稍后系统自动返回！
		</div>
  	<%
		  	if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow="+d_pageNow);
			}else
			{
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow=1'");
			}
		}
  	%>
  </body>
</html>
