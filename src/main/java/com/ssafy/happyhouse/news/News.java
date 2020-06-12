package com.ssafy.happyhouse.news;

public class News {
	
	int no;
	String title;
	String link;
	String description;
	String pubDate;
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	@Override
	public String toString() {
		return "News [no=" + no + ", title=" + title + ", link=" + link + ", description=" + description + ", pubDate="
				+ pubDate + "]";
	}
	public News(int no, String title, String link, String description, String pubDate) {
		super();
		this.no = no;
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
	}
	
	
}
