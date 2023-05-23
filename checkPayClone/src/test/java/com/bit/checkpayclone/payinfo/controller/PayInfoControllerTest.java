package com.bit.checkpayclone.payinfo.controller;

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

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PayInfoControllerTest {

	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	private static String rtk;
	static HttpHeaders headers = new HttpHeaders();
	
	private String memb_cd="U000000000U000000022", 
					card_id="dd", year_month="202305", start_date="20221101", end_date="20230504", 
					state="01", sort="desc", keyword="@none";
	private int installments=0, offset=0;

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
	
	@DisplayName("특정 멤버 월별 카드결제 정보 (전체, 최신순)디폴트 api")
	@Test
	void testPayInfo() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(
				contextPath + "/api/payinfo/" + memb_cd + "/" + card_id + "/" + year_month + "/" + offset,
				HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("특정 멤버 월별 카드결제 정보 (결제방법) api")
	@Test
	void testPayInfoInstall() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/payinfo/" + memb_cd + "/" + card_id + "/"
				+ installments + "/" + year_month + "/" + offset, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("card_payinfo 필터 날짜 범위 총 금액 api")
	@Test
	void testPayInfoFilterAmt() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(
				contextPath + "/api/payinfo/" + memb_cd + "/" + card_id + "/v1" + "/" + start_date + "/" + end_date,
				HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("날짜 사이 결제 정보 api(전체, 최신순,과거순) 디폴트")
	@Test
	void testPayInfoFilterAll() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/payinfo/" + memb_cd + "/" + card_id + "/"
				+ start_date + "/" + end_date + "/" + sort + "/" + keyword + "/" + offset, HttpMethod.GET, request,
				String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("날짜 사이 결제 정보 api(결제방법, 최신순,과거순)")
	@Test
	void testPayInfoFilterInstall() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate
				.exchange(contextPath + "/api/payinfo/" + memb_cd + "/" + card_id + "/" + start_date + "/" + end_date
								+ "/" + sort + "/" + installments + "/" + keyword + "/" + offset,
						HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("날짜 사이 결제 정보 api(결제 상태, 최신순, 과거순)")
	@Test
	void testPayInfoFilterStatus() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/payinfo/" + memb_cd + "/" + card_id
				+ "/v1" + "/" + state + "/" + start_date + "/" + end_date+"/"+sort+"/"+keyword+"/"+offset, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("날짜 사이 결제 정보 api(결제 상태, 결제방법, 최신순,과거순)")
	@Test
	void testPayInfoFilterInstallStatus() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/payinfo/" + memb_cd + "/" + card_id
				+ "/v1" + "/" + state +"/"+installments+"/" + start_date + "/" + end_date+"/"+sort+"/"+keyword+"/"+offset, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
}
