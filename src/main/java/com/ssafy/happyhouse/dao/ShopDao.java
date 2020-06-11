package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.ShopInfo;

@Mapper
@Repository
public interface ShopDao {
	/**
	 * 선택된 지역에 있는 상가 리스트를 조회하는 함수
	 * @param 	map	조회할 상가가 있는 위치 정보만을 담은 객체
	 * @return	조회된 상가 리스트
	 */
	public List<ShopInfo> searchAll(Map<String, Object> map);
	
	public int countShop(Map<String, Object> map);
	/* 상가정보 추가, 수정, 삭제가 이 프로젝트에서 일어나지 않을 것 같습니다.
	public int addShop(ShopInfo shop);
	public int modifyShop();
	public int deleteShop(String no);
	*/
}
