package com.gaji.app.keyword.repository;

import com.gaji.app.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
