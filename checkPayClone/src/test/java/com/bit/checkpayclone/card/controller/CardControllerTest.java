package com.bit.checkpayclone.card.controller;

import static org.junit.jupiter.api.Assertions.*;

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


@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CardControllerTest {

	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	static HttpHeaders headers = new HttpHeaders();
	
	private String memb_cd = "U000000000U000000001";
	private String org_code = "D1AAEV0000";
	private static String rtk;

	@BeforeAll
	static void login() throws Exception {
		// 로그인
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("member_email", "test00@gmail.com");
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
	
	@DisplayName("자산 메인에서 사용하는 카드 조회 API")
	@Test
	void testMemberCardList() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/card/membercard/" + memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

//	@DisplayName("자산 메인에서 사용하는 카드 조회 API")
//	@Test
//	void testMemberCardName() {
//		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
//		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/card/membercard/" + memb_cd, HttpMethod.GET, request, String.class);
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
//	}
	
	@DisplayName("자산 메인에서 사용하는 카드장기대출 API")
	@Test
	void testMembercardloanLongList() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/card/membercardloanlong/" + memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("자산 메인에서 사용하는 카드단기대출 API")
	@Test
	void testMembercardloanShortList() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/card/membercardloanshort/" + memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("카드 포인트 조회 API")
	@Test
	void testMembercardPoint() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/card/membercardpoint/" + memb_cd+"/"+org_code, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("카드 리볼빙 조회 API")
	@Test
	void testMembercardRevolvig() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/card/revolvig/" + memb_cd+"/"+org_code, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
