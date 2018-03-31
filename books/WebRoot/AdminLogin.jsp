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
    
    <title>管理员登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="css/css.css" type="text/css">

  </head>
  
<body bgcolor="#FFFFFF" text="#000000">

<%
  	String flag = (String)request.getAttribute("flag");
	PrintWriter pw = response.getWriter();
  	if (flag!=null && flag=="userError"){
  		pw.print("<script>alert('用户名错误,请检查！')</script>");
  	}else if(flag!=null && flag=="passError"){
  		pw.print("<script>alert('密码错误,请检查！')</script>");
  	}
%>
  
<br>
<table align=center border=0 cellpadding=0 cellspacing=0 width=575>
  <tbody> 
  <tr> 
    <td><img height=100 src="images/index_top.gif" width=575></td>
  </tr>
  <tr valign=top> 
    <td> 
      <table background="images/index_bg.gif" border=0 cellpadding=0 cellspacing=2 
      height=147 width=575>
        <tbody> 
        <tr>
          <td height=26 width=73>&nbsp;</td>
          <td height=26 width=496>&nbsp;</td>
        </tr>
        <tr> 
          <td height=57 width=73>&nbsp;</td>
          <td height=57 width=496> 
          	<form name="form1" method="post" action="ManagerLoginServlet">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                  <td width="41%"> 
                    用户名：
                      <input type="text" id="Managername" name="Managername" class="input1" size="16">
                      </td>
                  <td width="38%"> 
                    密码：
                      <input type="password" id="Password" name="Password" class="input1" size="16">
                </td>
                  <td width="21%"> 
                    <input type="image" class="btn-login" border="0" name="imageField" src="images/Login.gif" width="72" height="22">
                  </td>
              </tr>
              </table>
            </form>
            <div class="warn"></div>
          </td>
        </tr>
        <tr> 
          <td width=73>&nbsp;</td>
          <td width=496>&nbsp;</td>
        </tr>
        </tbody>
      </table>
    </td>
  </tr>
  <tr> 
    <td><img height=130 src="images/index_mid.gif" width=575></td>
  </tr>
  </tbody>
</table>
	<script src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	    $(".btn-login").click(function () {
	        if ($("#Managername").val() == "") {
	            $(".warn").text("用户名不能为空！");
	            return false;
	        }
	        if ($("#Password").val() == "") {
	            $(".warn").text("密码不能为空！");
	            return false;
	        }
	        $("#form1").submit();
	    });
	</script>
</body>
</html>