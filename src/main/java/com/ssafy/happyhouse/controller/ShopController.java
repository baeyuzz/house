package com.ssafy.happyhouse.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.ssafy.happyhouse.dto.ShopChartData;
import com.ssafy.happyhouse.dto.ShopInfo;
import com.ssafy.happyhouse.service.ShopService;
import com.ssafy.happyhouse.util.ShopComparator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Shop Controller")
@RequestMapping("/rest/shop")
@Controller
public class ShopController {

	private ShopService service;
	
	@Autowired
	public void setService(ShopService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "상권 정보 조회")
	@ResponseBody
	@PostMapping
	private ResponseEntity<Map<String, Object>> shopList(@RequestBody Map<String, Object> param) {
		String sigudong = (String) param.get("sigudong");
		
		// 상권정보조회
		List<ShopInfo> list = service.searchAllShop(sigudong);

		// Default: 이름 순 정렬
		Collections.sort(list, new Comparator<ShopInfo>() {
			@Override
			public int compare(ShopInfo o1, ShopInfo o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		
		// chart 정보 조회
		List<ShopChartData> chartData = service.chartData(sigudong);

		HashMap<String, Object> ret = new HashMap<>();
		ret.put("list", list);
		ret.put("chart", chartData);
		
		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}
	
	@ApiOperation(value = "현재 상권 정보 테이블을 주어진 조건으로 정렬")
	@ResponseBody
	@PostMapping("/sort")
	private ResponseEntity<List<LinkedHashMap<String, Object>>> sort(@RequestBody Map<String, Object> param) {
		// 데이터 조회
		List<LinkedHashMap<String, Object>> shopList = 
				(List<LinkedHashMap<String, Object>>) param.get("list");

		// 정렬 기준 조회(3개)
		String by = (String) param.get("by");

		// 정렬
		Collections.sort(shopList, ShopComparator.getComparator(by));

		return new ResponseEntity<List<LinkedHashMap<String, Object>>>(shopList, HttpStatus.OK);
	}
}
