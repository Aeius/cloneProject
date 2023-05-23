package com.bit.checkpayclone.card.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardPointVo {
	private String org_name, point_name;
	private long remain_point_amt, expiring_point_amt;
}
