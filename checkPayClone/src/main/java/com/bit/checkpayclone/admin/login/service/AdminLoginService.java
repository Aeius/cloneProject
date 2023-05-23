package com.bit.checkpayclone.admin.login.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bit.checkpayclone.admin.login.model.AdminLoginMapper;
import com.bit.checkpayclone.admin.login.model.AdminLoginVo;
import com.bit.checkpayclone.member.model.MemberMapper;
import com.bit.checkpayclone.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminLoginService{

	private final JwtTokenProvider jwtTokenProvider;
	private final SqlSession sqlSession;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final RefreshTokenService refreshTokenService;
	
	//로그인
	public Map<String, Object> adminLogin(AdminLoginVo vo, HttpServletRequest request) {
		String ip = refreshTokenService.getClientIp(request);
		Map<String, Object> jwt = new HashMap<>();
		
		if(vo.getId()==null || vo.getId().equals("")) {
			jwt.put("state", "fail");
			jwt.put("msg", "존재하지 않는 계정입니다.");
			return jwt;
		}
		
		if(!isExistsAdmin(vo.getId())){
			jwt.put("state", "fail");
			jwt.put("msg", "존재하지 않는 계정입니다.");
			return jwt;
		}
		Optional<AdminLoginVo> adminInfoVo = sqlSession.getMapper(AdminLoginMapper.class).findAdminInfoById(vo.getId());
		
		if(bCryptPasswordEncoder.matches(vo.getPassword(),adminInfoVo.get().getPassword())) {
				//redis에 rtk가 존재안하면
				if(!refreshTokenService.isRefreshToken(adminInfoVo.get().getId())) {
					Map<String, Object> accessToken = jwtTokenProvider.generateToken(adminInfoVo.get());
					Map<String, Object> refreshToken = jwtTokenProvider.generateRToken(adminInfoVo.get());
					
					String atk = (String) accessToken.get("atk");
					String rtk = (String) refreshToken.get("rtk");
					String role = (String) accessToken.get("role");
					jwt.put("atk", atk);
					jwt.put("rtk", rtk);
					jwt.put("role",role);
					jwt.put("state", "ok");
					refreshTokenService.saveTokenInfo(vo.getId(),atk, rtk);
					refreshTokenService.saveUserIp(vo.getId(), ip);
					return jwt;
				}
				//redis에 rtk가 존재하며, 접근한 user의 ip주소가 일치하면
				else if((refreshTokenService.isRefreshToken(adminInfoVo.get().getId())) && refreshTokenService.isAccessByUserIp(adminInfoVo.get().getId(), ip)) {
					//기존의 redis에 저장된 rtk 업데이트.
					//새로운 atk, rtk, 생성 후 전달 및 rtk,ip redis에 새롭게 저장.
					Map<String, Object> accessToken = jwtTokenProvider.generateToken(adminInfoVo.get());
					Map<String, Object> refreshToken = jwtTokenProvider.generateRToken(adminInfoVo.get());
					
					String atk = (String) accessToken.get("atk");
					String rtk = (String) refreshToken.get("rtk");
					String role = (String) accessToken.get("role");
					jwt.put("atk", atk);
					jwt.put("rtk", rtk);
					jwt.put("role",role);
					jwt.put("state", "ok");
					refreshTokenService.saveTokenInfo(vo.getId(),atk, rtk);
					refreshTokenService.saveUserIp(vo.getId(), ip);
					return jwt;
				}
				else {
					jwt.put("state", "duplicated");
					jwt.put("msg", "이미 접속중인 계정입니다.");
					return jwt;
				}
		}
		else if(!bCryptPasswordEncoder.matches(vo.getPassword(),adminInfoVo.get().getPassword())) {
				jwt.put("state", "fail");
				jwt.put("msg", "패스워드가 틀렸습니다.");
				return jwt;
		}
		else {
				jwt.put("state", "fail");
				jwt.put("msg", "알수없는 에러.");
				return jwt;
		}
		
	}
	
	//어드민 계정 존재 유무 확인
	public boolean isExistsAdmin(String id) {
		return sqlSession.getMapper(AdminLoginMapper.class).isExistsAdmin(id);
	}
	
	
	
	//토큰 재발급
	public String reissuedToken(String id,String rtk) {
		if(refreshTokenService.getRefreshToken(id,rtk)) {
			Optional<AdminLoginVo> adminInfoVo = sqlSession.getMapper(AdminLoginMapper.class).findAdminInfoById(id);
			Map<String, Object> accessToken = jwtTokenProvider.generateToken(adminInfoVo.get());
			String atk = (String) accessToken.get("atk");
			return atk;
		}
		return null;
	}
	
	//로그아웃
	public void adminLogout(String rtk) {
		String id = jwtTokenProvider.getUserIdByRTK(rtk);
		refreshTokenService.tokenRemoveByLogout(id,rtk);
	}

}
