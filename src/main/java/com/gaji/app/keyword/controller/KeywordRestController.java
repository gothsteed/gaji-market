package com.gaji.app.keyword.controller;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.keyword.domain.KeywordAlert;
import com.gaji.app.keyword.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KeywordRestController {

    KeywordService keywordService;

    @Autowired
    public KeywordRestController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    //todo: JWT 토큰 사용해서 stateless 구현
    @PostMapping("/api/keyword")
    public ResponseEntity<List<KeywordAlert>> getKeywordAlert(@AuthenticationPrincipal MemberUserDetail user) {

        long memberSeq = user.getMemberSeq();

        List<KeywordAlert> keywordAlertList = keywordService.getKeywordAlert(memberSeq);

        return ResponseEntity.ok(keywordAlertList);
    }
}
