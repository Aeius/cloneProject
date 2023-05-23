package com.bit.checkpayclone.calender.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CalenderStarMemoVo {
	private String member_id;
	private String calender_day;
	private String is_star;
	private String memo;
	
	
	@Builder
	public CalenderStarMemoVo(String member_id, String calender_day, String is_star, String memo) {
		this.member_id = member_id;
		this.calender_day = calender_day;
		this.is_star = is_star;
		this.memo = memo;
	}
}
