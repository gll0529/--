package com.wuxianedu.been;

import java.io.Serializable;
import java.util.List;

public class FriendQuan implements Serializable{
	private String username;
	private String head;
	private String content;
	private String time;
	private List<String> list;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "FriendQuan [username=" + username + ", head=" + head + ", content=" + content + ", time=" + time
				+ ", list=" + list + "]";
	}
	
}
