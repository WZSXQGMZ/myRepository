<%@page import="com.scushare.entity.UserModifiableInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="userhomepage_head.jsp" %>

        <div id="page-wrapper">
            <div id="page-inner">
                <div class="col-md-6 col-sm-6 col-xs-12">
               		<div class="panel panel-danger">
                        <div class="panel-heading">
                           	修改信息
                        </div>
                        <div class="panel-body">
                            <form id="userInfoForm" name="userInfoForm" role="form" action="doUserInfoModify" onsubmit="return checkUserInfo()">
                                        
<!--                                 <div class="form-group">
                                    <label>姓名</label>
                                    <input class="form-control" type="text">
                             		<p class="help-block">Help text here.</p>
                                </div> -->
                                <div class="form-group">
                                    <label>性别</label>
                                    <div class="radio" style="margin-left:20px; ">
										<input id="gender_male" name="user_gender" type="radio" value="男" checked="checked"/><span><i>男 &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;</i></span>
										<input id="gender_female" type="radio" value="女"/><span><i>女 &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;</i></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>学院</label>
                                    <select class="form-control" id="first" name="user_college" onChange="change()" >
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
                                <div class="form-group">
                                    <label>专业</label>
	                            	<select id="second" name="user_major" class="form-control">
										<option selected="selected">请选择</option>
									</select>
                                </div>
                                <div class="form-group">
                                    <label>手机</label>
                                    <input id="user_phone_num" name="user_phone_num" class="form-control" type="text">
                                </div>
                                <input type="hidden" name="user_id" id="user_id" <%="value='" + user_id.toString() + "'"%>/>
                                <!--<div class="form-group">
                                    <label>签名</label>
                                    <textarea class="form-control" rows="3"></textarea>
                                </div>-->
                                 
                                <button type="submit" class="btn btn-danger">修改 </button>
								<a href="script:void(0)" class="btn btn-danger " onclick="document.userInfoForm.reset();pwStrength('');">重置</a>
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
    <!-- LIST SCRIPTS -->
    <script src="<%=path%>/statics/js/list.js"></script>
    <!-- CHECK USERINFO FORM SCRIPTS -->
    <script src="<%=path%>/statics/js/check-userinfo-form.js"></script>
    
    <%
    	UserModifiableInfo userMlbInfo = (UserModifiableInfo)request.getAttribute("userMlbInfo");
    	out.println("<script type=\"text/javascript\">");
    	out.println("$(\"input:radio[value='" + userMlbInfo.getUser_gender() + "']\").attr('checked','true');");
    	out.println("$(\".selector\").find(\"option:[text='" + userMlbInfo.getUser_college() + "']\").attr(\"selected\",true);");
    	out.println("$(\".selector\").find(\"option:contains('" + userMlbInfo.getUser_major() + "')\").attr(\"selected\",true);");
    	out.println("$('#user_phone_num').val('" + userMlbInfo.getUser_phone_num() + "')");
    	out.println("</script>");
    %>
   	<script type="text/javascript">
		document.getElementById("userInfoLink").className = 'active-menu';
   	</script>
<%@ include file="userhomepage_tail.jsp" %>