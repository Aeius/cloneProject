package com.bit.checkpayclone.mypage.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.mypage.model.MypageMapper;
import com.bit.checkpayclone.mypage.model.TermsVo;
import com.bit.checkpayclone.mypage.model.FaqVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageService {
	final SqlSession sqlSession;
	
	public List<FaqVo> MypagefaqSelectAll() { 
		return sqlSession.getMapper(MypageMapper.class).findMypageAll();
	}
	
	public List<TermsVo> findTerms() { 
		return sqlSession.getMapper(MypageMapper.class).findTerms();
	}
}
