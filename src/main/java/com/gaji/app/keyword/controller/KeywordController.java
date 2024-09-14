package com.gaji.app.keyword.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.dto.KeywordDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    public KeywordController(MemberService memberService) {
        this.memberService = memberService;
    }
	
	@ResponseBody
	@PostMapping("/keyword/insertkeyword")
	public ResponseEntity<Void> insertkeyword(@RequestParam String newKeyword, @AuthenticationPrincipal MemberUserDetail userDetail) {
		
		Long memberseq = userDetail.getMemberSeq();
		boolean isInserted = memberService.addKeyword(newKeyword, memberseq);
		
		if (isInserted) {
    		return ResponseEntity.ok().build();
    	} else {
    		return ResponseEntity.notFound().build();
    	}
	}
	
	@ResponseBody
	@PostMapping("/keyword/keywordlist")
	public ResponseEntity<List<KeywordDTO>> getKeywordList(@AuthenticationPrincipal MemberUserDetail userDetail) {
	    Long memberseq = userDetail.getMemberSeq();
	    List<KeywordRegister> keywordList = memberService.getKeywordListByMemberSeq(memberseq);
	    
	    // Convert to DTOs
	    List<KeywordDTO> keywordDTOs = keywordList.stream()
	        .map(kr -> new KeywordDTO(kr.getWord().getWord()))
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(keywordDTOs);
	}

}
