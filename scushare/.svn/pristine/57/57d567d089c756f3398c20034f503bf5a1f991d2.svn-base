<%@page import="com.scushare.utils.JSPprinter"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="scu.pojo.Download"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	final long OneMB = 1024 * 1024;
	final long OneKB = 1024;
	String user_name = null;
	user_name = (String)request.getSession().getAttribute("user_name");
	if(user_name == null){
		response.sendRedirect("index");
	}
	int user_id = (Integer)request.getSession().getAttribute("user_id");
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
    <title>我的下载</title>

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
								<%out.print("UID: " + String.valueOf(user_id)); %>
                            	<br />
                                <small>Last Login : 2 Weeks Ago </small>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a  href="uploadRecPage"><i class="fa fa-dashboard "></i>我的上传</a>
                    </li>
                    <li>
                        <a class="active-menu" href="UMDownloadQuery"><i class="fa fa-flash "></i>&ensp;我的下载</a>
                        
                    </li>
                      <li>
                        <a href="uploadPage"><i class="fa fa-anchor "></i>上传文件</a>
                    </li>
                     <li>
                        <a href="userhomepage_userInfo.jsp"><i class="fa fa-bug "></i>我的资料</a>
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
                <div class="panel panel-default">
                        <div class="panel-heading">
                        	下载记录
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                    <%
                                    	List<Download> downloadList = (List<Download>)request.getAttribute("downloadList"); 
                                    	if(downloadList == null || downloadList.size() == 0){
                                    		out.print("没有下载记录");
                                    	}else{
                                    		int currPage = (Integer)request.getAttribute("startPage");
                                    		int maxPage = (Integer)request.getAttribute("maxPageNum");
                                    		int recPerPage = (Integer)request.getAttribute("recordsCountPerPage");
                                    		int currIndex = (currPage - 1) * recPerPage + 1;
                                    		int length = downloadList.size();
                                    		Download record = null;
                                    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                                    		out.println("<table class=\"table table-hover\">");
                                    		out.println("<thead>");
                                    		out.println("<tr>");
                                    		out.println("<th>#</th>");
                                    		out.println("<th>资料名</th>");                                   	    
                                    		out.println("<th>大小</th>");
                                    		out.println("<th>上传时间</th>");
                                    		out.println("</tr>");
                                    		out.println("</thead>");
                                    		out.println("<tbody>");
                                    		for(int i = 0; i < length; i++){
                                    			record = downloadList.get(i);
                                    			out.println("<tr>");
                                    			out.println("<td>" + (i + currIndex) + "</td>");
                                    			/* out.println("1");
                                    			out.println("2");
                                    			out.println("3"); */
                                    			if(record.getFileName()== null){
                                    				out.println("1");
                                    			}else{
                                        			out.println("<td>" + (i + currIndex) + "</td>");
                                        			out.println("<td><a href=\"preview?fileID=" + String.valueOf(record.getFileId()) + "\">");
                                        			out.println(record.getFileName() + "</a></td>");
                                    			}
                                    			
                                    			//计算文件大小
                                    			long file_size = record.getFileSize();
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
                                    			if(record.getDownloadTime() == null){
                                    				out.println("2");
                                    				
                                    			}else
                                    			{
                                    				out.println("<td>" + sdf.format(record.getDownloadTime()) + "</td>"); 
                                    			}
                                    			out.println("</tr>");
                                    		}//LOOP END
                                    		out.println("</tbody>");
                                    		out.println("</table>");
                                    		
                                    		//打印按钮
                                    		out.println(JSPprinter.printPageButton(currPage, maxPage, "UMDownloadQuery"));
                                    	}
                                    %>
                            </div>
                        </div>
                    </div>
                    <!-- End  Hover Rows  -->
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
<%out.flush();%>