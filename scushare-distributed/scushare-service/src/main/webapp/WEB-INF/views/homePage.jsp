<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie"%> 
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		out.println("session: " + (String)request.getSession().getAttribute("user_name"));
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie:cookies){
			out.println("localCookie: " + cookie.getName() + ", " + cookie.getValue());	
		}
	%>
	<form action="doLogout">
		<input type="submit" value="注销" />
	</form>
</body>
</html>