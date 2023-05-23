package com.bit.checkpayclone.admin.notice.model;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminNoticeMapper {
	
	/**
	 * 공지 리스트 페이징 처리
	 * 
	 * @param firstIndex : 게시글 범위(첫번째로 보여줄 게시글 idx)
	 * @param recordCountPerPage : 한페이지당 게시되는 게시물 수
	 * @param board_tp : 게시판 구분 (000001 : 공지사항, 000002 : faq)
	 * @param use_yn : 사용 여부 (Y : 사용, N : 미사용)
	 * @param seq_type : 정렬 기준 컬럼
	 * @param pagination_yn : 페이지네이션 사용 여부 (Y : 사용, N : 미사용)
	 * @param searchValue : 검색 키워드
	 * @return List<AdminNoticeVo>
	 */
	List<AdminNoticeVo> getNotice(int firstIndex,int recordCountPerPage,String board_tp,String use_yn,String seq_type,String pagination_yn,String searchValue);
	
	/**
	 * 공지 개수 받기
	 * 
	 * @param board_tp : board_tp : 게시판 구분 (000001 : 공지사항, 000002 : faq)
	 * @param use_yn : 사용 여부 (Y : 사용, N : 미사용)
	 * @param searchValue : 검색 키워드
	 * @return int
	 */
	int getNoticeCount(String board_tp,String use_yn,String searchValue);
	
	/**
	 * 공지 디테일 조회
	 * 
	 * @param num : notice_num(공지게시글 번호)
	 */
	AdminNoticeVo findOneNotice(int num);
	
	/**
	 * 공지 작성
	 * 
	 * @param subject : 제목
	 * @param content : 내용
	 */
	int insertNotice(String subject,String content);
	
	/**
	 * 공지 수정
	 * 
	 * @param subject : 제목
	 * @param content : 내용
	 * @param use_yn : 사용 여부 (Y : 사용, N : 미사용)
	 * @param num : 공지 번호
	 * @return int 
	 */
	int updateNotice(String subject,String content,String use_yn,int num);
	
	/**
	 * 공지 삭제
	 * 
	 * @param num : 공지 번호
	 * @return int
	 */
	int deleteNotice(int num);
}
