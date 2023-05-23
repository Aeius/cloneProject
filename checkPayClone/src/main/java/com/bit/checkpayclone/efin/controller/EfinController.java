package com.bit.checkpayclone.efin.controller;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.efin.model.EfinVo;
import com.bit.checkpayclone.efin.service.EfinService;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/efin")
@RestController
public class EfinController {
	
	final EfinService efinService;
	
	/**권면 관련 API (prepaid)
	 * 
	 * 권면 거래내역 리스트 조회 /prepaid/transactions  findPrepaidTranAll
	 * 
	 * 2023.05.01 prepaid 종합정보 API 추가 /prepaid/info   findPrepaidInfoOne
	 * 
	 * 권면 거래내역 상세정보 조회 /prepaid/transactions/detail   findPrepaidTranDetailOne
	 * @param bean
	 * @return 
	 */
	//권면 거래내역 리스트 조회 /prepaid/transactions
	@PostMapping("/prepaid/transactions")
	public ResponseEntity<?> findPrepaidTranAll(@RequestBody EfinVo bean){
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
	    
	    return new ResponseEntity<>(new Gson().toJson(efinService.findPrepaidTranList(bean)), headers, HttpStatus.OK);
	}
	
	//2023.05.01 prepaid 종합정보 API 추가 /prepaid/info
	@PostMapping("/prepaid/info")
	public ResponseEntity<?> findPrepaidInfoOne(@RequestBody EfinVo bean){
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
	    
	    return new ResponseEntity<>(new Gson().toJson(efinService.findPrepaidInfoOne(bean)), headers, HttpStatus.OK);
	}
	
	//권면 거래내역 상세정보 조회 /prepaid/transactions/detail
	@PostMapping("/prepaid/transactions/detail")
	public EfinVo findPrepaidTranDetailOne(@RequestBody EfinVo bean){
		return efinService.findPrepaidTranDetailOne(bean);
	}
	
	//
	@GetMapping("/prepaid/{id}")
	public List<EfinVo> findPrepaidList(@PathVariable("id") String id){
		return efinService.findPrepaidList(id);
	}
	/**
	 * 기관별 권면 계정 조회
	 * 23/05/02 수정
	 * */
	@GetMapping("/prepaid/accountid/{id}/{orgname}")
	public List<EfinVo> findAccountId(@PathVariable("id") String id,@PathVariable("orgname") String orgname){
		return efinService.findAccountIdByOrgName(id, orgname);
	}
	
//	@PostMapping("/prepaid")
//	public List<EfinVo> findPrepaidList(@RequestBody EfinVo bean){
//		return efinService.findPrepaidList(bean);
//	}
	
	
	/** 계정 관련 API (paid)
	 * 2023.05.02 계정 거래내역 리스트 조회 수정 /paid/transactions  findPaidTranList
	 * 
	 * 2023.05.02 계정 거래내역 상세정보 조회 수정 /paid/transactions/detail   findPaidTranDetailOne
	 * 
	 * 2023.05.02 paid 종합정보 API 추가 /paid/info
	 * @param bean
	 * @return 
	 */
	
	//2023.05.02 계정 거래내역 리스트 조회 수정 /paid/transactions  findPaidTranList
//	@GetMapping("/paid/transactions")
//	public List<EfinVo> findPaidTranByAccNm(@RequestBody EfinVo bean){
//		return efinService.findPaidTranByAccNm(bean);
//	}
	@PostMapping("/paid/transactions")
	public ResponseEntity<?> findPaidTranList(@RequestBody EfinVo bean){
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
	    
	    return new ResponseEntity<>(new Gson().toJson(efinService.findPaidTranList(bean)), headers, HttpStatus.OK);
	}
	
	//2023.05.02 계정 거래내역 상세정보 조회 수정 /paid/transactions/detail   findPaidTranDetailOne
//	@GetMapping("/paid/transactions/detail")
//	public EfinVo findPaidTranDetail(@RequestBody EfinVo bean) {
//		return efinService.findPaidTranDetail(bean);
//	}
	@PostMapping("/paid/transactions/detail")
	public EfinVo findPaidTranDetailOne(@RequestBody EfinVo bean){
		return efinService.findPaidTranDetailOne(bean);
	}
	
	// 2023.05.02 paid 종합정보 API 추가 /paid/info
	@PostMapping("/paid/info")
	public ResponseEntity<?> findPaidInfoOne(@RequestBody EfinVo bean) {

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

		return new ResponseEntity<>(new Gson().toJson(efinService.findPaidInfo(bean)), headers, HttpStatus.OK);
	}

}
