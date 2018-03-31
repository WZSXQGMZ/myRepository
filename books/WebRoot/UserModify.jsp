<%@page import="com.books.model.UserBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'UserModify.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" href="http://ly.b2c2b.org/p/Assets/Dist/default/index.min.css">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1422965688_7705863.css">
    
	<style type="text/css">
	input{
		text-align:center;
	}
	body {
		background:#FFF
	}
	</style>
	<script type="text/javascript">
	function forwardurl(pageNow){
		var url="";
		//alert(pagenow);
		if(typeof(pageNow)!="undefined"){
			//alert(pagenow);
			url="UserClServlet?flag=divpage&pageNow="+pageNow;
		}else{
			url="history.go(-1)";
			//alert(pagenow);
		}
		return this.location.href=url;
	}
	
	</script>

  </head>
  
  <body>
    <%
    String pageNow = (String)request.getAttribute("pageNow");
  	UserBean ub = (UserBean)request.getAttribute("ub");
  	%>
 <div id="contentWrap">
<div class="pageTitle"></div>
<div class="pageColumn">
<div class="pageButton"></div>
<form  method="post" name="form1">
  <table>
    <thead>
      <th width="30">编号</th>
      <th width="100">用户名</th>
      <th width="100">密码</th>
      <th width="100">姓名</th>
      <th width="100">性别</th>
      <th width="50">年龄</th>
      <th width="100">职业</th>
      <th width="188">电话</th>
      <th width="188">邮箱</th>
      <th width="188">地址</th>
      <th width="50">信誉度</th>
      <th width="188">操作</th>
      <td width="16"></thead>
   </thead>
    <tbody>
    	<tr>
        <td><input type="text" value="<%=ub.getUsrid()%>" name="usrid" readonly="readonly" style="background:transparent"></td>
        <td><input type="text" value="<%=ub.getUsraccount()%>" name="usraccount"></td>
        <td><input type="text" value="<%=ub.getUsrpassword()%>" name="usrpassword"></td>
        <td><input type="text" value="<%=ub.getUsrname()%>" name="usrname"></td>
        <td><input type="text" value="<%=ub.getUsrsex()%>" name="usrsex"></td>
        <td><input type="text" value="<%=ub.getUsrage()%>" name="usrage"></td>
        <td><input type="text" value="<%=ub.getVocation()%>" name="vocation"></td>
        <td><input type="text" value="<%=ub.getUsrtel()%>" name="usrtel"></td>
        <td><input type="text" value="<%=ub.getEmail()%>" name="email"></td>
        <td><input type="text" value="<%=ub.getAddress()%>" name="address"></td>
        <td><input type="text" value="<%=ub.getUsrcredit()%>" name="usrcredit"></td>
        <td><input type="submit" value="修改" onclick="document.form1.action='UserClServlet?flag=modify&pageNow=<%=pageNow%>'">&nbsp;<input type="reset" value="取消" onclick="return forwardurl(<%=pageNow%>)"></td>
      </tr>
    </tbody>
  </table>
  </form>
</div></div>
  </body>
</html>
