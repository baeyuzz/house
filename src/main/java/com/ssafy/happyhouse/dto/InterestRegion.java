package com.ssafy.happyhouse.dto;

public class InterestRegion {
	private int no;
	private String province;
	private String city;
	private String dong;
	private String id;

	public InterestRegion(int no, String province, String city, String dong, String id) {
		this.no = no;
		this.province = province;
		this.city = city;
		this.dong = dong;
		this.id = id;
	}
	
	public InterestRegion(String province, String city, String dong, String id) {
		this.province = province;
		this.city = city;
		this.dong = dong;
		this.id = id;
	}

	public InterestRegion() {
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "InterestRegion [no=" + no + ", province=" + province + ", city=" + city + ", dong=" + dong + ", id="
				+ id + "]";
	}
}
