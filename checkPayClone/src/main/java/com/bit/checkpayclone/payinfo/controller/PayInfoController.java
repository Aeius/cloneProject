package com.bit.checkpayclone.payinfo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.payinfo.service.PayInfoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/payinfo/{member_id}/{card_id}")
@RestController
public class PayInfoController {
	
	final PayInfoService payInfoService;
	
	// 특정 멤버 월별 카드결제 정보 (전체, 최신순)디폴트 api
	@GetMapping("/{year_month}/{limit}")
	public ResponseEntity<?> payInfo(@PathVariable String member_id,
										@PathVariable String card_id,
										@PathVariable String year_month, 
										@PathVariable int limit) {
		return ResponseEntity.ok(payInfoService.payInfoSelectDesc(member_id, card_id, year_month, limit));
	}
	
	// 특정 멤버 월별 카드결제 정보 (결제방법) api
	@GetMapping("{installments}/{year_month}/{limit}")
	public ResponseEntity<?> payInfoInstall(@PathVariable String member_id, 
										@PathVariable String card_id, 
										@PathVariable int installments, 
										@PathVariable String year_month,
										@PathVariable int limit) {
		return ResponseEntity.ok(payInfoService.payInfoSelectInstallments(member_id, card_id, installments, year_month, limit));
	}
	
	// card_payinfo 필터 날짜 범위 총 금액
	@GetMapping("v1/{startDate}/{endDate}")
	public ResponseEntity<?> payInfoFilterAmt(@PathVariable String member_id, @PathVariable String card_id, @PathVariable String startDate, @PathVariable String endDate) {
		return ResponseEntity.ok(payInfoService.payInfoFilterAmt(member_id, card_id, startDate, endDate));
	}
	
	// 날짜 사이 결제 정보 api(전체, 최신순,과거순) 디폴트 
	@GetMapping("{startDate}/{endDate}/{sort}/{keyword}/{limit}")
	public ResponseEntity<?> payInfoFilterAll(@PathVariable String member_id, 
											@PathVariable String card_id, 
											@PathVariable String startDate,
											@PathVariable String endDate,
											@PathVariable String sort,
											@PathVariable String keyword,
											@PathVariable int limit
											) {
		return ResponseEntity.ok(payInfoService.payInfoFilterAll(member_id, card_id, startDate, endDate, sort, keyword, limit));
	}
	
	// 날짜 사이 결제 정보 api(결제방법, 최신순,과거순) 
	@GetMapping("{startDate}/{endDate}/{sort}/{installments}/{keyword}/{limit}")
	public ResponseEntity<?> payInfoFilterInstall(@PathVariable String member_id, 
												@PathVariable String card_id, 
												@PathVariable int installments, 
												@PathVariable String startDate, 
												@PathVariable String endDate, 
												@PathVariable String sort,
												@PathVariable String keyword,
												@PathVariable int limit) {
		return ResponseEntity.ok(payInfoService.payInfoFilterInstallAll(member_id, card_id, installments, startDate, endDate, sort, keyword, limit));
	}
	
	
	// 날짜 사이 결제 정보 api(결제 상태, 최신순, 과거순)
	@GetMapping("v1/{state}/{startDate}/{endDate}/{sort}/{keyword}/{limit}")
	public ResponseEntity<?> payInfoFilterStatus(@PathVariable String member_id, 
												@PathVariable String card_id, 
												@PathVariable String state, 
												@PathVariable String startDate,
												@PathVariable String endDate, 
												@PathVariable String sort,
												@PathVariable String keyword,
												@PathVariable int limit) {
		return ResponseEntity.ok(payInfoService.payInfoFilterStatus(member_id, card_id, state, startDate, endDate, sort, keyword, limit));
	}
	
	
	// 날짜 사이 결제 정보 api(결제 상태, 결제방법, 최신순,과거순)
	@GetMapping("v1/{state}/{installments}/{startDate}/{endDate}/{sort}/{keyword}/{limit}")
	public ResponseEntity<?> payInfoFilterInstallStatus(@PathVariable String member_id,
													@PathVariable String card_id,
													@PathVariable String state, 
													@PathVariable int installments,
													@PathVariable String startDate, 
													@PathVariable String endDate, 
													@PathVariable String sort,
													@PathVariable String keyword,
													@PathVariable int limit) {
		return ResponseEntity.ok(payInfoService.payInfoFilterInstallStatus(member_id, card_id, state, installments, startDate, endDate, sort, keyword, limit));
	}
	
	
}
