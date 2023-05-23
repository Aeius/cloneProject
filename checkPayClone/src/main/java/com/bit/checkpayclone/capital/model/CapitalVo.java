package com.bit.checkpayclone.capital.model;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CapitalVo {
	
	private String memb_cd,account_num,org_name,currency_code,prod_name,repay_method,org_code,is_minus;
	private double last_offered_rate;
	private long balance_amt,loan_principal;
	private String next_repay_date,issue_date,exp_date,repay_date;
}
