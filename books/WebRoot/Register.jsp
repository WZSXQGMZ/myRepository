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
    
    <title>用户注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="css/index.min.css">
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
	
	<style>
		.get-vcode {
		  display: inline-block;
		  position: absolute;
		  top: 0;
		  right: 8px;
		  font-size: 12px;
		  color: #fff;
		  margin-top: 10px;
		  padding: 0 6px;
		  text-align: center;
		  -moz-border-radius: 3px;
		  border-radius: 3px;
		  height: 24px;
		  line-height: 24px;
		  z-index: 9;
		}
	</style>

  </head>
  
  <body>  
  <%
  	String flag = (String)request.getAttribute("flag");
	PrintWriter pw = response.getWriter();
  	if (flag!=null && flag=="Failure"){
  		pw.print("<script>alert('注册失败！请检查个人信息！')</script>");
  	}
  %>
    <!-- 注册 begin -->
<div class="reigster-wrapper">
    <div class="body">
        <div class="title">
            <span>已有账号了？<a href="Login.jsp">登录</a></span>
          	<h1><a>用户注册</a></h1>
        </div>
        <div class="reigster-container">
			<form action="UserRegisterServlet?flag=<%=flag%> "id="form1" class="J_FormValidate" method="post">
			<div class="safe-line">
				<div class="safe-line">
                    <div class="label">账号：</div>
                    <div class="txt">
                        <input class="txt-lx" id="Username" name="Username" placeholder="此项必填" type="text" value="" />
                    </div>
                </div>
                <div class="safe-line">
                    <div class="label">密码：</div>
                    <div class="txt">
                        <input class="txt-lx required" placeholder="此项必填" id="Password" name="Password" type="password" />
                    </div>
                </div>
                <div class="safe-line">
                    <div class="label">确认密码：</div>
                    <div class="txt">
                        <input class="txt-lx required" placeholder="此项必填" id="RePassword" name="RePassword" type="password" />
                    </div>
                </div>
                <div class="safe-line">
                    <div class="label">姓名：</div>
                    <div class="txt">
                        <input class="txt-lx" id="TrueName" name="TrueName" placeholder="此项必填" type="text" value="" />
                    </div>
                </div>
				<div class="safe-line">
                    <div class="label">性别：</div>
                    <div class="txt">
                        <input class="txt-lx" placeholder="此项必填" id="Sex" name="Sex" type="text" value="" />
                    </div>
                </div>
                <div class="safe-line">
                    <div class="label">年龄：</div>
                    <div class="txt">
                        <input class="txt-lx" id="Age" name="Age" type="text" value="" />
                    </div>
                </div>
                <div class="safe-line">
                    <div class="label">职业：</div>
                    <div class="txt">
                        <input class="txt-lx" id="Vocation" name="Vocation" type="text" value="" />
                    </div>
                </div>
                <div class="safe-line">
                    <div class="label">联系电话：</div>
                    <div class="txt">
                        <input class="txt-lx" placeholder="此项必填" id="Tel" name="Tel" type="text" value="" />
                    </div>
                </div>
        		<div class="safe-line">
                    <div class="label">邮箱：</div>
                    <div class="txt">
                        <input class="txt-lx email" placeholder="此项必填" data-val="true" data-val-length="Email不能超过100个字符！" data-val-length-max="100" id="Email" name="Email" type="text" value="" />
                    </div>
                </div>
                <div class="safe-line">
                    <div class="label">住址：</div>
                    <div class="txt">
                        <input class="txt-lx" placeholder="此项必填" id="Address" name="Address" type="text" value="" />
                    </div>
                </div>
                <div class="safe-line">
                  <div class="safe-line">
                    <div class="label"></div>
                    <div class="txt">
                      <button class="btn-login">提交</button>
                    </div>
                    <div class="warn"></div>
                  </div>
                </div>
</form>        
</div>
    </div>
</div>
    
	<div class="footer footer-other">
        <div class="menu">
            <a href="AdminLogin.jsp">网站后台管理</a>
        </div>
    </div>
    
    <script src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	    $(".btn-login").click(function () {
	        if ($("#Username").val() == "") {
	            $(".warn").text("用户名不能为空！");
	            return false;
	        }
	        if ($("#Password").val() == "") {
	            $(".warn").text("密码不能为空！");
	            return false;
	        }
	        if ($("#Password").val() != $("#RePassword").val()) {
	            $(".warn").text("两次输入密码不同！");
	            return false;
	        }
	        if ($("#TrueName").val() == "") {
	            $(".warn").text("姓名不能为空！");
	            return false;
	        }
	        if ($("#Sex").val() == "") {
	            $(".warn").text("性别不能为空！");
	            return false;
	        }
	        if ($("#Tel").val() == "") {
	            $(".warn").text("联系电话不能为空！");
	            return false;
	        }
	        if ($("#Email").val() == "") {
	            $(".warn").text("邮箱不能为空！");
	            return false;
	        }
	        if ($("#Address").val() == "") {
	            $(".warn").text("住址不能为空！");
	            return false;
	        }
	        $("#form1").submit();
	    });
	</script>
  </body>
</html>
