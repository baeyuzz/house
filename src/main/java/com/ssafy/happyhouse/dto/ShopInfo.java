package com.ssafy.happyhouse.dto;

public class ShopInfo {
	private String no; // 고유번호
	private String shopName; // 상가이름
	private String localName; // 지점이름
	private String code1; // 분류코드 1
	private String codename1; // 분류이름 1
	private String code2; // 분류코드 2
	private String codename2; // 분류이름 2
	private String code3; // 분류코드 3
	private String codename3; // 분류이름 3
	private String code4; // 분류코드 4
	private String codename4; // 분류이름 4
	private String citycode; // 도시코드
	private String city; // 도시이름
	private String gucode; // 구 코드
	private String gu; // 구 이름
	private String dongcode; // 동 코드
	private String dong; // 동 이름
	private String jibuaddress; // 지번주소
	private String address; // 도로명주소
	private String oldpostcode; // 과거 우편번호
	private String postcode; // 현재 우편번호
	private String lng; // 위도
	private String lat; // 경도

	public ShopInfo() {}
	
	public ShopInfo(String no, String shopName, String localName, String code1, String codename1, String code2,
			String codename2, String code3, String codename3, String code4, String codename4, String citycode,
			String city, String gucode, String gu, String dongcode, String dong, String jibuaddress, String address,
			String oldpostcode, String postcode, String lng, String lat) {
		this.no = no;
		this.shopName = shopName;
		this.localName = localName;
		this.code1 = code1;
		this.codename1 = codename1;
		this.code2 = code2;
		this.codename2 = codename2;
		this.code3 = code3;
		this.codename3 = codename3;
		this.code4 = code4;
		this.codename4 = codename4;
		this.citycode = citycode;
		this.city = city;
		this.gucode = gucode;
		this.gu = gu;
		this.dongcode = dongcode;
		this.dong = dong;
		this.jibuaddress = jibuaddress;
		this.address = address;
		this.oldpostcode = oldpostcode;
		this.postcode = postcode;
		this.lng = lng;
		this.lat = lat;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public String getCodename1() {
		return codename1;
	}

	public void setCodename1(String codename1) {
		this.codename1 = codename1;
	}

	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public String getCodename2() {
		return codename2;
	}

	public void setCodename2(String codename2) {
		this.codename2 = codename2;
	}

	public String getCode3() {
		return code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	public String getCodename3() {
		return codename3;
	}

	public void setCodename3(String codename3) {
		this.codename3 = codename3;
	}

	public String getCode4() {
		return code4;
	}

	public void setCode4(String code4) {
		this.code4 = code4;
	}

	public String getCodename4() {
		return codename4;
	}

	public void setCodename4(String codename4) {
		this.codename4 = codename4;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGucode() {
		return gucode;
	}

	public void setGucode(String gucode) {
		this.gucode = gucode;
	}

	public String getGu() {
		return gu;
	}

	public void setGu(String gu) {
		this.gu = gu;
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

	public String getJibuaddress() {
		return jibuaddress;
	}

	public void setJibuaddress(String jibuaddress) {
		this.jibuaddress = jibuaddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOldpostcode() {
		return oldpostcode;
	}

	public void setOldpostcode(String oldpostcode) {
		this.oldpostcode = oldpostcode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
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

}
