package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.EnvChartData;
import com.ssafy.happyhouse.dto.EnvInfo;

public interface EnvService {
	
	public List<EnvInfo> searchAllEnvInfo(String sigudong);
	public List<EnvChartData> chartData();
	
}
