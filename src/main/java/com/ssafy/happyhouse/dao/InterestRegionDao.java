package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HouseInfo;
import com.ssafy.happyhouse.dto.InterestRegion;

@Mapper
@Repository
public interface InterestRegionDao {
	
	public List<InterestRegion> search(String userid);
	
	public int addInterestRegion(InterestRegion ir);
	
	public int deleteInterestRegion(InterestRegion ir);

	public List<HouseInfo> searchApt(String id);

	public int addApt(Map<String, Object> map);

	public int getNo(int no);

}
