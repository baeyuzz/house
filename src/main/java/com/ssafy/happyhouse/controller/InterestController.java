package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.InterestRegion;
import com.ssafy.happyhouse.service.InterestRegionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Interest Controller")
@Controller
@RequestMapping("/rest/interest")
public class InterestController {
	private InterestRegionService service;

	@Autowired
	public void setService(InterestRegionService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "관심지역 추가")
	@ResponseBody
	@PostMapping
	private ResponseEntity<Boolean> interestRegionAdd(@RequestBody InterestRegion region) {
		return new ResponseEntity<Boolean>(service.addInterestRegion(region), HttpStatus.OK);
	}

	@ApiOperation(value = "관심지역 리스트 조회")
	@ResponseBody
	@GetMapping("{userid}")
	private ResponseEntity<List<InterestRegion>> interestRegionList(@PathVariable String userid) {
		return new ResponseEntity<List<InterestRegion>>(service.searchInterestRegion(userid), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심지역 삭제")
	@ResponseBody
	@PostMapping("/delete")
	private ResponseEntity<Boolean> deleteInterestRegion(@RequestBody InterestRegion region) {
		return new ResponseEntity<Boolean>(service.deleteInterestRegion(region), HttpStatus.OK);
	}
}
