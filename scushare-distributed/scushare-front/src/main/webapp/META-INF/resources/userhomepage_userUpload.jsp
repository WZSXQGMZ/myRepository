<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="userhomepage_head.jsp" %>
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                        <h1 class="page-head-line">资料上传</h1>
                        <h1 class="page-subhead-line">您可以分享、展示您的资料 （川大资料网支持上传各种类型的文件，文件大小最大不超过100M）</h1>

                    </div>
                </div>
                <!-- /. ROW  -->
                
                    <form name="upload_form" id="upload_form">
               <div class="row">
                  <div class="col-md-12">                     
         <div class="panel panel-default">
                        <div class="panel-heading">
                            	资料上传
                        </div>
                        <div class="panel-body">
                             <div id="wizard">
                <h2>第一步</h2>
                    <p>请选择您要上传的文件：</p><hr />
                    <div class="form-group">
                        <div class="">
                            <div class="fileupload fileupload-new" data-provides="fileupload">
                                <span class="btn btn-file btn-default">
                                    <span class="fileupload-new">选择文件</span>
                                    <span class="fileupload-exists">更换</span>
                                    <input type="file" name="file" id="file">
                                </span>
                                <span class="fileupload-preview"></span>
                                <a href="#" class="close fileupload-exists" data-dismiss="fileupload" style="float: none">×</a>
                            </div>
                        </div>
                    </div>

                <h2>第二步</h2>
                    <p>请选择您上传资料的门类：</p><hr />
										 <div class="form-group input-group">                                        
											<span class="input-group-addon" >学院</span>
											<select name="file_college" type="select-one" id="first" onChange="change()" >
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
											<span class="input-group-addon" >专业</span>
											<select name="file_major" id="second">
												<option selected="selected">请选择</option>
											</select>										
										</div> 

                <h2>第三步</h2>
                    <p>请编辑您上传资料的信息：</p><hr />

                    <div class="form-group input-group">
                    	<span class="input-group-addon">简介</span>
                   		<input id="file_description" name="file_description" type="text" class="form-control"  placeholder="此资料的简介 " />
                    </div>
                    <div class="form-group input-group">
                    	<span class="input-group-addon">关键词1</span>
                   		<input id="file_description" name="keyword1" type="text" class="form-control"  placeholder="关键词1 " />
                    </div>
                    <div class="form-group input-group">
                    	<span class="input-group-addon">关键词2</span>
                   		<input id="file_description" name="keyword2" type="text" class="form-control"  placeholder="关键词2" />
                    </div>
                    <div class="form-group input-group">
                    	<span class="input-group-addon">关键词3</span>
                   		<input id="file_description" name="keyword3" type="text" class="form-control"  placeholder="关键词3 " />
                    </div>

                <h2>第四步</h2>
                    <p><i id="upload_statu">上传文件</i><i id="upload_progress"></i><i id="percent_sign"></i></p>
                    <%-- <img src="<%=path%>/statics/img/uploadsuccess.png" style="width: 150px;height: 150px;"/> --%>
                    <input id="upload_button" type="button" class="btn btn-lg btn-primary" onclick="upload()" value="上传"/>

            </div>
                             
                        </div>
                    </div>
                 </div>
                </div>
                
                  
			</form>           
            </div>
            <!-- /. PAGE INNER  -->
        </div>
        <!-- /. PAGE WRAPPER  -->


	
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery-1.10.2.js"></script>
     <!-- WIZARD SCRIPTS -->
    <script src="<%=path%>/statics/js/wizard/modernizr-2.6.2.min.js"></script>
    <script src="<%=path%>/statics/js/wizard/jquery.cookie-1.3.1.js"></script>
    <script src="<%=path%>/statics/js/wizard/jquery.steps.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="<%=path%>/statics/js/jquery.metisMenu.js"></script>
    <!-- BOOTSTRAP SCRIPTS -->
    <script src="<%=path%>/statics/js/bootstrap.js"></script>
    <!-- PAGE LEVEL SCRIPTS -->
    <script src="<%=path%>/statics/js/bootstrap-fileupload.js"></script>
    <!-- LIST SCRIPTS -->
    <script src="<%=path%>/statics/js/list.js"></script>
       <!-- CUSTOM SCRIPTS -->
    <script src="<%=path%>/statics/js/custom.js"></script>
    <!-- FILE UPLOAD SCRIPTS -->
    <script src="<%=path%>/statics/js/file-upload.js"></script>
    <script type="text/javascript">
		document.getElementById("uploadFileLink").className = 'active-menu';
   	</script>
<%@ include file="userhomepage_tail.jsp" %>