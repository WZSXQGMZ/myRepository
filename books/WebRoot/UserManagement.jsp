<%@page import="java.io.PrintWriter"%>
<%@ page language="java" import="java.util.*,com.books.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'UserManagement.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="css/style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
    
	<style type="text/css">
	body {
		background:#FFF
	}
	</style>

	<script type="text/javascript">
	function suredel(){
		return window.confirm("你确实要删除该数据吗?");
	}
	</script>
  </head>
  
  <body>
    <%
  	int pageCount=0;
  	int pageNow=1;
  	
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
	      <th width="70">编号</th>
	      <th width="100">用户名</th>
	      <th width="100">密码</th>
	      <th width="100">姓名</th>
	      <th width="100">性别</th>
	      <th width="70">年龄</th>
	      <th width="100">职业</th>
	      <th width="188">电话</th>
	      <th width="188">邮箱</th>
	      <th width="188">地址</th>
	      <th width="70">信誉度</th>
	      <th width="188">操作</th>
	      <td width="16"></thead>
	    <tbody>
	      <%
	      	ArrayList<UserBean> list = (ArrayList)request.getAttribute("list");
	      	for (int i=0; i<list.size(); i++){
	      		UserBean ub = (UserBean)list.get(i);
	      %>
	    	<tr>
		        <td><%=ub.getUsrid() %></td>
		        <td><%=ub.getUsraccount() %></td>
		        <td><%=ub.getUsrpassword() %></td>
		        <td><%=ub.getUsrname() %></td>
		        <td><%=ub.getUsrsex()%></td>
		        <td><%=ub.getUsrage() %></td>
		        <td><%=ub.getVocation() %></td>
		        <td><%=ub.getUsrtel() %></td>
		        <td><%=ub.getEmail() %></td>
		        <td><%=ub.getAddress() %></td>
		        <td><%=ub.getUsrcredit() %></td>
		        <td><a href="UserModifyServlet?pageNow=<%=pageNow%>&usrid=<%=ub.getUsrid()%>">[修改]</a>&nbsp;
		        <a href="UserClServlet?flag=del&pageNow=<%=pageNow%>&userid=<%=ub.getUsrid()%>" onclick="return suredel()">[删除]</a></td>
	      	</tr>
		<%	} %>
	    </tbody>
	  </table>
	  	
	<%
	    out.println("<a href='UserClServlet?pageNow=1'>首页</a>");
	    if(pageNow!=1){
	    	out.println("<a href='UserClServlet?pageNow="+(pageNow-1)+"'>上一页</a>");
	    }
	    for(int i=pageNow;i<pageNow+10;i++){
	    	if(i<=pageCount){
	    		out.println("<a href='UserClServlet?pageNow="+i+"'>["+i+"]</a>");
	    	}
	    }
	    if(pageNow!=pageCount){
	    	out.println("<a href='UserClServlet?pageNow="+(pageNow+1)+"'>下一页</a>");
	    }
	    out.println("<a href='UserClServlet?pageNow="+pageCount+"'>末页</a>");
    %>
	</div></div>
  </body>
</html>
