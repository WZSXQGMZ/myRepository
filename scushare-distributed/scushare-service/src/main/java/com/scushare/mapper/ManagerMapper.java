package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import scu.pojo.DataFile;
import scu.pojo.Manager;

public interface ManagerMapper {
	//��¼
	@Select("select manager_name as managerName,manager_password as managerPassword   from manager where manager_name=#{Mname}")
	Manager Login(@Param("Mname") String name);
	//�����ѯ
	@Select("<script>"
			 +"SELECT a.file_id as fileId ,a.file_name as fileName,a.file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a,user as b"
		        +"<where>"
		            +"<if test=\"file_college == null and file_major == null and file_name ==''\">"
		               + "and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
		             +  " and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and file_college =#{file_college} and file_major =#{file_major} order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
		              +"and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and file_name  like CONCAT('%',#{file_name},'%') order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
		                +"and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and file_college =#{file_college} and file_major =#{file_major} and file_name  like CONCAT('%',#{file_name},'%') order by a.file_up_time DESC LIMIT #{currentPage},#{pageSize}"
		            +"</if>"

		        +"</where>"
			+ "</script>")
	List<DataFile> getFileList(@Param("file_college") String file_college,
            @Param("file_major") String file_major,
            @Param("file_name") String file_name,
            @Param("currentPage") int currentPage,
            @Param("pageSize") int pageSize);
	//��ò�ѯ��¼��������
	@Select("<script>"
			+"select COUNT(*) from file as a,user as b"
	        +"<where>"
	            +"<if test=\"file_college == null and file_major == null and file_name ==''\">"
	               + "and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' " 
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
	             +  " and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and file_college =#{file_college} and file_major =#{file_major} "
	            +"</if>"
	            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
	              +"and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
	                +"and  a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and file_college =#{file_college} and file_major =#{file_major} and file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"

	        +"</where>"
			+ "</script>")
	int pageCount(@Param("file_college") String file_college,
            @Param("file_major") String file_major,
            @Param("file_name") String file_name
            );
	//ɾ������
	@Update("update file set file_isdelete='1' where file_id = #{fileId} ")
	int dataDelet(@Param("fileId") String fileId);
    
	//��ѯ���ϵ�URL
	@Select("Select file_path as filePath from file where file_id = #{fileId}")
	String getFileUrlById(@Param("fileId")String fileId);
	
	//��ѯ�����Ƿ�ͨ�����
	@Select("Select  a.file_id as fileId ,a.file_name as fileName,a.file_up_time as fileUpTime,a.file_isverify as fileIsVerify,b.user_name as fileUsername FROM file as a,user as b where a.file_up_user_id=b.user_id AND (a.file_isverify='��' OR a.file_isverify='��') order by file_up_time DESC LIMIT #{currentPage},#{pageSize}")
	List<DataFile> getFileCheck(@Param("currentPage") int currentPage,
            					@Param("pageSize") int pageSize);
	
	//��ѯ�����ʷ������
	@Select("select COUNT(*) from file where file_isverify='��' OR file_isverify='��'")
	int checkPageCount();
	
	//��ѯ������˵�����
	@Select("Select  a.file_id as fileId ,a.file_name as fileName,a.file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a,user as b where a.file_up_user_id=b.user_id  AND a.file_isverify='�����' order by file_up_time DESC LIMIT #{currentPage},#{pageSize}")
	List<DataFile> getFileChecking(@Param("currentPage") int currentPage,
	            					@Param("pageSize") int pageSize);
	//��ѯ����������ϵ�����
	@Select("select COUNT(*) from file where file_isverify='�����'")
	int checkingPageCount();
	
	//��ѯ���ϵ�׼ȷ��Ϣ
	@Select("Select file_mark as fileMark, file_path as filePath, file_up_time as fileUpTime,file_name as fileName,file_size as fileSize,file_page_num as filePageNum,file_major as fileMajor,file_college as fileCollege,file_abstract as fileIntroduction from file where file_id = #{fileId}")
	DataFile getAccuDataInfo(@Param("fileId") String fileId);
	
