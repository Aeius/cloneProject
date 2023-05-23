package com.bit.checkpayclone.irp.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IrpAccountProductsVo {
	private String irp_name;
	private double eval_amt, inv_principal, fund_num, int_rate;
	private Timestamp open_date, exp_date;
}
