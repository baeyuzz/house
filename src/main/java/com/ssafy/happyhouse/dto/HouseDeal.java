package com.ssafy.happyhouse.dto;

import java.io.Serializable;

/**
 * @author minseong
 *
 */
public class HouseDeal implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String[] typeToString = { "", "아파트 매매", "연립,주택 매매", "아파트 전월세", "연립,주택 전월세" };

	/** 거래 정보를 식별할 번호 */
	private int no;

	/** 주소 */
	private String address;
	/** 거래일 */
	private String dealDate;
	/** 아파트 이름 */
	private String aptName;
	/** 전용면적 */
	private double area;
	/** 층 */
	private int floor;

	/** 건축 연도 */
	private int buildYear;
	/** 거래 금액 */
	private int dealAmount;
	/** 임대료 */
	private int rentMoney;

	/**
	 * type 1 : 아파트 매매 2 : 연립 다세세 매매 3 : 아파트 전월세 4 : 연립 다세세 전월세
	 */
	private int type;

	private String typeString;

	/** houseinfo 와 join 해서 가져올 거 */
	private String lat;
	private String lng;

	/** 생성자 */
	public HouseDeal() {
	}

	public HouseDeal(int no) {
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

	public String getDealDate() {
		return dealDate;
	}

	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	public String getAptName() {
		return aptName;
	}

	public void setAptName(String aptName) {
		this.aptName = aptName;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}

	public int getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(int dealAmount) {
		this.dealAmount = dealAmount;
	}

	public int getRentMoney() {
		return rentMoney;
	}

	public void setRentMoney(int rentMoney) {
		this.rentMoney = rentMoney;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		this.typeString = HouseDeal.typeToString[type];
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "HouseDeal [no=" + no + ", address=" + address + ", dealDate=" + dealDate + ", aptName=" + aptName
				+ ", area=" + area + ", floor=" + floor + ", buildYear=" + buildYear + ", dealAmount=" + dealAmount
				+ ", rentMoney=" + rentMoney + ", type=" + type + ", typeString=" + typeString + ", lat=" + lat
				+ ", lng=" + lng + "]";
	}
}
