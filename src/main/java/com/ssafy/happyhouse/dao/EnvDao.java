package com.ssafy.happyhouse.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.happyhouse.dto.EnvInfo;

@Mapper
@Repository
public interface EnvDao {
	/**
	 * 해당 동에 위치한 환경관련 정보 리스트 반환
	 * @param dong	동 이름
	 * @return	환경관련 정보리스트 반환
	 */
	public List<EnvInfo> searchAll(Map<String, Object> map);

	public int countEnv(String dong);
	
	/* 추가 수정 삭제를 이 웹사이트를 통해 하게될까요?
	public void addEnvInfo(EnvInfo env);
	public void modifyEnvInfo(int no, Map<String, String> mod);
	public void deleteEnvInfo(int no);
	*/
}
