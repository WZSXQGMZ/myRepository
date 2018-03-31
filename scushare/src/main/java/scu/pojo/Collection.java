package scu.pojo;

import java.sql.Date;

public class Collection {
	
	/**
	 * @return the collectionId
	 */
	public int getCollectionId() {
		return collectionId;
	}

	/**
	 * @param collectionId the collectionId to set
	 */
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
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
	 * @return the collectionTime
	 */
	public Date getCollectionTime() {
		return collectionTime;
	}

	/**
	 * @param collectionTime the collectionTime to set
	 */
	public void setCollectionTime(Date collectionTime) {
		this.collectionTime = collectionTime;
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

	private int collectionId;
	
	private int userId;
	
	private int fileId;
	
	private Date collectionTime;
	
	private String userName;

}
