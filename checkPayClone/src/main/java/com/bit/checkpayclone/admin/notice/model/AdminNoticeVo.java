package com.bit.checkpayclone.admin.notice.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AdminNoticeVo extends PageVo{
	private int notice_num;
	private String subject, content, nalja, board_tp, use_yn;
	
	
	@Builder
	public AdminNoticeVo(int notice_num, String subject, String content, String nalja, String board_tp, String use_yn) {
		super();
		this.notice_num = notice_num;
		this.subject = subject;
		this.content = content;
		this.nalja = nalja;
		this.board_tp = board_tp;
		this.use_yn = use_yn;
	}
	
	
}
