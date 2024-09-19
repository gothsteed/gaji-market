package com.gaji.app.keyword.service;

import com.gaji.app.keyword.domain.Keyword;
import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.repository.KeywordAlertRepository;
import com.gaji.app.keyword.repository.KeywordRegisterRepository;
import com.gaji.app.keyword.repository.KeywordRepository;
import com.gaji.app.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class KeywordService implements AlertSubject<Product>{

    private Set<AlertObserver<Product>> keywordSubscriber;



    public KeywordService(Set<AlertObserver<Product>> subscribers) {
        keywordSubscriber = subscribers;
    }

    @Override
    public void attach(AlertObserver<Product> observer) {
        keywordSubscriber.add(observer);
    }

    @Override
    public void detach(AlertObserver<Product> observer) {
        keywordSubscriber.remove(observer);
    }

    @Override
    public void notify(Product message) {
        for(AlertObserver<Product> observer : keywordSubscriber) {
            observer.alert(message);
        }

    }
}
