package com.ssafy.happyhouse.crime;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.Crime;

@Service
public class CrimeServiceImpl implements CrimeService{
	
	@Autowired
	private CrimeDao  dao;

	@Override
	public List<Crime> searchCrime(Map<String, Object> param) {
		return dao.searchCrime(param);
	}
}




