package com.bit.checkpayclone.calender.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.calender.model.CalenderStarMemoVo;
import com.bit.checkpayclone.calender.model.UpdateBudgetVo;
import com.bit.checkpayclone.calender.model.UpdateCategoryVo;
import com.bit.checkpayclone.calender.service.CalenderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CalenderController {

	final CalenderService calenderService;
	
	/*
	 * 캘린더 날짜 선택 시 입출금 내역 조회 API
	 * [GET] /api/calender/account/{member_id}/{select_date}
	 * 작성자 : 구본영
	 */
	@GetMapping("/api/calender/account/{member_id}/{select_date}")
	public ResponseEntity<?> accountTransList(@PathVariable("member_id") String member_id, @PathVariable("select_date") String select_date){
		return ResponseEntity.ok(calenderService.getUserAccountTransSelectDate(member_id, select_date));
	}

	/*
	 * 캘린더 날짜 선택 시 카드 내역 조회  API
	 * [GET] /api/calender/card/{member_id}/{select_date}
	 * 작성자 : 구본영
	 */
	@GetMapping("/api/calender/card/{member_id}/{select_date}")
	public ResponseEntity<?> cardPayInfoList(@PathVariable("member_id") String member_id, @PathVariable("select_date") String select_date){
		return ResponseEntity.ok(calenderService.getUserCardPayInfoSelectDate(member_id, select_date));
	}
	/*
	 * 캘린더 카드내역 및 계좌내역 내림차순 정렬 API
	 * [GET] /api/calender/list/{member_id}/{select_month}/desc
	 * 작성자 : 구본영
	 */
	@GetMapping("/api/calender/list/{member_id}/{year_month}/{sort}")
	public ResponseEntity<?> calenderCardAndAcoountListDesc(@PathVariable("member_id") String member_id, @PathVariable("year_month") String year_month, @PathVariable("sort") String sort){	
		return ResponseEntity.ok(calenderService.getUserCardAndAccountSelectMonth(member_id, year_month, sort));
	}
	
	/*
	 * 특정 유저가 선택한 달의 캘린더 예산 조회 API
	 * [GET] /api/calender/budget/{member_id}/{year_month}
	 * 작성자 : 구본영 & 박재현
	 */
	String[] catg_list = {"1000","1100","1200","2000","3000","3100","3200","4000","4100","5000","5100","5200","6000","7000","7100","8000","8100","8200","8300"};
	@GetMapping("/api/calender/budget/{member_id}/{year_month}")
	public ResponseEntity<?> calenderBudgetList(@PathVariable("member_id") String member_id, @PathVariable("year_month") String year_month){
		if(calenderService.checkExistsCalenderBudget(member_id, year_month)) {
			return ResponseEntity.ok(calenderService.getUserCalenderBudget(member_id, year_month));			
		} else {
			if(calenderService.insertUserCalenderBudget(member_id, year_month, catg_list)==18) {
				return ResponseEntity.ok(calenderService.getUserCalenderBudget(member_id, year_month));
			} else {
				return ResponseEntity.ok("{\"result\":\"관리자에게 문의 바랍니다.\"}");
			}
		}
	}
	/*
	 * 특정 유저의 캘린더 예산 수정 API
	 * [POST] /api/calender/budget
	 * 작성자 : 구본영 & 박재현
	 */
	@PostMapping("/api/calender/budget")
	public void calenderBudgetEdit(@RequestBody UpdateBudgetVo updateBudgetVo) {
		Map<String, Long> budget_map = new HashMap<String, Long>();
		for(int i = 0; i < catg_list.length; i++) {
			budget_map.put(catg_list[i], updateBudgetVo.getCategory_budget()[i]);
		}
		calenderService.setUserCalenderBudget(
					updateBudgetVo.getTotal_budget(),
					updateBudgetVo.getMember_id(),
					updateBudgetVo.getYear_month(),
					budget_map
				);	
	}
	/*
	 * 선택한 달의 메모 및 중요 표시 조회 API
	 * [GET] /api/calender/info/{member_id}/{select_month}
	 * 작성자 : 구본영 & 박재현
	 */
	@GetMapping("/api/calender/info/{member_id}/{year_month}")
	public ResponseEntity<?> calenderStarMemoList(@PathVariable("member_id") String member_id, @PathVariable("year_month") String year_month){
		return ResponseEntity.ok(calenderService.getUserCalenderStarMemo(member_id, year_month));
	}
	/*
	 * 선택한 날의 메모 및 중요 표시 추가 API
	 * [POST] /api/calender/info/new
	 * 작성자 : 구본영 & 박재현
	 */
	@PostMapping("/api/calender/info/new")
	public void insertUserCalenderStarMemo(@RequestBody CalenderStarMemoVo calenderStarMemoVo) {
		String reg_dttm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		calenderService.insertUserCalenderStarMemo(
				calenderStarMemoVo.getMember_id(),
				calenderStarMemoVo.getCalender_day(),
				calenderStarMemoVo.getIs_star(),
				calenderStarMemoVo.getMemo(),
				reg_dttm
			);
	}
	/*
	 * 선택한 날의 메모 및 중요 표시 수정 API
	 * [POST] /api/calender/info/set
	 * 작성자 : 구본영 & 박재현
	 */
	@PostMapping("/api/calender/info/set")
	public void updateUserCalenderStarMemo(@RequestBody CalenderStarMemoVo calenderStarMemoVo) {
		String upd_dttm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		calenderService.setUserCalenderStarMemo(
				calenderStarMemoVo.getIs_star(), 
				calenderStarMemoVo.getMemo(), 
				calenderStarMemoVo.getMember_id(), 
				calenderStarMemoVo.getCalender_day(),
				upd_dttm
			);
	}
	/*
	 * 캘린더 카드내역 상세보기 카테고리 변경 API
	 * [POST] /api/calender/card/industry
	 * 작성자 : 구본영 & 박재현
	 */
	@PostMapping("/api/calender/card/industry")
	public void updateCardPayInfoIndustry(@RequestBody UpdateCategoryVo updateCategoryVo) {
		calenderService.setCardPayInfoIndustry(
				updateCategoryVo.getMemb_cd(),
				updateCategoryVo.getOrg_code(),
				updateCategoryVo.getCard_id(),
				updateCategoryVo.getApproved_dtime(),
				updateCategoryVo.getApproved_num(),
				updateCategoryVo.getStatus(),
				updateCategoryVo.getSeq(),
				updateCategoryVo.getCatg_cd());
	}
	
	@GetMapping("/api/calender/card/loan/{memb_cd}/{year_month}")
	public ResponseEntity<?> findUserCalenderCardLoanAll(@PathVariable String memb_cd, @PathVariable String year_month){
		return ResponseEntity.ok(calenderService.findUserCalenderCardLoanAll(memb_cd, year_month));
	}
	
	@GetMapping("/api/calender/card/bills/{memb_cd}/{year_month}")
	public ResponseEntity<?> findUserCardBills(@PathVariable String memb_cd, @PathVariable String year_month){
		return ResponseEntity.ok(calenderService.findUserCardBills(memb_cd, year_month));
	}
	
	@GetMapping("/api/calender/telecom/bills/{memb_cd}/{year_month}")
	public ResponseEntity<?> findUserTelecomPaymentDue(@PathVariable String memb_cd, @PathVariable String year_month){
		return ResponseEntity.ok(calenderService.findUserTelecomPaymentDue(memb_cd, year_month));
	}
 }
