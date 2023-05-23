package com.bit.checkpayclone.invest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountDetailReqVo {
	private String memb_cd, account_num, org_code;
	
	@Builder
	public InvestAccountDetailReqVo(String memb_cd, String account_num, String org_code) {
		this.memb_cd = memb_cd;
		this.account_num = account_num;
		this.org_code = org_code;
	}	
}
