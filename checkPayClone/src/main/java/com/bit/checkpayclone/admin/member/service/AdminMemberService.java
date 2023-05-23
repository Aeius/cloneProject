package com.bit.checkpayclone.admin.member.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.admin.member.model.AdminMemberAssetVo;
import com.bit.checkpayclone.admin.member.model.AdminMemberMapper;
import com.bit.checkpayclone.member.model.MemberVo;

import lombok.RequiredArgsConstructor;

/**
 * 통합 어드민의 회원 관리와 관련된 service
 * 데이터 가공 및 sql 실행
 * 
 * @author wonhak
 *
 */


@Service
@RequiredArgsConstructor
public class AdminMemberService {
	private final SqlSession sqlSession;

	/**
	 * 회원 목록
	 * 페이지네이션, 검색 기능을 위해서 여러 매개변수들을 받습니다.
	 * 검색할 대상, 검색 키워드가 존재한다면 필터링된 데이터들을 조회합니다.
	 * 
	 * @param v_currentPageNum : 현재 페이지 번호
	 * @param v_countDataInPage : 한 페이지 내에 보일 데이터의 수
	 * @param v_countInPageGroup : 한 그룹 내에 묶이는 페이지의 수
	 * @param v_searchColumn : 검색할 대상
	 * @param v_searchValue : 검색 키워드
	 * @param v_beginDate : 조회 시작 날짜
	 * @param v_endDate : 조회 종료 날짜
	 * @param member_id : 검색할 회원
	 * @return List<MemberVo>
	 */
	public List<MemberVo> getMember(
			String currentPageNum, 
			String countDataInPage, 
			String countInPageGroup, 
			String searchColumn, 
			String searchValue,
			String beginDate,
			String endDate,
			String member_id) {
		int v_currentPageNum = currentPageNum!=null&&!currentPageNum.equals("")?Integer.parseInt(currentPageNum):1;
		int v_countDataInPage = countDataInPage!=null&&!countDataInPage.equals("")?Integer.parseInt(countDataInPage):10;
		int v_countInPageGroup = countInPageGroup!=null&&!countInPageGroup.equals("")?Integer.parseInt(countInPageGroup):5;
		String v_searchColumn = searchColumn!=null&&!searchColumn.equals("")?searchColumn:null;
		String v_searchValue = searchValue!=null&&!searchValue.equals("")?searchValue:null;
		String v_beginDate = beginDate!=null&&!beginDate.equals("")?beginDate:null;
		String v_endDate = endDate!=null&&!endDate.equals("")?endDate:null;
		
		if(v_currentPageNum < 1) {
			v_currentPageNum = 1;
		}
		
		int startPage = (v_currentPageNum-1)*v_countDataInPage;
		
		return sqlSession.getMapper(AdminMemberMapper.class).getMember(startPage,v_countDataInPage,v_searchColumn,v_searchValue,v_beginDate,v_endDate,member_id);
	}
	
	/**
	 * 총 회원 수
	 * 검색 필터가 적용되었다면 검색된 총 회원 수만 조회
	 * 
	 * @param searchColumn : 검색할 대상
	 * @param searchValue : 검색 키워드
	 * @param beginDate : 조회 시작 날짜
	 * @param endDate : 조회 종료 날짜
	 * @return int
	 */
	public int getMemberCount(String searchColumn, String searchValue, String beginDate,String endDate) {
		return sqlSession.getMapper(AdminMemberMapper.class).getMemberCount(searchColumn,searchValue,beginDate,endDate);
	}
	
	/**
	 * 자산 관련 활동을 한 회원의 활동 기록 등록
	 * 
	 * @param member_id : 회원
	 * @param action_type : 자산 관련 활동 타입 (1 - 조회 // 2 - 인증 // 3 - 연동)
	 * @param api_url : 요청 api
	 */
	public void addMemberLog(String member_id, Integer action_type, String api_url) {
		sqlSession.getMapper(AdminMemberMapper.class).addMemberLog(member_id, action_type, api_url);
	}
	
	/**
	 * 회원이 소유한 자산 목록 조회
	 * 
	 * @param currentPageNum : 현재 페이지 번호
	 * @param countDataInPage : 한 페이지 내에 보일 데이터의 수
	 * @param countInPageGroup : 한 그룹 내에 묶이는 페이지의 수
	 * @param searchColumn : 검색할 대상
	 * @param searchValue : 검색 키워드
	 * @param member_id : 검색한 회원
	 * @return
	 */
	public List<AdminMemberAssetVo> getMemberAsset(
			Integer currentPageNum, 
			Integer countDataInPage, 
			Integer countInPageGroup, 
			String searchColumn, 
			String searchValue, 
			String member_id
			){
		
		int v_currentPageNum = currentPageNum!=null&&!currentPageNum.equals("")?currentPageNum:1;
		int v_countDataInPage = countDataInPage!=null&&!countDataInPage.equals("")?countDataInPage:10;
		int v_countInPageGroup = countInPageGroup!=null&&!countInPageGroup.equals("")?countInPageGroup:5;
		
		if(v_currentPageNum < 1) {
			v_currentPageNum = 1;
		}
		
		int startPage = (v_currentPageNum-1)*v_countDataInPage;
		
		return sqlSession.getMapper(AdminMemberMapper.class).getMemberAsset(startPage, countDataInPage, searchColumn, searchValue, member_id);
	}
	
	/**
	 * 회원이 소유한 자산의 총 갯수 조회
	 * 
	 * @param member_id : 검색한 회원
	 * @return int
	 */
	public int getMemberAssetCount(String member_id) {
		return sqlSession.getMapper(AdminMemberMapper.class).getMemberAssetCount(member_id);
	}
}
