package com.gaji.app.keyword.service;

import com.gaji.app.keyword.domain.Keyword;
import com.gaji.app.keyword.domain.KeywordRegister;
import com.gaji.app.keyword.repository.KeywordAlertRepository;
import com.gaji.app.keyword.repository.KeywordRegisterRepository;
import com.gaji.app.keyword.repository.KeywordRepository;
import com.gaji.app.member.domain.Member;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KeywordServiceTest {

    @Mock
    private KeywordRepository keywordRepository;

    @Mock
    private KeywordAlertRepository keywordAlertRepository;

    @Mock
    private KeywordRegisterRepository keywordRegisterRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private KeywordService keywordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAlert() throws Exception {

        Keyword keyword = new Keyword("testKeyword");
        Product product = mock(Product.class);
        when(product.getKeyword()).thenReturn(keyword);

        Member member = new Member();
        KeywordRegister keywordRegister = new KeywordRegister(keyword, member);
        KeywordObserver keywordObserver = new KeywordObserver(keywordRegister, keywordAlertRepository);

        when(keywordRegisterRepository.findByKeyword(keyword)).thenReturn(List.of(keywordRegister));
        when(product.getKeyword()).thenReturn(keyword);

        keywordService.alert(product);

        Field observerField = KeywordService.class.getDeclaredField("keywordObservers");
        observerField.setAccessible(true);
        Map<String, Set<KeywordObserver>> observerMap = (Map<String, Set<KeywordObserver>>) observerField.get(keywordService);

        assertTrue(observerMap.containsKey(keyword.getWord()), "no keyword in map");
        Set<KeywordObserver> observersSet = observerMap.get(keyword.getWord());
        assertNotNull(observersSet, "no observer subscribed");
        assertEquals(1, observersSet.size(), "no observer for the keyword");
        assertTrue(observersSet.contains(keywordObserver), "correct observer not registered");

        verify(keywordAlertRepository, times(1)).save(any());



    }
}