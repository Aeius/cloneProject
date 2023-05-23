package com.bit.checkpayclone.paybills.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.paybills.service.PayBillsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PayBillsController {
	
	final PayBillsService billsService;
	
	// 청구 상세
	@GetMapping("/api/bills/{member_id}/{org_code}/{paid_date}") 
	public ResponseEntity<?> billsCard(@PathVariable String member_id, @PathVariable String org_code, @PathVariable String paid_date ) {
		return ResponseEntity.ok(billsService.payBillsCard(member_id, org_code, paid_date));
	}
	
	// 청구 할부 상세
	@GetMapping("/api/bills/{member_id}/{org_code}/{prod_type}/{paid_date}") 
	public ResponseEntity<?> billsCardInstall(@PathVariable String member_id, @PathVariable String org_code, @PathVariable String prod_type, @PathVariable String paid_date ) {
		return ResponseEntity.ok(billsService.payBillsCardInstall(member_id, org_code, prod_type, paid_date));
	}
		
}
