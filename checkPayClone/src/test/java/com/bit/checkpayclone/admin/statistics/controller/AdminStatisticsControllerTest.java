package com.bit.checkpayclone.admin.statistics.controller;

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
class AdminStatisticsControllerTest {
	
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
	void testGetMemberChart1Data() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/memberChart1Data").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetMemberChart2Data() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/memberChart2Data").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetAssetChart1Data() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/assetChart1Data").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetAssetChart2Data() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/assetChart2Data").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetBankChartData() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/bankChartData").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetCardChartData() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/cardChartData").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetEfinChartData() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/efinChartData").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetInvestChartData() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/investChartData").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetTelecomChartData() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/telecomChartData").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testGetCapitalChartData() {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("selectYear", "2023");

		UriComponents uri = UriComponentsBuilder.fromHttpUrl(contextPath+"/api/admin/statistics/capitalChartData").queryParams(parameter).build();
		
		String url = uri.toString();
		
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
