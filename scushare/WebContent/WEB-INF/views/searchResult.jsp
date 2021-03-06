<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.apache.taglibs.standard.tag.common.xml.ForEachTag"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%  String path = request.getContextPath(); 
		String user_name = (String)request.getSession().getAttribute("user_name");
		String user_chathead = (String)request.getSession().getAttribute("user_chathead");
		if(user_chathead == null || user_chathead.equals("")){
			user_chathead = "/userChathead/defaultChathead.jpg";
		}
	%>
	<link rel="shortcut icon" href="<%=path%>/statics/image/shareico.ico" type="image/x-icon"> 
    <script src="<%=path%>/statics/js/jquery-3.2.1.slim.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=path%>/statics/css/searchResult.css">
	<title>搜索结果</title>
</head>
<body>
	<div id="navbar">
	 		<div id="nav-left">
		  		<a href="#" >
		  			<img id ="logo" src="<%=path%>/statics/image/sharelogo.png" >
		  		</a>
	  		</div>

	  		<div id="catalog">
				<ul>
					<li >
						<a class="nav-link" href="index"><span class="white-span">首页</span></a>
					</li>
					<li id="docu">
						<a class="nav-link dropdown-toggle" href="document" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<span class="white-span">文档</span>
						</a>
			      	</li>
					<li >
						<a class="nav-link" href="#"><span class="white-span">任务</span></a>
					</li>
			    </ul>	
			</div>
			<%if(user_name == null){ %>
				<div id="user">
					<a href="loginPage"><span class="white-span">登录</span></a>
					<span class="white-span">|</span>
					<a href="registerPage"><span class="white-span">注册</span></a>
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
		

		<div id="content">
			<div id=main-content>
				<div id="catalogue">
					<a href="#"><span class="black-span">文档</span></a>
					<a href="#"><span class="black-span">任务</span></a>
					<span id="search-result">
						<span class="black-span">约有</span>
						<span id="search-result-num" class="red-span">${pageTotalNum}</span>
						<span class="black-span">篇，以下是第</span>
						<span id="search-result-order" class="red-span">${pageMin} - ${pageMax}</span>
						<span class="black-span">篇</span>
					</span>
				</div>

				<div id="select-format">
					<form id="filter">
						筛选：
						<input class="fileType" type="radio"  name="format" value=""/><span>全部格式</span>
						<input class="fileType" type="radio"  name="format" value="pdf"/><span>PDF</span>
						<input class="fileType" type="radio"  name="format" value="doc"/><span>DOC</span>
						<input class="fileType" type="radio"  name="format" value="ppt"/><span>PPT</span>
						<input class="fileType" type="radio"  name="format" value="xls"/><span>XLS</span>
					</form>
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
						<a href="preview?fileID=${file.fileId}" ><img style="height: 80px;width: 93px" src='/scushare/fileData/${file.fileCollege}/${file.image}/0.jpg'></a>
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
				<div id="help">
					<span class="black-span">如果对搜索结果不满意，您可以尝试让更多人帮助您！</span>
					<a href="#">发布任务</a>
				</div>	

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
			<div id="recommend">
				<div id="mission">
					<button>发布找文档任务</button>
					<p>
						找不到需要的文档?<br/>
						<span>提交需求</span>，请网友为你提供文档
					</p>
				</div>
				<div id="recommend-result">
					<p>
						推荐文档
					</p>
					<ul id="file-info">
						<c:forEach items ="${recommendList}" var="file">
							<li class="file">
								<a href="preview?fileID=${file.fileId}" title="${file.fileName}">
									<img src="<%=path%>/statics/image/${file.type}.png">
									<span  class="black-span">
										<span class="underline">${file.fileName}</span><br/>
										星级：
										<img class="star" src="<%=path%>/statics/image/5.bmp"></span>
										<span id="page-num">${file.pages}</span>页
									</span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		<!-- <div id="down-list" >
			<ul>
         		<li><a href="#" ><span class="white-span">软件学院</span></a></li>      
         		<li><a href="#" ><span class="white-span">计算机学院</span></a></li>    
         		<li><a href="#" ><span class="white-span">经济学院</span></a></li>    
         		<li><a href="#" ><span class="white-span">法学院</span></a></li>
         		<li><a href="#" ><span class="white-span">文学与新闻学院</span></a></li>
         		<li><a href="#" ><span class="white-span">外国语学院</span></a></li>
         		<li><a href="#" ><span class="white-span">艺术学院</span></a></li>
         		<li><a href="#" ><span class="white-span">历史文化学院(旅游学院)</span></a></li>
         		<li><a href="#" ><span class="white-span">数学学院</span></a></li>
         		<li><a href="#" ><span class="white-span">物理科学与技术学院</span></a></li>
         		<li><a href="#" ><span class="white-span">化学学院</span></a></li>
         		<li><a href="#" ><span class="white-span">生命科学学院</span></a></li>
         		<li><a href="#" ><span class="white-span">电子信息学院</span></a></li>
         		<li><a href="#" ><span class="white-span">材料科学与工程学院</span></a></li>
         		<li><a href="#" ><span class="white-span">制造科学与工程学院</span></a></li>
         		<li><a href="#" ><span class="white-span">电气信息学院</span></a></li>
         		<li><a href="#" ><span class="white-span">建筑与环境学院</span></a></li>
         		<li><a href="#" ><span class="white-span">水利水电学院</span></a></li>
         		<li><a href="#" ><span class="white-span">化学工程学院</span></a></li>
         		<li><a href="#" ><span class="white-span">轻纺与食品学院</span></a></li>
         		<li><a href="#" ><span class="white-span">高分子科学与工程学院</span></a></li>
         		<li><a href="#" ><span class="white-span">华西基础医学与法医学院</span></a></li>
         		<li><a href="#" ><span class="white-span">华西临床医学院</span></a></li>
         		<li><a href="#" ><span class="white-span">华西口腔医学院</span></a></li>
         		<li><a href="#" ><span class="white-span">华西公共卫生学院</span></a></li>
         		<li><a href="#" ><span class="white-span">华西药学院</span></a></li>
         		<li><a href="#" ><span class="white-span">公共管理学院</span></a></li>
         		<li><a href="#" ><span class="white-span">商学院</span></a></li>
         		<li><a href="#" ><span class="white-span">马克思主义学院</span></a></li>
         		<li><a href="#" ><span class="white-span">体育学院</span></a></li>
         		<li><a href="#" ><span class="white-span">灾后与重建学院</span></a></li>
         		<li><a href="#" ><span class="white-span">空天科学与工程学院</span></a></li>
         		<li><a href="#" ><span class="white-span">匹兹堡学院</span></a></li>
         		<li><a href="#" ><span class="white-span">国际关系学院</span></a></li>
         		<li><a href="#" ><span class="white-span">网络空间安全学院</span></a></li>

	        </ul>
		</div>
		 -->

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
	                        document.getElementById(this.id).style.border = "1px solid #EEEEEE";
	                    }  
	                );  
	            }) ; 
			
			$(".page").bind("click",function(){
				var page = document.getElementById(this.id).children[0].innerText;
				//不包含页面参数
				if(window.location.href.indexOf("page")<0)
					window.location.href = window.location.href + "&page=" + page;
				//包含页面参数
				else{
					href = window.location.href;
					var num = href.indexOf("page");
					var left = href.slice(0,num-1);
					window.location.href = left + "&page="+page;
				}		
			});
			
			$("#last-page").bind("click",function(){
				href = window.location.href;
				var num = href.indexOf("page");
				var left = href.slice(0,num-1);
				console.log(left);
				var right = href.slice(num,href.length);
				console.log(right);
				var current_page = right.split("=")[1];
				console.log(current_page);
				page = parseInt(current_page)-1;
				console.log(page);
				window.location.href = left + "&page="+page;
			});
			
			$("#next-page").bind("click",function(){
				//不包含page参数又有下一页显示则说明为首页，下一页则为2页
				if(window.location.href.indexOf("page")<0)
					window.location.href = window.location.href + "&page=" + "2";
				//包含页面参数
				else{
					href = window.location.href;
					var num = href.indexOf("page");
					var left = href.slice(0,num-1);
					console.log(left);
					var right = href.slice(num,href.length);
					console.log(right);
					var current_page = right.split("=")[1];
					console.log(current_page);
					page = parseInt(current_page)+1;
					console.log(page);
					window.location.href = left + "&page="+page;
				}	
			});
			
            $(function(){  
                $(".next-page").hover(  
                    function(){    
                    	this.style.border = "1px solid #000000";
                    } ,    
                    function(){  
                        this.style.border = "1px solid #EEEEEE";
                    }  
                ) ;  
                $(".last-page").hover(  
                        function(){    
                        	this.style.border = "1px solid #000000";
                        } ,    
                        function(){  
                            this.style.border = "1px solid #EEEEEE";
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
			
			//搜索文档事件
			$("#submit").bind("click",function(){
					var search = document.getElementById("input-tag").value;
					if(search != ""){
						event.preventDefault();
						window.location.href = "searchResult?search=" + search;
					}
	
			});
			
			//添加文档筛选功能
			$(".fileType").bind("click",function(){
				var value = this.value;	
				var params = window.location.href.split("?")[1];
				if(value == "all"){
					var search = decodeURI(params).split("=")[1];
					window.location.href = "searchResult?search="+search;
				}
				//判断参数包含类型
				//只有一个keyword参数
				if(params.indexOf("&")<0){
					var search = decodeURI(params).split("=")[1];
					window.location.href = "searchResult?search="+search+"&type="+value;
				}else{
					//有keyword和type两个参数或者有keyword,type,page三个参数
					var leftUrl = decodeURI(window.location.href.split("&")[0]);
					window.location.href = leftUrl + "&type=" + value;
				}	
	  			
			});
			$(document).ready(function(){
				var filters = document.getElementsByClassName("fileType");
				if(window.location.href.indexOf("type") < 0){
					filters[0].checked="checked";
					return;
				}
				var params = window.location.href.split("?");
				if(params.length > 1 && params.indexOf("?")!=(params-1)){
					params = decodeURI(params[1]);
					args = params.split("&");
					for(arg in args){
						if(args[arg].indexOf("type")>=0){
							var value = args[arg].split("=")[1].toLowerCase();
						}
					}
				}
				for(var i = 0;i<filters.length;i++){
					if(filters[i].value==value)
						filters[i].checked="checked";
					else
						filters[i].checked="";
				}	
			});
			
		</script>

		<script type="text/javascript">
			
		</script>
</body>
</html>
<% out.flush();%>