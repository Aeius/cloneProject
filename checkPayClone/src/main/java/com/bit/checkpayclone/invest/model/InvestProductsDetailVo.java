package com.bit.checkpayclone.invest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestProductsDetailVo {
	private double eval_amt, holding_num, purchase_amt;
	private String currency_code, account_name, prod_type, account_num, org_code;
}
