package com.ssafy.happyhouse.crime;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.Crime;
public interface CrimeService {
	
	public List<Crime> searchCrime(Map<String, Object> param);
}
