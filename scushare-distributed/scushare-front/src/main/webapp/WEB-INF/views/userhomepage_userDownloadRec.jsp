<%@page import="com.scushare.utils.JSPprinter"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="scu.pojo.Download"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="userhomepage_head.jsp" %>
<%
	final long OneMB = 1024 * 1024;
	final long OneKB = 1024;
%>

        <div id="page-wrapper">
            <div id="page-inner">
                <div class="panel panel-default">
                        <div class="panel-heading">
                        	下载记录
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                    <%
                                    	List<Download> downloadList = (List<Download>)request.getAttribute("downloadList"); 
                                    	if(downloadList == null || downloadList.size() == 0){
                                    		out.print("没有下载记录");
                                    	}else{
                                    		int currPage = (Integer)request.getAttribute("startPage");
                                    		int maxPage = (Integer)request.getAttribute("maxPageNum");
                                    		int recPerPage = (Integer)request.getAttribute("recordsCountPerPage");
                                    		int currIndex = (currPage - 1) * recPerPage + 1;
                                    		int length = downloadList.size();
                                    		Download record = null;
                                    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                                    		out.println("<table class=\"table table-hover\">");
                                    		out.println("<thead>");
                                    		out.println("<tr>");
                                    		out.println("<th>#</th>");
                                    		out.println("<th>资料名</th>");                                   	    
                                    		out.println("<th>大小</th>");
                                    		out.println("<th>上传时间</th>");
                                    		out.println("</tr>");
                                    		out.println("</thead>");
                                    		out.println("<tbody>");
                                    		for(int i = 0; i < length; i++){
                                    			record = downloadList.get(i);
                                    			out.println("<tr>");
                                    			out.println("<td>" + (i + currIndex) + "</td>");
                                    			/* out.println("1");
                                    			out.println("2");
                                    			out.println("3"); */
                                    			if(record.getFileName()== null){
                                    				out.println("1");
                                    			}else{
                                        			out.println("<td>" + (i + currIndex) + "</td>");
                                        			out.println("<td><a href=\"preview?fileID=" + String.valueOf(record.getFileId()) + "\">");
                                        			out.println(record.getFileName() + "</a></td>");
                                    			}
                                    			
                                    			//计算文件大小
                                    			long file_size = record.getFileSize();
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
                                    			if(record.getDownloadTime() == null){
                                    				out.println("2");
                                    				
                                    			}else
                                    			{
                                    				out.println("<td>" + sdf.format(record.getDownloadTime()) + "</td>"); 
                                    			}
                                    			out.println("</tr>");
                                    		}//LOOP END
                                    		out.println("</tbody>");
                                    		out.println("</table>");
                                    		
                                    		//打印按钮
                                    		out.println(JSPprinter.printPageButton(currPage, maxPage, "UMDownloadQuery"));
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
			document.getElementById("downloadInfoLink").className = 'active-menu';
    	</script>
<%@ include file="userhomepage_tail.jsp" %>