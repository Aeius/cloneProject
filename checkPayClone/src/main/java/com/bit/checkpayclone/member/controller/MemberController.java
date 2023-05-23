package com.bit.checkpayclone.member.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bit.checkpayclone.member.model.MemberVo;
import com.bit.checkpayclone.member.service.MemberService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
	private final MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@ModelAttribute MemberVo bean, HttpServletRequest request){
		return ResponseEntity.ok(memberService.login(bean, request));
	}
	
	// 회원가입 api
	@PostMapping("/join")
	public ResponseEntity<?> join(@ModelAttribute MemberVo bean) {
		return ResponseEntity.ok(memberService.join(bean));
	}
	
	// 고객인증 api
	@GetMapping("/auth")
	public ResponseEntity<?> memberAuth() {
		return ResponseEntity.ok(memberService.memberAuth());
	}
	
	// 고객정보 api
	@GetMapping("/{member_id}")
	public ResponseEntity<?> memberInfo(@PathVariable String member_id) {
		MemberVo bean = memberService.memberInfo(member_id, false, "member_id");
		return ResponseEntity.ok(bean);
	}
	
	// 회원(이름, 이메일)수정 api
	// 회원 수정 이거 개판이라 임시로 이거 일단 쓰세요.
	@PutMapping("/{member_id}")
	public ResponseEntity<?> updateMember(@PathVariable String member_id, @RequestBody MemberVo bean, HttpServletRequest req) throws URISyntaxException {
		bean.setMemb_cd(member_id);
		if(memberService.updateMember(bean) > 0) {
			RestTemplate template = new RestTemplate();
			URI url = new URI(req.getRequestURL().toString());
			RequestEntity param = new RequestEntity(HttpMethod.GET, url);
			return template.exchange(param, MemberVo.class);
		}
		return ResponseEntity.internalServerError().build();
	}
	
	// 회원탈퇴 api
	@DeleteMapping("/{member_id}")
	public ResponseEntity<?> deleteMember(@PathVariable String member_id) {
		memberService.deleteMember(member_id);
		return ResponseEntity.ok().build();
	}
	
	
	//----2023.04.18 tb_member 테이블 관련 API 추가-----
	@PutMapping("/member/{member_id}")
	public ResponseEntity<?> updateMember2(@PathVariable String member_id, @RequestBody MemberVo bean, HttpServletRequest req) throws URISyntaxException {
		bean.setMemb_cd(member_id);
		if(memberService.updateMember2(bean) > 0) {
			return ResponseEntity.ok(bean);
		}
		return ResponseEntity.internalServerError().build();
	}
	
	//----2023.04.17 tb_member_fin 테이블 관련 API 추가-----
	@GetMapping("/member-fin/{member_id}")
	public ResponseEntity<?> memberFinSelectOne(@PathVariable String member_id){
		return ResponseEntity.ok(memberService.finInfoSelectOne(member_id));
	}
	
	//----2023.05.11 tb_member 이메일 중복 검사 추가
	@GetMapping("/email-duplication/{email}")
	public ResponseEntity<?> emailDuplication(@PathVariable String email){
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("result", memberService.memberEmailCount(email));
		
		HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
	    
	    return new ResponseEntity<>(new Gson().toJson(jsonObject), headers, HttpStatus.OK);
	}
}
