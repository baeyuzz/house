package com.ssafy.happyhouse.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.ssafy.happyhouse.dto.HousePageBean;
import com.ssafy.happyhouse.news.NaverNewsApi;
import com.ssafy.happyhouse.news.News;
import com.ssafy.happyhouse.service.HouseService;
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
	@GetMapping("/pagenav/{pg}")
	private ResponseEntity<Map<String, Object>> pageNav(@PathVariable int pg, HttpSession session) {
		HousePageBean bean = HousePageBean.getInstance();
		// 조회할 데이터의 수 검색
		int n = service.numberOfData(bean);
		System.out.println("Number Of Data: " + n);
		
		// 클릭한 페이지
		int currentPage = pg;

		// 조회할 데이터의 시작 위치 계산
		int start = (currentPage - 1) * HousePageBean.INTERVAL;
		bean.setStart(start);
		
		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);

		// 둘다 세션에 넣기
		List<HouseDeal> dealList = service.searchAll(bean);
		session.setAttribute("navigation", pageNavigation);
		session.setAttribute("dealList", dealList);
		
		// Map 에 네비게이션과 데이터 넣기
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", dealList);
		
		if(n > HousePageBean.INTERVAL) {			
			map.put("nav", pageNavigation.getNavigator());
		} else {
			map.put("nav", "");
		}
				
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 정보 테이블 정렬")
	@ResponseBody
	@GetMapping("/sort/{by}")
	private ResponseEntity<Map<String, Object>> houseSort(@PathVariable String by, HttpSession session) {
		// 검색 조건에서 정렬 방식 재설정 
		HousePageBean bean = HousePageBean.getInstance();
		bean.setStart(0);
		bean.setOrderBy(by);
				
		// 조회할 데이터의 수 검색
		int n = service.numberOfData(bean);

		// 검색했을때 첫 화면은 1페이지부터 보여줌
		int currentPage = 1;

		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);

		// 데이터 조회
		List<HouseDeal> dealList = service.searchAll(bean);

		// session 에 네비게이션과 데이터 넣기
		session.setAttribute("navigation", pageNavigation);
		session.setAttribute("dealList", dealList);
		
		// Map 에 네비게이션과 데이터 넣기
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", dealList);
		
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
		List<News> news = NaverNewsApi.searchNews(house.getAptName());
		
		map.put("house", house);
		map.put("news", news);
		map.put("state", true);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "메인 페이지 이동시 아파트/주택 거래정보 채우기")
	@ResponseBody
	@GetMapping("/init")
	public ResponseEntity<Map<String, Object>> initInfo(HttpSession session) {
		// 세션에 이미 저장된 데이터가 있으면 그거 그대로 보여주면 됨
		if(session.getAttribute("dealList") != null) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("list", session.getAttribute("dealList"));
			if(session.getAttribute("navigation") != null) {				
				// Map 에 네비게이션과 데이터 넣기
				map.put("nav", ((PageNavigation)session.getAttribute("navigation")).getNavigator());
			} else {
				map.put("nav", "");
			}
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
				
		// 첫 접속일 경우
		HousePageBean bean = HousePageBean.getInstance();
				
		// 조회할 데이터의 수 검색
		int n = service.numberOfData(bean);

		// 검색했을때 첫 화면은 1페이지부터 보여줌
		int currentPage = 1;
		bean.setStart(0);
				
		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);
				
		List<HouseDeal> dealList = service.searchAll(bean);
		session.setAttribute("navigation", pageNavigation);
		session.setAttribute("dealList", dealList);
		
		// Map 에 네비게이션과 데이터 넣기
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", dealList);
		
		if(n > HousePageBean.INTERVAL) {			
			map.put("nav", pageNavigation.getNavigator());
		} else {
			map.put("nav", "");
		}
				
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 거래정보 검색")
	@ResponseBody
	@PostMapping("/list")
	public ResponseEntity<Map<String, Object>> searchHouseDeal(@RequestBody Map<String, Object> params
															, HttpSession session) {
		// 검색조건 수정하기
		HousePageBean bean = HousePageBean.getInstance();
		
		boolean[] type = new boolean[4];
		type[0] = (params.get("aptdeal") != null)? (boolean)params.get("aptdeal"): false;
		type[1] = (params.get("housedeal") != null)? (boolean)params.get("housedeal"): false;
		type[2] = (params.get("aptrent") != null)? (boolean)params.get("aptrent"): false;
		type[3] = (params.get("houserent") != null)? (boolean)params.get("houserent"): false;
		bean.setSearchType(type);
		
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
				
		// 조회할 데이터의 수 검색
		int n = service.numberOfData(bean);

		// 검색했을때 첫 화면은 1페이지부터 보여줌
		int currentPage = 1;

		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);

		// 데이터 조회
		List<HouseDeal> dealList = service.searchAll(bean);

		// session 에 네비게이션과 데이터 넣기
		session.setAttribute("navigation", pageNavigation);
		session.setAttribute("dealList", dealList);

		// Map 에 네비게이션과 데이터 넣기
		HashMap<String, Object> map = new HashMap<>();
		map.put("list", dealList);
		
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
		List<Integer> data = new ArrayList<Integer>();
		List<Integer> rent = new ArrayList<Integer>();
		List<Integer> nos = new ArrayList<Integer>();
		
		for(HouseDeal deal : list) {
			labels.add(deal.getDealDate());
			data.add(deal.getDealAmount());
			rent.add(deal.getRentMoney());
			nos.add(deal.getNo());
		}
		
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("labels", labels);
		ret.put("data", data);
		ret.put("nos", nos);
		ret.put("rent", rent);
		
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}
	
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
