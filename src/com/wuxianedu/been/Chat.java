package com.wuxianedu.been;

import java.io.Serializable;

public class Chat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 579628194151221203L;
	private String imagePath;
	private String voicePath;
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getVoicePath() {
		return voicePath;
	}
	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}
	
}
