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
    
    <title>用户登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
	-->
	<link rel="stylesheet" href="css/index.min.css">
	
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
    
	<script language="javascript">
	function myReload() {
		document.getElementById("CreateCheckCode").src = document
				.getElementById("CreateCheckCode").src
				+ "?nocache=" + new Date().getTime();
	}
	</script>

  </head>
  
<body>
  <%
  	String flag = (String)request.getAttribute("flag");
	PrintWriter pw = response.getWriter();
  	if (flag!=null && flag=="userError"){
  		pw.print("<script>alert('用户名错误,请检查！')</script>");
  	}else if(flag!=null && flag=="passError"){
  		pw.print("<script>alert('密码错误,请检查！')</script>");
  	}else if(flag!=null && flag=="Success"){
  		pw.print("<script>alert('注册成功！')</script>");
  	}else if(flag!=null && flag=="codeNull"){
  		pw.print("<script>alert('请输入验证码！')</script>");
  	}else if(flag!=null && flag=="codeError"){
  		pw.print("<script>alert('验证码错误！')</script>");
  	}
  %>
  
    <div class="head-other">
        <a class="logo" style="background:none;">
            <img style="max-width: 200px; max-height: 80px;" class="logo-img"  style="margin-top: 10px; width: 280px; height: 80px;"/>
        </a>
    </div>
    
<!-- 表单 -->
<form action="UserLoginServlet" name="form1" id="form1" method="post">
<input id="returnUrl" name="returnUrl" type="hidden" value="" />	
	<div class="login-wrapper">
		<div class="body">
			<div class="login-container">
				<h1>登录</h1>
				<div class="warn"><a href="Register.jsp">免费注册</a></div>
				<div class="safe-line">
					<input class="txt-lx" id="Username" name="Username" placeholder="用户名" type="text" value="" />
				</div>
				<div class="safe-line">
					<input class="txt-lx" id="Password" name="Password" placeholder="密码" type="password" />
				</div>
				<div class="safe-line">
					<input name="checkCode" type="text" id="checkCode" title="验证码"  
                	size="8" ,maxlength="4" />  
            		<img src="CheckCodeServlet" id="CreateCheckCode" align="middle">  
            		<a href="" onclick="myReload()"> 看不清,换一个</a>
				</div>
				<div class="safe-line">
                    <button class="btn-login">登录</button>
				</div>
			</div>
		</div>
	</div>
</form>


    <div class="footer footer-other">
        <div class="menu">
            <a href="AdminLogin.jsp">网站后台管理</a>
        </div>
    </div>
    <input type="hidden" value="" id="hidAppName"/>
    <script src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	    $(".btn-login").click(function () {
	        if ($("#Username").val() == "") {
	            $(".warn").text("用户名不能为空");
	            return false;
	        }
	        if ($("#Password").val() == "") {
	            $(".warn").text("密码不能为空");
	            return false;
	        }
	        $("#form1").submit();
	    });
	</script>



</body>
</html>
