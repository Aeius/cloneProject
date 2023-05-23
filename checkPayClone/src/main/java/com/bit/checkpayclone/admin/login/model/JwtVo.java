package com.bit.checkpayclone.admin.login.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@Builder
@NoArgsConstructor
public class JwtVo {
	 private String accessToken;
	 private String refreshToken;
	 private String role;
}
