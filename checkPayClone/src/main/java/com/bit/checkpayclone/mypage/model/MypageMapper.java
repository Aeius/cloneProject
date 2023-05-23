package com.bit.checkpayclone.mypage.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageMapper {
	
	// FAQ 조회
	List<FaqVo> findMypageAll();
	// 약관 조회
	List<TermsVo> findTerms();
}
