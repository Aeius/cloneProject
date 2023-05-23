package com.bit.checkpayclone.card.model;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardLoanAllVo {
	private String org_name;
	private Date loan_dtime;
	private double loan_amt, balance_amt;
	private Date pay_due_date;
	private double int_rate;
	private String loan_name, loan_cnt, loan_type;
	private Date exp_date;
	private String repay_method;
	private double int_amt;
	private Timestamp reg_dttm;
	
}
