package com.bit.checkpayclone.admin.member.controller;

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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class AdminMemberControllerTest {
	
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
		
		ResponseEntity<?> response = restTemplate.exchange(url , HttpMethod.POST, request, String.class);
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
	void testMemberList() {
		/**
		 * 파라미터 없음
		 */
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/member/").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);

		/**
		 * 페이지네이션 적용
		 */
		parameter.add("currentPageNum", "1");
		parameter.add("countDataInPage", "10");
		parameter.add("countInPageGroup", "5");
		
		uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/member/").queryParams(parameter).build();
		
		url = uri.toString();
		
		response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		/**
		 * 기간 적용
		 */
		parameter.add("beginDate", "2023-03-30 17:20:09");
		parameter.add("endDate", "2023-04-01 17:28:00");
		
		uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/member/").queryParams(parameter).build();
		
		url = uri.toString();
		
		response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		/**
		 * 검색 필터 적용
		 */
		parameter.add("searchColumn", "memb_nm");
		parameter.add("searchValue", "J");
		
		uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/member/").queryParams(parameter).build();
		
		url = uri.toString();
		
		response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testMemberDetail() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		
		String url = contextPath+"/api/admin/member/13";
		
		HttpEntity request = new HttpEntity<>(parameter, headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testMemberLog() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("member_id", "1");
		parameter.add("action_type", "1");
		parameter.add("api_url", "http://192.168.240.208:8081/api/asset/main/editinfo/{member_id}");
		
		String url = contextPath+"/api/admin/member/log";
		
		HttpEntity request = new HttpEntity<>(parameter, headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testMemberAsset() {
		/**
		 * 파라미터 없음
		 */
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		
		String url = contextPath+"/api/admin/member/asset/U000000000U000000001";
		
		HttpEntity request = new HttpEntity<>(parameter, headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		/**
		 * 페이지네이션 적용
		 */
		parameter.add("currentPageNum", "1");
		parameter.add("countDataInPage", "10");
		parameter.add("countInPageGroup", "5");
		
		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/member/asset/U000000000U000000001").queryParams(parameter).build();
		
		url = uri.toString();
		
		response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
