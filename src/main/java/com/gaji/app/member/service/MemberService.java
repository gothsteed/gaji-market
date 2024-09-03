package com.gaji.app.member.service;

import com.gaji.app.member.domain.Member;
import com.gaji.app.member.dto.MyPageDto;
import com.gaji.app.member.repository.MemberRepository;
import com.gaji.app.product.repository.ProductRepository;
import com.gaji.app.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private ProductRepository productRepository;
    private ReviewRepository reviewRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository, ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public MyPageDto getMyPageInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();

        Member member = memberRepository.findByUserId(userid).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디 입니다: " + userid));
        int productCount = productRepository.countOnSaleProductsByMemberSeq(userid);
        int reviewCount = reviewRepository.countReviewByUserid(userid);
        int likeCount = productRepository.countLikedProductByUserid(userid);
        int soldCount = productRepository.countSoldProductsByMemberSeq(userid);

        MyPageDto myPageDto = new MyPageDto();
        myPageDto.setName(member.getName());
        myPageDto.setMannerTemp(member.getMannerTemp());
        myPageDto.setOnSaleCount(productCount);
        myPageDto.setSoldCount(soldCount);
        myPageDto.setReviewCount(reviewCount);
        myPageDto.setLikedProductCount(likeCount);


        return myPageDto;
    }
}
