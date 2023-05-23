package com.bit.checkpayclone.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author wonhak
 * @date 2023-03-28
 *
 * 원할한 개발을 위해 filter 사용 안 함
 * 
 */
//@Component
public class TokenCheckFilter extends HttpFilter {

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String Authorization = request.getHeader("Authorization");
		
		if(Authorization==null) {
			response.setStatus(403, "token is invalid");
			return;
		}
		
		String token = Authorization.split(" ")[1];
		
		if(token==null) {
			response.setStatus(403, "token is invalid");
			return;
		}
		
		chain.doFilter(request, response);
	}
}
