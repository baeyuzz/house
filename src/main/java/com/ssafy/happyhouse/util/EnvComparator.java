package com.ssafy.happyhouse.util;

import java.util.Comparator;
import java.util.LinkedHashMap;

public class EnvComparator {
	
	public static Comparator<LinkedHashMap<String, Object>> getComparator(String by) {
		if("name".equals(by)) {
			return new NameComparator();
		} else if("type".equals(by)) {
			return new TypeComparator();
		} else if("address".equals(by)) {
			return new AddressComparator();
		}
		return new NameComparator();
	}
	
	private static class NameComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return ((String)o1.get("name")).compareTo((String)o2.get("name"));
		}
		
	}
	
	private static class TypeComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return ((String)o1.get("type")).compareTo((String)o2.get("type"));
		}
		
	}
	
	private static class AddressComparator implements Comparator<LinkedHashMap<String, Object>> {

		@Override
		public int compare(LinkedHashMap<String, Object> o1, LinkedHashMap<String, Object> o2) {
			return ((String)o1.get("address")).compareTo((String)o2.get("address"));
		}
		
	}
	
}
