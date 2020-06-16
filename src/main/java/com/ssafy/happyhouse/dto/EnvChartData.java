package com.ssafy.happyhouse.dto;

public class EnvChartData {
	private String type;
	private int count;
	
	public EnvChartData() {}

	public EnvChartData(String type, int count) {
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
		return "EnvChartData [type=" + type + ", count=" + count + "]";
	}
	
	
}
