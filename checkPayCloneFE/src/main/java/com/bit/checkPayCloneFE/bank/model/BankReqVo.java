package com.bit.checkPayCloneFE.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BankReqVo {
	private String org_code, account_num;
}
