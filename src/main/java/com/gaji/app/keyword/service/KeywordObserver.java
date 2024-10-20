package com.gaji.app.keyword.service;

import com.gaji.app.keyword.domain.KeywordAlert;
import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.repository.KeywordAlertRepository;
import com.gaji.app.product.domain.Product;

import java.time.LocalDateTime;
import java.util.Objects;

public class KeywordObserver implements AlertObserver<Product>{
    private final KeywordRegister keywordRegister;
    private final KeywordAlertRepository keywordAlertRepository;

    public KeywordObserver(KeywordRegister keywordRegister, KeywordAlertRepository keywordAlertRepository) {
        this.keywordRegister = keywordRegister;
        this.keywordAlertRepository = keywordAlertRepository;
    }

    @Override
    public void alert(Product product) {
        keywordAlertRepository.save(new KeywordAlert(product.getProductseq(), keywordRegister.getWord(), keywordRegister.getMemberSeq(), LocalDateTime.now()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeywordObserver that = (KeywordObserver) o;
        return Objects.equals(keywordRegister, that.keywordRegister);
    }

    @Override
    public int hashCode() {
        return  keywordRegister.hashCode();
    }
}
