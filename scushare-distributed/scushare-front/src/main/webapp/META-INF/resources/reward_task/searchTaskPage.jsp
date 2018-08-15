<%@page import="com.scushare.utils.DateUtil"%>
<%@page import="com.scushare.entity.RewardTaskInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%  String path = request.getContextPath(); 
		String user_name = (String)request.getSession().getAttribute("user_name");
		String user_chathead = (String)request.getSession().getAttribute("user_chathead");
		if(user_chathead == null || user_chathead.equals("")){
			user_chathead = "/userChathead/defaultChathead.jpg";
		}
    %>
    <link rel="shortcut icon" href="<%=path%>/statics/image/shareico.ico" type="image/x-icon">
   	<link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/index.css">
    <script src="<%=path%>/statics/js/jquery-3.2.1.slim.min.js"></script>
    <title>川大资料网</title>
    <style type="text/css">　　
	    .description {
			max-width: 300px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
	</style>
	</head> 
	<body>
	 	<div id="navbar" >
	 		<div id="nav-left">
		  		<a href="#" >
		  			<img id ="logo" src="<%=path%>/statics/image/sharelogo.png" >
		  		</a>
	  		</div>

	  		<div id="catalog">
				<ul>
					<li >
						<a class="nav-link" href="index"><span>首页</span></a>
					</li>
					<li id="docu">
						<a class="nav-link dropdown-toggle" href="document" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span>文档</span>
						</a>
			      	</li>
					<li >
						<a class="nav-link" href="searchTaskPage"><span>任务</span></a>
					</li>
			    </ul>	
			</div>
			
			<%if(user_name == null){ %>
			<div id="user">
				<a href="loginPage"><span>登录</span></a>
				<span>|</span>
				<a href="registerPage"><span>注册</span></a>
			</div>
			<%}else{ %>
			<div id="login_image">
				<a href="userInfoPage"><img src="<%=path%><%=user_chathead %>"></a>
			</div>
			<%}%>
			
			<div id="form">
				<div id="input">
				    <form>
						<input id="input-tag" type="search" placeholder="在文库中查找">
						<input type="submit" id="submit">
				    </form>
				</div>
				<button type="submit">上传我的文档</button> <!--uploadPage-->
			</div>
		</div>
		<div>
			<form action="/searchTaskResultPage">
				<input type="text" name="keywords">
				<input type="submit" value="搜索">
			</form>
		</div>
		<div>
			<%
			List<RewardTaskInfo> recentTaskList = (List<RewardTaskInfo>)request.getAttribute("recentTaskList");
			if(recentTaskList == null || recentTaskList.size() == 0){
				
			}else{
			%>
			<div>
				最新任务
			</div>
			<div>
			<table>
			<thead>
			<tr>
				<th></th>
				<th></th>
				<th></th>
			</tr>
			</thead>
			<tbody>
			<%
				int i = recentTaskList.size();
				for(RewardTaskInfo task : recentTaskList){
					out.println("<tr>");
					out.println("<td><a href=\"taskInfoPage?task_id=" + task.getTask_id() + "\">");
					out.println(task.getTitle() + "</a></td>");
					out.println("<td class=\"description\">" + task.getDescription() + "</td>");
					out.println("<td>" + DateUtil.getStringByDate(task.getDate()) + "</td>");
					out.println("</tr>");
				}
			}
			%>
			</tbody>
			</table>
			</div>
			<div>
			<a href="rewardTaskPublishPage">发布任务</a>
			</div>
		</div>
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