package com.bit.checkpayclone.telecom.controller;

import java.sql.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.telecom.service.TelecomService;

//import com.bit.checkpayclone.telecom.serivce.TelecomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TelecomController {
	
	final TelecomService myTelecomService;
	
	@GetMapping("/api/telecom/main/{id}")
	public ResponseEntity<?> memberbillList(@PathVariable("id") String id){
		return ResponseEntity.ok(myTelecomService.selectbill(id));
	}
	
	@GetMapping("/api/telecom/bill/{id}")
	public ResponseEntity<?> currentbillList(@PathVariable("id") String id){
		return ResponseEntity.ok(myTelecomService.selectcurrentbill(id));
	}
	
	@GetMapping("/api/telecom/detialtop/{id}")
	public ResponseEntity<?> detailCount(@PathVariable("id") String id){
		return ResponseEntity.ok(myTelecomService.selectdetailtop(id));
	}
	
	@GetMapping("/api/telecom/detailBill/{id}/{start_date}/{end_date}/{sort}/{offset}")
	public ResponseEntity<?> detailBillList(@PathVariable("id") String id,
			@PathVariable("start_date") Date start_date,
			@PathVariable("end_date") Date end_date,
			@PathVariable("sort") String sort,
			@PathVariable("offset") int offset
			){
		return ResponseEntity.ok(myTelecomService.selectbillDetail(id, start_date, end_date, sort, offset));
	}
	
	@GetMapping("/api/telecom/Micropayment/{id}/{start_date}/{end_date}/{sort}/{offset}")
	public ResponseEntity<?> detailMicropaymentList(@PathVariable("id") String id,
			@PathVariable("start_date") Date start_date,
			@PathVariable("end_date") Date end_date,
			@PathVariable("sort") String sort,
			@PathVariable("offset") int offset
			){
		return ResponseEntity.ok(myTelecomService.selectMicropaymentDetail(id, start_date, end_date, sort, offset));
	}
}




