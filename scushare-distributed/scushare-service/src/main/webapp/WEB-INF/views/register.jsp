<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>用户注册</title>

	<% String originUrl = request.getParameter("ringinUrl");
		if(originUrl == null || originUrl.equals("")){
			originUrl = "";
		}
		%>
    <%  String path = request.getContextPath(); %>
    <!-- BOOTSTRAP STYLES-->
    <link href="<%=path%>/statics/css/bootstrap.css" rel="stylesheet" />
    <!-- FONTAWESOME STYLES-->
    <link href="<%=path%>/statics/css/font-awesome.css" rel="stylesheet" />
    <!-- PAGE LEVEL STYLES -->
    <link href="<%=path%>/statics/css/bootstrap-fileupload.min.css" rel="stylesheet" />
    <!--CUSTOM BASIC STYLES-->
    <link href="<%=path%>/statics/css/basic.css" rel="stylesheet" />
    <!--CUSTOM MAIN STYLES-->
    <link href="<%=path%>/statics/css/custom.css" rel="stylesheet" />
    <!-- GOOGLE FONTS-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
	<style type="text/css">
	input[type=text],input[type=password]
            {
               width                    : 19em;
               height                   : 2.0em;
               padding                  : 0.2em 0.4em 0.2em 0.4em;
               vertical-align           : middle;
               border                   : 1px solid #94c1e7;
               -moz-border-radius       : 0.2em;
               -webkit-border-radius    : 0.2em;
               border-radius            : 0.2em;
               -webkit-appearance       : none;
               -moz-appearance          : none;
               appearance               : none;
               background               : #ffffff;
               font-family              : SimHei;
               font-size                : 1.1em;
               color                    : RGBA(102,102,102,1);
               cursor                   : pointer;
            }	
	select
            {
               width                    : 17.6em;
               height                   : 2.0em;
               padding                  : 0.2em 0.4em 0.2em 0.4em;
               vertical-align           : middle;
               border                   : 1px solid #94c1e7;
               -moz-border-radius       : 0.2em;
               -webkit-border-radius    : 0.2em;
               border-radius            : 0.2em;
               -webkit-appearance       : none;
               -moz-appearance          : none;
               appearance               : none;
               background               : #ffffff;
               font-family              : SimHei;
               font-size                : 1.1em;
               color                    : RGBA(102,102,102,1);
               cursor                   : pointer;
            }
    </style>
