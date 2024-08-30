package com.gaji.app.member.service;

import com.gaji.app.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService_imple implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

}
