package com.bit.checkpayclone.admin.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.admin.member.service.AdminMemberService;

import lombok.RequiredArgsConstructor;

/**
 * 통합 어드민의 회원 관리와 관련된 controller
 * 요청과 응답 처리
 * 
 * @author wonhak
 *
 */

@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
@RestController
public class AdminMemberController {
	final AdminMemberService adminMemberService;
	
	/**
	 * 회원 목록
	 * 
	 * @param currentPageNum : 현재 페이지 번호
	 * @param countDataInPage : 한 페이지 내에 보일 데이터의 수
	 * @param countInPageGroup : 한 그룹 내에 묶이는 페이지의 수
	 * @param searchColumn : 검색할 대상
	 * @param searchValue : 검색 키워드
	 * @param beginDate : 조회 시작 날짜
	 * @param endDate : 조회 종료 날짜
	 * @return Map<String, Object> 
	 */
	@GetMapping("/")
	public Map<String, Object> memberList(
			String currentPageNum, 
			String countDataInPage, 
			String countInPageGroup,
			String searchColumn, 
			String searchValue,
			String beginDate,
			String endDate)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		/**
		 * 회원들 조회
		 */
		result.put("data", adminMemberService.getMember(
				currentPageNum,
				countDataInPage,
				countInPageGroup,
				searchColumn,
				searchValue,
				beginDate,
				endDate,
				null
		));
		
		/**
		 * 총 회원 수 조회
		 */
		result.put("totalDataCount", adminMemberService.getMemberCount(searchColumn, 
				searchValue,
				beginDate,
				endDate));
		
		return result;
	}
	
	@GetMapping("/{member_id}")
	public Map<String, Object> memberDetail(@PathVariable("member_id") String member_id){
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println(member_id);
		
		/**
		 * 회원 상세 조회
		 */
		result.put("data", adminMemberService.getMember(
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				member_id
				));
		
		return result;
	}
	
	/**
	 * 유저의 자산과 관련된 활동에 대한 기록을 등록
	 * 
	 * @param member_id : 회원 구분
	 * @param action_type : 자산 관련 활동 타입 (1 - 조회 // 2 - 인증 // 3 - 연동)
	 * @param api_url : 요청 url 
	 * @return Map<String,Object>
	 */
	@PostMapping("/log")
	public Map<String,Object> memberLog(String member_id, Integer action_type, String api_url){
		Map<String, Object> result = new HashMap<String, Object>();
		
		adminMemberService.addMemberLog(member_id, action_type, api_url);
		result.put("status", "success");
		
		return result;
	}
	
	/**
	 * 회원 한명에 대한 자산 목록 조회 api 추가
	 * 
	 * @param member_id : 회원 식별자
	 * @param currentPageNum : 현재 페이지 번호
	 * @param countDataInPage : 한 페이지 내에 보일 데이터의 수
	 * @param countInPageGroup : 한 그룹 내에 묶이는 페이지의 수
	 * @param searchColumn : 검색할 대상
	 * @param searchValue : 검색 키워드
	 * @return Map<String,Object>
	 */
	@GetMapping("/asset/{member_id}")
	public Map<String,Object> memberAsset(
			@PathVariable("member_id") String member_id,
			Integer currentPageNum, 
			Integer countDataInPage, 
			Integer countInPageGroup, 
			String searchColumn, 
			String searchValue
			){
		Map<String, Object> result = new HashMap<String, Object>();
		
		/**
		 * 자산들 조회
		 */
		result.put("data", adminMemberService.getMemberAsset(currentPageNum, countDataInPage, countInPageGroup, searchColumn, searchValue, member_id));
		
		/**
		 * 자산 총 갯수 조회
		 */
		result.put("totalDataCount", adminMemberService.getMemberAssetCount(member_id));
		
		return result;
	}
}
