package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.EnvChartData;
import com.ssafy.happyhouse.dto.EnvInfo;

public interface EnvService {
	
	public List<EnvInfo> searchAllEnvInfo(String sigudong);
	public List<EnvChartData> chartData();
	public List<EnvInfo> getMapData(String sigudong);
	
	public List<String> getAddress();
	public boolean updateLngLat(Map<String, Object> param);
}
