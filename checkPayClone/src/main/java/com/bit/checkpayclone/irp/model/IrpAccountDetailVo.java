package com.bit.checkpayclone.irp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IrpAccountDetailVo {
	private String prod_name, org_name, account_num;
	private double eval_amt, accum_amt, employer_amt, employee_amt;
}
