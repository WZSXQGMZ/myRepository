<%@page import="com.scushare.entity.FileInfo"%>
<%@page import="com.scushare.entity.RewardTaskAnswerInfo"%>
<%@page import="com.scushare.utils.DateUtil"%>
<%@page import="com.scushare.utils.JSPprinter"%>
<%@page import="com.scushare.entity.UserUploadRec"%>
<%@page import="java.util.List"%>
<%@page import="com.scushare.entity.RewardTaskInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	final long OneMB = 1024 * 1024;
	final long OneKB = 1024;
%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%  String path = request.getContextPath(); 
    	Integer user_id = (Integer)request.getSession().getAttribute("user_id");
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
			<%RewardTaskInfo task = (RewardTaskInfo)request.getAttribute("task"); %>
			<h4>标题：<%=task.getTitle() %> 
			<%
			if(task.getIs_closed() == 1){
				out.print("（已完成）");
    		} %></h4>
			<h5>描述：<%=task.getDescription() %></h5>
			<h5>关键词：<%=task.getKeywords() %></h5>
		</div>
		<div>
			<div class="panel panel-default">
                        <div class="panel-body">
                            <div class="table-responsive">
                                    <%
                            			List<RewardTaskAnswerInfo> answerList = (List<RewardTaskAnswerInfo>)request.getAttribute("answerList");
                                    	List<FileInfo> fileList = (List<FileInfo>)request.getAttribute("fileList");
                                    	List<String> userList = (List<String>)request.getAttribute("userList");
                                    	if(task.getUser_id() != user_id && task.getIs_closed() == 0){
                                    		out.println("<div><a href=\"answerTaskPage?task_id=" + task.getTask_id() + "\">我要回答</a></div>");
                                    	}
                                    	if(answerList == null || answerList.size() == 0){
                                    		out.print("暂时没有回答");
                                    	}else{%>
                                    	<div>
											用户回答：
                                    		<table>
                                    			<thead>
                                    			<tr>
	                                    		<%
	                                    		if(task.getIs_closed() == 1 || task.getUser_id() == user_id){
	                                				out.print("<th></th>");
	                                    		}
	                                    		%>
                                    			<th></th>
                                    			<th></th>
                                    			<th></th>
                                    			</tr>
                                    			</thead>
                                    			<tbody>
                                    			<% 	int length = answerList.size();
                                    				RewardTaskAnswerInfo closedAnswer = (RewardTaskAnswerInfo)request.getAttribute("closedAnswer");
                                    				for(int i = 0; i < length; i++){
                                    					FileInfo fileInfo = fileList.get(i);
                                    					RewardTaskAnswerInfo answer = answerList.get(i);
                                    					if(closedAnswer != null && answer.getAnswer_id() == closedAnswer.getAnswer_id()){
                                    						//continue;
                                    					}
                                    					out.print("<tr>");
                                    					if(task.getIs_closed() == 1){
                                    						out.print("<td>");
                                    						if(answer.getAnswer_id() == closedAnswer.getAnswer_id()){
                                    							out.println("满意答案");
                                    						}
                                    						out.print("</td>");
                                    					}else if(task.getUser_id() == user_id){
                                    						out.print("<td><a href=\"rewardTaskClose?task_id=" + task.getTask_id() + "&answer_id=" + answer.getAnswer_id() + "\">"
                                    								+"设为满意答案</a></td>");
                                    					}
                                    					out.print("<td><a href=\"preview?fileID=" + fileInfo.getFile_id() + "\">" + fileInfo.getFile_name() + "</a></td>");
                                    					out.print("<td>" + userList.get(i) + "</td>");
                                    					out.print("<td>" + DateUtil.getStringByDate(answer.getDate()));
                                    					out.print("</tr>");
                                    				}
                                    			%>
                                    			</tbody>
                                    		</table>
                                    	</div>
                                    	<%}%>
                            </div>
                        </div>
                    </div>
                    <!-- End  Hover Rows  -->
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