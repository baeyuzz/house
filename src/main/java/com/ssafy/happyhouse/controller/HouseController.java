package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HouseInfo;
import com.ssafy.happyhouse.dto.HousePageBean;
import com.ssafy.happyhouse.news.NaverNewsApi;
import com.ssafy.happyhouse.news.News;
import com.ssafy.happyhouse.service.HouseService;
import com.ssafy.happyhouse.util.HouseComparator;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("House Controller")
@Controller
@RequestMapping("/rest/house")
public class HouseController {
	
	private HouseService service;
	@Autowired
	public void setService(HouseService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "아파트/주택 거래 테이블 페이지 이동")
	@ResponseBody
	@PostMapping("/pagenav")
	private ResponseEntity<Map<String, Object>> pageNav(@RequestBody Map<String, Object> param,
												HttpSession session) {
		
		int pg = (Integer)param.get("pg");
		int n = (Integer)param.get("nod");
		// 클릭한 페이지
		int currentPage = pg;

		// 조회할 데이터의 시작 위치 계산
		int start = (currentPage - 1) * HousePageBean.INTERVAL;
		// 조회할 데이터의 마지막 위치 계산
		int end = start + HousePageBean.INTERVAL;
		if(end > n) end = n;
		
		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);

		// 전체 리스트 가져오기
		List<LinkedHashMap<String, Object>> dealList = (List<LinkedHashMap<String, Object>>)param.get("list");
		
		// nav 세션에 넣기
		session.setAttribute("navigation", pageNavigation);
		
		// Map 에 네비게이션과 데이터 넣기
		HashMap<String, Object> map = new HashMap<>();
		int subIdx = HousePageBean.INTERVAL;
		if(subIdx > n) subIdx = n;
		map.put("tableList", dealList.subList(start, end));
		
		if(n > HousePageBean.INTERVAL) {			
			map.put("nav", pageNavigation.getNavigator());
		} else {
			map.put("nav", "");
		}
				
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 정보 테이블 정렬")
	@ResponseBody
	@PostMapping("/sort")
	private ResponseEntity<Map<String, Object>> houseSort(@RequestBody Map<String, Object> param,
										HttpSession session) {
		
		// 검색했을때 첫 화면은 1페이지부터 보여줌
		int currentPage = 1;

		// 리스트의 전체 데이터 수
		int n = (Integer) param.get("nod");
		
		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);

		// 데이터 조회
		List<LinkedHashMap<String, Object>> dealList = (List<LinkedHashMap<String, Object>>)param.get("list");

		// 정렬 기준 조회(9개)
		String by = (String)param.get("by");
		
		// 정렬
		Collections.sort(dealList, HouseComparator.getComparator(by));

		// session 에 네비게이션과 데이터 넣기
		session.setAttribute("navigation", pageNavigation);
		session.setAttribute("dealList", dealList);
		
		// Map 에 네비게이션과 데이터 넣기
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", dealList);
		
		int subIdx = HousePageBean.INTERVAL;
		if(subIdx > n) subIdx = n;
		map.put("tableList", dealList.subList(0, subIdx));
		
