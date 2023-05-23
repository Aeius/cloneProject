package com.bit.checkpayclone.admin.notice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bit.checkpayclone.admin.notice.model.AdminNoticeVo;
import com.bit.checkpayclone.admin.notice.service.AdminNoticeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notice")
public class AdminNoticeController {
	final AdminNoticeService noticeService;


	//페이징처리된 공지리스트 조회 api
	@GetMapping("/{pageIndex}")	
	public Map<String, Object> getNoticeList(AdminNoticeVo searchVo,@PathVariable("pageIndex") int pageIndex, String board_tp, String use_yn, String seq_type, String searchValue) {
		return noticeService.getNoticeWithPaging(searchVo, pageIndex, board_tp, use_yn, seq_type,"Y", searchValue);
	}
	 
	//상세 공지 조회 api
	@GetMapping("/detail/{notice_num}")
	public Map<String, Object> getNoticeDetail(@PathVariable("notice_num") int num) {
		return noticeService.selectOneNotice(num);
	}

	//공지 작성 api
	@PostMapping("/add")
	public int addNotice(@RequestBody Map<String, Object> map) {
		return noticeService.insertNotice(map);
	}
	
	//공지 수정 api
	@PutMapping("/detail/modify")
	public int updateNotice(@RequestBody Map<String, Object> map) {
		return noticeService.updateNotice(map);
	}

	//공지 삭제 api
	@DeleteMapping("/detail/delete/{notice_num}")
	public int deleteNotice(@PathVariable("notice_num") int num) {
		return noticeService.deleteNotice(num);
	}
	
}
