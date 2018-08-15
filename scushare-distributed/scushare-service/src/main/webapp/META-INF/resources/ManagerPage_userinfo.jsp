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
<!-- GOOGLE FONTS-->
<link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
<div id="wrapper">
  <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
    <div class="navbar-header" >
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse"> <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <a class="navbar-brand" href="index.html" style="font-size: 40px;">川大资料网</a> </div>
    <div class="header-right"> <a href="login.html" class="btn btn-danger" title="退出"><i class="fa fa-exclamation-circle fa-2x"></i></a> </div>
  </nav>
  <!-- /. NAV TOP  -->
  <nav class="navbar-default navbar-side" role="navigation">
    <div class="sidebar-collapse">
      <ul class="nav" id="main-menu">
        <li>
          <div class="user-img-div"> <img src="<%=path%>/statics/img/user.png" class="img-thumbnail" />
            <div class="inner-text"> <font size="5">管理员：</font>${managerSession.managerName } <br />
              <a href="Manager_modifyPassword.html" style="color: black;">修改个人信息</a> </div>
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
                        <h1 class="page-head-line">个人主页</h1>
                        <h1 class="page-subhead-line">这里你可以查看用户的详细信息 </h1>

                    </div>
                </div>
                <div class="row">
	                <div class="col-md-4" style="width: 100%;">
	                    <div class="panel panel-default">
	                        <!--<div class="panel-heading">
	                        	个人信息
	                        </div>-->
	                        <div class="panel-body">
	                        	<h2>用户ID:  
	                                <small>${userId}</small>
	                            </h2>
	                            <h2>姓名:      
	                                <small>${userName}</small>
	                            </h2>
	                            
	                            <h2>性别:
	                                <small>${userGender}</small>
	                            </h2>
	                            <h2>学院:
	                                <small>${userCollege}</small>
	                            </h2>
	                            <h2>专业:
	                                <small>${userMajor}</small>
	                            </h2>
                                <h2>年级:
	                                <small>${userGrade}</small>
	                            </h2>
	                            <h2>邮箱:
	                                <small>${userMail}</small>
	                            </h2>
	                            <h2>手机:
	                                <small>${userPhoneNum}</small>
	                            </h2>
	                            <h2>注册时间:
	                                <small>${userRegTime}</small>
	                            </h2>
                                <h2>注册IP:
	                                <small>${userRegIp}</small>
	                            </h2>
	                            
	                            <hr />
						       
	                        </div>
	                    </div>
	                </div>
	            </div>
            </div>
            <!-- /. PAGE INNER  -->
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
<script src="<%=path%>/statics/js/list.js"></script>
</body>
</html>
