package com.gaji.app.keyword.repository;

import com.gaji.app.keyword.domain.KeywordRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRegisterRepository extends JpaRepository<KeywordRegister, Long> {
}
