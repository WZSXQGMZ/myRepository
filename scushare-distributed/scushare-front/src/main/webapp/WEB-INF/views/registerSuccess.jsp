<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册成功</title>
</head>
<body>
<h1>注册成功，请前往邮箱访问连接以完成账号激活</h1>
系统将在 <span id="time">5</span> 秒钟后自动跳转至登陆页面，如果未能跳转，请点击<a href="http://localhost/scushare/loginPage" title="点击访问">这里</a>
<%  String path = request.getContextPath(); %>
<script type="text/javascript">  
    delayURL();    
    function delayURL() { 
        var delay = document.getElementById("time").innerHTML;
 		var t = setTimeout("delayURL()", 1000);
        if (delay > 0) {
            delay--;
            document.getElementById("time").innerHTML = delay;
        } else {
     clearTimeout(t); 
            window.location.href = "<%=path%>/scushare/loginPage";
        }        
    } 
</script>
</body>
</html>