package com.bit.checkpayclone.asset.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.admin.member.service.AdminMemberService;
import com.bit.checkpayclone.asset.model.AssetMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetService {
	final SqlSession sqlSession;

	public void updateMemberLastBalance() {
		sqlSession.getMapper(AssetMapper.class).updateMemberLastBalance();
	}
	
	

}
