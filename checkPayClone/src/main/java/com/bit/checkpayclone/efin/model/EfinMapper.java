package com.bit.checkpayclone.efin.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EfinMapper {
	/**권면관련2 2023.05.02 추가 
	 *  선불 페이 권면 결제정보 리스트 조회
	 *  선불 페이 권면 결제정보 상세 조회
	 * */
	List<EfinVo> findPrepaidTranList(EfinVo bean);
	EfinVo findPrepaidTranDetailOne(EfinVo bean);	
	
	/**권면관련
	 *  선불 페이 조회 리스트 페이지
	 * 2023.05.01
	 * 선불 페이 기관 해당 권면 종합 정보 조회
	 * 2023.04.27 추가
	 *  선불 회원의 기관 해당 권면 정보 조회  
	 *  선불 회원의 권면 잔액 정보 조회
	 * */
	List<EfinVo> findPrepaidList(String id);
	
	EfinVo findPrepaidInfoOne(EfinVo bean);
	
	EfinVo findPrepaidListOne(EfinVo bean);
	EfinVo findPrepaidBalanceOne(EfinVo bean);
	
	/**계정관련
	 * 기관에 해당하는 계정 조회
	 * 
	 * 2023.05.02 추가
	 * 계정 거래내역 조회
	 * 계정 거래내역 상세정보 조회
	 * 계정 종합정보 조회
	 * */
	List<EfinVo> findAccountIdByOrgName(String id,String orgname);
	
	List<EfinVo> findPaidTranList(EfinVo bean);
	EfinVo findPaidTranDetailOne(EfinVo bean);
	EfinVo findPaidInfoOne(EfinVo bean);
	List<EfinVo> findPaidMethod(EfinVo bean);
}
