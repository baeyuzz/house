package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.ShopChartData;
import com.ssafy.happyhouse.dto.ShopInfo;

@Mapper
@Repository
public interface ShopDao {
	
	public List<ShopInfo> searchAll(String sigudong);
	
	public List<ShopChartData> chartData(String sigudong);
}
