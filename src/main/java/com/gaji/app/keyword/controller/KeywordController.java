package com.gaji.app.keyword.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.dto.KeywordDTO;

import java.util.List;
import java.util.stream.Collectors;

import com.gaji.app.keyword.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaji.app.member.service.MemberService;

@Controller
public class KeywordController {

	private MemberService memberService;
	private KeywordService keywordService;

	@Autowired
	public KeywordController(MemberService memberService, KeywordService keywordService) {
		this.memberService = memberService;
		this.keywordService = keywordService;
	}

	@ResponseBody
	@PostMapping("/keyword/insertkeyword")
	public ResponseEntity<String> insertkeyword(@RequestParam String newKeyword, @AuthenticationPrincipal MemberUserDetail userDetail) {
		
		Long memberseq = userDetail.getMemberSeq();

		keywordService.registerKeyword(newKeyword, memberseq);
		return ResponseEntity.ok().build();

//		if (isInserted) {
//
//    	} else {
//    		return ResponseEntity.notFound().build();
//    	}
	}
	
	@ResponseBody
	@PostMapping("/keyword/keywordlist")
	public ResponseEntity<List<KeywordDTO>> getKeywordList(@AuthenticationPrincipal MemberUserDetail userDetail) {
	    Long memberseq = userDetail.getMemberSeq();
	    List<KeywordRegister> keywordList = memberService.getKeywordListByMemberSeq(memberseq);
	    
	    // Convert to DTOs
	    List<KeywordDTO> keywordDTOs = keywordList.stream()
	        .map(kr -> new KeywordDTO(kr.getWord()))
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(keywordDTOs);
	}
	
	@ResponseBody
	@PostMapping("/keyword/keyworddelete")
	public ResponseEntity<Void> Keyworddelete(@RequestParam String keywordname, @AuthenticationPrincipal MemberUserDetail userDetail) {
	    Long memberseq = userDetail.getMemberSeq();

	    boolean isDeleted = memberService.deleteKeyword(keywordname, memberseq);

	    
	    if (isDeleted) {
	        return ResponseEntity.ok().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}


}
