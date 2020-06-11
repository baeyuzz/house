package com.ssafy.happyhouse.service;

import java.util.List;
import java.util.Map;

import com.ssafy.happyhouse.dto.EnvInfo;

public interface EnvService {
	/**
	 * 해당 동에 위치한 환경관련 정보 리스트 반환
	 * @param dong	동 이름
	 * @return	환경관련 정보리스트 반환
	 */
	public List<EnvInfo> searchAllEnvInfo(Map<String, Object> map);
	
	public int countEnv(String dong);
}