	//�����Ƿ�ͨ�����
	@Update("Update file set file_isverify =#{isverify} where file_id=#{fileId} ")
	int UpdataVerify(@Param("fileId") String fileId,
					 @Param("isverify") String isverify);
	//��ѯԭ�����Ƿ�������һ��
	@Select("Select manager_password as managerPassword from manager where manager_name=#{managerName}")
	String getOldPWd(@Param("managerName")  String managerName);
	
	//��������
	@Update("Update manager set manager_password=#{newPwd} where manager_name=#{managerName}")
	int UpdataPwd(@Param("newPwd") String newPwd,
				  @Param("managerName") String managerName);
	//Ԥ�����棬��ѯ�ļ���·����ѧԺ��ҳ��
	@Select("Select file_path as filePath,file_college as fileColloge,file_page_num as filePageNum from file where file_id = #{fileId}")
	DataFile getPreview(@Param("fileId") String  fileId);
	
	
	//��ҳ��ѯ�������ļ�����
	@Select("<script>"
			 +"SELECT a.file_college as fileColloge, a.file_name as fileName,a.file_id as fileId,a.file_path as filePath, a.file_abstract as fileIntroduction,file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a ,user as b  "
		        +"<where>"
		            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
		               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��'"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
		             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major}  "
		            +"</if>"
		            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
		              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
		                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
	                +"</if>"
	                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} "
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
	
	//��ҳ��ѯ���������ļ�����
	@Select("<script>"
			+"SELECT a.file_college as fileColloge, a.file_name as fileName, a.file_id as fileId,a.file_path as filePath, a.file_abstract as fileIntroduction,file_up_time as fileUpTime,b.user_name as fileUsername FROM file as a ,user as b  "
	        +"<where>"
	            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
	               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' LIMIT #{currentPage},#{pageSize}" 
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
	             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major} LIMIT #{currentPage},#{pageSize} "
	            +"</if>"
	            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
	              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_name  like CONCAT('%',#{file_name},'%') LIMIT #{currentPage},#{pageSize}"
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') LIMIT #{currentPage},#{pageSize}"
	            +"</if>"
	            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
                +"</if>"
                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} "
                +"</if>"
	        +"</where>"
			+ "</script>")
	List<DataFile> NgetIndexfileId(@Param("file_college") String file_college,
           @Param("file_major") String file_major,
           @Param("file_name") String file_name,
           @Param("currentPage") int currentPage,
           @Param("pageSize") int pageSize);
	
	//��ҳ��ѯ������������������
	@Select("<script>"
			 +"SELECT count(*)  FROM file as a ,user as b"
			 +"<where>"
	            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
	               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��'"
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
	             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major}  "
	            +"</if>"
	            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
	              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"
	            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') "
	            +"</if>"
	            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
                +"</if>"
                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} "
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
	
	//��ҳ��ѯ��������������������
		@Select("<script>"
				 +"SELECT count(*)  FROM file as a ,user as b "
				 +"<where>"
		            +"<if test=\"file_college == null and file_major == null and file_name =='' \">"
		               + "and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��'"
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name == ''\">"
		             +  " and a.file_up_user_id=b.user_id  and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major}  "
		            +"</if>"
		            +"<if test=\"file_college == null and file_major == null and file_name != ''\">"
		              +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major != null and file_name != ''\">"
		                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_major =#{file_major} and a.file_name  like CONCAT('%',#{file_name},'%') "
		            +"</if>"
		            +"<if test=\"file_college != null and file_major == null and file_name != ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} and a.file_name  like CONCAT('%',#{file_name},'%') "
	                +"</if>"
	                +"<if test=\"file_college != null and file_major == null and file_name == ''\">"
	                +"and a.file_up_user_id=b.user_id and a.file_isdelete !='1' and a.file_isverify = '��' and a.file_college =#{file_college} "
	                +"</if>"
		        +"</where>"
				+ "</script>")
		int CountNIndexfileId(@Param("file_college") String file_college,
		           @Param("file_major") String file_major,
		           @Param("file_name") String file_name,
		           @Param("currentPage") int currentPage,
		           @Param("pageSize") int pageSize);
}
