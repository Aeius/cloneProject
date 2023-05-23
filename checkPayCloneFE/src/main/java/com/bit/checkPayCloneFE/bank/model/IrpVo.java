package com.bit.checkPayCloneFE.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class IrpVo {
	private String org_code,prod_name, account_num;
	private Double eval_amt;
}
