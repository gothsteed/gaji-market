package com.gaji.app.auth.controller;

import com.gaji.app.auth.service.KakaoOAuth2Handler;
import com.gaji.app.auth.service.OAuth2Handler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class loginController {
    @Autowired
    private OAuth2Handler kakaoOAuth2Handler;

    @Autowired
    private SecurityContextRepository securityContextRepository;


    @GetMapping("/")
    public String index() { return "redirect:/login"; }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/login/kakao")
    public String TakaoAuthorization(@RequestParam String code, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(code);
        String accessToken = kakaoOAuth2Handler.getAccessToken(code);
        //UserInfo userinfo =  kakaoOAuth2Handler.getUserInfo(accessToken);
        String email  = kakaoOAuth2Handler.getEmail(accessToken);

        Authentication authentication = kakaoOAuth2Handler.loginByEmail(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
        return "redirect:/home";
    }




}
