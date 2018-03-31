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
		body {
			background:#FFF;
		}
	</style>

  </head> 
  <body>
  <%
  	int pageCount=0;
  	int pageNow=1;
  	ArrayList<bookbean> list=(ArrayList)request.getAttribute("list");
  	String s_pageCount=(String)request.getAttribute("pageCount");
  	if(s_pageCount!=null){
  		pageCount=Integer.parseInt(s_pageCount);
  	}
  	String s_pageNow=(String)request.getAttribute("pageNow");
  	if(s_pageNow!=null){
  		pageNow=Integer.parseInt(s_pageNow);
  	}
  %>
	<div id="contentWrap">
	<div class="pageColumn">
	<div class="pageButton"></div>
	  <table>
    <thead>
      <th width="180">ISBN</th>
      <th width="180">书名</th>
      <th width="180">作者</th>
      <th width="180">出版社</th>
      <th width="180">书架</th>
      <th width="180">类型</th>
      <th width="180">总量</th>
      <th width="180">可用</th>
      <th width="180">操作</th>
        <td width="16"></thead>
    <tbody>
    <%try{
    for(int i=0;i<list.size();i++){
    	bookbean ub=(bookbean)list.get(i);
    %>
      <tr class="mytr">
        <td><%=ub.getISBN() %></td>
        <td><%=ub.getBookname() %></td>
        <td><%=ub.getAuthor() %></td>
        <td><%=ub.getPress() %></td>
        <td><%=ub.getShelfid() %></td>
        <td><%=ub.getBooktype() %></td>
        <td><%=ub.getTotal() %></td>
        <td><%=ub.getAvaliable() %></td>
        <td><a href="change.jsp?pageNow=<%=pageNow%>&ccISBN=<%=ub.getISBN()%>
        &ccBookname=<%=ub.getBookname()%>&ccAuthor=<%=ub.getAuthor()%>
        &ccPress=<%=ub.getPress()%>&ccShelfid=<%=ub.getShelfid()%>
        &ccBooktype=<%=ub.getBooktype()%>&ccTotal=<%=ub.getTotal()%>
        &ccAvaliable=<%=ub.getAvaliable()%>">[修改]</a>
        &nbsp;<a href="DLServlet?pageNow=<%=pageNow%>&ISBN=<%=ub.getISBN()%>" >[删除]</a></td>
        
      </tr>
    <%}}catch(Exception e){e.printStackTrace();} %>  
    </tbody>
    
  </table>
  <%
    out.println("<a href='BICLServlet?pageNow=1'>首页</a>");
    if(pageNow!=1){
    	out.println("<a href='BICLServlet?pageNow="+(pageNow-1)+"'>上一页</a>");
    }
    for(int i=pageNow;i<pageNow+10;i++){
    	if(i<=pageCount){
    		out.println("<a href='BICLServlet?pageNow="+i+"'>["+i+"]</a>");
    	}
    }
    if(pageNow!=pageCount){
    	out.println("<a href='BICLServlet?pageNow="+(pageNow+1)+"'>下一页</a>");
    }
    out.println("<a href='BICLServlet?pageNow="+pageCount+"'>末页</a>");
    %>
</div></div>
  </body>
</html>

