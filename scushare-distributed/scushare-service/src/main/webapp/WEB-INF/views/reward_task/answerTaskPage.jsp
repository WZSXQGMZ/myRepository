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
			<h4>标题：<%=task.getTitle() %></h4>
			<h5>描述：<%=task.getDescription() %></h5>
			<h5>关键词：<%=task.getKeywords() %></h5>
		</div>
		
		<div>
			<div class="panel panel-default">
                        <div class="panel-heading">
                        	上传记录
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                    <%
                                    	List<UserUploadRec> uploadRecList = (List<UserUploadRec>)request.getAttribute("recList"); 
                                    	if(uploadRecList == null || uploadRecList.size() == 0){
                                    		out.print("没有上传记录");
                                    	}else{
                                    		int currPage = (Integer)request.getAttribute("startPage");
                                    		int maxPage = (Integer)request.getAttribute("maxPageNum");
                                    		int recPerPage = (Integer)request.getAttribute("recordsCountPerPage");
                                    		int currIndex = (currPage - 1) * recPerPage + 1;
                                    		int length = uploadRecList.size();
                                    		UserUploadRec record = null;

                                    		out.println("<table class=\"table table-hover\">");
                                    		out.println("<thead>");
                                    		out.println("<tr>");
                                    		out.println("<th>#</th>");
                                    		out.println("<th>资料名</th>");
                                    		out.println("<th>大小</th>");
                                    		out.println("<th>上传时间</th>");
                                    		out.println("<th>审核状态</th>");
                                    		out.println("</tr>");
                                    		out.println("</thead>");
                                    		out.println("<tbody>");
                                    		for(int i = 0; i < length; i++){
                                    			record = uploadRecList.get(length - i - 1);
                                    			out.println("<tr>");
                                    			out.println("<td>" + (i + currIndex) + "</td>");
                                    			String prefix = "";
                                    			String postfix = "";
                                    			//获取审核情况
                                    			String verified = record.getFile_isverify();
                                    			if(verified == null || verified.equals("")){
                                    				verified = "未审核";
                                    			}else if(verified.equals("是")){
                                    				verified = "已通过";
                                    				prefix = "<a href=\"answerTask?file_id=" + String.valueOf(record.getFile_id())
										                                    				+ "&task_id=" + task.getTask_id()
										                                    				+ "\">";
                                    				postfix = "</a>";
                                    			}else if(verified.equals("否")){
                                    				verified = "未通过";
                                    			}
                                    			out.println("<td>" + prefix + record.getFile_name() + postfix + "</td>");
                                    			
                                    			//计算文件大小
                                    			long file_size = record.getFile_size();
                                    			String file_size_str = "";
                                    			if(file_size < OneKB){
                                    				file_size_str = "1K";
                                    			}else if(file_size < OneMB){
                                    				file_size_str = file_size / OneKB + "K";
                                    			}else {
                                    				file_size_str = file_size / OneMB + "M";
                                    			}
                                    			out.println("<td>" + file_size_str + "</td>");
                                    			
                                    			//获取上传时间
                                    			out.println("<td>" + DateUtil.getStringByDate(record.getFile_up_time()) + "</td>");

                                    			out.println("<td>" + verified + "</td>");
                                    			out.println("</tr>");
                                    		}//LOOP END
                                    		out.println("</tbody>");
                                    		out.println("</table>");
                                    		
                                    		//打印按钮
                                    		out.println(JSPprinter.printPageButton(currPage, maxPage, "answerTaskPage", "&task_id=" + task.getTask_id()));
                                    	}
                                    %>
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