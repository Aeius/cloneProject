package com.bit.checkpayclone.irp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IrpAccountVo {
	private String org_code, prod_name;
	private double eval_amt;
	private String account_num;
}
