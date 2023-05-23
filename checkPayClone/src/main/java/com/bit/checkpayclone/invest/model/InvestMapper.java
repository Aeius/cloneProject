package com.bit.checkpayclone.invest.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InvestMapper {

	List<InvestAccountVo> findUserInvestAccountList(String memb_cd);
	
	List<InvestProductsVo> findUserInvestProductsList(String memb_cd);
	
	List<InvestProductsDetailListVo> findUserInvestProductDetailList(String memb_cd, String prod_type);
	
	List<InvestProductsDetailVo> findUserInvestProductDetail(String memb_cd, String account_num, String org_code, String prod_type, int seq);

	List<InvestProductsDetailListVo> findUserInvestStockList(String memb_cd);
	
	InvestAccountDetailVo findUserInvestAccountDetail(String memb_cd, String account_num, String org_code);
	
	List<InvestAccountProductsListVo> findUserInvestAccountProductsList(String memb_cd, String account_num, String org_code);
	
	List<InvestAccountTransVo> findUserInvestAccountTransList(String memb_cd, String account_num, String org_code, String start_day, String end_day, String sort);
}
