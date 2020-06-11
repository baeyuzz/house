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

import com.ssafy.happyhouse.dto.EnvInfo;
import com.ssafy.happyhouse.service.EnvService;
import com.ssafy.happyhouse.util.PageNavigation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Env Controller")
@RequestMapping("/env")
@Controller
public class EnvController {

	private EnvService service;
	
	@Autowired
	public void setService(EnvService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "관심지역의 모든 환경관련 정보 조회(페이지 단위)")
	@GetMapping("/env.do")
	private String envSearch(Model model, String dong, HttpSession session) {
		session.setAttribute("ir_dong", dong);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dong", dong);
		map.put("start", 0);
		map.put("interval", 10);
		
		int n = service.countEnv(dong);
		
		List<EnvInfo> list = service.searchAllEnvInfo(map);
		
		if(list.size() > 0) {
			model.addAttribute("envList", list);
		}
		
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(1, 10, n, "_env_");
			model.addAttribute("envPaging", pageNavigation);
		}
		
		return "interestlist";
	}
	
	@ApiOperation(value = "환경정보 테이블 페이지 이동")
	@GetMapping("/pageNav.do")
	private String envPaging(int pg, HttpSession session, Model model) {
		String dong = (String)session.getAttribute("ir_dong");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dong", dong);
		map.put("start", (pg - 1) * 10);
		map.put("interval", 10);
		
		int n = service.countEnv(dong);
		
		List<EnvInfo> list = service.searchAllEnvInfo(map);
		
		if(list.size() > 0) {
			model.addAttribute("envList", list);
		}
		
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(pg, 10, n, "_env_");
			model.addAttribute("envPaging", pageNavigation);
		}
		
		return "interestlist";
	}
}
