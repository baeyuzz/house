package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.EnvChartData;
import com.ssafy.happyhouse.dto.EnvInfo;

@Mapper
@Repository
public interface EnvDao {

	public List<EnvInfo> searchAll(String sigudong);

	public List<EnvChartData> chartData();
	
	public List<EnvInfo> getMapData(String sigudong);
	
	public List<String> getAddress();
	public int updateLngLat(Map<String, Object> param);
}
