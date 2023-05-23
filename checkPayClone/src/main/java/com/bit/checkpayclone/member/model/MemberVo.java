package com.bit.checkpayclone.member.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter	
@NoArgsConstructor
public class MemberVo {
	private String member_id, member_name, member_email, member_gender, member_reg_date, member_tel, member_password;
	
	//2023.04.17 KSH: tb_member 테이블 관련 필드 추가
	private String memb_cd, email, memb_nm, brt_dt, ci, di, trx_pwd_yn, fin_conn_ver, fin_conn_dt, pwd_chng_dt, trx_pwd_chng_dt, 
	reg_dttm, upd_dttm, fin_upd_dttm, cp_memb_no, ph_no, tele_corp, nick_nm, api_final_upd_dttm, admin_yn, eml_cycle_dttm;
	private Character gndr, in_frn_tp, pwd_yn, memb_st, auth_yn, update_init_yn, eml_cycle_yn;
	private Integer conn_cnt, pwd_fail_cnt, trx_pwd_fail_cnt;
	
	//2023.04.19 KSH: tb_member 테이블 추가 컬럼 관련 필드 추가
	private String memb_pwd, role; 
	private Character leave_yn;
	
	//2023.04.11 KSH: tb_member_fin 테이블 관련 필드 추가
	private String fin_no, eml_req_dt, eml_auth_no;
	private Integer fin_no_fail_cnt, eml_auth_cnt, eml_fail_cnt;
	private Timestamp fail_timestamp;
	
	@Builder
	public MemberVo(String member_id, String member_name, String member_email, String member_gender,
			String member_reg_date, String member_tel, String member_password, String memb_cd, String email,
			String memb_nm, String brt_dt, String ci, String di, String trx_pwd_yn, String fin_conn_ver,
			String fin_conn_dt, String pwd_chng_dt, String trx_pwd_chng_dt, String reg_dttm, String upd_dttm,
			String fin_upd_dttm, String cp_memb_no, String ph_no, String tele_corp, String nick_nm,
			String api_final_upd_dttm, String admin_yn, String eml_cycle_dttm, Character gndr, Character in_frn_tp,
			Character pwd_yn, Character memb_st, Character auth_yn, Character update_init_yn, Character eml_cycle_yn,
			Integer conn_cnt, Integer pwd_fail_cnt, Integer trx_pwd_fail_cnt, String fin_no, String eml_req_dt,
			String eml_auth_no, Integer fin_no_fail_cnt, Integer eml_auth_cnt, Integer eml_fail_cnt,
			Timestamp fail_timestamp) {
		super();
		this.member_id = member_id;
		this.member_name = member_name;
		this.member_email = member_email;
		this.member_gender = member_gender;
		this.member_reg_date = member_reg_date;
		this.member_tel = member_tel;
		this.member_password = member_password;
		this.memb_cd = memb_cd;
		this.email = email;
		this.memb_nm = memb_nm;
		this.brt_dt = brt_dt;
		this.ci = ci;
		this.di = di;
		this.trx_pwd_yn = trx_pwd_yn;
		this.fin_conn_ver = fin_conn_ver;
		this.fin_conn_dt = fin_conn_dt;
		this.pwd_chng_dt = pwd_chng_dt;
		this.trx_pwd_chng_dt = trx_pwd_chng_dt;
		this.reg_dttm = reg_dttm;
		this.upd_dttm = upd_dttm;
		this.fin_upd_dttm = fin_upd_dttm;
		this.cp_memb_no = cp_memb_no;
		this.ph_no = ph_no;
		this.tele_corp = tele_corp;
		this.nick_nm = nick_nm;
		this.api_final_upd_dttm = api_final_upd_dttm;
		this.admin_yn = admin_yn;
		this.eml_cycle_dttm = eml_cycle_dttm;
		this.gndr = gndr;
		this.in_frn_tp = in_frn_tp;
		this.pwd_yn = pwd_yn;
		this.memb_st = memb_st;
		this.auth_yn = auth_yn;
		this.update_init_yn = update_init_yn;
		this.eml_cycle_yn = eml_cycle_yn;
		this.conn_cnt = conn_cnt;
		this.pwd_fail_cnt = pwd_fail_cnt;
		this.trx_pwd_fail_cnt = trx_pwd_fail_cnt;
		this.fin_no = fin_no;
		this.eml_req_dt = eml_req_dt;
		this.eml_auth_no = eml_auth_no;
		this.fin_no_fail_cnt = fin_no_fail_cnt;
		this.eml_auth_cnt = eml_auth_cnt;
		this.eml_fail_cnt = eml_fail_cnt;
		this.fail_timestamp = fail_timestamp;
	}
}
