package com.gaji.app.member.repository;


import com.gaji.app.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userid);
    Optional<Member> findByEmail(String email); // 이메일 중복 체크시 사용하려고 추가

    Optional<Member> findByMemberSeqAndNickname(Long memberSeq, String nic); // 수정시 별명 중복 체크

    Optional<Member> findByMemberSeqAndPassword(Long memberSeq, String pwd); // 수정시 비밀번호 중복 체크

    Optional<Member> findByMemberSeqAndTel(Long memberSeq, String tel); // 수정시 전화번호 중복 체크

    Optional<Member> findByMemberSeqAndEmail(Long memberSeq, String email); // 수정시 이메일 중복 체크

    Optional<Member> findByMemberSeq(Long memberSeq); // 이메일 중복 체크시 사용하려고 추가



}
