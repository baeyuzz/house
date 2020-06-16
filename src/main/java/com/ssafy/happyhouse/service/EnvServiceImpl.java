package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.EnvDao;
import com.ssafy.happyhouse.dto.EnvChartData;
import com.ssafy.happyhouse.dto.EnvInfo;

@Service
public class EnvServiceImpl implements EnvService{

	private EnvDao dao;
	
	@Autowired
	public void setDao(EnvDao dao) {
		this.dao = dao;
	}

	@Override
	public List<EnvInfo> searchAllEnvInfo(String sigudong) {
		return dao.searchAll(sigudong);
	}

	@Override
	public List<EnvChartData> chartData() {
		return dao.chartData();
	}

	
}
