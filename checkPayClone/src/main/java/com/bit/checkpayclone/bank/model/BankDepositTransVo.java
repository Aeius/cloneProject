package com.bit.checkpayclone.bank.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankDepositTransVo {
	private String currency_code, trans_type, trans_class, trans_memo;
	private Timestamp trans_dtime;
	private double trans_amt, balance_amt;
	
}
