package com.bit.checkpayclone.card.serivce;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.admin.member.service.AdminMemberService;
import com.bit.checkpayclone.card.model.CardLoneLongVo;
import com.bit.checkpayclone.card.model.CardLoneShortVo;
import com.bit.checkpayclone.card.model.CardMapper;
import com.bit.checkpayclone.card.model.CardPointVo;
import com.bit.checkpayclone.card.model.CardRevolvigVo;
import com.bit.checkpayclone.card.model.CardVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
	final SqlSession sqlSession;
	
	final AdminMemberService adminMemberService;
	
	public List<CardVo> selectUserCard(String id) {
		
		/**
		 * 자산 관련 조회 시 회원 활동 기록 등록하는 서비스 호출
		 * 
		 * @param member_id : 자산과 관련된 일을 진행한 회원 id
		 * @param action_type : 1 - 조회 // 2 - 인증
		 * @param api_url : 요청한 api 주소
		 *
		 * @author 차원학
		 * ----------------------------------------------------------------------------
		 */	
		adminMemberService.addMemberLog(String.valueOf(id), 1, "/api/card/membercard/{id}");
		/**
		 * ----------------------------------------------------------------------------
		 */
		
		return sqlSession.getMapper(CardMapper.class).findUserCard(id);
	}
	
	public List<CardVo> selectUserCardName(String id, String card_id) {
		return sqlSession.getMapper(CardMapper.class).findUserCardName(id, card_id);
	}
	
	public List<CardLoneLongVo> selectUserCardloanLong(String id) {
		return sqlSession.getMapper(CardMapper.class).findLoanLongCard(id);
	}

	public List<CardLoneShortVo> selectUserCardloanShort(String id) {
		return sqlSession.getMapper(CardMapper.class).findLoanShortCard(id);
	}
	
	public List<CardPointVo> selectUserCardPoint(String id, String org_code) {
		return sqlSession.getMapper(CardMapper.class).findUserCardPoint(id, org_code);
	}
	
	public List<CardRevolvigVo> selectUserCardRevolving(String id, String org_code) {
		return sqlSession.getMapper(CardMapper.class).findUserCardRevolvig(id, org_code);
	}
}
