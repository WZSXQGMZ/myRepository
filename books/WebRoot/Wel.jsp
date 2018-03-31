<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Wel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
    
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
	body {
		background:#FFF
	}
	</style>
	

  </head>

  <body>
    <div id="contentWrap">
	<div class="pageTitle">你好，<%=request.getSession().getAttribute("username") %>！欢迎使用管理系统！</div>
	<div class="pageColumn">
	<div class="pageButton"></div>
	  <table>
	    <thead>
	    <th width="23"><input name="" type="checkbox" value="" /></th>
	    <th width="60">编号</th>
	      <th width="188">用户</th>
	      <th width="188">性别</th>
	      <th width="188">年龄</th>
	      <th width="188">电话</th>
	      <th width="188">职业</th>
	      <th width="394">住址</th>
	        <td width="16"></thead>
	    <tbody>
	    	<tr>
	        <td class="checkBox"><input name="" type="checkbox" value="" /></td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	        <td>11</td>
	      </tr>
	    </tbody>
	  </table>

	</div></div>
  </body>
</html>
