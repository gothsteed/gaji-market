package com.gaji.app.auth.dto;


public class KakaoAuthToken {
    public String token_type;
    public String access_token;
    public String id_token;
    public String refresh_token;
    public Integer expires_in;
    public Integer refresh_token_expires_in;
    public String scope;
}
