package com.ssafy.happyhouse.dto;

public class EnvInfo {
	private int no;
	private String name;
	private String bizcode;
	private String address;
	private String dongcode;
	private String dong;
	private String lng;
	private String lat;

	public EnvInfo() {
	}

	public EnvInfo(int no, String name, String bizcode, String address, String dongcode, String dong, String lng,
			String lat) {
		this.no = no;
		this.name = name;
		this.bizcode = bizcode;
		this.address = address;
		this.dongcode = dongcode;
		this.dong = dong;
		this.lng = lng;
		this.lat = lat;
	}

	public EnvInfo(String name, String bizcode, String address, String dongcode, String dong, String lng, String lat) {
		this.name = name;
		this.bizcode = bizcode;
		this.address = address;
		this.dongcode = dongcode;
		this.dong = dong;
		this.lng = lng;
		this.lat = lat;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDongcode() {
		return dongcode;
	}

	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}

	public String getDong() {
		return dong;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "EnvInfo [no=" + no + ", name=" + name + ", bizcode=" + bizcode + ", address=" + address + ", dongcode="
				+ dongcode + ", dong=" + dong + ", lng=" + lng + ", lat=" + lat + "]";
	}

	
}
