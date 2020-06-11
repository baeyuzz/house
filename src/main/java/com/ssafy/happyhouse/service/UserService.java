package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.UserInfo;

public interface UserService {
	
	/**
	 * 아이디와 패스워드를 통해 회원인지 확인
	 * @param user 유저 정보
	 * @return 유저의 이름
	 */
	String login(UserInfo user);

	/**
	 * 모든 유저의 정보를 검색해 반환
	 * @return 조회한 유저 목록
	 */
	public List<UserInfo> searchAll();
	
	/**
	 * 아이디를 통해 특정 유저의 정보 조회
	 * @param id	유저의 id
	 * @return		조회된 유저의 정보
	 */
	public UserInfo search(String id);
	
	/**
	 * 새로운 유저를 추가
	 * @param user	추가할 유저 정보
	 */
	public boolean addUser(UserInfo user);
	
	/**
	 * 유저의 정보 수정
	 * @param user	수정할 유저 정보
	 */
	public boolean modifyUser(UserInfo user);
	
	/**
	 * 아이디를 통해 유저의 정보 삭제
	 * @param id	삭제할 유저의 id
	 */
	public boolean deleteUser(String id);

	/**
	 * 아이디를 통해 유저의 비밀번호 변경
	 * @param id, pwd
	 */
	public boolean changePw(String userid, String userpwd);
}
