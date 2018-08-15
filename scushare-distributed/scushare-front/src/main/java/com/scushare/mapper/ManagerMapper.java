package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import scu.pojo.DataFile;
import scu.pojo.Manager;

public interface ManagerMapper {
	//登录
	@Select("select manager_name as managerName,manager_password as managerPassword   from manager where manager_name=#{Mname}")
	Manager Login(@Param("Mname") String name);
	//联表查询
	@Select("<script>"
			 +"SELECT a.file_id as fileId ,a.file_name as fileName,a.file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a,user as b"
		        +"<where>"
		            +"<if test=\"file_college == null and file_major == null and file_name ==''\">"
		               + "and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
		             +  " and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and file_college =#{file_college} and file_major =#{file_major} order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
		              +"and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and file_name  like CONCAT('%',#{file_name},'%') order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
		                +"and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and file_college =#{file_college} and file_major =#{file_major} and file_name  like CONCAT('%',#{file_name},'%') order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"

		        +"</where>"
			+ "</script>")
	List<DataFile> getFileList(@Param("file_college") String file_college,
            @Param("file_major") String file_major,
            @Param("file_name") String file_name,
            @Param("currentPage") int currentPage,
            @Param("pageSize") int pageSize);
	//获得查询记录数的总量
	@Select("<script>"
			+"select COUNT(*) from file as a,user as b"
	        +"<where>"
	            +"<if test=\"file_college == null and file_major == null and file_name ==''\">"
	               + "and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' " 
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
	             +  " and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and file_college =#{file_college} and file_major =#{file_major} "
	            +"</if>"
	            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
	              +"and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
	                +"and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and file_college =#{file_college} and file_major =#{file_major} and file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"

	        +"</where>"
			+ "</script>")
	int pageCount(@Param("file_college") String file_college,
            @Param("file_major") String file_major,
            @Param("file_name") String file_name
            );
	//删除资料
	@Update("update file set file_isdelete='1' where file_id = #{fileId} ")
	int dataDelet(@Param("fileId") String fileId);
    
	//查询资料的URL
	@Select("Select file_path as filePath from file where file_id = #{fileId}")
	String getFileUrlById(@Param("fileId")String fileId);
	
	//查询资料是否通过审核
	@Select("Select  a.file_id as fileId ,a.file_name as fileName,a.file_up_time as fileUpTime,a.file_isverify as fileIsVerify,b.user_name as fileUsername FROM file as a,user as b where a.file_up_user_id=b.user_id AND (a.file_isverify='是' OR a.file_isverify='否') order by file_up_time DESC LIMIT #{currentPage},#{pageSize}")
	List<DataFile> getFileCheck(@Param("currentPage") int currentPage,
            					@Param("pageSize") int pageSize);
	
	//查询审核历史的总数
	@Select("select COUNT(*) from file where file_isverify='是' OR file_isverify='否'")
	int checkPageCount();
	
	//查询正在审核的资料
	@Select("Select  a.file_id as fileId ,a.file_name as fileName,a.file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a,user as b where a.file_up_user_id=b.user_id  AND a.file_isverify='待审核' order by file_up_time DESC LIMIT #{currentPage},#{pageSize}")
	List<DataFile> getFileChecking(@Param("currentPage") int currentPage,
	            					@Param("pageSize") int pageSize);
	//查询正在审核资料的总数
	@Select("select COUNT(*) from file where file_isverify='待审核'")
	int checkingPageCount();
	
	//查询资料的准确信息
	@Select("Select file_mark as fileMark, file_path as filePath, file_up_time as fileUpTime,file_name as fileName,file_size as fileSize,file_page_num as filePageNum,file_major as fileMajor,file_college as fileCollege,file_abstract as fileIntroduction from file where file_id = #{fileId}")
	DataFile getAccuDataInfo(@Param("fileId") String fileId);
	
