<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>管理员登录</title>
<%
	String path = request.getContextPath();
%>
<!-- BOOTSTRAP STYLES-->
<link href="<%=path%>/statics/css/bootstrap.css" rel="stylesheet" />
<!-- FONTAWESOME STYLES-->
<link href="<%=path%>/statics/css/font-awesome.css" rel="stylesheet" />
<!-- GOOGLE FONTS-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css' />

</head>
<body style="background-color: #E2E2E2;">
	<div class="container">
		<div class="row text-center " style="padding-top: 100px;">
			<div class="col-md-12">
				<img src="<%=path%>/statics/img/logo.png" align="middle" />
			</div>
		</div>
		<div class="row ">

			<div
				class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">

				<div class="panel-body">
					
						<hr />
						<h5>管理员你好！请输入登录信息：</h5>
						<br />
						<form action="Mlogin2" name="loginForm" id="loginForm"
							method="post">
							<span style="color: red;font-size:15px;">${error} </span>
							<div class="form-group input-group">
								<span class="input-group-addon"><i class="fa fa-tag"></i></span>
								
								<input type="text" class="form-control" placeholder="你的账号 "
									name="managerName" />
							</div>
							<div class="form-group input-group">
								<span class="input-group-addon"><i class="fa fa-lock"></i></span>
								<input type="password" class="form-control" placeholder="你的密码"
									name="managerPassword" />
							</div>

							<div class="form-group">
								<label class="checkbox-inline"> <input type="checkbox" />
									记住我
								</label>

							</div>
							<input type="submit" class="btn btn-primary " value="登录" />
							<input type="reset" class="btn btn-primary " value="重置"/>
							
							<hr />
						</form>
					
				</div>

			</div>


		</div>
	</div>

</body>
</html>

