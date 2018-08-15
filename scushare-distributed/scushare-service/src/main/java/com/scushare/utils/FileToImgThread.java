package com.scushare.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.scushare.file2image.File2Image;
import com.scushare.mapper.FileRecMapper;

/**
 * 线程类，用于创建新线程执行运行文件转图片函数
 *
 */
@Component
public class FileToImgThread{

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
	
	@Async
	public void run(int fileId, String tranFilePath) {
		//转图片并获取页数
		int pageNum = File2Image.transform(tranFilePath);
		
		//向数据库更新页数
		//FileToImgThreadUtil.updateFilePageNum(pageNum, pageNum);
		fileRecMapper.updateFilePageNum(pageNum, fileId);
	}
}
