<%@page import="com.scushare.entity.UserModifiableInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String user_name = null;
	user_name = (String)request.getSession().getAttribute("user_name");
	if(user_name == null){
		response.sendRedirect("index");
	}
	Integer user_id = (Integer)request.getSession().getAttribute("user_id");
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
    <script type="text/javascript"></script>
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
								<%=user_name %>
								<%="<br />" %>
								<%="UID: <span id=\"user_id\">" + String.valueOf(user_id) + "</span>" %>
                            <br />
                                <small>Last Login : 2 Weeks Ago </small>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a href="uploadRecPage"><i class="fa fa-dashboard "></i>我的上传</a>
                    </li>                                                                
                    <li>
                        <a href="UMDownloadQuery"><i class="fa fa-flash "></i>&ensp;我的下载 </a>
                        
                    </li>
                    
                      <li>
                        <a href="uploadPage"><i class="fa fa-anchor "></i>上传文件</a>
                    </li>
                     <li>
                        <a class="active-menu" href="userInfoPage"><i class="fa fa-bug "></i>我的资料</a>
                    </li>
                    
                    <li>
                        <a href="userhomepage_userCommentRec.jsp"><i class="fa fa-edit "></i>我的评论</a>
                    </li>
                    
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="col-md-6 col-sm-6 col-xs-12">
               		<div class="panel panel-danger">
                        <div class="panel-heading">
                           	修改信息
                        </div>
                        <div class="panel-body">
                            <form id="userInfoForm" name="userInfoForm" role="form" action="doUserInfoModify" onsubmit="return checkUserInfo()">
                                        
<!--                                 <div class="form-group">
                                    <label>姓名</label>
                                    <input class="form-control" type="text">
                             		<p class="help-block">Help text here.</p>
                                </div> -->
                                <div class="form-group">
                                    <label>性别</label>
                                    <div class="radio" style="margin-left:20px; ">
										<input id="gender_male" name="user_gender" type="radio" value="男" checked="checked"/><span><i>男 &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;</i></span>
										<input id="gender_female" type="radio" value="女"/><span><i>女 &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;</i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>学院</label>
                                    <select class="form-control" id="first" name="user_college" onChange="change()" >
                                        <option selected="selected">请选择</option>
										<option>材料科学与工程学院</option>
										<option>电气信息学院</option>
										<option>电子信息学院</option>
										<option>软件学院</option>
										<option>法学院</option>
										<option>高分子科学与工程学院</option>
										<option>公共管理学院</option>
										<option>华西公共卫生学院</option>
										<option>华西口腔医学院</option>
										<option>华西临床医学院</option>
										<option>华西药学院</option>
										<option>化学学院</option>
										<option>化学工程学院</option>
										<option>华西基础医学与法医学院</option>
										<option>计算机学院</option>
										<option>建筑与环境学院</option>
										<option>经济学院</option>
										<option>匹兹堡学院</option>
										<option>历史文化学院（旅游学院）</option>
										<option>轻纺与食品学院</option>
										<option>商学院</option>
										<option>生命科学学院</option>
										<option>数学学院</option>
										<option>水利水电学院</option>
										<option>外国语学院</option>
										<option>文学与新闻学院</option>
										<option>物理科学与技术学院</option>
										<option>艺术学院</option>
										<option>制造科学与工程学院</option>
										<option>空天科学与工程学院</option>		
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>专业</label>
	                            	<select id="second" name="user_major" class="form-control">
										<option selected="selected">请选择</option>
									</select>
                                </div>
                                <div class="form-group">
                                    <label>手机</label>
                                    <input id="user_phone_num" name="user_phone_num" class="form-control" type="text">
                                </div>
                                <input type="hidden" name="user_id" id="user_id" <%="value='" + user_id.toString() + "'"%>/>
                                <!--<div class="form-group">
                                    <label>签名</label>
                                    <textarea class="form-control" rows="3"></textarea>
                                </div>-->
                                 
                                <button type="submit" class="btn btn-danger">修改 </button>
								<a href="script:void(0)" class="btn btn-danger " onclick="document.userInfoForm.reset();pwStrength('');">重置</a>
								<a href="userInfoPage" class="btn btn-danger ">返回</a>
                            </form>
                        </div>
                    </div>
                </div>
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
    <!-- LIST SCRIPTS -->
    <script src="<%=path%>/statics/js/list.js"></script>
    <!-- CHECK USERINFO FORM SCRIPTS -->
    <script src="<%=path%>/statics/js/check-userinfo-form.js"></script>
    
    <%
    	UserModifiableInfo userMlbInfo = (UserModifiableInfo)request.getAttribute("userMlbInfo");
    	out.println("<script type=\"text/javascript\">");
    	out.println("$(\"input:radio[value='" + userMlbInfo.getUser_gender() + "']\").attr('checked','true');");
    	out.println("$(\".selector\").find(\"option:[text='" + userMlbInfo.getUser_college() + "']\").attr(\"selected\",true);");
    	out.println("$(\".selector\").find(\"option:contains('" + userMlbInfo.getUser_major() + "')\").attr(\"selected\",true);");
    	out.println("$('#user_phone_num').val('" + userMlbInfo.getUser_phone_num() + "')");
    	out.println("</script>");
    %>


</body>
</html>
<%out.flush();%>