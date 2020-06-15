package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ssafy.happyhouse.dto.QnA;
import com.ssafy.happyhouse.dto.Reply;
import com.ssafy.happyhouse.service.QnAService;
import com.ssafy.happyhouse.util.Algo;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("QnA Controller")
@Controller
@RequestMapping("/rest/qna")
public class QnAController {

	private QnAService service;

	@Autowired
	public void setService(QnAService service) {
		this.service = service;
	}

	/*
	 * Rest request
	 */
	@ApiOperation(value = "QnA 리스트 전체 조회")
	@ResponseBody
	@GetMapping
	private ResponseEntity<Map<String, Object>> getQnAList() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("start", 0);
		map.put("interval", 10);

		int n = service.countQna();

		List<QnA> list = service.getAllQnA(map);

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

	@ApiOperation(value = "QnA 상세 조회")
	@ResponseBody
	@GetMapping("/{qna_no}")
	public ResponseEntity<QnA> getQnA(@PathVariable int qna_no) {
		QnA qna = service.getQnA(qna_no);
		qna.setHitCount(qna.getHitCount()+1);
		service.updateHitCount(qna_no);

		return new ResponseEntity<QnA>(service.getQnA(qna_no), HttpStatus.OK);
	}

	@ApiOperation(value = "QnA 등록")
	@ResponseBody
	@PostMapping
	public ResponseEntity<Boolean> addQnA(@RequestBody QnA qna) {
		return new ResponseEntity<Boolean>(service.addQnA(qna), HttpStatus.OK);
	}

	@ApiOperation(value = "QnA 수정")
	@ResponseBody
	@PutMapping("/qna")
	public ResponseEntity<Boolean> modifyQnA(@RequestBody QnA qna) {
		return new ResponseEntity<Boolean>(service.modifyQnA(qna), HttpStatus.OK);
	}

	@ApiOperation(value = "QnA 삭제")
	@ResponseBody
	@DeleteMapping("/{qna_no}")
	public ResponseEntity<Boolean> deleteQnA(@PathVariable int qna_no) {
		return new ResponseEntity<Boolean>(service.deleteQnA(qna_no), HttpStatus.OK);
	}

	@ApiOperation(value = "제목으로 Q&A 검색")
	@ResponseBody
	@PostMapping("/search")
	public ResponseEntity<Map<String, Object>> searchQna(@RequestBody Map<String, Object> params) {

		String title = (String) params.get("title");
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("qna_title", title);
		int m = service.countQna(); // 전체 qna 개수

		map.put("start", 0);
		map.put("interval", 10);

		List<QnA> qnaList = service.getAllQnA(map);

		if (title != null && title.length() > 0) {
			qnaList = service.searchQna(map);
			map.put("paging", "");
		} else {
			int currentPage = 1;
			PageNavigation pageNavigation = PageNavigation.makePageNavigation(currentPage, 10, m + 9);
			qnaList = service.getAllQnA(map);
			if (m > 10)
				map.put("paging", pageNavigation.getNavigator());
			else {
				map.put("paging", "");
			}

		}

		map.put("total", qnaList.size());
		map.put("list", qnaList);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);

	}

	@ApiOperation(value = "QnA 페이지 이동")
	@ResponseBody
	@GetMapping("/pagenav/{pg}")
	private ResponseEntity<Map<String, Object>> shopPageNav(@PathVariable int pg) {

		int n = service.countQna();

		int start = (pg - 1) * 10;
		PageNavigation pageNavigation = PageNavigation.makePageNavigation(pg, 10, n + 9);

		HashMap<String, Object> map = new HashMap<>();
		map.put("start", start);
		map.put("interval", 10);

		List<QnA> list = service.getAllQnA(map);

		map.put("list", list);
		if (n > 10) {
			map.put("hasPaging", true);
			map.put("paging", pageNavigation.getNavigator());
		} else {
			map.put("hasPaging", false);
		}

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Reply 등록")
	@ResponseBody
	@PostMapping("/reply")
	public ResponseEntity<Boolean> addReply(@RequestBody Map<String, Object> map) {
		int qno = Integer.parseInt((String)map.get("qno"));
		String writer = (String) map.get("writer");
		String comment = (String) map.get("comment");
		Reply reply = new Reply(qno,writer,comment);
		return new ResponseEntity<Boolean>(service.addReply(reply), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Reply 삭제")
	@ResponseBody
	@GetMapping("/reply/del/{no}")
	public ResponseEntity<Boolean> deleteReply(@PathVariable int no) {
		return new ResponseEntity<Boolean>(service.deleteReply(no), HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Reply 목록 가져오기")
	@ResponseBody
	@GetMapping("/reply/{qno}")
	public ResponseEntity<List<Reply>> replyList(@PathVariable int qno) {
		List<Reply> list = service.replyList(qno);
		return new ResponseEntity<List<Reply>>(list, HttpStatus.OK);
	}
}
