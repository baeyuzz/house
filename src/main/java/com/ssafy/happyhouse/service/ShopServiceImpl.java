package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.ShopDao;
import com.ssafy.happyhouse.dto.ShopChartData;
import com.ssafy.happyhouse.dto.ShopInfo;

@Service
public class ShopServiceImpl implements ShopService{

	private ShopDao dao;
	
	@Autowired
	public void setDao(ShopDao dao) {
		this.dao = dao;
	}

	@Override
	public List<ShopInfo> searchAllShop(String sigudong) {
		return dao.searchAll(sigudong);
	}

	@Override
	public List<ShopChartData> chartData(String sigudong) {
		return dao.chartData(sigudong);
	}
	
}
