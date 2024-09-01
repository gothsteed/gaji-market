package com.gaji.app.auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface OAuth2Handler {

    String getAccessToken(String code);

    String getEmail(String accessToken);

    Authentication loginByEmail(String email) throws UsernameNotFoundException;
}
