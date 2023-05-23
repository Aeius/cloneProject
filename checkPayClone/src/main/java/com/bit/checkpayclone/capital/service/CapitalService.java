package com.bit.checkpayclone.capital.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.admin.member.service.AdminMemberService;
import com.bit.checkpayclone.capital.model.CapitalMapper;
import com.bit.checkpayclone.capital.model.CapitalTransVo;
import com.bit.checkpayclone.capital.model.CapitalVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CapitalService {
	
	final AdminMemberService adminMemberService;
	final SqlSession sqlSession;
	
	public List<CapitalVo> selectUserCapital(String id) {
		
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
		adminMemberService.addMemberLog(String.valueOf(id), 1, "/api/capital/membercapital/{id}");
		/**
		 * ----------------------------------------------------------------------------
		 */
		
		return sqlSession.getMapper(CapitalMapper.class).selectUserCapital(id);
	}
	public List<CapitalVo> selectUserCapitalDetail(String id,String org_code,String account_num){
		return sqlSession.getMapper(CapitalMapper.class).selectUserCapitalDetail(id, org_code, account_num);
	}
	public List<CapitalTransVo> selectCapitalTransDetail(String id,String org_code,String account_num,Date start_date,Date end_date,String sort,int offset){
		return sqlSession.getMapper(CapitalMapper.class).selectCapitalTransDetail(id,org_code,account_num,start_date,end_date,sort,offset);
	}
}

//id,org_code,account_num,start_date,end_date,sort,offset