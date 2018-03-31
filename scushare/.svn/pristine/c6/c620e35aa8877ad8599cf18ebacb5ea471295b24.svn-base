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
						<a class="nav-link" href="#"><span>任务</span></a>
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
		<div id="pic">
		</div>
		<div id="down-list" >
			<select class="form-control" id="first" onmouseover="this.size='25';">
                <option class="sbxjl">请选择学院</option>
                <option class="sbxjl">材料科学与工程学院</option>
                <option class="sbxjl">电气信息学院</option>
                <option class="sbxjl">电子信息学院</option>
                <option class="sbxjl">软件学院</option>
                <option class="sbxjl">法学院</option>
                <option class="sbxjl">高分子科学与工程学院</option>
                <option class="sbxjl">公共管理学院</option>
                <option class="sbxjl">华西公共卫生学院</option>
                <option class="sbxjl">华西口腔医学院</option>
                <option class="sbxjl">华西临床医学院</option>
                <option class="sbxjl">华西药学院</option>
                <option class="sbxjl">化学学院</option>
                <option class="sbxjl">化学工程学院</option>
                <option class="sbxjl">华西基础医学与法医学院</option>
                <option class="sbxjl">计算机学院</option>
                <option class="sbxjl">建筑与环境学院</option>
                <option class="sbxjl">经济学院</option>
                <option class="sbxjl">匹兹堡学院</option>
                <option class="sbxjl">历史文化学院（旅游学院）</option>
                <option class="sbxjl">轻纺与食品学院</option>
                <option class="sbxjl">商学院</option>
                <option class="sbxjl">生命科学学院</option>
                <option class="sbxjl">数学学院</option>
                <option class="sbxjl">水利水电学院</option>
                <option class="sbxjl">外国语学院</option>
                <option class="sbxjl">文学与新闻学院</option>
                <option class="sbxjl">物理科学与技术学院</option>
                <option class="sbxjl">艺术学院</option>
                <option class="sbxjl">制造科学与工程学院</option>
                <option class="sbxjl">空天科学与工程学院</option>      
            </select>
		</div>
		<div id="openbook">
			<img src="<%=path%>/statics/image/openbook.png">
			<a href="#"><span>计算机资料</span></a>
		</div>
		<div id="novels">
			<ul>
				<c:forEach var="file" items="${list}">
					<li>
						<!--<img src="/scushare/fileData/${file.fileCollege}/${file.FolderName}/0.jpg">  -->
						<img  src="/scushare/fileData/${file.fileCollege}/${file.FolderName}/0.jpg">
						<br/>
						<a href="preview?fileID=${file.fileId}" id="novel1">
							<span class="black_text">${file.fileName}</span>
						</a>
						<br/><br/>
						<a href="#"><span class="blue_text">${file.userName}</span></a>
	
					</li>
				</c:forEach>
			</ul>
		</div>

		<div id="back-img">
			<a href="javascript:void(0);"><img src="<%=path%>/statics/image/back.png"></a>
		</div>
		<div id="back-text">
			<a href="javascript:void(0);"><span>返回顶部</span></a>
		</div>

	<script type="text/javascript">
		//文档分类的显示与隐藏
		$(function(){  
	            $(docu).hover(  
	                function(){  
	                    $("#down-list").show();  
	                } ,  
	                function(){  
	                     $("#down-list").hide();
	                }   
	            ) ;  
	
	            $("#down-list").hover(  
	                function(){  
	                    $("#down-list").show();  
	                } ,  
	                function(){  
	                     $("#down-list").hide();
	                }   
	           	 ) ;  
	      	}) ;    

			//回到顶部的显示与隐藏
			$(function(){  
		            $("#back-img").hover(  
		                function(){  
		                	$("#back-img").hide();
		                    $("#back-text").show();    
		                },    
		            ) ;  

			$("#back-text").hover(  
				function(){    
				} , 

				function(){  
					$("#back-text").hide();
					$("#back-img").show();
					}  
				) ;  
        	}) ;  
			
			//回到顶部的点击事件
		
			$("#back-text").bind("click",function(){
					scrollTo(0,0);
			});
		
			//学院选择的点击跳转事件
			$(".sbxjl").bind("click",function(){
				window.location.href = "document?college=" + this.text;
			});
			
			//搜索文档事件
			$("#submit").bind("click",function(){
					var search = document.getElementById("input-tag").value;
					if(search != ""){
						event.preventDefault();
						window.location.href = "searchResult?search=" + search;
					}
	
			});
		</script>

	</body>
</html>
<% out.flush();%>