	//更新是否通过审核
	@Update("Update file set file_isverify =#{isverify} where file_id=#{fileId} ")
	int UpdataVerify(@Param("fileId") String fileId,
					 @Param("isverify") String isverify);
	//查询原密码是否与输入一致
	@Select("Select manager_password as managerPassword from manager where manager_name=#{managerName}")
	String getOldPWd(@Param("managerName")  String managerName);
	
	//更新密码
	@Update("Update manager set manager_password=#{newPwd} where manager_name=#{managerName}")
	int UpdataPwd(@Param("newPwd") String newPwd,
				  @Param("managerName") String managerName);
	//预览界面，查询文件的路径，学院，页数
	@Select("Select file_path as filePath,file_college as fileColloge,file_page_num as filePageNum from file where file_id = #{fileId}")
	DataFile getPreview(@Param("fileId") String  fileId);
	
	
	//首页查询，包含文件类型
	@Select("<script>"
			 +"SELECT a.file_college as fileColloge, a.file_name as fileName,a.file_id as fileId,a.file_path as filePath, a.file_abstract as fileIntroduction,file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a ,user as b  "
		        +"<where>"
		            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
		               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是'"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
		             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major}  "
		            +"</if>"
		            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
		              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
		                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
	                +"</if>"
	                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} "
	                +"</if>"
		            +"and a.file_name like CONCAT('%',#{fileType},'%')  LIMIT #{currentPage},#{pageSize}"
		        +"</where>"
			+ "</script>")
	List<DataFile> IgetIndexfileId(@Param("file_college") String file_college,
            @Param("file_major") String file_major,
            @Param("file_name") String file_name,
            @Param("currentPage") int currentPage,
            @Param("pageSize") int pageSize,
            @Param("fileType") String fileType);
	
	//首页查询，不包含文件类型
	@Select("<script>"
			+"SELECT a.file_college as fileColloge, a.file_name as fileName, a.file_id as fileId,a.file_path as filePath, a.file_abstract as fileIntroduction,file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a ,user as b  "
	        +"<where>"
	            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
	               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' LIMIT #{currentPage},#{pageSize}" 
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
	             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major} LIMIT #{currentPage},#{pageSize} "
	            +"</if>"
	            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
	              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_name  like CONCAT('%',#{file_name},'%') LIMIT #{currentPage},#{pageSize}"
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') LIMIT #{currentPage},#{pageSize}"
	            +"</if>"
	            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
                +"</if>"
                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} "
                +"</if>"
	        +"</where>"
			+ "</script>")
	List<DataFile> NgetIndexfileId(@Param("file_college") String file_college,
           @Param("file_major") String file_major,
           @Param("file_name") String file_name,
           @Param("currentPage") int currentPage,
           @Param("pageSize") int pageSize);
	
	//首页查询总数，包含数据类型
	@Select("<script>"
			 +"SELECT count(*)  FROM file as a ,user as b"
			 +"<where>"
	            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
	               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是'"
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
	             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major}  "
	            +"</if>"
	            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
	              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"
	            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
                +"</if>"
                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} "
                +"</if>"
	            +"and a.file_name like CONCAT('%',#{fileType},'%')  "
	        +"</where>"
			+ "</script>")
	int CountIndexfileId(@Param("file_college") String file_college,
	           @Param("file_major") String file_major,
	           @Param("file_name") String file_name,
	           @Param("currentPage") int currentPage,
	           @Param("pageSize") int pageSize,
	           @Param("fileType") String fileType);
	
	//首页查询总数，不包含数据类型
		@Select("<script>"
				 +"SELECT count(*)  FROM file as a ,user as b "
				 +"<where>"
		            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
		               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是'"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
		             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major}  "
		            +"</if>"
		            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
		              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
		                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
	                +"</if>"
	                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '是' and a.file_college =#{file_college} "
	                +"</if>"
		        +"</where>"
				+ "</script>")
		int CountNIndexfileId(@Param("file_college") String file_college,
		           @Param("file_major") String file_major,
		           @Param("file_name") String file_name,
		           @Param("currentPage") int currentPage,
		           @Param("pageSize") int pageSize);
}
