package com.bit.checkpayclone.asset.main.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EditinfoMapper {

	void updateUserEditList(String member_id, String catg_cd, int ord_seq, String hide);

	EditinfoVo findUserEditList(String member_id);
	
	AssetVo findUserAsset(String member_id);
	
}
