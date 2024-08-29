package com.gaji.app.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;

@Service
public class MemberService_imple implements MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public String emailDuplicateCheck(String email) throws Exception {
		
		Optional<Member> emailCheck = memberRepository.findByEmail(email);
		
		return emailCheck.toString();
	}

}
