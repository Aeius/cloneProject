package com.bit.checkpayclone.bank.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.bank.model.BankDepositTransVo;
import com.bit.checkpayclone.bank.model.BankInvestDetailVo;
import com.bit.checkpayclone.bank.model.BankInvestTransVo;
import com.bit.checkpayclone.bank.model.BankLoanTransVo;
import com.bit.checkpayclone.bank.model.BankMapper;
import com.bit.checkpayclone.bank.model.BankMemberAccountVo;
import com.bit.checkpayclone.capital.model.CapitalMapper;
import com.bit.checkpayclone.capital.model.CapitalVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankService {
	final SqlSession sqlSession;

	// 사용자에 대한 계좌 정보 조회 서비스
	public List<BankMemberAccountVo> memberAccount(String id) {
		return sqlSession.getMapper(BankMapper.class).findMemberAccount(id);
	}
	// 사용자에 대한 거래내역 조회 서비스
	public List<BankDepositTransVo> BankDepositTransSelect(
		String memb_cd, String org_code, String account_num, Date start_date, Date end_date, String trans_type, String sort, int offset){
		return sqlSession.getMapper(BankMapper.class).findDepositTrans(memb_cd, org_code, account_num,  start_date, end_date, trans_type, sort, offset);
	}
	// 펀드, 신탁 상세 조회 서비스
	public BankInvestDetailVo investDetail(String memb_cd, String account_num) {
		return sqlSession.getMapper(BankMapper.class).findInvestDetail(memb_cd, account_num);
	}
	// 펀드, 신탁 거래내역 조회 서비스
	public List<BankInvestTransVo> BankInvestTransSelect(
			String memb_cd, String org_code, String account_num, Date start_date, Date end_date, String sort, int offset){
		return sqlSession.getMapper(BankMapper.class).findInvestTrans(memb_cd, org_code, account_num,  start_date, end_date, sort, offset);
	}
	//철진
	public List<CapitalVo> selectUserBankCapitalDetail(String id,String org_code,String account_num){
		return sqlSession.getMapper(BankMapper.class).selectUserBankCapitalDetail(id, org_code, account_num);
	}
	// 은행 대출 거래내역 조회 서비스
	public List<BankLoanTransVo> BankLoanTransSelect(
			String memb_cd, String org_code, String account_num, Date start_date, Date end_date, String sort, int offset){
		return sqlSession.getMapper(BankMapper.class).findLoanTrans(memb_cd, org_code, account_num,  start_date, end_date, sort, offset);
	}
}
