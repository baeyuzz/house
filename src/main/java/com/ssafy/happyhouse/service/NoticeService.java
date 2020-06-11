package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.Notice;


public interface NoticeService {
	
	boolean insertNotice(Notice notice);

	boolean updateNotice(Notice notice);

	boolean updateHitCount(int no);

	boolean deleteNotice(int no);

	List<Notice> getNoticeList();

	Notice getNotice(int no);
}
