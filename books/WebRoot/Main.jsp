<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主页</title>
    
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
  	HttpSession hs = request.getSession();
  	String usrid = (String)hs.getAttribute("userid");
  	//String userid = (String)request.getSession().getAttribute("userid");
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
        	<li class="subMenu"><a href="#" target="right">个人帐户管理</a>
            	<ul>
                    <li><a href="PersonalUserInfoServlet?flag=getInfo&userid=<%=usrid %>" target="right">&nbsp;&nbsp;&nbsp;查询信息</a></li>
                </ul>
            </li>
            <li class="subMenu"><a href="#" target="right">借阅管理</a>
	            <ul>
	                 <li><a href="booksQuery.jsp?queryResult=reset&booksQuery=''&pageNow=1&pageCount=0&userid=<%=usrid %>" target="right">&nbsp;&nbsp;&nbsp;查询图书</a></li>
	                 <li><a href="BookReturnServlet?flag=hasborrow" target="right">&nbsp;&nbsp;&nbsp;图书续还</a></li>
	                 <li><a href="BookReturnServlet?flag=allborrow" target="right">&nbsp;&nbsp;&nbsp;借阅记录</a></li>
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
