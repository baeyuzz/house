package com.ssafy.happyhouse.dto;

import java.io.Serializable;
import java.util.Arrays;

public class HousePageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int INTERVAL = 10;
	
	// Singleton
	private static HousePageBean bean;

	static {
		HousePageBean.INTERVAL = 10;
		bean = new HousePageBean();
	}

	public static HousePageBean getInstance() {
		return bean;
	}
	
	// 검색의 초기 설정
	public HousePageBean() {
		searchType = new boolean[4];
		for(int i = 0; i < 4; i++) {
			searchType[i] = true;
		}
		
		orderBy = "address";
		orderType = "ASC";
		
		start = 0;
		
		// sql 의 type in(??) 에 들어갈 숫자들을 문자열로 만들기
		// 이걸 mybatis 의 foreach 로 할만한 방법이 있나요
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < searchType.length; i++) {
			if (searchType[i]) {
				sb.append(i + 1).append(",");
			}
		}
		if (sb.toString().charAt(sb.length() - 1) == ',') {
			sb.setLength(sb.length() - 1);
		}

		typeInStr = sb.toString();
		
		interval = HousePageBean.INTERVAL;
	}
	
	private int interval;
	
	private String	address;
	private String  aptName;
	private String	orderBy;
	private String	orderType;
	
	private String typeInStr;
	/** 
	 * 0 : 아파트 매매
	 * 1 : 다세대 매매
	 * 2 : 아파트 전월세
	 * 3 : 다세대 전월세  
	 * */
	private boolean[] searchType;
	
	/**가져오기 시작할 행 번호*/
	private int 	start;

	
	/*
	 * 여기부터 메서드들(getter, setter)
	 */
	public boolean[] getSearchType() {
		return searchType;
	}
	public void setSearchType(boolean[] searchType) {
		this.searchType = searchType;
		
		// sql 의 type in(??) 에 들어갈 숫자들을 문자열로 만들기
		// 이걸 mybatis 의 foreach 로 할만한 방법이 있나요
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < searchType.length; i++) {
			if(searchType[i]) {
				sb.append(i + 1).append(",");
			}
		}
		if(sb.toString().charAt(sb.length() - 1) == ',') {
			sb.setLength(sb.length()-1); 
		}

		typeInStr = sb.toString();
	}

	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}

	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}	
	
	public String getTypeInStr() {
		return typeInStr;
	}
	public void setTypeInStr(String typeInStr) {
		this.typeInStr = typeInStr;
	}
	
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
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

	@Override
	public String toString() {
		return "HousePageBean [interval=" + interval + ", address=" + address + ", aptName=" + aptName + ", orderBy="
				+ orderBy + ", orderType=" + orderType + ", typeInStr=" + typeInStr + ", searchType="
				+ Arrays.toString(searchType) + ", start=" + start + "]";
	}

}
