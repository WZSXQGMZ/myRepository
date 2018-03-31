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
<script type="text/javascript">
function forwardurl(){
	var url="BICLServlet";
	return this.location.href=url;
}

</script>
  </head>
  <body>
 
    <%
    String pageNow = request.getParameter("pageNow");
    String ccISBN=request.getParameter("ccISBN");
    String ccBookname=request.getParameter("ccBookname");
    String ccAuthor=request.getParameter("ccAuthor");
    String ccPress=request.getParameter("ccPress");
    String ccShelfid=request.getParameter("ccShelfid");
    String ccBooktype=request.getParameter("ccBooktype");
    String ccTotal=request.getParameter("ccTotal");
    String ccAvaliable=request.getParameter("ccAvaliable");
    %>
     <div id="contentWrap">
     <div class="pageTitle"></div>
     <div class="pageColumn">
     <div class="pageButton"></div>
    <form action="CHServlet?pageNow=<%=pageNow %>" method="post"><div class="content">
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
   			   <th width="180">操作</th>
           </thead>
           <tbody>
    	      <tr>
        <td><input id="ccISBN" name="ccISBN" type="text" value=<%=ccISBN %> readonly="readonly" ></td>
        <td><input id="ccBookname" name="ccBookname" type="text" value=<%=ccBookname %> ></td>
        <td><input id="ccAuthor" name="ccAuthor" type="text" value=<%=ccAuthor %>></td>
        <td><input id="ccPress" name="ccPress" type="text" value=<%=ccPress %>></td>
        <td><input id="ccShelfid" name="ccShelfid" type="text" value=<%=ccShelfid %>></td>
        <td><input id="ccBooktype" name="ccBooktype" type="text" value=<%=ccBooktype %>></td>
        <td><input id="ccTotal" name="ccTotal" type="text" value=<%=ccTotal %>></td>
        <td><input id="ccAvaliable" name="ccAvaliable" type="text" value=<%=ccAvaliable %>></td>
        <td class="btn"><input name="" type="submit" class="login-btn" value="确认" />&nbsp;
        <input type="reset" value="取消" onclick="return forwardurl()"></td>
        </tr></tbody></table></div>
   </form></div></div>
  </body>
</html>
