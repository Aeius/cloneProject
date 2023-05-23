package com.bit.checkpayclone.invest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.bit.checkpayclone.invest.model.InvestAccountDetailReqVo;
import com.bit.checkpayclone.invest.model.InvestAccountTransReqVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailReqVo;

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class InvestControllerTest {
	
	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	private static HttpHeaders headers = new HttpHeaders();
	
	private String memb_cd = "U000000000U000000022";
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
	
	@DisplayName("증권 투자 계좌 리스트 조회 API")
	@Test
	void testFindUserInvestAccountList() throws Exception {
		System.out.println(headers);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/account/list/" + memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("증권 투자 상품별 리스트 조회 API")
	@Test
	void testFindUserInvestProductsList() throws Exception {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/product/list/" + memb_cd , HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("증권 투자 상품별 상세 리스트 조회 API")
	@Test
	void testFindUserInvestProductDetailList() throws Exception {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/product/list/"+memb_cd + "/" + prod_type, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("증권 투자 상품 상세 정보 조회 API")
	@Test
	void testFindUserInvestProductDetail() throws Exception {
		InvestProductsDetailReqVo parameters = InvestProductsDetailReqVo.builder()
				.memb_cd(memb_cd)
				.org_code("C1AAIK0000")
				.account_num("IhngwfP8vZ00551U6osTgg==")
				.seq(1)
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/product/detail", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("증권 종목 리스트 조회 API")
	@Test
	void testfindUserInvestStockDetailList() throws Exception {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/stock/list/"+memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("증권 투자 계좌 상세 조회 API")
	@Test
	void testFindUserInvestAccountDetail() throws Exception {
		InvestAccountDetailReqVo parameters = InvestAccountDetailReqVo.builder()
				.memb_cd(memb_cd)
				.org_code("C1AAIK0000")
				.account_num("IhngwfP8vZ00551U6osTgg==")
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/account/detail", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("증권 투자 계좌 투자종목 조회 API")
	@Test
	void testFindUserInvestAccountProductsList() throws Exception {
		InvestAccountDetailReqVo parameters = InvestAccountDetailReqVo.builder()
				.memb_cd(memb_cd)
				.org_code("C1AAIK0000")
				.account_num("IhngwfP8vZ00551U6osTgg==")
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/account/product", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("증권 투자 계좌 거래내역 조회 API")
	@Test
	void testFindUserInvestAccountTransList() throws Exception {
		InvestAccountTransReqVo parameters = InvestAccountTransReqVo.builder()
				.memb_cd(memb_cd)
				.org_code("C1AAIK0000")
				.account_num("IhngwfP8vZ00551U6osTgg==")
				.start_day("20230108")
				.end_day("20230111")
				.sort("asc")
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/invest/account/product", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
}
