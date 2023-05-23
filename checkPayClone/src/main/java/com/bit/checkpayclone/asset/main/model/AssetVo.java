package com.bit.checkpayclone.asset.main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssetVo {
	private long sum;

	public AssetVo(long sum) {
		this.sum = sum;
	}
	
	
}
