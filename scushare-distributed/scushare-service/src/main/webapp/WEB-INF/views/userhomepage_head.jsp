<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String user_name = null;
	user_name = (String)request.getSession().getAttribute("user_name");
	if(user_name == null){
		response.sendRedirect("index");
	}
	Integer user_id = (Integer)request.getSession().getAttribute("user_id");
	if(user_id == null){
		response.sendRedirect("index");
	}
	String user_chathead = (String)request.getSession().getAttribute("user_chathead");
	if(user_chathead == null || user_chathead.equals("")){
		user_chathead = "/userChathead/defaultChathead.jpg";
	}
%>
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
                <a href="index" class="btn btn-primary ">返回首页</a>
                <a href="doLogout" class="btn btn-danger" title="注销"><i class="fa fa-exclamation-circle fa-2x"></i></a>

            </div>
        </nav>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li>
                        <div class="user-img-div">
                            <img src="<%=path%><%=user_chathead %>" class="img-thumbnail" />

                            <div class="inner-text">
								<%out.print(user_name); %>
								<%out.print("<br />"); %>
								<%out.print("UID: <span id=\"user_id\">" + String.valueOf(user_id) + "</span>"); %>
                            <br />
                                <small>Last Login : 2 Weeks Ago </small>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a id="uploadInfoLink" href="uploadRecPage"><i class="fa fa-dashboard "></i>我的上传</a>
                    </li>
                   
                    <li>
                        <a id="downloadInfoLink" href="UMDownloadQuery"><i class="fa fa-flash "></i>&ensp;我的下载 </a>
                        
                    </li>
          
                    <li>
                        <a id="uploadFileLink" href="uploadPage"><i class="fa fa-anchor "></i>上传文件</a>
                    </li>
                     <li>
                        <a id="userInfoLink" href="userInfoPage"><i class="fa fa-bug "></i>我的资料</a>
                    </li>
                    
                    <li>
                        <a id="commentLink" href="userhomepage_userCommentRec.jsp"><i class="fa fa-edit "></i>我的评论</a>
                    </li>
                   
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->