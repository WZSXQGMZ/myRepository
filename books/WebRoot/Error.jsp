<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Error.jsp' starting page</title>
    
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
		background:#fff;
	}
	</style>

  </head>
  
  <body>
    <%
	  	String flag=(String)request.getAttribute("flag");
    	String d_pageNow=(String)request.getAttribute("pageNow");
	  	if(flag!=null && flag.equals("timeout")){
	%>
	   	<div class="div1">	
		时间已过，无法续借！稍后系统将自动返回！
		</div>
	<%
		response.setHeader("Refresh", "2;URL='BookReturnServlet?flag=message'");
	  	}
	  	if(flag!=null && flag.equals("delFailure")){
	%>
		<div class="div1">	
		删除失败！稍后系统将自动返回！		
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow="+d_pageNow);
			}else{
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow=1'");
			}
		}
	  	if(flag!=null && flag.equals("modifyFailure")){
	%>
		<div class="div1">	
		修改失败！稍后系统将自动返回！		
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow="+d_pageNow);
			}else{
				response.setHeader("Refresh", "2;URL='UserClServlet?pageNow=1'");
			}
		}
	  	if(flag!=null && flag.equals("bookModifyFailure")){
	%>
		<div class="div1">	
		修改失败！稍后系统将自动返回！		
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow="+d_pageNow);
			}else{
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow=1'");
			}
		}
	  	if(flag!=null && flag.equals("insertFailure")){
	%>
		<div class="div1">	
		修改失败！稍后系统将自动返回！		
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow="+d_pageNow);
			}else{
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow=1'");
			}
	  	}
	  	if(flag!=null && flag.equals("delFailure")){
	%>
		<div class="div1">	
		增加失败！稍后系统将自动返回！		
		</div>
	<%
			if(d_pageNow!=null){
				System.out.print(d_pageNow);
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow="+d_pageNow);
			}else{
				response.setHeader("Refresh", "2;URL='BICLServlet?pageNow=1'");
			}
	  	}
	%>
  </body>
</html>
