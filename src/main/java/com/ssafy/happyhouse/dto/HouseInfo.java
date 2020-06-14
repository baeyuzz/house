package com.ssafy.happyhouse.dto;

import java.io.Serializable;

public class HouseInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 아파트를 식별할 번호 */
	private int no;
	/** 주소 */
	private String address;
	/** 아파트 이름 */
	private String aptName;

	/** 건축 연도 */
	private int buildYear;

	/** 위도, 경도 */
	private double lat;
	private double lng;

	/** 생성자 */
	public HouseInfo() {
	}

	public HouseInfo(int no) {
		super();
		this.no = no;
	}

	/** getter, setter */
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAptName() {
		return aptName;
	}

	public void setAptName(String aptName) {
		this.aptName = aptName;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "HouseInfo [no=" + no + ", address=" + address + ", aptName=" + aptName + ", buildYear=" + buildYear
				+ ", lat=" + lat + ", lng=" + lng + "]";
	}

}
