package com.bit.checkpayclone.invest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountVo {
	private String org_code, account_num, account_name;
	private double eval_amt, withholdings_amt;
	private String currency_code;
}
