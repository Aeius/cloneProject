package com.bit.checkpayclone.admin.login.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
class AdminLoginControllerTest {
	
	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	private static HttpHeaders headers = new HttpHeaders();
	
	private String memb_cd = "admin5";
	private static String rtk;

	/**
	 * 통합 어드민 관리자 로그인 테스트
	 * 
	 * @throws Exception
	 */
	@BeforeAll
	static void login() throws Exception {
		headers.add("Content-Type", "application/json");
		String jsonParameter = "{\"id\":\"admin5\", \"password\":\"123123\"}";
		
		HttpEntity request = new HttpEntity<>(jsonParameter, headers);
		
		String url = contextPath + "/api/login/";
		
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		JSONObject json = new JSONObject(response.getBody().toString());
		
		System.out.println(json);
		
		headers.remove("Content-Type");
		headers.add("Authorization", "Bearer " + json.get("atk").toString());
		rtk = json.get("rtk").toString();
	}

	/**
	 * 통합 어드민 관리자 로그아웃 테스트
	 * 
	 * @throws Exception
	 */
	@AfterAll
	static void logout() throws Exception {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("rtk", rtk);
		HttpEntity request = new HttpEntity<>(parameter, headers);
		
		String url = contextPath + "/api/login/logout";
		
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		System.out.println(response);
	}

	@Test
	void testTokenRefresh() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("id", memb_cd);
		parameter.add("rtk", rtk);
		HttpEntity request = new HttpEntity<>(parameter, headers);
		
		String url = contextPath + "/api/login/refresh";
		
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		
		System.out.println(request.getHeaders());

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
