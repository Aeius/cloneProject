package com.bit.checkpayclone.telecom.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.telecom.model.TelecomVo;
import com.bit.checkpayclone.telecom.model.TelecomBillVo;
import com.bit.checkpayclone.telecom.model.TelecomDetailtopVo;
import com.bit.checkpayclone.telecom.model.TelecomDetailBillVo;
import com.bit.checkpayclone.telecom.model.MicropaymentVo;
import com.bit.checkpayclone.telecom.model.TelecomMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelecomService {
	final SqlSession sqlSession;
	
	public List<TelecomVo> selectbill(String id) {
		return sqlSession.getMapper(TelecomMapper.class).findUserBill(id);
	}
	
	public List<TelecomBillVo> selectcurrentbill(String id) {
		return sqlSession.getMapper(TelecomMapper.class).findBillList(id);
	}
	
	public List<TelecomDetailtopVo> selectdetailtop(String id) {
		return sqlSession.getMapper(TelecomMapper.class).findDetailCount(id);
	}
	
	public List<TelecomDetailBillVo> selectbillDetail(String id, Date start_date, Date end_date, String sort, int offset) {
		return sqlSession.getMapper(TelecomMapper.class).findDetailFirst(id, start_date, end_date, sort, offset);
	}
	
	public List<MicropaymentVo> selectMicropaymentDetail(String id, Date start_date, Date end_date, String sort, int offset) {
		return sqlSession.getMapper(TelecomMapper.class).findMicropayment(id, start_date, end_date, sort, offset);
	}
	
}
