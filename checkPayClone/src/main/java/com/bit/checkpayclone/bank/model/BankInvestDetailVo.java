package com.bit.checkpayclone.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankInvestDetailVo {
	private double balance_amt, eval_amt ,inv_principal;
	private String paid_in_type , exp_date , prod_name, org_name;
}
