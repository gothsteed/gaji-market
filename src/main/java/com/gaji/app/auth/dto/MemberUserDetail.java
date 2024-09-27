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
    private String role;  // 역할 필드

    public MemberUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, Long memberSeq, String userId, String role) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.memberSeq = memberSeq;
        this.userId = userId;
        this.role = role;  // 역할 필드 초기화
    }

    // role 없이 사용하는 생성자 추가
    public MemberUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, Long memberSeq, String userId) {
        this(username, password, authorities, memberSeq, userId, "MEMBER"); // 기본 역할을 "MEMBER"로 설정
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

    // Add getters for memberSeq, userId, and role
    public Long getMemberSeq() {
        return memberSeq;
    }

    public String getUserId() {
        return userId;
    }

    public String getRole() {  // 역할 필드에 대한 getter 추가
        return role;
    }
}