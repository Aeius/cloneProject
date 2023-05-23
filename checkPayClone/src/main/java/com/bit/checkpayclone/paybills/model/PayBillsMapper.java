package com.bit.checkpayclone.paybills.model;

import java.util.List;


public interface PayBillsMapper {

	// 청구 가능한 카드사 표출
	List<PayBillsVo> findBillsCard(String member_id, String org_code, String paid_date);
	
	// 청구 결제방법(일시불, 할부)구분
	List<PayBillsVo> findBillsCardInstall(String member_id, String org_code, String prod_type, String paid_date);
}
