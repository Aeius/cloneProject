package com.bit.checkpayclone.admin.member.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.checkpayclone.member.model.MemberVo;

@Mapper
public interface AdminMemberMapper {

	/**
	 * 회원 목록
	 * 페이지네이션 처리와 더불어 검색 필터가 적용된 데이터들을 조회합니다.
	 * 
	 * @param startPage : 데이터 조회 시작 기준이 되는 매개변수
	 * @param countDataInPage : 데이터 조회 범위 기준이 되는 매개변수
	 * @param searchColumn : 검색 대상
	 * @param searchValue : 검색 키워드
	 * @param endDate : 조회 시작 날짜 
	 * @param beginDate : 조회 종료 날짜
	 * @param member_id : 검색한 회원
	 * @return List<AdminMemberVo>
	 */
	List<MemberVo> getMember(int startPage, int countDataInPage, String searchColumn, String searchValue, String beginDate, String endDate, String member_id);
	
	/**
	 * 검색 필터가 적용된 총 회원 수
	 * 
	 * @param searchColumn : 검색 대상
	 * @param searchValue : 검색 키워드
	 * @param endDate : 조회 시작 날짜 
	 * @param beginDate : 조회 종료 날짜 
	 * @return int
	 */
	int getMemberCount(String searchColumn, String searchValue, String beginDate, String endDate);

	/**
	 * 회원이 자산과 관련되어 한 일을 기록. 주로 조회, 인증, 연동
	 * 
	 * @param member_id : 회원 식별자
	 * @param action_type : 1 - 조회 // 2 - 인증 // 3 - 연동
	 * @param api_url : 요청한 api 주소
	 */
	void addMemberLog(String member_id, Integer action_type, String api_url);
	
	/**
	 * 회원이 소유한 자산 목록 조회
	 * 
	 * @param startPage : 데이터 조회 시작 기준이 되는 매개변수
	 * @param countDataInPage : 데이터 조회 범위 기준이 되는 매개변수
	 * @param searchColumn : 검색 대상
	 * @param searchValue : 검색 키워드
	 * @param member_id : 검색한 회원
	 * @return List<AdminMemberAssetVo>
	 */
	List<AdminMemberAssetVo> getMemberAsset(Integer startPage, Integer countDataInPage, String searchColumn, String searchValue, String member_id);
	
	/**
	 * 회원이 소유한 자산 총 갯수 조회
	 * 
	 * @param member_id
	 * @return int
	 */
	int getMemberAssetCount(String member_id);
}
