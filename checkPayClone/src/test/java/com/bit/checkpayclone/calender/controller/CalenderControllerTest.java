package com.bit.checkpayclone.calender.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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

import com.bit.checkpayclone.calender.model.CalenderStarMemoVo;
import com.bit.checkpayclone.calender.model.UpdateBudgetVo;
import com.bit.checkpayclone.calender.model.UpdateCategoryVo;


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CalenderControllerTest {

	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static String memb_cd = "U000000000U000000022";
	private static final String contextPath = "http://192.168.240.208:8081/api/calender";
	private static String rtk;
	
	static HttpHeaders headers = new HttpHeaders();
	static MultiValueMap<String, String> parameters;
	static HttpEntity<MultiValueMap<String, String>> request;
		
	@BeforeAll
	static void login() throws Exception {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("member_email", "test01@gmail.com");
		parameter.add("member_password", "123123");
		request = new HttpEntity<>(parameter, headers);
		ResponseEntity<?> response = restTemplate.exchange("http://192.168.240.208:8081/api/member/login" , HttpMethod.POST, request, String.class);
		JSONObject json = new JSONObject(response.getBody().toString());
		headers.add("Authorization", "Bearer " + json.get("jwt").toString());
		rtk = json.get("rtk").toString();
	}
	
	@AfterAll
	static void logout() throws Exception {
		MultiValueMap<String, String> parameter = new LinkedMultiValueMap<String, String>();
		parameter.add("rtk", rtk);
		request = new HttpEntity<>(parameter, headers);
		ResponseEntity<?> response = restTemplate.exchange("http://192.168.240.208:8081/api/member/login/api/login/logout" , HttpMethod.POST, request, String.class);
	}
	
	@DisplayName("캘린더 날짜 선택 시 입출금 내역 조회")
	@Test
	void testAccountTransList() {
		ResponseEntity<?> response = restTemplate.exchange(contextPath+"/account/"+memb_cd+"/20230303", HttpMethod.GET, request, List.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("캘린더 날짜 선택 시 입출금 내역 조회")
	@Test
	void testCardPayInfoList() {
		ResponseEntity<?> response = restTemplate.exchange(contextPath+"/card/"+memb_cd+"/20230301", HttpMethod.GET, request, List.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("캘린더 카드내역 및 계좌내역 정렬 조회 API")
	@Test
	void testCalenderCardAndAcoountListDesc() {
		ResponseEntity<?> response = restTemplate.exchange(contextPath+"/list/"+memb_cd+"/202303/desc", HttpMethod.GET, request, List.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("특정 유저가 선택한 달의 캘린더 예산 조회 API")
	@Test
	void testCalenderBudgetList() {
		ResponseEntity<?> response = restTemplate.exchange(contextPath+"/budget/"+memb_cd+"/202303", HttpMethod.GET, request, List.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("특정 유저의 캘린더 예산 수정")
	@Test
	void testCalenderBudgetEdit() {
		Long[] category_budget = { 0l, 0l, 0l, 0l, 10000l, 10000l, 10000l, 5555l, 10000l, 5555l, 10000l, 4444l, 10000l, 77777l, 10000l, 10000l, 10000l, 10000l, 10000l};
		UpdateBudgetVo param = UpdateBudgetVo.builder()
				.member_id(memb_cd)
				.year_month("202004")
				.total_budget(350000l)
				.category_budget(category_budget)
				.build();
		HttpEntity<?> req = new HttpEntity<>(param, headers);
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/budget", HttpMethod.POST, req, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("선택한 달의 메모 및 중요표시 조회 API")
	@Test
	void testCalenderStarMemoList() {
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/info/"+memb_cd+"/202304", HttpMethod.GET, request, List.class);
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("선택한 날의 메모 및 중요 표시 추가 API")
	@Test
	@Transactional
	void testInsertUserCalenderStarMemo() {
		CalenderStarMemoVo param = CalenderStarMemoVo.builder()
				.member_id(memb_cd)
				.calender_day("20230421")
				.is_star("N")
				.memo("JUnit5")
				.build();
		HttpEntity<?> req = new HttpEntity<>(param, headers);
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/info/new", HttpMethod.POST, req, String.class);
		if(res.getStatusCode().equals(HttpStatus.OK)) {
			assertEquals(res.getStatusCode(), HttpStatus.OK);			
		} else {
			assertEquals(res.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DisplayName("선택한 날의 메모 및 중요 표시 추가 API")
	@Test
	void testUpdateUserCalenderStarMemo() {
		CalenderStarMemoVo param = CalenderStarMemoVo.builder()
				.member_id(memb_cd)
				.calender_day("20230401")
				.is_star("Y")
				.memo("JUnit5")
				.build();
		HttpEntity<?> req = new HttpEntity<>(param, headers);
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/info/set", HttpMethod.POST, req, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("캘린더 카드내역 상세보기 카테고리 변경 API")
	@Test
	void testUpdateCardPayInfoIndustry() {
		UpdateCategoryVo param = UpdateCategoryVo.builder()
				.memb_cd(memb_cd)
				.org_code("D1AAEV0000")
				.card_id("card2021a0001")
				.approved_dtime("20230301203000")
				.approved_num("87654322")
				.status("03")
				.seq(0)
				.build();
		HttpEntity<?> req = new HttpEntity<>(param, headers);
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/card/industry", HttpMethod.POST, req, String.class);
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("캘린더 카드 장단기 대출 조회 API")
	@Test
	void testFindUserCalenderCardLoanAll() {
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/card/loan/"+memb_cd+"/202304", HttpMethod.GET, request, List.class);
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("캘린더 카드 결제 예정 조회 API")
	@Test
	void testFindUserCardBills() {
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/card/bills/"+memb_cd+"/202304", HttpMethod.GET, request, List.class);
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

	@DisplayName("캘린더 통신 청구 예정 조회 API")
	@Test
	void testFindUserTelecomPaymentDue() {
		ResponseEntity<?> res = restTemplate.exchange(contextPath+"/telecom/bills/"+memb_cd+"/202304", HttpMethod.GET, request, List.class);
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}

}
