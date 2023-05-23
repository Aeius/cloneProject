package com.bit.checkpayclone.bank.model;


import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.checkpayclone.capital.model.CapitalVo;

@Mapper
public interface BankMapper {
	// 계좌정보 조회
	List<BankMemberAccountVo> findMemberAccount(String id);
	// 입출금 거래내역 조회
	List<BankDepositTransVo> findDepositTrans(String memb_cd, String org_code, String account_num, Date start_date, Date end_date, String trans_type, String sort, int offset);
	// 투자 상품 조회
	BankInvestDetailVo findInvestDetail(String memb_cd, String account_num);
	// 투자 상품 거래내역 조회
	List<BankInvestTransVo> findInvestTrans(String memb_cd, String org_code, String account_num, Date start_date, Date end_date, String sort, int offset);
	// 은행 대출 조회
	List<CapitalVo> selectUserBankCapitalDetail(String id, String org_code,String account_num);
	// 은행 대출 거래내역 조회
	List<BankLoanTransVo> findLoanTrans(String memb_cd, String org_code, String account_num, Date start_date, Date end_date, String sort, int offset);
	
}
