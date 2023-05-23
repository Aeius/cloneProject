package com.bit.checkpayclone.calender.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCategoryVo {
	private String catg_cd, memb_cd, org_code, card_id, approved_dtime, approved_num, status;
	private Number seq;
	
	
	@Builder
	public UpdateCategoryVo(String catg_cd, String memb_cd, String org_code, String card_id, String approved_dtime,
			String approved_num, String status, Number seq) {
		this.catg_cd = catg_cd;
		this.memb_cd = memb_cd;
		this.org_code = org_code;
		this.card_id = card_id;
		this.approved_dtime = approved_dtime;
		this.approved_num = approved_num;
		this.status = status;
		this.seq = seq;
	}
	
}
