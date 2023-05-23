package com.bit.checkpayclone.config;

import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//20230330 김세환: 메일서비스를 위한 Mypage config 추가
@Configuration
@EnableAsync
public class MypageConfig {
	@Value("${spring.mailsender.host}")
	String mailSendHost;
	@Value("${spring.mailsender.port}")
	int mailSendPort;
	@Value("${spring.mailsender.username}")
	String mailSendUserName;
	@Value("${spring.mailsender.password}")
	String mailSendPassword;
	
	
	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl impl = new JavaMailSenderImpl();
		impl.setHost(mailSendHost);
		impl.setPort(mailSendPort);
		impl.setUsername(mailSendUserName);
		impl.setPassword(mailSendPassword);
		impl.setDefaultEncoding("UTF-8");
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", true);
		
		// gmail의 경우 보안문제 업데이트로 인해 SSLSocketFactory를 추가해야 smtp 사용 가능.
		properties.put("mail.smtp.socketFactory.class", SSLSocketFactory.class);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.debug", true);
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		impl.setJavaMailProperties(properties);
		return impl;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
