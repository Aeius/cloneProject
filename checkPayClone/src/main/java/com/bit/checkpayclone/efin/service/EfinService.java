package com.bit.checkpayclone.efin.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.checkpayclone.efin.model.EfinMapper;
import com.bit.checkpayclone.efin.model.EfinVo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EfinService {
	
	final SqlSession sqlSession;
	
	// 권면 API 관련---------------------------------
	/**
	 * 선불거래정보 권면별 리스트 출력
	 */
	public JsonObject findPrepaidTranList(EfinVo bean){
		Gson gson = new Gson();
		JsonObject resultObject = new JsonObject();
		
		List<EfinVo> prepaidTranList = sqlSession.getMapper(EfinMapper.class).findPrepaidTranList(bean);
		JsonArray trans=null;
		try {
			trans = gson.toJsonTree(prepaidTranList).getAsJsonArray();
		}catch(IllegalStateException e) {
			resultObject.add("result",trans);
			return resultObject;
		}
		
		resultObject.add("result",trans);
		return resultObject;
	}
	
	/**
	 * 2023.05.01
	 *  선불 페이 기관 권면별 결제정보 상세 조회
	 * */
	public EfinVo findPrepaidTranDetailOne(EfinVo bean) {
		return sqlSession.getMapper(EfinMapper.class).findPrepaidTranDetailOne(bean);
	}
	/** 2023.05.01
	 * 선불 페이 권면 종합정보 조회
	 * */
	@Transactional
	public JsonObject findPrepaidInfoOne(EfinVo bean){
		Gson gson = new Gson();
		JsonObject resultObject = new JsonObject();
		
		EfinVo prepaidInfo = sqlSession.getMapper(EfinMapper.class).findPrepaidInfoOne(bean);
		JsonObject info = null;
		try {
			info = gson.toJsonTree(prepaidInfo).getAsJsonObject();
		}catch(IllegalStateException e) {
			resultObject.add("result",info);
			return resultObject;
		}
		resultObject.add("result",info);
		return resultObject;
	}
	
	/**
	 * 선불 페이 권면 조회 리스트
	 * */
	public List<EfinVo> findPrepaidList(String id){
		return sqlSession.getMapper(EfinMapper.class).findPrepaidList(id);
	}
	
	
	// 계정 API 관련---------------------------------
	
	/**
	 * 2023.05.02
	 *  선불 페이 기관 계정 결제정보 조회
	 * */
	public JsonObject findPaidTranList(EfinVo bean){
		Gson gson = new Gson();
		JsonObject resultObject = new JsonObject();
		
		List<EfinVo> paidTranList = sqlSession.getMapper(EfinMapper.class).findPaidTranList(bean);
		JsonArray trans = null;
		try {
			trans = gson.toJsonTree(paidTranList).getAsJsonArray();
		}catch(IllegalStateException e) {
			resultObject.add("result",trans);
			return resultObject;
		}
		
		resultObject.add("result",trans);
		return resultObject;
	}
	
	/**
	 * 2023.05.02
	 *  선불 페이 기관 계정 결제정보 상세 조회
	 * */
	public EfinVo findPaidTranDetailOne(EfinVo bean) {
		return sqlSession.getMapper(EfinMapper.class).findPaidTranDetailOne(bean);
	}
	
	/** 2023.05.02
	 * 선불 페이 계정 종합정보 조회
	 * */
	@Transactional
	public JsonObject findPaidInfo(EfinVo bean){
		Gson gson = new Gson();
		JsonObject resultObject = new JsonObject();
		
		EfinVo prepaidInfo = sqlSession.getMapper(EfinMapper.class).findPaidInfoOne(bean);
		List<EfinVo> paidMethodList = sqlSession.getMapper(EfinMapper.class).findPaidMethod(bean);
		JsonObject info = null;		
		JsonArray trans = null;
		
		try {
			info = gson.toJsonTree(prepaidInfo).getAsJsonObject();
			trans = gson.toJsonTree(paidMethodList).getAsJsonArray();
		}catch(IllegalStateException e) {
			resultObject.add("result",info);
			resultObject.add("method",trans);
			return resultObject;
		}
		resultObject.add("result",info);
		resultObject.add("method",trans);
		
		return resultObject;
	}
	
	// tb_a_mydata_orgs API 관련---------------------------------
	/**
	 * 기관에 해당하는 계정 조회
	 * */
	public List<EfinVo> findAccountIdByOrgName(String id,String orgname) {
		return sqlSession.getMapper(EfinMapper.class).findAccountIdByOrgName(id, orgname);
	}
}
