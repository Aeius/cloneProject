package com.bit.checkpayclone.bank.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankInvestTransVo {
	private String currency_code, trans_type;
	private Double base_amt, balance_amt,trans_amt;
	private Timestamp trans_dtime;
	private long trans_fund_num;
}


