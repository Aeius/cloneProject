package com.bit.checkpayclone.member.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	// 회원가입
	void join(MemberVo bean);
	
	// 고객정보
	MemberVo memberInfo(String pk, String column);
	
	// 회원정보 수정(이메일, 이름 수정)
	int updateMember(MemberVo bean);
	
	// 회원탈퇴
	int deleteMember(String pk);
	
	
	
	//-- 2023.04.11 tb_member_fin 테이블 관련 매퍼 추가 --
	// Fin 인증 최초 없을 시 삽입
	int finInsertIfNotExist(String pk);
	
	// Fin 인증 정보 수정
	int finInfoSetting(MemberVo bean);
	
	// Fin 인증 정보 조회
	MemberVo finInfoSelectOne(String pk);
	
	//-- 2023.04.18 tb_member 테이블 관련 매퍼 추가 --
	// tb_member Vo에 set 한 값만 업데이트 (memb_cd 필수)
	int updateMember2(MemberVo bean);
	
	/**
	 * 회원 존재 유무
	 * 
	 * @param pk : 서로를 식별할 수 있는 값
	 * @return
	 */
	boolean isExistsMember(String pk);
	
	int memberEmailCount(String email);
}
