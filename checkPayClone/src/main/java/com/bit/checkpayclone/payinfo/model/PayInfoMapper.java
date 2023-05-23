package com.bit.checkpayclone.payinfo.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayInfoMapper {

	// 특정 멤버 월별 카드결제 쓴 총 금액 및 결제 정보 (전체, 최신순) 디폴트
	List<PayInfoVo> findPayInfoAll(String member_id, String card_id, String year_month, int limit);
	
	// 특정 멤버 월별 카드결제 쓴 총 금액 및 결제 정보 (결제 방법)
	List<PayInfoVo> findPayInfoInstallments(String member_id, String card_id, int installments, String year_month, int limit);
	
	// card_payinfo 필터 날자 범위 총 금액
	List<PayInfoVo> findPayFilterAmt(String member_id, String card_id, String startDate, String endDate);
	
	// card_payinfo 날짜별 필터 처리 (전체) 최신순, 과거순
	List<PayInfoVo> findPayFilterAll(String member_id, String card_id, String startDate, String endDate, String sort, String keyword, int limit);
	
	// card_payinfo 날짜별 필터 처리 (결제 방법) 최신순, 과거순
	List<PayInfoVo> findPayFilterInstall(String member_id, String card_id, int installments, String startDate, String endDate, String sort, String keyword, int limit);
	
	// card_payinfo 날짜별 필터 처리 (결제 상태) 최신순, 과거순
	List<PayInfoVo> findPayFilterStatus(String member_id, String card_id, String state, String startDate, String endDate, String sort, String keyword, int limit);
	
	// card_payinfo 날짜별 필터 처리 (결제 상태, 결제 방법) 최신순, 과거순
	List<PayInfoVo> findPayFilterInstallStatus(String member_id, String card_id, int installments, String state, String startDate, String endDate, String sort, String keyword, int limit);
	
}
