package com.ssafy.happyhouse.dto;

public class Crime {

	int code;
	String crime;
	int count;
	String year;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCrime() {
		return crime;
	}

	public void setCrime(String crime) {
		this.crime = crime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Crime [code=" + code + ", crime=" + crime + ", count=" + count + ", year=" + year + "]";
	}

	public Crime(int code, String crime, int count, String year) {
		super();
		this.code = code;
		this.crime = crime;
		this.count = count;
		this.year = year;
	}

}
