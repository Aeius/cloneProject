package com.bit.checkpayclone.asset.controller;

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

import com.bit.checkpayclone.asset.main.model.UpdateinfoVo;


@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class assetControllerTest {
	
	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static final String contextPath = "http://192.168.240.208:8081";
	private static HttpHeaders headers = new HttpHeaders();
	
	private String memb_cd = "U000000000U000000022";
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
	
	@DisplayName("자산 메인 유저 자산 편집 순서 조회 API")
	@Test
	void testFindUserAssetMainEditList() throws Exception {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/asset/main/editinfo/" + memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	
	@DisplayName("자산 메인 유저 자산 편집 순서 수정 API")
	@Test
	void testUpdateUserAssetMainEditList() throws Exception {
		String[] edit_list={"mainpay","maininvest","maincap","mainphone","mainbank","maincard"};
		String[] hide_list={};
		UpdateinfoVo parameters = UpdateinfoVo.builder()
				.member_id(memb_cd)
				.edit_list(edit_list)
				.hide_list(hide_list)
				.build();
		HttpEntity request = new HttpEntity<>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/asset/main/editinfo/", HttpMethod.POST, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@DisplayName("자산메인 유저의 저번달 자산의 합 조회 API")
	@Test
	void testFindUserAssetMainSumOfLastMonth() throws Exception {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/asset/main/"+memb_cd, HttpMethod.GET, request, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
}
