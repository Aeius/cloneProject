package com.bit.checkpayclone.asset.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class EditinfoVo {
	private String memb_cd;
	private Object catg_cd;
	
	public EditinfoVo(String memb_cd, Object catg_cd) {
		this.memb_cd = memb_cd;
		this.catg_cd = catg_cd;
	}
}