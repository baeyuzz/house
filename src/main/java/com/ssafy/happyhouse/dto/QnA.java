package com.ssafy.happyhouse.dto;

public class QnA {
	private int hitCount;
	private int qna_no;
	private String qna_title;
	private String qna_content;
	private String qna_userid;
	private String qna_datetime;
	private String reply_content;
	private String reply_datetime;
	private String reply_userid;
	private int replyCnt;

	public int getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}

	public QnA(int hitCount, int qna_no, String qna_title, String qna_content, String qna_userid, String qna_datetime,
			String reply_content, String reply_datetime, String reply_userid, int replyCnt) {
		super();
		this.hitCount = hitCount;
		this.qna_no = qna_no;
		this.qna_title = qna_title;
		this.qna_content = qna_content;
		this.qna_userid = qna_userid;
		this.qna_datetime = qna_datetime;
		this.reply_content = reply_content;
		this.reply_datetime = reply_datetime;
		this.reply_userid = reply_userid;
		this.replyCnt = replyCnt;
	}

	public QnA() {
	}

	public QnA(int qna_no, String qna_title, String qna_content, String qna_userid, String qna_datetime,
			String reply_content, String reply_datetime, String reply_userid, int hitCount) {
		this.qna_no = qna_no;
		this.qna_title = qna_title;
		this.qna_content = qna_content;
		this.qna_userid = qna_userid;
		this.qna_datetime = qna_datetime;
		this.reply_content = reply_content;
		this.reply_datetime = reply_datetime;
		this.reply_userid = reply_userid;
		this.hitCount = hitCount;
	}

	public int getQna_no() {
		return qna_no;
	}

	public void setQna_no(int qna_no) {
		this.qna_no = qna_no;
	}

	public String getQna_title() {
		return qna_title;
	}

	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}

	public String getQna_content() {
		return qna_content;
	}

	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}

	public String getQna_userid() {
		return qna_userid;
	}

	public void setQna_userid(String qna_userid) {
		this.qna_userid = qna_userid;
	}

	public String getQna_datetime() {
		return qna_datetime;
	}

	public void setQna_datetime(String qna_datetime) {
		this.qna_datetime = qna_datetime;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public String getReply_datetime() {
		return reply_datetime;
	}

	public void setReply_datetime(String reply_datetime) {
		this.reply_datetime = reply_datetime;
	}

	public String getReply_userid() {
		return reply_userid;
	}

	public void setReply_userid(String reply_userid) {
		this.reply_userid = reply_userid;
	}

	@Override
	public String toString() {
		return "QnA [qna_no=" + qna_no + ", qna_title=" + qna_title + ", qna_content=" + qna_content + ", qna_userid="
				+ qna_userid + ", qna_datetime=" + qna_datetime + ", reply_content=" + reply_content
				+ ", reply_datetime=" + reply_datetime + ", reply_userid=" + reply_userid + "]";
	}

	public int getHitCount() {
		return hitCount;
	}

	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}

}
