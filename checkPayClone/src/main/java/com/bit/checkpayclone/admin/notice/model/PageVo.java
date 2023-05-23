package com.bit.checkpayclone.admin.notice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVo {
	private int pageIndex = 1;				    //현재페이지
	private int pageUnit = 8;				    //페이지갯수
	private int pageSize = 0;	    			//페이지사이즈
	private int firstIndex = 1;		    		//firstIndex
	private int recordCountPerPage = 0;			//한페이지당 게시물 수
	private int totCnt = 0;				      	//총갯수
	private int startDate = 0;			    	//페이징 첫 번호
	private int endDate = 0;				    //페이징 마지막 번호
    private int realEnd = 0;				    //페이징 마지막 숫자
		
	private boolean prev, next;	    			//이전,다음버튼
}
