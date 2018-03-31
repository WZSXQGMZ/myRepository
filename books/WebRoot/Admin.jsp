<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>图书管理系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
	<script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	$(function(){
		//setMenuHeight
		$('.menu').height($(window).height()-51-27-26);
		$('.sidebar').height($(window).height()-51-27-26);
		$('.page').height($(window).height()-51-27-26);
		$('.page iframe').width($(window).width()-15-168);
		
		//menu on and off
		$('.btn').click(function(){
			$('.menu').toggle();
			
			if($(".menu").is(":hidden")){
				$('.page iframe').width($(window).width()-15+5);
				}else{
				$('.page iframe').width($(window).width()-15-168);
					}
			});		
		//
		$('.subMenu a[href="#"]').click(function(){
			$(this).next('ul').toggle();
			return false;
			});
	});
</script>
  </head>
  
  <body>
  <%
  	String s_adminid = request.getParameter("adminid");
  %>
    <div id="wrap">
	<div id="header">
    <div class="logo fleft"></div>
    <a class="logout fright" href="Login.jsp" title="退出系统"> </a>
    <div class="clear"></div>
    <div class="subnav">
    	<div class="subnavLeft fleft"></div>
        <div class="fleft"></div>
        <div class="subnavRight fright"></div>
    </div>
    </div><!--#header -->
    <div id="content">
    <div class="space"></div>
    <div class="menu fleft">
    	<ul>
        	<li class="subMenuTitle">系统管理</li>
        	<li class="subMenu"><a href="#">管理员管理</a>
            	<ul>
                	<li><a href="MICLServlet?pageNow=1" target="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;管理员管理</a></li>
                    <li><a href="AdminInfoServlet?flag=getInfo&adminid=<%=s_adminid %>" target="right">&nbsp;&nbsp;&nbsp;修改信息</a></li>
                </ul>
            </li>
            <li class="subMenu"><a href="#">读者管理</a>
            	<ul>
                	<li><a href="UserClServlet?pageNow=1" target="right">&nbsp;&nbsp;&nbsp;读者管理</a></li>
                    <li><a href="UserClServlet?flag=add&pageNow=1" target="right">&nbsp;&nbsp;&nbsp;添加读者</a></li>
                </ul>
            </li>
            <li class="subMenu"><a href="#">图书管理</a>
	            <ul>
	                <li><a href="BICLServlet?pageNow=1" target="right">&nbsp;&nbsp;&nbsp;图书管理</a></li>
	                <li><a href="insert.jsp" target="right">&nbsp;&nbsp;&nbsp;图书添加</a></li>
	            </ul>
            </li>
        </ul>
    </div>
    <div class="sidebar fleft"><div class="btn"></div></div>
    <div class="page">
    <iframe width="100%" scrolling="auto" height="100%" frameborder="false" allowtransparency="true" style="border: medium none;" src="Welcome.jsp" id="rightMain" name="right"></iframe>
    </div>
    </div><!--#content -->
    <div class="clear"></div>
    <div id="footer"></div><!--#footer -->

</div><!--#wrap -->
  </body>
</html>
