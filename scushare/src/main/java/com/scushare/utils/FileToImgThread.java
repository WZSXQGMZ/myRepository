package com.scushare.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scushare.file2image.File2Image;
import com.scushare.mapper.FileRecMapper;

/**
 * �߳��࣬���ڴ������߳�ִ�������ļ�תͼƬ����
 *
 */
@Component
public class FileToImgThread implements Runnable{

	private String tranFilePath = null;
	private int fileId = 0;
	
	public String getTranFilePath() {
		return tranFilePath;
	}

	public void setTranFilePath(String tranFilePath) {
		this.tranFilePath = tranFilePath;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

/*	public FileToImgThread(String file_path, int file_id) {
		this.tranFilePath = file_path;
		this.fileId = file_id;
	}*/
	
	@Autowired
	FileRecMapper fileRecMapper;
	
	@Override
	public void run() {
		//תͼƬ����ȡҳ��
		int pageNum = File2Image.transform(tranFilePath);
		
		//�����ݿ����ҳ��
		//FileToImgThreadUtil.updateFilePageNum(pageNum, pageNum);
		fileRecMapper.updateFilePageNum(pageNum, fileId);
	}
}
