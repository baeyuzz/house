package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.UserDao;
import com.ssafy.happyhouse.dto.UserInfo;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public String login(UserInfo user) {
		String name = userDao.login(user);
		if(name != null && name.length() > 0) {
			return name;
		}
		return null;
	}

	@Override
	public List<UserInfo> searchAll() {
		return userDao.searchAll();
	}

	@Override
	public UserInfo search(String id) {
		return userDao.search(id);
	}

	@Override
	public boolean addUser(UserInfo user) {
		int n = userDao.addUser(user);
		return n > 0;
	}

	@Override
	public boolean modifyUser(UserInfo user) {
		int n = userDao.modifyUser(user);
		return n > 0;
	}

	@Override
	public boolean deleteUser(String id) {
		int n = userDao.deleteUser(id);
		return n > 0;
	}

	@Override
	public boolean changePw(String userid, String userpwd) {
		return userDao.changePwd(userid, userpwd) > 0;
		
	}
		
}
