<%@page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%  String path = request.getContextPath(); 
		String user_name = (String)request.getSession().getAttribute("user_name");
		String user_chathead = (String)request.getSession().getAttribute("user_chathead");
		if(user_chathead == null || user_chathead.equals("")){
			user_chathead = "/userChathead/defaultChathead.jpg";
	
	}
	%>
	<link rel="shortcut icon" href="<%=path%>/statics/image/shareico.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/preview.css">
    <script type="text/javascript" src="<%=path%>/statics/js/jquery-3.2.1.slim.min.js"></script>
    <script type="text/javascript" src="<%=path%>/statics/js/jquery.media.js"></script>
	<title>查看</title>
</head>
<body>
	<div id="navbar" >
 		<div id="nav-left">
	  		<a>
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
					<a class="nav-link" ><span>任务</span></a>
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
			    <form >
					<input id="input-tag" type="search" placeholder="在文库中查找">
					<input type="submit" id="submit">
			    </form>
			</div>
			<button type="submit">上传我的文档</button>
		</div>
	</div>

	<div id="tool">
		<div id="download-save" class="right-border">
			<button id="save">
				<img src="<%=path%>/statics/image/save.png">
				<span>收藏</span>
			</button>
			<button id="download">
				<img src="<%=path%>/statics/image/download.png">
					<span>下载</span>
			</button>   
		</div>
		<div id="zoom" class="right-border">
			<img class="zoom-oper" id="enlarge" src="<%=path%>/statics/image/enlarge.png">
			<img class="zoom-oper" id="reduce" src="<%=path%>/statics/image/reduce.png">
			<img class="zoom-oper" id="fullscreen" src="<%=path%>/statics/image/fullscreen.png">
			<img class="zoom-oper" id="thumbnail" src="<%=path%>/statics/image/thumbnail.png">
		</div>
		<div id="pages" class="right-border">
			<div>
				<
				<input id="changePage">
				/
				<span id="total-pages">${pageNum}</span>
				>
			</div>
		</div>
		<div id="share" class="right-border">
			<img id="share-link" src="<%=path%>/statics/image/share.png">
			<img id="code" src="<%=path%>/statics/image/code.png">
		</div>
		<div id="oper" class="right-border">
			<img id="tip-off" src="<%=path%>/statics/image/tip-off.png">
			<img id="comment" src="<%=path%>/statics/image/comment.png">
		</div>
	</div>
	<div id="title">
		<img  id="doc-icon" src="<%=path%>/statics/image/${fileType}.png">
		<span id="doc-title">${fileName}</span>
		<span id="download-text">下载积分:</span>
		<span id="download-score" class="red-span">0</span>
		<img id="arrow" src="<%=path%>/statics/image/down-arrow.png" alt="1">
	</div>
	<div id="hide-info">
		<span id="main-mean">内容提示：${fileIntroduce}</span>
		<span></span>
		<ul>
			<li>文档格式：<span>DOCX</span></li>
			<li>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;浏览次数：<span>5</span></li>
			<li>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;上传日期：<span>${fileUpTime}</span></li>
			<li>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;文档星级；<img src="<%=path%>/statics/image/4.5.bmp"></li>
		</ul>

	</div>
	<c:forEach var = "i" begin="0" end="${pageNum-1}" step="1">
		<div class="content">
			<img class="file" src='/scushare/fileData/${fileCollege}/${FolderName}/${i}.jpg'>		
		</div>
		<span></span>
	</c:forEach>

	<div id="info">
		<div id="personal-info">
			<img src="<%=path%>/userChathead/${userImage}">
			<a id="name"><span>${userName}</span></a>
			<span id="upload">上传于:</span>
			<span id="upload-date">${fileUpTime}</span>
		</div>
		<button id="download2">
			下载此文档
		</button>
		<div id="relative-doc">
			<p>
				推荐文档
			</p>
			<ul id="file-info">
			<c:forEach  var = "file" items="${list}">
				<li>	
					<a href="preview?fileID=${file.fileId}" title="${file.fileName}">
						<img src="<%=path%>/statics/image/${file.fileType}.png">
						<span  class="black-span">
							<span class="underline">${file.fileName}</span><br/>
							星级：
							<img class="star" src="<%=path%>/statics/image/5.bmp"></span>
							<span id="page-num">${file.pageNum}</span>页
						</span>
					</a>
				</li>
			</c:forEach>
			</ul>
		</div>
	</div>

	<script type="text/javascript">
		$("#arrow").bind("click",function(){
			var status = document.getElementById("arrow").alt;
			if(status==1){
				$("#hide-info").show();
				$("#arrow").attr('src', '../../scushare/statics/image/up-arrow.png');
				document.getElementById("arrow").alt = 2;
			}
			else{
				$("#hide-info").hide();
				$("#arrow").attr('src', '../../scushare/statics/image/down-arrow.png');
				document.getElementById("arrow").alt = 1;
			}
		});
		//跳转页数
		$("#changePage").blur(function(){
			value = document.getElementById("changePage").value;
			scrollTo(0,150+(value-1)*1090);
		});
		$("#submit").bind("click",function(){
			var search= document.getElementById("input-tag").value;
			console.log(search);
			if(search != null){
				window.location.href = "searchResult?search="+ search;
			}
			
		});
		$("#download").bind("click",function(){
			event.preventDefault();
			var fileName = document.getElementById("doc-title").innerText;
			var fileID = window.location.href.split("=")[1];
			window.open("MDownload?fileId="+fileID+"&fileName="+fileName);
		});
		$("#download2").bind("click",function(){
			var fileName = document.getElementById("doc-title").innerText;
			var fileID = window.location.href.split("=")[1];
			window.open("MDownload?fileId="+fileID+"&fileName="+fileName);
		});
	</script>
</body>
</html>
<% out.flush();%>