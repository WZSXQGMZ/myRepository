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
	 * ���������ϴ��ļ�
	 * @param request �����ϴ��ļ�������Ϣ��request
	 * @param response ����response��response
	 * @return �����ϴ����ö����
	 */
	public UserUploadResult doUpload(HttpServletRequest request, HttpServletResponse response) {
		String localPath = LOCAL_DATA_PATH + "UPLOADED_FILE/";
		
		//�жϵ�½��Ϣ
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
		//�����ļ�����
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileItemsList = null;
		File file = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		//�����ϴ����
		UserUploadResult uploadResult = UserUploadResult.UPLOAD_SUCCESS;
		try {
			
			//��ȡ�ϴ��б�
			fileItemsList = upload.parseRequest(request);
			//��ȡ�ϴ��ļ����������ͼ
			FileItem fileItem = null;
			//��ȡ����������
			HashMap<String, String> hashMap = new HashMap<String, String>();
			//�ҵ��ļ�������
			for(FileItem item: fileItemsList) {
				if(!item.isFormField()) {
					if(item.getFieldName().equals("file")) {
						fileItem = item;
					}
				}else {
					//��ȡ��ͨ������
					hashMap.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
			if(fileItem == null) {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//��ȡѧԺ
			String college = hashMap.get("file_college");
			if(college == null) {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//·������ѧԺ�ļ���
			localPath += college + "/";
			//�жϱ����ļ���·���Ƿ���ڣ��������򴴽�Ŀ¼
			File fileLocalPathDir = new File(localPath);
			if(fileLocalPathDir.exists() == false || fileLocalPathDir.isDirectory() == false) {
				fileLocalPathDir.mkdirs();
			}
			//��ȡ�ļ�·��
			String filePath = fileItem.getName();
			//����ļ�·��Ϊ���򷵻ش���
			if(filePath == null || filePath == "") {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//��ȡ�ļ���
			String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
			String originFileName = fileName;
			//ƴ�ӱ����ļ�·��
			//String fileSavePath = request.getSession().getServletContext().getRealPath("") + "/statics/UPLOADED_FILE/" + fileName;
			String fileSavePath = localPath + fileName;

			//��ȡ�ļ���׺��
			//String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
			//�ж��ļ���׺����ȷ��
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
			//��ȡ�ļ���С
			long file_size = fileItem.getSize();
			//�жϴ�С��ȷ��
			if(file_size > MAX_FILE_SIZE) {
				return UserUploadResult.FILE_SIZE_ERROR;
			}
			
			//�����ϴ�id�����ڲ�ѯ����
			String uploadId = session.getId() + filePath;
			//���ó�ʼ����Ϊ0
			long progress = 0;
			UploadProgress.putProgress(uploadId, progress);
			
			//�ļ��ϴ�������
			inputStream = fileItem.getInputStream();
			//ÿ�ζ�ȡ��buffer��С
			int readNum = 0;
			byte[] buffer = new byte[1024];
			//�ļ�������/statics/UPLOADED_FILE��
			file = new File(fileSavePath);
			//����ļ����ڣ�����ǰ׺ֱ��·�����ظ�
			while(file.exists()) {
				fileName = "r_" + fileName;
				fileSavePath = localPath + fileName;
				file = null;
				file = new File(fileSavePath);
			}
			file.createNewFile();
			//�ļ����������
			outputStream = new FileOutputStream(file);
			while((readNum = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer);
				progress += readNum;
				UploadProgress.putProgress(uploadId, progress);
			}
			//�ر����������
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
			//�����ݿ�����ļ���¼
			if(insertFileInfo(request, hashMap, originFileName, fileSavePath, user_id, file_size, file) != 1) {
				uploadResult = UserUploadResult.UPLOAD_FAILED;
			}
			//�Ƴ��˴ν���
			UploadProgress.removeProgress(uploadId);
		}catch(Exception exception) {
			exception.printStackTrace();
			if(file != null) {
				//file.delete();
			}
			uploadResult = UserUploadResult.UPLOAD_FAILED;
			return uploadResult;
		}finally {
			//�ر����������
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
	 * �������ڻ�ȡ�ϴ�����
	 * @param Я���ϴ���Ϣ��request
	 * @return �������ϴ���������(Byte)
	 */
	public Long getUploadProgress(HttpServletRequest request) {
		String uploadId = request.getSession().getId() + request.getParameter("file_name");
		Long result = (Long)UploadProgress.getProgress(uploadId);
		return result;
	}

	/**
	 * �������ڼ����ļ�MD5ֵ����ȫ���ڵ����ݣ������ݿ�����ļ���Ϣ��
	 * �Ƚ������ļ���md5ֵ�������ظ���ɾ�����ļ�������¼�е�file_path��Ϊ�����ļ���file_path
	 * @param request Я�������ļ���Ϣ��request
	 * @param file_path �ļ��洢·��
	 * @param user_name �ϴ���name
	 * @param file_size �ļ���С
	 * @param file ��ǰ�ļ�
	 * @return �޴��󷵻�1�����򷵻�0
	 */
	private int insertFileInfo(HttpServletRequest request, HashMap<String, String> hashMap,String file_name, String file_path, int user_id, Long file_size, File file) {

		String file_pic_path = null;
		//��ȡ����
		Timestamp date = DateUtil.getCurrentTimestamp();
		//��ȡ�ļ���������е����ƣ�
		//String file_name = hashMap.get("file_name");
		//��ȡ�ļ����
		String file_abstract = hashMap.get("file_description");
		//��ȡѧԺרҵ
		String file_college = hashMap.get("file_college");
		String file_major = hashMap.get("file_major");
		//��ȡ�ļ��ؼ���
		List<String> keywordList = new ArrayList<String>();
		for(int i = 0; i < 3; i++) {
			keywordList.add(hashMap.get("keyword" + String.valueOf(i + 1)));
		}
		//����md5��Ϊ�ļ���
		String file_md5 = MD5Util.getMd5ByPath(file_path);
		//�����ݿ�����ѧԺרҵ��md5�뵱ǰ�ļ���ͬ���ļ�
		List<FileInfo> existedFileInfoList = userMapper.getExistedMD5FileInfo(file_md5);
		//���������ͬ�ļ��򽫵�ǰ�ļ�ɾ����file_path�滻Ϊ�����ļ�
		if(existedFileInfoList.size() != 0) {
			fileRepeat = true;
			file_path = existedFileInfoList.get(0).getFile_path();
			file_pic_path = existedFileInfoList.get(0).getFile_pic_path();
			int file_page_num = existedFileInfoList.get(0).getFile_page_num();
			//�����ݿ��в����¼������
			int file_id = insertFileInfoIntoDB(file_name, file_size, date, user_id, file_college, file_major, file_path, file_md5, file_abstract, file_pic_path, file_page_num, keywordList);
			if(file_id == 0) {
				return 0;
			}else {
				return 1;
			}
		}
		//�����׺����Ŀ¼
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
		//�������ļ�
		file.renameTo(renamedFile);
		//ȡ���ļ����·��
		String file_relative_path = file_path.substring(file_path.lastIndexOf("/UPLOADED_FILE"));
		//�����ļ�ͼƬ·��
		file_pic_path = file_relative_path + file_relative_path.substring(file_relative_path.lastIndexOf("/"),file_relative_path.lastIndexOf(".")) + "/";
		//�����ݿ��в����¼
		int file_id = insertFileInfoIntoDB(file_name, file_size, date, user_id, file_college, file_major, file_relative_path, file_md5, file_abstract, file_pic_path, 0, keywordList);

		
		
		//�ļ�תPDF��ͼƬ
		try {
			//�����ļ�תPDF��ͼƬ�߳�
			fileToImgThread.setFileId(file_id);
			fileToImgThread.setTranFilePath(file_path);
			//�����߳�
			new Thread(fileToImgThread).start();
			
		} catch(Exception exception) {
			exception.printStackTrace();
			return 1;
		}
		
		return 1;
	}
	
	/**
	 * �������ڽ��ļ����ݲ������ݿ⡢���ؼ��ʲ������ݿ�
	 * @return �����ļ�id��ʧ�ܷ���0
	 */
	int insertFileInfoIntoDB(String file_name, long file_size, Timestamp date, int user_id
							, String file_college, String file_major, String file_path
							, String file_md5, String file_abstract
							, String file_pic_path, int file_page_num, List<String> keywordList) {
		int result = 0;
		//�����ݿ��в����¼
		FileInfo file_info = creatFileInfo(file_name, file_size, date, user_id, file_college, file_major, file_path, file_md5, file_abstract, file_pic_path, 0);
		result = userMapper.insertFileInfoByFileClass(file_info);
		if(result == 0) {
			return 0;
		}
		//��ȡ����id
		int file_id = file_info.getFile_id();
		//�����ļ��ؼ���
		for(int i = 0; i < 3; i++) {
			String key_word = keywordList.get(i);
			if(key_word != null && key_word.equals("") == false) {
				userMapper.insertFileKeyWord(file_id, key_word);
			}
		}
		
		return file_id;
	}
	
	/**
	 * ��������ʼ��FileInfo�ಢ����
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
	 * �����ݿ����ͷ��·��,ɾ��ԭͷ��
	 * @param user_id �û�id
	 * @param chathead_path ͷ��·��
	 * @return
	 */
	private int insertChatheadInfo(int user_id, String chathead_path, File chatheadFile) {
		//����MD5��Ϊ�ļ���
		String file_name = MD5Util.getMd5ByPath(chathead_path);
		String file_dir_path = chathead_path.substring(0, chathead_path.lastIndexOf("/") + 1);
		String file_postfix = chathead_path.substring(chathead_path.lastIndexOf("."));
		//ƴ���ļ�·��
		chathead_path = file_dir_path + file_name + file_postfix;
		File renamedFile = new File(chathead_path);
		while(renamedFile.exists()) {
			file_name += "r";
			chathead_path = file_dir_path + file_name + file_postfix;
			renamedFile = null;
			renamedFile = new File(chathead_path);
		}
		//�������ļ�
		chatheadFile.renameTo(renamedFile);
		//�����ݿ��л�ȡԭͷ��·��
		String old_chathead_path = LOCAL_CHATHEAD_PATH + userMapper.getUserChatheadById(user_id);
		//�����ݿ��и�����ͷ�����·��
		int result = userMapper.updateChatheadInfo(user_id, chathead_path.substring(chathead_path.lastIndexOf("/") + 1));
		//ɾ��ԭͷ��
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
	 * ���������ϴ�ͷ��
	 * @param request �����ϴ��ļ�������Ϣ��request
	 * @param response ����response��response
	 * @return �����ϴ����ö����
	 */
	public UserUploadResult doUploadChathead(HttpServletRequest request, HttpServletResponse response) {
		String localPath = LOCAL_DATA_PATH + "USER_CHATHEAD/";
		
		//�жϵ�½��Ϣ
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
		//�����ļ�����
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileItemsList = null;
		File file = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		//�����ϴ����
		UserUploadResult uploadResult = UserUploadResult.UPLOAD_SUCCESS;
		try {
			
			//��ȡ�ϴ��б�
			fileItemsList = upload.parseRequest(request);
			//��ȡ�ϴ��ļ�����
			FileItem fileItem = null;
			//��ȡ����������
			HashMap<String, String> hashMap = new HashMap<String, String>();
			//�ҵ��ļ�������
			for(FileItem item: fileItemsList) {
				if(!item.isFormField()) {
					if(item.getFieldName().equals("file")) {
						fileItem = item;
					}
				}else {
					//��ȡ��ͨ������
					hashMap.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
			if(fileItem == null) {
				return UserUploadResult.UPLOAD_FAILED;
			}
			
			//��ȡ�ļ�·��
			String filePath = fileItem.getName();
			//����ļ�·��Ϊ���򷵻ش���
			if(filePath == null || filePath == "") {
				return UserUploadResult.UPLOAD_FAILED;
			}
			//��ȡ�ļ���
			String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
			//��ȡ�ļ���׺��
			//String filePostfix = fileName.substring(fileName.lastIndexOf(".") + 1);
			//��ȡ�ļ���С
			long file_size = fileItem.getSize();
			//�жϺ�׺���ʹ�С��ȷ��
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
			
			//�����ϴ�id�����ڲ�ѯ����
			String uploadId = session.getId() + filePath;
			//���ó�ʼ����Ϊ0
			long progress = 0;
			UploadProgress.putProgress(uploadId, progress);
			
			//�ļ��ϴ�������
			inputStream = fileItem.getInputStream();
			//ÿ�ζ�ȡ��buffer��С
			int readNum = 0;
			byte[] buffer = new byte[1024];
			//ƴ�ӱ����ļ�·��
			//String fileSavePath = request.getSession().getServletContext().getRealPath("") + "/statics/UPLOADED_FILE/" + fileName;
			String fileSavePath = localPath + fileName;
			//�ļ�������/statics/UPLOADED_FILE��
			file = new File(fileSavePath);
			//����ļ����ڣ�����ǰ׺ֱ��·�����ظ�
			while(file.exists()) {
				fileName = "r_" + fileName;
				fileSavePath = localPath + fileName;
				file = null;
				file = new File(fileSavePath);
			}
			file.createNewFile();
			//�ļ����������
			outputStream = new FileOutputStream(file);
			while((readNum = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer);
				progress += readNum;
				UploadProgress.putProgress(uploadId, progress);
			}
			//�ر����������
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
			//�����ݿ����ͷ����Ϣ
			if(insertChatheadInfo(user_id, fileSavePath, file) != 1) {
				uploadResult = UserUploadResult.UPLOAD_FAILED;
			}
			//�Ƴ��˴ν���
			UploadProgress.removeProgress(uploadId);
		}catch(Exception exception) {
			exception.printStackTrace();
			if(file != null) {
				file.delete();
			}
			uploadResult = UserUploadResult.UPLOAD_FAILED;
			return uploadResult;
		}finally {
			//�ر����������
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