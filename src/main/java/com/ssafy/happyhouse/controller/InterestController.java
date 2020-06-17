package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ssafy.happyhouse.dto.HouseInfo;
import com.ssafy.happyhouse.dto.InterestRegion;
import com.ssafy.happyhouse.service.HouseService;
import com.ssafy.happyhouse.service.HouseServiceImpl;
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

	@ApiOperation(value = "관심 아파트 정보 조회")
	@ResponseBody
	@PostMapping("/myAptList")
	private ResponseEntity<Map<String, Object>> aptList(@RequestBody Map<String, Object> param) {
		String id = (String) param.get("id");
		List<HouseInfo> list = service.searchApt(id);
//
		Map<String, Object> map = new HashMap<String, Object>();
		if (!list.isEmpty()) {
//			List<String> no = new ArrayList<String>();
//			for (int i = 0; i < list.size(); i++) {
//				no.add(service.getNo(list.get(i).getNo())+"");
//
//			}
//
//			map.put("no", no);
			map.put("list", list);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "관심 아파트 정보 추가")
	@ResponseBody
	@PostMapping("/addMyApt")
	private ResponseEntity<Boolean> addApt(@RequestBody Map<String, Object> param) {
		String id = (String) param.get("id");
		int no = (int) param.get("no");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", id);
		map.put("no", no);
		return new ResponseEntity<Boolean>(service.addApt(map), HttpStatus.OK);
	}

}
