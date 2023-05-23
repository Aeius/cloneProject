package com.bit.checkPayCloneFE.bank.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BankVo {
	// 이미지를 불러보기 위한 org_code, alt에 값 넣기 위한 org_name
	// 상품명 prod_name, 입출금 예적금인지 확인하는 account_type
	// detail로 값을 넘기기 위한 
	private String currency_code,org_code,org_name,prod_name,account_type,account_num, is_consent;
	// 계좌 금액 balance_amt
	private double balance_amt, eval_amt;
	private double last_offered_rate;
	
	@Builder
	public BankVo(String currency_code,String org_code, String org_name, String prod_name, String account_type, String account_num,
			String is_consent, double balance_amt, double last_offered_rate, double eval_amt) {
		super();
		this.currency_code=currency_code;
		this.org_code = org_code;
		this.org_name = org_name;
		this.prod_name = prod_name;
		this.account_type = account_type;
		this.account_num = account_num;
		this.is_consent = is_consent;
		this.balance_amt = balance_amt;
		this.last_offered_rate= last_offered_rate;
		this.eval_amt=eval_amt;
	}
	
	
}
