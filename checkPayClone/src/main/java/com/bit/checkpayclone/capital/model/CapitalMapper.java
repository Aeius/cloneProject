package com.bit.checkpayclone.capital.model;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface CapitalMapper {
	List<CapitalVo> selectUserCapital(String id);
	List<CapitalVo> selectUserCapitalDetail(String id, String org_code,String account_num);
	List<CapitalTransVo> selectCapitalTransDetail (String id,String org_code,String account_num,Date start_date,Date end_date,String sort,int offset);
}
