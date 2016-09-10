package com.wuxianedu.been;

import java.io.Serializable;

public class SubscribeInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7786001617703400615L;
	
    private String title;
	private String image;
	private String content;
	private String time;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "SubscribeInformation [title=" + title + ", image=" + image + ", content=" + content + ", time=" + time
				+ "]";
	}
	
	
}
