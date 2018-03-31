<%@page import="com.books.model.Tools"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.books.model.bookbean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'booksQuery.jsp' starting page</title>
    
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
		background:#FFF
	}
	</style>
  </head>
  
  <body>
  
  <%
  	PrintWriter pw = response.getWriter();
  	String queryResult = request.getParameter("queryResult");
  	String booksQuery = (String)request.getAttribute("booksQuery");
  	String booksQuery_ISO = "";
  	if(booksQuery != null) {
  		booksQuery_ISO = new String(booksQuery.getBytes("utf-8"), "ISO-8859-1");
  	} else {
  		booksQuery = "";
  	}

  	
  	//String booksQuery = request.getParameter("booksQuery");
  	
  	String queryRange = request.getParameter("queryRange");
	String usrid = request.getParameter("userid");
  	String borrowResult = request.getParameter("borrowResult");
  	if(borrowResult == null) {
  		//pw.print("<script>alert('借阅出错')</script>");
  	} else if(borrowResult.equals("creditLimit")){
  		pw.print("<script>alert('已达可借书数量上限')</script>");
  	} else if(borrowResult.equals("noBooksRemain")){
  		pw.print("<script>alert('没有剩余书籍')</script>");
  	} else if(borrowResult.equals("borrowFail")){
  		pw.print("<script>alert('借阅出错')</script>");
  	} else if(borrowResult.equals("")){
  		pw.print("<script>alert('借阅出错')</script>");
  	} else {
  		pw.print("<script>alert('借阅成功，书号：" + borrowResult + "')</script>");
  	}
  	int pageNow = 1;
  	pageNow = Integer.parseInt(request.getParameter("pageNow"));
  	
  	int pageCount = 0;
  	String s_pageCount = request.getParameter("pageCount");
  	if(s_pageCount != null){
  		pageCount = Integer.parseInt(s_pageCount);
  	}
  %>
  <form name="form_booksquery" action="BooksQueryServlet?pageNow=1&userid=<%=usrid %>" method="post">
  
    <div class="nav-body">
        <div class="body">
            <div class="search-container">
                <div class="search">
                    <div class="search-hd">
                        <input id="txtSearchKey" name="booksQuery" type="text" placeholder="请输入关键字" value=<%=booksQuery %>>
                        <button id="btnSearch"><i class="iconfont icon-iconsearch2"></i></button>
                    </div>
                    <!-- 
	                <select name="queryRange">
				    	<option value="bookname">书名</option>
				    	<option value="isbn">ISBN</option>
				    	<option value="author">作者</option>
				    	<option value="publisher">出版社</option>
				    	<option value="booktype">类型</option>
				    </select>
				     -->
                </div>
            </div>
            <label><input name="queryRange" type="radio" value="bookname" checked/>书名&nbsp;&nbsp;</label> 
            <label><input name="queryRange" type="radio" value="isbn" />ISBN&nbsp;&nbsp;</label> 
            <label><input name="queryRange" type="radio" value="author" />作者&nbsp;&nbsp;</label> 
            <label><input name="queryRange" type="radio" value="publisher" />出版社&nbsp;&nbsp;</label> 
            <label><input name="queryRange" type="radio" value="booktype" />类型&nbsp;&nbsp;</label> 
        </div>
    </div>
  

  </form>
 <div id="contentWrap">
	<div class="pageColumn">
	<div class="pageButton"></div>
  <table>
	  <thead>
	      <th width="300">书名</th>
	      <th width="100">作者</th>
	      <th width="300">出版社</th>
	      <th width="200">所在书架编号</th>
	      <th width="150">类型</th>
	      <th width="300">ISBN</th>
	      <th width="120">借阅</th>
	  </thead>
	  <tbody>
  <%
  	if(queryResult.equals("none")) {
  		pw.print("<script>alert('未查询到书籍') </script>");
  	} else if(queryResult.equals("success")) {
  	  	ArrayList<bookbean> bookList = (ArrayList<bookbean>)request.getAttribute("bookList");
  	  	if(bookList == null || bookList.size() == 0) {
  	  		pw.print("<script>alert('未查询到书籍') </script>");
  	  		return;
  	  	}
  		//打印书籍信息
  		int booksCount = bookList.size();
  		for(int i = 0; i < booksCount; i++) {
			bookbean bookBean = (bookbean)bookList.get(i);
  %>
  	<tr class="userBeanRow" >
  	<td><%=bookBean.getBookname() %></td>
  	<td><%=bookBean.getAuthor() %></td>
  	<td><%=bookBean.getPress() %></td>
  	<td><%=bookBean.getShelfid() %></td>
  	<td><%=bookBean.getBooktype() %></td>
  	<td><%=bookBean.getISBN() %></td>
  	<td><form action="BooksBorrowSevlet?userid=<%=usrid %>&booksQuery=<%=booksQuery_ISO %>&queryRange=<%=queryRange %>&pageNow=<%=String.valueOf(pageNow) %>&isbn=<%=bookBean.getISBN() %>" method="post"><input type="submit" value="借阅" /></form></td>
  	</tr>
  	
  	<%
  		}
  	
  	%>
  	</tbody>
  </table>
   </div></div>
  <%
    	String style = " style=\"color:#00F;\"";
    	//首页
    	out.print("<a href='BooksQueryServlet?queryRange=" + queryRange + "&booksQuery=" + booksQuery_ISO + "&pageNow=1'" + style + ">首页&nbsp;</a>");
    	//上一页
    	if(pageNow == 1){
    		out.print("<a href='BooksQueryServlet?queryRange=" + queryRange + "&booksQuery=" + booksQuery_ISO + "&pageNow=1'" + style + ">上一页</a>");
    	} else {
    		out.print("<a href='BooksQueryServlet?queryRange=" + queryRange + "&booksQuery=" + booksQuery_ISO + "&pageNow=" + (pageNow-1) + "'" + style + ">上一页</a>");
    	}
    	
    	//中间页
    	int pageLinkCount = 0;	//中间显示的页面链接
    	if(pageCount < 5){
    		pageLinkCount = pageCount;
    	} else {
    		pageLinkCount = 5;
    	}
    	int j = pageNow - pageLinkCount/2;
    	if(j < 1){
    		j = 1;
    	} else if(j > pageCount-4 && j >= 5) {
    		j = pageCount - 4;
    	}
    	//打印"..."
    	if(j != 1){
    		out.print("..");
    	} else {
    		out.print("&nbsp;");
    	}
    	for(int z = 0; z < pageLinkCount; z++){
    		if(j == pageNow){
    			out.print("[" + j + "]");
    		} else if(j > pageCount){
    			break;
    		} else {
    			out.print("<a href='BooksQueryServlet?queryRange=" + queryRange + "&booksQuery=" + booksQuery_ISO + "&pageNow=" + j + "'" + style + ">[" + j + "]</a>");
    		}
    		j++;
    	}
    	//打印"..."
    	if(j-1 != pageCount){
    		out.print("..");
    	} else {
    		out.print("&nbsp;");
    	}
    	
    	
    	//下一页
    	if(pageNow == pageCount){
    		out.print("<a href='BooksQueryServlet?queryRange=" + queryRange + "&booksQuery=" + booksQuery_ISO + "&pageNow=" + pageCount + "'" + style + ">下一页</a>");
    	} else {
    		out.print("<a href='BooksQueryServlet?queryRange=" + queryRange + "&booksQuery=" + booksQuery_ISO + "&pageNow=" + (pageNow+1) + "'" + style + ">下一页</a>");
    	}
    	//尾页
    	out.print("<a href='BooksQueryServlet?queryRange=" + queryRange + "&booksQuery=" + booksQuery_ISO + "&pageNow=" + pageCount + "'" + style + ">&nbsp;尾页</a>");
   	
  	}
   	%>
 
  </body>
</html>
