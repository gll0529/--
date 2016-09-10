package com.wuxianedu.been;

import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable{

	

    /**
	 * 
	 */
	private static final long serialVersionUID = 3444302504220925168L;
	private String name;
    private String pinYinName;//全拼名字
    private String nameFrist;//每个字首字母的名字
    private String nFrist;//首字母的名字
    private String area;
    private int weCode;
	private String head;
    private String autograph;
    private String lastStr;
    private int newsNum;
    private String lastTime;
    private List<String> images;
    private boolean isCheck;
    private float alpha = 1.0f;
    
    
    
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinYinName() {
		return pinYinName;
	}
	public void setPinYinName(String pinYinName) {
		this.pinYinName = pinYinName;
	}
	public String getNameFrist() {
		return nameFrist;
	}
	public void setNameFrist(String nameFrist) {
		this.nameFrist = nameFrist;
	}
	public String getnFrist() {
		return nFrist;
	}
	public void setnFrist(String nFrist) {
		this.nFrist = nFrist;
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
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "Contact [name=" + name + ", pinYinName=" + pinYinName + ", nameFrist=" + nameFrist + ", nFrist="
				+ nFrist + ", area=" + area + ", weCode=" + weCode + ", head=" + head + ", autograph=" + autograph
				+ ", lastStr=" + lastStr + ", newsNum=" + newsNum + ", lastTime=" + lastTime + ", images=" + images
				+ "]";
	}
	
    
    

}
