package com.bit.checkpayclone.asset.main.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.admin.member.service.AdminMemberService;
import com.bit.checkpayclone.asset.main.model.EditinfoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditinfoService {

	final SqlSession sqlSession;
	
	final AdminMemberService adminMemberService;
	
//	 @Value("${rt.baseurl}") 
//	 private String baseurl;

	private String baseurl="http://192.168.240.208:8081";

	public Object getUserEditList(String member_id) {
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
		adminMemberService.addMemberLog(String.valueOf(member_id), 1, "/api/asset/main/editinfo/{member_id}");
		/**
		 * ----------------------------------------------------------------------------
		 */
		
		return sqlSession.getMapper(EditinfoMapper.class).findUserEditList(member_id);
	}

	public void setUserEditList(String member_id, String[] edit_list, String[] hide_list) {
		int num=5;	//업권 개수
		for (int i =0; i<edit_list.length;i++) {
			if(edit_list[i].equals("mainphone")) {
				edit_list[i]="telecom";
			}
			if(edit_list[i].equals("mainbank")) {
				edit_list[i]="bank";
			}
			if(edit_list[i].equals("maincard")) {
				edit_list[i]="card";
			}
			if(edit_list[i].equals("mainpay")) {
				edit_list[i]="efin";
			}
			if(edit_list[i].equals("maininvest")) {
				edit_list[i]="invest";
			}
			if(edit_list[i].equals("maincap")) {
				edit_list[i]="capital";
			}
			sqlSession.getMapper(EditinfoMapper.class).updateUserEditList(member_id,edit_list[i],i+1,"N");
		}
		for (int i =0; i<hide_list.length;i++) {
			if(hide_list[i].equals("mainphone")) {
				hide_list[i]="telecom";
			}
			if(hide_list[i].equals("mainbank")) {
				hide_list[i]="bank";
			}
			if(hide_list[i].equals("maincard")) {
				hide_list[i]="card";
			}
			if(hide_list[i].equals("mainpay")) {
				hide_list[i]="efin";
			}
			if(hide_list[i].equals("maininvest")) {
				hide_list[i]="invest";
			}
			if(hide_list[i].equals("maincap")) {
				hide_list[i]="capital";
			}
			sqlSession.getMapper(EditinfoMapper.class).updateUserEditList(member_id,hide_list[i],5,"Y");
		}
	}
	
	public Object getAssetSum(String member_id) {
		return sqlSession.getMapper(EditinfoMapper.class).findUserAsset(member_id);
	}

}
