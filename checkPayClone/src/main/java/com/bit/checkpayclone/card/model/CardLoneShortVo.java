package com.bit.checkpayclone.card.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardLoneShortVo {
	String memb_cd, org_code, loan_dtime, pay_due_date, reg_dttm,descrption;
	long loan_amt, balance_amt;
	double int_rate;
}
