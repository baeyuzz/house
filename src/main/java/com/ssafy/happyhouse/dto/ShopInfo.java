package com.ssafy.happyhouse.dto;

public class ShopInfo {
	private int no;
	private String name;
	private String type1;
	private String type2;
	private String type3;
	private String type4;
	private String oldAddr;
	private String address;
	private String sigudong;
	private double lng;
	private double lat;

	public ShopInfo() {}

	public ShopInfo(int no, String name, String type1, String type2, String type3, String type4, String oldAddr,
			String address, String sigudong, double lng, double lat) {
		super();
		this.no = no;
		this.name = name;
		this.type1 = type1;
		this.type2 = type2;
		this.type3 = type3;
		this.type4 = type4;
		this.oldAddr = oldAddr;
		this.address = address;
		this.sigudong = sigudong;
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

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getType3() {
		return type3;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	public String getType4() {
		return type4;
	}

	public void setType4(String type4) {
		this.type4 = type4;
	}

	public String getOldAddr() {
		return oldAddr;
	}

	public void setOldAddr(String oldAddr) {
		this.oldAddr = oldAddr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSigudong() {
		return sigudong;
	}

	public void setSigudong(String sigudong) {
		this.sigudong = sigudong;
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

	@Override
	public String toString() {
		return "ShopInfo [no=" + no + ", name=" + name + ", type1=" + type1 + ", type2=" + type2 + ", type3=" + type3
				+ ", type4=" + type4 + ", oldAddr=" + oldAddr + ", address=" + address + ", sigudong=" + sigudong
				+ ", lng=" + lng + ", lat=" + lat + "]";
	}
}
