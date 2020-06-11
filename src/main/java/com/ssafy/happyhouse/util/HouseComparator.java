package com.ssafy.happyhouse.util;

import java.util.Comparator;

import com.ssafy.happyhouse.dto.HouseDeal;

public class HouseComparator {
	
	public static Comparator<HouseDeal> getComparator(int by) {
		switch(by) {
		case 1:
			return new NoComparator();
		case 2:
			return new DongComparator();
		case 3:
			return new AptComparator();
		case 4:
			return new BuildYearComparator();
		case 5:
			return new DealAmountComparator();
		case 6:
			return new DealYearComparator();
		case 7:
			return new DealMonthComparator();
		case 8:
			return new AreaComparator();
		case 9:
			return new FloorComparator();
		case 10:
			return new TypeComparator();
		case 11:
			return new RentMoneyComparator();
		}
		return new NoComparator();
	}
	
	private static class NoComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			return Integer.compare(o1.getNo(), o2.getNo());
		}
	}
	
	private static class DongComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			if(o1.getDong() == null) return 1;
			if(o2.getDong() == null) return -1;
			return o1.getDong().compareTo(o2.getDong());
		}
		
	}
	
	private static class AptComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			if(o1.getAptName() == null) return 1;
			if(o2.getAptName() == null) return -1;
			return o1.getAptName().compareTo(o2.getAptName());
		}
		
	}
	
	private static class BuildYearComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			return o1.getBuildYear().compareTo(o2.getBuildYear());
		}
		
	}
	
	private static class DealAmountComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			String a1 = o1.getDealAmount();
			String a2 = o2.getDealAmount();
			if(a1 == null) a1 = "0";
			if(a2 == null) a2 = "0";
			a1 = a1.replaceAll(",", "");
			a2 = a2.replace(",", "");
			int m1 = Integer.parseInt(a1.trim());
			int m2 = Integer.parseInt(a2.trim());
			return Integer.compare(m1, m2);
		}
		
	}
	
	private static class DealYearComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			return o1.getDealYear().compareTo(o2.getDealYear());
		}
		
	}
	
	private static class DealMonthComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			return o1.getDealMonth().compareTo(o2.getDealMonth());
		}
		
	}
	
	private static class AreaComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			Double d1 = Double.parseDouble(o1.getArea());
			Double d2 = Double.parseDouble(o2.getArea());
			return Double.compare(d1, d2);
		}
		
	}
	
	private static class FloorComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			return o1.getFloor().compareTo(o2.getFloor());
		}
		
	}
	
	private static class TypeComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			if(o1.getTypeString() == null) return 1;
			if(o2.getTypeString() == null) return -1;
			return o1.getTypeString().compareTo(o2.getTypeString());
		}
		
	}
	
	private static class RentMoneyComparator implements Comparator<HouseDeal> {

		@Override
		public int compare(HouseDeal o1, HouseDeal o2) {
			String r1 = o1.getRentMoney();
			String r2 = o2.getRentMoney();
			if(r1 == null) r1 = "0";
			if(r2 == null) r2 = "0";
			int m1 = Integer.parseInt(r1.trim());
			int m2 = Integer.parseInt(r2.trim());
			return Integer.compare(m1, m2);
		}
		
	}
}
