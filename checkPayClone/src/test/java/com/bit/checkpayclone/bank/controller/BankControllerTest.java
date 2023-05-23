package com.bit.checkpayclone.bank.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.bit.checkpayclone.bank.model.BankTransReqVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailReqVo;


@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BankControllerTest {

	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	static HttpHeaders headers = new HttpHeaders();
	
	private String memb_cd = "U000000000U000000022";
	private String account_num = "5918747029547";
	private String org_code = "A1AAEO0000";
	private String prod_type = "401";
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
	
	@DisplayName("수신계좌 목록 조회 API")
	@Test
	void testMemberAccountList() throws Exception {
		System.out.println(headers);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/bank/memberact/" + memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("수신계좌 거래내역 조회 API")
	@Test
	void testBankDepositTransList() throws Exception {
		BankTransReqVo parameters = BankTransReqVo.builder()
				.memb_cd(memb_cd)
				.org_code("A1AAEO0000")
				.account_num("5918747029544")
				.start_date(Date.valueOf("2023-02-01"))
				.end_date(Date.valueOf("2023-02-28"))
				.trans_type("both")
				.sort("desc")
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/bank/depositTrans", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("투자 상품(펀드, 신탁) 상세 조회 API")
	@Test
	void testBankInvestList() throws Exception {
		System.out.println(headers);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/bank/investDetail/" + memb_cd+"/"+account_num, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("투자 상품(펀드, 신탁) 거래내역 조회 API")
	@Test
	void testBankInvestTransList() throws Exception {
		BankTransReqVo parameters = BankTransReqVo.builder()
				.memb_cd(memb_cd)
				.org_code("A1AAEO0000")
				.account_num("5918747029544")
				.start_date(Date.valueOf("2023-01-01"))
				.end_date(Date.valueOf("2023-04-28"))
				.sort("desc")
				.offset(0)
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/bank/investTrans", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("은행 대출 상세 조회 API")
	@Test
	void testSelectUserCapitalDetail() throws Exception {
		System.out.println(headers);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/bank/bankcapitaldetail/"+ memb_cd+"/"+org_code+"/"+account_num, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
}
