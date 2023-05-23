package com.bit.checkpayclone.capital.controller;

import java.sql.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.bank.model.BankTransReqVo;
import com.bit.checkpayclone.capital.service.CapitalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CapitalController {
	
	final CapitalService myCapitalService;
	
	@GetMapping("/api/capital/membercapital/{id}") 
	public ResponseEntity<?> selectUserCapital(@PathVariable("id") String id){
		return ResponseEntity.ok(myCapitalService.selectUserCapital(id));
	}
	@GetMapping("/api/capital/capitaldetail/{id}/{org_code}/{account_num}")
	public ResponseEntity<?> selectUserCapitalDetail(@PathVariable("id") String id,
			@PathVariable("org_code") String org_code,
			@PathVariable("account_num") String account_num){
		return ResponseEntity.ok(myCapitalService.selectUserCapitalDetail(id,org_code,account_num));
	}

	@GetMapping("/api/capital/capitaltransdetail/{id}/{org_code}/{account_num}/{start_date}/{end_date}/{sort}/{offset}")
	public ResponseEntity<?> selectUserCapitalTransDetail(@PathVariable("id") String id,
			@PathVariable("org_code") String org_code,
			@PathVariable("account_num") String account_num,
			@PathVariable("start_date") Date start_date,
			@PathVariable("end_date") Date end_date,
			@PathVariable("sort") String sort,
			@PathVariable("offset") int offset
			){
		return ResponseEntity.ok(myCapitalService.selectCapitalTransDetail(id,org_code,account_num,start_date,end_date,sort,offset));
	}
}
