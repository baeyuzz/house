package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.happyhouse.dto.InterestRegion;
import com.ssafy.happyhouse.service.InterestRegionService;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Interest Controller")
@Controller
@RequestMapping("/interest")
public class InterestController {
	private InterestRegionService service;

	@Autowired
	public void setService(InterestRegionService service) {
		this.service = service;
	}
	

	@GetMapping("/addRegion.go")
	private String addRegionGo() {
		return "addRegion";
	}
	
	@GetMapping("/add.do") // interestlist.jsp 에서 관심지역 추가 버튼 누르면 addregion.jsp로 가고 거기서 submit하면 이리로 옴 
	private String interestAdd(Model model,HttpSession session, String province, String city, String dong) {
		String id = (String)session.getAttribute("id");
		service.addInterestRegion(new InterestRegion(province, city, dong, id));
		return "redirect:/interest/list.do";
	}



	@GetMapping(value = "/list.do")
	private String interestRegionList(HttpSession session) {
		String id = (String)session.getAttribute("id");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("start", 0);
		map.put("interval", 10);
		
		int n = service.countRegion(id);
		
		List<InterestRegion> list = service.searchInterestRegion(map);
		
		if(list.size() > 0) {
			session.setAttribute("irList", list);
		}
		
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(1, 10, n, "_ir_");
			session.setAttribute("irPaging", pageNavigation);
		}
		
		return "interestlist";
	}
	
	@GetMapping("/pageNav.do")
	private String interestPaging(int pg, HttpSession session) {
		String id = (String)session.getAttribute("id");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("start", (pg - 1) * 10);
		map.put("interval", 10);
		
		int n = service.countRegion(id);
		
		List<InterestRegion> list = service.searchInterestRegion(map);
		
		if(list.size() > 0) {
			session.setAttribute("irList", list);
		}
		
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(pg, 10, n, "_ir_");
			session.setAttribute("irPaging", pageNavigation);
		}
		
		return "interestlist";
	}
	
	@GetMapping("/remove.do")
	private String interestRemove(Model model, int no) {
		service.deleteInterestRegion(no);
		return  "redirect:/interest/list.do";
	}
}
