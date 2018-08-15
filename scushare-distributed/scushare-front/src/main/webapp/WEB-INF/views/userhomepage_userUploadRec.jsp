<%@page import="com.scushare.utils.DateUtil"%>
<%@page import="com.scushare.utils.JSPprinter"%>
<%@page import="com.scushare.entity.UserUploadRec"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	final long OneMB = 1024 * 1024;
	final long OneKB = 1024;
%>
<%@ include file="userhomepage_head.jsp" %>
        <div id="page-wrapper">
            <div id="page-inner">
                <div class="panel panel-default">
                        <div class="panel-heading">
                        	上传记录
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                    <%
                                    	List<UserUploadRec> uploadRecList = (List<UserUploadRec>)request.getAttribute("uploadRecList"); 
                                    	if(uploadRecList == null || uploadRecList.size() == 0){
                                    		out.print("没有上传记录");
                                    	}else{
                                    		int currPage = (Integer)request.getAttribute("startPage");
                                    		int maxPage = (Integer)request.getAttribute("maxPageNum");
                                    		int recPerPage = (Integer)request.getAttribute("recordsCountPerPage");
                                    		int currIndex = (currPage - 1) * recPerPage + 1;
                                    		int length = uploadRecList.size();
                                    		UserUploadRec record = null;

                                    		out.println("<table class=\"table table-hover\">");
                                    		out.println("<thead>");
                                    		out.println("<tr>");
                                    		out.println("<th>#</th>");
                                    		out.println("<th>资料名</th>");
                                    		out.println("<th>大小</th>");
                                    		out.println("<th>上传时间</th>");
                                    		out.println("<th>审核状态</th>");
                                    		out.println("<th></th>");
                                    		out.println("</tr>");
                                    		out.println("</thead>");
                                    		out.println("<tbody>");
                                    		for(int i = 0; i < length; i++){
                                    			record = uploadRecList.get(length - i - 1);
                                    			out.println("<tr>");
                                    			out.println("<td>" + (i + currIndex) + "</td>");
                                    			String prefix = "";
                                    			String postfix = "";
                                    			//获取审核情况
                                    			String verified = record.getFile_isverify();
                                    			if(verified == null || verified.equals("")){
                                    				verified = "未审核";
                                    			}else if(verified.equals("是")){
                                    				verified = "已通过";
                                    				prefix = "<a href=\"preview?fileID=" + String.valueOf(record.getFile_id()) + "\">";
                                    				postfix = "</a>";
                                    			}else if(verified.equals("否")){
                                    				verified = "未通过";
                                    			}
                                    			out.println("<td>" + prefix + record.getFile_name() + postfix + "</td>");
                                    			
                                    			//计算文件大小
                                    			long file_size = record.getFile_size();
                                    			String file_size_str = "";
                                    			if(file_size < OneKB){
                                    				file_size_str = "1K";
                                    			}else if(file_size < OneMB){
                                    				file_size_str = file_size / OneKB + "K";
                                    			}else {
                                    				file_size_str = file_size / OneMB + "M";
                                    			}
                                    			out.println("<td>" + file_size_str + "</td>");
                                    			
                                    			//获取上传时间
                                    			out.println("<td>" + DateUtil.getStringByDate(record.getFile_up_time()) + "</td>");

                                    			out.println("<td>" + verified + "</td>");
                                    			out.println("<td><a href='deleteFile?pageNum=" + currPage + "&file_id=" + record.getFile_id() + "'>删除</a>" + "</td>");
                                    			out.println("</tr>");
                                    		}//LOOP END
                                    		out.println("</tbody>");
                                    		out.println("</table>");
                                    		
                                    		//打印按钮
                                    		out.println(JSPprinter.printPageButton(currPage, maxPage, "userUploadRecPaging"));
                                    	}
                                    %>
                            </div>
                        </div>
                    </div>
                    <!-- End  Hover Rows  -->
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
		document.getElementById("uploadInfoLink").className = 'active-menu';
   	</script>
<%@ include file="userhomepage_tail.jsp" %>