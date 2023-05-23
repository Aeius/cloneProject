package com.bit.checkpayclone.admin.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.bit.checkpayclone.admin.notice.model.AdminNoticeMapper;
import com.bit.checkpayclone.admin.notice.model.AdminNoticeVo;
import com.bit.checkpayclone.admin.notice.model.Pagination;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class AdminNoticeService {
	final SqlSession sqlSession;

	//공지게시판 페이징처리 및 불러오기
	public Map<String, Object> getNoticeWithPaging(AdminNoticeVo searchVo,int pageIndex, String board_tp, String use_yn, String seq_type,String pagination_yn, String searchValue) {
		Map<String, Object> map = new HashMap<>();
		Pagination pagination = new Pagination();
		
		pagination.setCurrentPageNo(pageIndex);
	    pagination.setRecordCountPerPage(searchVo.getPageUnit());
	    pagination.setPageSize(searchVo.getPageSize());
	    searchVo.setFirstIndex(pagination.getFirstRecordIndex());
	    searchVo.setRecordCountPerPage(pagination.getRecordCountPerPage());

	    seq_type = (seq_type!=null&&!seq_type.equals(""))?seq_type:"ord_seq";
	    
	    // 목록 조회
	    List<AdminNoticeVo> noticeList = sqlSession.getMapper(AdminNoticeMapper.class).getNotice(searchVo.getFirstIndex(),searchVo.getRecordCountPerPage(),board_tp,use_yn,seq_type,pagination_yn,searchValue);
	    int noticeListCount = sqlSession.getMapper(AdminNoticeMapper.class).getNoticeCount(board_tp,use_yn,searchValue);

	    pagination.setTotalRecordCount(noticeListCount);
	    
	    searchVo.setPageIndex(pageIndex);
	    searchVo.setEndDate(pagination.getLastPageNoOnPageList());
	    searchVo.setStartDate(pagination.getFirstPageNoOnPageList());
	    searchVo.setPrev(pagination.getXprev());
	    searchVo.setNext(pagination.getXnext());
	    searchVo.setRealEnd(pagination.getRealEnd());
	    
	    map.put("noticeList",noticeList);
	    map.put("totCnt",noticeListCount);
	    map.put("pagination",searchVo);
	    map.put("totalPageCnt",(int)Math.ceil(noticeListCount / (double)searchVo.getPageUnit()));
	    
		return map;
	}

	//공지 상세 조회
	public Map<String,Object> selectOneNotice(int num) {
		 AdminNoticeVo adminNoticeVo = sqlSession.getMapper(AdminNoticeMapper.class).findOneNotice(num);
		 Map<String, Object> map = new HashMap<>();
		 map.put("noticeNum",adminNoticeVo.getNotice_num());
		 map.put("subject",adminNoticeVo.getSubject());
		 map.put("content",adminNoticeVo.getContent());
		 map.put("nalja", adminNoticeVo.getNalja());
		 return map;
	}
	
	//공지 작성
	public int insertNotice(Map<String, Object> map) {
		String subject= map.get("subject").toString();
		String content= map.get("content").toString();
		if(subject.isEmpty())return 0;
		else if(content.isEmpty())return 2;
		return sqlSession.getMapper(AdminNoticeMapper.class).insertNotice(map.get("subject").toString(), map.get("content").toString());
		
	}
	
	//수정
	public int updateNotice(Map<String, Object> map) {
		String subject= map.get("subject")!=null?map.get("subject").toString():null;
		String content= map.get("content")!=null?map.get("content").toString():null;
		String use_yn = map.get("use_yn")!=null?map.get("use_yn").toString():null;
		
		int num = (int) map.get("notice_num");
		
		return sqlSession
				.getMapper(AdminNoticeMapper.class)
				.updateNotice(subject,content,use_yn,num);
	}
	
	//삭제
	public int deleteNotice(int num) {
		return sqlSession.getMapper(AdminNoticeMapper.class).deleteNotice(num);
	}
	
}
