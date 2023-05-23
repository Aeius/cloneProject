package com.bit.checkpayclone.card.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardLoneLongVo {
	String memb_cd,org_code;
	double int_rate;
	long loan_amt,balance_amt,int_amt;
	String loan_name,loan_type,repay_method,reg_dttm,exp_date;
}
