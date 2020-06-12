package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.HouseDao;
import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HousePageBean;

@Service
public class HouseServiceImpl implements HouseService{
	
	private HouseDao dao;
	
	@Autowired
	public void setDao(HouseDao dao) {
		this.dao = dao;
	}

	@Override
	public List<HouseDeal> searchAll(HousePageBean bean) {		
		return dao.searchAll(bean);
	}

	@Override
	public HouseDeal search(int no) {
		return dao.search(no);
	}

	@Override
	public int numberOfData(HousePageBean bean) {
		return dao.numberOfData(bean);
	}

	@Override
	public List<HouseDeal> chartData(Map<String, Object> param) {
		return dao.chartData(param);
	}

}




