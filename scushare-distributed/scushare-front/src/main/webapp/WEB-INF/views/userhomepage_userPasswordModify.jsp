<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="userhomepage_head.jsp" %>
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="col-md-6 col-sm-6 col-xs-12">
               		<div class="panel panel-danger">
                        <div class="panel-heading">
                           	修改密码
                        </div>
                        <div class="panel-body">
                            <form role="form" action="doUserPasswordModify" onsubmit="return checkPwd(this)">
                                        
                                <div class="form-group">
                                    <label>原密码</label>
                                    <input class="form-control" type="password" name="oldpwd">
                                    <span></span>
                                </div>        
                                <div class="form-group">
                                    <label>新密码（x-xx位）</label>
                                    <input class="form-control" type="password" name="newpwd" onKeyUp="pwStrength(this.value)" onBlur="pwStrength(this.value)">
                                    
									<table width="210" border="0" cellspacing="0" cellpadding="1" bordercolor="#eeeeee" height="22" style='display:inline'> 
									<tr align="center" bgcolor="#f5f5f5"> 
									<td width="56%" bgcolor="white">密码强度：</td>
									<td width="14%" id="strength_L">弱</td> 
									<td width="14%" id="strength_M">中</td> 
									<td width="14%" id="strength_H">强</td> 
									</tr> 
									</table>
                                </div>        
                                <div class="form-group">
                                    <label>确认新密码</label>
                                    <input class="form-control" type="password" name="newpwdcof">
                                </div>
                                <input type="hidden" name="user_id" id="user_id" <%="value='" + user_id.toString() + "'"%>/>
                                 
                                <button type="submit" class="btn btn-danger">修改 </button>
								<a href="userInfoPage" class="btn btn-danger ">返回</a>

                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="<%=path%>/statics/js/bootstrap.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery.metisMenu.js"></script>
    <!-- CUSTOM SCRIPTS -->
    <script src="<%=path%>/statics/js/custom.js"></script>
    <!-- CHECKPWD SCRIPTS -->
    <script src="<%=path%>/statics/js/check-password.js"></script>
    <script type="text/javascript">
		document.getElementById("userInfoLink").className = 'active-menu';
   	</script>
<%@ include file="userhomepage_tail.jsp" %>