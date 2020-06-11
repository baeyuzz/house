package com.ssafy.happyhouse.dto;

import java.io.Serializable;

public class HouseDeal implements Serializable, Comparable<HouseDeal> {
	private static final long serialVersionUID = 1L;
	
	public static String[] typeToString = {"", "아파트 매매", "연립,주택 매매", "아파트 전월세", "연립,주택 전월세"};
	
	/**거래 정보를 식별할 번호*/
	private int    no;
	/**법정 동명 */
	private String dong;
	/**아파트 이름 */
	private String AptName;
	/**법정 동코드 */
	private String code;
	/**거래 금액 */
	private String dealAmount;
	/**건축 연도*/
	private String buildYear;
	/**거래 연도*/
	private String dealYear;
	/**거래 월 */
	private String dealMonth;
	/**거래 일 */
	private String dealDay;
	/**전용면적 */
	private String area;
	/**층*/
	private String floor;
	/**지번*/
	private String jibun;
	/**type
	 * 1 : 아파트 매매
	 * 2 : 연립 다세세 매매
	 * 3 : 아파트 전월세
	 * 4 : 연립 다세세 전월세
	 * */
	private String type;
	/**임대료*/
	private String rentMoney;
	
	private String typeString;

	/** houseinfo 와 join 해서 가져올 거 */
	private String lat;
	private String lng;
	private String img;
	
	public HouseDeal() {	}
	public HouseDeal(int no) {
		this.no = no;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getAptName() {
		return AptName;
	}
	public void setAptName(String aptName) {
		AptName = aptName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	public String getDealYear() {
		return dealYear;
	}
	public void setDealYear(String dealYear) {
		this.dealYear = dealYear;
	}
	public String getDealMonth() {
		return dealMonth;
	}
	public void setDealMonth(String dealMonth) {
		this.dealMonth = dealMonth;
	}
	public String getDealDay() {
		return dealDay;
	}
	public void setDealDay(String dealDay) {
		this.dealDay = dealDay;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		this.typeString = typeToString[Integer.parseInt(type)];
	}
	public String getRentMoney() {
		return rentMoney;
	}
	public void setRentMoney(String rentMoney) {
		this.rentMoney = rentMoney;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public int compareTo(HouseDeal o) {
		if(o!=null && o.dong != null&&dong!=null) {
			return dong.compareTo(o.dong);
		}
		return 0;
	}
	@Override
	public String toString() {
		return "HouseDeal [no=" + no + ", dong=" + dong + ", AptName=" + AptName + ", code=" + code + ", dealAmount="
				+ dealAmount + ", buildYear=" + buildYear + ", dealYear=" + dealYear + ", dealMonth=" + dealMonth
				+ ", dealDay=" + dealDay + ", area=" + area + ", floor=" + floor + ", lat=" + lat + ", lng=" + lng
				+ ", jibun=" + jibun + ", type=" + type + ", rentMoney=" + rentMoney + ", img=" + img + "]";
	}
	
}






