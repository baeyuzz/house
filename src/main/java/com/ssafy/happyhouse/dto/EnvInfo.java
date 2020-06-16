package com.ssafy.happyhouse.dto;

public class EnvInfo {
	private int no;
	private String name;
	private int code;
	private String type;
	private String address;
	private double lng;
	private double lat;
	private String sigudong;

	public EnvInfo() {
	}

	public EnvInfo(int no, String name, int code, String type, String address, double lng, double lat,
			String sigudong) {
		super();
		this.no = no;
		this.name = name;
		this.code = code;
		this.type = type;
		this.address = address;
		this.lng = lng;
		this.lat = lat;
		this.sigudong = sigudong;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getSigudong() {
		return sigudong;
	}

	public void setSigudong(String sigudong) {
		this.sigudong = sigudong;
	}

	@Override
	public String toString() {
		return "EnvInfo [no=" + no + ", name=" + name + ", code=" + code + ", type=" + type + ", address=" + address
				+ ", lng=" + lng + ", lat=" + lat + ", sigudong=" + sigudong + "]";
	}

	
}
