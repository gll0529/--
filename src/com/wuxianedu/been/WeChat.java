package com.wuxianedu.been;

import java.io.Serializable;
import java.util.List;

public class WeChat implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3502145566241496133L;
	private String name;
    private String area;
    private int weCode;
    private String head;
    private String autograph;
    private String lastStr;
    private int newsNum;
    private String lastTime;
    private String images;
    private List<String> list;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getWeCode() {
		return weCode;
	}
	public void setWeCode(int weCode) {
		this.weCode = weCode;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getLastStr() {
		return lastStr;
	}
	public void setLastStr(String lastStr) {
		this.lastStr = lastStr;
	}
	public int getNewsNum() {
		return newsNum;
	}
	public void setNewsNum(int newsNum) {
		this.newsNum = newsNum;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "WeChat [name=" + name + ", area=" + area + ", weCode=" + weCode + ", head=" + head + ", autograph="
				+ autograph + ", lastStr=" + lastStr + ", newsNum=" + newsNum + ", lastTime=" + lastTime + ", images="
				+ images + ", list=" + list + "]";
	}
	
    
}
