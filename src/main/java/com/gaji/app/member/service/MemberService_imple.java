package com.gaji.app.member.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.dto.MemberDTO;
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

    @Override
    public int memberRegister_end(MemberDTO mdto) throws Exception {
        // MemberDTO를 Member 엔티티로 변환
        Member member = new Member(mdto.getUserId(), mdto.getName(), mdto.getNickname(), mdto.getPassword(), mdto.getEmail(), mdto.getTel(), mdto.getProfilepic());
        
        try {
            // Member 엔티티 저장
            Member savedMember = memberRepository.save(member);

            // 저장된 엔티티의 ID를 반환 (성공적으로 저장되었음을 나타냄)
            return savedMember.getMemberSeq() != null ? 1 : 0;
        } catch (Exception e) {
            // 예외가 발생하면 실패를 나타내는 0을 반환
            e.printStackTrace();
            return 0;
        }
    }
	@Override
	public String idDuplicateCheck(String id) throws Exception {

		Optional<Member> idCheck = memberRepository.findByUserId(id);
		
		return idCheck.toString();
	}

}
