//package com.bit.checkpayclone.admin.notice.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.TestConstructor;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import com.bit.checkpayclone.admin.notice.model.AdminNoticeVo;
//
//@Transactional
//@SpringBootTest
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
//class AdminNoticeControllerTest {
//
//	private static TestRestTemplate restTemplate = new TestRestTemplate();
//	private static final String contextPath = "http://192.168.240.208:8081";
////	private static final String contextPath = "http://localhost:8081";
//	private static HttpHeaders headers = new HttpHeaders();
//	
//	private int pageIndex = 1;
//	private int notice_num =184;
//	private static String rtk;
//	
//	@BeforeAll
//	   static void login() throws Exception {
//	      headers.add("Content-Type", "application/json");
//	      String jsonParameter = "{\"id\":\"admin\", \"password\":\"123123\"}";
//	      
//	      HttpEntity request = new HttpEntity<>(jsonParameter, headers);
//	      ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/login/" , HttpMethod.POST, request, String.class);
//	      JSONObject json = new JSONObject(response.getBody().toString());
//	      
//	      System.out.println(json);
//	      headers.remove("Content-Type");
//	      headers.add("Authorization", "Bearer " + json.get("atk").toString());
//	      rtk = json.get("rtk").toString();
//	   }
//	
//	@DisplayName("어드민 공지리스트 조회 API")
//	@Test
//	void test1GetNoticeList() {
//		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
//		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/admin/notice/" + pageIndex , HttpMethod.GET, request, String.class);
//		assertEquals(response.getStatusCode(), HttpStatus.OK);			
//	}
//
//	@DisplayName("어드민 공지디테일 조회 API")
//	@Test
//	void test2GetNoticeDetail() {
//		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//		HttpEntity request = new HttpEntity<MultiValueMap<String,String>>(parameters, headers);
//		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/admin/notice/detail/" + notice_num , HttpMethod.GET, request, String.class);
//		assertEquals(response.getStatusCode(), HttpStatus.OK);		
//	}
//
////	@DisplayName("어드민 공지작성 API")
////	@Test
////	void test3AddNotice() {
////		String subject="test 공지 작성 제목";
////		String content="test 공지 작성 내용";
////		AdminNoticeVo parameters = AdminNoticeVo.builder()
////				.subject(subject)
////				.content(content)
////				.build();
////		
////		HttpEntity request = new HttpEntity<>(parameters, headers);
////		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/admin/notice/add" , HttpMethod.POST, request, String.class);
////		assertEquals(response.getStatusCode(), HttpStatus.OK);
////	}
//
//	@DisplayName("어드민 공지수정 API")
//	@Test
//	void test4UpdateNotice() {
//		String subject="test 공지 작성 제목";
//		String content="test 공지 작성 내용";
//		String use_yn = "Y";
//		int num = 2;
//		AdminNoticeVo parameters = AdminNoticeVo.builder()
//				.subject(subject)
//				.content(content)
//				.use_yn(use_yn)
//				.notice_num(num)
//				.build();
//		
//		HttpEntity request = new HttpEntity<>(parameters, headers);
//		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/admin/notice/detail/modify" , HttpMethod.PUT, request, String.class);
//		assertEquals(response.getStatusCode(), HttpStatus.OK);
//	}
//
////	@DisplayName("어드민 공지삭제 API")
////	@Test
////	@Transactional()
////	void test5DeleteNotice() {
////		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
////		HttpEntity request = new HttpEntity<>(parameters, headers);
////		ResponseEntity<?> response = restTemplate.exchange(contextPath + "/api/admin/notice/detail/delete/"+notice_num , HttpMethod.DELETE, request, String.class);
////		assertEquals(response.getStatusCode(), HttpStatus.OK);
////	}
//
//	
//	/**
//	    * 통합 어드민 관리자 로그아웃 테스트
//	    * 
//	    * @throws Exception
//	    */
//	   @AfterAll
//	   static void logout() throws Exception {
//	      MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
//	      parameter.add("rtk", rtk);
//	      HttpEntity request = new HttpEntity<>(parameter, headers);
//	      
//	      String url = contextPath + "/api/login/logout";
//	      
//	      ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
//	      System.out.println(response);
//	   }
//}
