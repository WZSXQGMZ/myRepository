
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
    
    <title>My JSP 'AdminPassModify.jsp' starting page</title>
    
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
    	if(flag.equals("modifyPass")) {
    		
    	} else if(flag.equals("modifyPassReturn")) {
    		String modifyResult = request.getParameter("modifyResult");
    		if(modifyResult.equals("0")) {
    			pw.print("<script>alert('修改成功')</script>");
    		} else {
    			pw.print("<script>alert('修改失败')</script>");
    		}
    	}
    	String s_adminid = request.getParameter("adminid");
    %>

    <form name="form_adminPass" action="AdminInfoModifyServlet?flag=modifyPass&adminid=<%=s_adminid %>" method="post">
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