</head>
<body style="background-color: #E2E2E2;">
    <div class="container">
        <div class="row text-center " style="padding-top:100px;">
            <div class="col-md-12">
                <img src="<%=path%>/statics/img/logo.png" align="middle"/>
            </div>
        </div>
        <div class="row ">
            <div class="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-10 col-xs-offset-1">
                <div class="panel-body">
                    <hr />
                    <h5>欢迎注册川大资料网！请输入你的注册信息：</h5>
                   	<br />
                           
	                <form id="regForm" role="form" name="regForm" action="doRegister" method="get" onsubmit="return submitCheck()">
	                    <div class="form-group input-group">
                            <span class="input-group-addon"><i>账号&ensp;</i></span>
                            <input id="user_name" name="user_name" type="text" class="form-control" placeholder="你的账号 " value=""/>
                    	</div>
	                    <span id="name-check"></span>
	                    <div class="form-group input-group">
                            <span class="input-group-addon"><i>密码&ensp;</i></span>
                            <input id="user_password" name="user_password" type="password" class="form-control"  placeholder="你的密码" onkeyup="pwStrength(this.value)"/>
                            <table width="100%" border="0" cellspacing="0" cellpadding="1" bordercolor="#eeeeee" height="22"> 
							<tr align="center" bgcolor="#f5f5f5"> 
							<td width="40%" bgcolor="white">密码强度：</td>
							<td width="20%" id="strength_L">弱</td> 
							<td width="20%" id="strength_M">中</td> 
							<td width="20%" id="strength_H">强</td> 
							</tr> 
							</table>
	                    </div>
                        <div class="form-group input-group">
                        	<span class="input-group-addon"><i>密码&ensp;</i></span>
                            <input id="user_password_confirm" name="user_password_confirm" type="password" class="form-control"  placeholder="重新输入你的密码" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon"><i>邮箱&ensp;</i></span>
                            <input id="user_mail" name="user_mail" type="text" class="form-control"  placeholder="你的邮箱" />
                        </div>
                        <div class="form-group input-group">
                            <span class="input-group-addon"><i>手机&ensp;</i></span>
                            <input id="user_phone_num" name="user_phone_num" type="text" class="form-control"  placeholder="你的手机" />
                        </div>
                        <div class="form-group input-group">
							<span class="input-group-addon" id="gender"><i>性别&ensp;</i></span>
							<input name="user_gender" type="radio" value="男" checked="checked"/><span><i>男 &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;</i></span>
							<input name="user_gender" type="radio" value="女"/><span><i>女 &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;</i></span>
						</div>
						<div class="form-group input-group">
							<span class="input-group-addon" ><i>年级&ensp;</i></span>
							<select name="user_grade" id="user_grade">
								<option selected="selected" >请选择</option>
								<option>1</option>
								<option>2</option>
								<option>3</option>
								<option>4</option>
							</select>
						</div>
						
						<div class="form-group input-group">                                        
							<span class="input-group-addon" ><i>学院&ensp;</i></span>
							<select name="user_college" type="select-one" id="first" onChange="change()") >
								<option selected="selected">请选择</option>
								<option>材料科学与工程学院</option>
								<option>电气信息学院</option>
								<option>电子信息学院</option>
								<option>软件学院</option>
								<option>法学院</option>
								<option>高分子科学与工程学院</option>
								<option>公共管理学院</option>
								<option>华西公共卫生学院</option>
								<option>华西口腔医学院</option>
								<option>华西临床医学院</option>
								<option>华西药学院</option>
								<option>化学学院</option>
								<option>化学工程学院</option>
								<option>华西基础医学与法医学院</option>
								<option>计算机学院</option>
								<option>建筑与环境学院</option>
								<option>经济学院</option>
								<option>匹兹堡学院</option>
								<option>历史文化学院（旅游学院）</option>
								<option>轻纺与食品学院</option>
								<option>商学院</option>
								<option>生命科学学院</option>
								<option>数学学院</option>
								<option>水利水电学院</option>
								<option>外国语学院</option>
								<option>文学与新闻学院</option>
								<option>物理科学与技术学院</option>
								<option>艺术学院</option>
								<option>制造科学与工程学院</option>
								<option>空天科学与工程学院</option>												
							</select>										
						</div>
						<div class="form-group input-group">                                       
							<span class="input-group-addon" ><i>专业&ensp;</i></span>
							<select name="user_major" id="second">
								<option selected="selected">请选择</option>
							</select>										
						</div>
						<input type="submit" class="btn btn-primary " value="注册"/>                             
	                    <input type="text" name="originUrl" hidden="true" value="<%=originUrl %>"/>
	                    <a href="script:void(0)" class="btn btn-primary " onclick="document.regForm.reset();pwStrength('');">重置</a>
	                    <hr />
                   	</form>
                </div>
            </div>
        </div>
    </div>
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="<%=path%>/statics/js/bootstrap.js"></script>
    <!-- PAGE LEVEL SCRIPTS -->
    <script src="<%=path%>/statics/js/bootstrap-fileupload.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery.metisMenu.js"></script>
    <!-- CUSTOM SCRIPTS -->
    <script src="<%=path%>/statics/js/custom.js"></script>
    <!-- LIST SCRIPTS -->
    <script src="<%=path%>/statics/js/list.js"></script>
    <!-- CHECK PASSWORD SCRIPTS -->
    <script src="<%=path%>/statics/js/check-password.js"></script>
    <!-- CHECK REGISTER FORM SCRIPTS -->
    <script src="<%=path%>/statics/js/check-register-form.js"></script>
</body>
</html>
<% out.flush(); %>
