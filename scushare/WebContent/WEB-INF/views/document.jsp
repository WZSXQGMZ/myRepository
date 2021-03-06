<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" autoFlush="true"%>
<%@page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/document.css">
    <script src="<%=path%>/statics/js/jquery-3.2.1.slim.min.js"></script>
    <script src="<%=path%>/statics/js/list.js"></script>
	<title>文档</title>
	<style type="text/css">
	</style>
</head>
<body>
	<div id="navbar">
		<div id="nav-left">
			<a href="#">
				<img id="logo" src="<%=path%>/statics/image/sharelogo.png">
			</a>
		</div>
		
		<div id="catalog">
			<ul>
				<li>
					<a class="nav-link" href="index">
						<span>首页</span>
					</a>
				</li>
				<li id="docu">
					<a href="document"><span>文档</span></a>
				</li>
				<li>
					<a class="nav-link" href="#">
						<span>任务</span>
					</a>
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
	<div id="down-list" >
		<select class="form-control" id="first" onmouseover="this.size='25';">
                <option class="sbxjl">请选择学院</option>
                <option class="sbxjl">材料科学与工程学院</option>
                <option class="sbxjl">电气信息学院</option>
                <option class="sbxjl">电子信息学院</option>
                <option class="sbxjl">软件学院</option>
                <option class="sbxjl">法学院</option>
                <option class="sbxjl">高分子材料与工程学院</option>
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
    <div id="location">
    	<span class="black-span">当前位置 :</span>
    	<div id="location-div">
    		<a id="current-location">&nbsp;<span class="blue-span" id="real-location"></span></a>
   		</div>
    </div>
    <div id="content">
    	<div id="types">
    		<div id="layer">
    			<a href="document"><span class="blue-span">全部分类</span></a>
    		</div>

			<div id="type">
				<ul id="ul">
				</ul>
			</div>

			<div id="filter">
				<ul>
					<li class="left-li"><a class="all"><span class="black-span">全部格式</span></a></li>
					<li class="left-li"><a class="format"><span class="black-span">PDF</span></a></li>
					<li class="left-li"><a class="format"><span class="black-span">DOC</span></a></li>
					<li class="left-li"><a class="format"><span class="black-span">PPT</span></a></li>
					<li class="left-li"><a class="format"><span class="black-span">XLS</span></a></li>
				</ul>
			</div>
    	</div>
    		<c:forEach var="file" items="${list}">
		    	<div  class="bor">
					<h3>
						<img src="<%=path%>/statics/image/${file.type}.png">
						<a href="preview?fileID=${file.fileId}" class="title">${file.fileName}</a>
						<ul class="like-share">
		                    <li class="like" >收藏</li>
		                    <li class="share">分享</li>
						</ul>
					</h3>
					<div class="material">
						<a href="preview?fileID=${file.fileId}" ><img alt="图片已损坏或被移向其他地方" style="height: 80px;width: 93px" src='/scushare/fileData/${file.fileCollege}/${file.image}/0.jpg'></a>
					<div class="info">
							<div class="desc">
								${file.fileIntro}
							</div>
							<div class="intro">
								<span>上传时间：</span>
								<span>${file.upTime}</span>
								<span class="margin">|</span>
								<span>上传者：</span>
								<span>${file.userName}</span>
								<span class="margin">|</span>
								<span>浏览：</span>
								<span>5次</span>
								<span class="margin">|</span>
								<span>星级：</span>
								<span></span>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		
		<div id="change-page">
			<ul>
				<c:if test="${pageNum>1}">
					<li class="last-page" id="last-page"><span class="black-span">上一页</span></li>
				</c:if>
				
				<c:forEach var = "i" begin="1" end="${pages}" step="1">
					<li class="page" id="page${i}"><span class="black-span" id="span${i}">${i}</span></li>
				</c:forEach>
				
				<c:if test="${pages > pageNum}">
					<li class="next-page" id="next-page"><span class="black-span">下一页</span></li>
				</c:if>
			</ul>
		</div>
    </div>
    <div id="mission">
		<button>发布找文档任务</button>
		<p>
			找不到需要的文档?<br/>
			<span>提交需求</span>，请网友为你提供文档
		</p>
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
	            }) ;    
			$(function(){  
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
	                    } ,    
	                ) ;  
	            }) ;  
			$(function(){  
	                $("#back-text").hover(  
	                    function(){    
	                    } ,    
	                    function(){  
	                         $("#back-text").hide();
	                         $("#back-img").show();
	                    }  
	                ) ;  
	            }) ;  

			//页面切换的鼠标移进移出事件
			$(function(){  
	                $(".page").hover(  
	                    function(){    
	                    	document.getElementById(this.id).style.border = "1px solid #000000";
	                    } ,    
	                    function(){  
	                        document.getElementById(this.id).style.border = "1px solid #F8F8F8";
	                    }  
	                ) ;  
	            }) ; 
            $(function(){  
                $(".next-page").hover(  
                    function(){    
                    	this.style.border = "1px solid #000000";
                    } ,    
                    function(){  
                        this.style.border = "1px solid #F8F8F8";
                    }  
                ) ;  
            }) ;  

            //鼠标悬停推荐文档产生下划线事件
            $(function(){  
	                $(".underline").hover(  
	                    function(){    
	                    	this.style.color = "blue";
	                    	this.style.textDecoration = "underline";
	                    } ,    
	                    function(){  
	                    	this.style.color = "#000000";
	                        this.style.textDecoration = "none";
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
						window.location.href = "document?search=" + search;
					}
	
			}); 
			
			function getArgs(href,arg){
				href = decodeURI(href);
				if(href.indexOf("#")==(href.length-1))
					href = href.slice(0,href.length-1);
				if(href.indexOf("?")==(href.length-1))
					href = href.slice(0,href.length-1);
				if(href.indexOf("?") >=0){
					var right = href.split("?")[1];
					for(i in right.split("&")){
						if(right.split("&")[i].indexOf(arg) >= 0)
							return(right.split("&")[i].split("=")[1]);	
					}
				}
					
			}
			function remove(href){
				var a = href;
				for(var i = 0;i<a.length;i++){
					if(a.slice(-1) == "&" || a.slice(-1) == "?")
						a = a.slice(0,a.length-1);
					else
						return a;
				}
			
			}
			//   college|major/type/page
			$(".left-li").bind("click",function(){
				//<li class="left-li"><a class="all"><span class="black-span">全部格式</span></a></li>
				var value = this.children[0].children[0].innerText;
				if(value == "全部格式")
					value = "";
				else
					value = value.toLowerCase();
				var href = window.location.href;
				var college = getArgs(href,"college");
				var major = getArgs(href,"major");
				var page = getArgs(href,"page");
				var type = getArgs(href,"type");
				var link = "document?";
				if(major != null)
					link += "major="+major+"&";
				if(college != null)
					link += "college="+college+"&";
				link += "type="+value;
				window.location.href = link;
			});
			$(".page").bind("click",function(){
				var page = document.getElementById(this.id).children[0].innerText;
				href = window.location.href;
				for(var i = 0;i<href.length;i++){
					if(href.slice(-1) == "&" || href.slice(-1) == "?")
						href = href.slice(0,href.length-1);
				}
				//不包含页面参数
				if(window.location.href.indexOf("page")<0){
					
					
					if(window.location.href.indexOf("?")<0)
						window.location.href = window.location.href + "?page=" + page;
					else if(window.location.href.slice(-1)!="?")
						window.location.href = window.location.href + "&page=" + page;
					else
						window.location.href = window.location.href + "page=" + page;
				}	
				//包含页面参数
				else{
					if(href.indexOf("&") == (href.length-1) || href.indexOf("?") == (href.length-1))
						href = href.slice(0,href.length-1);
					var num = href.indexOf("page");
					var left = href.slice(0,num);
					if(left.indexOf("?")<0)
						window.location.href = left + "?page=" + page;
					else if(window.location.href.slice(-1)!="?")
						window.location.href = left + "&page=" + page;
					else
						window.location.href = left + "page=" + page;
				}	
			});
    </script>
	<script type="text/javascript">
	//返回访问文档的层级
	function judgeType(){
		var href = window.location.href;
		if(href.indexOf("#") == (href.length-1))
			href = href.slice(0,href.length-2);
		//没有参数
		if(href.indexOf("?")>=0 && href.indexOf("major")<0 && href.indexOf("college")<0)
			return "所有文档";
		if(href.indexOf("?") < 0 || href.indexOf("?")==(href.length-1)){
			return "所有文档";
		}
		//有参数
		else{
			string = href.split("?")[1];
			//只有一个参数
			if(string.indexOf("&")<0){
				return string;
			}else{
				//有多个参数
				params = string.split("&");
				for(var i = 0;i<params.length;i++){
					if(params[i].indexOf("college")==0)
						return params[i];
					if(params[i].indexOf("major")==0)
						return params[i];
				}
			}
		}
	}
	function createItem(string){
		//<li><a href="#" ><span class="black-span">资料分类</span></a></li>
		var ul = document.getElementById("ul");
		if(string == "所有文档"){
			document.getElementById("current-location").href = "document";
			document.getElementById("real-location").innerText = "所有文档";
			var colleges = getAllColleges();
			for(var i = 0;i<colleges.length;i++){
				var li = document.createElement("li");
				var a = document.createElement("a");
				var span = document.createElement("span");
				span.className = "black-span";
				span.innerText = colleges[i];
				a.appendChild(span);
				a.href = "document?college="+colleges[i];
				li.appendChild(a);
				ul.appendChild(li);
			}
			return;
		}
		var type = string.split("=")[0];
		var value = string.split("=")[1];
		//如果加载学院页面
		if(type=="college"){
			document.getElementById("current-location").href = "document";
			document.getElementById("real-location").innerText = "所有文档";
			var span = document.createElement("span");
			span.innerText = " > ";
			document.getElementById("current-location").appendChild(span);
			var a = document.createElement("a");
			a.href = "document?college="+value;
			var span2 = document.createElement("span");
			span2.innerText = value;
			span2.className= "blue-span";
			a.appendChild(span2);
			document.getElementById("location-div").appendChild(a);
			var majors = getMajorsByCollege(value);
			for(var i = 0;i<majors.length;i++){
				var li = document.createElement("li");
				var a = document.createElement("a");
				var span = document.createElement("span");
				span.className = "black-span";
				span.innerText = majors[i];
				a.appendChild(span);
				a.href = "document?major="+majors[i];
				li.appendChild(a);
				ul.appendChild(li);
			}
		}else if(type=="major"){
		//如果加载专业页面	
			var college = getCollegeByMajor(value);	
			document.getElementById("current-location").href = "document";
			document.getElementById("real-location").innerText = "所有文档";
			var span = document.createElement("span");
			span.innerText = " > ";
			document.getElementById("current-location").appendChild(span);
			var a = document.createElement("a");
			a.id="college";
			a.href = "document?college="+college;
			var span2 = document.createElement("span");
			span2.innerText = college;
			span2.className= "blue-span";
			a.appendChild(span2);
			document.getElementById("location-div").appendChild(a);
			
			var span = document.createElement("span");
			span.innerText = " > ";
			document.getElementById("college").appendChild(span);
			var a = document.createElement("a");
			a.href = "document?major="+value;
			var span3 = document.createElement("span");
			span3.innerText = value;
			span3.className= "blue-span";
			a.appendChild(span3);
			document.getElementById("location-div").appendChild(a);
			
		
		
			
			var majors = getMajorsByCollege(college);
			for(var i = 0;i<majors.length;i++){
				var li = document.createElement("li");
				var a = document.createElement("a");
				var span = document.createElement("span");
				if(value == majors[i])
					span.className = "deep-blue-span";
				else
					span.className = "black-span";
				span.innerText = majors[i];
				a.appendChild(span);
				a.href = "document?major="+majors[i];
				li.appendChild(a);
				ul.appendChild(li);
			}
		}
	}
	var type = decodeURI(judgeType());
	createItem(type);
	</script>
</body>
</html>
<% out.flush();%>