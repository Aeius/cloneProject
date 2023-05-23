package com.bit.checkpayclone.member.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bit.checkpayclone.admin.login.model.AdminLoginVo;
import com.bit.checkpayclone.admin.login.service.RefreshTokenService;
import com.bit.checkpayclone.admin.member.service.AdminMemberService;
import com.bit.checkpayclone.member.model.MemberMapper;
import com.bit.checkpayclone.member.model.MemberVo;
import com.bit.checkpayclone.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final SqlSession sqlSession;
	
	final AdminMemberService adminMemberService;
	
	/**
	 * jwt 관련 참조 변수
	 */
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenService refreshTokenService;
	
	/**
	 * 암호화 관련 참조 변수
	 */
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * 회원 로그인
	 * 
	 * @param bean : 로그인을 시도한 회원 정보. email과 password 정보만이 담겨있다.
	 * @return Map<String, Object> : 로그인 요청의 상황을 Map 형식으로 반환
	 */
	public Map<String, Object> login(MemberVo bean, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(bean.getMember_email()==null || bean.getMember_email().equals("")) {
			result.put("status", "fail");
			result.put("msg", "이메일이 존재하지 않습니다.");
			
			return result;
		}
		
		if(bean.getMember_password()==null || bean.getMember_password().equals("")) {
			result.put("status", "fail");
			result.put("msg", "비밀번호가 존재하지 않습니다.");
			
			return result;
		}
		
		MemberVo memberVo = memberInfo(bean.getMember_email(), true, "email");
		
		if(!isExistsMember(bean.getMember_email())) {
			result.put("status", "fail");
			result.put("msg", "계정이 존재하지 않습니다.");
			
			return result;
		}
		
		if(memberVo.getLeave_yn()=='Y') {
			result.put("status", "fail");
			result.put("msg", "이미 탈퇴한 계정입니다.");
			
			return result;
		}
		
		if(!bCryptPasswordEncoder.matches(bean.getMember_password(),memberVo.getMember_password())) {
			result.put("status", "fail");
			result.put("msg", "비밀번호가 일치하지 않습니다.");
			
			return result;
		}
		
		String ip = refreshTokenService.getClientIp(request);
		System.out.println(ip);
		
		/**
		 * 동일한 ip일 경우 로그인 허용
		 * 일단 임시로 주석 처리
		 */
//		if(refreshTokenService.isRefreshToken(memberVo.getMemb_cd())) {
//			if(!refreshTokenService.isAccessByUserIp(memberVo.getMemb_cd(), ip)) {
//				result.put("status", "fail");
//				result.put("msg", "이미 로그인된 유저가 있습니다.");
//				
//				return result;
//			}
//		}

		AdminLoginVo loginVo = AdminLoginVo.builder().id(memberVo.getMemb_cd()).password(memberVo.getMember_password()).build();

		//수정된 부분.
		Map<String, Object> map = jwtTokenProvider.generateToken(loginVo);
		String token = (String)map.get("atk");
		
		if(token == null && !token.equals("")) {
			result.put("status", "fail");
			result.put("msg", "유효하지 않은 토큰입니다.");
			
			return result;
		}
		
		Map<String, Object> rtk = jwtTokenProvider.generateRToken(loginVo);
		String refreshToken = (String) rtk.get("rtk");
		
		refreshTokenService.saveTokenInfo(memberVo.getMemb_cd(),token, refreshToken);
		refreshTokenService.saveUserIp(memberVo.getMemb_cd(), ip);
		
		/**
		 * 로그인 시 회원 활동 기록 등록하는 서비스 호출
		 * 
		 * @param member_id : 자산과 관련된 일을 진행한 회원 id
		 * @param action_type : 1 - 조회 // 2 - 로그인
		 * @param api_url : 요청한 api 주소
		 *
		 * @author 차원학
		 * ----------------------------------------------------------------------------
		 */	
		adminMemberService.addMemberLog(memberVo.getMemb_cd(), 2, "/api/member/login");
		/**
		 * ----------------------------------------------------------------------------
		 */
		
		result.put("status", "success");
		result.put("jwt", token);
		result.put("rtk", refreshToken);

		return result;
	}
	
	// 회원가입
	@Transactional
	public Map<String, Object> join(MemberVo bean) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(bean.getMember_email()==null || bean.getMember_email().equals("")) {
			result.put("status", "fail");
			result.put("msg", "이메일이 존재하지 않습니다.");
			
			return result;
		}
		
		MemberVo memberVo = memberInfo(bean.getMember_email(), true, "email");
		if(isExistsMember(bean.getMember_email())) {
			result.put("status", "fail");
			result.put("msg", "이미 존재하는 계정입니다.");
			
			return result;
		}
		
		if(bean.getMember_name()==null || bean.getMember_name().equals("")) {
			result.put("status", "fail");
			result.put("msg", "이름이 존재하지 않습니다.");
			
			return result;
		}
		if(bean.getMember_password()==null || bean.getMember_password().equals("")) {
			result.put("status", "fail");
			result.put("msg", "비밀번호가 존재하지 않습니다.");
			
			return result;
		}
		if(bean.getMember_gender()==null || bean.getMember_gender().equals("")) {
			result.put("status", "fail");
			result.put("msg", "성별이 존재하지 않습니다.");
			
			return result;
		}
		if(bean.getMember_tel()==null || bean.getMember_tel().equals("")) {
			result.put("status", "fail");
			result.put("msg", "연락처가 존재하지 않습니다.");
			
			return result;
		}
		
		bean.setMemb_cd("U"+System.currentTimeMillis());
		bean.setMember_password(bCryptPasswordEncoder.encode(bean.getMember_password()));
		
		sqlSession.getMapper(MemberMapper.class).join(bean);
		
		result.put("status", "success");
		
		return result;
	}
	
	/**
	 * 회원 인증
	 * 로그인한 회원의 jwt 토큰을 복호화 후 그 회원의 정보 얻기
	 * 
	 * @return MemberVo : 로그인한 회원의 정보
	 */
	public MemberVo memberAuth() {
		MemberVo memberVo = memberInfo(((AdminLoginVo)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(), false, "memb_cd");
		
		return memberVo;
	}
	
	// 고객정보
	public MemberVo memberInfo(String member_id, boolean visiable_password, String column) {
		MemberVo memberVo = sqlSession.getMapper(MemberMapper.class).memberInfo(member_id, column);
		
		/**
		 * 보안 상의 문제가 있을 경우 비밀번호는 강제로 null 처리
		 */
		if(!visiable_password) {
			memberVo.setMember_password(null);
		}

		return memberVo;
	}
	
	public boolean isExistsMember(String member_email) {
		return sqlSession.getMapper(MemberMapper.class).isExistsMember(member_email);
	}
	
	// 회원 이메일 수정
	@Transactional
	public int updateMember(MemberVo bean) {
		return sqlSession.getMapper(MemberMapper.class).updateMember(bean);
	}
	
	// 회원탈퇴
	@Transactional
	public int deleteMember(String member_id) {
		return sqlSession.getMapper(MemberMapper.class).deleteMember(member_id);
	}
	
	//----2023.04.18 tb_member 테이블 관련 서비스 추가-----
	// 회원 이메일 수정
	@Transactional
	public int updateMember2(MemberVo bean) {
		return sqlSession.getMapper(MemberMapper.class).updateMember2(bean);
	}
	
	// ----2023.04.11 tb_member_fin 테이블 관련 서비스 추가-----
	// insert 안되어있으면 자동으로 넣어준다음, bean에 넣은 값만 UPDATE 날짜는 자동 NOW 업데이트(member_id 필수)
	@Transactional
	public int finAutoInsertInfoSetting(MemberVo bean) {
		sqlSession.getMapper(MemberMapper.class).finInsertIfNotExist(bean.getMemb_cd());
		return sqlSession.getMapper(MemberMapper.class).finInfoSetting(bean);
	}

	// 한 사용자 인증정보 가져오기
	public MemberVo finInfoSelectOne(String pk) {
		return sqlSession.getMapper(MemberMapper.class).finInfoSelectOne(pk);
	}
	
	//2023.10.12 이메일 중복검사
	public boolean memberEmailCount(String email) {
		email = new String(Base64.getDecoder().decode(email.getBytes()));
		return 0 == sqlSession.getMapper(MemberMapper.class).memberEmailCount(email);
	}

}
