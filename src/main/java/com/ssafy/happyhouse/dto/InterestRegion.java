package com.ssafy.happyhouse.dto;

public class InterestRegion {
	
	private String sigungu;
	private String userid;
	
	public InterestRegion() {
	}

	public InterestRegion(String sigungu, String userid) {
		super();
		this.sigungu = sigungu;
		this.userid = userid;
	}

	public String getSigungu() {
		return sigungu;
	}

	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "InterestRegion [sigungu=" + sigungu + ", userid=" + userid + "]";
	}

}
