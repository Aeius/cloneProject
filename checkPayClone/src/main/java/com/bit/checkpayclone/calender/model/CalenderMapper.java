package com.bit.checkpayclone.calender.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.bit.checkpayclone.bank.model.BankTransDetailVo;
import com.bit.checkpayclone.card.model.CardBillsVo;
import com.bit.checkpayclone.card.model.CardLoanAllVo;
import com.bit.checkpayclone.payinfo.model.PayInfoVo;
import com.bit.checkpayclone.telecom.model.TelecomPaymentDueVo;

@Mapper
public interface CalenderMapper {

	List<BankTransDetailVo> findUserAccountTransSelectDate(String member_id, String select_date);

	List<PayInfoVo> findUserCardPayInfoSelectDate(String member_id, String select_date);

	boolean existsCalenderBudget(String member_id, String year_month);

	int insertUserCalenderBudget(String member_id, String year_month, String[] catg_list);

	List<CalenderBudgetVo> findCalenderBudget(String member_id, String year_month);

	void updateCalenderBudget(long total_budget, String member_id, String year_month, Map<String, Long> budget_map);

	List<CalenderCardAndAccountVo> findUserCardAndAccountSelectMonth(String member_id, String year_month, String sort);

	List<CalenderStarMemoVo> findUserCalenderStarMemo(String member_id, String year_month);

	void insertUserCalenderStarMemo(String member_id, String calender_day, String is_star, String memo,String reg_dttm);

	void updateUserCalenderStarMemo(String is_star, String memo, String member_id, String calender_day,String upd_dttm);

	void updateCardPayInfoIndustry(String memb_cd, String org_code, String card_id, String approved_dtime,String approved_num, String status, Number seq, String catg_cd);

	void deleteCalenderBudgetOldData();

	void deleteCalenderBudgetDetOldData();
	
	void deleteCalenderStarMemoOldData();
	
	List<CardLoanAllVo> findUserCalenderCardLoanAll(String memb_cd, String year_month);
	
	List<CardBillsVo> findUserCardBills(String memb_cd, String year_month);
	
	List<TelecomPaymentDueVo> findUserTelecomPaymentDue(String memb_cd, String year_month);
}
