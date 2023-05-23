package com.bit.checkpayclone.payinfo.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.paybills.model.PayBillsVo;
import com.bit.checkpayclone.payinfo.model.PayInfoMapper;
import com.bit.checkpayclone.payinfo.model.PayInfoVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PayInfoService {
	
	final SqlSession sqlSession;
	
	// 특정 멤버 월별 카드결제 쓴 총 금액 및 결제 정보 (전체, 최신순) 디폴트
	public List<PayInfoVo> payInfoSelectDesc(String member_id, String card_id, String year_month, int limit) {
		return sqlSession.getMapper(PayInfoMapper.class).findPayInfoAll(member_id, card_id, year_month, limit);
	}
	
	// 특정 멤버 월별 카드결제 쓴 총 금액 및 결제 정보 (결제방법, 분류)
	public List<PayInfoVo> payInfoSelectInstallments(String member_id, String card_id, int installments, String year_month, int limit) {
		return sqlSession.getMapper(PayInfoMapper.class).findPayInfoInstallments(member_id, card_id, installments, year_month, limit);
	}
	
	// card_payinfo 필터 날자 범위 총 금액
	public List<PayInfoVo> payInfoFilterAmt(String member_id, String card_id, String startDate, String endDate) {
		return sqlSession.getMapper(PayInfoMapper.class).findPayFilterAmt(member_id, card_id, startDate, endDate);
	}
	
	// card_payinfo 날짜별 필터 처리 (전체, 최신순,과거순)
	public List<PayInfoVo> payInfoFilterAll(String member_id, String card_id, String startDate, String endDate, String sort, String keyword, int limit) {
		if(keyword.equals("@none")) {keyword = "";};
		return sqlSession.getMapper(PayInfoMapper.class).findPayFilterAll(member_id, card_id, startDate, endDate, sort, keyword, limit);
	}
	
	// card_payinfo 날짜별 필터 처리 (결제방법, 최신순,과거순)
	public List<PayInfoVo> payInfoFilterInstallAll(String member_id, String card_id, int installments, String startDate, String endDate, String sort, String keyword, int limit) {
		if(keyword.equals("@none")) {keyword = "";};
		return sqlSession.getMapper(PayInfoMapper.class).findPayFilterInstall(member_id, card_id, installments, startDate, endDate, sort, keyword, limit);
	}
	
	// card_payinfo 날짜별 필터 처리 (결제 상태, 최신순,과거순)
	public List<PayInfoVo> payInfoFilterStatus(String member_id, String card_id, String state, String startDate, String endDate, String sort, String keyword, int limit) {
		if(keyword.equals("@none")) {keyword = "";};
		return sqlSession.getMapper(PayInfoMapper.class).findPayFilterStatus(member_id, card_id, state, startDate, endDate, sort, keyword, limit);
	}
	
	// card_payinfo 날짜별 필터 처리 (결제 상태, 최신순, 과거순 결제방법) 
	public List<PayInfoVo> payInfoFilterInstallStatus(String member_id, String card_id, String state, int installments, String startDate, String endDate, String sort, String keyword, int limit) {
		if(keyword.equals("@none")) {keyword = "";};
		return sqlSession.getMapper(PayInfoMapper.class).findPayFilterInstallStatus(member_id, card_id, installments, state, startDate, endDate, sort, keyword, limit);
	}
	
}