		if(n > HousePageBean.INTERVAL) {
			map.put("nav", pageNavigation.getNavigator());
		} else {
			map.put("nav", "");
		}
				
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 거래 상세 정보 조회")
	@ResponseBody
	@GetMapping("/detail/{no}")
	private ResponseEntity<Map<String, Object>> houseDetail(@PathVariable int no, HttpSession session) {
		HashMap<String, Object> map = new HashMap<>();
		HouseDeal house = service.search(no);
		String addrArr[] = house.getAddress().split(" ");
		String addr = addrArr[1]+house.getAptName();
		List<News> news = NaverNewsApi.searchNews(addr);
		
		map.put("house", house);
		map.put("news", news);
		map.put("state", true);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 거래정보 검색")
	@ResponseBody
	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> searchHouseDeal(@RequestBody Map<String, Object> params
															, HttpSession session) {
		// 검색조건 수정하기
		HousePageBean bean = HousePageBean.getInstance();
		
		// 검색할 정보 타입
		boolean[] type = new boolean[4];
		type[0] = (params.get("aptdeal") != null)? (boolean)params.get("aptdeal"): false;
		type[1] = (params.get("housedeal") != null)? (boolean)params.get("housedeal"): false;
		type[2] = (params.get("aptrent") != null)? (boolean)params.get("aptrent"): false;
		type[3] = (params.get("houserent") != null)? (boolean)params.get("houserent"): false;
		bean.setSearchType(type);
		
		// 검색할 주소와 아파트 이름
		String address = (String) params.get("address");
		String aptName = (String) params.get("aptName");
				
		if(address != null && address.length() > 0)
			bean.setAddress(address);
		else
			bean.setAddress(null);
				
		if(aptName != null && aptName.length() > 0)
			bean.setAptName(aptName);
		else
			bean.setAptName(null);
				
		// 검색 결과는 0번부터 보여줌
		bean.setStart(0);
				
		// 데이터 조회
		List<HouseDeal> dealList = service.searchAll(bean);
		
		// 마커 전용 데이터 만들기
		HashSet<HouseDeal> set = new HashSet<HouseDeal>();
		for(HouseDeal deal : dealList) {
			set.add(new HouseDeal(deal.getNo(), deal.getAddress(), deal.getAptName(), deal.getLat(), deal.getLng()));
		}
		
		HouseDeal[] listForMap = set.toArray(new HouseDeal[set.size()]);
		System.out.println(dealList.size());
		System.out.println(listForMap.length);
		
		// 조회할 데이터의 수 검색
		// int n = service.numberOfData(bean);
		int n = dealList.size();

		// 검색했을때 첫 화면은 1페이지부터 보여줌
		int currentPage = 1;

		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);

		// session 에 네비게이션과 데이터 넣기
		session.setAttribute("navigation", pageNavigation);
		session.setAttribute("dealList", dealList);

		// Map 에 네비게이션과 데이터 넣기
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", dealList);
		int subIdx = HousePageBean.INTERVAL;
		if(subIdx > n) subIdx = n;
		map.put("tableList", dealList.subList(0, subIdx));
		map.put("mapList", listForMap);
		map.put("nod", n);
		
		if(n > HousePageBean.INTERVAL) {
			map.put("nav", pageNavigation.getNavigator());
		} else {
			map.put("nav", "");
		}
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "그래프를 그려주기 위한 데이터 조회")
	@ResponseBody
	@PostMapping("/chart")
	public ResponseEntity<Map<String, Object>> getChartData(@RequestBody Map<String, Object> param) {
		List<HouseDeal> list = service.chartData(param);
		
		List<String> labels = new ArrayList<String>();
		List<String> labels2 = new ArrayList<String>();
		List<Integer> data = new ArrayList<Integer>();
		List<Integer> rent = new ArrayList<Integer>();
		List<Integer> nos = new ArrayList<Integer>();
		
		int avgDeal = 0;
		int avgRent1 = 0;
		int avgRent2 = 0;
		int totalDeal = 0;
		int totalRent = 0;
		for(HouseDeal deal : list) {
			if(deal.getType()==1 || deal.getType()==2) {
			labels.add(deal.getDealDate());
			data.add(deal.getDealAmount());
			avgDeal+=deal.getDealAmount();
			totalDeal++;
			}
			else {
			rent.add(deal.getRentMoney());
			String l2 = deal.getDealDate() + "\n보증금 " +deal.getDealAmount();
			labels2.add(l2);
			avgRent1 += deal.getDealAmount();
			avgRent2 += deal.getRentMoney();
			totalRent++;
			}
			nos.add(deal.getNo());
		}
		avgDeal/=totalDeal;
		avgRent1/=totalRent;
		avgRent2/=totalRent;
		
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("labels", labels);
		ret.put("data", data);
		ret.put("nos", nos);
		ret.put("rent", rent);
		ret.put("labels2",labels2);
		ret.put("avgDeal", avgDeal);
		ret.put("avgRent1", avgRent1);
		ret.put("avgRent2", avgRent2);
		
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}
	
	// 아래 세 개는 검색에 필요한 select 를 설정하기 위해 있는 것
	@GetMapping("/sidoname")
	@ResponseBody
	private ResponseEntity<List<String>> getSidoName() {
		return new ResponseEntity<List<String>>(service.sidoName(), HttpStatus.OK);
	}
	
	@GetMapping("/gugunname/{sido}")
	@ResponseBody
	private ResponseEntity<List<String>> getGugunName(@PathVariable String sido) {
		return new ResponseEntity<List<String>>(service.gugunName(sido), HttpStatus.OK);
	}
	
	@GetMapping("/dongname/{gugun}")
	@ResponseBody
	private ResponseEntity<List<String>> getDongName(@PathVariable String gugun) {
		return new ResponseEntity<List<String>>(service.dongName(gugun), HttpStatus.OK);
	}
	
	// 아래 두개는 DB 에 좌표를 넣기 위한 것
	@GetMapping("/houseaddress")
	@ResponseBody
	private ResponseEntity<List<String>> getAddresses() {
		return new ResponseEntity<List<String>>(service.address(), HttpStatus.OK);
	}
	
	@PutMapping("/addrtolng")
	@ResponseBody
	private ResponseEntity<Boolean> addrToLng(@RequestBody Map<String, Object> param) {
		return new ResponseEntity<Boolean>(service.addrToLng(param), HttpStatus.OK);
	}
}
