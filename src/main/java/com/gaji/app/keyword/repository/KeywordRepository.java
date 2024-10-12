package com.gaji.app.keyword.repository;

import com.gaji.app.keyword.domain.Keyword;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeywordRepository extends JpaRepository<Keyword, String> {
	
	Optional<Keyword> findByWord(String newKeyword);
	
	@Query("SELECT CASE WHEN COUNT(k) > 0 THEN true ELSE false END FROM Keyword k WHERE k.word = ?1")
	boolean existsByWord(String word);
	
}
