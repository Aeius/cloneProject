package com.bit.checkpayclone.irp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.irp.model.IrpAccountDetailVo;
import com.bit.checkpayclone.irp.model.IrpAccountTransVo;
import com.bit.checkpayclone.irp.model.IrpAccountVo;
import com.bit.checkpayclone.irp.model.IrpMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IrpService {
	final SqlSession sqlSession;
	
	public List<IrpAccountVo> findUserIrpAccountList(String memb_cd, String org){
		return sqlSession.getMapper(IrpMapper.class).findUserIrpAccountList(memb_cd, org);
	};
	
	public IrpAccountDetailVo findUserIrpAccountDetail(String memb_cd, String org_code, String account_num){
		return sqlSession.getMapper(IrpMapper.class).findUserIrpAccountDetail(memb_cd, org_code, account_num);
	}

	public List<IrpAccountTransVo> findUserIrpAccountTrans(String memb_cd, String account_num, String start_day, String end_day, String sort, int offset){
		return sqlSession.getMapper(IrpMapper.class).findUserIrpAccountTrans(memb_cd, account_num, start_day, end_day, sort, offset);
	}

	public Object findUserIrpAccountProducts(String memb_cd, String org_code, String account_num) {
		return sqlSession.getMapper(IrpMapper.class).findUserIrpAccountProducts(memb_cd, org_code, account_num);
	}}
