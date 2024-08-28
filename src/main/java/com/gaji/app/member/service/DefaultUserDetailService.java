package com.gaji.app.member.service;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class DefaultUserDetailService implements UserDetailsService {

    private MemberRepository memberRepository;

    @Autowired
    public DefaultUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userid)
                .orElseThrow( () -> new UsernameNotFoundException("존재하지 않는 아이디 입니다: " + userid));
        return new User(member.getUserId(), member.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("MEMBER")));
    }
}
