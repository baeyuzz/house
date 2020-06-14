package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HousePageBean;
public interface HouseService {
	
	public List<HouseDeal> searchAll(HousePageBean  bean);
	
	public HouseDeal search(int no);
	
	public int numberOfData(HousePageBean bean);
	
	public List<HouseDeal> chartData(Map<String, Object> param);
	
	public List<String> address();
	
	public boolean addrToLng(Map<String, Object> param);
}
