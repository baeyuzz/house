package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.ShopDao;
import com.ssafy.happyhouse.dto.ShopInfo;

@Service
public class ShopServiceImpl implements ShopService{

	private ShopDao dao;
	
	@Autowired
	public void setDao(ShopDao dao) {
		this.dao = dao;
	}

	@Override
	public List<ShopInfo> searchAllShop(Map<String, Object> map) {
		return dao.searchAll(map);
	}

	@Override
	public int countShop(Map<String, Object> map) {
		return dao.countShop(map);
	}
	
}
