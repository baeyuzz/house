package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.InterestRegionDao;
import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HouseInfo;
import com.ssafy.happyhouse.dto.InterestRegion;

@Service
public class InterestRegionServiceImpl implements InterestRegionService{
	
	private InterestRegionDao dao;

	@Autowired
	public void setDao(InterestRegionDao dao) {
		this.dao = dao;
	}

	@Override
	public List<InterestRegion> searchInterestRegion(String userid) {
		return dao.search(userid);
	}

	@Override
	public boolean addInterestRegion(InterestRegion ir) {
		// TODO Auto-generated method stub
		return dao.addInterestRegion(ir) > 0;
	}

	@Override
	public boolean deleteInterestRegion(InterestRegion ir) {
		return dao.deleteInterestRegion(ir) > 0;
	}

	@Override
	public List<HouseInfo> searchApt(String id) {
		return dao.searchApt(id);
	}

	@Override
	public boolean addApt(Map<String, Object> map) {
		return dao.addApt(map) > 0;
	}

	@Override
	public int getNo(int no) {
		// TODO Auto-generated method stub
		return dao.getNo(no);
	}

}




