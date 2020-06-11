package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.EnvDao;
import com.ssafy.happyhouse.dto.EnvInfo;

@Service
public class EnvServiceImpl implements EnvService{

	private EnvDao dao;
	
	@Autowired
	public void setDao(EnvDao dao) {
		this.dao = dao;
	}

	@Override
	public List<EnvInfo> searchAllEnvInfo(Map<String, Object> map) {
		return dao.searchAll(map);
	}

	@Override
	public int countEnv(String dong) {
		return dao.countEnv(dong);
	}
	
}
