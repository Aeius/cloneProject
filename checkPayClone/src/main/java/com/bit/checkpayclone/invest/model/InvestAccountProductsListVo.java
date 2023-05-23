package com.bit.checkpayclone.invest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountProductsListVo {
	private String prod_name, org_name, account_num, currency_code;
	private double holding_num, eval_amt, purchase_amt;
	private int seq;
	private double withholdings_amt;
	private String encrypt_an, prod_type;
}
