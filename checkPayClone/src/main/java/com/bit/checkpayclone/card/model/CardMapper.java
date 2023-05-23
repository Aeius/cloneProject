package com.bit.checkpayclone.card.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardMapper {
		
	List<CardVo> findUserCard(String id);
	List<CardVo> findUserCardName(String id, String card_id);
	List<CardLoneLongVo> findLoanLongCard(String id);
	List<CardLoneShortVo> findLoanShortCard(String id);
	List<CardPointVo> findUserCardPoint(String id, String org_code);
	List<CardRevolvigVo> findUserCardRevolvig(String id, String org_code);
}
