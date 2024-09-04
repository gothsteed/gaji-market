package com.gaji.app.auth.service;

import com.gaji.app.auth.dto.MemberUserDetail;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        UserDetails memberUserDetails  = new MemberUserDetail(member.getName(), member.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("MEMBER")), member.getMemberSeq(), member.getUserId());

        return memberUserDetails;
    }


}
