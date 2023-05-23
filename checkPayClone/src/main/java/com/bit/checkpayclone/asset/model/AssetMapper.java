package com.bit.checkpayclone.asset.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AssetMapper {

	void updateMemberLastBalance();

}
