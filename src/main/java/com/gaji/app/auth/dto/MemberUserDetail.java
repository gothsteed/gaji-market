package com.gaji.app.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MemberUserDetail implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Long memberSeq;
    private String userId;

    public MemberUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, Long memberSeq, String userId) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.memberSeq = memberSeq;
        this.userId = userId;
    }

    // Implement all methods from UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Add getters for memberSeq and userId
    public Long getMemberSeq() {
        return memberSeq;
    }

    public String getUserId() {
        return userId;
    }
}
