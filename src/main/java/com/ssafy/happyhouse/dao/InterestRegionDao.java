package com.ssafy.happyhouse.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.InterestRegion;

@Mapper
@Repository
public interface InterestRegionDao {
	
	public List<InterestRegion> search(String userid);
	
	public int addInterestRegion(InterestRegion ir);
	
	public int deleteInterestRegion(InterestRegion ir);

}
