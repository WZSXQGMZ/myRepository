﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>川大资料网</title>

    <!-- BOOTSTRAP STYLES-->
        <%  String path = request.getContextPath(); %>
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
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html" style="font-size: 40px;">川大资料网</a>
            </div>

            <div class="header-right">

                
                <a href="javascript:void(0)" class="btn btn-danger" onclick="exit()" title="退出"><i class="fa fa-exclamation-circle fa-2x"></i></a>

            </div>
            <form action="logout" method="post" id="subForm"></form>>
        </nav>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <div class="user-img-div">
                            <img src="<%=path%>/statics/img/user.png" class="img-thumbnail" />

                            <div class="inner-text">
                                <font size="5">管理员：</font>${managerSession.managerName }
                            <br />
                                <a href="<%=path%>/MInfoModify" style="color: black;">修改个人信息</a>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a  href="<%=path%>/main" ><i></i>资料查询</a>
                    </li>
                    <li>
                        <a href="#" ><i ></i>资料审核 <span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level">
                            <li>
                                <a  href="<%=path%>/MCheckHistory" ><i ></i>审核历史</a>
                            </li>
                            <li>
                                <a href="<%=path%>/MDataCheck"><i ></i>资料审核</a>
                            </li>
                             
                           
                        </ul>
                    </li>
                     <li>
                        <a class="active-menu" href="<%=path%>/MmanagerQuery"><i ></i>管理员查询</a>
                         
                    </li>
                    <li>
                        <a  href="<%=path%>/MEnterUserQuery"><i ></i>用户查询 </a>
                        
                    </li>
                     
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
        	<div id="page-inner">
        		<div class="row">
                    <div class="col-md-12">
                        <h1 class="page-head-line">管理员查询</h1>
                        <h1 class="page-subhead-line">这里，你将可以对所有管理员进行查询</h1>

                    </div>
                </div>
                <form action="<%=path%>/MmanagerQuery" method="post" name="Mmanagerquery">
            <div class="row">
                   <div class="col-md-12">
                            <div class="col-md-5">
                            	<input class="form-control" placeholder="请输入搜索关键词" type="text" name="managerName"/>
                            </div>
                            <div class="col-md-1">
                            	<input class="form-control" type="submit" value="搜索" >
                            </div>
                          </div>    
                </div>
                
                </form>
               </br>
                </hr>
                <div class="row">
                <div class="col-md-12">
                     <!--    Hover Rows  -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	管理员列表
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th scope="col">编号</th>
    										<th scope="col">姓名</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="manager" items="${managerList}">
                                        <tr>
                                            <td>${manager.managerId}</td>
                                            <td>${manager.managerName}</td>
                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                       
                    </div>
                    <!-- End  Hover Rows  -->
                </div>
                <!-- /. ROW  -->
               
              
        </div>
        <!-- /. PAGE WRAPPER  -->
    	</div>
    </div>
   
    <!-- /. WRAPPER  -->

    </div>
    <div id="footer-sec">
       川大资料网
    </div>
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
    <script src="<%=path%>/statics/js/ManagerOperation.js"></script>
    


</body>
</html>
<%out.flush();%>