package com.ssafy.happyhouse.controller;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Notice Controller")
@Controller
@RequestMapping("/notice")
public class NoticeController {

	NoticeService service;
	
	@Autowired
	public void setService(NoticeService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "공지사항 리스트 받아오기")
	@ResponseBody
	@GetMapping("/list")
	private ResponseEntity<List<Notice>> getAllNotice() {
		return new ResponseEntity<List<Notice>>(service.getNoticeList(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 상세보기")
	@ResponseBody
	@GetMapping("/detail/{no}/{isAdmin}")
	private ResponseEntity<Notice> getNotice(@PathVariable int no,
										@PathVariable boolean isAdmin,
										HttpSession session) {	
		Notice notice = service.getNotice(no);
		
		if(!isAdmin) {
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
	private ResponseEntity<List<Notice>> searchNotice(@RequestBody Map<String, Object> map) {
		List<Notice> noticeList = service.getNoticeList();
		String title = (String) map.get("title");
		
		// 공지사항을 제목으로 검색할때 KMP 알고리즘 사용
		if(title != null && title.length() > 0) {
			noticeList = Algo.KMP(noticeList, title);
		}
		
		return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
	}
}
