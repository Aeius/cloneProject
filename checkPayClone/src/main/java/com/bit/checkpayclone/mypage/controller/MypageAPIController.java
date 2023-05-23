package com.bit.checkpayclone.mypage.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.admin.notice.model.AdminNoticeVo;
import com.bit.checkpayclone.admin.notice.service.AdminNoticeService;
import com.bit.checkpayclone.mypage.service.FinAuthService;
import com.bit.checkpayclone.mypage.service.MypageService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MypageAPIController {

	final MypageService mypageService;
	
	final AdminNoticeService noticeService;
	
	@Autowired
	FinAuthService finAuthService;

	@GetMapping("/api/mypage/faq")
	public ResponseEntity<?> mypagelist() {
		return ResponseEntity.ok(mypageService.MypagefaqSelectAll()); 
	}
	
	@GetMapping("/api/mypage/terms")
	public ResponseEntity<?> mypageterms() {
		return ResponseEntity.ok(mypageService.findTerms()); 
	}
	
	@GetMapping("/api/mypage/email-authentication")
	public String emailAthenticaionNumberCreate(String email, String sendType, String id, Integer limit, Long sendTimeOut, Long authCodeTimeOut) throws UnsupportedEncodingException {
		JsonObject resultObject = finAuthService.authJoinEmail(email, sendType, id, limit, sendTimeOut, authCodeTimeOut);

		return new Gson().toJson(resultObject);
	}
	
	@PostMapping("/api/mypage/email-authentication")
	public String emailAthenticaion(String email, String inputCode, String useType, String id, Integer limit, Long timeOut) throws UnsupportedEncodingException {
		if(inputCode == null) inputCode= "0";
		JsonObject resultObject = finAuthService.emailAuthentication(email, inputCode, useType, id, limit, timeOut);
		
		return new Gson().toJson(resultObject);
	}
	
	@PostMapping("/api/mypage/fin-authentication")
	public String finAthenticaion(String id, String step, String input, Integer limit) throws UnsupportedEncodingException {
		JsonObject resultObject;
		resultObject = finAuthService.finAuthentication(id, step, input, limit);
		
		return new Gson().toJson(resultObject);
	}
	
	@GetMapping("/api/mypage/notice/{pageIndex}")
	public Map<String, Object> getNoticeList(AdminNoticeVo searchVo, @PathVariable("pageIndex") int pageIndex, String board_tp, String use_yn,String pagination_yn) {
		return noticeService.getNoticeWithPaging(searchVo, pageIndex, board_tp, use_yn, "upd_dttm",pagination_yn,null);
	}
}
