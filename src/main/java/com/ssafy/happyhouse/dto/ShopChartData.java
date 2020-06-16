package com.ssafy.happyhouse.dto;

public class ShopChartData {
	private String type;
	private int count;
	
	public ShopChartData() {
		
	}
	
	public ShopChartData(String type, int count) {
		super();
		this.type = type;
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ShopChartData [type=" + type + ", count=" + count + "]";
	}
	
	
}
