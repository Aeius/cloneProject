package com.bit.checkpayclone.telecom.model;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TelecomMapper {
	
	// 메인 3개월 치 청구 금액 조회
	List<TelecomVo> findUserBill(String id);
	
	// 당월 청구금액 및 3개월치 청구액 조회
	List<TelecomBillVo> findBillList(String id);
	
	// Detail 페이지 상단
	List<TelecomDetailtopVo> findDetailCount(String id);
	
	// Detail 페이지 하단(청구내역)
	List<TelecomDetailBillVo> findDetailFirst(String id, Date start_date, Date end_date, String sort, int offset);

	// Detail 페이지 하단(소액결제)
	List<MicropaymentVo> findMicropayment(String id, Date start_date, Date end_date, String sort, int offset);

}
