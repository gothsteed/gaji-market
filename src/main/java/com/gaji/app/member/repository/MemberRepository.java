package com.gaji.app.member.repository;


import com.gaji.app.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userid);

    Optional<Member> findByEmail(String email);
}
