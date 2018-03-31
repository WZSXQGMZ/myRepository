<%@ page language="java" import="java.util.*,com.books.model.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'BookReturn.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
	<style type="text/css">
	body {
		background:#FFF
	}
	</style>

  </head>
  
  <body>
  <%
  String usrid = (String)request.getSession().getAttribute("userid");
  %>
    <div id="contentWrap">
	<div class="pageColumn">
	<div class="pageButton"></div>
	  <table>
	    <thead>
	      <th width="188">借阅编号</th>
	      <th width="188">书籍名称</th>
	      <th width="188">借阅日期</th>
	      <th width="188">应该归还日期</th>
	      <th width="188">实际归还日期</th>
	      <th width="188">是否归还</th>
	      <th width="188">罚款</th>
	      <th width="188">操作</th>
	      <td width="16"></thead>
	    <tbody>
	      <%
	        ArrayList<BookReturn> list = (ArrayList<BookReturn>)request.getAttribute("list");
	      	String hasReturn = "";
	      	String returnDate = "";
	      	for (int i=0; i<list.size(); i++){
	      		BookReturn br = (BookReturn)list.get(i);
	      		if (br.getReturndate()==null){
	      			returnDate = "无";
	      			hasReturn = "否";
	      		} else{
	      			returnDate = br.getReturndate();
	      			hasReturn = "是";
	      		}
	      %>
	      <tr>
	        <td><%=br.getBorrowid() %></td>
	        <td><%=br.getBookname() %></td>
	        <td><%=br.getBorrowdate() %></td>
	        <td><%=br.getShouldreturn() %></td>
	        <td><%=returnDate %></td>
	        <td><%=hasReturn %></td>
	        <td><%=br.getFine() %></td>
	        <td>
	      <%
	      	if (hasReturn.equals("否")){
	      %>
	        <a href="BorrowReturnServlet?flag=reborrow&borrowid=<%=br.getBorrowid()%>&shouldreturn=<%=br.getShouldreturn()%>">[续借]</a>&nbsp;
	        <a href="BorrowReturnServlet?flag=return&usrid=<%=usrid %>&ISBN=<%=br.getISBN() %>&bookid=<%=br.getBookid() %>&borrowid=<%=br.getBorrowid()%>&shouldreturn=<%=br.getShouldreturn()%>">[归还]</a>
	      <% } %>
	        </td>
	      </tr>
		<%	} %>
	    </tbody>
	  </table>
	</div></div>
  </body>
</html>
