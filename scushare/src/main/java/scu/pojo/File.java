package scu.pojo;

import java.sql.Date;

public class File {
	
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the fileUpTime
	 */
	public Date getFileUpTime() {
		return fileUpTime;
	}

	/**
	 * @param fileUpTime the fileUpTime to set
	 */
	public void setFileUpTime(Date fileUpTime) {
		this.fileUpTime = fileUpTime;
	}

	/**
	 * @return the fileUpUserId
	 */
	public int getFileUpUserId() {
		return fileUpUserId;
	}

	/**
	 * @param fileUpUserId the fileUpUserId to set
	 */
	public void setFileUpUserId(int fileUpUserId) {
		this.fileUpUserId = fileUpUserId;
	}

	/**
	 * @return the fileColloge
	 */
	public String getFileColloge() {
		return fileColloge;
	}

	/**
	 * @param fileColloge the fileColloge to set
	 */
	public void setFileColloge(String fileColloge) {
		this.fileColloge = fileColloge;
	}

	/**
	 * @return the fileMajor
	 */
	public String getFileMajor() {
		return fileMajor;
	}

	/**
	 * @param fileMajor the fileMajor to set
	 */
	public void setFileMajor(String fileMajor) {
		this.fileMajor = fileMajor;
	}

	/**
	 * @return the fileIsdelete
	 */
	public String getFileIsdelete() {
		return fileIsdelete;
	}

	/**
	 * @param fileIsdelete the fileIsdelete to set
	 */
	public void setFileIsdelete(String fileIsdelete) {
		this.fileIsdelete = fileIsdelete;
	}

	/**
	 * @return the fileMark
	 */
	public int getFileMark() {
		return fileMark;
	}

	/**
	 * @param fileMark the fileMark to set
	 */
	public void setFileMark(int fileMark) {
		this.fileMark = fileMark;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileIntroduction
	 */
	public StringBuffer getFileIntroduction() {
		return fileIntroduction;
	}

	/**
	 * @param fileIntroduction the fileIntroduction to set
	 */
	public void setFileIntroduction(StringBuffer fileIntroduction) {
		this.fileIntroduction = fileIntroduction;
	}

	/**
	 * @return the fileCollectSum
	 */
	public int getFileCollectSum() {
		return fileCollectSum;
	}

	/**
	 * @param fileCollectSum the fileCollectSum to set
	 */
	public void setFileCollectSum(int fileCollectSum) {
		this.fileCollectSum = fileCollectSum;
	}

	/**
	 * @return the fileLikeSum
	 */
	public int getFileLikeSum() {
		return fileLikeSum;
	}

	/**
	 * @param fileLikeSum the fileLikeSum to set
	 */
	public void setFileLikeSum(int fileLikeSum) {
		this.fileLikeSum = fileLikeSum;
	}

	/**
	 * @return the fileReviewSum
	 */
	public int getFileReviewSum() {
		return fileReviewSum;
	}

	/**
	 * @param fileReviewSum the fileReviewSum to set
	 */
	public void setFileReviewSum(int fileReviewSum) {
		this.fileReviewSum = fileReviewSum;
	}

	/**
	 * @return the fileMd5
	 */
	public String getFileMd5() {
		return fileMd5;
	}

	/**
	 * @param fileMd5 the fileMd5 to set
	 */
	public void setFileMd5(String fileMd5) {
		this.fileMd5 = fileMd5;
	}

	/**
	 * @return the fileDownloadSum
	 */
	public int getFileDownloadSum() {
		return fileDownloadSum;
	}

	/**
	 * @param fileDownloadSum the fileDownloadSum to set
	 */
	public void setFileDownloadSum(int fileDownloadSum) {
		this.fileDownloadSum = fileDownloadSum;
	}
	

	public String getFileUsername() {
		return fileUsername;
	}

	public void setFileUsername(String fileUsername) {
		this.fileUsername = fileUsername;
	}


	private int fileId;
	
	private String fileName;
	
	private int fileSize;
	
	private Date fileUpTime;
	
	private int fileUpUserId;
	
	private String fileColloge;
	
	private String fileMajor;
	
	private String fileIsdelete;
	
	private int fileMark;
	
	private String filePath;
	
	private StringBuffer fileIntroduction;
	
	private int fileCollectSum;
	
	private int fileLikeSum;
	
	private int fileReviewSum;
	
	private String fileMd5;
	
	private int fileDownloadSum;
	
	private String fileUsername;

}
