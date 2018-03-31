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
  	ArrayList<managerbean> list=(ArrayList)request.getAttribute("list");
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
      <th width="60">编号</th>
      <th width="188">用户</th>
      <th width="394">电话</th>
      <th width="394">邮箱</th>
        <td width="16"></thead>
    <tbody>
    <%
    for(int i=0;i<list.size();i++){
    	managerbean ub=(managerbean)list.get(i);
    %>
      <tr>
        <td><%=ub.getManagerid() %></td>
        <td><%=ub.getManagername() %></td>
        <td><%=ub.getManagertel() %></td>
        <td><%=ub.getEmail() %></td>
      </tr>
    <%} %>  
    </tbody>
    
  </table>
  <%
    out.println("<a href='MICLServlet?flag=divpage&pageNow=1'>首页</a>");
    if(pageNow!=1){
    	out.println("<a href='MICLServlet?flag=divpage&pageNow="+(pageNow-1)+"'>上一页</a>");
    }
    for(int i=pageNow;i<pageNow+10;i++){
    	if(i<=pageCount){
    		out.println("<a href='MICLServlet?flag=divpage&pageNow="+i+"'>["+i+"]</a>");
    	}
    }
    if(pageNow!=pageCount){
    	out.println("<a href='MICLServlet?flag=divpage&pageNow="+(pageNow+1)+"'>下一页</a>");
    }
    out.println("<a href='MICLServlet?flag=divpage&pageNow="+pageCount+"'>末页</a>");
    %>
</div></div>
  </body>
</html>

