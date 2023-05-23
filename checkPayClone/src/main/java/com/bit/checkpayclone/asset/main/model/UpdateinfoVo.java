package com.bit.checkpayclone.asset.main.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateinfoVo {
	private String member_id;
	private String[] edit_list;
	private String[] hide_list;
	
	@Builder
	public UpdateinfoVo(String member_id, String[] edit_list, String[] hide_list) {
		super();
		this.member_id = member_id;
		this.edit_list = edit_list;
		this.hide_list = hide_list;
	}
}
