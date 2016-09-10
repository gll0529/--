package com.wuxianedu.been;

import java.io.Serializable;

public class User implements Serializable{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6741808007054331841L;
	private String userId;
	private String userName;
	private Long userPhoneNum;
	private String head;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getUserPhoneNum() {
		return userPhoneNum;
	}
	public void setUserPhoneNum(Long userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}

	
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
}
