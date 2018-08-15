<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="userhomepage_head.jsp" %>
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-6">
                       	<div class="panel panel-default">
	                        <div class="panel-heading">
	                           	上传头像
	                        </div>
	                        <div class="panel-body">
	                    		<div class="form-group">
			                        <label class="control-label col-lg-4">上传本地头像</label>
			                        <div class="">
	                            		<div class="fileupload fileupload-new" data-provides="fileupload">
			                                <div class="fileupload-new thumbnail" style="width: 200px; height: 150px;"><img src="<%=path%><%=user_chathead %>" alt="" /></div>
			                                <div class="fileupload-preview fileupload-exists thumbnail" style="max-width: 200px; max-height: 150px; line-height: 20px;"></div>
			                                <div>
			                                	<form action="doUploadChathead" onsubmit="return checkChathead()" method="post" enctype="multipart/form-data">
			                                    <span class="btn btn-file btn-primary"><span class="fileupload-new">选择文件</span><span class="fileupload-exists">更换</span><input id="file" name="file" type="file"></span>
			                                    <input type="submit" class="btn btn-danger fileupload-exists" value="上传">
			                                    <input type="hidden" name="user_id" id="user_id" <%="value='" + user_id.toString() + "'"%>/>
			                                	</form>
			                                </div>
	                            		</div>
	                        		</div>
	                    		</div>
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
	    <!-- PAGE LEVEL SCRIPTS -->
	    <script src="<%=path%>/statics/js/bootstrap-fileupload.js"></script>
	    <!-- CHECK CHATHEAD SCRIPTS -->
	    <script src="<%=path%>/statics/js/check-chathead-form.js"></script>
    	<script type="text/javascript">
			document.getElementById("userInfoLink").className = 'active-menu';
    	</script>
<%@ include file="userhomepage_tail.jsp" %>
