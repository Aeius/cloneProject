package com.bit.checkpayclone.invest.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InvestAccountTransReqVo {
	private String memb_cd, account_num, org_code, start_day, end_day, sort;

	@Builder
	public InvestAccountTransReqVo(String memb_cd,String account_num,String org_code,String start_day,String end_day,String sort) {
		this.memb_cd = memb_cd;
		this.account_num = account_num;
		this.org_code = org_code;
		this.start_day = start_day;
		this.end_day = end_day;
		this.sort = sort;
	}
}
