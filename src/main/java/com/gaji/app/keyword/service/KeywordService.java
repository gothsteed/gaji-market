package com.gaji.app.keyword.service;

import com.gaji.app.keyword.domain.Keyword;
import com.gaji.app.keyword.domain.KeywordAlert;
import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.repository.KeywordAlertRepository;
import com.gaji.app.keyword.repository.KeywordRegisterRepository;
import com.gaji.app.keyword.repository.KeywordRepository;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class KeywordService {

    private final Map<String, Set<KeywordObserver>> keywordObservers = new ConcurrentHashMap<>();
    private final KeywordRepository keywordRepository;
    private final KeywordAlertRepository keywordAlertRepository;
    private final KeywordRegisterRepository keywordRegisterRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository,
                        KeywordRegisterRepository keywordRegisterRepository,
                        KeywordAlertRepository keywordAlertRepository,
                          MemberRepository memberRepository) {
        this.keywordRepository = keywordRepository;
        this.keywordRegisterRepository = keywordRegisterRepository;
        this.keywordAlertRepository = keywordAlertRepository;
        this.memberRepository = memberRepository;
    }

    private void initializeKeywordObservers() {
        keywordRegisterRepository.findAll().forEach(this::registerKeyword);
    }

    private void initializeKeywordObservers(Keyword word) {
//        keywordRegisterRepository.findByWord(word).forEach(this::registerKeyword);
        keywordRegisterRepository.findByKeyword(word).forEach(this::registerKeyword);
    }

    private void registerKeyword(KeywordRegister keywordRegister) {
        keywordObservers.computeIfAbsent(keywordRegister.getWord(), k -> new HashSet<>()).add(new KeywordObserver(keywordRegister, keywordAlertRepository));
    }

    public void registerKeyword(String newKeyword, long memberSeq) {
        Keyword keyword = keywordRepository.findByWord(newKeyword).orElseGet(() -> keywordRepository.save(new Keyword(newKeyword)));

        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        KeywordRegister register = keywordRegisterRepository.save(new KeywordRegister(keyword, member));

        if(keywordObservers.containsKey(register.getWord())) {
            keywordObservers.get(register.getWord()).add(new KeywordObserver(register, keywordAlertRepository));
        }


    }

    public void removeObserver(String newKeyword, long memberSeq) {
        Keyword keyword = keywordRepository.findByWord(newKeyword).orElseGet(() -> keywordRepository.save(new Keyword(newKeyword)));
        Member member = memberRepository.findById(memberSeq).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다"));
        keywordRegisterRepository.delete(new KeywordRegister(keyword, member));

        KeywordRegister register = new KeywordRegister(keyword, member);
        keywordRegisterRepository.delete(register);

        if(keywordObservers.containsKey(newKeyword)) {
            keywordObservers.get(newKeyword).remove(new KeywordObserver(register, keywordAlertRepository));
        }
    }

    public void alert(Product product) {
        Keyword word = product.getKeyword();
        if(!keywordObservers.containsKey(word.getWord())) {
            initializeKeywordObservers(word);
        }

        Set<KeywordObserver> observers = keywordObservers.get(word.getWord());
        for(KeywordObserver observer : observers) {
            observer.alert(product);
        }
    }


    @Scheduled(fixedRate = 1800000)
    public void refreshKeywordObservers() {
        keywordObservers.clear();
    }

    // 키워드테이블에 키워드 삽입
	public boolean insertKeyword(Keyword keyword) {
		if (keywordRepository.existsByWord(keyword.getWord())) {
			return true; // 이미 존재하는 경우 성공으로 간주
		}
		    
		try {
	        keywordRepository.save(keyword); // 삽입 시도
	        return true; // 삽입 성공
	    } catch (Exception e) {// 예외 처리
	        e.printStackTrace();
	        return false; // 삽입 실패
	    }
	}
    public List<KeywordAlert> getKeywordAlert(long memberSeq) {

        return keywordAlertRepository.findByMemberSeq(memberSeq);
    }
}
