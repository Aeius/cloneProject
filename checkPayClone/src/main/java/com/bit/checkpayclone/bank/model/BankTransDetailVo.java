package com.bit.checkpayclone.bank.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankTransDetailVo {
	private String trans_memo,org_name, prod_name;
	private String account_num;
	private String trans_type;
	private double trans_amt;
	private double balance_amt;
	private Timestamp trans_dtime;
}
