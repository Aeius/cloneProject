package com.bit.checkpayclone.card.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardVo {
	private long sum;
	private String card_id, card_num, card_name, card_type, card_member, linked_bank_code, account_num, card_brand, org_code,img_uri;
	private Timestamp issue_date;
}
