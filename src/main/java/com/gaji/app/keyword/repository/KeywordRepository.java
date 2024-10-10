package com.gaji.app.keyword.repository;

import com.gaji.app.keyword.domain.Keyword;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, String> {
	
	Optional<Keyword> findByWord(String newKeyword);
	
	boolean existsByWord(String word);
	
}
