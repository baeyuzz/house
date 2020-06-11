package com.ssafy.happyhouse.dto;

public class Notice {
	private int no;
	private String title;
	private String content;
	private int hitCount;
	private String userid;
	
	public Notice() {}
	
	public Notice(String title, String content, int hitCount, String userid) {
		this.title = title;
		this.content = content;
		this.hitCount = hitCount;
		this.setUserid(userid);
	}
	
	public Notice(int no, String title, String content) {
		this.no = no;
		this.title = title;
		this.content = content;
	}
	
	public Notice(int no, String title, String content, int hitCount, String userid) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hitCount = hitCount;
		this.userid = userid;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
