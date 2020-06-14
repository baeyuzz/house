package com.ssafy.happyhouse.crime;

import java.util.ArrayList;
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

import com.ssafy.happyhouse.dto.Crime;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// Crime 컨트롤러가 여기에 있는 줄은 몰랐네
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("Crime Controller")
@Controller
@RequestMapping("/rest/crime")
public class CrimeController {
	
	private CrimeService service;
	@Autowired
	public void setService(CrimeService service) {
		this.service = service;
	}
	
	@ApiOperation(value = "그래프를 그려주기 위한 데이터 조회")
	@ResponseBody
	@PostMapping
	public ResponseEntity<Map<String, Object>> getChartData(@RequestBody Map<String, Object> param) {
		List<Crime> list = service.searchCrime(param);
		
		List<String> labels = new ArrayList<String>();
		List<Integer> data = new ArrayList<Integer>();
		
		for(Crime crime : list) {
			String c = crime.getCrime();
			labels.add(c);
			data.add(crime.getCount());			
		}
		
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("labels", labels);
		ret.put("data", data);
		
		return new ResponseEntity<Map<String,Object>>(ret, HttpStatus.OK);
	}
}
