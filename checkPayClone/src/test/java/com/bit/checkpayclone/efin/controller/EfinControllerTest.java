package com.bit.checkpayclone.efin.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.bit.checkpayclone.efin.model.EfinVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailReqVo;

public class EfinControllerTest {
	
	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	static HttpHeaders headers = new HttpHeaders();
	
	private String memb_cd = "U000000000U000000001";
	
	private String start_date = "20200101";
	private String end_date = "20230503";
	private Character sort = 'D';
	
	private static String rtk;

	@BeforeAll
	static void login() throws Exception {
		// 로그인
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("member_email", "test01@gmail.com");
		parameter.add("member_password", "123123");
		HttpEntity request = new HttpEntity<>(parameter, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/member/login" , HttpMethod.POST, request, String.class);
		JSONObject json = new JSONObject(response.getBody().toString());
		headers.add("Authorization", "Bearer " + json.get("jwt").toString());
		rtk = json.get("rtk").toString();
	}
	
	@AfterAll
	static void logout() throws Exception {
		// 로그아웃
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("rtk", rtk);
		HttpEntity request = new HttpEntity<>(parameter, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/login/logout" , HttpMethod.POST, request, String.class);
	}
	
	@DisplayName("(메인 페이지)회원별 권면 리스트 조회 API")
	@Test
	void testMemberPrepaidList() throws Exception{
		System.out.println(headers);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/prepaid/" + memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("(선불페이 메인)해당 맴버 기관별 권면 리스트 조회 API")
	@Test
	void testMemberOrgPrepaidList() {
		System.out.println(headers);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/prepaid/accountid/" + memb_cd + "/" + "네이버페이", HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("계정 종합정보 조회 API")
	@Test
	void testPaidInfo() throws Exception {
		EfinVo parameters = EfinVo.builder()
				.memb_cd(memb_cd)
				.org_code("E1AAHY0000")
				.sub_key("naverpayacc0001")
				.build();
		
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/paid/info", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("계정 결제정보 리스트 조회 API")
	@Test
	void testPaidTransList() throws Exception {
		EfinVo parameters = EfinVo.builder()
				.memb_cd(memb_cd)
				.org_code("E1AABE0000")
				.sub_key("key2002acc0004")
				.flag1(sort)
				.startDt(start_date)
				.endDt(end_date)
				.page(0)
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/paid/transactions", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("계정 결제정보 상세 조회 API")
	@Test
	void testPaidTransDetail() throws Exception {
		EfinVo parameters = EfinVo.builder()
				.memb_cd(memb_cd)
				.org_code("E1AABE0000")
				.sub_key("key2002acc0004")
				.trans_dtime("20221218114253")
				.trans_no("")
				.seq(1)
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/paid/transactions/detail", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("권면 종합정보 조회 API")
	@Test
	void testPrepaidInfo() throws Exception {
		EfinVo parameters = EfinVo.builder()
				.memb_cd(memb_cd)
				.org_code("E1AABE0000")
				.fob_id("skfobid000001")
				.build();
		
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/prepaid/info", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("권면 결제정보 리스트 조회 API")
	@Test
	void testPrepaidTransList() throws Exception {
		EfinVo parameters = EfinVo.builder()
				.memb_cd(memb_cd)
				.org_code("E1AABE0000")
				.fob_id("skfobid000001")
				.flag1(sort)
				.startDt(start_date)
				.endDt(end_date)
				.page(0)
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/prepaid/transactions", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("권면 결제정보 상세 조회 API")
	@Test
	void testPrepaidTransDetail() throws Exception {
		EfinVo parameters = EfinVo.builder()
				.memb_cd(memb_cd)
				.org_code("E1AABE0000")
				.fob_id("skfobid000001")
				.account_id("skacc0000001")
				.trans_type("5501")
				.trans_dtime("20220803130000")
				.trans_no("")
				.trans_dt("20220803")
				.seq(8)
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/efin/paid/transactions/detail", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
