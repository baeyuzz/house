package com.ssafy.happyhouse.controller;

import java.util.List;

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
import com.ssafy.happyhouse.service.QnAService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("QnA Controller")
@Controller
@RequestMapping("/qna")
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
	public ResponseEntity<List<QnA>> getQnAList() {
		return new ResponseEntity<List<QnA>>(service.getAllQnA(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QnA 상세 조회")
	@ResponseBody
	@GetMapping("/{qna_no}")
	public ResponseEntity<QnA> getQnA(@PathVariable int qna_no) {
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
	
	@ApiOperation(value = "Reply 등록")
	@ResponseBody
	@PutMapping("/reply")
	public ResponseEntity<Boolean> addReply(@RequestBody QnA qna) {
		return new ResponseEntity<Boolean>(service.addReply(qna), HttpStatus.OK);
	}
}
