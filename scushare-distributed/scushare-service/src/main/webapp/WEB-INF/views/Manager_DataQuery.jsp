<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>川大资料网</title>
	
    <!-- BOOTSTRAP STYLES-->
    <%  String path = request.getContextPath(); %>
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
            <div class="navbar-header" >
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
                                <a href="<%=path%>/MInfoModify" style="color: black;">修改个人信息</a>
                            </div>
                        </div>

                    </li>


                    <li>
                        <a class="active-menu" href="<%=path%>/main" ><i></i>资料查询</a>
                    </li>
                    <li>
                        <a href="#" ><i ></i>资料审核 <span class="fa arrow"></span></a>
                         <ul class="nav nav-second-level">
                            <li>
                                <a  href="<%=path%>/MCheckHistory" ><i ></i>审核历史</a>
                            </li>
                            <li>
                                <a href="<%=path%>/MDataCheck"><i ></i>资料审核</a>
                            </li>
                             
                           
                        </ul>
                    </li>
                     <li>
                        <a href="<%=path%>/MmanagerQuery"><i ></i>管理员查询</a>
                         
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
                        <h1 class="page-head-line">资料查询</h1>
                        <h1 class="page-subhead-line">这里，你将可以对所有资料进行查询</h1>

                    </div>
                </div>
                <form action="<%=path%>/MdataQuery" method="post" name="Mdataquery" value="Mdataquery">
            <div class="row">
                   <div class="col-md-12">
                            <div class="col-md-3">
                                            
                                            <select class="form-control" id="first" onChange="change()" name="fileCollege">
                                                <option selected="selected">请选择学院</option>
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
                            <div class="col-md-3">
                            	<select id="second"  class="form-control" name="fileMajor">
									<option selected="selected">请选择专业</option>
								</select>
                            </div>
                            <div class="col-md-5">
                            	<input class="form-control" placeholder="请输入搜索关键词" type="text" name="fileName"/>
                            </div>
                            <div class="col-md-1">
                            	<input class="form-control" type="submit" value="搜索" >
                            </div>
                          </div>    
                </div>
                </form>
               </br>
                </hr>
                <div class="row">
                <div class="col-md-12">
                     <!--    Hover Rows  -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            	资料列表
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
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
                                            <td>${file.fileUsername} </td>
                                            <td>${file.fileUpTime} </td>
                                            <td> <p style="float: right;width: 300px;">
                              		<a class="btn btn-inverse" href="<%=path%>/MDownload?fileId=${file.fileId}&fileName=${file.fileName}"><i class="glyphicon glyphicon-plus" ></i>下载</a>
                                    <a class="btn btn-primary" href="<%=path%>/Mpreview?fileID=${file.fileId}"   ><i class="glyphicon glyphicon-search"></i>预览</a>
                                    <button class="btn btn-danger" onclick="Delet(${file.fileId})"><i class="glyphicon glyphicon-home" ></i>删除</button>
                                    
                                </p></td>
                                        </tr>
                                        </c:forEach>
                                       
                                    </tbody>
                                </table>
                           		<span>共有：${totalPageCount}页 /${totalCount}条记录 </span>
                           	
						
						<%--如果当前页为第一页时，就没有上一页这个超链接显示 --%>
						
				              <c:if test="${currentPageNo ==1}">
				              		<ul class="pagination" style="float: right;">
				                  <c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
				                      <c:if test="${currentPageNo == i}">
				                          <li class="active"><a href="#"> ${i}<span class="sr-only">(current)</span></a></li>
				                     </c:if>                
				                     <c:if test="${currentPageNo != i}">
				                       
				                        <li><a href="<%=path%>/MdataQuery?pageIndex=${i}&fileCollege=${fileCollege}&fileMajor=${fileMajor}&fileName=${fileName}">${i}</a></li>                                       
				                     </c:if>                        
				                 </c:forEach>
				                 <li><a href="<%=path%>/MdataQuery?pageIndex=${currentPageNo+1}&fileCollege=${fileCollege}&fileMajor=${fileMajor}&fileName=${fileName}">&raquo;</a></li>                   
				             		</ul>
				             </c:if>
				             
				             <%--如果当前页不为第一页时，就有上一页这个超链接显示 --%>
				             
				             <c:if test="${currentPageNo>1&&currentPageNo<totalPageCount}">
				              		<ul class="pagination" style="float: right;">
				              		<li ><a href="<%=path%>/MdataQuery?pageIndex=${currentPageNo-1}&fileCollege=${fileCollege}&fileMajor=${fileMajor}&fileName=${fileName}" >&laquo;</a></li>
				                  <c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
				                      <c:if test="${currentPageNo==i}">
				                          <li class="active"><a href="#"> ${i}<span class="sr-only">(current)</span></a></li>
				                     </c:if>                
				                     <c:if test="${currentPageNo!= i}">
				                       
				                        <li  ><a href="<%=path%>/MdataQuery?pageIndex=${i}&fileCollege=${fileCollege}&fileMajor=${fileMajor}&fileName=${fileName}">${i}</a></li>                                       
				                     </c:if>                        
				                 </c:forEach>
				                 <li><a href="<%=path%>/MdataQuery?pageIndex=${currentPageNo+1}&fileCollege=${fileCollege}&fileMajor=${fileMajor}&fileName=${fileName}">&raquo;</a></li>                   
				             		</ul>
				             </c:if>
				             
				              <%--如果当前页为最后一页时，就没有下一页这个超链接显示 --%>
				             
				             <c:if test="${currentPageNo==totalPageCount}">
				              		<ul class="pagination" style="float: right;">
				              		<li ><a href="<%=path%>/MdataQuery?pageIndex=${currentPageNo-1}&fileCollege=${fileCollege}&fileMajor=${fileMajor}&fileName=${fileName}" >&laquo;</a></li>
				                  <c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
				                      <c:if test="${currentPageNo==i}">
				                          <li class="active"><a href="#"> ${i}<span class="sr-only">(current)</span></a></li>
				                     </c:if>                
				                     <c:if test="${currentPageNo!=i}">
				                       
				                        <li><a href="<%=path%>/MdataQuery?pageIndex=${i}&fileCollege=${fileCollege}&fileMajor=${fileMajor}&fileName=${fileName}">${i}</a></li>                                       
				                     </c:if>                        
				                 </c:forEach>
				                                  
				             		</ul>
				             </c:if>
                        </div>
                       
                    </div>
                    <!-- End  Hover Rows  -->
                </div>
                <!-- /. ROW  -->
               </div>

				</div>
				


                    
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
    <script src="<%=path%>/statics/js/list.js"></script>
    <script src="<%=path%>/statics/js/ManagerOperation.js"></script>
    


</body>
</html>
<% out.flush();%>