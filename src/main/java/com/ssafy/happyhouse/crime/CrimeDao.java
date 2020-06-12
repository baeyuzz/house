package com.ssafy.happyhouse.crime;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.Crime;

@Mapper
@Repository
public interface CrimeDao {
		
	
	/**
	 * 아파트의 거래 일 별 거래금액 그래프를 그려주기 위한 데이터 반환
	 * @param param 검색 조건이 담긴 맵 객체
	 * @return 검색된 데이터 리스트
	 */
	public List<Crime> searchCrime(Map<String, Object> param);
}
