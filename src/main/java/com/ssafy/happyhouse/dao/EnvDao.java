package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.EnvChartData;
import com.ssafy.happyhouse.dto.EnvInfo;

@Mapper
@Repository
public interface EnvDao {

	public List<EnvInfo> searchAll(String sigudong);

	public List<EnvChartData> chartData();
}
