package com.ssafy.happyhouse.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@GetMapping("/list.do")
	private String getNoticeList(Model model) throws SQLException {
		model.addAttribute("noticeList", service.getNoticeList());
		return "notice_list";
	}	
	
	@ApiOperation(value = "공지사항 상세보기")
	@ResponseBody
	@GetMapping("/detail/{no}")
	private ResponseEntity<Notice> getNotice(@PathVariable int no, HttpSession session) {
		String userId = (String)session.getAttribute("id"); // 게시물 접근자
		
		Notice notice = service.getNotice(no);
		
		if(!notice.getUserid().equals(userId)) { // 게시물 접근자와 작성자가 다른가?
			notice.setHitCount(notice.getHitCount() + 1);
			service.updateHitCount(no);	
		}
		
		return new ResponseEntity<Notice>(notice, HttpStatus.OK);
	}
	
	@GetMapping("/detail.do")
	private String getNoticeDetail(int no, Model model, HttpSession session) throws SQLException {
		String userId = (String)session.getAttribute("id"); // 게시물 접근자
		
		Notice notice = service.getNotice(no);
		
		if(!notice.getUserid().equals(userId)) { // 게시물 접근자와 작성자가 다른가?
			notice.setHitCount(notice.getHitCount() + 1);
			service.updateHitCount(no);	
		}
		model.addAttribute("notice", notice);
		return "notice_detail";
	}
	
	@PostMapping("/modify.do")
	private String modifyNotice(Notice notice) throws SQLException {
		service.updateNotice(notice);
		return "redirect:/notice/list.do";
	}
	
	@PostMapping("/write.do")
	private String writeNotice(Notice notice, HttpSession session) throws SQLException {
		String id = (String)session.getAttribute("id");
		notice.setUserid(id);
		service.insertNotice(notice);
		return "redirect:/notice/list.do";
	}
	
	@GetMapping("/delete.do")
	private String deleteNotice(int no) throws SQLException {
		service.deleteNotice(no);
		return "redirect:/notice/list.do";
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
	
	@PostMapping("/search.do")
	private String searchNotice2(String title, Model model) throws SQLException {
		List<Notice> noticeList = service.getNoticeList();
		
		// 공지사항을 제목으로 검색할때 KMP 알고리즘 사용
		if(title != null && title.length() > 0) {
			noticeList = Algo.KMP(noticeList, title);
		}
		
		model.addAttribute("noticeList", noticeList);
		return "notice_list";
	}

	@GetMapping("/notice_write.go")
	private String goWrite() {
		return "notice_write";
	}
	
	@GetMapping("/notice_modify.go")
	private String goModify(Notice notice, Model model) {
		model.addAttribute("notice", notice);
		return "notice_modify";
	}
}
