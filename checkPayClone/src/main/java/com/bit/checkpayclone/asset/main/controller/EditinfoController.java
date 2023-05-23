package com.bit.checkpayclone.asset.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.asset.main.model.EditinfoVo;
import com.bit.checkpayclone.asset.main.model.UpdateinfoVo;
import com.bit.checkpayclone.asset.main.service.EditinfoService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
class EditinfoController {

	final EditinfoService editinfoService;

	/*
	 * 특정 유저의 자산 메인화면 순서 리스트 조회 API [GET] /api/asset/main/editinfo/{member_id} 작성자 :
	 * 박정훈
	 */
	@GetMapping("/api/asset/main/editinfo/{member_id}")
	public ResponseEntity<?> getEditList(@PathVariable("member_id") String member_id) {
		EditinfoVo editinfo = (EditinfoVo) editinfoService.getUserEditList(member_id);
		String[] editList = (String[]) editinfo.getCatg_cd();
		List temp = new ArrayList<>();
		for (String string : editList) {
			if (string.contains("ONE") || string.contains("TWO")) {
			} else {
				if (string.equals("telecom")) {
					temp.add("mainphone");
				}
				if (string.equals("bank")) {
					temp.add("mainbank");
				}
				if (string.equals("card")) {
					temp.add("maincard");
				}
				if (string.equals("efin")) {
					temp.add("mainpay");
				}
				if (string.equals("invest")) {
					temp.add("maininvest");
				}
				if (string.equals("capital")) {
					temp.add("maincap");
				}
			}
		}
		EditinfoVo newEdit = new EditinfoVo(editinfo.getMemb_cd(), temp.toArray());
		return ResponseEntity.ok(newEdit);
	}

	/*
	 * 특정 유저의 자산 메인화면 순서 리스트 변경 API [POST] /api/asset/main/editinfo/ 작성자 : 박정훈
	 */
	@PostMapping("/api/asset/main/editinfo/")
	public void setEditList(@RequestBody UpdateinfoVo updateinfoVo) {
		editinfoService.setUserEditList(updateinfoVo.getMember_id(), updateinfoVo.getEdit_list(),
				updateinfoVo.getHide_list());
	}

	
	@GetMapping("/api/asset/main/{id}")
	public ResponseEntity<?> memberAssetSum(@PathVariable("id") String id){
		return ResponseEntity.ok(editinfoService.getAssetSum(id));
	}
}
