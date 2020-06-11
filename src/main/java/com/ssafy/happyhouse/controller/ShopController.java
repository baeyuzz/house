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

import com.ssafy.happyhouse.dto.ShopInfo;
import com.ssafy.happyhouse.service.ShopService;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Shop Controller")
@RequestMapping("/shop")
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
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("city", param.get("city"));
		map.put("gu", param.get("gu"));
		map.put("dong", param.get("dong"));
		map.put("start", 0);
		map.put("interval", 10);
		
		int n = service.countShop(map);
		
		List<ShopInfo> list = service.searchAllShop(map);
			
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
	
	@ApiOperation(value = "상권정보 페이지 이동")
	@ResponseBody
	@PostMapping("/pagenav")
	private ResponseEntity<Map<String, Object>> shopPageNav(@RequestBody Map<String, Object> param) {
		int pg = Integer.parseInt((String)param.get("pg"));
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("city", param.get("city"));
		map.put("gu", param.get("gu"));
		map.put("dong", param.get("dong"));
		map.put("start", (pg - 1) * 10);
		map.put("interval", 10);
		
		int n = service.countShop(map);
		
		List<ShopInfo> list = service.searchAllShop(map);
		
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
