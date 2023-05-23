package com.bit.checkpayclone.calender.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.bank.model.BankTransDetailVo;
import com.bit.checkpayclone.calender.model.CalenderBudgetVo;
import com.bit.checkpayclone.calender.model.CalenderCardAndAccountVo;
import com.bit.checkpayclone.calender.model.CalenderMapper;
import com.bit.checkpayclone.calender.model.CalenderStarMemoVo;
import com.bit.checkpayclone.card.model.CardBillsVo;
import com.bit.checkpayclone.card.model.CardLoanAllVo;
import com.bit.checkpayclone.payinfo.model.PayInfoVo;
import com.bit.checkpayclone.telecom.model.TelecomPaymentDueVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalenderService {

	final SqlSession sqlSession;

	public List<BankTransDetailVo> getUserAccountTransSelectDate(String member_id, String select_date) {
		return sqlSession.getMapper(CalenderMapper.class).findUserAccountTransSelectDate(member_id, select_date);
	}

	public List<PayInfoVo> getUserCardPayInfoSelectDate(String member_id, String select_date) {
		return sqlSession.getMapper(CalenderMapper.class).findUserCardPayInfoSelectDate(member_id, select_date);
	}

	public boolean checkExistsCalenderBudget(String member_id, String year_month) {
		return sqlSession.getMapper(CalenderMapper.class).existsCalenderBudget(member_id, year_month);
	}

	public int insertUserCalenderBudget(String member_id, String year_month, String[] catg_list) {
		return sqlSession.getMapper(CalenderMapper.class).insertUserCalenderBudget(member_id, year_month, catg_list);
	}

	public List<CalenderBudgetVo> getUserCalenderBudget(String member_id, String year_month) {
		return sqlSession.getMapper(CalenderMapper.class).findCalenderBudget(member_id, year_month);
	}

	public void setUserCalenderBudget(long total_budget, String member_id, String year_month,Map<String, Long> budget_map) {
		sqlSession.getMapper(CalenderMapper.class).updateCalenderBudget(total_budget, member_id, year_month,budget_map);
	}

	public List<CalenderCardAndAccountVo> getUserCardAndAccountSelectMonth(String member_id, String year_month,String sort) {
		return sqlSession.getMapper(CalenderMapper.class).findUserCardAndAccountSelectMonth(member_id, year_month,sort);
	}

	public List<CalenderStarMemoVo> getUserCalenderStarMemo(String member_id, String year_month) {
		return sqlSession.getMapper(CalenderMapper.class).findUserCalenderStarMemo(member_id, year_month);
	}

	public void insertUserCalenderStarMemo(String member_id, String calender_day, String is_star, String memo,String reg_dttm) {
		sqlSession.getMapper(CalenderMapper.class).insertUserCalenderStarMemo(member_id, calender_day, is_star, memo,reg_dttm);
	};

	public void setUserCalenderStarMemo(String is_star, String memo, String member_id, String calender_day,String upd_dttm) {
		sqlSession.getMapper(CalenderMapper.class).updateUserCalenderStarMemo(is_star, memo, member_id, calender_day,upd_dttm);
	}

	public void setCardPayInfoIndustry(String memb_cd, String org_code, String card_id, String approved_dtime,String approved_num, String status, Number seq, String catg_cd) {
		sqlSession.getMapper(CalenderMapper.class).updateCardPayInfoIndustry(memb_cd, org_code, card_id,approved_dtime, approved_num, status, seq, catg_cd);
	}

	public void deleteCalenderBudgetOldData() {
		sqlSession.getMapper(CalenderMapper.class).deleteCalenderBudgetOldData();
	}

	public void deleteCalenderBudgetDetOldData() {
		sqlSession.getMapper(CalenderMapper.class).deleteCalenderBudgetDetOldData();
	}
	
	public void deleteCalenderStarMemoOldData() {
		sqlSession.getMapper(CalenderMapper.class).deleteCalenderStarMemoOldData();
	}
	
	public List<CardLoanAllVo> findUserCalenderCardLoanAll(String memb_cd, String year_month){
		return sqlSession.getMapper(CalenderMapper.class).findUserCalenderCardLoanAll(memb_cd, year_month);
	}
	
	public List<CardBillsVo> findUserCardBills(String memb_cd, String year_month){
		return sqlSession.getMapper(CalenderMapper.class).findUserCardBills(memb_cd, year_month);
	}
	
	public List<TelecomPaymentDueVo> findUserTelecomPaymentDue(String memb_cd, String year_month){
		return sqlSession.getMapper(CalenderMapper.class).findUserTelecomPaymentDue(memb_cd, year_month);
	}
}
