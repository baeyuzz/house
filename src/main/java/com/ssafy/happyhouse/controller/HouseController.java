package com.ssafy.happyhouse.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.HouseDeal;
import com.ssafy.happyhouse.dto.HousePageBean;
import com.ssafy.happyhouse.service.HouseService;
import com.ssafy.happyhouse.util.HouseComparator;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("House Controller")
@Controller
@RequestMapping("/house")
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
		map.put("nav", pageNavigation.getNavigator());
				
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 거래 테이블 페이지 이동")
	@GetMapping("/pageNav.do")
	private String pageNav2(int pg, Model model, HttpSession session) throws Exception {
		HousePageBean bean = HousePageBean.getInstance();
		// 조회할 데이터의 수 검색
		int n = service.numberOfData(bean);

		// 클릭한 페이지
		int currentPage = pg;

		// 조회할 데이터의 시작 위치 계산
		int start = (currentPage - 1) * HousePageBean.INTERVAL;
		bean.setStart(start);
		
		// 페이지 네비게이션 만들기
		PageNavigation pageNavigation = 
				PageNavigation.makePageNavigation(currentPage, HousePageBean.INTERVAL, n);

		// 둘다 세션에 넣기(다시 홈페이지로 가면 이거 그대로 보여주게)
		session.setAttribute("navigation", pageNavigation);
		session.setAttribute("dealList", service.searchAll(bean));
		
		return "index";
	}
	
	@ApiOperation(value = "Rest 아파트/주택 정보 테이블 정렬")
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
		map.put("nav", pageNavigation.getNavigator());
				
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 정보 테이블 정렬")
	@GetMapping("/sort.do")
	private String houseSort2(String by, Model model, HttpSession session) {
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
		
		// 메인페이지로
		return "index";
	}
	
	@ApiOperation(value = "Rest 아파트/주택 거래 상세 정보 조회")
	@ResponseBody
	@GetMapping("/detail/{no}")
	private ResponseEntity<HouseDeal> houseDetail(@PathVariable int no, HttpSession session) {
		// DB 조회를 통해 찾지 않고, 메인 페이지에 표시된 정보 리스트에서 이분탐색으로 찾는 방법을 사용
		// 이 함수가 호출되는 건 메인페이지에 표시된 항목 중 하나의 정보를 가져오는 것
		/* 알고리즘 관통때 했던거 무시하고 DB 에서 가져올래요
		List<HouseDeal> dealList = null;
		Object obj = session.getAttribute("dealList");
		if(obj != null) {
			dealList = (List<HouseDeal>) obj;
		}
		HouseDeal[] hdList = dealList.toArray(new HouseDeal[dealList.size()]);
		// 이분 탐색
		// 이분탐색 하려면 정렬된 상태여야함
		Arrays.sort(hdList, HouseComparator.getComparator(1));
		int start = 0;
		int end = hdList.length;
		while(start < end) {
			int mid = (start + end) / 2;
			int val = hdList[mid].getNo();
			if(val < no) {
				start = mid + 1;
			} else if(val > no) {
				end = mid;
			} else {
				start = mid;
				break;
			}
		}
		HouseDeal house = hdList[start];
		*/
		HouseDeal house = service.search(no);
		return new ResponseEntity<HouseDeal>(house, HttpStatus.OK);
	}
	
	@ApiOperation(value = "아파트/주택 거래 상세 정보 조회")
	@GetMapping("/detail.do")
	@SuppressWarnings("unchecked")
	private String houseDetail2(int no, Model model, HttpSession session) throws Exception {
		// DB 조회를 통해 찾지 않고, 메인 페이지에 표시된 정보 리스트에서 이분탐색으로 찾는 방법을 사용
		// 이 함수가 호출되는 건 메인페이지에 표시된 항목 중 하나의 정보를 가져오는 것
		// HouseDeal house = houseService.search(no);
		List<HouseDeal> dealList = null;
		Object obj = session.getAttribute("dealList");
		if(obj != null) {
			dealList = (List<HouseDeal>) obj;
		}
		HouseDeal[] hdList = dealList.toArray(new HouseDeal[dealList.size()]);
		// 이분 탐색
		// 이분탐색 하려면 정렬된 상태여야함
		Arrays.sort(hdList, HouseComparator.getComparator(1));
		int start = 0;
		int end = hdList.length;
		while(start < end) {
			int mid = (start + end) / 2;
			int val = hdList[mid].getNo();
			if(val < no) {
				start = mid + 1;
			} else if(val > no) {
				end = mid;
			} else {
				start = mid;
				break;
			}
		}
		HouseDeal house = hdList[start];
		model.addAttribute("house", house);
		return "detail";
	}

	/*
	@ApiOperation(value = "특정 기준으로 아파트/주택 거래 정보 조회")
	@PostMapping("/search.do")
	private String houseSearch(HttpServletRequest request,Model model, HttpSession session) throws Exception {		
		// 검색조건 수정하기
		HousePageBean bean = HousePageBean.getInstance();

		boolean[] type = new boolean[4];
		type[0] = "ok".equals(request.getParameter("aptdeal"));
		type[1] = "ok".equals(request.getParameter("housedeal"));
		type[2] = "ok".equals(request.getParameter("aptrent"));
		type[3] = "ok".equals(request.getParameter("houserent"));
		bean.setSearchType(type);
		
		String dong = (String) request.getParameter("dong");
		String aptName = (String) request.getParameter("aptname");
		
		if(dong != null && dong.length() > 0)
			bean.setDong(dong);
		else
			bean.setDong(null);
		
		if(aptName != null && aptName.length() > 0)
			bean.setAptname(aptName);
		else
			bean.setAptname(null);
		
		// 체크박스 체크상태 유지를 위해 세션에 설정해주기
		if(bean.getSearchType()[0]) session.setAttribute("adck", "checked");
		else						session.setAttribute("adck", "");
		if(bean.getSearchType()[1]) session.setAttribute("hdck", "checked");
		else						session.setAttribute("hdck", "");
		if(bean.getSearchType()[2]) session.setAttribute("arck", "checked");
		else						session.setAttribute("arck", "");
		if(bean.getSearchType()[3]) session.setAttribute("hrck", "checked");
		else						session.setAttribute("hrck", "");
				
		// 검색 인풋박스 유지를 위해 세션에 설정해주기
		if(bean.getDong() != null)		session.setAttribute("donginp", bean.getDong());
		else							session.setAttribute("donginp", "");
		if(bean.getAptname() != null)	session.setAttribute("aptinp", bean.getAptname());
		else							session.setAttribute("aptinp", "");
		
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

		return "index";
	}
	*/
	
	@ApiOperation(value = "Rest 메인 페이지 이동시 아파트/주택 거래정보 채우기")
	@ResponseBody
	@GetMapping("/init")
	public ResponseEntity<Map<String, Object>> initInfo(HttpSession session) {
		// 세션에 이미 저장된 데이터가 있으면 그거 그대로 보여주면 됨
		if(session.getAttribute("dealList") != null) {
			if(session.getAttribute("navigation") != null) {				
				// Map 에 네비게이션과 데이터 넣기
				HashMap<String, Object> map = new HashMap<>();
				map.put("list", session.getAttribute("dealList"));
				map.put("nav", ((PageNavigation)session.getAttribute("navigation")).getNavigator());
				
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}
		}
				
		// 첫 접속일 경우
		HousePageBean bean = HousePageBean.getInstance();
				
		// 체크박스 체크상태 유지를 위해 세션에 설정해주기
		if(bean.getSearchType()[0]) session.setAttribute("adck", "checked");
		else						session.setAttribute("adck", "");
		if(bean.getSearchType()[1]) session.setAttribute("hdck", "checked");
		else						session.setAttribute("hdck", "");
		if(bean.getSearchType()[2]) session.setAttribute("arck", "checked");
		else						session.setAttribute("arck", "");
		if(bean.getSearchType()[3]) session.setAttribute("hrck", "checked");
		else						session.setAttribute("hrck", "");
				
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
		map.put("nav", pageNavigation.getNavigator());
				
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Rest 아파트/주택 거래정보 검색")
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
		
		String dong = (String) params.get("dong");
		String aptName = (String) params.get("aptname");
				
		if(dong != null && dong.length() > 0)
			bean.setDong(dong);
		else
			bean.setDong(null);
				
		if(aptName != null && aptName.length() > 0)
			bean.setAptname(aptName);
		else
			bean.setAptname(null);
				
		// 체크박스 체크상태 유지를 위해 세션에 설정해주기
		if(bean.getSearchType()[0]) session.setAttribute("adck", "checked");
		else						session.setAttribute("adck", "");
		if(bean.getSearchType()[1]) session.setAttribute("hdck", "checked");
		else						session.setAttribute("hdck", "");
		if(bean.getSearchType()[2]) session.setAttribute("arck", "checked");
		else						session.setAttribute("arck", "");
		if(bean.getSearchType()[3]) session.setAttribute("hrck", "checked");
		else						session.setAttribute("hrck", "");
						
		// 검색 인풋박스 유지를 위해 세션에 설정해주기
		if(bean.getDong() != null)		session.setAttribute("donginp", bean.getDong());
		else							session.setAttribute("donginp", "");
		if(bean.getAptname() != null)	session.setAttribute("aptinp", bean.getAptname());
		else							session.setAttribute("aptinp", "");
				
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
		map.put("nav", pageNavigation.getNavigator());
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
}
