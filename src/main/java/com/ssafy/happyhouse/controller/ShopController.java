package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.happyhouse.dto.ShopInfo;
import com.ssafy.happyhouse.service.ShopService;
import com.ssafy.happyhouse.util.PageNavigation;

@RequestMapping("/shop")
@Controller
public class ShopController {

	private ShopService service;
	
	@Autowired
	public void setService(ShopService service) {
		this.service = service;
	}
	
	@GetMapping("/shop.do")
	private String shopSearch(Model model, String city, String gu, String dong, HttpSession session) {		
		
		session.setAttribute("shop_city", city);
		session.setAttribute("shop_gu", gu);
		session.setAttribute("shop_dong", dong);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("city", city);
		map.put("gu", gu);
		map.put("dong", dong);
		map.put("start", 0);
		map.put("interval", 10);
		
		int n = service.countShop(map);
		
		List<ShopInfo> list = service.searchAllShop(map);
		
		if(list.size() > 0) {
			model.addAttribute("shopList", list);
		}
		
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(1, 10, n, "_shop_");
			model.addAttribute("shopPaging", pageNavigation);
		}
		
		return "interestlist";
	}
	
	@GetMapping("/pageNav.do")
	private String envPaging(int pg, HttpSession session, Model model) {
		String city = (String)session.getAttribute("shop_city");
		String gu = (String)session.getAttribute("shop_gu");
		String dong = (String)session.getAttribute("shop_dong");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("city", city);
		map.put("gu", gu);
		map.put("dong", dong);
		map.put("start", (pg - 1) * 10);
		map.put("interval", 10);
		
		int n = service.countShop(map);
		
		List<ShopInfo> list = service.searchAllShop(map);
		
		if(list.size() > 0) {
			model.addAttribute("shopList", list);
		}
		
		if(n > 10) {
			PageNavigation pageNavigation = 
					PageNavigation.makePageNavigation(pg, 10, n, "_shop_");
			model.addAttribute("shopPaging", pageNavigation);
		}
		
		return "interestlist";
	}
}
