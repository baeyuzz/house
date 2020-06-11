package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.ShopInfo;

public interface ShopService {
	/**
	 * 선택된 지역에 있는 상가 리스트를 조회하는 함수
	 * @param scity	시
	 * @param sgu	구
	 * @param sdong	동
	 * @return	조회된 상가 리스트
	 * @throws SQLException
	 */
	public List<ShopInfo> searchAllShop(Map<String, Object> map);
	public int countShop(Map<String, Object> map);
}
