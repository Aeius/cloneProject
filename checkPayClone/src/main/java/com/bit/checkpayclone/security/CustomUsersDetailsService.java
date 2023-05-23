package com.bit.checkpayclone.security;

import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bit.checkpayclone.admin.login.model.AdminLoginMapper;
import com.bit.checkpayclone.admin.login.model.AdminLoginVo;
import com.bit.checkpayclone.admin.login.model.ROLE;
import com.bit.checkpayclone.member.model.MemberMapper;
import com.bit.checkpayclone.member.model.MemberVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService{
	private final SqlSession sqlSession;
	
	public UserDetails loadUserByUsername(String username, String role) throws UsernameNotFoundException {
		if(role.equals("USER")) {
			System.out.println("사용자 전용 loadUserByUsername 호출");
			MemberVo userInfo = sqlSession.getMapper(MemberMapper.class).memberInfo(username,"member_id");

			AdminLoginVo authUser = AdminLoginVo.builder()
					.id(userInfo.getMember_email())
					.password(userInfo.getMember_password())
					.role(ROLE.USER)
					.build();
			return authUser;
		}else {
			System.out.println("관리자 전용 loadUserByUsername 호출");
			Optional<AdminLoginVo> adminInfo = sqlSession.getMapper(AdminLoginMapper.class).findAdminInfoById(username);
			if(adminInfo.isPresent()) {
				AdminLoginVo admin = adminInfo.get();
				
				AdminLoginVo authAdmin = AdminLoginVo.builder()
						.id(admin.getId())
						.password(admin.getPassword())
						.role(admin.getRole())
						.build();
				return authAdmin;
			}
		}
		
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AdminLoginVo> adminInfo = sqlSession.getMapper(AdminLoginMapper.class).findAdminInfoById(username);

		if(adminInfo.isPresent()) {
			AdminLoginVo admin = adminInfo.get();
			AdminLoginVo authAdmin = AdminLoginVo.builder()
					.id(admin.getId())
					.password(admin.getPassword())
					.role(admin.getRole())
					.build();
			return authAdmin;
		}
		return null;
	}

}
