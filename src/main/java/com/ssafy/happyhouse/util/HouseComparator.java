package com.ssafy.happyhouse.util;

import java.util.Comparator;
import java.util.LinkedHashMap;

public class HouseComparator {
	
	public static Comparator<LinkedHashMap<String, Object>> getComparator(String by) {
		if("address".equals(by)) {
			return new AddressComparator();
		} else if("aptName".equals(by)) {
			return new AptComparator();
		} else if("buildYear".equals(by)) {
			return new BuildYearComparator();
		} else if("area".equals(by)) {
			return new AreaComparator();
		} else if("floor".equals(by)) {
			return new FloorComparator();
		} else if("dealDate".equals(by)) {
			return new DealDateComparator();
		} else if("dealAmount".equals(by)) {
			return new DealAmountComparator();
		} else if("rentMoney".equals(by)) {
			return new RentMoneyComparator();
		} else if("type".equals(by)) {
			return new TypeComparator();
		}
		return new AddressComparator();
	}
	
	private static class AddressComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return ((String)o1.get("address")).compareTo((String)o2.get("address"));
		}
		
	}
	
	private static class AptComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return ((String)o1.get("aptName")).compareTo((String)o2.get("aptName"));
		}
		
	}
	
	private static class BuildYearComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return Integer.compare((Integer)o1.get("buildYear"), (Integer)o2.get("buildYear"));
		}
		
	}
	
	private static class AreaComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return Double.compare((Double)o1.get("area"), (Double)o2.get("area"));
		}
		
	}
	
	private static class FloorComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return Integer.compare((Integer)o1.get("floor"), (Integer)o2.get("floor"));
		}
		
	}
	
	private static class DealDateComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return ((String)o1.get("dealDate")).compareTo((String)o2.get("dealDate"));
		}
		
	}
	
	private static class DealAmountComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return Integer.compare((Integer)o1.get("dealAmount"), (Integer)o2.get("dealAmount"));
		}
		
	}
	
	private static class RentMoneyComparator implements Comparator<LinkedHashMap<String, Object>> {
		
		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return Integer.compare((Integer)o1.get("rentMoney"), (Integer)o2.get("rentMoney"));
		}
		
	}
	
	private static class TypeComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return Integer.compare((Integer)o1.get("type"), (Integer)o2.get("type"));
		}
		
	}
	
}
