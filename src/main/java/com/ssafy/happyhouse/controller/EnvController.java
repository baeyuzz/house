package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.EnvInfo;
import com.ssafy.happyhouse.service.EnvService;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Env Controller")
@RequestMapping("/rest/env")
@Controller
public class EnvController {

	private EnvService service;
	
	@Autowired
	public void setService(EnvService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "관심지역의 모든 환경관련 정보 조회(페이지 단위)")
	@ResponseBody
	@PostMapping
	private ResponseEntity<Map<String, Object>> envList(@RequestBody Map<String, Object> param) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dong", param.get("dong"));
		map.put("start", 0);
		map.put("interval", 10);
		
		int n = service.countEnv((String)param.get("dong"));
		
		List<EnvInfo> list = service.searchAllEnvInfo(map);
		
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
	
	@ApiOperation(value = "환경정보 테이블 페이지 이동")
	@ResponseBody
	@PostMapping("/pagenav")
	private ResponseEntity<Map<String, Object>> envPageNav(@RequestBody Map<String, Object> param) {
		String dong = (String)param.get("dong");
		int pg = Integer.parseInt((String)param.get("pg"));
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dong", dong);
		map.put("start", (pg - 1) * 10);
		map.put("interval", 10);
		
		int n = service.countEnv(dong);
		
		List<EnvInfo> list = service.searchAllEnvInfo(map);
		
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
}
