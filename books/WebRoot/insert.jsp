<%@ page language="java" import="java.util.*,com.books.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
    
	<style type="text/css">
	input{
		text-align:center;
	}
	body {
		background:#FFF;
	}
	</style>
  </head>
  
  <body>
     <div id="contentWrap">
     <div class="pageTitle"></div>
     <div class="pageColumn">
     <div class="pageButton"></div>
    <form action="InsertServlet" method="post"><div class="content">
 <table>
           <thead>
        	   <th width="90">ISBN</th>
  			   <th width="90">书名</th>
   			   <th width="90">作者</th>
   			   <th width="90">出版社</th>
   			   <th width="90">书架</th>
  			   <th width="90">类型</th>
  	    	   <th width="90">总数</th>
   			   <th width="90">可用</th>
   			   <th width="90">操作</th>
           </thead>
           <tbody>
    	      <tr>
        <td><input id="cISBN" name="cISBN" type="text"></td>
        <td><input id="cName" name="cName" type="text"></td>
        <td><input id="cAuthor" name="cAuthor" type="text"></td>
        <td><input id="cPress" name="cPress" type="text"></td>
        <td><input id="cShelt" name="cShelt" type="text"></td>
        <td><input id="cKind" name="cKind" type="text"></td>
        <td><input id="cAc" name="cAc" type="text"></td>
        <td><input id="cAv" name="cAv" type="text"></td>
        <td class="btn" align="center"><input name="" type="submit" class="login-btn" value="确认" /></td>
        </tr></tbody></table></div>
   </form></div></div>
  </body>
</html>
