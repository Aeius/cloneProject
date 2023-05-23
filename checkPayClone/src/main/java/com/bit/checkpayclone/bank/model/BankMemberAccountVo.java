package com.bit.checkpayclone.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankMemberAccountVo {
	// 이미지를 불러보기 위한 org_code, alt에 값 넣기 위한 org_name
	// 상품명 prod_name, 입출금 예적금인지 확인하는 account_type
	// detail로 값을 넘기기 위한 
	// 계좌번호를 넘기기 위한 account_num
	private String  currency_code,org_code,org_name,prod_name,account_type,account_num, is_consent, is_minus;
	// 계좌 금액 balance_amt
	private double balance_amt;
	private double last_offered_rate;
	
}
