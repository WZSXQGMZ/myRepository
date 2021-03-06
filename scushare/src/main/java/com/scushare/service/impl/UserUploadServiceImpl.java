package com.scushare.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.entity.FileInfo;
import com.scushare.mapper.UserServiceMapper;
import com.scushare.service.UserUploadResult;
import com.scushare.service.UserUploadService;
import com.scushare.utils.DateUtil;
import com.scushare.utils.FileToImgThread;
import com.scushare.utils.MD5Util;

@Service
public class UserUploadServiceImpl implements UserUploadService{

	@Autowired
	UserServiceMapper userMapper;
	
	@Autowired
	FileToImgThread fileToImgThread;
	
	final static long OneMB = 1024 * 1024;
	final static long OneKB = 1024;
	final static long MAX_FILE_SIZE = 100 * OneMB;
	final static long MAX_CHATHEAD_SIZE = 2 * OneMB;
	
	final static String LOCAL_DATA_PATH = "E:/SCUSHARE_DATA/"; 
	final static String LOCAL_CHATHEAD_PATH = LOCAL_DATA_PATH + "USER_CHATHEAD/";
	
	private boolean fileRepeat = false;
	
	/**
	 * 函数用于上传文件
	 * @param request 含有上传文件表单信息的request
	 * @param response 含有response的response
	 * @return 返回上传结果枚举类
	 */
	public UserUploadResult doUpload(HttpServletRequest request, HttpServletResponse response) {
		String localPath = LOCAL_DATA_PATH + "UPLOADED_FILE/";
		
		//判断登陆信息
		HttpSession session = request.getSession();
		String user_name = (String)session.getAttribute("user_name");
		if(user_name == null || user_name == "") {
			return UserUploadResult.USER_NAME_NOT_FOUND;
		}
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return UserUploadResult.USER_NAME_NOT_FOUND;
		}
		
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		//fileItemFactory.setSizeThreshold(10*1024);
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		//设置文件编码
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileItemsList = null;
		File file = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		//设置上传结果
		UserUploadResult uploadResult = UserUploadResult.UPLOAD_SUCCESS;
		try {
			
			//获取上传列表
			fileItemsList = upload.parseRequest(request);
			//获取上传文件对象和缩略图
			FileItem fileItem = null;
			//获取其他表单项
			HashMap<String, String> hashMap = new HashMap<String, String>();
			//找到文件输入项
			for(FileItem item: fileItemsList) {
				if(!item.isFormField()) {
					if(item.getFieldName().equals("file")) {
						fileItem = item;
					}
				}else {
					//获取普通表单项
					hashMap.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
			if(fileItem == null) {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//获取学院
			String college = hashMap.get("file_college");
			if(college == null) {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//路径添加学院文件夹
			localPath += college + "/";
			//判断本地文件夹路径是否存在，不存在则创建目录
			File fileLocalPathDir = new File(localPath);
			if(fileLocalPathDir.exists() == false || fileLocalPathDir.isDirectory() == false) {
				fileLocalPathDir.mkdirs();
			}
			//获取文件路径
			String filePath = fileItem.getName();
			//如果文件路径为空则返回错误
			if(filePath == null || filePath == "") {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//获取文件名
			String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
			String originFileName = fileName;
			//拼接本地文件路径
			//String fileSavePath = request.getSession().getServletContext().getRealPath("") + "/statics/UPLOADED_FILE/" + fileName;
			String fileSavePath = localPath + fileName;

			//获取文件后缀名
			//String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
			//判断文件后缀名正确性
			if(fileName.endsWith(".docx")
			|| fileName.endsWith(".doc")
			|| fileName.endsWith(".pptx")
			|| fileName.endsWith(".ppt")
			|| fileName.endsWith(".xlsx")
			|| fileName.endsWith(".xls")
			|| fileName.endsWith(".pdf")) {
				//do nothing
			}else {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//获取文件大小
			long file_size = fileItem.getSize();
			//判断大小正确性
			if(file_size > MAX_FILE_SIZE) {
				return UserUploadResult.FILE_SIZE_ERROR;
			}
			
			//设置上传id，用于查询进度
			String uploadId = session.getId() + filePath;
			//设置初始进度为0
			long progress = 0;
			UploadProgress.putProgress(uploadId, progress);
			
			//文件上传输入流
			inputStream = fileItem.getInputStream();
			//每次读取的buffer大小
			int readNum = 0;
			byte[] buffer = new byte[1024];
			//文件保存在/statics/UPLOADED_FILE下
			file = new File(fileSavePath);
			//如果文件存在，添加前缀直到路径不重复
			while(file.exists()) {
				fileName = "r_" + fileName;
				fileSavePath = localPath + fileName;
				file = null;
				file = new File(fileSavePath);
			}
			file.createNewFile();
			//文件保存输出流
			outputStream = new FileOutputStream(file);
			while((readNum = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer);
				progress += readNum;
				UploadProgress.putProgress(uploadId, progress);
			}
			//关闭输入输出流
			if(inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//向数据库插入文件记录
			if(insertFileInfo(request, hashMap, originFileName, fileSavePath, user_id, file_size, file) != 1) {
				uploadResult = UserUploadResult.UPLOAD_FAILED;
			}
			//移除此次进度
			UploadProgress.removeProgress(uploadId);
		}catch(Exception exception) {
			exception.printStackTrace();
			if(file != null) {
				//file.delete();
			}
			uploadResult = UserUploadResult.UPLOAD_FAILED;
			return uploadResult;
		}finally {
			//关闭输入输出流
			if(inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileRepeat) {
				file.delete();
			}
			
		}
		return uploadResult;
	}

	/**
	 * 函数用于获取上传进度
	 * @param 携带上传信息的request
	 * @return 返回已上传的数据量(Byte)
	 */
	public Long getUploadProgress(HttpServletRequest request) {
		String uploadId = request.getSession().getId() + request.getParameter("file_name");
		Long result = (Long)UploadProgress.getProgress(uploadId);
		return result;
	}

	/**
	 * 函数用于计算文件MD5值，补全日期等数据，向数据库插入文件信息，
	 * 比较现有文件的md5值，若有重复则删除该文件并将记录中的file_path改为现有文件的file_path
	 * @param request 携带现有文件信息的request
	 * @param file_path 文件存储路径
	 * @param user_name 上传者name
	 * @param file_size 文件大小
	 * @param file 当前文件
	 * @return 无错误返回1，否则返回0
	 */
	private int insertFileInfo(HttpServletRequest request, HashMap<String, String> hashMap,String file_name, String file_path, int user_id, Long file_size, File file) {

		String file_pic_path = null;
		//获取日期
		Timestamp date = DateUtil.getCurrentTimestamp();
		//获取文件名（简介中的名称）
		//String file_name = hashMap.get("file_name");
		//获取文件简介
		String file_abstract = hashMap.get("file_description");
		//获取学院专业
		String file_college = hashMap.get("file_college");
		String file_major = hashMap.get("file_major");
		//获取文件关键词
		List<String> keywordList = new ArrayList<String>();
		for(int i = 0; i < 3; i++) {
			keywordList.add(hashMap.get("keyword" + String.valueOf(i + 1)));
		}
		//计算md5作为文件名
		String file_md5 = MD5Util.getMd5ByPath(file_path);
		//向数据库请求学院专业和md5与当前文件相同的文件
		List<FileInfo> existedFileInfoList = userMapper.getExistedMD5FileInfo(file_md5);
		//如果存在相同文件则将当前文件删除，file_path替换为现有文件
		if(existedFileInfoList.size() != 0) {
			fileRepeat = true;
			file_path = existedFileInfoList.get(0).getFile_path();
			file_pic_path = existedFileInfoList.get(0).getFile_pic_path();
			int file_page_num = existedFileInfoList.get(0).getFile_page_num();
			//向数据库中插入记录并返回
			int file_id = insertFileInfoIntoDB(file_name, file_size, date, user_id, file_college, file_major, file_path, file_md5, file_abstract, file_pic_path, file_page_num, keywordList);
			if(file_id == 0) {
				return 0;
			}else {
				return 1;
			}
		}
		//计算后缀名，目录
		String file_dir_path = file_path.substring(0, file_path.lastIndexOf("/") + 1);
		String file_postfix = file_path.substring(file_path.lastIndexOf("."));
		file_path = file_dir_path + file_md5 + file_postfix;
		File renamedFile = new File(file_path);
		while(renamedFile.exists()) {
			file_dir_path += "r_";
			file_path = file_dir_path + file_md5 + file_postfix;
			renamedFile = null;
			renamedFile = new File(file_path);
		}
		//重命名文件
		file.renameTo(renamedFile);
		//取得文件相对路径
		String file_relative_path = file_path.substring(file_path.lastIndexOf("/UPLOADED_FILE"));
		//计算文件图片路径
		file_pic_path = file_relative_path + file_relative_path.substring(file_relative_path.lastIndexOf("/"),file_relative_path.lastIndexOf(".")) + "/";
		//向数据库中插入记录
		int file_id = insertFileInfoIntoDB(file_name, file_size, date, user_id, file_college, file_major, file_relative_path, file_md5, file_abstract, file_pic_path, 0, keywordList);

		
		
		//文件转PDF和图片
		try {
			//创建文件转PDF和图片线程
			fileToImgThread.setFileId(file_id);
			fileToImgThread.setTranFilePath(file_path);
			//启动线程
			new Thread(fileToImgThread).start();
			
		} catch(Exception exception) {
			exception.printStackTrace();
			return 1;
		}
		
		return 1;
	}
	
	/**
	 * 函数用于将文件数据插入数据库、将关键词插入数据库
	 * @return 返回文件id，失败返回0
	 */
	int insertFileInfoIntoDB(String file_name, long file_size, Timestamp date, int user_id
							, String file_college, String file_major, String file_path
							, String file_md5, String file_abstract
							, String file_pic_path, int file_page_num, List<String> keywordList) {
		int result = 0;
		//向数据库中插入记录
		FileInfo file_info = creatFileInfo(file_name, file_size, date, user_id, file_college, file_major, file_path, file_md5, file_abstract, file_pic_path, 0);
		result = userMapper.insertFileInfoByFileClass(file_info);
		if(result == 0) {
			return 0;
		}
		//获取自增id
		int file_id = file_info.getFile_id();
		//插入文件关键词
		for(int i = 0; i < 3; i++) {
			String key_word = keywordList.get(i);
			if(key_word != null && key_word.equals("") == false) {
				userMapper.insertFileKeyWord(file_id, key_word);
			}
		}
		
		return file_id;
	}
	
	/**
	 * 创建并初始化FileInfo类并返回
	 * @param file_name
	 * @param file_size
	 * @param date
	 * @param user_id
	 * @param file_college
	 * @param file_major
	 * @param file_path
	 * @param file_md5
	 * @param file_abstract
	 * @param file_pic_path
	 * @param file_page_num
	 * @return
	 */
	FileInfo creatFileInfo(String file_name, long file_size, Timestamp date, int user_id
							, String file_college, String file_major, String file_path
							, String file_md5, String file_abstract
							, String file_pic_path, int file_page_num) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFile_name(file_name);
		fileInfo.setFile_size(file_size);
		fileInfo.setFile_up_time(date);
		fileInfo.setFile_up_user_id(user_id);
		fileInfo.setFile_college(file_college);
		fileInfo.setFile_major(file_major);
		fileInfo.setFile_path(file_path);
		fileInfo.setFile_md5(file_md5);
		fileInfo.setFile_abstract(file_abstract);
		fileInfo.setFile_pic_path(file_pic_path);
		fileInfo.setFile_page_num(file_page_num);
		
		return fileInfo;
	}
	
	/**
	 * 向数据库更新头像路径,删除原头像
	 * @param user_id 用户id
	 * @param chathead_path 头像路径
	 * @return
	 */
	private int insertChatheadInfo(int user_id, String chathead_path, File chatheadFile) {
		//计算MD5作为文件名
		String file_name = MD5Util.getMd5ByPath(chathead_path);
		String file_dir_path = chathead_path.substring(0, chathead_path.lastIndexOf("/") + 1);
		String file_postfix = chathead_path.substring(chathead_path.lastIndexOf("."));
		//拼接文件路径
		chathead_path = file_dir_path + file_name + file_postfix;
		File renamedFile = new File(chathead_path);
		while(renamedFile.exists()) {
			file_name += "r";
			chathead_path = file_dir_path + file_name + file_postfix;
			renamedFile = null;
			renamedFile = new File(chathead_path);
		}
		//重命名文件
		chatheadFile.renameTo(renamedFile);
		//向数据库中获取原头像路径
		String old_chathead_path = LOCAL_CHATHEAD_PATH + userMapper.getUserChatheadById(user_id);
		//向数据库中更新新头像相对路径
		int result = userMapper.updateChatheadInfo(user_id, chathead_path.substring(chathead_path.lastIndexOf("/") + 1));
		//删除原头像
		if(old_chathead_path == null || old_chathead_path.equals("")) {
			//do nothing
		}else {
			File oldChatheadFile = new File(old_chathead_path);
			if(oldChatheadFile.exists()) {
				try {
					oldChatheadFile.delete();
				} catch (Exception e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
		return result;
	}
	
	/**
	 * 函数用于上传头像
	 * @param request 含有上传文件表单信息的request
	 * @param response 含有response的response
	 * @return 返回上传结果枚举类
	 */
	public UserUploadResult doUploadChathead(HttpServletRequest request, HttpServletResponse response) {
		String localPath = LOCAL_DATA_PATH + "USER_CHATHEAD/";
		
		//判断登陆信息
		HttpSession session = request.getSession();
		String user_name = (String)session.getAttribute("user_name");
		if(user_name == null || user_name == "") {
			return UserUploadResult.USER_NAME_NOT_FOUND;
		}
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return UserUploadResult.USER_NAME_NOT_FOUND;
		}
		
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		//fileItemFactory.setSizeThreshold(10*1024);
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		//设置文件编码
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileItemsList = null;
		File file = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		//设置上传结果
		UserUploadResult uploadResult = UserUploadResult.UPLOAD_SUCCESS;
		try {
			
			//获取上传列表
			fileItemsList = upload.parseRequest(request);
			//获取上传文件对象
			FileItem fileItem = null;
			//获取其他表单项
			HashMap<String, String> hashMap = new HashMap<String, String>();
			//找到文件输入项
			for(FileItem item: fileItemsList) {
				if(!item.isFormField()) {
					if(item.getFieldName().equals("file")) {
						fileItem = item;
					}
				}else {
					//获取普通表单项
					hashMap.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
			if(fileItem == null) {
				return UserUploadResult.UPLOAD_FAILED;
			}
			
			//获取文件路径
			String filePath = fileItem.getName();
			//如果文件路径为空则返回错误
			if(filePath == null || filePath == "") {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//获取文件名
			String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
			//获取文件后缀名
			//String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
			//获取文件大小
			long file_size = fileItem.getSize();
			//判断后缀名和大小正确性
			if(fileName.endsWith(".jpg")
			|| fileName.endsWith(".jpeg")
			|| fileName.endsWith(".png")
			|| fileName.endsWith(".gif")) {
				//do nothing
			}else {
				return UserUploadResult.UPLOAD_FAILED;
			}
			if(file_size > MAX_CHATHEAD_SIZE) {
				return UserUploadResult.FILE_SIZE_ERROR;
			}
			if(hashMap.get("user_id").equals(String.valueOf((Integer)request.getSession().getAttribute("user_id"))) == false) {
				return UserUploadResult.USER_NAME_NOT_FOUND;
			}
			
			//设置上传id，用于查询进度
			String uploadId = session.getId() + filePath;
			//设置初始进度为0
			long progress = 0;
			UploadProgress.putProgress(uploadId, progress);
			
			//文件上传输入流
			inputStream = fileItem.getInputStream();
			//每次读取的buffer大小
			int readNum = 0;
			byte[] buffer = new byte[1024];
			//拼接本地文件路径
			//String fileSavePath = request.getSession().getServletContext().getRealPath("") + "/statics/UPLOADED_FILE/" + fileName;
			String fileSavePath = localPath + fileName;
			//文件保存在/statics/UPLOADED_FILE下
			file = new File(fileSavePath);
			//如果文件存在，添加前缀直到路径不重复
			while(file.exists()) {
				fileName = "r_" + fileName;
				fileSavePath = localPath + fileName;
				file = null;
				file = new File(fileSavePath);
			}
			file.createNewFile();
			//文件保存输出流
			outputStream = new FileOutputStream(file);
			while((readNum = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer);
				progress += readNum;
				UploadProgress.putProgress(uploadId, progress);
			}
			//关闭输入输出流
			if(inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//向数据库更新头像信息
			if(insertChatheadInfo(user_id, fileSavePath, file) != 1) {
				uploadResult = UserUploadResult.UPLOAD_FAILED;
			}
			//移除此次进度
			UploadProgress.removeProgress(uploadId);
		}catch(Exception exception) {
			exception.printStackTrace();
			if(file != null) {
				file.delete();
			}
			uploadResult = UserUploadResult.UPLOAD_FAILED;
			return uploadResult;
		}finally {
			//关闭输入输出流
			if(inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fileRepeat) {
				file.delete();
			}
			
		}
		return uploadResult;
	}
}
