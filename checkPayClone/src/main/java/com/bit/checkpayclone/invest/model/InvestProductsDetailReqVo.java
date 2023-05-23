package com.bit.checkpayclone.invest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestProductsDetailReqVo {
	private String memb_cd, account_num, org_code, prod_type;
	private int seq;
	
	@Builder
	public InvestProductsDetailReqVo(String memb_cd, String account_num,String org_code, String prod_type, int seq) {
		this.memb_cd = memb_cd;
		this.account_num = account_num;
		this.org_code = org_code;
		this.prod_type = prod_type;
		this.seq = seq;
	}
	
}
