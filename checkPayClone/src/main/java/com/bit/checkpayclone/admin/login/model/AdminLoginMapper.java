package com.bit.checkpayclone.admin.login.model;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminLoginMapper {

	//UserDetails에서 데이터베이스의 정보를 가져오게 하기
	Optional<AdminLoginVo> findAdminInfoById(String id);
	
	/**
	 * ADMIN 존재 유무 확인
	 * 
	 * */
	boolean isExistsAdmin(String id);
	
}

