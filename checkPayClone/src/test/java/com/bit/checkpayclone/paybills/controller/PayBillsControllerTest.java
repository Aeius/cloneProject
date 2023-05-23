package com.bit.checkpayclone.paybills.controller;

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

/**
 * @author 박진호
 * 청구 상세 API 조회
 */
@Transactional
//@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class PayBillsControllerTest {
	
	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	static HttpHeaders headers = new HttpHeaders();
	
	private static String rtk;

	private String memb_cd = "U000000000U000000022",org_code = "D1AAEV0000",
				   paid_date = "202304",prod_type = "01";
	
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
	
	@DisplayName("청구 상세 api")
	@Test
	void testBillsCard() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/bills/"+ memb_cd+ "/" + org_code+"/" +paid_date, HttpMethod.GET, request,  String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("청구 할부 상세 api")
	@Test
	void testBillsCardInstall() {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/bills/"+ memb_cd+ "/" + org_code+"/"+ prod_type+"/" +paid_date, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}
