package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.EnvChartData;
import com.ssafy.happyhouse.dto.EnvInfo;
import com.ssafy.happyhouse.service.EnvService;
import com.ssafy.happyhouse.util.EnvComparator;

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
	
	@ApiOperation(value = "관심지역의 모든 환경관련 정보 조회(업체 리스트, 차트용 데이터, 지도 표시용 데이터)")
	@ResponseBody
	@PostMapping
	private ResponseEntity<Map<String, Object>> envList(@RequestBody Map<String, Object> param) {
		String sigudong = (String)param.get("sigudong");
		List<EnvInfo> list = service.searchAllEnvInfo(sigudong);
		List<EnvChartData> chart = service.chartData();
		List<EnvInfo> map = service.getMapData(sigudong);
		
		HashMap<String, Object> ret = new HashMap<String, Object>();
		ret.put("list", list);
		ret.put("chart", chart);
		ret.put("map", map);
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}
	
	@ApiOperation(value = "현재 환경 정보 리스트를 주어진 기준으로 정렬")
	@ResponseBody
	@PostMapping("/sort")
	private ResponseEntity<List<LinkedHashMap<String, Object>>> sort(@RequestBody Map<String, Object> param) {
		// 데이터 조회
		List<LinkedHashMap<String, Object>> envList = 
				(List<LinkedHashMap<String, Object>>) param.get("list");

		// 정렬 기준 조회(3개)
		String by = (String) param.get("by");

		// 정렬
		Collections.sort(envList, EnvComparator.getComparator(by));

		return new ResponseEntity<List<LinkedHashMap<String, Object>>>(envList, HttpStatus.OK);
	}
	
	// 아래 두 개는 DB 에 주소를 넣기 위한 것
	@ApiOperation(value = "환경 정보 테이블에서 좌표를 얻기위한 주소 추출")
	@ResponseBody
	@GetMapping("/address")
	private ResponseEntity<List<String>> getAddress() {
		List<String> list = service.getAddress();
		
		ArrayList<String> ret = new ArrayList<String>();
		
		for(String address : list) {
			String[] strs = address.split(" ");
			
			if(strs.length < 4) continue;
			
			strs[3] = strs[3].replace("번지", "");
			String addr = strs[0] + " " + 
						  strs[1] + " " + 
						  strs[2] + " " + strs[3];
			
			ret.add(addr);
		}
		
		return new ResponseEntity<List<String>>(ret, HttpStatus.OK);
	}
	
	@ApiOperation(value = "환경정보 테이블에 좌표값 입력")
	@ResponseBody
	@PostMapping("/update")
	private ResponseEntity<Boolean> updateLngLat(@RequestBody Map<String, Object> param) {
		return new ResponseEntity<Boolean>(service.updateLngLat(param), HttpStatus.OK);
	}
}
