package com.bit.checkpayclone.paybills.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.paybills.model.PayBillsMapper;
import com.bit.checkpayclone.paybills.model.PayBillsVo;
import com.bit.checkpayclone.payinfo.model.PayInfoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayBillsService {
	
	final SqlSession sqlSession;
	
	// 청구 가능한 카드사 표출
	public List<PayBillsVo> payBillsCard(String member_id, String org_code, String paid_date) {
		return sqlSession.getMapper(PayBillsMapper.class).findBillsCard(member_id, org_code, paid_date);
	}
	
	// 청구 결제방법(일시불, 할부)구분
	public List<PayBillsVo> payBillsCardInstall(String member_id, String org_code, String prod_type, String paid_date) {
		return sqlSession.getMapper(PayBillsMapper.class).findBillsCardInstall(member_id, org_code,prod_type, paid_date);
	}
}
