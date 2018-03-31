<%@page import="com.books.model.AdminBean"%>
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
    
    <title>My JSP 'AdminInfoModify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">

	<style type="text/css">
	input{
		text-align:center;
	}
	body {
		background:#FFF
	}
	</style>
  </head>
  
  <body>
    <%
    	PrintWriter pw = response.getWriter();
    	String flag = request.getParameter("flag");
    	
    	if(flag.equals("modifyInfo")) {
    		
    	} else if(flag.equals("modifyInfoReturn")) {
    		String modifyResult = request.getParameter("modifyResult");
    		if(modifyResult.equals("0")) {
    			pw.print("<script>alert('修改成功')</script>");
    		} else if(modifyResult.equals("4")) {
    			pw.print("<script>alert('手机号已存在')</script>");
    		} else {
    			pw.print("<script>alert('修改失败')</script>");
    		}
    	}
    	
    	String s_adminid = request.getParameter("adminid");
    	AdminBean adminBean = (AdminBean)request.getAttribute("adminBean");
    %>
    <form name="form_adminInfo" action="AdminInfoModifyServlet?flag=modifyInfo&adminid=<%=s_adminid %>" method="post">
    	姓名<input name="adminName" type="text" value=<%=adminBean.getAdminName() %> />
    	电话<input name="adminTel" type="text" value=<%=adminBean.getAdminTel() %> />
    	邮箱<input name="adminEmail" type="text" value=<%=adminBean.getAdminEmail() %> />
    	<input name="submitButton" type="submit" value="提交" />
    </form>
  </body>
</html>
