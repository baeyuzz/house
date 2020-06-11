package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.InterestRegionDao;
import com.ssafy.happyhouse.dto.InterestRegion;

@Service
public class InterestRegionServiceImpl implements InterestRegionService{
	
	private InterestRegionDao dao;

	@Autowired
	public void setDao(InterestRegionDao dao) {
		this.dao = dao;
	}

	@Override
	public List<InterestRegion> searchInterestRegion(Map<String, Object> map) {
		return dao.search(map);
	}

	@Override
	public boolean addInterestRegion(InterestRegion ir) {
		int n = dao.addInterestRegion(ir);
		return n > 0;
	}

	@Override
	public boolean deleteInterestRegion(int no) {
		int n = dao.deleteInterestRegion(no);
		return n > 0;
	}

	@Override
	public int countRegion(String id) {
		return dao.countRegion(id);
	}

}




