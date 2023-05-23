package com.bit.checkPayCloneFE.efin.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EfinVo {

	/**
	 * tb_a_efin_prepaid_list 관련 변수
	 * 
	 * @param memb_cd    회원_코드
	 * @param org_code   기관코드
	 * @param sub_key    서브키
	 * @param account_id 계정식별값
	 * @param reg_date   가입일
	 * @param reg_dttm   등록일시
	 * @param upd_dttm   수정일시
	 * @param is_consent 전송요구여부
	 * @param is_pay     결제수단등록여부
	 */
	String memb_cd, org_code, sub_key, account_id, reg_date, reg_dttm, upd_dttm;
	Character is_consent, is_pay;

	/**
	 * tb_a_efin_prepaid_trans 관련 변수 tb_a_efin_prepaid_list 관련 변수
	 * tb_a_efin_paid_trans 관련 변수
	 */
	String trans_type, trans_num, trans_dtime, trans_no, trans_dt, currency_code, trans_org_code, pay_id, trans_memo,
			merchant_name, trans_title, trans_category, pay_method, merchant_regno;
	Double trans_amt;
	Integer total_install_cnt, seq;

	/**
	 * tb_a_efin_prepaid_list 관련 변수
	 */
	String fob_id, fob_name;
	Character is_charge;

	Integer limit_amt, account_cnt;

	/**
	 * tb_a_efin_prepaid_list 관련 변수
	 */
	String trans_id;
	Double balance_amt;

	/**
	 * 날짜 및 추가검색 등을 위한 추가 변수
	 * 
	 * @param startDt, endDt 검색 시작일, 종료일
	 * @param flag1    팝업등에서 선택한 플래그값
	 * @param page    페이징 사용시 페이지 번호
	 * @param pay_method_name    온라인, 오프라인 결제 이름 출력 위해
	 */
	String startDt, endDt, pay_method_name;
	Character flag1;
	Integer page;
	/**
	 * tb_a_efin_prepaid_balance 관련 필드 추가
	 */
	String exp_due_amt;

	Double total_balance_amt, charge_balance_amt, reserve_balance_amt, reserve_due_amt;

	/**
	 * tb_a_efin_paid_member 관련 필드 추가
	 */
	String acct_nick_nm;

	Integer ord_seq;
	Character error_yn, hidden_yn;

	/**
	 * tb_a_efin_paid_method 관련 필드 추가
	 */
	String pay_org_code;

	Character is_primary;

	/**
	 * tb_a_mydata_orgs 관련 필드 추가
	 */
	String org_name, industry, org_name_abb, org_img_name, org_type, bank_code, scard_img_name, check_start_dttm,
			check_end_dttm;
	
	/**
	 * tb_a_efin_item_catg 관련 필드 추가
	 */
	String item_catg, catg_name, inclusions;
	
	@Builder
	public EfinVo(String memb_cd, String org_code, String sub_key, String account_id, String reg_date, String reg_dttm,
			String upd_dttm, Character is_consent, Character is_pay, String trans_type, String trans_num,
			String trans_dtime, String trans_no, String trans_dt, String currency_code, String trans_org_code,
			String pay_id, String trans_memo, String merchant_name, String trans_title, String trans_category,
			String pay_method, String merchant_regno, Double trans_amt, Integer total_install_cnt, Integer seq,
			String fob_id, String fob_name, Character is_charge, Integer limit_amt, Integer account_cnt,
			String trans_id, Double balance_amt, String startDt, String endDt, String pay_method_name, Character flag1,
			Integer page, String exp_due_amt, Double total_balance_amt, Double charge_balance_amt,
			Double reserve_balance_amt, Double reserve_due_amt, String acct_nick_nm, Integer ord_seq,
			Character error_yn, Character hidden_yn, String pay_org_code, Character is_primary, String org_name,
			String industry, String org_name_abb, String org_img_name, String org_type, String bank_code,
			String scard_img_name, String check_start_dttm, String check_end_dttm, String item_catg, String catg_name,
			String inclusions) {
		super();
		this.memb_cd = memb_cd;
		this.org_code = org_code;
		this.sub_key = sub_key;
		this.account_id = account_id;
		this.reg_date = reg_date;
		this.reg_dttm = reg_dttm;
		this.upd_dttm = upd_dttm;
		this.is_consent = is_consent;
		this.is_pay = is_pay;
		this.trans_type = trans_type;
		this.trans_num = trans_num;
		this.trans_dtime = trans_dtime;
		this.trans_no = trans_no;
		this.trans_dt = trans_dt;
		this.currency_code = currency_code;
		this.trans_org_code = trans_org_code;
		this.pay_id = pay_id;
		this.trans_memo = trans_memo;
		this.merchant_name = merchant_name;
		this.trans_title = trans_title;
		this.trans_category = trans_category;
		this.pay_method = pay_method;
		this.merchant_regno = merchant_regno;
		this.trans_amt = trans_amt;
		this.total_install_cnt = total_install_cnt;
		this.seq = seq;
		this.fob_id = fob_id;
		this.fob_name = fob_name;
		this.is_charge = is_charge;
		this.limit_amt = limit_amt;
		this.account_cnt = account_cnt;
		this.trans_id = trans_id;
		this.balance_amt = balance_amt;
		this.startDt = startDt;
		this.endDt = endDt;
		this.pay_method_name = pay_method_name;
		this.flag1 = flag1;
		this.page = page;
		this.exp_due_amt = exp_due_amt;
		this.total_balance_amt = total_balance_amt;
		this.charge_balance_amt = charge_balance_amt;
		this.reserve_balance_amt = reserve_balance_amt;
		this.reserve_due_amt = reserve_due_amt;
		this.acct_nick_nm = acct_nick_nm;
		this.ord_seq = ord_seq;
		this.error_yn = error_yn;
		this.hidden_yn = hidden_yn;
		this.pay_org_code = pay_org_code;
		this.is_primary = is_primary;
		this.org_name = org_name;
		this.industry = industry;
		this.org_name_abb = org_name_abb;
		this.org_img_name = org_img_name;
		this.org_type = org_type;
		this.bank_code = bank_code;
		this.scard_img_name = scard_img_name;
		this.check_start_dttm = check_start_dttm;
		this.check_end_dttm = check_end_dttm;
		this.item_catg = item_catg;
		this.catg_name = catg_name;
		this.inclusions = inclusions;
	}
}