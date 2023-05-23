package com.bit.checkpayclone.capital.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CapitalTransVo {
	String prod_name,org_name,account_num,currency_code,trans_type,int_type,int_start_date,int_end_date;
	Timestamp trans_dtime;
	long trans_amt,balance_amt,principal_amt,int_amt;
	double ret_int_amt,int_rate;
}
