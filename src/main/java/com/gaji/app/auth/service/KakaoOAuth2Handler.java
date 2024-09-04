package com.gaji.app.auth.service;

import com.gaji.app.auth.dto.KakaoAuthToken;
import com.gaji.app.auth.dto.KakaoUserInfo;
import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class KakaoOAuth2Handler implements OAuth2Handler {

    private RestTemplate restTemplate;
    private MemberRepository memberRepository;

    @Autowired
    public KakaoOAuth2Handler(RestTemplate restTemplate, MemberRepository memberRepository) {
        this.restTemplate = restTemplate;
        this.memberRepository = memberRepository;
    }

    @Override
    public String getAccessToken(String code) {
        String requestURL = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "7c27a381ebd55bf96ab7741ce9d9b819");
        params.add("redirect_uri", "http://localhost:8080/gaji/login/kakao");
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        KakaoAuthToken tokenResponse = restTemplate.postForObject(requestURL, request, KakaoAuthToken.class);

        return tokenResponse.access_token;
    }

    @Override
    public String getEmail(String accessToken) {
        String requestURL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        KakaoUserInfo kakaoUserInfo = restTemplate.postForObject(requestURL, request, KakaoUserInfo.class);

        return kakaoUserInfo.kakaoAccount.getEmail();
    }


    //todo : useDetail 서비스와 authentication provider 분리하기
    @Override
    public Authentication loginByEmail(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("아직 회원가입을 하지 않았습니다."));
        UserDetails userDetails  = new MemberUserDetail(member.getName(), member.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("MEMBER")), member.getMemberSeq(), member.getUserId());

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }


}
