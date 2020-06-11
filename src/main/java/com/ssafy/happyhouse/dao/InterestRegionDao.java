package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.InterestRegion;

@Mapper
@Repository
public interface InterestRegionDao {
	
	public List<InterestRegion> search(Map<String, Object> map);
	
	public int countRegion(String id);
	
	public int addInterestRegion(InterestRegion ir);
	
	public int deleteInterestRegion(int no);

}
