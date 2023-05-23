package com.bit.checkpayclone.card.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bit.checkpayclone.card.serivce.CardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CardController {
	
	final CardService myCardService;
	
	@GetMapping("/api/card/membercard/{id}")
	public ResponseEntity<?> memberCardList(@PathVariable("id") String id){
		return ResponseEntity.ok(myCardService.selectUserCard(id));
	}
	
	@GetMapping("/api/card/membercard/{id}/{card_id}")
	public ResponseEntity<?> memberCardName(@PathVariable String id, @PathVariable String card_id){
		return ResponseEntity.ok(myCardService.selectUserCardName(id, card_id));
	}
	
	@GetMapping("/api/card/membercardloanlong/{id}")
	public ResponseEntity<?> membercardloanLongList(@PathVariable("id") String id){
		return ResponseEntity.ok(myCardService.selectUserCardloanLong(id));
	}
	
	@GetMapping("/api/card/membercardloanshort/{id}")
	public ResponseEntity<?> membercardloanShortList(@PathVariable("id") String id){
		return ResponseEntity.ok(myCardService.selectUserCardloanShort(id));
	}
	
	@GetMapping("/api/card/membercardpoint/{id}/{org_code}")
	public ResponseEntity<?> membercardPoint(@PathVariable String id, @PathVariable String org_code){
		return ResponseEntity.ok(myCardService.selectUserCardPoint(id, org_code));
	}
	
	@GetMapping("/api/card/revolvig/{id}/{org_code}")
	public ResponseEntity<?> membercardRevolvig(@PathVariable String id, @PathVariable String org_code){
		return ResponseEntity.ok(myCardService.selectUserCardRevolving(id, org_code));
	}
}
