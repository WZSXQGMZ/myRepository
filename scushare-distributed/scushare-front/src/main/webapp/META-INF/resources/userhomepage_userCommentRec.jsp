<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>个人主页</title>

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
    <link href='<%=path%>/statics/css/font.css' rel='stylesheet' type='text/css' />
</head>
<body>
    <div id="wrapper">
        <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index">川大资料网</a>
            </div>

            <div class="header-right">

                <a href="loginPage" class="btn btn-primary ">重新登录</a>
                <a href="homePage" class="btn btn-primary ">返回首页</a>
                <a href="doLogout" class="btn btn-danger" title="注销"><i class="fa fa-exclamation-circle fa-2x"></i></a>
            </div>
        </nav>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <div class="user-img-div">
                            <img src="<%=path%>/statics/img/user.png" class="img-thumbnail" />

                            <div class="inner-text">
                                uername
                            <br />
                                <small>Last Login : 2 Weeks Ago </small>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a href="userhomepage_userUploadRec.jsp"><i class="fa fa-dashboard "></i>我的上传</a>
                    </li>
                    
                    <li>
                        <a href="userhomepage_userDownloadRec.jsp"><i class="fa fa-flash "></i>&ensp;我的下载 </a>
                        
                    </li>
                     
                      <li>
                        <a href="userhomepage_userupload.jsp"><i class="fa fa-anchor "></i>上传文件</a>
                    </li>
                     <li>
                        <a href="userhomepage_userInfo.jsp"><i class="fa fa-bug "></i>我的资料</a>
                    </li>
                    <li>
                        <a class="active-menu" href="#"><i class="fa fa-edit "></i>我的评论</a>
                    </li>
                  
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-head-line">我的评论</h1>
                        <h1 class="page-subhead-line">这是你的历史评论，你可以回顾你的点点滴滴： </h1>

                    </div>
                </div>
                <!-- /. ROW  -->
                 
                                 
            <div class="row">
                <div class="col-md-4 col-sm-4">
                    <div class="panel panel-default">
                        <div class="panel-heading" id="file_name">
                           	 <a href="download.jsp\file_name" >file_name</a>
                        </div>
                        <div class="panel-body" id="file_content">·
                            <p>file_content</p>
                        </div>
                        <div class="panel-footer" id="review_time" align="right">
                            review_time
                        </div>
                    </div>
                </div>
            </div>
			<hr/>
			没有评论 or 评论太少？ <a href="search.jsp" >点击这里</a>搜索资源进行评论
            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->

    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="<%=path%>/statics/js/bootstrap.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery.metisMenu.js"></script>
    <!-- CUSTOM SCRIPTS -->
    <script src="<%=path%>/statics/js/custom.js"></script>
    


</body>
</html>
