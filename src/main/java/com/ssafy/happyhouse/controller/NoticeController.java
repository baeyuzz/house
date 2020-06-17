package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.Notice;
import com.ssafy.happyhouse.service.NoticeService;
import com.ssafy.happyhouse.util.Algo;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Notice Controller")
@Controller
@RequestMapping("/rest/notice")
public class NoticeController {

	NoticeService service;

	@Autowired
	public void setService(NoticeService service) {
		this.service = service;
	}

	@ApiOperation(value = "공지사항 리스트 받아오기")
	@ResponseBody
	@GetMapping("/list")
	private ResponseEntity<Map<String, Object>> getAllNotice() {

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", 0);
		map.put("interval", 10);

		int n = service.countNotice();

		List<Notice> list = service.getNoticeList(map);

		HashMap<String, Object> ret = new HashMap<>();
		ret.put("list", list);

		if (n > 10) {
			PageNavigation pageNavigation = PageNavigation.makePageNavigation(1, 10, n + 10);
			ret.put("hasPaging", true);
			ret.put("paging", pageNavigation.getNavigator());
		} else {
			ret.put("hasPaging", false);
		}

		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}

	@ApiOperation(value = "공지사항 상세보기")
	@ResponseBody
	@GetMapping("/detail/{no}/{isAdmin}")
	private ResponseEntity<Notice> getNotice(@PathVariable int no, @PathVariable boolean isAdmin, HttpSession session) {
		Notice notice = service.getNotice(no);

		if (!isAdmin) {
			notice.setHitCount(notice.getHitCount() + 1);
			service.updateHitCount(no);
		}

		return new ResponseEntity<Notice>(notice, HttpStatus.OK);
	}

	@ApiOperation(value = "공지사항 수정")
	@ResponseBody
	@PutMapping("/modify")
	private ResponseEntity<Boolean> modifyNotice(@RequestBody Notice notice) {
		return new ResponseEntity<Boolean>(service.updateNotice(notice), HttpStatus.OK);
	}

	@ApiOperation(value = "공지사항 작성")
	@ResponseBody
	@PostMapping("/write")
	private ResponseEntity<Boolean> writeNotice(@RequestBody Notice notice) {
		return new ResponseEntity<Boolean>(service.insertNotice(notice), HttpStatus.OK);
	}

	@ApiOperation(value = "공지사항 삭제")
	@ResponseBody
	@DeleteMapping("/delete/{no}")
	private ResponseEntity<Boolean> deleteNotice(@PathVariable int no) {
		return new ResponseEntity<Boolean>(service.deleteNotice(no), HttpStatus.OK);
	}

	@ApiOperation(value = "제목으로 공지사항 검색")
	@ResponseBody
	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> searchNotice(@RequestBody Map<String, Object> params) {

		String title = (String) params.get("title");
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("title", title);
		int m = service.countNotice(); // 전체 qna 개수

		map.put("start", 0);
		map.put("interval", 10);

		List<Notice> noticeList = service.getNoticeList(map);

		// 공지사항을 제목으로 검색할때 KMP 알고리즘 사용
		if (title != null && title.length() > 0) {
			noticeList = Algo.KMP(noticeList, title);
			map.put("paging", "");

		} else {
			int currentPage = 1;
			PageNavigation pageNavigation = PageNavigation.makePageNavigation(currentPage, 10, m + 9);
			noticeList = service.getNoticeList(map);
			if (m > 10)
				map.put("paging", pageNavigation.getNavigator());
			else {
				map.put("paging", "");
			}
		}
		map.put("list", noticeList);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Notice 페이지 이동")
	@ResponseBody
	@GetMapping("/pagenav/{pg}")
	private ResponseEntity<Map<String, Object>> shopPageNav(@PathVariable int pg) {

		int n = service.countNotice();

		int start = (pg - 1) * 10;
		PageNavigation pageNavigation = PageNavigation.makePageNavigation(pg, 10, n + 9);

		HashMap<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("interval", 10);

		List<Notice> list = service.getNoticeList(map);

		map.put("list", list);
		
		if (n > 10) {
			map.put("hasPaging", true);
			map.put("paging", pageNavigation.getNavigator());
		} else {
			map.put("hasPaging", false);
		}

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
}
