package com.gaji.app.keyword.repository;

import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.domain.KeywordRegisterId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeywordRegisterRepository extends JpaRepository<KeywordRegister, KeywordRegisterId> {
    
    @Query("SELECT kr FROM KeywordRegister kr WHERE kr.member.memberSeq = :memberseq")
    List<KeywordRegister> findByMemberSeq(@Param("memberseq") Long memberseq);
}

