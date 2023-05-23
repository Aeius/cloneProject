package com.bit.checkpayclone.irp.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IrpMapper {
	List<IrpAccountVo> findUserIrpAccountList(String memb_cd, String org);
	
	IrpAccountDetailVo findUserIrpAccountDetail(String memb_cd, String org_code, String account_num);

	List<IrpAccountTransVo> findUserIrpAccountTrans(String memb_cd, String account_num, String start_day, String end_day, String sort, int offset);

	List<IrpAccountProductsVo> findUserIrpAccountProducts(String memb_cd, String org_code, String account_num);}
