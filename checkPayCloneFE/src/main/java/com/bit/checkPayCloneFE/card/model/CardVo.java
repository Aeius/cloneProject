package com.bit.checkPayCloneFE.card.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardVo {
	
	private String org_code,card_name,card_id,card_type,card_num,img_uri;
	private int price_sum;
	
	public CardVo(String org_code, String card_name, String card_id, String card_type, String card_num, String img_uri, int price_sum) {
		this.org_code = org_code;
		this.card_name = card_name;
		this.card_id = card_id;
		this.card_type = card_type;
		this.card_num = card_num;
		this.img_uri = img_uri;
		this.price_sum = price_sum;
	}

	public CardVo(String card_id) {
		this.card_id = card_id;
	}
}