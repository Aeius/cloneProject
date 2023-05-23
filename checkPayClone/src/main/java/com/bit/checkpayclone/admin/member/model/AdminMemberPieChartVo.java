package com.bit.checkpayclone.admin.member.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminMemberPieChartVo {
	private int actionType;
	private int actionTypeCount;
	private int actionTypePercent;
}
