package com.bit.checkPayCloneFE.card.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardBillsVo {
	private String org_code, paid_out_date, charge_amt;
	private String paid_dtime, card_name, paid_amt, merchant_name,merchant_regno;
	private String credit_fee_amt, total_install_cnt, cur_install_cnt, balance_amt;
	
	public CardBillsVo(String org_code, String paid_out_date, String charge_amt) {
		this.org_code = org_code;
		this.paid_out_date = paid_out_date;
		this.charge_amt = charge_amt;
	}
	
}
