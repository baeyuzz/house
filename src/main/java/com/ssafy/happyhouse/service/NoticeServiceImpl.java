package com.ssafy.happyhouse.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dao.NoticeDao;
import com.ssafy.happyhouse.dto.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeDao noticeDao;
	
	@Autowired
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public boolean insertNotice(Notice notice) {
		int n = noticeDao.insertNotice(notice);
		return n > 0;
	}

	@Override
	public boolean updateNotice(Notice notice) {
		int n = noticeDao.updateNotice(notice);
		return n > 0;
	}

	@Override
	public boolean updateHitCount(int no) {
		int n = noticeDao.updateHitCount(no);
		return n > 0;
	}

	@Override
	public boolean deleteNotice(int no) {
		int n = noticeDao.deleteNotice(no);
		return n > 0;
	}

	@Override
	public Notice getNotice(int no) {
		return noticeDao.getNotice(no);
	}

	@Override
	public int countNotice() {
		// TODO Auto-generated method stub
		return noticeDao.countNotice();
	}

	@Override
	public List<Notice> getNoticeList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return noticeDao.getNoticeList(map);
	}

}
