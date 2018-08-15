<%@page import="com.scushare.entity.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.scushare.utils.DateUtil"%>
<%@ include file="userhomepage_head.jsp" %>
<% UserInfo userInfo = (UserInfo)request.getAttribute("userInfo"); %>
        <div id="page-wrapper">
            <div id="page-inner">
            	<div class="row">
                    <div class="col-md-12">
                        <div class="userinfo-img-div" style="width: 200px; height: 150px" >
	                        <a href="uploadChatheadPage"><img src="<%=path%><%=user_chathead %>" ></a>
	                        
                        </div>
                        <div>
                        <span><%

                    			String modifyStatu = (String)request.getAttribute("modifyStatu");
	                    		if(modifyStatu == null){
	                    			//do nothing
	                    		}else if(modifyStatu.equals("modifyChatheadSuccess")){
	                    			out.print("头像上传成功！");
	                    		}else if(modifyStatu.equals("modifyChatheadFailed")){
	                    			out.print("头像上传失败！");
	                    		}
	                        %></span>
	                    </div>
                        <h1 class="page-head-line"><%out.print(user_name); %></h1>
                        <h1 class="page-subhead-line">这个人很懒，什么都没有写 </h1>

                    </div>
                </div>
                <div class="row">
	                <div class="col-md-4" style="width: 100%;">
	                    <div class="panel panel-default">
	                        <!--<div class="panel-heading">
	                        	个人信息
	                        </div>-->
	                        <div class="panel-body">
	                            <h2>性别
	                                <small id="user_gender"><%out.print(userInfo.getUser_gender()); %></small>
	                            </h2>
	                            <h2>学院
	                                <small id="user_college"><%out.print(userInfo.getUser_college()); %></small>
	                            </h2>
	                            <h2>专业
	                                <small id="user_major"><%out.print(userInfo.getUser_major()); %></small>
	                            </h2>
	                            <h2>邮箱
	                                <small id="user_mail"><%out.print(userInfo.getUser_mail()); %></small>
	                            </h2>
	                            <h2>手机
	                                <small id="user_phone_num"><%out.print(userInfo.getUser_phone_num()); %></small>
	                            </h2>
	                            <h2>注册时间 
	                                <small id="user_reg_time"><%out.print(DateUtil.getStringByDate(userInfo.getUser_reg_time())); %></small>
	                            </h2>
	                            <hr />
						        <button type="button" class="btn btn-lg btn-primary" onclick="redirectUserInfoModify()">修改信息</button>
						        <button type="button" class="btn btn-lg btn-primary" onclick="redirectUserPasswordModify()">修改密码</button>
	                        	<span><%
	                        		if(modifyStatu == null){
	                        			//do nothing
	                        		}else if(modifyStatu.equals("modifyInfoSuccess")){
	                        			out.print("修改成功！");
	                        		}else if(modifyStatu.equals("modifyInfoFailed")){
	                        			out.print("修改失败！");
	                        		}else if(modifyStatu.equals("modifyMailSuccess")){
	                        			out.print("邮箱修改成功！");
	                        		}else if(modifyStatu.equals("modifyMailFailed")){
	                        			out.print("邮箱修改失败！");
	                        		}else if(modifyStatu.equals("modifyPasswordSuccess")){
	                        			out.print("密码修改成功！");
	                        		}else if(modifyStatu.equals("modifyPasswordFailed")){
	                        			out.print("密码修改失败！");
	                        		}
								%></span>
	                        </div>
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
        <script type="text/javascript">
		function redirectUserInfoModify(){
	    	window.location.href="userInfoModifyPage?user_gender=" + $("#user_gender").text()
	    						+ "&user_college=" + $("#user_college").text()
	    						+ "&user_major=" + $("#user_major").text()
	    						+ "&user_phone_num=" + $("#user_phone_num").text()
	    						+ "&user_id=" + $("#user_id").text();
		}
		
		function redirectUserPasswordModify(){
			window.location.href="userPasswordModifyPage?user_id=" + $("#user_id").text();
		}
    	</script>
    	<script type="text/javascript">
			document.getElementById("userInfoLink").className = 'active-menu';
    	</script>
<%@ include file="userhomepage_tail.jsp" %>