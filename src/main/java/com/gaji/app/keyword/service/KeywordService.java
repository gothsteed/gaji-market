package com.gaji.app.keyword.service;

import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.repository.KeywordAlertRepository;
import com.gaji.app.keyword.repository.KeywordRegisterRepository;
import com.gaji.app.keyword.repository.KeywordRepository;
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
        initializeKeywordObservers();
    }

    private void initializeKeywordObservers() {
        keywordRegisterRepository.findAll().forEach(this::registerKeyword);
    }

    private void initializeKeywordObservers(String word) {
        keywordRegisterRepository.findByWord(word).forEach(this::registerKeyword);
    }

    public void registerKeyword(KeywordRegister register) {
        if(keywordObservers.containsKey(register.getWord())) {
            keywordObservers.get(register.getWord()).add(new KeywordObserver(register, keywordAlertRepository));
        }

        keywordRegisterRepository.save(register);
    }

    public void alert(Product product) {
        String word = product.getKeywordString();
        if(!keywordObservers.containsKey(word)) {
            initializeKeywordObservers(word);
        }

        Set<KeywordObserver> observers = keywordObservers.get(word);
        for(KeywordObserver observer : observers) {
            observer.alert(product);
        }
    }


    @Scheduled(fixedRate = 1800000) // Run every hour
    public void refreshKeywordObservers() {
        keywordObservers.clear();
    }
}
