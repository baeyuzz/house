package com.ssafy.happyhouse.dto;

public class Reply {
	int no;
	int qno;
	String comment;
	String writer;
	String reg_date;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getQno() {
		return qno;
	}

	public void setQno(int qno) {
		this.qno = qno;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public Reply(int no, int qno, String comment, String writer, String reg_date) {
		super();
		this.no = no;
		this.qno = qno;
		this.comment = comment;
		this.writer = writer;
		this.reg_date = reg_date;
	}

	public Reply(int qno, String writer, String comment	) {
		this.qno = qno;
		this.comment = comment;
		this.writer = writer;	
		}

}
