<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>川大资料网</title>
    <%  String path = request.getContextPath(); %>
<!-- BOOTSTRAP STYLES-->
<link href="<%=path%>/statics/css/bootstrap.css" rel="stylesheet" />
<!-- FONTAWESOME STYLES-->
<link href="<%=path%>/statics/css/font-awesome.css" rel="stylesheet" />
<!--CUSTOM BASIC STYLES-->
<link href="<%=path%>/statics/css/basic.css" rel="stylesheet" />
<!--CUSTOM MAIN STYLES-->
<link href="<%=path%>/statics/css/custom.css" rel="stylesheet" />
<link href="<%=path%>/statics/css/bootstrap-fileupload.min.css" rel="stylesheet">
<!-- GOOGLE FONTS-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
<div id="wrapper">
  <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
    <div class="navbar-header" >
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <a class="navbar-brand" href="index.html" style="font-size: 40px;">川大资料网</a> </div>
    <div class="header-right"> <a href="#" onclick="exit()" class="btn btn-danger" title="退出"><i class="fa fa-exclamation-circle fa-2x"></i></a> </div>
  </nav>
  <!-- /. NAV TOP  -->
  <nav class="navbar-default navbar-side" role="navigation">
    <div class="sidebar-collapse">
      <ul class="nav" id="main-menu">
        <li>
          <div class="user-img-div"> <img src="<%=path%>/statics/img/user.png" class="img-thumbnail" />

            <div class="inner-text"> <font size="5">管理员：</font>${managerSession.managerName } <br />
			<a href="<%=path%>/MInfoModify" style="color: black;">修改个人信息</a> </div>
          </div>
        </li>
        <li> <a href="<%=path%>/main"><i></i>资料查询</a> </li>
        <li> <a href="#" ><i ></i>资料审核 <span class="fa arrow"></span></a>
          <ul class="nav nav-second-level">
            <li> <a  href="<%=path%>/MCheckHistory" ><i ></i>审核历史</a> </li>
            <li> <a href="<%=path%>/MDataCheck"><i ></i>资料审核</a> </li>
          </ul>
        </li>
        <li> <a href="<%=path%>/MmanagerQuery"><i ></i>管理员查询</a> </li>
        <li> <a  href="<%=path%>/MEnterUserQuery"><i ></i>用户查询 </a> </li>
      </ul>
    </div>
  </nav>
  <!-- /. NAV SIDE  -->
  <div id="page-wrapper">
    <div id="page-inner">
      <div class="row">
        <div class="col-md-12">
          <h1 class="page-head-line">管理员密码修改</h1>
          <h1 class="page-subhead-line">改密！改密！改密！</h1>
        </div>
      </div>
      </br>
      
      <div class="col-md-6 col-sm-6 col-xs-12">
               		<div class="panel panel-danger">
                        <div class="panel-heading">
                           	修改密码
                        </div>
                        <div class="panel-body">

                           
                                 <form role="form" action="MOldpassword" onsubmit="return checkPwd(this)">             
                                <div class="form-group">
                                    <label>原密码</label>
                                    <input class="form-control" type="password" name="oldpwd">
                                    <span style="color:red ;">${ConError}</span>
                                </div>        
                                <div class="form-group">
                                    <label>新密码（x-xx位）</label>
                                    <input class="form-control" type="password" name="newpwd" onKeyUp="pwStrength(this.value)" onBlur="pwStrength(this.value)">
                                    
									<table width="210" border="0" cellspacing="0" cellpadding="1" bordercolor="#eeeeee" height="22" style='display:inline'> 
									<tr align="center" bgcolor="#f5f5f5"> 
									<td width="56%" bgcolor="white">密码强度：</td>
									<td width="14%" id="strength_L">弱</td> 
									<td width="14%" id="strength_M">中</td> 
									<td width="14%" id="strength_H">强</td> 
									</tr> 
									</table>
                                </div>        
                                <div class="form-group">
                                    <label>确认新密码</label>
                                    <input class="form-control" type="password" name="newpwdcof">
                                </div>
                                 
                                <input type="submit" class="btn btn-danger" value="修改密码" name=/>
								<a href="userInfoPage" class="btn btn-danger ">返回</a>
								<span style="color: red;">${ModifySuccess}</span>
                            	</form>

                             

                        </div>
                    </div>
                </div>
      
    </div>
  </div>
  <!-- /. PAGE WRAPPER  --> 
</div>
<!-- /. WRAPPER  -->

<div id="footer-sec"> 川大资料网 </div>
<!-- /. FOOTER  --> 
<!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME--> 
<!-- JQUERY SCRIPTS --> 
<script src="<%=path%>/statics/js/jquery-1.10.2.js"></script> 
<!-- BOOTSTRAP SCRIPTS --> 
<script src="<%=path%>/statics/js/bootstrap.js"></script> 
<!-- METISMENU SCRIPTS --> 
<script src="<%=path%>/statics/js/jquery.metisMenu.js"></script> 
<!-- CUSTOM SCRIPTS --> 
<script src="<%=path%>/statics/js/custom.js"></script> 
<script src="<%=path%>/statics/js/check-password.js"></script>
<script src="<%=path%>/statics/js/bootstrap-fileupload.js"></script>
<script src="<%=path%>/statics/js/ManagerOperation.js"></script>
</body>
</html>
<% out.flush();%>