package scu.pojo;

import java.sql.Date;

public class Download {
	
	/**
	 * @return the downloadId
	 */
	public int getDownloadId() {
		return downloadId;
	}

	/**
	 * @param downloadId the downloadId to set
	 */
	public void setDownloadId(int downloadId) {
		this.downloadId = downloadId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return user_id;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.user_id = userId;
	}

	/**
	 * @return the fileId
	 */
	public int getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the downloadTime
	 */
	public Date getDownloadTime() {
		return downloadTime;
	}

	/**
	 * @param downloadTime the downloadTime to set
	 */
	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}
	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	private int downloadId;
	
	private int user_id;
	
	private int fileId;
	
	private String userName;
	
	private Date downloadTime;
	
	//增加了属性：文件名，文件大小
	private String fileName;
	
	private long fileSize;
	

}
