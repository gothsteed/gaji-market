package com.gaji.app.auth.controller;

import com.gaji.app.auth.jwtUtil.JwtUtil;
import com.gaji.app.auth.service.DefaultUserDetailService;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

    private final DefaultUserDetailService userDetailService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginRestController(DefaultUserDetailService userDetailService,
                               JwtUtil jwtUtil,
                               AuthenticationManager authenticationManager) {
        this.userDetailService = userDetailService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }


    public ResponseEntity<String> getJWT(@RequestBody AuthenticationRequest authenticationRequest) {

        try{
            //authenticationManager.authenticate();
        }
        catch(Exception e){


        }

    }
}
