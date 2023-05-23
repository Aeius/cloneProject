package com.bit.checkpayclone.invest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestProductsDetailListVo {
	private String org_code, account_num, prod_name, prod_type, currency_code;
	private double holding_num, eval_amt, purchase_amt; 
	private int seq;
}
