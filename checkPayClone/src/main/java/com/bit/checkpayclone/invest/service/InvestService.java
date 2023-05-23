package com.bit.checkpayclone.invest.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.invest.model.InvestAccountDetailVo;
import com.bit.checkpayclone.invest.model.InvestAccountProductsListVo;
import com.bit.checkpayclone.invest.model.InvestAccountTransVo;
import com.bit.checkpayclone.invest.model.InvestAccountVo;
import com.bit.checkpayclone.invest.model.InvestMapper;
import com.bit.checkpayclone.invest.model.InvestProductsDetailListVo;
import com.bit.checkpayclone.invest.model.InvestProductsDetailVo;
import com.bit.checkpayclone.invest.model.InvestProductsVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestService {
	final SqlSession sqlSession;
	
	public List<InvestAccountVo> findUserInvestAccountList(String memb_cd){
		return sqlSession.getMapper(InvestMapper.class).findUserInvestAccountList(memb_cd);
	};
	
	public List<InvestProductsVo> findUserInvestProductsList(String memb_cd){
		return sqlSession.getMapper(InvestMapper.class).findUserInvestProductsList(memb_cd);
	};
	
	public List<InvestProductsDetailListVo> findUserInvestProductDetailList(String memb_cd, String prod_type){
		return sqlSession.getMapper(InvestMapper.class).findUserInvestProductDetailList(memb_cd, prod_type);
	};
	
	public List<InvestProductsDetailVo> findUserInvestProductDetail(String memb_cd, String account_num, String org_code, String prod_type, int seq){
		return sqlSession.getMapper(InvestMapper.class).findUserInvestProductDetail(memb_cd, account_num, org_code, prod_type, seq);
	}

	public List<InvestProductsDetailListVo> findUserInvestStockList(String memb_cd) {
		return sqlSession.getMapper(InvestMapper.class).findUserInvestStockList(memb_cd);
	};
	
	public InvestAccountDetailVo findUserInvestAccountDetail(String memb_cd, String account_num, String org_code) {
		return sqlSession.getMapper(InvestMapper.class).findUserInvestAccountDetail(memb_cd, account_num, org_code);
	};
	
	public List<InvestAccountProductsListVo> findUserInvestAccountProductsList(String memb_cd, String account_num, String org_code){
		return sqlSession.getMapper(InvestMapper.class).findUserInvestAccountProductsList(memb_cd, account_num, org_code);
	};
	
	public List<InvestAccountTransVo> findUserInvestAccountTransList(String memb_cd, String account_num, String org_code, String start_day, String end_day, String sort){
		return sqlSession.getMapper(InvestMapper.class).findUserInvestAccountTransList(memb_cd, account_num, org_code, start_day, end_day, sort);
	};
}
