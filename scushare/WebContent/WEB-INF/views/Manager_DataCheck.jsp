<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>川大资料网</title>
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
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
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
                <a class="navbar-brand" href="index.html" style="font-size: 40px;">川大资料网</a>
            </div>

            <div class="header-right">

              
                 <a href="#" onclick="exit()" class="btn btn-danger" title="退出"><i class="fa fa-exclamation-circle fa-2x"></i></a>

            </div>
            <form id="subForm" action="logout" method="post"></form>
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
                                  <a href="Manager_modifyPassword.jsp" style="color: black;">修改个人信息</a>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a href="<%=path%>/main"><i  ></i>资料查询</a>
                    </li>
                    <li>
                        <a href="Manager_DataCheck.jsp"  class="active-menu-top"><i ></i>资料审核 <span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level collapse in">
                            <li>
                                <a href="<%=path%>/MCheckHistory"><i></i>审核历史</a>
                            </li>
                            <li>
                                <a class="active-menu" href="<%=path%>/MDataCheck"><i ></i>资料审核</a>
                            </li>
                             
                            
                           
                        </ul>
                    </li>
                     <li>
                        <a href="<%=path%>/MmanagerQuery"><i ></i>管理员查询 </a>
                     </li>
                    <li>
                        <a href="<%=path%>/MEnterUserQuery"><i ></i>用户查询 </a>
                        
                    </li>
                    
                </ul>

            </div>

        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-head-line">资料审核</h1>
                        <h1 class="page-subhead-line">这里，你将可以对用户上传的资料进行审核</h1>

                    </div>
                </div>
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	用户上传的资料
                            	<form action="<%=path%>/MDataCheck" method="post" name="Mdataquery" value="Mdataquery">
                            	<table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>用户ID</th>
                                            <th>资料名称</th>
                                            <th>上传作者</th>
                                            <th>上传时间</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach  var="file" items="${fileList}">
                                        <tr>
                                            <td>${file.fileId}</td>
                                            <td>${file.fileName}</td>
                                            <td>${file.fileUsername}</td>
                                            <td>${file.fileUpTime}</td>
                                           
                                            <td> <p style="float: right;width: 300px;">
                                   
                                    <a class="btn btn-primary" href="<%=path%>/MDataCheckDeeper?fileId=${file.fileId}"><i class="glyphicon glyphicon-search"></i>查看</a>
                                    
                                   
                                </p></td>
                                        </tr>
                                       </c:forEach>
                                       
                                    </tbody>
                                </table>
                                </form>
                                <span>共有：${totalPageCount}页 /${totalCount}条记录 </span>
                           	
						
						<%--如果当前页为第一页时，就没有上一页这个超链接显示 --%>
						
				              <c:if test="${currentPageNo ==1}">
				              		<ul class="pagination" style="float: right;">
				                  <c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
				                      <c:if test="${currentPageNo == i}">
				                          <li class="active"><a href="#"> ${i}<span class="sr-only">(current)</span></a></li>
				                     </c:if>                
				                     <c:if test="${currentPageNo != i}">
				                       
				                        <li><a href="<%=path%>/MDataCheck?pageIndex=${i}">${i}</a></li>                                       
				                     </c:if>                        
				                 </c:forEach>
				                 <li><a href="<%=path%>/MDataCheck?pageIndex=${currentPageNo+1}">&raquo;</a></li>                   
				             		</ul>
				             </c:if>
				             
				             <%--如果当前页不为第一页时，就有上一页这个超链接显示 --%>
				             
				             <c:if test="${currentPageNo>1&&currentPageNo<totalPageCount}">
				              		<ul class="pagination" style="float: right;">
				              		<li ><a href="<%=path%>/MDataCheck?pageIndex=${currentPageNo-1}" >&laquo;</a></li>
				                  <c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
				                      <c:if test="${currentPageNo==i}">
				                          <li class="active"><a href="#"> ${i}<span class="sr-only">(current)</span></a></li>
				                     </c:if>                
				                     <c:if test="${currentPageNo!= i}">
				                       
				                        <li  ><a href="<%=path%>/MDataCheck?pageIndex=${i}">${i}</a></li>                                       
				                     </c:if>                        
				                 </c:forEach>
				                 <li><a href="<%=path%>/MDataCheck?pageIndex=${currentPageNo+1}">&raquo;</a></li>                   
				             		</ul>
				             </c:if>
				             
				              <%--如果当前页为最后一页时，就没有下一页这个超链接显示 --%>
				             
				             <c:if test="${currentPageNo==totalPageCount}">
				              		<ul class="pagination" style="float: right;">
				              		<li ><a href="<%=path%>/MDataCheck?pageIndex=${currentPageNo-1}" >&laquo;</a></li>
				                  <c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
				                      <c:if test="${currentPageNo==i}">
				                          <li class="active"><a href="#"> ${i}<span class="sr-only">(current)</span></a></li>
				                     </c:if>                
				                     <c:if test="${currentPageNo!=i}">
				                       
				                        <li><a href="<%=path%>/MDataCheck?pageIndex=${i}">${i}</a></li>                                       
				                     </c:if>                        
				                 </c:forEach>
				                                  
				             		</ul>
				             </c:if> 
                        </div>
                        <div class="panel-body">
                            
                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                            <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                        </div>
                                        <div class="modal-body">
                                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                            <button type="button" class="btn btn-primary">Save changes</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <!-- /. ROW  -->
             
           
                
            </div>
            
            </div>
                    

            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    </div>
    <!-- /. WRAPPER  -->
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
    <script src="<%=path%>/statics/js/ManagerOperation.js"></script>


</body>
</html>
<% out.flush();%>