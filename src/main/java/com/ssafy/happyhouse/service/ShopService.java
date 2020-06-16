package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.ShopChartData;
import com.ssafy.happyhouse.dto.ShopInfo;

public interface ShopService {

	public List<ShopInfo> searchAllShop(String sigudong);
	
	public List<ShopChartData> chartData(String sigudong);
}
