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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.InterestRegion;
import com.ssafy.happyhouse.service.InterestRegionService;
import com.ssafy.happyhouse.util.PageNavigation;

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
	@GetMapping("{id}")
	private ResponseEntity<Map<String, Object>> interestRegionList(@PathVariable String id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("start", 0);
		map.put("interval", 10);
		
		int n = service.countRegion(id);
		
		List<InterestRegion> list = service.searchInterestRegion(map);
		
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("list", list);
		
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(1, 10, n);
			ret.put("hasPaging", true);
			ret.put("paging", pageNavigation.getNavigator());
		} else {
			ret.put("hasPaging", false);
		}
		
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심지역 페이지 이동")
	@ResponseBody
	@PostMapping("/pagenav")
	private ResponseEntity<Map<String, Object>> interestPageNav(@RequestBody Map<String, Object> param) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String id = (String)param.get("id");
		int pg = Integer.parseInt((String)param.get("pg"));
		map.put("id", id);
		map.put("start", (pg - 1) * 10);
		map.put("interval", 10);
		
		int n = service.countRegion(id);
		
		List<InterestRegion> list = service.searchInterestRegion(map);
		
		HashMap<String, Object> ret = new HashMap<>();

		ret.put("list", list);
	
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(pg, 10, n);
			ret.put("hasPaging", true);
			ret.put("paging", pageNavigation.getNavigator());
		} else {
			ret.put("hasPaging", false);
		}
		
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심지역 삭제")
	@ResponseBody
	@DeleteMapping("{no}")
	private ResponseEntity<Boolean> deleteInterestRegion(@PathVariable int no) {
		return new ResponseEntity<Boolean>(service.deleteInterestRegion(no), HttpStatus.OK);
	}
}
