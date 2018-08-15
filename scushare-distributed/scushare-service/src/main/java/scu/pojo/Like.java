package scu.pojo;

import java.sql.Date;

public class Like {
	
	
	/**
	 * @return the likeId
	 */
	public int getLikeId() {
		return likeId;
	}

	/**
	 * @param likeId the likeId to set
	 */
	public void setLikeId(int likeId) {
		this.likeId = likeId;
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
	 * @return the likeTime
	 */
	public Date getLikeTime() {
		return likeTime;
	}

	/**
	 * @param likeTime the likeTime to set
	 */
	public void setLikeTime(Date likeTime) {
		this.likeTime = likeTime;
	}

	/**
	 * @return the userTame
	 */
	public String getUserTame() {
		return userTame;
	}

	/**
	 * @param userTame the userTame to set
	 */
	public void setUserTame(String userTame) {
		this.userTame = userTame;
	}
	
	private int likeId;
	
	private int userId;
	
	private int fileId;
	
	private Date likeTime;
	
	private String userTame;

}
